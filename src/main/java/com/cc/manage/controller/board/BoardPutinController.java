package com.cc.manage.controller.board;

import com.cc.manage.common.CodeMsg;
import com.cc.manage.common.Result;
import com.cc.manage.domain.board.Board;
import com.cc.manage.exception.BizException;
import com.cc.manage.service.board.BoardPutinService;
import com.cc.manage.service.board.BoardService;
import com.cc.manage.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "入库管理")
@Slf4j
@RestController
@RequestMapping("boardIn")
public class BoardPutinController {
    @Resource
    BoardPutinService putinService;

    @ApiOperation("点开始入库后，扫码枪扫出SN号，通过SN号获取电路板")
    @GetMapping(value = "getBySn")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sn", value = "SN号", required = true, dataType = "string")
    })
    public Result<Board> getBySn(String sn){
        log.info("通过SN号获取电路板,接口参数,{}",sn);
        Result result = new Result();
        Board board;
        try {
            board = putinService.getForPutIn(sn);
            result = result.success(board);
        } catch (BizException e){
            e.printStackTrace();
            result = result.fail(e.getCodeMsg());
            log.info("通过SN号获取电路板，业务异常:{}",result.toString());
        }catch (Exception e){
            e.printStackTrace();
            result = result.fail(CodeMsg.SERVER_ERROR);
            log.error("通过SN号获取电路板，系统异常:{}",e.getMessage(),e);
        }
        log.info("通过SN号获取电路板，响应参数:{}",result.toString());
        return result;
    }

    @ApiOperation("确认是否是同一类板子")
    @GetMapping(value = "checkType")
    public Result checkType(@RequestParam(value = "sns") List<String> sns){
        log.info("确认是否是同一类板子,接口参数,{}",sns.toString());
        Result<String> result = new Result();
        try {
           return putinService.checkType(sns);
        }catch (Exception e){
            e.printStackTrace();
            result = result.fail(CodeMsg.SERVER_ERROR);
            log.error("确认是否是同一类板子，系统异常:{}",e.getMessage(),e);
        }
        log.info("确认是否是同一类板子，响应参数:{}",result.toString());
        return result;
    }

    @ApiOperation("确认入库")
    @GetMapping(value = "doPutin")
    public Result<String> doPutin(@RequestParam(value = "sns") List<String> sns){
        log.info("确认入库,接口参数,{}",sns.toString());
        Result<String> result = new Result();
        try {
            putinService.doPutin(sns);
        } catch (BizException e){
            e.printStackTrace();
            result = result.fail(e.getCodeMsg());
            log.error("确认入库，业务异常:{}",e.getMessage(),e);
        }catch (Exception e){
            e.printStackTrace();
            result = result.fail(CodeMsg.SERVER_ERROR);
            log.error("确认入库，系统异常:{}",e.getMessage(),e);
        }
        log.info("确认出库，响应参数:{}",result.toString());
        return result;
    }

    @ApiOperation("取消入库")
    @GetMapping(value = "cancelPutin")
    public Result cancelPutin(@RequestParam(value = "sn") List<String> sn){
        log.info("取消入库,接口参数,{}",sn);
        Result result = new Result();
        try {
            putinService.cancelPutin(sn);
            result = result.success();
        }catch (Exception e){
            e.printStackTrace();
            result = result.fail(CodeMsg.SERVER_ERROR);
            log.error("取消入库，系统异常:{}",e.getMessage(),e);
        }
        log.info("取消入库，响应参数:{}",result.toString());
        return result;
    }

    @ApiOperation("入库列表-取消")
    @GetMapping(value = "cancel")
    public Result cancel(@RequestParam(value = "sn") List<String> sn){
        log.info("回到上一步,接口参数,{}",sn);
        Result result = new Result();
        try {
            putinService.cancel(sn);
            result = result.success();
        }catch (Exception e){
            e.printStackTrace();
            result = result.fail(CodeMsg.SERVER_ERROR);
            log.error("回到上一步，系统异常:{}",e.getMessage(),e);
        }
        log.info("回到上一步，响应参数:{}",result.toString());
        return result;
    }

    @ApiOperation("开始入库")
    @GetMapping(value = "beginPutin")
    public Result<List<Board>> beginPutin(){
        log.info("开始入库");
        Result<List<Board>> result = new Result();
        try {
            List<Board> list = putinService.beginPutin();
            result = result.success(list);
        }catch (BizException e){
            e.printStackTrace();
            result = result.fail(e.getCodeMsg());
            log.error("开始入库，业务异常:{}",e.getMessage(),e);
        }catch (Exception e){
            e.printStackTrace();
            result = result.fail(CodeMsg.SERVER_ERROR);
            log.error("开始入库，系统异常:{}",e.getMessage(),e);
        }
        log.info("开始入库，响应参数:{}",result.toString());
        return result;
    }

}
