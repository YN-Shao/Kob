package com.kob.backend.controller.community;

import com.kob.backend.service.community.CommunityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CommunityInfoController {
    @Autowired
    private CommunityInfoService communityInfoService;
    @GetMapping("/getCommunityInfo/")
    public Map<String, String> getUserInfo(@RequestParam Map<String,String> data) {
        Integer userId = Integer.parseInt(data.get("user_id"));
        return communityInfoService.getUserInfo(userId);
    }

}

