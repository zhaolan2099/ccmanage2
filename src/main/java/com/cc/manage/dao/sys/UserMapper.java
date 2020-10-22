package com.cc.manage.dao.sys;

import com.cc.manage.domain.sys.LoginUser;
import com.cc.manage.domain.sys.Role;
import com.cc.manage.domain.sys.User;
import com.cc.manage.domain.sys.UserRole;
import com.cc.manage.query.sys.UserQuery;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int selectTotal(UserQuery query);
    List<User> selectList(UserQuery query);

    void setUserRole(List<UserRole> list);
    void deleteUserRoleByUserId(Long userId);
    int checkLoginName(User user);
    List<Role> getRoleByUserId(Long userId);
    User getUser(User user);
    LoginUser getLoginUser(User user);
}