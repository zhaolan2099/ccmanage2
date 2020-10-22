package com.cc.manage.domain.sys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * role
 * @author 
 */
@Data
public class Role implements Serializable {
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称",required = true)
    private String name;

    /**
     * 0不可用1可用
     */
    @ApiModelProperty(value = "0不可用1可用",required = true)
    private Integer status;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date ct;

    /**
     * 修改时间
     */
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date mt;

    @ApiModelProperty(value = "角色ids")
    private String menuIds;

    private static final long serialVersionUID = 1L;
}