package com.kodilla;

import java.util.Scanner;

public class SudokuGame {
    private Scanner input = new Scanner(System.in);
    protected SudokuBoard board;

    //======TEST======
    int[] inputX =   {2,4,6,8,1,4,6,9,2,5,8,3,7,1,2,8,9,3,7,2,5,8,1,4,6,9,2,4,6,8};
    int[] inputY =   {1,1,1,1,2,2,2,2,3,3,3,4,4,5,5,5,5,6,6,7,7,7,8,8,8,8,9,9,9,9};
    int[] inputVal = {2,5,1,9,8,2,3,6,3,6,7,1,6,5,4,1,9,2,7,9,3,8,2,8,4,7,1,9,7,6};
    //================

    public void play() {
        board = new SudokuBoard();
        PlayerChoiceDecoder choiceDecoder = new PlayerChoiceDecoder();
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
            if (choiceDecoder.entryIsCorrect(playerChoice)) {
                int x = choiceDecoder.getX(playerChoice);
                int y = choiceDecoder.getY(playerChoice);
                int val = choiceDecoder.getVal(playerChoice);
                System.out.println("Proba wstawienia wartosci "+val+" na polu x: "+x+", y: "+y+".");
                board.trySetValue(x, y, val);
            } else {
                System.out.println("Błąd. Nieprawidłowy wpis.");
            }
            //tymczasowe wypisywanie na ekran
            int[][] valuesArray = board.getValuesArray();
            System.out.println();
            System.out.println("=========");
            System.out.println("WYPISUJĘ WPROWADZONE LICZBY:");
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
            System.out.println("WYPISUJĘ ILOSCI LICZB MOZLIWYCH DO WPROWADZENIA:");
            for (int j = 0; j < board.getColumnsQuantity(); j++) {
                for (int i = 0; i < board.getRowsQuantity(); i++) {
                    if (board.getElement(i, j).getValue() == -1) {
                        System.out.print(board.getElement(i, j).getPossibleValues().stream().filter(e -> e != -1).count()+" ");
                    } else {
                        if (board.getElement(i, j).getValue() == -1 && board.getElement(i, j).getPossibleValues().size() == 0) {
                            System.out.print("X ");
                        } else {
                            System.out.print("0 ");
                        }
                    }
                }
                System.out.println();
            }
            System.out.println();
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
}
