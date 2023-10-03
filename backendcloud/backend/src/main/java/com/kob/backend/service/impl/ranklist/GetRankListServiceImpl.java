package com.kob.backend.service.impl.ranklist;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kob.backend.mapper.RatingMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.Rating;
import com.kob.backend.pojo.User;
import com.kob.backend.service.ranklist.GetRankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class GetRankListServiceImpl implements GetRankListService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RatingMapper ratingMapper;
    @Override
    public JSONObject getList(Integer page , Integer gameId) {
        IPage<User> userIpage = new Page<>(page, 10);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Rating> ratingQueryWrapper = new QueryWrapper<>();

        List<User> users = userMapper.selectPage(userIpage,queryWrapper).getRecords();

        ratingQueryWrapper.eq("game_id", gameId);
        List<Rating> ratings = ratingMapper.selectList(ratingQueryWrapper);

        for (User user : users) {
            for (Rating rating : ratings) {
                if (user.getId().equals(rating.getUserId())) {
                    user.setRating(rating.getScore());
                    break;
                }
            }
        }
        users.sort(Comparator.comparing(User::getRating).reversed());
        JSONObject resp = new JSONObject();
        for(User user:users){
            user.setPassword("");
        }
        resp.put("users", users);
        resp.put("users_count", userMapper.selectCount(null));
        return resp;
    }

}

