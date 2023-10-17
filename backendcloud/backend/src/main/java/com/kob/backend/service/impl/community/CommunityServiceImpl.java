package com.kob.backend.service.impl.community;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.PostMapper;
import com.kob.backend.pojo.Post;
import com.kob.backend.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private PostMapper postMapper;

    @Override
    public List<Post> getLatestPosts() {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByDesc("post_id");
        queryWrapper.last("LIMIT 10");  // 使用MyBatis的last方法来附加SQL片段，从而限制返回的记录数量

        return postMapper.selectList(queryWrapper);
    }
}
