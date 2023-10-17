package com.kob.backend.service.community;

import java.util.Map;

public interface CommunityInfoService {
    Map<String, String> getUserInfo(Integer userId);
}
