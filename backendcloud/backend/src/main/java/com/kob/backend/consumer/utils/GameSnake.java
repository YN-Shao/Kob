package com.kob.backend.consumer.utils;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.snakeRecord;
import com.kob.backend.pojo.User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class GameSnake extends Thread{
    private final Integer rows, cols, inner_walls_count;
    private final int[][] g;
    static final private int[] dx = {-1 ,0 ,1, 0}, dy = {0, 1, 0, -1};
    private final PlayerSnake playerSnakeA, playerSnakeB;
    private Integer nextStepA = null;
    private Integer nextStepB = null;
    private final ReentrantLock lock = new ReentrantLock();
    private String status =  "playing"; // playing -> finished
    private String loser = "";//all,A, B
    private final static String addBotUrl = "http://127.0.0.1:3002/bot/add/";


    public GameSnake(Integer rows,
                     Integer cols,
                     Integer inner_walls_count,
                     Integer idA,
                     Bot botA,
                     Integer idB,
                     Bot botB
    ) {
        this.rows = rows;
        this.cols = cols;
        this.inner_walls_count = inner_walls_count;
        this.g = new int[rows][cols];
        Integer botIdA = -1 , botIdB = -1;
        String botCodeA = "", botCodeB = "";
        if(botA != null){
            botIdA = botA.getId();
            botCodeA = botA.getContent();
        }
        if(botB != null){
            botIdB = botB.getId();
            botCodeB = botB.getContent();
        }

        this.playerSnakeA = new PlayerSnake(idA, botIdA, botCodeA, rows - 2 , 1, new ArrayList<>());

        this.playerSnakeB = new PlayerSnake(idB, botIdB,botCodeB,1, cols - 2, new ArrayList<>());
    }

    public int[][] getG() {
        return g;
    }

    boolean check_connectivity(int sx, int sy, int tx, int ty){
        if(sx == tx && sy == ty) return true;
        g[sx][sy] = 1;

        for(int i=0; i <4 ; i++){
            int x = sx + dx[i], y = sy + dy[i];
            if( x >= 0 && x <this.rows && y >= 0 && y < this.cols && g[x][y] == 0) {
                if(check_connectivity(x, y, tx, ty)) {
                    g[sx][sy] = 0; // backtracking
                    return true;
                }
            }
        }
        g[sx][sy] = 0; // backtracking
        return false;
    }

    boolean draw(){
        for(int i=0 ; i<rows ; i++){
            for(int j=0 ; j<cols ; j++){
                g[i][j] = 0;
            }
        }
        for(int r=0 ; r<this.rows ; r++){
            g[r][0] = 1;
            g[r][this.cols-1] = 1;
        }
        for(int c=0 ; c<this.cols ; c++){
            g[0][c] = 1;
            g[this.rows-1][c] = 1;
        }

        Random random = new Random();
        for(int i=0 ; i < this.inner_walls_count/2 ; i++){
            for(int j=0 ; j<1000 ; j++){
            int r = random.nextInt(this.rows);
            int c = random.nextInt(this.cols);

            if(g[r][c] == 1 || g[this.rows -1 -r ][this.cols -1 -c] == 1){
                continue;
            }
            if( r == this.rows -2 && c == this.cols -2){
                continue;
            }
            g[r][c] = g[this.rows - 1 - r ][this.cols - 1 - c ] = 1;
            break;
            }
        }
        return check_connectivity(this.rows -2, 1,1,this.cols -2);
    }

    public void createMap(){
        for(int i = 0 ; i<1000 ; i++){
            if(draw()){
                break;
            }
        }
    }

    public PlayerSnake getPlayerA() {
        return playerSnakeA;
    }

    public PlayerSnake getPlayerB() {
        return playerSnakeB;
    }

    private String getInput(PlayerSnake playerSnake){//将当前的局面信息，编码成字符串
        PlayerSnake me, you;
        if(playerSnakeA.getId().equals(playerSnake.getId())){
            me = playerSnakeA;
            you = playerSnakeB;
        } else{
            me = playerSnakeB;
            you = playerSnakeA;
        }
        return getMapString() + "#" +       //right
                me.getSx() + "#" +
                me.getSy() + "#(" +
                me.getStepString() + ")#" +
                you.getSx() + "#" +
                you.getSy() + "#(" +
                you.getStepString() + ")";
    }

    private void sendBotCode(PlayerSnake playerSnake){
        if(playerSnake.getBotId().equals(-1)){ //人类玩家，不用执行代码
            return;
        }
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id" , playerSnake.getId().toString());
        data.add("bot_code" , playerSnake.getBotCode());
        data.add("input", getInput(playerSnake));
        WebSocketServer.restTemplate.postForObject(addBotUrl, data, String.class);//right
    }

    public void setNextStepA(Integer nextStepA) {
        lock.lock();
        try{
        this.nextStepA = nextStepA;
        } finally{
            lock.unlock();
        }
    }
    public void setNextStepB(Integer nextStepB) {
        lock.lock();
        try{
        this.nextStepB = nextStepB;
        } finally{
            lock.unlock();
        }
    }

    private boolean nextStep(){
        try{
            Thread.sleep(200);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        sendBotCode(playerSnakeA);
        sendBotCode(playerSnakeB);
        for(int i = 0 ; i<50 ; i++){
            try{
                Thread.sleep(100);
                lock.lock();
                try{
                    if(nextStepA != null && nextStepB != null){
                        playerSnakeA.getStep().add(nextStepA);
                        playerSnakeB.getStep().add(nextStepB);
                        return true;
                    }
                } finally{
                    lock.unlock();
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    private boolean check_valid(List<Cell> cellsA, List<Cell> cellsB){
        int n= cellsA.size();
        Cell cell = cellsA.get(n-1);
        if(g[cell.x][cell.y] == 1){
            return false;
        }
        for(int i=0 ; i<n-1 ; i++){
            if(cellsA.get(i).x == cell.x && cellsA.get(i).y == cell.y){
                return false;
            }
        }
        for(int i=0 ; i<n-1 ; i++){
            if(cellsB.get(i).x == cell.x && cellsB.get(i).y == cell.y){
                return false;
            }
        }
        return true;
    }
    private void judge(){ // 判断下一步是否合法
        List<Cell> cellsA = playerSnakeA.getCells();
        List<Cell> cellsB = playerSnakeB.getCells();
        boolean validA = check_valid(cellsA, cellsB);
        boolean validB = check_valid(cellsB, cellsA);
        if(!validA || !validB) {
            status = "finished";

            if (!validA && !validB) {
                loser = "all";
            }
            else if (!validA) {
                loser = "A";
            }
            else {
                loser = "B";
            }
        }
    }

    private String getMapString(){
        StringBuilder res = new StringBuilder();
        for(int i=0 ; i<rows ; i++){
            for(int j=0 ; j<cols ; j++){
                res.append(g[i][j]);
            }
        }
        return res.toString();
    }

    private void updateUserRating(PlayerSnake playerSnake, Integer rating){
        User user = WebSocketServer.userMapper.selectById(playerSnake.getId());
        user.setRating(rating);
        WebSocketServer.userMapper.updateById(user);
    }

    private void saveToDatabase(){
        Integer ratingA = WebSocketServer.userMapper.selectById(playerSnakeA.getId()).getRating();
        Integer ratingB = WebSocketServer.userMapper.selectById(playerSnakeB.getId()).getRating();
        if("A".equals(loser)){
            ratingA -= 2;
            ratingB += 5;
        }else if ("B".equals(loser)){
            ratingA += 5;
            ratingB -= 2;
        }
        updateUserRating(playerSnakeA, ratingA);
        updateUserRating(playerSnakeB, ratingB);

        snakeRecord snakeRecord = new snakeRecord(
                null,
                playerSnakeA.getId(),
                playerSnakeA.getSx(),
                playerSnakeA.getSy(),
                playerSnakeB.getId(),
                playerSnakeB.getSx(),
                playerSnakeB.getSy(),
                playerSnakeA.getStepString(),
                playerSnakeB.getStepString(),
                getMapString(),
                loser,
                new Date()
        );
        WebSocketServer.recordMapper.insert(snakeRecord);
    }

    private void sendResult(){  //向客户端广播
        JSONObject resp = new JSONObject();
        resp.put("event", "result");
        resp.put("loser", loser);
        saveToDatabase();
        senAllMessage(resp.toJSONString());
    }

    private void sendMove(){  //向客户端广播移动
        lock.lock();
        try{
            JSONObject resp =  new JSONObject();
            resp.put("event", "move");
            resp.put("a_direction", nextStepA);
            resp.put("b_direction", nextStepB);
            senAllMessage(resp.toJSONString());
            nextStepA = nextStepB = null;
        }finally{
            lock.unlock();
        }
    }

    private void senAllMessage(String message){
        if( WebSocketServer.users.get(playerSnakeA.getId()) != null)
            WebSocketServer.users.get(playerSnakeA.getId()).sendMessage(message);
        if( WebSocketServer.users.get(playerSnakeB.getId())!= null)
            WebSocketServer.users.get(playerSnakeB.getId()).sendMessage(message);

    }


    @Override
    public void run(){
        for(int i = 0 ; i<1000 ;  i++ ){
            if (nextStep()){
                judge();
                if(status.equals("playing")){
                    sendMove();
                }
                else{
                    sendResult();
                    break;
                }
            }
            else{
                status = "finished";
                lock.lock();
                try {
                    if (nextStepA == null && nextStepB == null) {
                        loser = "all";
                    } else if (nextStepA == null) {
                        loser = "A";
                    } else {
                        loser = "B";
                    }
                }finally {
                    lock.unlock();
                }
                sendResult();
                break;
            }
        }
    }

}
