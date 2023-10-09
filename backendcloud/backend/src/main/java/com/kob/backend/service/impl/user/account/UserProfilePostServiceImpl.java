package com.kob.backend.service.impl.user.account;

import com.kob.backend.mapper.PostMapper;
import com.kob.backend.pojo.Post;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.util.UserDetailImpl;
import com.kob.backend.service.user.account.UserProfilePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserProfilePostServiceImpl implements UserProfilePostService {
    @Autowired
    PostMapper postMapper;
    @Override
    public Map<String, String> addPost(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        String content = data.get("content");
        Map<String, String> map = new HashMap<>();
        if (content == null || content.isEmpty()) {
            map.put("error_message", "Content can't be null");
            return map;
        }
        if (content.length() > 2000) {
            map.put("error_message", "Content is too long");
            return map;
        }

        Post post = new Post(null, user.getId(), content);

        postMapper.insert(post);
        map.put("error_message", "success");

        return map;
    }
}
