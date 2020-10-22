package com.cc.manage.domain.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * user
 * @author 
 */
@Data
public class LoginUser implements Serializable {
    private Long id;

    /**
     * 登陆名
     */
    @ApiModelProperty(value = "登陆名，不允许修改")
    private String loginName;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "密码")
    private String pwd;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;

    /**
     * 所属组织
     */
    @ApiModelProperty(value = "公司ID")
    private Long orgId;

    private String orgNum;

    /**
     * 0禁用1正常
     */
    @ApiModelProperty(value = "0禁用1正常")
    private Integer status;

    /**
     * 最近一次登陆时间
     */
    private Date lastLoginTime;

    private String temp2;

    private String temp1;

    /**
     * 修改时间
     */
    private Date ct;

    /**
     * 创建时间
     */
    private Date mt;

    /**
     * 是否超管0否1是
     */
    private Integer isAdmin;


    //辅助字段
    private String orgName;

    @ApiModelProperty(hidden = true)
    private List<Long> roleIds;
    @ApiModelProperty(hidden = true)
    private List<Menu> menuList;
    @ApiModelProperty(hidden = true)
    private String ipAddress;
    @ApiModelProperty(hidden = true)
    private  List<Long> menuIds;

    @ApiModelProperty(hidden = true)
    private List<MenuView> menuViewList;

    private static final long serialVersionUID = 1L;
}