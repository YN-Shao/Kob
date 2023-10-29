package com.kob.backend.service.impl.ranklist;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.ranklist.GetRankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetRankListServiceImpl implements GetRankListService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public JSONObject getList(Integer page , Integer gameId) {
        IPage<User> userIpage = new Page<>(page, 10);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        switch (gameId) {
            case 1:
                queryWrapper.orderByDesc("rating");
                break;
            case 2:
                queryWrapper.orderByDesc("gomoku_rating");
                break;
        }

        List<User> users = userMapper.selectPage(userIpage,queryWrapper).getRecords();

        JSONObject resp = new JSONObject();
        for(User user:users){
            user.setPassword("");
        }
        resp.put("users", users);
        resp.put("users_count", userMapper.selectCount(null));
        return resp;
    }

}

