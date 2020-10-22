package com.cc.manage.domain.sys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable {
    private Long id;

    /**
     * 登陆名
     */
    @ApiModelProperty(value = "登陆名，不允许修改",required = true)
    private String loginName;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名",required = true)
    private String name;

    @ApiModelProperty(value = "密码",required = true)
    private String pwd;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话",required = true)
    private String phone;

    /**
     * 所属组织
     */
    @ApiModelProperty(value = "公司ID",required = true)
    private Long orgId;

    /**
     * 0禁用1正常
     */
    @ApiModelProperty(value = "0禁用1正常",required = true)
    private Integer status;

    private Integer isDelete;

    /**
     * 最近一次登陆时间
     */
    @ApiModelProperty(hidden = true)
    private Date lastLoginTime;
    @ApiModelProperty(hidden = true)
    private String temp2;
    @ApiModelProperty(hidden = true)
    private String temp1;

    /**
     * 修改时间
     */
    private Date ct;

    /**
     * 创建时间
     */
    private Date mt;

    @ApiModelProperty(value = "用户角色")
    private List<Long> roleIds;
    @ApiModelProperty(value = "厂商名称")
    private String orgName;

    private static final long serialVersionUID = 1L;
}