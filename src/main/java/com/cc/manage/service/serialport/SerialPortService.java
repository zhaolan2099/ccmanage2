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
     * 处理测试逻辑
     * @param testResult
     */
    JSONObject parseTestResult(String testResult) throws BizException;
}
