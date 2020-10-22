package com.cc.manage.service.sys;

import com.cc.manage.domain.sys.LoginUser;
import com.cc.manage.domain.sys.User;
import com.cc.manage.exception.BizException;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.sys.UserQuery;
import java.util.List;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/26 17:35
 * @description：
 */
public interface UserService {

    void save(User user) throws BizException;

    /**
     * 逻辑删除
     * @param ids
     */
    void delete(List<Long> ids);
    int selectTotal(UserQuery query) throws BizException;
    List<User> selectList(UserQuery query) throws BizException;
    PageVo selectForPage(UserQuery query) throws BizException;
    void setRole(Long userId,List<Long> roleIds);
    User getUser(Long id);
    LoginUser getLoginUser(String loginName, String pwd);
    void updatePwd(User user);
}
