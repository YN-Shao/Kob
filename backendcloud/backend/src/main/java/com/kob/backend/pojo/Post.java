package com.kob.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Post {
    @TableId(type = IdType.AUTO)
    public Integer postId;
    public Integer userId;
    public String postContent;

    public Post() {
        // 无参构造函数
    }
    public Post(Integer userId, String content) {
        this.userId = userId;
        this.postContent = content;
    }
    public Post(Integer postId, Integer userId, String content) {
        this.postId = postId;
        this.userId = userId;
        this.postContent = content;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String content) {
        this.postContent = content;
    }
}