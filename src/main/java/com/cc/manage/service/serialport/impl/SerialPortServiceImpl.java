package com.cc.manage.service.serialport.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cc.manage.common.CodeMsg;
import com.cc.manage.common.Constant;
import com.cc.manage.common.Result;
import com.cc.manage.conf.SystemConfig;
import com.cc.manage.dao.board.BoardMapper;
import com.cc.manage.dao.board.SnLogMappr;
import com.cc.manage.domain.board.Board;
import com.cc.manage.domain.board.SnLog;
import com.cc.manage.domain.print.BarcodeParams;
import com.cc.manage.domain.print.PrintParams;
import com.cc.manage.domain.sys.LoginUser;
import com.cc.manage.exception.BizException;
import com.cc.manage.service.board.BoardService;
import com.cc.manage.service.serialport.SerialPortService;
import com.cc.manage.utils.DateUtil;
import com.cc.manage.utils.HttpUtils;
import com.cc.manage.utils.RedisUtil;
import com.cc.manage.utils.UserUtil;
import com.cc.manage.websocket.WebSocketServer;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Map;

import static com.cc.manage.common.Constant.MAC_ADDR;
import static com.cc.manage.common.Constant.MCU_ID;
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
    @Override
    public Result getPortNames() throws IOException, BizException {
        LoginUser user = UserUtil.getCurrentUser();
        if(user.getOrgId() == null){
            throw new BizException(0,"只有厂商人员才可操作");
        }
        Subject subject = SecurityUtils.getSubject();
//        String res = HttpUtils.httpGet("http://"+user.getIpAddress()+":"+systemConfig.getClientPort()+"/cc/sp/getSPNames",
//                (String) subject.getSession().getId());
        String res = HttpUtils.httpGet(user.getIpAddress()+"/cc/sp/getSPNames",
                (String) subject.getSession().getId());
        Result result = JSONObject.toJavaObject(JSON.parseObject(res),Result.class);
        log.info("获取通讯端串口名称，收到通讯端返回数据:{}",res);
        return result;
    }

    @Override
    public Result beginTest(String portName) throws IOException, BizException {
        LoginUser user = UserUtil.getCurrentUser();
        if(user.getOrgId() == null){
            throw new BizException(0,"只有厂商人员才可操作");
        }
        if(user.getOrgId() == null){
            throw new BizException(0,"只有厂商人员才可操作");
        }
        Subject subject = SecurityUtils.getSubject();
//        String url = "http://"+user.getIpAddress()+":"+systemConfig.getClientPort()+"/cc/sp/beginTest?portName="+portName;
        String url = user.getIpAddress()+"/cc/sp/beginTest?portName="+portName;
        String res = HttpUtils.httpGet(url, (String) subject.getSession().getId());
        Result result = JSONObject.toJavaObject(JSON.parseObject(res),Result.class);
        log.info("开始测试接口，收到通讯端返回数据:{}",res);
        return result;
    }


    @Override
    public Result beginScanner(String sn) throws IOException, BizException {
        return null;
    }


    @Override
    @Transactional(rollbackFor = Exception.class,propagation = REQUIRED)
    public JSONObject parseTestResult(String msg) throws IOException, BizException {

        //解析入库
        JSONObject jsonObject;
        try {
            jsonObject= JSON.parseObject(msg);
        }catch (Exception e){
            throw new BizException(0,"JSON格式错误");
        }
        String step = "";
        if(jsonObject.get(Constant.TEST_STEP) != null){
            step = (String) jsonObject.get(Constant.TEST_STEP);
        }
        switch (step){
            case Constant.TEST_STEP_BEGIN_TEST:
                return handleTest(jsonObject);
            case Constant.TEST_STEP_WRITE_SN:
                return handleWriteSN(jsonObject);
            case Constant.TEST_STEP_WRITE_SN_RESULT:
                return handleWriteSNResult(jsonObject);
            default:
                break;
        }
        return null;
    }

    private JSONObject handleTest(JSONObject jsonObject) throws BizException, IOException {
        Board board = new Board();
        String boardType = (String) jsonObject.get(Constant.BOARD_TYPE);
        if(jsonObject.get(Constant.MAC_ADDR)!= null){
            board.setMac((String) jsonObject.get(Constant.MAC_ADDR));
        }
        if(jsonObject.get(Constant.MCU_ID)!= null){
            board.setMcuId((String) jsonObject.get(Constant.MCU_ID));
        }
        String testResult = (String) jsonObject.get(Constant.TEST_RESULT);
        board.setBoardType(boardType);
        Board fromDb = boardMapper.get(board);
        JSONObject msg = new JSONObject();
        if(!testResult.equals(Constant.SUCCESS)){
            return null;
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
    private JSONObject handleWriteSN(JSONObject jsonObject) throws BizException, IOException {
        Board board = new Board();
        JSONObject msg = new JSONObject();
        if(jsonObject.get(Constant.MAC_ADDR)!= null){
            board.setMac((String) jsonObject.get(Constant.MAC_ADDR));
            msg.put(Constant.MAC_ADDR,board.getMac());
        }
        if(jsonObject.get(Constant.MCU_ID)!= null){
            board.setMcuId((String) jsonObject.get(Constant.MCU_ID));
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
            SnLog snLogDb = snLogMappr.get(snLog);
            //如果在日志里查找相关记录，证明之前写入失败了，现在仍然用之前的SN号
            if(snLogDb == null){
                snValue = boardService.buildSn(board);
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
            snLogMappr.insertSelective(snLog);
            Jedis jedis = RedisUtil.getJedis();
            jedis.set(snValue.get("key"),snValue.get("value"));
            jedis.close();
        }
        return msg;
    }
    private JSONObject handleWriteSNResult(JSONObject jsonObject) throws IOException, BizException {
        String snWriteResult = (String) jsonObject.get(Constant.SN_WRITE_RESULT);
        String mac = "";
        String mcuId = "";
        if(jsonObject.get(MAC_ADDR) != null){
            mac = (String) jsonObject.get(MAC_ADDR);
        }
        if(jsonObject.get(MCU_ID) != null){
            mcuId = (String) jsonObject.get(MCU_ID);
        }
        SnLog query = new SnLog();
        query.setMac(mac);
        query.setMcu(mcuId);
        SnLog snLogDb = snLogMappr.get(query);
        if(snLogDb == null){
            throw  new BizException(0,"未找到生成的SN记录,请严格按照步骤测试。");
        }

        if(!snWriteResult.equals(Constant.SUCCESS)){
            //SN写入失败
            snLogDb.setStatus(Constant.SN_WRITE_RES_0);
            snLogMappr.updateByPrimaryKeySelective(snLogDb);
            return null;
        }
        //SN写入成功
        snLogDb.setStatus(Constant.SN_WRITE_RES_1);
        snLogMappr.updateByPrimaryKeySelective(snLogDb);
        //将SN号入库
        Board board = new Board();
        board.setSn(snLogDb.getSn());
        if(!StringUtils.isEmpty(mac)){
            board.setMac(mac);
        }
        if(!StringUtils.isEmpty(mcuId)){
            board.setMcuId(mcuId);
        }
        boardService.updateByTest(board);
        return null;
    }

    @Override
    public Result sendMsg(String msg) throws IOException, BizException {
        LoginUser user = UserUtil.getCurrentUser();
        Subject subject = SecurityUtils.getSubject();
        String url = user.getIpAddress()+"/cc/sp/sendMsg";
        String res = HttpUtils.httpPostWithJSON(url, msg,(String) subject.getSession().getId());
        Result result = JSONObject.toJavaObject(JSON.parseObject(res),Result.class);
        log.info("下发信息,{}",msg);
        log.info("下发信息收到通讯端返回数据:{}",res);
        return result;
    }

    private Result printBarcodeOld(String sn,String mac) throws IOException, BizException {
        LoginUser user = UserUtil.getCurrentUser();
        Subject subject = SecurityUtils.getSubject();
        //调用热敏打印机打印SN
//        String url = "http://"+user.getIpAddress()+":"+systemConfig.getClientPort()+"/cc/print/printBarcode?sn="+sn+"&mac="+mac;
        String url = user.getIpAddress()+"/cc/print/printBarcode?sn="+sn+"&mac="+mac;
        String res = HttpUtils.httpGet(url,
                (String) subject.getSession().getId());
        Result result = JSONObject.toJavaObject(JSON.parseObject(res),Result.class);
        log.info("获取通讯端串口名称，收到通讯端返回数据:{}",result);
        if(result.getCode() != 1){
            WebSocketServer.SendMessage(new Result<>().fail(new CodeMsg(0,"打印失败，请确认打印机是否正常连接")).toString(),
                    (String) subject.getSession().getId());
            throw new BizException(0,"打印失败，请确认打印机是否正常连接");
        }
        return result;
    }

    private Result printBarcode(PrintParams params) throws IOException, BizException {
        LoginUser user = UserUtil.getCurrentUser();
        Subject subject = SecurityUtils.getSubject();
        //调用热敏打印机打印SN
//        String url = "http://"+user.getIpAddress()+":"+systemConfig.getClientPort()+"/cc/print/printBarcode?sn="+sn+"&mac="+mac;
        String url = user.getIpAddress()+"/cc/print/printBarcode";
        String res = HttpUtils.httpPostWithJSON(url, JSON.toJSONString(params),(String) subject.getSession().getId());
        Result result = JSONObject.toJavaObject(JSON.parseObject(res),Result.class);
        log.info("获取通讯端串口名称，收到通讯端返回数据:{}",result);
        if(result.getCode() != 1){
            WebSocketServer.SendMessage(new Result<>().fail(new CodeMsg(0,"打印失败，请确认打印机是否正常连接")).toString(),
                    (String) subject.getSession().getId());
            throw new BizException(0,"打印失败，请确认打印机是否正常连接");
        }
        return result;
    }


    public static void main(String[] args) {
        NumberFormat f = new DecimalFormat("00000");
        DateUtil.formatDate(new Date(),"yyMM");
        String sn = "B1"+DateUtil.formatDate(new Date(),"yyMM")+ f.format(1);
        System.out.println(sn);
        String x = "123456";
//        String command = "QRCODE 290,272,L,8,A,180,M2,S3,\"123456\"";
        String command = "QRCODE 290,272,L,8,A,180,M2,S3,\""+x+"\"";
        System.out.printf(command);
    }
}
