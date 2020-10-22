package com.cc.manage.domain.sys;

import lombok.Data;

import java.io.Serializable;

/**
 * dict_type
 * @author 
 */
@Data
public class DictType implements Serializable {
    private Long id;

    /**
     * 分类
     */
    private String typeName;

    /**
     * 分类编号
     */
    private String type;

    private static final long serialVersionUID = 1L;
}