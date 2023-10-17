package com.kob.backend.service.community;

import com.kob.backend.pojo.Post;

import java.util.List;
import java.util.Map;

public interface CommunityService {
    List<Post> getLatestPosts();
}
