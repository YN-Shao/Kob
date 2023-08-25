package com.kob.backend.controller.pk;

import com.kob.backend.consumer.utils.GomokuBoard;
import com.kob.backend.consumer.utils.GomokuMove;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class GomokuController {
    private GomokuBoard gameBoard = new GomokuBoard();

    @PostMapping("/move")
    public ResponseEntity makeMove(@RequestBody GomokuMove move) {
        boolean success = gameBoard.makeMove(move.getX(), move.getY());
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
