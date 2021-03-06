package com.cinnabar.client.extra.webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cinnabar.client.beans.Message;
import com.cinnabar.client.beans.User;
import com.cinnabar.client.common.util.bean.SpringBeans;
import com.cinnabar.client.config.redisHelper.RedisHelper;
import com.cinnabar.client.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName WebSocketServer.java
 * @Description
 * @createTime 2020-11-27  19:27:00
 */

@ServerEndpoint("/socket/{account}/{token}")
@Component
public class WebSocketServer {

    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static volatile int onlineCount = 0;

    private MessageService messageService = SpringBeans.getBean(MessageService.class);

    private static volatile int online;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收userId
     */
    private String account = "";
    private Integer userId = 0;
    private String token = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("account") String account, @PathParam("token") String token) {
        if (account != null && account.equals(RedisHelper.get(token))) {
            this.session = session;
            this.account = account;
            this.userId = messageService.getUserByAccount(account).getId();
            if (webSocketMap.containsKey(this.account)) {
                webSocketMap.remove(this.account);
                webSocketMap.put(this.account, this);
                //加入set中
            } else {
                webSocketMap.put(this.account, this);
                //加入set中
                addOnlineCount();
                //在线数加1
            }
            log.info("用户连接:" + this.account + ",当前在线人数为:" + getOnlineCount());
            try {
                List<Message> delayMessage = messageService.getDelayMessage(this.userId);
                sendMessage("连接成功");
                if (delayMessage.size() > 0)
                    this.session.getBasicRemote().sendText(JSONObject.toJSONString(delayMessage));
                messageService.deleteDelayMessage(this.userId);
            } catch (IOException e) {
                log.error("用户:" + this.account + ",网络异常!!!!!!");
            }
        } else {
            try {
                session.getBasicRemote().sendText("{\"login\":\"need\"}");
            } catch (IOException e) {
                log.error("用户:" + this.account + ",网络异常!!!!!!");
            }
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(account)) {
            webSocketMap.remove(account);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:" + account + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户:" + account + ", 报文:" + message);
        //可以群发消息
        //消息保存到数据库、redis
        if (!StringUtils.isEmpty(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("fromUser", this.account);
                String toUserAccount = jsonObject.getString("toUser");
                //传送给对应toUserId用户的websocket
                if (!StringUtils.isEmpty(toUserAccount) && webSocketMap.containsKey(toUserAccount)) {
                    webSocketMap.get(toUserAccount).sendMessage(jsonObject);
                } else {
                    String errMessage = "用户[" + toUserAccount + "]不在线，在线时会将信息发送";
                    log.info(errMessage);
                    sendMessage(errMessage);
                    //否则不在这个服务器上，发送到mysql或者redis
                    // 先存redis，最后一起存数据库
                    User toUser = messageService.getUserByAccount(toUserAccount);
                    List<Message> messages = new LinkedList<>();
                    Message messageEntity = new Message();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                    messageEntity.setDateTime(simpleDateFormat.format(new Date()));
                    messageEntity.setMessage(message);
                    messageEntity.setUserId(userId);
                    messageEntity.setToUserId(toUser.getId());
                    messages.add(messageEntity);
                    messageService.saveDelayMessage(messages);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.account + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        String data = "{\"data\":\"" + message + "\"}";
        this.session.getBasicRemote().sendText(data);
    }

    public void sendMessage(JSONObject message) throws IOException {
        this.session.getBasicRemote().sendText(message.toJSONString());
    }


    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:" + userId + "，报文:" + message);
        if (!StringUtils.isEmpty(userId) && webSocketMap.containsKey(userId)) {
            webSocketMap.get(userId).sendMessage(message);
        } else {
            log.error("用户" + userId + ",不在线！");
        }
    }

    public static int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
