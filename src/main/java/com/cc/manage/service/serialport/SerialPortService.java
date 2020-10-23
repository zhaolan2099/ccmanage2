package com.cc.manage.service.serialport;

import com.alibaba.fastjson.JSONObject;
import com.cc.manage.common.Result;
import com.cc.manage.exception.BizException;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.IOException;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/19 17:33
 * @description：
 */
public interface SerialPortService {
    /**
     * 获取所有串口名称
     * @return
     */
    Result getPortNames() throws IOException, BizException;

    /**
     * 打开串口并开始测试
     * @param portName
     */
    Result beginTest(String portName) throws IOException, BizException;

    Result beginScanner(String sn) throws IOException, BizException;

    /**
     * 处理测试逻辑
     * @param testResult
     */
    JSONObject parseTestResult(String testResult) throws IOException, BizException;

    /**
     * 下发MSG
     * @param msg
     * @return
     */
    Result sendMsg(String msg) throws IOException, BizException;
}
