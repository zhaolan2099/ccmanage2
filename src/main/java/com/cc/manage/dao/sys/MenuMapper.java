package com.cc.manage.dao.sys;

import com.cc.manage.domain.sys.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Long id);

    int delete(Long id);
    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> selectList(@Param(value = "id") Long id);
    List<Menu> selectListUsable(@Param(value = "id") Long id);
    List<Menu> selectListForUser(@Param(value = "id")Long id,@Param(value = "userId") Long userId);
    List<Menu> selectByUserId(Long userId);
    List<Menu> selectByLevel(Integer level);
}