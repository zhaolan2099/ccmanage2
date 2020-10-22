package com.cc.manage.service.sys.impl;
import com.cc.manage.dao.sys.DictTypeMapper;
import com.cc.manage.domain.sys.DictType;
import com.cc.manage.exception.BizException;
import com.cc.manage.query.BaseQuery;
import com.cc.manage.query.PageVo;
import com.cc.manage.service.sys.DictTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：
 * @time ：Created in 2020/9/23 9:35
 */
@Service
public class DictTypeServiceImpl implements DictTypeService {
    @Resource
    DictTypeMapper dictTypeMapper;

    @Override
    public void save(DictType dictType) throws BizException {
       List<DictType> checkList =  dictTypeMapper.check(dictType);
       if(checkList != null && checkList.size()  > 0){
            throw new BizException(0,"类型名称或类型编号已存在");
       }
       if(dictType.getId() != null){
            dictTypeMapper.updateByPrimaryKeySelective(dictType);
       }else {
           dictTypeMapper.insert(dictType);
       }
    }

    @Override
    public void delete(List<Long> ids) {
        ids.forEach(o->{
            dictTypeMapper.deleteByPrimaryKey(o);
        });
    }

    @Override
    public int selectTotal(BaseQuery query) {
        return dictTypeMapper.selectTotal(query);
    }

    @Override
    public List<DictType> selectList(BaseQuery query) {
        return dictTypeMapper.selectList(query);
    }

    @Override
    public PageVo<DictType> selectListForPage(BaseQuery query) {
        PageVo pageVo = new PageVo();
        pageVo.setTotal(this.selectTotal(query));
        pageVo.setObject(this.selectList(query));
        return pageVo;
    }
}
