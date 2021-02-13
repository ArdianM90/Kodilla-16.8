package com.kodilla;

import java.util.Scanner;

public class SudokuGame {
    private Scanner input = new Scanner(System.in);
    protected SudokuBoard board;

    //======TEST======
    int[] inputX = {2,4,6,8,1,4,6,9,2,5,8,3,7,1,2,8,9,3,7,2,5,8,1,4,6,9,2,4,6,8};
    int[] inputY = {1,1,1,1,2,2,2,2,3,3,3,4,4,5,5,5,5,6,6,7,7,7,8,8,8,8,9,9,9,9};
    int[] inputVal = {2,5,1,9,8,2,3,6,3,6,7,1,6,5,4,1,9,2,7,9,3,8,2,8,4,7,1,9,7,6};
    //================

    public void play() {
        board = new SudokuBoard();
        String playerChoice = "";
        //======TEST======
        int testIterator = 0;
        //================
        while (!playerChoice.equals("SUDOKU")) {
            System.out.println("Podaj 3 cyfry z przedziału od 1 do 9 oddzielone przecinkami, np: 2,3,9");
            System.out.println("gdzie 2 - oznacza nr kolumny, 3 - nr wiersza, a 9 - wpisywaną cyfrę.");
            System.out.println("Aby wypełnić tablicę sudoku wpisz: SUDOKU.");
//            playerChoice = input.nextLine();
            //======TEST======
            playerChoice = inputX[testIterator]+","+inputY[testIterator]+","+inputVal[testIterator];
            testIterator++;
            //================
            if (checkGivenDigits(playerChoice)) {
                int x = Character.getNumericValue(playerChoice.charAt(0))-1;
                int y = Character.getNumericValue(playerChoice.charAt(2))-1;
                int val = Character.getNumericValue(playerChoice.charAt(4));
                if (!board.trySetValue(x, y, val))
                    System.out.println("Nie można ustawić wartości "+val+" na żądanym polu - wartość się powtarza w wierszu, kolumnie, albo bloku 3x3.");;
            } else {
                System.out.println("Błąd. Nieprawidłowy wpis.");
            }
            //tymczasowe wypisywanie na ekran
            int[][] valuesArray = board.getValuesArray();
            System.out.println("=========");
            for (int j = 0; j < 9; j++) {
                for (int i = 0; i < 9; i++) {
                    if (valuesArray[i][j] < 0) {
                        System.out.print("  ");
                    } else {
                        System.out.print(valuesArray[i][j]+" ");
                    }
                }
                System.out.println();
            }
            System.out.println("=========");
            //======TEST======
            if (testIterator == inputVal.length-1) {
                playerChoice = "SUDOKU";
            }
            //================
            //==============================
        }
        AiMachine aiMachine = new AiMachine(board);
        aiMachine.fillSudoku();
    }

    private boolean checkGivenDigits(String playerChoice) {
        if (playerChoice.trim().length() != 5)
            return false;
        if (playerChoice.charAt(1) != ',' || playerChoice.charAt(3) != ',')
            return false;
        if (!Character.isDigit(playerChoice.charAt(0)) || !Character.isDigit(playerChoice.charAt(2)) || !Character.isDigit(playerChoice.charAt(4)))
            return false;
        if (playerChoice.charAt(0) == '0' || playerChoice.charAt(2) == '0' || playerChoice.charAt(4) == '0')
            return false;
        return true;
    }
}
