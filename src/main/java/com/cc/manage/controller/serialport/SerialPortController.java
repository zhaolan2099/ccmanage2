package com.cc.manage.controller.serialport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cc.manage.common.CodeMsg;
import com.cc.manage.common.Result;
import com.cc.manage.exception.BizException;
import com.cc.manage.service.serialport.SerialPortService;
import com.cc.manage.websocket.WebSocketServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.io.IOException;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/19 18:19
 * @description：
 */
@Api(tags = "串口通信-用于后台管理与通讯端的通讯")
@Slf4j
@RestController
@RequestMapping("/cc/sp")
public class SerialPortController {
    @Resource
    SerialPortService serialPortService;

    /**
     * 接收串口发送的测试信息
     * @param msg 消息内容
     */
    @PostMapping(value = "receiveTestMsg")
    public void receiveTestMsg(@RequestBody String msg){
        log.info("收到通讯端测试串口发送的数据,{}",msg);
        Subject subject = SecurityUtils.getSubject();
        Result result = new Result();
        try {
            serialPortService.parseTestResult(msg);
            result = result.success();
            WebSocketServer.SendMessage(JSON.toJSONString(result), (String) subject.getSession().getId());
        }  catch (IOException e) {
            e.printStackTrace();
            log.info("处理测试数据，业务异常:{}",CodeMsg.NO_PORT_SERVICE.toString());
        }catch (BizException e){
            e.printStackTrace();
            try {
                result = result.fail(e.getCodeMsg());
                WebSocketServer.SendMessage(JSON.toJSONString(result),
                        (String) subject.getSession().getId());
//                WebSocketServer.BroadCastInfo(JSON.toJSONString(result));
            } catch (IOException ex) {
                log.error("wehbsocket未连接");
                ex.printStackTrace();
            }
            log.info("处理测试数据，业务异常:{}",e.getCodeMsg());
        }catch (Exception e){
            e.printStackTrace();
            log.info("处理测试数据，系统异常:{}",e.getMessage());
        }

    }
    @PostMapping(value = "beginScanner")
    @ApiOperation("打开扫码枪串口")
    @ApiModelProperty(value = "串口名称")
    public Result beginScanner(String portName){
        log.info("打开扫码枪串口,请求参数:{}",portName);
        Result result = new Result();
        try {
            result= serialPortService.beginScanner(portName);
        }catch (IOException e){
            e.printStackTrace();
            result = result.fail(CodeMsg.NO_PORT_SERVICE);
            log.info("打开扫码枪串口，业务异常:{}",result.toString());
        }catch (BizException e){
            e.printStackTrace();
            result = result.fail(e.getCodeMsg());
            log.info("打开扫码枪串口，业务异常:{}",result.toString());
        }catch (Exception e){
            e.printStackTrace();
            result = result.fail(CodeMsg.SERVER_ERROR);
            log.info("打开扫码枪串口，系统异常:{}",e.getMessage(),e);
        }
        log.info("打开扫码枪串口，响应参数:{}",result.toString());
        return result;
    }

}
