package com.kob.backend.service.impl.pk;

import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.service.pk.StartGameService;
import com.kob.backend.service.pk.StartGomokuService;
import org.springframework.stereotype.Service;

@Service
public class StartGomokuServiceImpl implements StartGomokuService {
    @Override
    public String startGomoku(Integer aId, Integer aBotId,Integer bId, Integer bBotId) {
        System.out.println("startGame" + aId + " " +aBotId + "B: "+ bId + " " + bBotId);
        WebSocketServer.startGame(aId,aBotId ,bId, bBotId);
        return "start game success";
    }
}
