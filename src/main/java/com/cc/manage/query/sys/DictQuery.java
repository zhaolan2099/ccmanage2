package com.cc.manage.query.sys;

import com.cc.manage.query.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：
 * @time ：Created in 2020/9/7 9:42
 */
@Data
public class DictQuery extends BaseQuery {
    @ApiModelProperty(value = "描述")
    private String tag;
    @ApiModelProperty(value = "码表分类")
    private Long typeId;
}
