package com.cc.manage.security;

import com.alibaba.fastjson.JSON;
import com.cc.manage.common.CodeMsg;
import com.cc.manage.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author ：ZL
 * @time ：Created in 2020/4/24 17:53
 * @description：
 */
@Slf4j
public class CORSAuthenticationFilter extends FormAuthenticationFilter {

    public CORSAuthenticationFilter() {
        super();
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //Always return true if the request's method is OPTIONSif (request instanceof HttpServletRequest) {
        if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }


    /**
     * 拦截未登录的请求
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res = (HttpServletResponse)response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        res.setCharacterEncoding("UTF-8");
        PrintWriter writer = res.getWriter();
        CodeMsg codeMsg = CodeMsg.SESSION_EXPIRED;
        writer.write(JSON.toJSONString(new Result<>().fail(codeMsg)));
        writer.close();
        return false;
    }
}
