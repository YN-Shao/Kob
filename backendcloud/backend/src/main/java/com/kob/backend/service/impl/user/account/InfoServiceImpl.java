package com.kob.backend.service.impl.user.account;

import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.util.UserDetailImpl;
import com.kob.backend.service.user.account.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class InfoServiceImpl implements InfoService {
    @Override
    public Map<String, String> getInfo() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");
        map.put("id", user.getId().toString());
        map.put("username", user.getUsername());
        map.put("photo", user.getPhoto());
        return map;
    }
}