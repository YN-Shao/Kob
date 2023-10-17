package com.kob.matchingsystem.service.impl;

import com.kob.matchingsystem.service.MatchingService;
import com.kob.matchingsystem.service.impl.utils.GomokuMatchingPool;
import com.kob.matchingsystem.service.impl.utils.MatchingPool;
import org.springframework.stereotype.Service;

@Service
public class MatchingServiceImpl implements MatchingService {
    public final static MatchingPool matchingPool = new MatchingPool();
    @Override
    public String addPlayer(Integer userId, Integer rating, Integer botId , Integer gameId) {
        System.out.println("addPlayer" + userId + " " + rating+" "+botId + " " + gameId) ;
        matchingPool.addPlayers(userId, rating,botId , gameId);
        return "add player success";
    }
    @Override
    public String removePlayer(Integer userId) {
        System.out.println("removePlayer");
        matchingPool.removePlayers(userId);
        return "remove player success";
    }

}
