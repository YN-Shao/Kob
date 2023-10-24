package com.kob.backend.consumer;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.consumer.utils.Chess;
import com.kob.backend.consumer.utils.GameSnake;
import com.kob.backend.consumer.utils.JwtAuthentication;
import com.kob.backend.mapper.BotMapper;
import com.kob.backend.mapper.GomokuRecordMapper;
import com.kob.backend.mapper.RatingMapper;
import com.kob.backend.mapper.RecordMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {
    private Session session = null; //每个链接用session维护，对应一个用户
    private User user;

    //为了可以根据用户ID找到对应的链接，才可以给Client发请求，ConcurrentHashMap是线程安全的，用来存储所有的链接,对所有实例可见
    final public static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>();
    public static UserMapper userMapper;
    public Chess chess = null;
    public GameSnake gameSnake = null;
    public static RecordMapper recordMapper;
    public static GomokuRecordMapper gomokuRecordMapper;
    private static BotMapper botMapper;
    public static RestTemplate restTemplate ;//可以让两个服务之间互相通信
    private final static String addPlayerUrl = "http://127.0.0.1:3001/player/add/";
    private final static String removePlayerUrl = "http://127.0.0.1:3001/player/remove/";
    @Autowired
    public void setUserMapper(UserMapper userMapper){
        WebSocketServer.userMapper = userMapper;
    }//因为不是单例模式(同一时间只能有一个实例）而是每建立一个链接就会创建一个实例，所以不能用自动注入，要用静态方法注入

    @Autowired
    public void setRecordMapper(RecordMapper recordMapper){
        WebSocketServer.recordMapper = recordMapper;
    }

        @Autowired
    public void setGomokuRecordMapper(GomokuRecordMapper gomokuRecordMapper){
        WebSocketServer.gomokuRecordMapper = gomokuRecordMapper;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        WebSocketServer.restTemplate = restTemplate;
    }
    @Autowired
    public void setBotMapper(BotMapper botMapper){
        WebSocketServer.botMapper = botMapper;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException{
        // 建立连接
        this.session = session;
        System.out.println("Snake connect");
        Integer userId = JwtAuthentication.getUserId(token); // 从token中获取userId
        this.user = userMapper.selectById(userId);

        if( this.user != null ){
            users.put(userId, this);//ConcurrentHashMap<Integer, WebSocketServer> users
        }else{
            this.session.close();
        }

        System.out.println(users);
    }

    @OnClose
    public void onClose() {
        System.out.println("Snake close");
        // 关闭链接
        if( this.user != null) {
            users.remove(this.user.getId());
        }
    }

    public static void startGame(Integer aId, Integer aBotId, Integer bId, Integer bBotId){
        User user1 = userMapper.selectById(aId), user2 = userMapper.selectById(bId);
        Bot botA = botMapper.selectById(aBotId), botB = botMapper.selectById(bBotId);

        GameSnake gameSnake = new GameSnake(
                13,
                14,
                20,
                user1.getId(),
                botA,
                user2.getId(),
                botB
        );
        gameSnake.createMap();
        if(users.get(user1.getId()) != null){
            users.get(user1.getId()).gameSnake = gameSnake;
        }
        if(users.get(user2.getId()) != null){
            users.get(user2.getId()).gameSnake = gameSnake;
        }

        gameSnake.start();//start a new thread to run the game

        JSONObject respGame = new JSONObject();
        respGame.put("a_id", gameSnake.getPlayerA().getId());
        respGame.put("a_sx", gameSnake.getPlayerA().getSx());
        respGame.put("a_sy", gameSnake.getPlayerA().getSy());
        respGame.put("b_id", gameSnake.getPlayerB().getId());
        respGame.put("b_sx", gameSnake.getPlayerB().getSx());
        respGame.put("b_sy", gameSnake.getPlayerB().getSy());
        respGame.put("map", gameSnake.getG());

        JSONObject respA = new JSONObject();
        respA.put("event", "start-matching");
        respA.put("opponent_username", user2.getUsername());
        respA.put("opponent_photo", user2.getPhoto());
        respA.put("game", respGame);
        if(users.get(user1.getId()) != null)
            users.get(user1.getId()).sendMessage(respA.toJSONString());

        JSONObject respB = new JSONObject();
        respB.put("event", "start-matching");
        respB.put("opponent_username", user1.getUsername());
        respB.put("opponent_photo", user1.getPhoto());
        respB.put("game", respGame);
        if(users.get(user2.getId()) != null)
            users.get(user2.getId()).sendMessage(respB.toJSONString());
    }

    public static void startChess(Integer aId, Integer aBotId, Integer bId, Integer bBotId){
        User user1 = userMapper.selectById(aId), user2 = userMapper.selectById(bId);
        Bot botA = botMapper.selectById(aBotId), botB = botMapper.selectById(bBotId);

        Chess chess = new Chess(
                15,
                15,
                user1.getId(),
                botA,
                user2.getId(),
                botB
        );
        //chess.createMap();
        if(users.get(user1.getId()) != null){
            users.get(user1.getId()).chess = chess;
        }
        if(users.get(user2.getId()) != null){
            users.get(user2.getId()).chess = chess;
        }

        chess.start();

        JSONObject respGame = new JSONObject();
        respGame.put("a_id", chess.getPlayerA().getId());
        respGame.put("b_id", chess.getPlayerB().getId());
        respGame.put("map", chess.getG());

        JSONObject respA = new JSONObject();
        respA.put("event", "start-matching");
        respA.put("opponent_username", user2.getUsername());
        respA.put("opponent_photo", user2.getPhoto());
        respA.put("game", respGame);
        if(users.get(user1.getId()) != null)
            users.get(user1.getId()).sendMessage(respA.toJSONString());

        JSONObject respB = new JSONObject();
        respB.put("event", "start-matching");
        respB.put("opponent_username", user1.getUsername());
        respB.put("opponent_photo", user1.getPhoto());
        respB.put("game", respGame);
        if(users.get(user2.getId()) != null)
            users.get(user2.getId()).sendMessage(respB.toJSONString());
    }

    private void startMatching(Integer botId, Integer gameId){
        //开始匹配
        System.out.println("Snake start-matching");
        MultiValueMap<String ,String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        data.add("rating",this.user.getRating().toString());
        data.add("bot_id", botId.toString());
        data.add("game_id", gameId.toString());
        restTemplate.postForObject(addPlayerUrl, data, String.class);
    }
    private void stopMatching(){
        //停止匹配
        System.out.println("Snake stop-matching");
        MultiValueMap<String ,String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        restTemplate.postForObject(removePlayerUrl, data, String.class);
    }

    private void move(int direction){
        if(gameSnake.getPlayerA().getId().equals(user.getId())){
            if(gameSnake.getPlayerA().getBotId().equals(-1))//如果是人类玩家再接受操作，否则屏蔽
                gameSnake.setNextStepA(direction);
        }else if(gameSnake.getPlayerB().getId().equals(user.getId())){
            if(gameSnake.getPlayerB().getBotId().equals(-1))
                gameSnake.setNextStepB(direction);
        }
    }

    private void moveGomoku(int x, int y, String color){
        if(chess.getPlayerA().getId().equals(user.getId()) || chess.getPlayerB().getId().equals(user.getId())){
            if(chess.getPlayerA().getBotId().equals(-1))//如果是人类玩家再接受操作，否则屏蔽
                chess.setNextStep(x, y, color);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息时触发此函数
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");//接收前端传来的"event"
        System.out.println("game_id " + data.getInteger("game_id"));
        if("start-matching".equals(event)){
            //开始匹配
            startMatching(data.getInteger("bot_id"), data.getInteger("game_id"));
        }else if ("stop-matching".equals(event)){
            //停止匹配
            stopMatching();
        }
        else if("move".equals(event)){
            move(data.getInteger("direction"));
        }
        else if("moveGomoku".equals(event)){
            System.out.println(data);
            moveGomoku(data.getInteger("x"), data.getInteger("y"), data.getString("color"));
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
        System.out.println("onError");
    }
    public void sendMessage(String message){  //  从后端向Client发送消息
        synchronized (this.session){
            try{
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

