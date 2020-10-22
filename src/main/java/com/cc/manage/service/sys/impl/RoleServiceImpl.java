package com.cc.manage.service.sys.impl;

import com.cc.manage.dao.sys.RoleMapper;
import com.cc.manage.domain.sys.Role;
import com.cc.manage.domain.sys.RoleMenu;
import com.cc.manage.exception.BizException;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.sys.RoleQuery;
import com.cc.manage.service.sys.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/27 10:56
 * @description：
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleMapper roleMapper;
    @Override
    public void save(Role role)  throws BizException {
        if(roleMapper.checkName(role) > 0){
            throw new BizException(0,"角色名称已存在。");
        }
        if(role.getId() == null){
            role.setCt(new Date());
            roleMapper.insert(role);
        }else {
            role.setMt(new Date());
            roleMapper.updateByPrimaryKeySelective(role);
        }
    }

    @Override
    public void delete(List<Long> ids) {
        ids.forEach(o->{
            roleMapper.deleteByPrimaryKey(o);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setMenu(Long roleId, List<Long> menuIds) {
        roleMapper.deleteByRoleId(roleId);
        List<RoleMenu> list = new ArrayList<>();
        menuIds.forEach(o->{
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(o);
            list.add(roleMenu);
        });
        roleMapper.setMenu(list);
    }

    @Override
    public int selectTotal(RoleQuery query) {
        return roleMapper.selectTotal(query);
    }

    @Override
    public List<Role> selectList(RoleQuery query) {
        return roleMapper.selectList(query);
    }

    @Override
    public PageVo selectForPage(RoleQuery query) {
        PageVo pageVo = new PageVo();
        pageVo.setTotal(this.selectTotal(query));
        pageVo.setObject(this.selectList(query));
        return pageVo;
    }

    @Override
    public List<Role> selectForUser() {
        return roleMapper.selectForUser();
    }
}
