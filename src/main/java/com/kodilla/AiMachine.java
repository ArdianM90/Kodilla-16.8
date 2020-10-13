package com.kodilla;

import java.util.stream.Collectors;

public class AiMachine {
    private SudokuBoard board;

    public AiMachine(SudokuBoard board) {
        this.board = board;
    }

    public void fillSudoku() {
        boolean doNextLoop = true;
        int loopCounter = 0;
        //while (nie wszystkie pola są wypełnione) {
        while (doNextLoop) {
            loopCounter++;
            doNextLoop = loopOverAllElements();
        }
        //losowanie()
        //}
        System.out.println("Zrobiłem "+loopCounter+" petli.");
        //======TEST======
        System.out.println("WPROWADZONA TABLICA:");
        for (int i = 0; i < board.getColumnsQuantity(); i++) {
            for (int j = 0; j < board.getRowsQuantity(); j++) {
                if (board.getElement(i, j).getValue() != -1) {
                    System.out.print(board.getElement(i, j).getValue()+" ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("WYPISUJĘ ILOSCI LICZB MOZLIWYCH DO WPROWADZENIA:");
        for (int i = 0; i < board.getColumnsQuantity(); i++) {
            for (int j = 0; j < board.getRowsQuantity(); j++) {
                if (board.getElement(i, j).getValue() == -1) {
                    System.out.print(board.getElement(i, j).getPossibleValues().stream().filter(e -> e != -1).count()+" ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
        //================
    }

    private boolean loopOverAllElements() {
        boolean didSomeOperation = false;
        int i = 0;
        while(i < board.getColumnsQuantity()) {
            int j = 0;
            while (j < board.getRowsQuantity()) {
                if (board.getElement(i, j).getValue() == -1) {
                    if (board.getElement(i, j).getPossibleValues().stream().filter(e -> e != -1).count() == 1) {
                        int val = board.getElement(i, j).getPossibleValues().stream().filter(e -> e != -1).collect(Collectors.toList()).get(0);
                        //System.out.println("Trafiłem na możliwość wprowadzenia liczby: wiersz [" + (i + 1) + "], kolumna [" + (j + 1) + "], wartość [" + val + "]");
                        if (board.trySetValue(i, j, val)) {
                            didSomeOperation = true;
                        };
                    }
                }
                j++;
            }
            i++;
            System.out.println();
        }
        return didSomeOperation;
    }
}
