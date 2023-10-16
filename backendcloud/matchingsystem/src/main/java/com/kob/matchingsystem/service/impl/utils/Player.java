package com.kob.matchingsystem.service.impl.utils;


import java.util.Objects;

public class Player {
    private Integer userId;
    private Integer rating;
    private Integer botId;
    private Integer gameId;
    private Integer waitingTime;

        public Player() {}

        public Player(Integer userId, Integer rating, Integer botId,Integer gameId,Integer waitingTime) {
            this.userId = userId;
            this.rating = rating;
            this.botId = botId;
            this.gameId = gameId;
            this.waitingTime = waitingTime;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }
        public Integer getBotId() {
            return botId;
        }
        public Integer getGameId() {
        return gameId;
    }

        public Integer getWaitingTime() {
            return waitingTime;
        }

        public void setWaitingTime(Integer waitingTime) {
            this.waitingTime = waitingTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Player player = (Player) o;
            return Objects.equals(userId, player.userId) && Objects.equals(rating, player.rating) && Objects.equals(waitingTime, player.waitingTime) && Objects.equals(gameId, player.gameId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, rating, waitingTime);
        }

        @Override
        public String toString() {
            return "Player{" +
                    "userId=" + userId +
                    ", rating=" + rating +
                    ", waitingTime=" + waitingTime +
                    '}';
        }

    }


