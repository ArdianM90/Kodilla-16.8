package com.kodilla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SudokuMain {
    public static void main(String[] args) {
        SpringApplication.run(SudokuMain.class, args);
        SudokuGame myGame = new SudokuGame();
        myGame.play();
    }
}
