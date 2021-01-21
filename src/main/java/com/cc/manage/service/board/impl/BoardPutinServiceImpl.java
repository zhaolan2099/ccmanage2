package com.cc.manage.service.board.impl;

import com.cc.manage.common.CodeMsg;
import com.cc.manage.common.Constant;
import com.cc.manage.common.Result;
import com.cc.manage.dao.board.BoardMapper;
import com.cc.manage.domain.board.Board;
import com.cc.manage.domain.sys.LoginUser;
import com.cc.manage.exception.BizException;
import com.cc.manage.query.board.BoardQuery;
import com.cc.manage.service.board.BoardPutinService;
import com.cc.manage.utils.DateUtil;
import com.cc.manage.utils.RedisUtil;
import com.cc.manage.utils.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

@Service
public class BoardPutinServiceImpl implements BoardPutinService {
    @Resource
    BoardMapper boardMapper;

    @Override
    public String doPutin(List<String> sns) throws BizException {
        if(sns == null || sns.size() == 0){
            return null;
        }
        this.checkType(sns);
        Board temp = boardMapper.getBySn(sns.get(0));
        Map<String,String> map = buildPutinNum(temp);
        String putinNum = map.get("putinNum");
        LoginUser user = UserUtil.getCurrentUser();
        Date inDate = new Date();
        sns.forEach(o->{
            Board board = new Board();
            board.setPutinUser(user.getId());
            board.setPutinTime(inDate);
            board.setPutinNum(putinNum);
            board.setTestStatus(Constant.STATUS_4);
            board.setSn(o);
            boardMapper.updateBySn(board);
        });
        RedisUtil.set(map.get("key"),map.get("value"));
        return putinNum;
    }
    /**
     * 检查打包板类型是否一致，不一致不允许打包
     * @param sns
     * @throws BizException
     */
    private void checkType(List<String> sns) throws BizException{
        List<Board> list = boardMapper.selectBatchForSn(sns);
        boolean flag = true;
        String type = "";
        for (int i = 0; i < list.size(); i++){
            if(i == 0){
                type = list.get(i).getBoardType();
            }else {
                if(!type.equals(list.get(i).getBoardType())){
                    flag = false;
                    break;
                }
            }
        }
        if(!flag){
            throw new BizException(0,"测试板类型不致，无法打包。");
        }
    }

    @Override
    public Board getForPutIn(String sn)throws BizException {
        if(StringUtils.isEmpty(sn)){
            throw new BizException(0,"请扫描或输入SN号");
        }
        if(sn.contains("SN:")) {
            sn  = sn.split("SN:")[1];
        }else if(sn.contains("sn:")){
            sn  = sn.split("sn:")[1];
        }
        LoginUser user = UserUtil.getCurrentUser();
        Board board = boardMapper.getBySnAndOrgId(sn,user.getOrgId());
        if(board == null){
            throw new BizException(0,"没有找到电路板相关信息,"+sn);
        }
        if(Constant.STATUS_4.equals(board.getTestStatus())){
            throw new BizException(0,sn+"，已入库");
        }
        if(Constant.STATUS_5.equals(board.getTestStatus())){
            throw new BizException(0,sn+"，已出库");
        }

//        board.setPutinUser(user.getId());
//        board.setPutinTime(new Date());
        board.setTestStatus(Constant.STATUS_3);
        board.setSn(sn);
        boardMapper.putIn(board);
        return board;
    }

    @Override
    public void cancelPutin(List<String> sns) {
        sns.forEach(o->{
            boardMapper.cancelPutin(o);
        });
    }

    @Override
    public void cancel(List<String> sns) {
        for (String sn : sns){
            Board board = boardMapper.getBySn(sn);
            if(board.getTestStatus().equals(Constant.STATUS_3)){
                boardMapper.cancelPutin(sn);
            }
       }
        boardMapper.lastStepForPutin(sns,Constant.STATUS_2);
    }

    @Override
    public List<Board> beginPutin() throws BizException {
        LoginUser user = UserUtil.getCurrentUser();
        if(user.getOrgId() == null){
            throw new BizException(0,"只有厂商人员才可操作");
        }
        return boardMapper.getPutinByOrgId(user.getOrgId());
    }


    private void  checkStatus(List<String> sns)throws BizException{
        List<Board> list = boardMapper.selectBatchForSn(sns);
        list.forEach(o->{
            String status = o.getTestStatus();
            if(!status.equals(Constant.STATUS_3) && !status.equals(Constant.STATUS_4) ){

            }
        });
    }

    private Map<String,String> buildPutinNum(Board board)throws BizException{
        Map<String,String> map = new HashMap<>();
        NumberFormat f = new DecimalFormat("000");
        LoginUser user = UserUtil.getCurrentUser();
        String orgNum = user.getOrgNum();
        String date = DateUtil.formatDate(new Date(),"yyMM");
        String key = Constant.REDIS_KEY_PUTIN+date+board.getBoardType();
        Long value = Long.parseLong(StringUtils.isEmpty(RedisUtil.get(key))?"0":RedisUtil.get(key));
        value = value + 1;
        String putinNum = board.getBoardType()+orgNum+date+ f.format(value);
        board.setPutinNum(putinNum);
        map.put("key",key);
        map.put("value",String.valueOf(value));
        map.put("putinNum",putinNum);
        return map;
    }
}
