package com.kodilla;

import java.util.Scanner;

public class SudokuGame {
    Scanner input = new Scanner(System.in);

    public void play() {
        SudokuBoard board = new SudokuBoard();
        AiMachine aiMachine = new AiMachine();
        String playerChoice = "";
        while (!playerChoice.equals("SUDOKU")) {
            System.out.println("Podaj 3 cyfry z przedziału od 1 do 9 oddzielone przecinkami, np: 2,3,9");
            System.out.println("gdzie 2 - oznacza nr kolumny, 3 - nr wiersza, a 9 - wpisywaną cyfrę.");
            System.out.println("Aby wypełnić tablicę sudoku wpisz: SUDOKU.");
            playerChoice = input.nextLine();
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
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (valuesArray[i][j] < 0) {
                        System.out.print("  ");
                    } else {
                        System.out.print(valuesArray[i][j]+" ");
                    }
                }
                System.out.println();
            }
            System.out.println("=========");
            //==============================
        }
        aiMachine.fillSudoku(board);
//        System.out.println("  123 456 789 ");
//        System.out.println("  -----------  ");
//        System.out.println("1|   |   |   | ");
//        System.out.println("2|   |   |   | ");
//        System.out.println("3|   |   |   | ");
//        System.out.println(" ------------- ");
//        System.out.println("4|   |   |   | ");
//        System.out.println("5|   |   |   | ");
//        System.out.println("6|   |   |   | ");
//        System.out.println(" ------------- ");
//        System.out.println("7|   |   |   | ");
//        System.out.println("8|   |   |   | ");
//        System.out.println("9|   |   |   | ");
//        System.out.println("  -----------  ");
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
