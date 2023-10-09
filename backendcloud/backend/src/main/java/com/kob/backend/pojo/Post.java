package com.kob.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class Post {
    @TableId(type = IdType.AUTO)
    public Integer postId;
    public Integer userId;
    public String postContent;

    public Post() {
    }

    // Parameterized constructor
    public Post(Integer postId, Integer userId, String postContent) {
        this.postId = postId;
        this.userId = userId;
        this.postContent = postContent;
    }

    // Getter and Setter for postId
    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    // Getter and Setter for userId
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // Getter and Setter for postContent
    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

}
