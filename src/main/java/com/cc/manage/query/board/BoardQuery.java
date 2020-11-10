package com.cc.manage.query.board;

import com.cc.manage.query.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BoardQuery extends BaseQuery {

    private Long id;
    @ApiModelProperty(value = "厂商Id")
    private Long orgId;
    private String orgName;
    @ApiModelProperty(value = "出库人名称")
    private String outUserName;
    @ApiModelProperty(value = "出库时间起")
    private String outTimeBegin;
    @ApiModelProperty(value = "出库时间止")
    private String outTimeEnd;
    @ApiModelProperty(value = "SN")
    private String sn;
    @ApiModelProperty(value = "入库时间起")
    private String putInTimeBegin;
    @ApiModelProperty(value = "入库时间止")
    private String putInTimeEnd;
    @ApiModelProperty(value = "状态1测试中2已入库3出库中4已出库")
    private String testStatus;
    @ApiModelProperty(value = "测试板类型")
    private String boardType;
    @ApiModelProperty(value = "出库单号")
    private String outNum;
    @ApiModelProperty(value = "入库单号")
    private String putinNum;
    @ApiModelProperty(value = "1检测列表 2,入库列表；3，出库列表；4台帐",hidden = true)
    private Integer type;
}
