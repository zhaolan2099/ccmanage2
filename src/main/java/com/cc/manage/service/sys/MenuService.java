package com.cc.manage.service.sys;

import com.cc.manage.domain.sys.Menu;
import java.util.List;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/26 11:45
 * @description：
 */
public interface MenuService {
    void save(Menu menu);

    void delete(Long id);

    /**
     * 全部，用于菜单资源管理
     * @param id
     * @return
     */
    List<Menu> selectList(Long id);
    /**
     * 查询可用的菜单 ，用于给角色设置权限
     * @param id
     * @return
     */
    List<Menu> selectListUsable(Long id);

    /**
     * 根据用户获取菜单树
     * @param userId
     * @return
     */
    List<Menu> selectListForUser(Long id,Long userId);
}
