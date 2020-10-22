package com.cc.manage.controller.board;

import com.cc.manage.common.CodeMsg;
import com.cc.manage.common.Constant;
import com.cc.manage.common.Result;
import com.cc.manage.domain.board.Board;
import com.cc.manage.domain.board.BoardForOutExport;
import com.cc.manage.exception.BizException;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.board.BoardQuery;
import com.cc.manage.service.board.BoardService;
import com.cc.manage.utils.ExcelUtil;
import com.cc.manage.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Api(tags = "出库管理")
@Slf4j
@RestController
@RequestMapping("board")
public class BoardOutController {

    @Resource
    BoardService boardService;

    @Resource
    private HttpServletResponse response;


    @ApiOperation("电路板出库列表")
    @GetMapping(value = "outList")
    public Result<PageVo<Board>> outList(BoardQuery query){
        query.setType(Constant.BOARD_QUERY_TYPE_2);
        log.info("电路板出库列表,接口参数,{}",query.toString());
        Result<PageVo<Board>> result = new Result();
        PageVo<Board> pageVo;
        try {
            pageVo = boardService.selectListForPage(query);
            result = result.success(pageVo);
        }catch (BizException e) {
            e.printStackTrace();
            result = result.fail(e.getCodeMsg());
            log.info("电路板台帐列表,业务异常：{}",e.getCodeMsg());
        }catch (Exception e){
            e.printStackTrace();
            result = result.fail(CodeMsg.SERVER_ERROR);
            log.error("电路板出库列表，系统异常:{}",e.getMessage(),e);
        }
        log.info("电路板出库列表，响应参数:{}",result.toString());
        return result;
    }

    @ApiOperation("点开始出库后，扫码枪扫出SN号，通过SN号获取电路板")
    @GetMapping(value = "getBySn")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sn", value = "SN号", required = true, dataType = "string")
    })
    public Result<Board> getBySn(String sn){
        log.info("通过SN号获取电路板,接口参数,{}",sn);
        Result result = new Result();
        Board board;
        try {
            board = boardService.getBySnForOut(sn);
            result = result.success(board);
        } catch (BizException e){
            e.printStackTrace();
            result = result.fail(e.getCodeMsg());
            log.info("开始出库，业务异常:{}",result.toString());
        }catch (Exception e){
            e.printStackTrace();
            result = result.fail(CodeMsg.SERVER_ERROR);
            log.error("开始出库，系统异常:{}",e.getMessage(),e);
        }
        log.info("开始出库，响应参数:{}",result.toString());
        return result;
    }

    @ApiOperation("确认出库")
    @GetMapping(value = "doOut")
    public Result<String> doOut(@RequestParam(value = "sns") List<String> sns){
        log.info("确认出库,接口参数,{}",sns.toString());
        Result<String> result = new Result();
        Jedis jedis = RedisUtil.getJedis();
        try {
            Map<String,String> map= boardService.doOut(sns);
            jedis.set(map.get("key"),map.get("value"));
            result = result.success(map.get("outNum"));
        } catch (BizException e){
            e.printStackTrace();
            result = result.fail(e.getCodeMsg());
            log.info("确认出库，业务异常:{}",result.toString());
        }catch (Exception e){
            e.printStackTrace();
            result = result.fail(CodeMsg.SERVER_ERROR);
            log.error("确认出库，系统异常:{}",e.getMessage(),e);
        }finally {
            jedis.close();
        }
        log.info("确认出库，响应参数:{}",result.toString());
        return result;
    }

    @ApiOperation("出库单导出，确认出库后调用该接口导出excel")
    @GetMapping(value = "export")
    public void export(String outNum){
        log.info("出库单导出,接口参数,{}",outNum);
        List<BoardForOutExport> list = boardService.selectListForOutExport(outNum);
        try {
            ExcelUtil.exportExcel(list, null,
                    "出库单导出", BoardForOutExport.class, "出库单导出.xls", response);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("出库单导出异常");
        }
    }

    @ApiOperation("根据出库单查看")
    @GetMapping(value = "getByOutNum")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "outNum", value = "出库单号", required = true, dataType = "string")
    })
    public Result<List<Board>> getByOutNum(String outNum){
        log.info("根据出库单查看,接口参数,{}",outNum);
        BoardQuery query = new BoardQuery();
        query.setPageHiden(true);
        query.setOutNum(outNum);
        Result<List<Board>> result = new Result();
        List<Board> list;
        try {
            list = boardService.selectList(query);
            result = result.success(list);
        }catch (Exception e){
            e.printStackTrace();
            result = result.fail(CodeMsg.SERVER_ERROR);
            log.error("根据出库单查看，系统异常:{}",e.getMessage(),e);
        }
        log.info("根据出库单查看，响应参数:{}",result.toString());
        return result;
    }

    @ApiOperation("取消出库")
    @GetMapping(value = "cancelOut")
    public Result cancelOut(@RequestParam(value = "sn") List<String> sn){
        log.info("取消出库,接口参数,{}",sn);
        Result result = new Result();
        try {
            boardService.cancelOut(sn);
            result = result.success();
        }catch (Exception e){
            e.printStackTrace();
            result = result.fail(CodeMsg.SERVER_ERROR);
            log.error("取消出库，系统异常:{}",e.getMessage(),e);
        }
        log.info("取消出库，响应参数:{}",result.toString());
        return result;
    }

    @ApiOperation("开始出库")
    @GetMapping(value = "beginOut")
    public Result<List<Board>> beginOut(){
        log.info("开始出库");
        Result<List<Board>> result = new Result();
        try {
            List<Board> list = boardService.beginOut();
            result = result.success(list);
        }catch (BizException e){
            e.printStackTrace();
            result = result.fail(e.getCodeMsg());
            log.error("开始出库，业务异常:{}",e.getMessage(),e);
        }catch (Exception e){
            e.printStackTrace();
            result = result.fail(CodeMsg.SERVER_ERROR);
            log.error("开始出库，系统异常:{}",e.getMessage(),e);
        }
        log.info("开始出库，响应参数:{}",result.toString());
        return result;
    }
}
