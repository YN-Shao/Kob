package com.kob.backend.consumer.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChessPlayer {
    private Integer id;
    private Integer botId;
    private String botCode;
    private List<Integer[]> steps;
    private String colourCode;
    // private Boolean check_tail_increasing(int step) {  //检验当前回合蛇长度是否增加
    //     if(step <= 10) {
    //         return true;
    //     }
    //     return step % 3 == 1;
    // }
    // public List<Cell> getCells() {
    //     List<Cell> res = new ArrayList<>();
    //     int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
    //     int x= sx, y = sy;
    //     int step = 0;
    //     res.add(new Cell(x,y));
    //     for(Integer[] d: steps){
    //         x += dx[d];
    //         y += dy[d];
    //         res.add(new Cell(x,y));
    //         if( !check_tail_increasing( ++step ) ) {
    //             res.remove(0);
    //         }
    //     }
    //     return res;
    // }

    // public String getStepString(){
    //     StringBuilder res = new StringBuilder();
    //     for(Integer[] d : steps){
    //         res.append(d[0]);
    //         res.append(d[1]);
    //     }
    //     return res.toString();
    // }

    public ChessPlayer() {
    }

    public ChessPlayer(Integer id, Integer botId, String botCode, List<Integer[]> steps) {
        this.id = id;
        this.botId = botId;
        this.botCode = botCode;
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

    public List<Integer[]> getStep() {
        return steps;
    }
    public String getBotCode() {
        return botCode;
    }

    public void setStep(List<Integer[]> steps) {
        this.steps = steps;
    }

    public String getColourCode() {
        return this.colourCode;
    }

    public void setColourCode(String colourCode) {
        this.colourCode = colourCode;
    }


    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", steps=" + steps +
                '}';
    }
}

