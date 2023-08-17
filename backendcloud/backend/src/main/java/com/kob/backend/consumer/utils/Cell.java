package com.kob.backend.consumer.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Cell {
    int x, y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Cell(Cell cell) {
        this.x = cell.x;
        this.y = cell.y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}

