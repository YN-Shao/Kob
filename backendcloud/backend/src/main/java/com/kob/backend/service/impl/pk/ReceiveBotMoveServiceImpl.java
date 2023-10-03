package com.kob.backend.service.impl.pk;

import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.consumer.utils.GameSnake;
import com.kob.backend.service.pk.ReceiveBotMoveService;
import org.springframework.stereotype.Service;

@Service
public class ReceiveBotMoveServiceImpl implements ReceiveBotMoveService {
    @Override
    public String receiveBotMove(Integer userId, Integer direction) {
        System.out.println("receive bot move: " + userId + " " + direction);

        if(WebSocketServer.users.get(userId) != null){
            GameSnake gameSnake = WebSocketServer.users.get(userId).gameSnake;
            if(gameSnake != null){
                if(gameSnake.getPlayerA().getId().equals(userId)){
                    gameSnake.setNextStepA(direction);
                }else if(gameSnake.getPlayerB().getId().equals(userId)){
                    gameSnake.setNextStepB(direction);
                }
            }
        }


        return "receive bot move success";
    }

}
