package com.kodilla;

public class SoutMachine {
    private static final int EMPTY = -1;

    public void soutValues(SudokuBoard board) {
        System.out.println("WPROWADZONA TABLICA:");
        System.out.print("    ");
        for (int j = 0; j < board.getColumnsQuantity(); j++) {
            System.out.print((j+1)+". ");
        }
        System.out.println();
        for (int j = 0; j < board.getColumnsQuantity(); j++) {
            System.out.print((j+1)+". ");
            for (int i = 0; i < board.getRowsQuantity(); i++) {
                if (board.getElement(i, j).getValue() != -1) {
                    System.out.print(" "+board.getElement(i, j).getValue()+" ");
                } else {
                    System.out.print(" - ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void soutPossibilities(SudokuBoard board) {
        System.out.println("WYPISUJĘ ILOSCI LICZB MOZLIWYCH DO WPROWADZENIA:");
        System.out.print("    ");
        for (int j = 0; j < board.getColumnsQuantity(); j++) {
            System.out.print((j+1)+". ");
        }
        System.out.println();
        for (int j = 0; j < board.getColumnsQuantity(); j++) {
            System.out.print((j+1)+". ");
            for (int i = 0; i < board.getRowsQuantity(); i++) {
                if (board.getElement(i, j).getValue() == EMPTY) {
                    System.out.print(" "+board.getElement(i, j).getPossibleValues().stream().filter(e -> e != EMPTY).count()+" ");
                } else {
                    if (board.getElement(i, j).getValue() == EMPTY && board.getElement(i, j).getPossibleValues().size() == 0) {
                        System.out.print(" X ");
                    } else {
                        System.out.print(" - ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
