package com.cc.manage.service.sys.impl;

import com.cc.manage.dao.sys.OrgMapper;
import com.cc.manage.domain.sys.LoginUser;
import com.cc.manage.domain.sys.Org;
import com.cc.manage.exception.BizException;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.sys.OrgQuery;
import com.cc.manage.service.sys.OrgService;
import com.cc.manage.utils.DateUtil;
import com.cc.manage.utils.UserUtil;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Date;
import java.util.List;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/26 15:36
 * @description：
 */
@Service
public class OrgServiceImpl implements OrgService {

    @Resource
    OrgMapper orgMapper;
    @Override
    public void save(Org org) throws BizException{
        String errMsg = "";
        Org checkName = new Org();
        checkName.setId(org.getId());
        checkName.setName(org.getName());
        List<Org> checkNamelist = orgMapper.checkOrg(checkName);
        if(checkNamelist != null && checkNamelist.size() > 0){
            errMsg += "厂商名称已存在.";
        }
        Org checkNum = new Org();
        checkNum.setId(org.getId());
        checkNum.setNum(org.getNum());
        List<Org> checkNumlist = orgMapper.checkOrg(checkName);
        if(checkNumlist != null && checkNamelist.size() > 0){
            errMsg += "厂商编号已存在.";

        }
        if(!StringUtils.isEmpty(errMsg)){
            throw  new BizException(0,errMsg);
        }
        if(org.getId() != null){
            org.setMt(new Date());
            orgMapper.updateByPrimaryKeySelective(org);
        }else {
            org.setCt(new Date());
            orgMapper.insert(org);
        }
    }

//    private String buildNum(){
//        Long id = orgMapper.selectMaxId();
//        Format f1=new DecimalFormat("00000");
//        String  count =f1.format(id);
//        String date = DateUtil.formatDate(new Date(),"yyyy");
//        String num = date+count;
//        return num;
//    }
    @Override
    public void delete(List<Long> ids) {
        ids.forEach(o->{
            Org old = orgMapper.selectByPrimaryKey(o);
            Org org = new Org();
            org.setName(old.getName() + "(已删除)");
            org.setId(o);
            org.setMt(new Date());
            orgMapper.updateByPrimaryKeySelective(org);
        });
    }

    @Override
    public int selectTotal(OrgQuery query) throws BizException {
        //企业帐号，只有查看本企业信息
        LoginUser user = UserUtil.getCurrentUser();
        if(user.getIsAdmin().equals(0)){
            query.setId(user.getOrgId());
        }
        return orgMapper.selectTotal(query);
    }

    @Override
    public List<Org> selectList(OrgQuery query) throws BizException {
        //企业帐号，只有查看本企业信息
        LoginUser user = UserUtil.getCurrentUser();
        if(user.getIsAdmin().equals(0)){
            query.setId(user.getOrgId());
        }
        return orgMapper.selectList(query);
    }

    @Override
    public PageVo selectForPage(OrgQuery query) throws BizException {
        PageVo pageVo = new PageVo();
        pageVo.setTotal(this.selectTotal(query));
        pageVo.setObject(this.selectList(query));
        return pageVo;
    }

    public static void main(String[] args) {
        Long id = 100L;
        Format f1=new DecimalFormat("00000");
        System.out.println(f1.format(id));
    }
}
