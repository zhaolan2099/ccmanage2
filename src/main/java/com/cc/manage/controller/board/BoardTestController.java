package com.cc.manage.controller.board;

import com.cc.manage.common.CodeMsg;
import com.cc.manage.common.Constant;
import com.cc.manage.common.Result;
import com.cc.manage.domain.board.Board;
import com.cc.manage.exception.BizException;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.board.BoardQuery;
import com.cc.manage.service.board.BoardService;
import com.cc.manage.service.board.BoardTestService;
import com.cc.manage.service.serialport.SerialPortService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Api(tags = "电路板卡检测")
@Slf4j
@RestController
@RequestMapping("boardTest")
public class BoardTestController {
    @Resource
    BoardService boardService;

    @Resource
    BoardTestService boardTestService;

    @ApiOperation("电路板卡检测列表")
    @GetMapping(value = "list")
    public Result<PageVo<Board>> list(BoardQuery query){
        log.info("电路板卡检测列表,接口参数,{}",query.toString());
        query.setType(Constant.BOARD_QUERY_TYPE_1);
        Result<PageVo<Board>> result = new Result();
        PageVo<Board> pageVo;
        try {
            pageVo = boardService.selectListForPage(query);
            result = result.success(pageVo);
        }catch (BizException e) {
            e.printStackTrace();
            result = result.fail(e.getCodeMsg());
            log.info("电路板卡检测列表,业务异常：{}",e.getCodeMsg());
        }catch (Exception e){
            e.printStackTrace();
            result = result.fail(CodeMsg.SERVER_ERROR);
            log.error("电路板卡检测列表，系统异常:{}",e.getMessage(),e);
        }
        log.info("电路板卡检测列表，响应参数:{}",result.toString());
        return result;
    }
    @ApiOperation("取消")
    @GetMapping(value = "cancel")
    public Result cancel(List<Long> ids){
        log.info("取消,接口参数,{}",ids.toString());
        Result result = new Result();
        try {
            boardTestService.cancel(ids);
            result = result.success();
        }catch (Exception e){
            e.printStackTrace();
            result = result.fail(CodeMsg.SERVER_ERROR);
            log.error("电路板卡检测列表，系统异常:{}",e.getMessage(),e);
        }
        log.info("电路板卡检测列表，响应参数:{}",result.toString());
        return result;
    }
}
