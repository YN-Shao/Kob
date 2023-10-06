package com.kob.matchingsystem.service;

public interface GomokuMatchingService {
    String addPlayer(Integer userId, Integer rating,Integer botId);
    String removePlayer(Integer userId);
}
