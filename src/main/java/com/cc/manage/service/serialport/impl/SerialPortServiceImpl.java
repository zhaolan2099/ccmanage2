package com.cc.manage.service.serialport.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cc.manage.common.Constant;
import com.cc.manage.dao.board.BoardMapper;
import com.cc.manage.dao.board.SnLogMappr;
import com.cc.manage.domain.board.Board;
import com.cc.manage.domain.board.SnLog;
import com.cc.manage.exception.BizException;
import com.cc.manage.service.board.BoardService;
import com.cc.manage.service.board.BoardTestService;
import com.cc.manage.service.serialport.SerialPortService;
import com.cc.manage.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * @author ：ZL
 * @time ：Created in 2020/8/19 17:33
 * @description：
 */
@Slf4j
@Service
public class SerialPortServiceImpl implements SerialPortService {
    @Resource
    BoardService boardService;
    @Resource
    BoardMapper boardMapper;
    @Resource
    SnLogMappr snLogMappr;
    @Resource
    BoardTestService boardTestService;


    @Override
    @Transactional(rollbackFor = Exception.class,propagation = REQUIRED)
    public JSONObject parseTestResult(String msg) throws BizException {

        //解析入库
        JSONObject jsonObject;
        try {
            jsonObject= JSON.parseObject(msg);
        }catch (Exception e){
            throw new BizException(0,"JSON格式错误");
        }

        String step = "";
        if(jsonObject.get(Constant.TEST_STEP) != null){
            step = jsonObject.getString(Constant.TEST_STEP);
        }
        String boardType = jsonObject.getString(Constant.BOARD_TYPE);
        if(StringUtils.isEmpty(boardType)){
            if(StringUtils.isEmpty(jsonObject.getString(Constant.BOARD_TYPE))){
                throw  new BizException(0,"板类型不能为空");
            };
        }
        switch (step){
            case Constant.TEST_STEP_BEGIN_TEST:
                return handleTest(jsonObject);
            case Constant.TEST_STEP_WRITE_SN:
                return handleWriteSN(jsonObject);
            case Constant.TEST_STEP_WRITE_SN_RESULT:
                return handleWriteSNResult(jsonObject);
            default:
                throw new BizException(0,"请求参数不正确");
        }
    }

    private JSONObject handleTest(JSONObject jsonObject) throws BizException{
        Board board = new Board();
        String boardType = jsonObject.getString(Constant.BOARD_TYPE);
        String softwareVersion = jsonObject.getString(Constant.SOFTWARE_VERSION);
        if(jsonObject.get(Constant.MAC_ADDR)!= null){
            board.setMac(jsonObject.getString(Constant.MAC_ADDR));
        }
        if(jsonObject.get(Constant.MCU_ID)!= null){
            board.setMcuId(jsonObject.getString(Constant.MCU_ID));
        }
        String testResult =jsonObject.getString(Constant.TEST_RESULT);
        board.setBoardType(boardType);
        board.setSoftWareVersion(softwareVersion);
        Board fromDb = boardMapper.get(board);
        JSONObject msg = new JSONObject();
        if(!testResult.equals(Constant.SUCCESS)){
            log.info("{}电路板检测失败不做处理",board.getMac()+board.getMcuId());
            throw new BizException(0,"电路板检测失败不做处理");
        }
        if(!StringUtils.isEmpty(board.getMac())){
            msg.put(Constant.MAC_ADDR,board.getMac());
        }else if(!StringUtils.isEmpty(board.getMcuId())){
            msg.put(Constant.MCU_ID,board.getMcuId());

        }
        if(fromDb != null){
            msg.put(Constant.RESULT,"1");
        }else {
            msg.put(Constant.RESULT,"0");
            board.setTestStatus(Constant.STATUS_1);
            boardService.save(board);
        }
        return msg;
    }
    private JSONObject handleWriteSN(JSONObject jsonObject) throws BizException {
        Board board = new Board();
        JSONObject msg = new JSONObject();
        board.setBoardType(jsonObject.getString(Constant.BOARD_TYPE));
        if(jsonObject.get(Constant.MAC_ADDR)!= null){
            board.setMac( jsonObject.getString(Constant.MAC_ADDR));
            msg.put(Constant.MAC_ADDR,board.getMac());
        }
        if(jsonObject.get(Constant.MCU_ID)!= null){
            board.setMcuId(jsonObject.getString(Constant.MCU_ID));
            msg.put(Constant.MCU_ID,board.getMcuId());
        }
        Board fromDb = boardMapper.get(board);
        if(fromDb == null ){
            log.info("mac: "+ board.getMac() + "_mcu：" + board.getMcuId()+"，没有相关测试记录");
            msg.put(Constant.RESULT,"2");
            msg.put(Constant.SN_WRITE,board.getSn()); //下发收到测试结果指令
            return msg;
        }
        Map<String,String> snValue = null;
        //如果测试完成，将数据库里已有的sn发送到测试机
        if(fromDb.getTestStatus().equals(Constant.STATUS_2)){
            msg.put(Constant.RESULT,"1");
            board.setSn(fromDb.getSn());
        }else {
            msg.put(Constant.RESULT,"0");
            board.setBoardType(fromDb.getBoardType());
            //收到串口请求，生成SN，并下发
            SnLog snLog = new SnLog();
            snLog.setMcu(board.getMcuId());
            snLog.setMac(board.getMac());
            snLog.setBoardType(board.getBoardType());
            SnLog snLogDb = snLogMappr.get(snLog);

            //如果在日志里查找相关记录，证明之前写入失败了，现在仍然用之前的SN号
            if(snLogDb == null){
                snValue = boardTestService.buildSn(board);
            }else {
                board.setSn(snLogDb.getSn());
            }
        }
        msg.put(Constant.SN_WRITE,board.getSn()); //下发收到测试结果指令
        if(snValue != null){
            //记录SN使用日志
            SnLog snLog = new SnLog();
            snLog.setMac(board.getMac());
            snLog.setMcu(board.getMcuId());
            snLog.setCt(new Date());
            snLog.setSn(board.getSn());
            snLog.setStatus(Constant.SN_WRITE_RES_2);
            snLog.setBoardType(board.getBoardType());
            snLogMappr.insertSelective(snLog);
            RedisUtil.set(snValue.get("key"),snValue.get("value"));
        }
        return msg;
    }
    private JSONObject handleWriteSNResult(JSONObject jsonObject) throws BizException {
        String snWriteResult = jsonObject.getString(Constant.SN_WRITE_RESULT);
        JSONObject msg = new JSONObject();
        String mac = "";
        String mcuId = "";
        String boardType = jsonObject.getString(Constant.BOARD_TYPE);
        if(jsonObject.get(Constant.MAC_ADDR) != null){
            mac =  jsonObject.getString(Constant.MAC_ADDR);
            msg.put(Constant.MAC_ADDR,mac);
        }
        if(jsonObject.get(Constant.MCU_ID) != null){
            mcuId = jsonObject.getString(Constant.MCU_ID);
            msg.put(Constant.MCU_ID,mcuId);
        }
        SnLog query = new SnLog();
        query.setMac(mac);
        query.setMcu(mcuId);
        query.setBoardType(boardType);
        SnLog snLogDb = snLogMappr.get(query);
        if(snLogDb == null){
            throw  new BizException(0,"没有SN记录");
        }

        if(!snWriteResult.equals(Constant.SUCCESS)){
            //SN写入失败
            snLogDb.setStatus(Constant.SN_WRITE_RES_0);
            snLogMappr.updateByPrimaryKeySelective(snLogDb);
            throw new BizException(0,"SN写入失败s");
        }
        //SN写入成功
        snLogDb.setStatus(Constant.SN_WRITE_RES_1);
        snLogMappr.updateByPrimaryKeySelective(snLogDb);
        //将SN号入库
        Board board = new Board();
        board.setBoardType(boardType);
        board.setSn(snLogDb.getSn());
        if(!StringUtils.isEmpty(mac)){
            board.setMac(mac);
        }
        if(!StringUtils.isEmpty(mcuId)){
            board.setMcuId(mcuId);
        }
        boardService.updateByTest(board);
        msg.put(Constant.RESULT,"0");
        return msg;
    }
}
