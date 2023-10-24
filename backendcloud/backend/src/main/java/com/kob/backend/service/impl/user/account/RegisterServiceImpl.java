package com.kob.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.GameMapper;
import com.kob.backend.mapper.RatingMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.Game;
import com.kob.backend.pojo.Rating;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.util.Email;
import com.kob.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RatingMapper ratingMapper;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Transactional
    @Override
    public Map<String, String> register(String username, String email, String password, String confirmPassword) {

        Map<String,String> map = new HashMap<>();
        if(username == null ){
            map.put("error_message","Username can not be empty");
            return map;
        }
        username = username.trim();
        if(username.length() == 0){
            map.put("error_message","Username cannot be empty");
            return map;
        }

        if(password == null || confirmPassword == null){
            map.put("error_message","Password can not be empty");
            return map;
        }
        if(password.length() == 0 || confirmPassword.length() == 0){
            map.put("error_message","Password can not be empty");
            return map;
        }
        if(username.length() > 100 ){
            map.put("error_message","Username length cannot exceed 100 characters");
            return map;
        }
        if(password.length() > 100 || confirmPassword.length() > 100){
            map.put("error_message","Password length cannot exceed 100 characters");
            return map;
        }
        if(!password.equals(confirmPassword)){
            map.put("error_message","Passwords do not match");
            return map;
        }
        if(email.split("@").length != 2){
            map.put("error_message","Please enter valid email");
            return map;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<User> users = userMapper.selectList(queryWrapper); //根据用户名查询用户
        if(users.size() != 0){
            map.put("error_message","User already exists");
            return map;
        }

        String encodedPassword = passwordEncoder.encode(password);
        String photo = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRUO9e5Hydo9Cdn8CSCOoJvxWi4huO6bFJb0A&usqp=CAU";
        User user = new User(null, username, encodedPassword, photo,1500,1500);
        userMapper.insert(user);

        // 为新用户初始化评分
        Integer newUserId = user.getId();
        List<Game> allGames = gameMapper.selectList(new QueryWrapper<>());
        for (Game game : allGames) {
            Integer gameId = game.getId();
            Rating newRating = new Rating(newUserId, gameId, 1500);
            ratingMapper.insert(newRating);
        }
        Email.send(email, username);

        map.put("error_message","success");
        return map;
    }
}
