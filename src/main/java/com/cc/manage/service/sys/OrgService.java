package com.cc.manage.service.sys;

import com.cc.manage.domain.sys.Org;
import com.cc.manage.exception.BizException;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.sys.OrgQuery;
import java.util.List;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/26 15:36
 * @description：
 */
public interface OrgService {

    void save(Org org) throws BizException;

    /**
     * 逻辑删除
     * @param ids
     */
    void delete(List<Long> ids);
    int selectTotal(OrgQuery query) throws BizException;
    List<Org> selectList(OrgQuery query) throws BizException;
    PageVo selectForPage(OrgQuery query) throws BizException;
}
