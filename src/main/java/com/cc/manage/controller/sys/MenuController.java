package com.cc.manage.controller.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cc.manage.common.CodeMsg;
import com.cc.manage.common.Result;
import com.cc.manage.domain.sys.Menu;
import com.cc.manage.service.sys.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/26 14:34
 * @description：
 */
@Api(tags = "菜单管理")
@Slf4j
@RestController
@RequestMapping("menu")
public class MenuController {

    @Resource
    MenuService menuService;

    @GetMapping(value = "getMenuTree")
    @ApiOperation("获取所有菜单树")
    public String getMenuTree(){
       List<Menu> tree =  menuService.selectList(-1L);
        return JSON.toJSONString(tree);
    }
    @GetMapping(value = "save")
    @ApiOperation("保存菜单")
    public Result save(Menu menu){
        log.info("保存菜单，请求参数:{}", menu.toString());
        Result result = new Result();
        try {
            menuService.save(menu);
            log.info("保存菜单，响应参数:{}", result.toString());
            return result.success();
        }catch (Exception e){
            e.printStackTrace();
            log.error("保存菜单，系统异常:{}", e.getMessage(), e);
            return  result.fail(CodeMsg.FAIL);
        }
    }

    @GetMapping(value = "delete")
    @ApiOperation("删除菜单")
    public Result delete(Long id){
        log.info("删除菜单，请求参数:{}", id);
        Result result = new Result();
        try {
            menuService.delete(id);
            log.info("删除菜单，响应参数:{}", result.toString());
            return result.success();
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除菜单，系统异常:{}", e.getMessage(), e);
            return  result.fail(CodeMsg.FAIL);
        }
    }

    @GetMapping(value = "getMenuTreeUsable")
    @ApiOperation("获取可用的菜单树,用于权限分配")
    public String getMenuTreeUsable(){
        JSONObject jsonObject = new JSONObject();
        List<Menu> tree;
        try {
            tree =  menuService.selectListUsable(-1L);
            jsonObject.put("code", "1");
            jsonObject.put("msg", "success");
            jsonObject.put("data", tree);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取可用的菜单树，系统异常:{}", e.getMessage(), e);
            jsonObject.put("code", "0");
            jsonObject.put("msg", "重新登录");
        }
        return JSON.toJSONString(jsonObject);
    }
    @GetMapping(value = "getMenuTreeForUser")
    @ApiOperation("获取用户菜单树,自测用")
    public String getMenuTreeForUser(Long userId){
        List<Menu> tree =  menuService.selectListForUser(-1L,userId);
        return JSON.toJSONString(tree);
    }
}
