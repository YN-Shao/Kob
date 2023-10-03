package com.kob.backend.consumer.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data

public class PlayerSnake {
    private Integer id;
    private Integer botId;
    private String botCode;
    private Integer sx;
    private Integer sy;
    private List<Integer> steps;
    private Boolean check_tail_increasing(int step) {  //检验当前回合蛇长度是否增加
        if(step <= 10) {
            return true;
        }
        return step % 3 == 1;
    }
    public List<Cell> getCells() {
        List<Cell> res = new ArrayList<>();
        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
        int x= sx, y = sy;
        int step = 0;
        res.add(new Cell(x,y));
        for(int d: steps){
            x += dx[d];
            y += dy[d];
            res.add(new Cell(x,y));
            if( !check_tail_increasing( ++step ) ) {
                res.remove(0);
            }
        }
        return res;
    }

    public String getStepString(){
        StringBuilder res = new StringBuilder();
        for(int d : steps){
            res.append(d);
        }
        return res.toString();
    }

    public PlayerSnake() {
    }

    public PlayerSnake(Integer id, Integer botId, String botCode, Integer sx, Integer sy, List<Integer> steps) {
        this.id = id;
        this.botId = botId;
        this.botCode = botCode;
        this.sx = sx;
        this.sy = sy;
        this.steps = steps;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setBotId(Integer botId) {
        this.botId = botId;
    }
    public Integer getBotId() {
        return botId;
    }

    public Integer getSx() {
        return sx;
    }

    public void setSx(Integer sx) {
        this.sx = sx;
    }

    public Integer getSy() {
        return sy;
    }

    public void setSy(Integer sy) {
        this.sy = sy;
    }

    public List<Integer> getStep() {
        return steps;
    }
    public String getBotCode() {
        return botCode;
    }

    public void setStep(List<Integer> steps) {
        this.steps = steps;
    }


    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", sx=" + sx +
                ", sy=" + sy +
                ", steps=" + steps +
                '}';
    }
}

