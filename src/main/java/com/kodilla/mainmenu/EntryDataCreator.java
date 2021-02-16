package com.kodilla.mainmenu;

import com.kodilla.SudokuBoard;

import java.util.Random;

public class EntryDataCreator {
    private Random rnd = new Random();

    public SudokuBoard manual(SudokuBoard board) {
        int[] inputX =   {2,4,6,8,1,4,6,9,2,5,8,3,7,1,2,8,9,3,7,2,5,8,1,4,6,9,2,4,6,8};
        int[] inputY =   {1,1,1,1,2,2,2,2,3,3,3,4,4,5,5,5,5,6,6,7,7,7,8,8,8,8,9,9,9,9};
        int[] inputVal = {2,5,1,9,8,2,3,6,3,6,7,1,6,5,4,1,9,2,7,9,3,8,2,8,4,7,1,9,7,6};
        for (int i =0; i < inputX.length; i++) {
            if (board.trySetValue(inputX[i]-1, inputY[i]-1, inputVal[i])) {
                System.out.println("Wstawilem wartosc "+inputVal[i]+" w kolumnie "+inputX[i]+", wierszu "+inputY[i]+".");
            } else {
                System.out.println("BLAD podczas wstawiania wartosci "+inputVal[i]+"na swpolrzednych x: "+inputX[i]+", y: "+inputY[i]+".");
            };
        }
        soutValues(board);
        return board;
    }

    public SudokuBoard create(int inputDigitsCount, SudokuBoard board) {
        for (int i = 0; i < inputDigitsCount; i++) {
            int x;
            int y;
            int val;
            boolean success = false;
            while (!success) {
                x = rnd.nextInt(9);
                y = rnd.nextInt(9);
                val = rnd.nextInt(8)+1;
                success = board.trySetValue(x, y, val);
            }
            soutValues(board);
        }
        return board;
    }

    public void soutValues(SudokuBoard board) {
        System.out.println();
        System.out.println("=====================================================");
        System.out.println("ZAKONCZONO WPROWADZANIE. WYPISUJĘ WPROWADZONE LICZBY:");
        int[][] valuesArray = board.getValuesArray();
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                if (valuesArray[i][j] < 0) {
                    System.out.print("- ");
                } else {
                    System.out.print(valuesArray[i][j]+" ");
                }
            }
            System.out.println();
        }
        System.out.println("=====================================================");
    }
}
