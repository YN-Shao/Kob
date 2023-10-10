package com.kob.backend.service.pk;

public interface ReceiveChessMoveService {
    String receiveBotMove(Integer userId, Integer x, Integer y, String ColourCode);
}
