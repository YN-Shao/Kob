package com.kob.matchingsystem.service.impl;

import com.kob.matchingsystem.service.GomokuMatchingService;
import com.kob.matchingsystem.service.MatchingService;
import com.kob.matchingsystem.service.impl.utils.GomokuMatchingPool;
import com.kob.matchingsystem.service.impl.utils.MatchingPool;

public class GomokuMatchingServiceImpl implements GomokuMatchingService {
    public final static GomokuMatchingPool GomokumatchingPool = new GomokuMatchingPool();
    @Override
    public String addPlayer(Integer userId, Integer rating,Integer botId) {
        System.out.println("addPlayer" + userId + " " + rating+" "+botId);
        GomokumatchingPool.addPlayers(userId, rating,botId);
        return "add player success";
    }
    @Override
    public String removePlayer(Integer userId) {
        System.out.println("removePlayer");
        GomokumatchingPool.removePlayers(userId);
        return "remove player success";
    }

}

