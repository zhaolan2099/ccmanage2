package com.cc.manage.controller.serialport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cc.manage.common.CodeMsg;
import com.cc.manage.common.Result;
import com.cc.manage.domain.sys.LoginUser;
import com.cc.manage.exception.BizException;
import com.cc.manage.service.serialport.SerialPortService;
import com.cc.manage.utils.UserUtil;
import com.cc.manage.websocket.WebSocketServer;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
     * 接收客户端发送的信息
     * @param msg 消息内容
     */
    @PostMapping(value = "receiveMsg")
    public Result receiveMsg(@RequestBody String msg){

        log.info("收到客户端发送的数据,{}",msg);
        Subject subject = SecurityUtils.getSubject();
        Result result = new Result();
        JSONObject jsonObject = new JSONObject();
        CodeMsg codeMsg = CodeMsg.SUCCESS;
        LoginUser user = new LoginUser();
        try {
            user = UserUtil.getCurrentUser();
            jsonObject = serialPortService.parseTestResult(msg);
            result = result.success();
//            WebSocketServer.SendMessage(JSON.toJSONString(result), (String) subject.getSession().getId());
            WebSocketServer.sendMessageForLoginName(JSON.toJSONString(result), user.getLoginName());
        }catch (BizException e){
            e.printStackTrace();
            result = result.fail(e.getCodeMsg());
//            WebSocketServer.SendMessage(JSON.toJSONString(result),
//                    (String) subject.getSession().getId());
            WebSocketServer.sendMessageForLoginName(JSON.toJSONString(result), user.getLoginName());
            log.info("处理数据，业务异常:{}",e.getCodeMsg());
            codeMsg = e.getCodeMsg();
        }catch (Exception e){
            e.printStackTrace();
            log.info("处理数据，系统异常:{}",e.getMessage());
            codeMsg = CodeMsg.SERVER_ERROR;
        }
        return new Result(codeMsg,jsonObject);
    }

}
