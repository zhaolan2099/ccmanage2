package com.cc.manage.dao.sys;

import com.cc.manage.domain.sys.Dict;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.sys.DictQuery;

import java.util.List;

public interface DictMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);

    int selectTotal(DictQuery query);
    List<Dict> selectList(DictQuery query);
    List<Dict> selectByType(String type);
}