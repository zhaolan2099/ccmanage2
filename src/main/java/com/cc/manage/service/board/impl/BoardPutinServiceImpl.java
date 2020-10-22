package com.cc.manage.service.board.impl;

import com.cc.manage.common.Constant;
import com.cc.manage.dao.board.BoardMapper;
import com.cc.manage.domain.board.Board;
import com.cc.manage.domain.sys.LoginUser;
import com.cc.manage.domain.sys.User;
import com.cc.manage.exception.BizException;
import com.cc.manage.service.board.BoardPutinService;
import com.cc.manage.utils.UserUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardPutinServiceImpl implements BoardPutinService {
    @Resource
    BoardMapper boardMapper;

    @Override
    public Map<String,String> doPutin(List<String> sns) throws BizException {
        Map<String,String> map = this.buildPutinNum();
        String putinNum = map.get("putinNum");
        LoginUser user = UserUtil.getCurrentUser();
        Date outDate = new Date();
        sns.forEach(o->{
            Board board = new Board();
            board.setOutUser(user.getId());
            board.setOutTime(outDate);
            board.setOutNum(putinNum);
            board.setTestStatus(Constant.STATUS_4);
            board.setSn(o);
            boardMapper.updateBySn(board);
        });
        return map;
    }

    @Override
    public Board getForPutIn(String sn)throws BizException {
        Board board = boardMapper.getBySn(sn);
        if(board == null){
            throw new BizException(0,"没有找到电路板相关信息,"+sn);
        }
        if(Constant.STATUS_4.equals(board.getTestStatus())){
            throw new BizException(0,sn+"，已入库");
        }
        if(Constant.STATUS_5.equals(board.getTestStatus())){
            throw new BizException(0,sn+"，已出库");
        }
        LoginUser user = UserUtil.getCurrentUser();
        board.setPutinUser(user.getId());
        board.setPutinTime(new Date());
        board.setTestStatus(Constant.STATUS_3);
        board.setSn(sn);
        boardMapper.updatePutIn(board);
        return board;
    }

    @Override
    public void cancelPutin(List<String> sn) {
        sn.forEach(o->{
            boardMapper.cancelPutin(o);
        });
    }

    private Map<String,String> buildPutinNum(){
        Map<String,String> map = new HashMap<>();
        map.put("putinNum","putinNum");
        return map;
    }
}
