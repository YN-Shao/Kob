package com.kob.backend.controller.user.account;

import com.kob.backend.pojo.Post;
import com.kob.backend.service.user.account.UserProfileDeletePostService;
import com.kob.backend.service.user.account.UserProfileGetPostService;
import com.kob.backend.service.user.account.UserProfilePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/account")
public class UserProfilePostController {
    @Autowired
    private UserProfilePostService addPostService;
    @PostMapping("/post/")
    public Map<String, String> addPost(@RequestParam Map<String,String> data){
        return addPostService.addPost(data);
    }

    @Autowired
    private UserProfileGetPostService getPostService;
    @GetMapping("/post/")
    public List<Post> getPost(@RequestParam Map<String,String> data){
        return getPostService.getPost(data);
    }

    @Autowired
    private UserProfileDeletePostService deletePostService;
    @DeleteMapping("/post/")
    public Map<String, String> deletePost(@RequestParam Map<String,String> data){
        return deletePostService.deletePost(data);
    }
}
