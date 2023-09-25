package com.kob.backend.service.impl.pk;

import com.kob.backend.config.WebSocketConfig;
import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.consumer.utils.Chess;
import com.kob.backend.service.pk.ReceiveChessMoveService;
import org.springframework.stereotype.Service;

@Service
public class ReceiveChessMoveServiceImpl implements ReceiveChessMoveService{
    @Override
    public String receiveBotMove(Integer userId, Integer x, Integer y, Integer colourCode) {
        System.out.println("receive chess coord: " + userId + " " + x + " " + y);

        if(WebSocketServer.users.get(userId) != null){
            Chess chess = WebSocketServer.users.get(userId).chess;
            if(chess != null){
                if(chess.getPlayerA().getId().equals(userId) || chess.getPlayerB().getId().equals(userId) ){
                    chess.setNextStep(x, y, colourCode);
                }
            }
        }

        return "receive bot move success";
    }
}
