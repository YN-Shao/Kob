package com.kob.matchingsystem.service.impl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
@Component
public class MatchingPool extends Thread {

    private static List<Player> players = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();
    private static RestTemplate restTemplate ;
    private final static String startChessUrl = "http://127.0.0.1:3000/pk/start/chess/";
    private final static String startGameUrl = "http://127.0.0.1:3000/pk/start/game/";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        MatchingPool.restTemplate = restTemplate;
    }
    public void addPlayers(Integer userId, Integer rating ,Integer botId ,Integer gameId) {
        lock.lock();
        try {
            players.add(new Player(userId, rating, botId, gameId,0));
        } finally {
            lock.unlock();
        }
    }

    public void removePlayers(Integer userId) {
        lock.lock();
        try {
            List<Player> newPlayers = new ArrayList<>();
            for (Player player : players) {
                if (!player.getUserId().equals(userId)) {
                    newPlayers.add(player);
                }
            }
            players = newPlayers;
        } finally {
            lock.unlock();
        }
    }

    private void increaseWaitingTime() {
            for (Player player : players) {
                player.setWaitingTime(player.getWaitingTime() + 1);
            }
    }

    boolean checkMatched(Player a, Player b){
        int ratingDiff = Math.abs(a.getRating() - b.getRating());
        int waitingTime = Math.min(a.getWaitingTime(), b.getWaitingTime());
        return Objects.equals(a.getGameId(), b.getGameId()) && ratingDiff <= waitingTime * 10;
    }
    private void sendResult(Player a,Player b){ // 返回A，B的匹配结果
        System.out.println("SendResult" + a.toString() + b.toString());
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("a_id", a.getUserId().toString());
        data.add("a_bot_id", a.getBotId().toString());
        data.add("b_id", b.getUserId().toString());
        data.add("b_bot_id", b.getBotId().toString());

        System.out.println("Matching pool :  "+ data);

        if (a.getGameId() == 1 || b.getGameId() == 1) {
            restTemplate.postForObject(startGameUrl, data, String.class);
        } else if (a.getGameId() == 2 || b.getGameId() == 2) {
            restTemplate.postForObject(startChessUrl, data, String.class);
        }
    }

    void matchPlayers(){ //尝试匹配结果
        System.out.println("Matching players" + players.toString());
        boolean[] used = new boolean[players.size()];
        for(int i = 0 ;i < players.size() ; i++){
            if(used[i]) continue;
            for(int j = i + 1 ; j < players.size() ; j++){
                if(used[j]) continue;
                Player a = players.get(i);
                Player b = players.get(j);
                if(checkMatched(a,b)){
                    used[i] = true;
                    used[j] = true;
                    sendResult(a,b);
                    break;
                }
            }
        }
        List<Player> newPlayers = new ArrayList<>();
        for(int i = 0 ; i < players.size() ; i++){
            if(!used[i]){
                newPlayers.add(players.get(i));
            }
        }
        players = newPlayers;

    }

    @Override
    public void run(){
        while (true) {
            try {
                Thread.sleep(1000);
                lock.lock();
                try{
                    increaseWaitingTime();
                    matchPlayers();
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }

    }

}
