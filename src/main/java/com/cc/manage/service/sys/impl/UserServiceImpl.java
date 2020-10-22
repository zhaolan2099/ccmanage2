package com.cc.manage.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.cc.manage.dao.sys.MenuMapper;
import com.cc.manage.dao.sys.RoleMapper;
import com.cc.manage.dao.sys.UserMapper;
import com.cc.manage.domain.sys.*;
import com.cc.manage.exception.BizException;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.sys.UserQuery;
import com.cc.manage.security.PasswordHelper;
import com.cc.manage.service.sys.UserService;
import com.cc.manage.utils.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/26 17:36
 * @description：
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;
    @Resource
    MenuMapper menuMapper;
    @Resource
    RoleMapper roleMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(User user)  throws BizException {
        if(userMapper.checkLoginName(user) > 0){
            throw new BizException(0,"登陆名已存在。");
        }
        if(user.getRoleIds() == null || user.getRoleIds().size() < 1){
            throw new BizException(0,"请选择用户角色。");
        }
        //新增
        if(user.getId() == null){
            user.setCt(new Date());
            PasswordHelper ph = new PasswordHelper();
            user.setStatus(1);
            user.setPwd(ph.encryptPassword(user.getLoginName(),"123456"));
            userMapper.insert(user);
        }else{
            user.setMt(new Date());
            //修改
            userMapper.updateByPrimaryKeySelective(user);
            userMapper.deleteUserRoleByUserId(user.getId());
        }
        List<UserRole> list = new ArrayList<>();
        user.getRoleIds().forEach(o->{
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(o);
            list.add(userRole);
        });
        userMapper.setUserRole(list);
    }

    @Override
    public void delete(List<Long> ids) {
        ids.forEach(o->{
            User old = userMapper.selectByPrimaryKey(o);
            User user = new User();
            user.setLoginName(old.getLoginName()+"(已删除)");
            user.setId(o);
            user.setIsDelete(0);
            user.setMt(new Date());
            userMapper.updateByPrimaryKeySelective(user);
        });
    }

    @Override
    public int selectTotal(UserQuery query) throws BizException {
        //企业帐号，只有查看企业下面所有人员
        LoginUser user = UserUtil.getCurrentUser();
        if(user.getIsAdmin().equals(0)){
            query.setOrgId(user.getOrgId());
        }
        return userMapper.selectTotal(query);
    }

    @Override
    public List<User> selectList(UserQuery query) throws BizException {
        //企业帐号，只有查看企业下面所有人员
        LoginUser user = UserUtil.getCurrentUser();
        if(user.getIsAdmin().equals(0)){
            query.setOrgId(user.getOrgId());
        }
        return userMapper.selectList(query);
    }

    @Override
    public PageVo selectForPage(UserQuery query) throws BizException {
        PageVo pageVo = new PageVo();
        pageVo.setTotal(this.selectTotal(query));
        pageVo.setObject(this.selectList(query));
        return pageVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setRole(Long userId, List<Long> roleIds) {
        userMapper.deleteUserRoleByUserId(userId);
        List<UserRole> list = new ArrayList<>();
        roleIds.forEach(o->{
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(o);
            list.add(userRole);
        });
        userMapper.setUserRole(list);
    }

    @Override
    public User getUser(Long id) {
        User user = new User();
        user.setId(id);
        User fromDb = userMapper.getUser(user);
        List<Long> roleIds = roleMapper.selectRoleIdByUser(id);
        fromDb.setRoleIds(roleIds);
        return fromDb;
    }

    @Override
    public LoginUser getLoginUser(String loginName, String pwd){
        User user = new User();
        user.setLoginName(loginName);
        user.setPwd(pwd);
        LoginUser loginUser = userMapper.getLoginUser(user);
        if(loginUser == null){
            return null;
        }
//        List<Menu> menuList  = menuMapper.selectListForUser(-1L,loginUser.getId());
//        loginUser.setMenuList(menuList);
        List<Menu> menuList;

        if(loginUser.getIsAdmin() == 0){
            menuList = menuMapper.selectByUserId(loginUser.getId());
            //前端存放的时候，只有全选才会给父ID，所以需要特殊处理
            loginUser.setMenuList(getTreeForUser(menuList));
        }else {
            //admin有全部权限
            menuList = menuMapper.selectList(-1L);
            loginUser.setMenuList(menuList);
        }
        loginUser.setMenuIds(loginUser.getMenuList().stream().map(Menu::getId).collect(Collectors.toList()));
        loginUser.setMenuViewList(this.buildMenuView(loginUser.getMenuList()));
        return loginUser;
    }

    @Override
    public void updatePwd(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    private List<Menu> getTreeForUser(List<Menu> menuList){
        List<Menu> parts =  new ArrayList<>();
        List<Menu> res = new ArrayList<>();
        menuList.forEach(o->{
            if(o.getLevel()==1){
                parts.add(o);
            }
        });
        menuList.removeAll(parts);
        parts.forEach(o->{
            List<Menu> childs = new ArrayList<>();
            menuList.forEach(p->{
                if(p.getPid().equals(o.getId())){
                    childs.add(p);
                }
            });
            menuList.removeAll(childs);
            o.setChildList(childs);
            res.add(o);
        });
        List<Long> temp = new ArrayList<>();
        List<Menu> noPartsList = new ArrayList<>();
        List<Menu> res2 = new ArrayList<>();
        menuList.forEach(o->{
            if(!temp.contains(o.getId())){
                temp.add(o.getPid());
                noPartsList.add(menuMapper.selectByPrimaryKey(o.getPid()));
            }
        });
        noPartsList.forEach(o->{
            List<Menu> childs = new ArrayList<>();
            menuList.forEach(p->{
                if(o.getId().equals(p.getPid())){
                    childs.add(p);
                }
            });
            menuList.removeAll(childs);
            o.setChildList(childs);
            res2.add(o);
        });
        if(res2 != null  && res2.size()>0){
            res.addAll(res2);
        }
        return res;
    }


    /**
     * 需要封装成前端需要的样式
     * @param menuList
     * @return
     */
    private List<MenuView> buildMenuView(List<Menu> menuList){
        List<MenuView> viewList = new ArrayList<>();
        menuList.forEach(o->{
            MenuView view = new MenuView();
            List<MenuView> childList = new ArrayList<>();
            view.setPath("/"+o.getUrl());
            view.setComponent("Layout");
            String str = "{\"title\":\""+o.getName()+"\",\"icon\":\"table\"}";
            view.setMeta(JSON.parseObject(str));
            view.setName(o.getUrl());
            if(o.getChildList() == null || o.getChildList().size() == 0){
                MenuView childView = new MenuView();
                childView.setPath("index");
                childView.setComponent(o.getUrl()+"/"+childView.getPath());
                childView.setName(childView.getPath());
                String meta = "{\"title\":\""+o.getName()+"\",\"icon\":\"table\"}";
                childView.setMeta(JSON.parseObject(meta));
                childList.add(childView);
            }else{
                o.getChildList().forEach(p->{
                    MenuView childView = new MenuView();
                    childView.setPath(p.getUrl());
                    childView.setComponent(o.getUrl()+"/"+p.getUrl());
                    String str1 = "{\"title\":\""+p.getName()+"\",\"icon\":\"table\"}";
                    childView.setMeta(JSON.parseObject(str1));
                    childView.setName(p.getUrl());
                    childList.add(childView);
                });
            }
            view.setChildren(childList);
            viewList.add(view);
        });
        return viewList;
    }
}
