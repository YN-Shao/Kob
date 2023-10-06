package com.kob.backend.controller.pk;

import com.kob.backend.service.pk.StartGomokuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class StartGomokuController {
    @Autowired
    private StartGomokuService startGomokuService;
    @PostMapping("/pk/start/gomoku/")
    public String startGame(@RequestParam MultiValueMap<String, String> data){
        Integer aId = Integer.parseInt(Objects.requireNonNull(data.getFirst("a_id")));
        Integer aBotId = Integer.parseInt(Objects.requireNonNull(data.getFirst("a_bot_id")));
        Integer bId = Integer.parseInt(Objects.requireNonNull(data.getFirst("b_id")));
        Integer bBotId = Integer.parseInt(Objects.requireNonNull(data.getFirst("b_bot_id")));
        return startGomokuService.startGomoku(aId,aBotId ,bId, bBotId);

    }
}
