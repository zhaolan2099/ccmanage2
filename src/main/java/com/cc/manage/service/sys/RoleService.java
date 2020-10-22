package com.cc.manage.service.sys;

import com.cc.manage.domain.sys.Role;
import com.cc.manage.exception.BizException;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.sys.RoleQuery;
import java.util.List;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/27 10:56
 * @description：
 */
public interface RoleService {
    void save(Role role) throws BizException;
    void delete(List<Long> ids);
    void setMenu(Long roleId,List<Long> menuIds);
    int selectTotal(RoleQuery query);
    List<Role> selectList(RoleQuery query);
    PageVo selectForPage(RoleQuery query);
    List<Role> selectForUser();
}
