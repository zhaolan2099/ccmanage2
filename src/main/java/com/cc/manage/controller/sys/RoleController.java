package com.cc.manage.controller.sys;

import com.cc.manage.common.CodeMsg;
import com.cc.manage.common.Result;
import com.cc.manage.domain.sys.Role;
import com.cc.manage.exception.BizException;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.sys.RoleQuery;
import com.cc.manage.service.sys.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/27 13:53
 * @description：
 */
@Api(tags = "角色管理")
@Slf4j
@RestController
@RequestMapping("role")
public class RoleController {

    @Resource
    RoleService roleService;

    @GetMapping(value = "getAll")
    @ApiOperation("获取所有可用角色，用于新增/编辑用户")
    public Result<List<Role>> selectAllRole(){
        log.info("获取所有可用角色");
        Result<List<Role>> result = new Result();
        try {
            List<Role> list  = roleService.selectForUser();
            result = result.success(list);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取所有可用角色，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("获取所有可用角色，响应参数:{}",result.toString());
        return result;
    }

    @GetMapping(value = "selectList")
    @ApiOperation("角色列表")
    public Result<PageVo<Role>> selectList(RoleQuery query){
        log.info("角色列表，请求参数:{}",query.toString());
        query.setPageHiden(false);
        Result<PageVo<Role>> result = new Result();
        try {
            PageVo<Role> pageVo = roleService.selectForPage(query);
            result = result.success(pageVo);
        }catch (Exception e){
            e.printStackTrace();
            log.error("角色列表，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("角色列表，响应参数:{}",result.toString());
        return result;
    }
    @GetMapping(value = "save")
    @ApiOperation("保存角色")
    public Result save(Role role){
        log.info("保存角色，请求参数:{}",role.toString());
        Result result = new Result();
        try {
            roleService.save(role);
            result = result.success();
        }catch (BizException e){
            log.error("保存角色，系统异常:{}", e.getMessage(), e);
            result = result.fail(e.getCodeMsg());
        } catch (Exception e){
            e.printStackTrace();
            log.error("保存角色，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("保存用户，响应参数:{}",result.toString());
        return result;
    }

    @GetMapping(value = "delete")
    @ApiOperation("删除角色")
    public Result delete(@RequestParam(value = "ids") List<Long> ids){
        log.info("删除角色，请求参数:{}",ids.toString());
        Result result = new Result();
        try {
            roleService.delete(ids);
            result = result.success();
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除角色，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("删除角色，响应参数:{}",result.toString());
        return result;
    }

    @GetMapping(value = "setMenu")
    @ApiOperation("设置角色权限")
    public Result setMenu(@RequestParam("roleId") Long roleId,@RequestParam("menuIds") List<Long> menuIds){
        log.info("设置角色权限，请求参数:{},{}",roleId,menuIds.toString());
        Result result = new Result();
        try {
            roleService.setMenu(roleId,menuIds);
            result = result.success();
        }catch (Exception e){
            e.printStackTrace();
            log.error("设置角色权限，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("设置角色权限，响应参数:{}",result.toString());
        return result;
    }
}
