package com.kob.backend.controller.user.account;

import com.kob.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;
    @PostMapping("/user/account/register/") //configure it in SecurityConfig to make it public
    public Map<String, String> register(@RequestParam Map<String,String> map){
        System.out.println(map.get("username"));
        String username = map.get("username");
        String email = map.get("email");
        String password = map.get("password");
        String confirmedPassword = map.get("confirmedPassword");
        return registerService.register(username, email, password, confirmedPassword);
    }
}
