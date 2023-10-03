package com.kob.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Game {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    // 无参数构造函数
    public Game() {}

    // 带所有参数的构造函数
    public Game(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    // getter 和 setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // toString 方法
    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
