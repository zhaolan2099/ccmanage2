package com.cc.manage.utils;

import com.cc.manage.domain.sys.LoginUser;
import com.cc.manage.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * @author ：ZL
 * @time ：Created in 2020/4/15 11:24
 * @description：
 */
@Slf4j
public class UserUtil {

    public static LoginUser getCurrentUser() throws BizException {
        Session session = SecurityUtils.getSubject().getSession();
        Object obj  = session.getAttribute("USER_SESSION");
        LoginUser user = (LoginUser) obj;
        if(user == null){
            log.error("登录失效！请重新登陆。");
            throw new BizException(0,"登录失效！请重新登陆。");
        }
        return user;
    }
}
