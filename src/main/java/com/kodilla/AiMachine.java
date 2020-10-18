package com.kodilla;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AiMachine {
    public static int EMPTY = -1;
    private SudokuBoard board;
    private Random rnd = new Random();

    public AiMachine(SudokuBoard board) {
        this.board = board;
    }

    public void fillSudoku() {
        int loopCounter = 0;
        //while (nie wszystkie pola są wypełnione) {
        boolean doNextLoop = true;
        while (doNextLoop) {
            loopCounter++;
            doNextLoop = loopOverAllElements();
        }

        //losowanie()
        List<Integer> possibilities = findPossibilities();
        int guessedValueIndex = rnd.nextInt(possibilities.size());
        int guessedValue = possibilities.get(guessedValueIndex);
        System.out.println("Zgadłem i wstawiam wartość "+guessedValue+".");
        //losowanie()

        //}
        System.out.println("Zrobiłem "+loopCounter+" petli i sie zatrzymalem na braku oczywistych wyborow.");
        System.out.println();
        //======TEST======
        soutValues(board);
        soutPossibilities(board);
        //================
    }

    private boolean loopOverAllElements() {
        boolean didSomeOperation = false;
        int i = 0;
        while(i < board.getColumnsQuantity()) {
            int j = 0;
            while (j < board.getRowsQuantity()) {
                if (board.getElement(i, j).getValue() == -1) {
                    //petla trafila na pierwszy element - co jesli jest ich wiecej niz 1, i wypelnienie pierwszego koliduje z nastepnymi?
                    if (board.getElement(i, j).getPossibleValues().stream().filter(e -> e != -1).count() == 1) {
                        int val = board.getElement(i, j).getPossibleValues().stream().filter(e -> e != -1).collect(Collectors.toList()).get(0);
                        if (board.trySetValue(i, j, val)) {
                            didSomeOperation = true;
                        };
                    }
                }
                j++;
            }
            i++;
        }
        return didSomeOperation;
    }

    private List<Integer> findPossibilities() {
        List<Integer> possibilities = new ArrayList<>();
        int theLowestPossibility = findTheLowestPossibilitiesQuantity();
        boolean notFoundYet = true;
        int j = 0;
        while (j < board.getRowsQuantity() && notFoundYet) {
            int i = 0;
            while (i < board.getRowsQuantity() && notFoundYet) {
                if (board.getElement(i, j).getValue() == EMPTY && board.getElement(i, j).countPossibilities() == theLowestPossibility) {
                    notFoundYet = false;
                    board.getElement(i, j).getPossibleValues().stream().filter(e -> e != EMPTY).forEach(possibilities::add);
                }
                i++;
            }
            j++;
        }
        return possibilities;
    }

    private int findTheLowestPossibilitiesQuantity() {
        int theLowestPossibility = 10;
        for (int j = 0; j < board.getColumnsQuantity(); j++) {
            for (int i = 0; i < board.getRowsQuantity(); i++) {
                if (board.getElement(i, j).getValue() == EMPTY && board.getElement(i, j).countPossibilities() < theLowestPossibility) {
                    theLowestPossibility = board.getElement(i, j).countPossibilities();
                }
            }
        }
        return theLowestPossibility;
    }

    private void soutValues(SudokuBoard board) {
        System.out.println("WPROWADZONA TABLICA:");
        for (int j = 0; j < board.getColumnsQuantity(); j++) {
            for (int i = 0; i < board.getRowsQuantity(); i++) {
                if (board.getElement(i, j).getValue() != -1) {
                    System.out.print(board.getElement(i, j).getValue()+" ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private void soutPossibilities(SudokuBoard board) {
        System.out.println("WYPISUJĘ ILOSCI LICZB MOZLIWYCH DO WPROWADZENIA:");
        for (int j = 0; j < board.getColumnsQuantity(); j++) {
            for (int i = 0; i < board.getRowsQuantity(); i++) {
                if (board.getElement(i, j).getValue() == EMPTY) {
                    System.out.print(board.getElement(i, j).getPossibleValues().stream().filter(e -> e != EMPTY).count()+" ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
