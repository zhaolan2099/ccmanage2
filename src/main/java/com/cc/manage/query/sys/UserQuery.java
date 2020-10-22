package com.cc.manage.query.sys;

import com.cc.manage.query.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/26 17:38
 * @description：
 */
@Data
public class UserQuery  extends BaseQuery {
    @ApiModelProperty(value = "用户姓名")
    private String name;
    @ApiModelProperty(value = "组织ID")
    private Long orgId;
}
