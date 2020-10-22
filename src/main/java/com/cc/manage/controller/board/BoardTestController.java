package com.cc.manage.controller.board;

import com.cc.manage.common.CodeMsg;
import com.cc.manage.common.Constant;
import com.cc.manage.common.Result;
import com.cc.manage.domain.board.Board;
import com.cc.manage.exception.BizException;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.board.BoardQuery;
import com.cc.manage.service.board.BoardService;
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

@Api(tags = "电路板检测")
@Slf4j
@RestController
@RequestMapping("boardTest")
public class BoardTestController {

    @Resource
    SerialPortService serialPortService;

    @Resource
    BoardService boardService;

    @ApiOperation("删除所有电路板(联调测试用)")
    @GetMapping(value = "delAll")
    public Result<CodeMsg> delAll(){
        Result<CodeMsg> res = new Result<>();
        boardService.delAll();
        res = res.success();
        return res;
    }

    @ApiOperation("电路板入库列表")
    @GetMapping(value = "putInList")
    public Result<PageVo<Board>> putInList(BoardQuery query){
        log.info("电路板出库列表,接口参数,{}",query.toString());
        query.setType(Constant.BOARD_QUERY_TYPE_1);
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
            log.error("电路板入库列表，系统异常:{}",e.getMessage(),e);
        }
        log.info("电路板入库列表，响应参数:{}",result.toString());
        return result;
    }

    @ApiOperation("扫描本机串口")
    @GetMapping(value = "getSPNames")
    public Result getSPNames(){
        log.info("扫描本机串口接口");
        Result result = new Result();
        try {
            result  = serialPortService.getPortNames();
        }catch (IOException e){
            e.printStackTrace();
            result = result.fail(CodeMsg.NO_PORT_SERVICE);
            log.info("扫描本机串口接口，业务异常:{}",result.toString());
        }catch (BizException e){
            e.printStackTrace();
            result = result.fail(e.getCodeMsg());
            log.info("扫描本机串口接口，业务异常:{}",result.toString());
        }catch (Exception e){
            e.printStackTrace();
            result = result.fail(CodeMsg.SERVER_ERROR);
            log.error("扫描本机串口接口，系统异常:{}",e.getMessage(),e);
        }
        log.info("扫描本机串口接口，响应参数:{}",result.toString());
        return result;
    }

    @GetMapping(value = "beginTest")
    @ApiOperation("确认串口并开始检测")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "portName", value = "串口名称", required = true, dataType = "string")
    })
    public Result beginTest(String portName){
        log.info("开始测试接口,请求参数:{}",portName);
        Result result = new Result();
        try {
            result= serialPortService.beginTest(portName);
        }catch (IOException e){
            e.printStackTrace();
            result = result.fail(CodeMsg.NO_PORT_SERVICE);
            log.info("调用获取串口列表接口，业务异常:{}",result.toString());
        }catch (BizException e){
            e.printStackTrace();
            result = result.fail(e.getCodeMsg());
            log.info("调用获取串口列表接口，业务异常:{}",result.toString());
        }catch (Exception e){
            e.printStackTrace();
            result = result.fail(CodeMsg.SERVER_ERROR);
            log.error("开始测试接口，系统异常:{}",e.getMessage(),e);
        }
        log.info("开始测试接口，响应参数:{}",result.toString());
        return result;
    }

}
