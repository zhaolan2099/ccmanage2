package com.cc.manage.service.sys;

import com.cc.manage.domain.sys.DictType;
import com.cc.manage.exception.BizException;
import com.cc.manage.query.BaseQuery;
import com.cc.manage.query.PageVo;

import java.awt.print.Pageable;
import java.util.List;

/**
 * @author ：
 * @time ：Created in 2020/9/23 9:35
 */
public interface DictTypeService {
    /**
     *  新增和编辑
     * @param dictType
     * @throws BizException
     */
    void save(DictType dictType)throws BizException;

    void delete(List<Long> ids);

    int selectTotal(BaseQuery query);
    List<DictType> selectList(BaseQuery query);
    PageVo<DictType> selectListForPage(BaseQuery query);
}
