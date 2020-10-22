package com.cc.manage.dao.sys;

import com.cc.manage.domain.sys.Org;
import com.cc.manage.query.sys.OrgQuery;

import java.util.List;

public interface OrgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Org record);

    int insertSelective(Org record);

    Org selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Org record);

    int updateByPrimaryKey(Org record);

    int selectTotal(OrgQuery query);
    List<Org> selectList(OrgQuery query);

    Long selectMaxId();

    List<Org> checkOrg(Org org);
}