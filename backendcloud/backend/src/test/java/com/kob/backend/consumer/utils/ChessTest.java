package com.kob.backend.consumer.utils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;


public class ChessTest {
    Chess chess;
    WebSocketServer session;

    @BeforeEach                                         
    void setUp() {
        chess = new Chess(15, 15, 0, null, 1, null);
        session = mock(WebSocketServer.class);
        WebSocketServer.users.put(1, session);
        WebSocketServer.users.put(0, session);
    }

    @Test
    public void chessTest_check_insert_one_step() {
        chess.setNextStep(1, 1, "#000000");
        assertEquals(chess.getG()[1][1], "#000000");
    }

    @Test
    public void chessTest_check_cant_insert_same_colour() {
        chess.setNextStep(0, 0, "#000000");
        chess.setNextStep(1, 1, "#000000");
        assertEquals(chess.getG()[0][0], "#000000");
        assertNull(chess.getG()[1][1]);
    }

        @Test
    public void chessTest_check_cant_insert_on_same_pos() {
        chess.setNextStep(0, 0, "#000000");
        chess.setNextStep(0, 0, "#ffffff");
        assertEquals(chess.getG()[0][0], "#000000");
    }

    @Test
    public void chessTest_check_win_when_five_in_row() {
        JSONObject resp = new JSONObject();
        UserMapper userMapper = mock(UserMapper.class);
        WebSocketServer.userMapper = userMapper;
        when(userMapper.selectById(0)).thenReturn(mock(User.class));
        when(userMapper.selectById(1)).thenReturn(mock(User.class));
        resp.put("event", "result");
        resp.put("loser", "B");
        chess.setNextStep(0, 0, "#000000");
        chess.setNextStep(1, 0, "#ffffff");
        chess.setNextStep(0, 1, "#000000");
        chess.setNextStep(1, 1, "#ffffff");
        chess.setNextStep(0, 2, "#000000");
        chess.setNextStep(1, 2, "#ffffff");
        chess.setNextStep(0, 3, "#000000");
        chess.setNextStep(1, 3, "#ffffff");
        chess.setNextStep(0, 4, "#000000");
        verify(session, times(2)).sendMessage(resp.toJSONString());
    }
}
