package com.cc.manage.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageVo<T> implements Serializable {

    /**
     * 总条数
     */
    @ApiModelProperty(value = "总条数")
    private int total;

    /**
     * list对象
     */
    @ApiModelProperty(value = "分页数据对象")
    private List<T> object;
}
