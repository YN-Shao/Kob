package com.kob.backend.service.user.account;

import java.util.Map;

public interface RegisterService {
    public Map<String, String> register(String username, String email, String password, String confirmPassword);
}
