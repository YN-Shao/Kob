package com.kob.backend.consumer.utils;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.Record;
import com.kob.backend.pojo.User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import static com.kob.backend.consumer.WebSocketServer.restTemplate;

public class Chess extends Thread{
    private final Integer rows, cols;
    private final int[][] g;
    static final private int[] dx = {-1 ,0 ,1, 0}, dy = {0, 1, 0, -1};
    private final ChessPlayer playerA, playerB;
    private Integer[] nextStep = new Integer[2];
    private Integer nextColourCode;
    private Integer emptySpace;
    private final ReentrantLock lock = new ReentrantLock();
    private String status =  "playing"; // playing -> finished
    private String loser = "";//all,A, B
    private final static String addBotUrl = "http://127.0.0.1:3002/bot/add/";


    public Chess(Integer rows,
                Integer cols,
                Integer idA,
                Bot botA,
                Integer idB,
                Bot botB
    ) {
        this.rows = rows;
        this.cols = cols;
        this.emptySpace = rows * cols;
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

        this.playerA = new ChessPlayer(idA, botIdA, botCodeA, rows - 2 , 1, new ArrayList<>());

        this.playerB = new ChessPlayer(idB, botIdB,botCodeB,1, cols - 2, new ArrayList<>());
    }

    public int[][] getG() {
        return g;
    }

    // private boolean check_connectivity(int sx, int sy, int tx, int ty){
    //     if(sx == tx && sy == ty) return true;
    //     g[sx][sy] = 1;

    //     for(int i=0; i <4 ; i++){
    //         int x = sx + dx[i], y = sy + dy[i];
    //         if( x >= 0 && x <this.rows && y >= 0 && y < this.cols && g[x][y] == 0) {
    //             if(check_connectivity(x, y, tx, ty)) {
    //                 g[sx][sy] = 0; // backtracking
    //                 return true;
    //             }
    //         }
    //     }
    //     g[sx][sy] = 0; // backtracking
    //     return false;
    // }

    private boolean draw(){
        // for(int i=0 ; i<rows ; i++){
        //     for(int j=0 ; j<cols ; j++){
        //         g[i][j] = 0;
        //     }
        // }
        // for(int r=0 ; r<this.rows ; r++){
        //     g[r][0] = 1;
        //     g[r][this.cols-1] = 1;
        // }
        // for(int c=0 ; c<this.cols ; c++){
        //     g[0][c] = 1;
        //     g[this.rows-1][c] = 1;
        // }

        // Random random = new Random();
        // for(int i=0 ; i < this.inner_walls_count/2 ; i++){
        //     for(int j=0 ; j<1000 ; j++){
        //     int r = random.nextInt(this.rows);
        //     int c = random.nextInt(this.cols);

        //     if(g[r][c] == 1 || g[this.rows -1 -r ][this.cols -1 -c] == 1){
        //         continue;
        //     }
        //     if( r == this.rows -2 && c == this.cols -2){
        //         continue;
        //     }
        //     g[r][c] = g[this.rows - 1 - r ][this.cols - 1 - c ] = 1;
        //     break;
        //     }
        // }
        // return check_connectivity(this.rows -2, 1,1,this.cols -2);
        return true;
    }

    public void createMap(){
        // for(int i = 0 ; i<1000 ; i++){
        //     if(draw()){
        //         break;
        //     }
        // }
    }

    public ChessPlayer getPlayerA() {
        return playerA;
    }

    public ChessPlayer getPlayerB() {
        return playerB;
    }

    private String getInput(ChessPlayer player){//将当前的局面信息，编码成字符串
        // ChessPlayer me, you;
        // if(playerA.getId().equals(player.getId())){
        //     me = playerA;
        //     you = playerB;
        // } else{
        //     me = playerB;
        //     you = playerA;
        // }
        String output = "{";
        for (int i = 0; i < rows; i++) {
            output += "[";
            for (int j = 0; j < cols; j++) {
                output += String.format("%d,", g[i][j]);
            }
            output = output.substring(0, output.length() - 1);
            output += "]";
        }
        output += "}";
        return output;
        // return getMapString() + "#" +       //right
        //         me.getSx() + "#" +
        //         me.getSy() + "#(" +
        //         me.getStepString() + ")#" +
        //         you.getSx() + "#" +
        //         you.getSy() + "#(" +
        //         you.getStepString() + ")";
    }

    private void sendBotCode(ChessPlayer player){
        if(player.getBotId().equals(-1)){ //人类玩家，不用执行代码
            return;
        }
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id" , player.getId().toString());
        data.add("bot_code" , player.getBotCode());
        data.add("input", getInput(player));
        WebSocketServer.restTemplate.postForObject(addBotUrl, data, String.class);//right
    }

    public void setNextStep(Integer x, Integer y, Integer colourCode) {
        lock.lock();
        try{
            this.nextStep = new Integer[] {x, y};
            this.nextColourCode = colourCode;
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
        sendBotCode(playerA);
        sendBotCode(playerB);
        for(int i = 0 ; i<50 ; i++){
            try{
                Thread.sleep(100);
                lock.lock();
                try{
                    if(this.nextColourCode == playerA.getColourCode()){
                        playerA.getStep().add(nextStep);
                        return true;
                    }
                    else if (this.nextColourCode == playerB.getColourCode()) {
                        playerB.getStep().add(nextStep);
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
    private boolean checkWin(Integer[] coord, Integer colourCode) {
        for (int i = -1; i < 2; i += 2) {
            for (int j = -1; j < 2; j += 2) {
                int k = 0;
                while (k < 5) {
                    coord[0] += i;
                    coord[1] += j;
                    if (this.g[coord[0]][coord[1]] != colourCode) {
                        break;
                    }
                    k += 1;
                }
                if (k == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    // check if the current step is valid, if valid:
    // return -3 when the target coord is already taken
    // return -2 when a winner didn't exist after the current step
    // return -1 when there are no longer any empty space left on the board
    // return colourCode if there is a winner after the current step
    private Integer add_step_if_valid(Integer[] step, int[][] map, Integer colourCode){
        if (map[step[0]][step[1]] == 0) {
            this.emptySpace -= 1;
            map[step[0]][step[1]] = colourCode;
            if (checkWin(step, colourCode)) {
                return colourCode;
            }
            if (emptySpace <= 0) {
                return -1;
            }
            return -2;
        }
        return -3;

    }
    private void judge(){ // 判断下一步是否合法
        Integer result = add_step_if_valid(this.nextStep, this.g, this.nextColourCode);
        if (result >= 0) {
            status = "finished";
            if (result == playerA.getColourCode()) {
                this.loser = "B";
            }
            else {
                this.loser = "A";
            }
        }
        if (result == -1) {
            status = "finished";
            this.loser = "all";
        }
        else if (result == -3) {
            // error
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

    private void updateUserRating(ChessPlayer player, Integer rating){
        User user = WebSocketServer.userMapper.selectById(player.getId());
        user.setRating(rating);
        WebSocketServer.userMapper.updateById(user);
    }

    private void saveToDatabase(){
        Integer ratingA = WebSocketServer.userMapper.selectById(playerA.getId()).getRating();
        Integer ratingB = WebSocketServer.userMapper.selectById(playerB.getId()).getRating();
        if("A".equals(loser)){
            ratingA -= 2;
            ratingB += 5;
        }else if ("B".equals(loser)){
            ratingA += 5;
            ratingB -= 2;
        }
        updateUserRating(playerA, ratingA);
        updateUserRating(playerB, ratingB);

        Record record = new Record(
                null,
                playerA.getId(),
                playerA.getSx(),
                playerA.getSy(),
                playerB.getId(),
                playerB.getSx(),
                playerB.getSy(),
                playerA.getStepString(),
                playerB.getStepString(),
                getMapString(),
                loser,
                new Date()
        );
        WebSocketServer.recordMapper.insert(record);
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
            resp.put("step", nextStep);
            resp.put("colourCode", nextColourCode);
            senAllMessage(resp.toJSONString());
            nextStep = null;
        }finally{
            lock.unlock();
        }
    }

    private void senAllMessage(String message){
        if( WebSocketServer.users.get(playerA.getId()) != null)
            WebSocketServer.users.get(playerA.getId()).sendMessage(message);
        if( WebSocketServer.users.get(playerB.getId())!= null)
            WebSocketServer.users.get(playerB.getId()).sendMessage(message);

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
            // else{
            //     status = "finished";
            //     lock.lock();
            //     try {
            //         if (nextStepA == null && nextStepB == null) {
            //             loser = "all";
            //         } else if (nextStepA == null) {
            //             loser = "A";
            //         } else {
            //             loser = "B";
            //         }
            //     }finally {
            //         lock.unlock();
            //     }
            //     sendResult();
            //     break;
            // }
        }
    }

}
