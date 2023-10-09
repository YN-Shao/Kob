package com.kob.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.PostMapper;
import com.kob.backend.pojo.Post;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.util.UserDetailImpl;
import com.kob.backend.service.user.account.UserProfileGetPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserProfileGetPostServiceImpl implements UserProfileGetPostService {
    @Autowired
    private PostMapper postMapper;
    @Override
    public List<Post> getPost(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        Integer user_id = Integer.valueOf(data.get("user_id"));


        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("user_id", user.getId());

        queryWrapper.orderByDesc("post_id");

        return postMapper.selectList(queryWrapper);
    }
}
