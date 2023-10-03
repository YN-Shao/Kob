package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.util.UserDetailImpl;
import com.kob.backend.service.user.bot.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private BotMapper botMapper;
    @Override
public Map<String,String> update(Map<String,String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//获取当前用户
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        int bot_id = Integer.parseInt(data.get("bot_id"));

        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        Map<String, String> map = new HashMap<>();
        if (title == null  || title.isEmpty() || content == null || content.isEmpty()) {
            map.put("error_message", "Title and Content can't be null");
            return map;
        }
        if (title.length() > 100) {
            map.put("error_message", "Title is too long");
            return map;
        }
        if (description == null || description.isEmpty()) {
            description = "Nothing here";
        }
        if (description.length() > 300) {
            map.put("error_message", "Description is too long");
            return map;
        }
        if (content.length() > 10000) {
            map.put("error_message", "Content is too long");
            return map;
        }

        Bot bot = botMapper.selectById(bot_id);
        if(bot == null){
            map.put("error_message","Bot doesn't exist");
            return map;
        }
        if(!bot.getUserId().equals(user.getId())){
            map.put("error_message","You don't have permission to update this bot");
            return map;
        }
        Bot new_bot = new Bot(
                bot_id,
                user.getId(),
                title,
                description,
                content,
                bot.getCreateTime(),
                new Date()
        );

        botMapper.updateById(new_bot);
        map.put("error_message", "Update success");
        return map;
    }

}
