package com.cc.manage.common;

/**
 * @author ：
 * @time ：Created in 2020/9/3 9:25
 */
public class Constant {
    /**测试串口 */
    public static final Integer PORT_TYPE_1 = 1;
    /**扫码枪串口 */
    public static final Integer PORT_TYPE_2 = 1;

    public static final String  STATUS_SUCCESS = "0";
    public static final String  STATUS_FAIL = "1";
    //测试完成
    public static final String  STATUS_1 = "1";
    //序列号已写入
    public static final String  STATUS_2 = "2";
    // 入库中
    public static final String  STATUS_3 = "3";
    //已入库
    public static final String  STATUS_4 = "4";
    //出库中
    public static final String  STATUS_5 = "5";
    //已出库
    public static final String  STATUS_6 = "6";
    //查询类型，1检测列表 2,入库列表；3，出库列表；4台帐
    public static final Integer  BOARD_QUERY_TYPE_1 = 1;
    public static final Integer  BOARD_QUERY_TYPE_2 = 2;
    public static final Integer  BOARD_QUERY_TYPE_3 = 3;
    public static final Integer  BOARD_QUERY_TYPE_4 = 4;

    //A1 主板 A2 灯板
    public static final String  BOARD_TYPE_TYPE_A1 = "A1";
    public static final String  BOARD_TYPE_TYPE_A2 = "A2";

    /**开始测试 */
    public static final String TEST_STEP_BEGIN_TEST = "1.1";
    /**写SN号*/
    public static final String TEST_STEP_WRITE_SN = "2.1";
    /**写SN结果*/
    public static final String TEST_STEP_WRITE_SN_RESULT = "2.2";

    public static  final  String SUCCESS = "0";



    //JSON参数
    public static final String MAC_ADDR = "MAC_Addr";
    public static final String MCU_ID = "MCU_ID";
    public static final String TEST_STEP = "Test_Step";
    public static final String TEST_RESULT = "Test_Result";
    public static final String BOARD_TYPE = "Board_Type";
    public static final String RESULT = "Result";
    public static final String SN_WRITE = "SN_Write";
    public static final String SN_WRITE_RESULT = "SN_Write_Result";
    public static final String PRINT = "Print";
    public static final String SN = "SN";
    public static final String SOFTWARE_VERSION = "Software_Version";



    //0失败1成功2等待返回结果
    public static final Integer SN_WRITE_RES_0 = 0;
    public static final Integer SN_WRITE_RES_1 = 1;
    public static final Integer SN_WRITE_RES_2 = 2;

    public static final String REDIS_KEY_PUTIN = "PUTIN";
    public static final String REDIS_KEY_SN = "SN";

}
