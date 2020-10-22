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
    public void outIng(String sn) throws BizException {
        LoginUser user = UserUtil.getCurrentUser();
        Board board = new Board();
        board.setOutUser(user.getId());
        board.setOutTime(new Date());
        board.setTestStatus(Constant.STATUS_3);
        board.setSn(sn);
        boardMapper.updateOut(board);

    }

    @Override
    public Map<String,String>  doOut(List<String> sns) throws BizException {
        LoginUser user = UserUtil.getCurrentUser();
        Map<String,String> map = this.buildOutNum(user.getOrgNum());
        String outNum = map.get("outNum");
        Date outDate = new Date();
        sns.forEach(o->{
            Board board = new Board();
            board.setOutUser(user.getId());
            board.setOutTime(outDate);
            board.setOutNum(outNum);
            board.setTestStatus(Constant.STATUS_4);
            board.setSn(o);
            boardMapper.updateBySn(board);
        });
        return map;
    }


    @Override
    public void cancelOut(List<String> sn) {
        sn.forEach(o->{
            boardMapper.cancelOut(o);
        });
    }

    @Override
    public Board getBySn(String sn) {
        return boardMapper.getBySn(sn);
    }
    @Override
    public Map<String,String> buildSn(Board board) throws BizException {
        Map<String,String> map = new HashMap<>();
        NumberFormat f = new DecimalFormat("00000");
        LoginUser user = UserUtil.getCurrentUser();
        String orgNum = user.getOrgNum();
        String date = DateUtil.formatDate(new Date(),"yyMM");
        String key = date+board.getBoardType();
        Jedis jedis = RedisUtil.getJedis();
        Long value = Long.parseLong(StringUtils.isEmpty(jedis.get(key))?"0":jedis.get(key));
        value = value + 1;
        jedis.close();
        String sn = board.getBoardType()+orgNum+date+ f.format(value);
        board.setSn(sn);
        map.put("key",key);
        map.put("value",String.valueOf(value));
        return map;
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

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public Board getBySnForOut(String sn) throws BizException{
        Board board = boardMapper.getBySn(sn);
        if(board == null){
            throw new BizException(0,"未找到对应的电路板入库记录");
        }
//        if(board.getTestStatus().equals(Constant.TES_STATUS_3)){
////            throw new BizException(0,"【"+sn+"】正在T出库中");
//        }
        if(board.getTestStatus().equals(Constant.STATUS_4)){
            throw new BizException(0,"【"+sn+"】已出库");
        }
        //修改当前SN对应的电路板为出库中状态
        this.outIng(sn);
        board = boardMapper.getBySn(sn);
        return board;
    }

}
