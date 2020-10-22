package com.cc.manage.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;


@Data
public class BaseQuery implements Serializable {
    private static final long serialVersionUID = 896533036409560075L;

    /** 分页：false,不分页:true */
    @ApiModelProperty(hidden = true)
    public boolean pageHiden = false;

    /** 当前页码 */
    @ApiModelProperty(value = "当前页码",required = true)
    protected int pageIndex = 1;

    /** 分页条数 */
    @ApiModelProperty(value = "分页条数",required = true)
    protected int pageSize = 10;
    @ApiModelProperty(hidden = true)
    protected int startIndex;

    public int getStartIndex() {
        this.startIndex = (pageIndex - 1) * pageSize;
        return startIndex;
    }

}
