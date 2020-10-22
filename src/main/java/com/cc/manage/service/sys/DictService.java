package com.cc.manage.service.sys;

import com.cc.manage.domain.sys.Dict;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.sys.DictQuery;

import java.util.List;

/**
 * @author ：
 * @time ：Created in 2020/9/7 9:38
 */
public interface DictService {
    void save(Dict dict);
    void delete (List<Long> ids);
    int selectTotal(DictQuery query);
    List<Dict> selectList(DictQuery query);
    PageVo selectListForPage(DictQuery query);
    List<Dict> selectByType(String type);
}
