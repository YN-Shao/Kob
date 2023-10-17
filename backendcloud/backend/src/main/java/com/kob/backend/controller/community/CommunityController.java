package com.kob.backend.controller.community;

import com.kob.backend.pojo.Post;
import com.kob.backend.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommunityController {
    @Autowired
    private CommunityService communityService;
    @GetMapping("/community")
    public List<Post> getLatestPosts(){
        return communityService.getLatestPosts();
    }
}
