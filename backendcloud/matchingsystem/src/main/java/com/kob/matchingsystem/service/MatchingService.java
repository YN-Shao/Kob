package com.kob.matchingsystem.service;

public interface MatchingService {
    String addPlayer(Integer userId, Integer rating,Integer botId, Integer gameId);
    String removePlayer(Integer userId);
}
