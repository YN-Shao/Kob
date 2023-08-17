package com.kob.backend.service.user.bot;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;
public interface RemoveService {
    Map<String, String> remove(Map<String, String> data);

}
