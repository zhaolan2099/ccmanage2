package com.cc.manage.service.sys.impl;

import com.cc.manage.dao.sys.MenuMapper;
import com.cc.manage.domain.sys.Menu;
import com.cc.manage.service.sys.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/26 11:46
 * @description：
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    MenuMapper menuMapper;
    @Override
    public void save(Menu menu) {
        if(menu.getId() != null){
            menu.setMt(new Date());
            menuMapper.updateByPrimaryKeySelective(menu);
        }else{
            menu.setCt(new Date());
            menuMapper.insert(menu);
        }
    }

    @Override
    public void delete(Long id) {
        menuMapper.delete(id);
    }

    @Override
    public List<Menu> selectList(Long id) {
        return menuMapper.selectList(id);
    }

    @Override
    public List<Menu> selectListUsable(Long id) {
        return  menuMapper.selectListUsable(id);
    }

    @Override
    public List<Menu> selectListForUser(Long id,Long userId) {
        return  menuMapper.selectListForUser(id,userId);
    }


}
