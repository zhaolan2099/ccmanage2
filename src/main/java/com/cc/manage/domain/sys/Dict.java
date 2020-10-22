package com.cc.manage.domain.sys;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * dict
 * @author 
 */
@Data
public class Dict implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "值",required = true)
    private String val;
    @ApiModelProperty(value = "描述",required = true)
    private String tag;
    @ApiModelProperty(value = "类型id",required = true)
    private Long typeId;
    @ApiModelProperty(value = "类型名称",hidden = true)
    private String typeName;
    @ApiModelProperty(value = "类型编号",hidden = true)
    private String type;

    /**
     * 说明
     */
    @ApiModelProperty(value = "说明")
    private String explain;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    private static final long serialVersionUID = 1L;
}