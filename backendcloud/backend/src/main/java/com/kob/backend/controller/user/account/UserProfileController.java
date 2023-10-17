package com.kob.backend.controller.user.account;

import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.snakeRecord;
import com.kob.backend.service.user.account.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user/account")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    // Get user information
    @GetMapping("/info")
    public Map<String, String> getInfo(){
        return userProfileService.getInfo();
    }

    // Update user information
    @PostMapping("/update")
    public Map<String, String> updateInfo(@RequestBody Map<String, String> data){
        return userProfileService.updateInfo(data);
    }

    // Get list of robots
    @GetMapping("/bots")
    public List<Bot> getBots() {
        return userProfileService.getBots();
    }

    // Get list of game records
    @GetMapping("/records")
    public List<snakeRecord> getRecords() {
        return userProfileService.getRecords();
    }


}
