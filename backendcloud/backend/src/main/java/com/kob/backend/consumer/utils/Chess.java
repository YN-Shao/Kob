package com.kob.backend.consumer.utils;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.pojo.GomokuRecord;

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
    private final String[][] g;
    private final ChessPlayer playerA, playerB;
    private Integer[] nextStep = new Integer[]{-1, -1};
    private String nextColourCode = "#ffffff";
    private String prevColourCode = "";
    private Integer emptySpace;
    private final ReentrantLock lock = new ReentrantLock();
    private String status =  "playing"; // playing -> finished
    private String loser = "";//all,A, B
    private List<String> stepString = new ArrayList<>();
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
        this.g = new String[rows][cols];
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

        this.playerA = new ChessPlayer(idA, botIdA, botCodeA, new ArrayList<>());
        this.playerA.setColourCode("#000000");
        this.playerB = new ChessPlayer(idB, botIdB,botCodeB, new ArrayList<>());
        this.playerB.setColourCode("#ffffff");
        sendColorCode("#000000", "#ffffff");
    }

    public String[][] getG() {
        return g;
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

    public void setNextStep(Integer x, Integer y, String colourCode) {
        lock.lock();
        try{
            if (!colourCode.equals(nextColourCode)) {
                this.nextStep = new Integer[] {x, y};
                this.prevColourCode = nextColourCode;
                this.nextColourCode = colourCode;
            }
            if(status.equals("playing")){
                if (nextStep()){
                    judge();
                    if (!(this.nextStep[0] == -1 && this.nextStep[0] == -1)) {
                        sendMove();
                    }
                    // else{
                    //     sendResult();
                    // }
                }
            }
        } finally{
            lock.unlock();
        }
        // if (nextStep()){
        //     judge();
        //     if(status.equals("playing") && this.nextStep[0] != -1 && this.nextStep[0] != -1){
        //         sendMove();
        //     }
        //     else{
        //         sendResult();
        //     }
        // }
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
                    if(this.nextColourCode.equals(playerA.getColourCode())){
                        playerA.getStep().add(nextStep);
                        return true;
                    }
                    else if (this.nextColourCode.equals(playerB.getColourCode())) {
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
    private boolean checkWin(Integer[] coord, String colourCode) {
        List<Integer[]> comps = new ArrayList<>();
        comps.add(new Integer[]{0, 1});
        comps.add(new Integer[]{1, 0});
        comps.add(new Integer[]{1, 1});
        comps.add(new Integer[]{-1, 1});
        for (Integer[] comp: comps) {
            int k = 1;
            int left = -1;
            int right = 1;
            while (k < 5) {
                if (coord[0] + comp[0] * left >= 0 && coord[1] + comp[1] * left >= 0 && coord[0] + comp[0] * left < g.length && coord[1] + comp[1] * left < g.length) {
                    if (this.g[coord[0] + comp[0] * left][coord[1] + comp[1] * left] != null && this.g[coord[0] + comp[0] * left][coord[1] + comp[1] * left].equals(colourCode)) {
                        k += 1;
                        left -= 1;
                        continue;
                    }
                }
                if (coord[0] + comp[0] * right >= 0 && coord[1] + comp[1] * right >= 0 && coord[0] + comp[0] * right < g.length && coord[1] + comp[1] * right < g.length) {
                    if (this.g[coord[0] + comp[0] * right][coord[1] + comp[1] * right] != null && this.g[coord[0] + comp[0] * right][coord[1] + comp[1] * right].equals(colourCode)) {
                        k += 1;
                        right += 1;
                        continue;
                    }
                }
                break;
                
            }
            if (k == 5) {
                return true;
            }
        }
        return false;
    }

    // check if the current step is valid, if valid:
    // return -3 when the target coord is already taken
    // return -2 when a winner didn't exist after the current step
    // return -1 when there are no longer any empty space left on the board
    // return colourCode if there is a winner after the current step
    private String add_step_if_valid(Integer[] step, String colourCode){
        if (this.g[step[0]][step[1]] == null) {
            this.emptySpace -= 1;
            this.g[step[0]][step[1]] = colourCode;
            this.stepString.add(String.format("%d,%d,%s", step[0], step[1], colourCode));
            if (checkWin(step, colourCode)) {
                return colourCode;
            }
            if (emptySpace <= 0) {
                return "draw";
            }
            return "continue";
        }
        return "invalid";

    }
    private void judge(){ // 判断下一步是否合法
        if (this.nextStep[0] > 14 || this.nextStep[1] > 14 || this.nextStep[0] < 0 || this.nextStep[1] < 0) {
            return;
        }
        String result = add_step_if_valid(this.nextStep, this.nextColourCode);
        switch(result) {
            case "draw":
                status = "finished";
                this.loser = "all";
                break;
            case "invalid":
                nextStep[0] = -1;
                nextStep[1] = -1;
                this.nextColourCode = this.prevColourCode;
                break;
            case "continue":
                break;
            default:
                status = "finished";
                if (result.equals(playerA.getColourCode())) {
                    this.loser = "B";   
                }
                else {
                    this.loser = "A";
                }
                sendResult();
                break;
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

    private String getStepString(){
        StringBuilder res = new StringBuilder();
        for(String step: stepString){
            res.append(step);
            res.append(" ");
        }
        return res.toString();
    }

    private void updateUserRating(ChessPlayer player, Integer rating){
        User user = WebSocketServer.userMapper.selectById(player.getId());
        user.setGomokuRating(rating);
        WebSocketServer.userMapper.updateById(user);
    }

    private void saveToDatabase(){
        Integer ratingA = WebSocketServer.userMapper.selectById(playerA.getId()).getGomokuRating();
        Integer ratingB = WebSocketServer.userMapper.selectById(playerB.getId()).getGomokuRating();
        if("A".equals(loser)){
            ratingA -= 2;
            ratingB += 5;
        }else if ("B".equals(loser)){
            ratingA += 5;
            ratingB -= 2;
        }
        updateUserRating(playerA, ratingA);
        updateUserRating(playerB, ratingB);

        GomokuRecord gomokuRecord = new GomokuRecord(
                null,
                playerA.getId(),
                playerB.getId(),
                getStepString(),
                loser,
                new Date()
        );
        WebSocketServer.gomokuRecordMapper.insert(gomokuRecord);
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
            resp.put("x", nextStep[0]);
            resp.put("y", nextStep[1]);
            resp.put("color", nextColourCode);
            senAllMessage(resp.toJSONString());
            nextStep = new Integer[] {-1, -1};
        }finally{
            lock.unlock();
        }
    }

    private void senAllMessage(String message){
        System.out.println(message);
        if( WebSocketServer.users.get(playerA.getId()) != null)
            WebSocketServer.users.get(playerA.getId()).sendMessage(message);
        if( WebSocketServer.users.get(playerB.getId())!= null)
            WebSocketServer.users.get(playerB.getId()).sendMessage(message);

    }

    private void sendColorCode(String AColor, String BColor){
        lock.lock();
        try{
            JSONObject resp =  new JSONObject();
            resp.put("event", "assignColorCode");
            resp.put("color", AColor);
            if( WebSocketServer.users.get(playerA.getId()) != null)
                WebSocketServer.users.get(playerA.getId()).sendMessage(resp.toJSONString());
            resp =  new JSONObject();
            resp.put("event", "assignColorCode");
            resp.put("color", BColor);
            if( WebSocketServer.users.get(playerB.getId())!= null)
                WebSocketServer.users.get(playerB.getId()).sendMessage(resp.toJSONString());
        }finally{
            lock.unlock();
        }

    }


    @Override
    public void run(){
        for(int i = 0 ; i<1000 ;  i++ ){
            if (nextStep()){
                System.out.println("running");
                judge();
                if(status.equals("playing")){
                    if (this.nextStep[0] == -1 && this.nextStep[0] == -1) {
                        break;
                    }
                    sendMove();
                }
                else{
                    // sendResult();
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
