package com.kodilla.mainmenu;

import com.kodilla.SoutMachine;
import com.kodilla.SudokuBoard;

import java.util.Random;

public class EntryDataCreator {
    private final SudokuBoard board;
    private final SoutMachine display = new SoutMachine();
    private final Random rnd = new Random();

    public EntryDataCreator(SudokuBoard board) {
        this.board = board;
    }

    public SudokuBoard manual() {
        return testArray(); //brak recznego wypelniania danych
    }

    public SudokuBoard testArray() {
        int[] inputRow = {1,1,1,1,2,2,2,2,3,3,3,4,4,5,5,5,5,6,6,7,7,7,8,8,8,8,9,9,9,9};
        int[] inputCol = {2,4,6,8,1,4,6,9,2,5,8,3,7,1,2,8,9,3,7,2,5,8,1,4,6,9,2,4,6,8};
        int[] inputVal = {2,5,1,9,8,2,3,6,3,6,7,1,6,5,4,1,9,2,7,9,3,8,2,8,4,7,1,9,7,6};
        for (int i =0; i < inputRow.length; i++) {
            if (board.trySetValue(inputRow[i]-1, inputCol[i]-1, inputVal[i])) {
                System.out.println("Wstawilem wartosc "+inputVal[i]+" w kolumnie "+inputRow[i]+", wierszu "+inputCol[i]+".");
            } else {
                System.out.println("BLAD podczas wstawiania wartosci "+inputVal[i]+"na swpolrzednych x: "+inputRow[i]+", y: "+inputCol[i]+".");
            };
        }
        display.soutValues(board);
        display.soutPossibilities(board);
        return board;
    }

    public SudokuBoard random(int inputDigitsCount) {
        for (int i = 0; i < inputDigitsCount; i++) {
            int row;
            int col;
            int val;
            boolean success = false;
            while (!success) {
                row = rnd.nextInt(9);
                col = rnd.nextInt(9);
                val = rnd.nextInt(8)+1;
                success = board.trySetValue(row, col, val);
            }
        }
        display.soutValues(board);
        display.soutPossibilities(board);
        return board;
    }
}
