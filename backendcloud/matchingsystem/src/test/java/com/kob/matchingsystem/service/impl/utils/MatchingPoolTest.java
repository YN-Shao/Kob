package com.kob.matchingsystem.service.impl.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MatchingPoolTest {

    @InjectMocks
    private MatchingPool matchingPool;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddPlayers() {
        matchingPool.addPlayers(1, 100, 1, 1);
    }
    @Test
    public void testRemovePlayers() {
        matchingPool.addPlayers(1, 100, 1, 1);
        matchingPool.addPlayers(2, 110, 2, 2);
        matchingPool.removePlayers(1);
    }
    @Test
    public void testCheckMatched() {
        Player a = new Player(1, 100, 1, 1, 1);
        Player b = new Player(2, 105, 2, 1, 0);
        Player c = new Player(3, 200, 3, 1, 10);
        Player d = new Player(4, 105, 1, 1, 1);
        assertFalse(matchingPool.checkMatched(a, b));
        assertFalse(matchingPool.checkMatched(a, c));
        assertTrue(matchingPool.checkMatched(a, d));
    }



}
