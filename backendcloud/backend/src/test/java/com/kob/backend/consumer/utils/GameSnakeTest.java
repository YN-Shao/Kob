package com.kob.backend.consumer.utils;


import com.kob.backend.pojo.Bot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameSnakeTest {
    private GameSnake game;

    @BeforeEach
    public void setUp() {
        game = new GameSnake(20, 20, 5, 1, new Bot(1, 1,"hi","con","des",null,null), 2, new Bot(1, 2,"hi","con","des",null,null));
    }

    @Test
    public void testInitialization() {
        assertNotNull(game.getG());
        assertNotNull(game.getPlayerA());
        assertNotNull(game.getPlayerB());
    }



    @Test
    public void testDraw() {
        // Simply test if the draw function runs without exception.
        game.draw();
    }

    @Test
    public void testCreateMap() {
        game.createMap();
        assertNotNull(game.getG());
    }

    @Test
    public void testSetNextSteps() {
        game.setNextStepA(1);
        game.setNextStepB(2);

    }

}

