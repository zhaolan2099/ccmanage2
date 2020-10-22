package com.cc.manage.domain.sys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * menu
 * @author 
 */
@Data
public class Menu implements Serializable {
    private Long id;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称",required = true)
    private String name;

    /**
     * 父id
     */
    @ApiModelProperty(value = "父id",required = true)
    private Long pid;

    /**
     * 所有父级id
     */
    @ApiModelProperty(value = "所有父级id",required = true)
    private String pids;

    /**
     * url地址
     */
    @ApiModelProperty(value = "url地址")
    private String url;

    /**
     * 权限
     */
    @ApiModelProperty(value = "权限")
    private String permission;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String img;

    /**
     * 顺序
     */
    @ApiModelProperty(value = "顺序",required = true)
    private Integer seq;

    /**
     * 0禁用1启用
     */
    @ApiModelProperty(value = "0禁用1启用",required = true)
    private Integer status;

    /**
     * 菜单级别
     */
    @ApiModelProperty(value = "菜单级别",required = true)
    private Integer level;

    /**
     * 备用字段
     */
    @ApiModelProperty(hidden = true)
    private String temp1;

    /**
     * 备用字段
     */
    @ApiModelProperty(hidden = true)
    private String temp2;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date ct;

    /**
     * 修改时间
     */
    @ApiModelProperty(hidden = true)
    private Date mt;

    @ApiModelProperty(hidden = true)
    List<Menu> childList;
//    List<Menu> childListShow;
//    List<Menu> childListForUser;

    private static final long serialVersionUID = 1L;
}