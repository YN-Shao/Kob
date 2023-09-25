package com.kob.backend.service.impl.pk;

import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.service.pk.StartGameService;
import org.springframework.stereotype.Service;

@Service
public class StartGameServiceImpl implements StartGameService {
    @Override
    public String startGame(Integer aId, Integer aBotId,Integer bId, Integer bBotId) {
        System.out.println("startGame" + aId + " " +aBotId + "B: "+ bId + " " + bBotId);
        WebSocketServer.startGame(aId,aBotId ,bId, bBotId);
        return "start game success";
    }

    @Override
    public String startChess(Integer aId, Integer aBotId,Integer bId, Integer bBotId) {
        System.out.println("startGame" + aId + " " +aBotId + "B: "+ bId + " " + bBotId);
        WebSocketServer.startChess(aId,aBotId ,bId, bBotId);
        return "start game success";
    }

}
