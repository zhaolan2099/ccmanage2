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
public class BoardForOutExport implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * sn序号
     */
    @Excel(name = "SN条码",width = 12)
    @ApiModelProperty(value = "sn序号")
    private String sn;

    /**
     * sn序号
     */
    @Excel(name = "MAC",width = 12)
    @ApiModelProperty(value = "MAC")
    private String mac;

    @Excel(name = "厂商名称",width = 12)
    @ApiModelProperty(value = "厂商名称")
    private String orgName;

    @Excel(name = "电路板类型",width = 14)
    @ApiModelProperty(value = "电路板类型")
    private String boardTypeName;
    /**
     * 测试状态1测试中2已入库3出库中4已出库
     */
    @Excel(name = "电路板状态",width = 14,replace = {"测试完成_1","序列号已写入_2","入库中_3","已入库_4","已出库_5"})
    @ApiModelProperty(value = "1测试完成2序列号已写入3入库中4已入库5已出库")
    private String testStatus;

    @Excel(name = "出库人员",width = 12)
    @ApiModelProperty(value = "出库人姓名")
    private String outUserName;

    @Excel(name = "出库时间",width = 12,format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "出库时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date outTime;

    /**
     * 出库记录单号
     */
    @Excel(name = "出库记录单号",width = 15)
    @ApiModelProperty(value = "出库记录单号")
    private String outNum;



    private static final long serialVersionUID = 1L;
}