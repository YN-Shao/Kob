package com.kob.backend.service.impl.user.account;

import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.util.UserDetailImpl;
import com.kob.backend.service.user.account.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> getInfo() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        User dbUser = userMapper.selectById(user.getId());
        Map<String,String> map = new HashMap<>();
        map.put("error_message","success");
        map.put("id",dbUser.getId().toString());
        map.put("username",dbUser.getUsername());
        map.put("photo", dbUser.getPhoto());
        return map;
    }

    @Override
    public Map<String, Object> updateInfo(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        Map<String, Object> response = new HashMap<>();

        if (data.containsKey("oldPassword")) {
            String oldPassword = data.get("oldPassword");
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                response.put("success", false);
                response.put("message", "Old password is incorrect");
                return response;
            }

            if (data.containsKey("newPassword") && oldPassword.equals(data.get("newPassword"))) {
                response.put("success", false);
                response.put("message", "New password cannot be the same as the old password");
                return response;
            }

            if (data.containsKey("newPassword")) {
                System.out.println("Password updated successfully");
                String newPassword = data.get("newPassword");
                String encodedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encodedPassword);
                System.out.println("Encoded Password: " + encodedPassword); // Add this line for logging
                userMapper.updateById(user);
                System.out.println("User Object After Update: " + user.toString()); // Add this line for logging
                response.put("success", true);
                return response;
            }
        }

        user.setUsername(data.get("username"));
        user.setPhoto(data.get("photo"));
        userMapper.updateById(user);

        response.put("success", true);
        response.put("message", "User information updated successfully");
        return response;
    }

}
