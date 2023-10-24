package com.kob.backend.service.user.account;

import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.snakeRecord;

import java.util.List;
import java.util.Map;

public interface UserProfileService {
    Map<String, String> getInfo();
    Map<String, Object> updateInfo(Map<String, String> data);

}