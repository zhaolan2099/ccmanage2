package com.cc.manage.domain.serialport;

import lombok.Data;

/**
 * @author ：
 * @time ：Created in 2020/8/28 10:42
 */
@Data
public class PortParam {
    /**
     * 串口名称
     */
    private String portName;
    /**
     * 串口类型 1测试串口2打印串口
     */
    private Integer type;
}
