package com.cc.manage.service.board.impl;

import com.cc.manage.common.Constant;
import com.cc.manage.dao.board.BoardMapper;
import com.cc.manage.domain.board.Board;
import com.cc.manage.domain.board.BoardOuting;
import com.cc.manage.domain.sys.LoginUser;
import com.cc.manage.exception.BizException;
import com.cc.manage.service.board.BoardOutSerice;
import com.cc.manage.utils.DateUtil;
import com.cc.manage.utils.RedisUtil;
import com.cc.manage.utils.UserUtil;
import org.springframework.stereotype.Service;
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
 * @time ：Created in 2020/10/29 16:33
 */
@Service
public class BoardOutSericeImpl implements BoardOutSerice {
    @Resource
    BoardMapper boardMapper;

    @Override
    public BoardOuting outIng(String putinNum)throws BizException {
        BoardOuting boardOuting = new BoardOuting();
        List<Board> list = boardMapper.getByPutinNum(putinNum);
        if(list == null || list.size() == 0){
            throw new BizException(0,"没有找到相关入库记录");
        }
        if(Constant.STATUS_6.equals(list.get(0).getTestStatus())){
            throw new BizException(0,"已出库");
        }
        boardOuting.setPutinNum(putinNum);
        boardOuting.setBoardCount(list.size());
        boardOuting.setOrgName(list.get(0).getOrgName());
        boardOuting.setTypeName(list.get(0).getBoardTypeName());
        boardMapper.outIng(putinNum);
        return boardOuting;
    }

    @Override
    public List<Board> outIng2(String putinNum) throws BizException {
        List<Board> list = boardMapper.getByPutinNum(putinNum);
        if(list == null || list.size() == 0){
            throw new BizException(0,"没有找到相关入库记录");
        }
        if(Constant.STATUS_6.equals(list.get(0).getTestStatus())){
            throw new BizException(0,"已出库");
        }
        boardMapper.outIng(putinNum);
        list = boardMapper.getByPutinNum(putinNum);
        return  list;
    }

    @Override
    public void doOut(List<String> putinNums) throws BizException {
        LoginUser user = UserUtil.getCurrentUser();
        Map<String,String> map = this.buildOutNum(user.getOrgNum());

        String outNum = map.get("outNum");
        Date outDate = new Date();
        putinNums.forEach(o->{
            Board board = new Board();
            board.setOutUser(user.getId());
            board.setOutTime(outDate);
            board.setOutNum(outNum);
            board.setTestStatus(Constant.STATUS_6);
            board.setPutinNum(o);
            boardMapper.updateByPutinNum(board);
        });
    }

    @Override
    public void cancelOut(List<String> putinNums) {
        putinNums.forEach(o->{
            boardMapper.cancelOut(o);
        });

    }

    @Override
    public void cancel(List<String> sns) {
        boardMapper.lastStepForPutOut(sns,Constant.STATUS_4);
    }

    private Map<String,String> buildOutNum(String orgNum){
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
    public List<BoardOuting> beginOut()throws BizException {
        LoginUser user = UserUtil.getCurrentUser();
        if(user.getOrgId() == null){
            throw new BizException(0,"只有厂商人员才可操作");
        }
        return boardMapper.getOutingByOrgId(user.getOrgId());
    }

}
