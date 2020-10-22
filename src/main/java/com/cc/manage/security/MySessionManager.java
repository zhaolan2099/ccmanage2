package com.cc.manage.security;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Map;

;

/**
 * @author ：ZL
 * @time ：Created in 2020/4/14 13:57
 * @description：前后端分离，所以需要实现自己的session管理
 */
public class MySessionManager  extends DefaultWebSessionManager {
    private static final String TOKEN = "token";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public MySessionManager() {
        super();
//        //过期时间 30分钟
//        setGlobalSessionTimeout(MILLIS_PER_MINUTE*30);
        //过期时间为24小时
        setGlobalSessionTimeout(MILLIS_PER_HOUR*24);
//        setGlobalSessionTimeout(1000L* 10);
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String parameter = request.getParameter(TOKEN);
        String id = WebUtils.toHttp(request).getHeader(TOKEN);
        if(StringUtils.isEmpty(id)){
            id = parameter;
        }
        //如果请求头中有 token 则其值为sessionId
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            //否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }
    }
}
