package com.cc.manage.service.board.impl;

import com.cc.manage.common.Constant;
import com.cc.manage.dao.board.BoardMapper;
import com.cc.manage.domain.board.Board;
import com.cc.manage.domain.board.BoardForOutExport;
import com.cc.manage.domain.sys.LoginUser;
import com.cc.manage.exception.BizException;
import com.cc.manage.query.PageVo;
import com.cc.manage.query.board.BoardQuery;
import com.cc.manage.service.board.BoardService;
import com.cc.manage.utils.DateUtil;
import com.cc.manage.utils.RedisUtil;
import com.cc.manage.utils.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：
 * @time ：Created in 2020/9/3 11:16
 */
@Service
public class BoardServiceImpl implements BoardService {

    @Resource
    BoardMapper boardMapper;

    @Override
    public void delAll() {
        boardMapper.delAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Board board) throws BizException {
        LoginUser user = UserUtil.getCurrentUser();
        board.setCreateTime(new Date());
        board.setCreateUser(user.getId());
        board.setTestTime(new Date());
        board.setTestUser(user.getId());
        board.setOrgId(user.getOrgId());
        boardMapper.insert(board);;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByTest(Board board) throws BizException {
        LoginUser user = UserUtil.getCurrentUser();
        board.setTestUser(user.getId());
        board.setTestTime(new Date());
        board.setTestStatus(Constant.STATUS_2);
        boardMapper.update(board);
    }

    @Override
    public int selectTotal(BoardQuery query) throws BizException {
        LoginUser user = UserUtil.getCurrentUser();
        if(user.getOrgId() != null){
            query.setOrgId(user.getOrgId());
        }
        return boardMapper.selectTotal(query);
    }

    @Override
    public List<Board> selectList(BoardQuery query) throws BizException {
        LoginUser user = UserUtil.getCurrentUser();
        if(user.getOrgId() != null){
            query.setOrgId(user.getOrgId());
        }
        return boardMapper.selectList(query);
    }

    @Override
    public List<BoardForOutExport> selectListForOutExport(String outNum) {
        return boardMapper.selectListForOutExport(outNum);
    }

    @Override
    public PageVo selectListForPage(BoardQuery query) throws BizException {
        PageVo pageVo = new PageVo();
        pageVo.setTotal(this.selectTotal(query));
        pageVo.setObject(this.selectList(query));
        return pageVo;
    }


    @Override
    public Board getBySn(String sn) {
        return boardMapper.getBySn(sn);
    }

    @Override
    public Map<String,String> buildOutNum(String orgNum){
        Map<String,String>  map = new HashMap<>();
        NumberFormat f = new DecimalFormat("00");
        String date = DateUtil.formatDate(new Date(),"yyMM");
        String key = orgNum+date;
        Jedis jedis = RedisUtil.getJedis();
        Long value = Long.parseLong(StringUtils.isEmpty(jedis.get(key))?"0":jedis.get(key));
        value = value + 1;
        jedis.close();
        map.put("key",key);
        map.put("value",String.valueOf(value));
        map.put("outNum",orgNum+date+f.format(value));
        return map;
    }

    @Override
    public void updateBySn(Board board) {
        boardMapper.updateBySn(board);
    }

    @Override
    public List<Board> beginOut()throws BizException {
        LoginUser user = UserUtil.getCurrentUser();
        if(user.getOrgId() == null){
            throw new BizException(0,"只有厂商人员才可操作");
        }
        return boardMapper.getOutingByOrgId(user.getOrgId());
    }


}
