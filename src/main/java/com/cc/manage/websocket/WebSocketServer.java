package com.cc.manage.websocket;

import com.alibaba.fastjson.JSON;
import com.cc.manage.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
@Slf4j
@ServerEndpoint(value = "/ws/asset")
@Component
public class WebSocketServer {
    @PostConstruct
    public void init() {
        log.info("webSocket.. 加载");
    }
    private static final AtomicInteger OnlineCount = new AtomicInteger(0);
    // concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
//    private static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();
    private static Map<String,Session> sessionSet = new HashMap<>();
    private static Map<String,Session> sessionSetLoginName = new HashMap<>();


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        String loginName = session.getUserPrincipal().getName();
        sessionSet.put(session.getQueryString().split("=")[1],session);
        sessionSetLoginName.put(loginName,session);
        int cnt = OnlineCount.incrementAndGet(); // 在线数加1
        log.info("有连接加入，当前连接数为：{},sessionId为:{}", cnt,session.getId());
        Result result = new Result();
        result.setCode(3);
        result.setMsg("连接成功");
        sendMessage(session, JSON.toJSONString(result) );
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        String sessionId = session.getQueryString().split("=")[1];
        String loginName = session.getUserPrincipal().getName();
        sessionSet.remove(sessionId);
        sessionSetLoginName.remove(loginName);
        int cnt = OnlineCount.decrementAndGet();
        log.info("有连接关闭，当前连接数为：{}", cnt);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息：{}",message);

    }

    /**
     * 出现错误
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：{}，Session ID： {}",error.getMessage(),session.getId());
        error.printStackTrace();
    }

    /**
     * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
     * @param session
     * @param message
     */
    public static void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("发送消息出错：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 群发消息
     * @param message
     * @throws IOException
     */
    public static void broadCastInfo(String message) throws IOException {
        for (String key : sessionSet.keySet()) {
            Session session = sessionSet.get(key);
            if(session.isOpen()){
                sendMessage(session, message);
            }
        }
    }

    /**
     * 指定Session发送消息
     * @param sessionId
     * @param message
     * @throws IOException
     */
    public static void sendMessage(String message,String sessionId) {
        Session session = null;
        session = sessionSet.get(sessionId);
        if(session !=  null){
            sendMessage(session, message);
        }
    }


    /**
     * 根据登录名发送
     * @param loginName
     * @param message
     * @throws IOException
     */
    public static void sendMessageForLoginName(String message,String loginName) {
        Session session = null;
        session = sessionSetLoginName.get(loginName);
        if(session !=  null){
            sendMessage(session, message);
        }
    }
}
