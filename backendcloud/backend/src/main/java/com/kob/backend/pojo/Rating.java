package com.kob.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Rating {

    private Integer userId;

    private Integer gameId;

    private Integer score;

    // 无参数构造函数
    public Rating() {}

    // 带所有参数的构造函数
    public Rating(Integer userId, Integer gameId, Integer score) {
        this.userId = userId;
        this.gameId = gameId;
        this.score = score;
    }

    // getter 和 setter 方法


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    // toString 方法
    @Override
    public String toString() {
        return "Rating{" +
                ", userId=" + userId +
                ", gameId=" + gameId +
                ", score=" + score +
                '}';
    }
}
