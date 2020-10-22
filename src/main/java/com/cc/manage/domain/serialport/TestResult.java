package com.cc.manage.domain.serialport;

import lombok.Data;

/**
 * @author ：
 * @time ：Created in 2020/9/3 10:30 测试结果
 */
@Data
public class TestResult {

    /**
     *    {
     * 		"board_type": "1", //测试板类型
     * 		"board_serial": "1",
     * 		"MAC_addr": "000000000000",
     * 		"test_status": "2", //1测试中2完成
     * 		"test_result": "1" //0成功1失败
     *    }
     */
    /** 测试板类型 */
    private String boardType;
    /** 测试板序号 */
    private String boardSerial;
    /** 测试板mac地址*/
    private String macAddr;
    /** 测试状态 1测试中2完成*/
    private String testStatus;
    /** 测试结果 0成功1失败*/
    private String testResult;
    /** 测试结果 0成功1失败*/
    private Long orgId;

}
