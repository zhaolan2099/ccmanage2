package com.cc.manage.dao.sys;
import com.cc.manage.domain.sys.DictType;
import com.cc.manage.query.BaseQuery;

import java.util.List;

public interface DictTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DictType record);

    int insertSelective(DictType record);

    DictType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DictType record);

    int updateByPrimaryKey(DictType record);

    int selectTotal(BaseQuery query);

    List<DictType> selectList(BaseQuery query);

    List<DictType> check(DictType dictType);
}