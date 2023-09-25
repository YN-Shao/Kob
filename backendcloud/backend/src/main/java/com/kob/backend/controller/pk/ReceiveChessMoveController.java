package com.kob.backend.controller.pk;

import com.kob.backend.service.pk.ReceiveChessMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class ReceiveChessMoveController {
    @Autowired
    private ReceiveChessMoveService receiveChessMoveService;
    @PostMapping("/pk/receive/bot/chess/move/")
    public String receiveChessMove(@RequestParam MultiValueMap<String ,String> data){
        Integer userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("user_id")));
        Integer x = Integer.parseInt(Objects.requireNonNull(data.getFirst("x")));
        Integer y = Integer.parseInt(Objects.requireNonNull(data.getFirst("y")));
        Integer colourCode = Integer.parseInt(Objects.requireNonNull(data.getFirst("colour_code")));
        return receiveChessMoveService.receiveBotMove(userId, x, y, colourCode);
    }
}
