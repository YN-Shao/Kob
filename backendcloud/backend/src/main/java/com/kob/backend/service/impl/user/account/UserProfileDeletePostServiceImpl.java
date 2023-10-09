package com.kob.backend.service.impl.user.account;

import com.kob.backend.mapper.PostMapper;
import com.kob.backend.pojo.Post;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.util.UserDetailImpl;
import com.kob.backend.service.user.account.UserProfileDeletePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserProfileDeletePostServiceImpl implements UserProfileDeletePostService {
    @Autowired
    private PostMapper postMapper;
    @Override
    public Map<String, String> deletePost(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        Integer post_id = Integer.valueOf(data.get("post_id"));
        Map<String, String> map = new HashMap<>();
        Post post = postMapper.selectById(post_id);
        if(post.getUserId().equals(user.getId())) {
            postMapper.deleteById(post_id);
            map.put("error_message", "success");

        }
        return map;

    }
}
