package com.cc.manage.controller.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cc.manage.common.CodeMsg;
import com.cc.manage.common.Result;
import com.cc.manage.domain.sys.LoginUser;
import com.cc.manage.domain.sys.User;
import com.cc.manage.exception.BizException;
import com.cc.manage.exception.RedisServerException;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.sys.UserQuery;
import com.cc.manage.security.PasswordHelper;
import com.cc.manage.service.sys.UserService;
import com.cc.manage.utils.HttpUtils;
import com.cc.manage.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/27 13:53
 * @description：
 */
@Api(tags = "用户管理")
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping(value = "getUserById")
    @ApiOperation("通过用户ID获取用户对象")
    public Result<User> getUserById(Long id){
        if(id == null){
            return new Result().success(new User());
        }
        log.info("获取用户，请求参数:{}",id);
        Result<User> result = new Result();
        try {
            User user = userService.getUser(id);
            result = result.success(user);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取用户，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("获取用户，响应参数:{}",result.toString());
        return result;
    }

    @GetMapping(value = "selectList")
    @ApiOperation("用户列表，带分页")
    public Result<PageVo<User>> selectList(UserQuery query){
        log.info("用户列表，请求参数:{}",query.toString());
        query.setPageHiden(false);
        Result<PageVo<User>> result = new Result();
        try {
            PageVo<User> pageVo = userService.selectForPage(query);
            result = result.success(pageVo);
        }catch (Exception e){
            e.printStackTrace();
            log.error("用户列表，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("用户列表，响应参数:{}",result.toString());
        return result;
    }
    @GetMapping(value = "save")
    @ApiOperation("保存用户,新增/修改")
    public Result save(User user){
        log.error("保存用户，请求参数:{}", user.toString());
        Result result = new Result();
        try {
            userService.save(user);
            result = result.success();
        }catch (BizException e){
            log.error("保存用户，业务异常:{}", e.getCodeMsg(), e);
            result = result.fail(e.getCodeMsg());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存用户，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        return result;
    }

//    @PostMapping(value = "saveTest")
//    @ApiOperation("保存用户,新增/修改")
//    public Result saveTest(@RequestBody String str){
//        Result result = new Result();
//        try {
//
//            str = str.replace(" "," ");
//            userService.save(JSON.toJavaObject(JSON.parseObject(str),User.class));
//            result = result.success();
//        }catch (BizException e){
//            log.error("保存用户，业务异常:{}", e.getCodeMsg(), e);
//            result = result.fail(e.getCodeMsg());
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("保存用户，系统异常:{}", e.getMessage(), e);
//            result = result.fail(CodeMsg.SERVER_ERROR);
//        }
//        return result;
//    }

    @GetMapping(value = "delete")
    @ApiOperation("删除用户")
    public Result delete(@RequestParam(value = "ids") List<Long> ids){
        log.info("删除用户，请求参数:{}",ids.toString());
        Result result = new Result();
        try {
            userService.delete(ids);
            result = result.success();
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除用户，系统异常:{}", e.getMessage(), e);
            result = result.fail(CodeMsg.SERVER_ERROR);
        }
        log.info("删除用户，响应参数:{}",result.toString());
        return result;
    }
    @GetMapping(value = "getIp")
    public String getIp(HttpServletRequest request){
        try {
            return HttpUtils.getIp(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/login")
    @ApiOperation("用户登录")
    public String login( String loginName, String password) {
        JSONObject jsonObject = new JSONObject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
        Subject subject = SecurityUtils.getSubject();
        if(StringUtils.isEmpty(loginName)){
            jsonObject.put("code", "0");
            jsonObject.put("msg", "重新登录");
            return jsonObject.toString();
        }

        try {
            subject.login(token);
            LoginUser loginUser = (LoginUser)subject.getSession().getAttribute("USER_SESSION");
            if(loginUser.getMenuIds() != null && loginUser.getMenuIds().size() > 0){
                jsonObject.put("token", subject.getSession().getId());
                jsonObject.put("msg", "登录成功");
                jsonObject.put("code", "1");
                jsonObject.put("user", loginUser);
            }else{
                jsonObject.put("msg", "该用户没有分配任何角色，请先分配角色再登录系统！");
                jsonObject.put("code", "0");
            }
        }catch (RedisServerException e) {
            jsonObject.put("msg", "无法连接redis..");
            jsonObject.put("code", "0");
        } catch (IncorrectCredentialsException e) {
            jsonObject.put("msg", "密码错误");
            jsonObject.put("code", "0");
        } catch (LockedAccountException e) {
            jsonObject.put("msg", "登录失败，该用户已被冻结");
            jsonObject.put("code", "0");
        } catch (ExcessiveAttemptsException e){
            jsonObject.put("code", "0");
            jsonObject.put("msg", "该账号多次登录未成功，请5分钟后再试");
        } catch (AuthenticationException e) {
            if(StringUtils.isEmpty(loginName)){
                jsonObject.put("code", "0");
                jsonObject.put("msg", "重新登录");
            }else{
                jsonObject.put("code", "0");
                jsonObject.put("msg", "该用户不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @ApiOperation(value = "修改用户密码")
    @GetMapping("editPassword")
    public  Result editPassword(@RequestParam(value = "oldPwd") String oldPwd,
                                @RequestParam(value = "newPwd") String newPwd)  {
        Result result = new Result();
        LoginUser currentUser;
        try {
            currentUser = UserUtil.getCurrentUser();
            User old = userService.getUser(currentUser.getId());
            oldPwd = new PasswordHelper().encryptPassword(currentUser.getLoginName(),oldPwd);
            if(oldPwd.trim().equals(old.getPwd())) {
                User user = new User();
                user.setId(currentUser.getId());
                user.setPwd(new PasswordHelper().encryptPassword(currentUser.getLoginName(),newPwd));
                userService.updatePwd(user);
                result =  result.success();

            }else{
                result =  result.fail(new CodeMsg(0,"修改密码失败：原密码错误！"));
            }
        } catch (BizException e) {
            log.error("保存用户，业务异常:{}", e.getCodeMsg(), e);
            result = result.fail(e.getCodeMsg());
        }
        return result;
    }

    @PostMapping(value = "/clientLogin")
    @ApiOperation("客户端登录")
    public String clientLogin(@RequestBody LoginUser user) {
        JSONObject jsonObject = new JSONObject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), user.getPwd());
        Subject subject = SecurityUtils.getSubject();
        if(StringUtils.isEmpty(user.getLoginName())){
            jsonObject.put("code", 0);
            jsonObject.put("msg", "log in again ");
            return jsonObject.toString();
        }

        try {
            subject.login(token);
            LoginUser loginUser = (LoginUser)subject.getSession().getAttribute("USER_SESSION");
            if(loginUser.getMenuIds() != null && loginUser.getMenuIds().size() > 0){
                jsonObject.put("token", subject.getSession().getId());
                jsonObject.put("msg", "登录成功。");
                jsonObject.put("code",1);
            }else{
                jsonObject.put("msg", "no roles");
                jsonObject.put("code", 0);
            }
        }catch (RedisServerException e) {
            jsonObject.put("msg", "cont connect redis..");
            jsonObject.put("code", "0");
        } catch (IncorrectCredentialsException e) {
            jsonObject.put("msg", "pws error");
            jsonObject.put("code", 0);
        } catch (LockedAccountException e) {
            jsonObject.put("msg", "users are frozen");
            jsonObject.put("code", 0);
        } catch (ExcessiveAttemptsException e){
            jsonObject.put("code",0);
            jsonObject.put("msg", "This account has not been logged in successfully for many times. Please try again after 5 minutes");
        } catch (AuthenticationException e) {
            if(StringUtils.isEmpty(user.getLoginName())){
                jsonObject.put("code", 0);
                jsonObject.put("msg", "log in again ");
            }else{
                jsonObject.put("code", 0);
                jsonObject.put("msg", "The user does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
