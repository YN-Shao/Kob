package com.kob.backend.service.impl.community;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.service.community.CommunityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kob.backend.pojo.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommunityInfoServiceImpl implements CommunityInfoService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Map<String, String> getUserInfo(Integer userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String, String> userMap = new HashMap<>();
        User user = userMapper.selectOne(queryWrapper.eq("id", userId));
        userMap.put("username",user.getUsername());
        userMap.put("photo", user.getPhoto());
        return userMap;
    }
}
