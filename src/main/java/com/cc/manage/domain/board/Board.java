package com.cc.manage.domain.board;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * board
 * @author 
 */
@Data
public class Board implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * sn序号
     */
    @Excel(name = "SN条码",orderNum = "1",width = 20)
    @ApiModelProperty(value = "sn序号")
    private String sn;

    /**
     * mac地址
     */
    @Excel(name = "mac",orderNum = "5",width = 20)
    @ApiModelProperty(value = "mac地址")
    private String mac;

    /**
     * mac地址
     */
    @Excel(name = "mcuId",orderNum = "5",width = 20)
    @ApiModelProperty(value = "机器ID")
    private String mcuId;

    /**
     * 测试版类型
     */
    @ApiModelProperty(value = "测试版类型")
    private String boardType;

    /**
     * 厂商ID
     */
    @ApiModelProperty(value = "厂商ID")
    private Long orgId;

    /**
     * 测试状态1测试中2已入库3出库中4已出库
     */
    @ApiModelProperty(value = "测试状态1测试中2测试通过3入库中4已入库5已出库")
    @Excel(name = "电路板状态",orderNum = "4",replace = {"测试中_1","测试通过_2","入库中_3","已入库_4","已出库_5"},width = 20)
    private String testStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(hidden = true)
    private Long createUser;

    /**
     * 入库时间(入库时间不一定就是创建时间)
     */
    @ApiModelProperty(value = "入库时间")
    @Excel(name = "入库时间",orderNum = "6",format = "yyyy-MM-dd HH:mm:ss",width = 20)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date putinTime;

    /**
     * 入库人(入库人不一定就是创建人)
     */
    @ApiModelProperty(value = "入库人id")
    private Long putinUser;

    /**
     * 入库时间(入库时间不一定就是创建时间)
     */
    @ApiModelProperty(value = "测试时间")
    @Excel(name = "测试时间",orderNum = "6",format = "yyyy-MM-dd HH:mm:ss",width = 20)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date testTime;

    /**
     * 测试人员(测试人员不一定就是创建人)
     */
    @ApiModelProperty(value = "测试人员")
    private Long testUser;

    /**
     * 出库时间
     */
    @ApiModelProperty(value = "出库时间")
    @Excel(name = "出库时间",orderNum = "8",format = "yyyy-MM-dd HH:mm:ss",width = 20)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date outTime;

    @ApiModelProperty(value = "入库编号")
    private String putin_num;
    /**
     * 出库人
     */
    @ApiModelProperty(value = "出库人id")
    private Long outUser;
    /**
     * 出库记录单号
     */
    @ApiModelProperty(value = "出库记录单号")
    private String outNum;

    //view辅助字段
    @Excel(name = "厂商名称",orderNum = "2",width = 20)
    @ApiModelProperty(value = "厂商名称")
    private String orgName;
    @Excel(name = "出库人",orderNum = "7",width = 20)
    @ApiModelProperty(value = "出库人姓名")
    private String outUserName;
    @ApiModelProperty(value = "测试人员姓名")
    @Excel(name = "测试人",orderNum = "6",width = 20)
    private String testUserName;
    @ApiModelProperty(value = "入库人姓名")
    @Excel(name = "入库人",orderNum = "6",width = 20)
    private String putinUserName;
    @ApiModelProperty(value = "电路板类型")
    @Excel(name = "电路板类型",orderNum = "3",width = 20)
    private String boardTypeName;



    private static final long serialVersionUID = 1L;
}