package com.cc.manage.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：ZL
 * @time ：Created in 2020/4/14 11:04
 * @description：
 */
public class MyCredentialsMatcher extends SimpleCredentialsMatcher {

	private Cache<String, AtomicInteger> passwordRetryCache;

    private PasswordHelper passwordHelper = new PasswordHelper();

    public MyCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    /**
     * 密码比对方法，根据加密规则进行密文校验
     * return 比对结果
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken login_token = (UsernamePasswordToken) token;

        String username = (String) token.getPrincipal();
        //retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        if (retryCount.incrementAndGet() > 5) {
            //if retry count > 5 throw
            throw new ExcessiveAttemptsException();
        }
        passwordRetryCache.put(username, retryCount);
        String userName = login_token.getUsername();
        String pwd = String.valueOf(login_token.getPassword());
        String user_input_login_pass = passwordHelper.encryptPassword(userName, pwd);
        Object db_login_password = getCredentials(info);
        //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
        boolean matches = super.equals(user_input_login_pass, db_login_password);

        if (matches) {
            //clear retry count
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}
