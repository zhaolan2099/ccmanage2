package com.cc.manage.service.sys.impl;

import com.cc.manage.dao.sys.DictMapper;
import com.cc.manage.domain.sys.Dict;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.sys.DictQuery;
import com.cc.manage.service.sys.DictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：
 * @time ：Created in 2020/9/7 9:38
 */
@Service
public class DictServiceImpl implements DictService {

    @Resource
    DictMapper dictMapper;
    @Override
    public void save(Dict dict) {
        if(dict.getId() == null){
            dictMapper.insert(dict);
        }else {
            dictMapper.updateByPrimaryKeySelective(dict);
        }
    }

    @Override
    public void delete(List<Long> ids) {
        ids.forEach(o->{
            dictMapper.deleteByPrimaryKey(o);
        });
    }

    @Override
    public int selectTotal(DictQuery query) {
        return dictMapper.selectTotal(query);
    }

    @Override
    public List<Dict> selectList(DictQuery query) {
        return dictMapper.selectList(query);
    }

    @Override
    public PageVo selectListForPage(DictQuery query) {
        PageVo pageVo = new PageVo();
        pageVo.setTotal(this.selectTotal(query));
        pageVo.setObject(this.selectList(query));
        return pageVo;
    }

    @Override
    public List<Dict> selectByType(String type) {
        return dictMapper.selectByType(type);
    }
}
