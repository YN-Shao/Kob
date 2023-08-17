package com.kob.botrunningsystem.service.Impl.utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BotPool extends Thread{
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void addBot(Integer userId, String botCode, String input){
        lock.lock();
        try{
            bots.add(new Bot(userId, botCode, input));
            condition.signalAll();//
        } finally {
            lock.unlock();
        }
    }
    private void consume(Bot bot){
        Consumer consumer = new Consumer();
        consumer.startTimeout(2000, bot);

    }
    private Queue<Bot> bots = new LinkedList<>();
    @Override
    public void run() {
        while(true){
            lock.lock();
            if(bots.isEmpty()){
                try {
                    condition.await(); // wait until bots is not empty
                } catch (InterruptedException e) {
                    lock.unlock();
                    e.printStackTrace();
                    break;
                }
            }
            else{
                Bot bot = bots.remove();
                lock.unlock();;
                consume(bot);
            }
        }
    }
}
