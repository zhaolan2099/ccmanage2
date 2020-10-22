package com.cc.manage.domain.sys;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * org
 * @author 
 */
@Data
public class Org implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称",required = true)
    private String name;

    /**
     * 厂商编号
     */
    @ApiModelProperty(value = "厂商编号",required = true)
    private String num;

    /**
     * 厂商地址
     */
    @ApiModelProperty(value = "厂商地址")
    private String address;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String contacts;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /**
     * 外网IP地址，用于与通信端通信
     */
    @ApiModelProperty(value = "外网IP地址，用于与通信端通信",required = true)
    private String ipAddress;

    private String temp1;
    private String temp2;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date ct;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date mt;

    private static final long serialVersionUID = 1L;
}