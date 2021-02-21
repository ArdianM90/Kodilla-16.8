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
        for (int row = 0; row < board.getColumnsQuantity(); row++) {
            System.out.print((row+1)+". ");
            for (int col = 0; col < board.getRowsQuantity(); col++) {
                if (board.getElement(row, col).getValue() != -1) {
                    System.out.print(" "+board.getElement(row, col).getValue()+" ");
                } else {
                    System.out.print(" - ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void soutPossibilities(SudokuBoard board) {
        System.out.println("ILOSCI LICZB MOZLIWYCH DO WPROWADZENIA:");
        System.out.print("    ");
        for (int j = 0; j < board.getColumnsQuantity(); j++) {
            System.out.print((j+1)+". ");
        }
        System.out.println();
        for (int row = 0; row < board.getColumnsQuantity(); row++) {
            System.out.print((row+1)+". ");
            for (int col = 0; col < board.getRowsQuantity(); col++) {
                if (board.getElement(row, col).getValue() == EMPTY) {
                    System.out.print(" "+board.getElement(row, col).getPossibleValues().stream().filter(e -> e != EMPTY).count()+" ");
                } else {
                    System.out.print(" - ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
