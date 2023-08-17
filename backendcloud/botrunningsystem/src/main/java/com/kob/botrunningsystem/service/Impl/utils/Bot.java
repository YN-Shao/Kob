package com.kob.botrunningsystem.service.Impl.utils;

public class Bot {
    Integer userId;
    String botCode;
    String input;


    public Bot() {
    }


    public Bot(Integer userId) {
        this.userId = userId;
    }

    public Bot(String botCode) {
        this.botCode = botCode;
    }

    public Bot(String input, boolean isInput) {
        this.input = input;
    }


    public Bot(Integer userId, String botCode) {
        this.userId = userId;
        this.botCode = botCode;
    }

    public Bot(Integer userId, String input, boolean isInput) {
        this.userId = userId;
        this.input = input;
    }

    public Bot(String botCode, String input) {
        this.botCode = botCode;
        this.input = input;
    }

    public Bot(Integer userId, String botCode, String input) {
        this.userId = userId;
        this.botCode = botCode;
        this.input = input;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getInput() {
        return input;
    }
    public String getBotCode() {
        return botCode;
    }
}

