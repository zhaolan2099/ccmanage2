package com.cc.manage.security;

import com.cc.manage.domain.sys.LoginUser;
import com.cc.manage.exception.RedisServerException;
import com.cc.manage.service.sys.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;


/**
 * @author ：ZL
 * @time ：Created in 2020/4/14 11:26
 * @description：
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    UserService userService;
    /**
     * 授权操作，当登录认证通过之后，接受shiro授权操作
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
//        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Session session = SecurityUtils.getSubject().getSession();
//        LoginUserService user = (LoginUserService) session.getAttribute("USER_SESSION");
//        // 用户的角色集合
//        Set<String> roles = new HashSet<>();
//        roles.add(String.valueOf(user.getRoleId()));
//        authorizationInfo.setRoles(roles);
//        List<com.jc.ticketing.domain.dto.sys.Resource> list = user.getRole().getResourceList();
//        list.forEach(resource -> {
//            authorizationInfo.addStringPermission(resource.getPermissions());
//        });
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //获取用户的输入的账号.平台用电话登录
        String loginName = (String) token.getPrincipal();
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        LoginUser user = userService.getLoginUser(loginName,null);
        if (user == null) {
            // 没找到帐号
            throw new UnknownAccountException();
        }
         //帐号不正常,被锁定，被拉黑等
        if (1 != user.getStatus()) {
            throw new LockedAccountException();
        }
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getLoginName()+user.getPwd());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                //登录名
                user.getLoginName(),
                //密码
                user.getPwd(),
                credentialsSalt,
                //realm name
                getName()
        );
        Session session = null;
        try {
            session = SecurityUtils.getSubject().getSession();
        }catch (Exception e){
            e.printStackTrace();
            log.error("无法连接redis");
            throw  new RedisServerException();
        }
        session.setAttribute("USER_SESSION", user);
        return authenticationInfo;
    }
}
