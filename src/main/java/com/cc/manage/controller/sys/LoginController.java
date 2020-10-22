package com.cc.manage.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.cc.manage.common.CodeMsg;
import com.cc.manage.common.Result;
import com.cc.manage.domain.sys.LoginUser;
import com.cc.manage.service.sys.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * @author ：
 * @time ：Created in 2020/9/3 11:26
 */
@Api(tags = "登录")
@Slf4j
@RequestMapping("sys")
public class LoginController {
    @Autowired
    UserService userService;
    /**
     * 登录测试
     * @param loginName
     * @param password
     * @return
     */
    @GetMapping(value = "/login")
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
            LoginUser loginUser = userService.getLoginUser(loginName,password);
            if(loginUser.getMenuList() != null && loginUser.getMenuList().size() > 0){
                jsonObject.put("token", subject.getSession().getId());
                jsonObject.put("msg", "登录成功");
                jsonObject.put("code", "1");
                jsonObject.put("user", loginUser);
            }else{
                jsonObject.put("msg", "该用户没有分配任何角色，请先分配角色再登录系统！");
                jsonObject.put("code", "0");
            }
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
    /**
     * 用户退出系统操作，需要shiro清除session
     *
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public Result logout(RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Result result = new Result();
        HttpSession reqSession = request.getSession(true);
        try {
            Subject currentUser = SecurityUtils.getSubject();
            // shiro中停掉用户会话session
            currentUser.logout();
            Session session = currentUser.getSession(false);
            if (session == null) {
                return result.success();
            }
            session.stop();

            Enumeration<String> em = request.getSession().getAttributeNames();
            while (em.hasMoreElements()) {
                request.getSession().removeAttribute(em.nextElement().toString());
            }
            reqSession.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
            CodeMsg codeMsg = CodeMsg.SERVER_ERROR;
            log.debug("退出系统出现异常！");
            return result.fail(codeMsg);
        }
        return result.success();
    }
    @RequestMapping(value = "sessionExpired", method = RequestMethod.GET)
    public Result sessionExpired(RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Result result = new Result();
        CodeMsg codeMsg = CodeMsg.SESSION_EXPIRED;
        return result.fail(codeMsg);
    }
}
