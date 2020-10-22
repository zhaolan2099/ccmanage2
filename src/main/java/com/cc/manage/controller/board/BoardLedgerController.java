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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(tags = "电路板台帐")
@Slf4j
@RestController
@RequestMapping("boardLedger")
public class BoardLedgerController {

    @Resource
    BoardService boardService;

    @Resource
    HttpServletResponse response;

    @ApiOperation("电路板台帐列表")
    @GetMapping(value = "allList")
    public Result<PageVo<Board>> allList(BoardQuery query){
        query.setType(Constant.BOARD_QUERY_TYPE_3);
        log.info("电路板台帐列表,接口参数,{}",query.toString());
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
            log.error("电路板台帐列表，系统异常:{}",e.getMessage(),e);
        }
        log.info("电路板台帐列表，响应参数:{}",result.toString());
        return result;
    }

    @ApiOperation("电路板台帐导出")
    @GetMapping(value = "export")
    public void export(BoardQuery query){
        log.info("电路板台帐导出,接口参数,{}",query.toString());
        query.setType(Constant.BOARD_QUERY_TYPE_3);
        List<Board> list = null;
        try {
            list = boardService.selectList(query);
            ExcelUtil.exportExcel(list, null,
                    "电路板台帐导出", Board.class, "电路板台帐导出.xls", response);
        } catch (BizException e) {
            e.printStackTrace();
            log.info("电路板台帐导出,业务异常：{}",e.getCodeMsg());
        }catch (IOException e) {
            e.printStackTrace();
            log.error("电路板台帐导出异常");
        }
    }
}
