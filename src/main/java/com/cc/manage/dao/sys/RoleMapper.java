package com.cc.manage.dao.sys;

import com.cc.manage.domain.sys.Role;
import com.cc.manage.domain.sys.RoleMenu;
import com.cc.manage.query.sys.RoleQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    int checkName(Role role);

    void setMenu(List<RoleMenu> list);

    void deleteByRoleId(Long roleId);

    int selectTotal(RoleQuery query);
    List<Role> selectList(RoleQuery query);

    List<Role> selectForUser();
    List<Long> selectRoleIdByUser(Long userId);
}