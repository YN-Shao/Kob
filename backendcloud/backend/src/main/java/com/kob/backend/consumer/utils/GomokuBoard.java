package com.kob.backend.consumer.utils;

public class GomokuBoard {
        private int[][] board;
        private int currentPlayer;

        public GomokuBoard() {
            this.board = new int[15][15];
            this.currentPlayer = 1; // 1 for player 1, 2 for player 2
        }

        public boolean makeMove(int x, int y) {
            if (board[x][y] == 0) {
                board[x][y] = currentPlayer;
                currentPlayer = 3 - currentPlayer; // Switch player
                return true;
            }
            return false;
        }

        // Add methods to check for a win, etc.

}
