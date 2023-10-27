package com.kob.backend.consumer.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerSnakeTest {

    @Test
    void testGetCells() {
        // Arrange
        PlayerSnake snake = new PlayerSnake(1, 1, "code", 0, 0, Arrays.asList(1, 2, 3, 0));

        // Act
        List<Cell> cells = snake.getCells();

        // Assert
        assertEquals(5, cells.size());
        assertEquals(0, cells.get(0).getX());
        assertEquals(0, cells.get(0).getY());
        assertEquals(0, cells.get(1).getX());
        assertEquals(1, cells.get(1).getY());
        assertEquals(1, cells.get(2).getX());
        assertEquals(1, cells.get(2).getY());
        assertEquals(1, cells.get(3).getX());
        assertEquals(0, cells.get(3).getY());
    }

    @Test
    void testGetStepString() {
        // Arrange
        PlayerSnake snake = new PlayerSnake(1, 1, "code", 0, 0, Arrays.asList(1, 2, 3, 0));

        // Act
        String stepString = snake.getStepString();

        // Assert
        assertEquals("1230", stepString);
    }
    @Test
    void testGetCellsForInitialPosition() {
        // Arrange
        PlayerSnake snake = new PlayerSnake(1, 1, "code", 0, 0, Collections.emptyList());

        // Act
        List<Cell> cells = snake.getCells();

        // Assert
        assertEquals(1, cells.size());
        assertEquals(0, cells.get(0).getX());
        assertEquals(0, cells.get(0).getY());
    }

    @Test
    void testGetCellsForOneStep() {
        // Arrange
        PlayerSnake snake = new PlayerSnake(1, 1, "code", 0, 0, Collections.singletonList(1));

        // Act
        List<Cell> cells = snake.getCells();

        // Assert
        assertEquals(2, cells.size());
        assertEquals(0, cells.get(0).getX());
        assertEquals(0, cells.get(0).getY());
        assertEquals(0, cells.get(1).getX());
        assertEquals(1, cells.get(1).getY());
    }

    @Test
    void testGetCellsForMoreThan10Steps() {
        // Arrange
        PlayerSnake snake = new PlayerSnake(1, 1, "code", 0, 0, Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));

        // Act
        List<Cell> cells = snake.getCells();

        // Assert
        assertEquals(11, cells.size());
        // You can further assert the individual cell positions based on the steps given
    }

    @Test
    void testGetCellsForRepeatedSteps() {
        // Arrange
        PlayerSnake snake = new PlayerSnake(1, 1, "code", 0, 0, Arrays.asList(1, 3, 1, 3));

        // Act
        List<Cell> cells = snake.getCells();

        // Assert
        assertEquals(5, cells.size());
    }
}
