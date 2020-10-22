package com.cc.manage.exception;

import com.cc.manage.common.CodeMsg;
import lombok.Data;

/**
 * @author wujianjun
 * @date 2019/11/21 11:13 AM
 */
@Data
public class BizException extends Exception {

    private CodeMsg codeMsg;
    public BizException(CodeMsg codeMsg) {
        this.codeMsg = new CodeMsg(codeMsg.getCode(), codeMsg.getMsg());
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(int errorCode, String errorMessage) {
        codeMsg = new CodeMsg(errorCode, errorMessage);
    }

    @Override
    public String toString() {
        return this.codeMsg.toString();
    }

    @Override
    public String getMessage() {
        if (this.codeMsg == null){
            return super.getMessage();
        }
        return this.codeMsg.toString();
    }
}
