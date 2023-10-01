package com.kob.backend.service.impl.user.account;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.pojo.snakeRecord;
import com.kob.backend.service.impl.util.UserDetailImpl;
import com.kob.backend.service.user.account.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, String> getInfo() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        // Use UserMapper to get user information
        User dbUser = userMapper.selectById(user.getId());
        Map<String,String> map = new HashMap<>();
        map.put("error_message","success");
        map.put("id",dbUser.getId().toString());
        map.put("username",dbUser.getUsername());
        map.put("photo", dbUser.getPhoto());
        return map;
    }

    @Override
    public Map<String, String> updateInfo(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        // Update user information using the UserMapper
        user.setUsername(data.get("username"));
        user.setPhoto(data.get("photo"));
        // If you allow password updates, ensure to encrypt the new password
        userMapper.updateById(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User information updated successfully");
        return response;
    }


    @Override
    public List<Bot> getBots() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        return null;
//        return botMapper.selectListByUserId(user.getId());
    }

    @Override
    public List<snakeRecord> getRecords() {
        // TODO: Implement the method to get the list of user's game records
        return null;
    }
}