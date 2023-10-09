package com.kob.backend.service.user.account;

import com.kob.backend.pojo.Post;

import java.util.List;
import java.util.Map;

public interface UserProfileGetPostService {
    List<Post> getPost(Map<String,String> data);
}
