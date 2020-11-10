package com.cc.manage.domain.board;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：
 * @time ：Created in 2020/11/10 15:54
 */
@Data
public class BoardOuting {
    @ApiModelProperty(value = "包装箱条码")
    private String putinNum;
    @ApiModelProperty(value = "厂商名称")
    private String orgName;
    @ApiModelProperty(value = "电路板类型")
    private String typeName;
    @ApiModelProperty(value = "电路板个数")
    private Integer boardCount;

}
