package com.cc.manage.query.sys;

import com.cc.manage.query.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/27 11:19
 * @description：
 */
@Data
public class RoleQuery extends BaseQuery {
    @ApiModelProperty(value = "角色名称")
    private String name;
}
