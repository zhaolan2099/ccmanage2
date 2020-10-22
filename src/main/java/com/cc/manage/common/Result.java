package com.cc.manage.common;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.BindingResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 接口返回结果封装类。
 * @Author: cby
 * @Date 2019/7/18
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Result<T> implements Serializable{

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "响应码")
    private int code;
    @ApiModelProperty(value = "响应信息")
    private String msg;
    @ApiModelProperty(value = "响应数据")
    private T data;

    public Result(CodeMsg codeMsg){
        this.code=codeMsg.getCode();
        this.msg=codeMsg.getMsg();
    }

    public Result(CodeMsg codeMsg, T data){
        this.code=codeMsg.getCode();
        this.msg=codeMsg.getMsg();
        this.data=data;
    }

    public Result success() {
        return  new Result(CodeMsg.SUCCESS);
    }

    public Result success(Object data) {
        return  new Result(CodeMsg.SUCCESS,data);
    }

    public Result fail(CodeMsg codeMsg) {
        return new Result(codeMsg);
    }


    public static Result createfail(CodeMsg codeMsg) {
        Result result=new Result();
        result.setCode(codeMsg.getCode());
        result.setMsg(codeMsg.getMsg());
        return result;
    }
    /**
     *  参数错误
     * @return
     */
    public void  argumentsFail(String msg){
        this.code=CodeMsg.FAIL.getCode();
        this.msg=msg;
    }
    //spring 参数绑定错误处理
    public static Result deployBindingErr(BindingResult bindingResult){
        List<String> bindingErrorInfoList=new ArrayList<String>();
        bindingResult.getAllErrors().stream().forEach(
                objectError -> {
                    bindingErrorInfoList.add(objectError.getDefaultMessage());
                }
        );
        return new Result(CodeMsg.PARAMS_BINDING_ERROR,bindingErrorInfoList);
    }
}
