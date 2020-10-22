package com.cc.manage.query.sys;

import com.cc.manage.query.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/26 15:41
 * @description：
 */
@Data
public class OrgQuery extends BaseQuery {
    @ApiModelProperty(value = "联系人姓名")
    private String contacts;
    @ApiModelProperty(value = "id")
    private Long id;
    private Long orgId;
}
