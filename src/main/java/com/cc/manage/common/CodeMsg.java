package com.cc.manage.common;

import lombok.Data;

/**
 * @Description: 返回码
 * @Author: cby
 * @Date 2019/7/23
 */
@Data
public class CodeMsg {
    private int code;
    private String msg;
    private transient boolean hasdetail;

    //通用的错误码
    public static CodeMsg FAIL = new CodeMsg(0, "fail");
    public static CodeMsg SUCCESS = new CodeMsg(1, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500001, "服务端异常");
    public static CodeMsg SESSION_EXPIRED = new CodeMsg(500005, "登录过期，请重新登录");

    public static CodeMsg PARAMS_INVALID_DETAIL = new CodeMsg(100004, "参数错误：%s", true);
    public static CodeMsg PARAMS_BINDING_ERROR = new CodeMsg(100005, "参数绑定错误");
    public static CodeMsg PARAMS_ENCRYPTED = new CodeMsg(100006, "参数中无待解密类：%s", true);
    public static CodeMsg PARAMS_ENCRYPTED_TYPE = new CodeMsg(100007, "参数中未指定解密类型：%s", true);
    public static CodeMsg PARAMS_SIGN_ERR = new CodeMsg(100007, "验签失败", true);
    public static CodeMsg REQ_TIME_OUT = new CodeMsg(100008, "请求失效", true);
    public static CodeMsg SERVERIMPL_ERROR = new CodeMsg(500002, "接口返回错误");
    public static CodeMsg PARAMS_ERROR = new CodeMsg(500003, "参数为空");
    public static CodeMsg PARAMS_DETAIL = new CodeMsg(500004, "参数不一致：%s", true);


    public static CodeMsg ORDER_ERROR_900000 = new CodeMsg(900000, "操作失败:%s", true);

    //端口异常
    public static CodeMsg PORT_PARAMETER_FAILURE = new CodeMsg(80000, "设置串口参数失败", true);
    public static CodeMsg NOT_SERIAL_PORT = new CodeMsg(80001, "端口指向设备不是串口类型", true);
    public static CodeMsg NO_SUCH_PORT = new CodeMsg(80002, "没有该端口对应的串口设备", true);
    public static CodeMsg PORT_IN_USE = new CodeMsg(80003, "端口已被占用", true);
    public static CodeMsg SEND_TO_SERIAL_PORT_FAILURE = new CodeMsg(80004, "向串口发送数据失败", true);
    public static CodeMsg OUTPUT_STREAM_CLOSE_FAILURE = new CodeMsg(80006, "关闭串口对象的输出流出错", true);
    public static CodeMsg READ_FROM_SERIAL_PORT_FAILURE = new CodeMsg(80007, "从串口读取数据时出错", true);
    public static CodeMsg INPUT_STREAM_CLOSE_FAILURE = new CodeMsg(80008, "关闭串口对象输入流出错", true);
    public static CodeMsg TOO_MANY_LISTENERS = new CodeMsg(80008, "关闭串口对象输入流出错", true);
    public static CodeMsg NEED_OPEN_SERIAL_PORT = new CodeMsg(80009, "请先打开串口", true);
    public static CodeMsg NO_PORT_SERVICE = new CodeMsg(80010, "无法连接通信端，请先开启！", true);
    public static CodeMsg NO_PRINT_PORT = new CodeMsg(80011, "打印机串口未打开，请重新测试！", true);

    public CodeMsg(CodeMsg serverError) {

    }

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.hasdetail = false;
    }

    public CodeMsg(int code, String msg, boolean hasdetail) {
        this.code = code;
        this.msg = msg;
        this.hasdetail = hasdetail;

    }

    public CodeMsg formatDetail(String details) {
        if (hasdetail) {
            if (this.code == 100004) {
                this.msg = String.format(String_100004, details);
            }
            if (this.code == 900000) {
                this.msg = String.format(String_900000, details);
            }
            this.msg = String.format(msg, details);
        } else {
            throw new UnsupportedOperationException("该CodeMsg不支持String format。");
        }
        return this;
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ",msg=" + msg + "]";
    }

    private static final String String_100004 = "参数错误: %s";
    private static final String String_900000 = "操作失败: %s";

}
