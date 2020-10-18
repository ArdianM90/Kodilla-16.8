package com.kodilla;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AiMachine {
    private static final int EMPTY = -1;
    private SudokuBoard board;
    private PrevMovesRepo boardRepository = new PrevMovesRepo();
    private Random rnd = new Random();

    public AiMachine(SudokuBoard board) {
        this.board = board;
    }

    public void fillSudoku() {
        int loopCounter = 0;
        boolean notFilledYet = true;
        boolean noNeedToGuess = true;
        boolean putGuessSuccess = true;
        while (notFilledYet && putGuessSuccess) {
            if (noNeedToGuess) { //NORMALNE WYPELNIANIE JEDYNEK
                noNeedToGuess = loopOverAllElements();
            } else { //ZGADYWANIE
                List<Integer> possibilities = findPossibilities();
                System.out.println("Possibilites size: "+possibilities.size());
                try {
                    int guessedValueIndex = rnd.nextInt(possibilities.size());
                    int guessedValue = possibilities.get(guessedValueIndex);
                    System.out.println("Próbuję wstawić wartość "+guessedValue+".");
                    //zapis stanu tablicy Sudoku
                        //- usuniecie 'guessedValue' z mozliwosci w zapisanej tablicy
                    putGuessSuccess = tryPutGuessedValue(guessedValue, possibilities.size());
                    if (putGuessSuccess) {
                        System.out.println("=============================");
                        System.out.println("Wstawiłem wartość "+guessedValue+".");
                        System.out.println("=============================");
                        noNeedToGuess = true;
                    } else {
                        System.out.println("=============================");
                        System.out.println("Nie udało się wstawic wartosci "+guessedValue+".");
                        System.out.println("=============================");
                    }
                }
                catch (IllegalArgumentException e) {
                    System.out.println("Błąd losowania wartości.");
                    System.out.println("Tablica SUDOKU nie jest możliwa do wykonania.");
                    System.exit(0);
                    //wczytanie stanu poprzedniej tablicy Sudoku
                        //wylosowanie nowej wartosci 'guessedValue'
                }
            }
            notFilledYet = !board.isFilled();
            loopCounter++;
            soutValues(board);
            soutPossibilities(board);
        }
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

    private boolean tryPutGuessedValue(int guessedValue, int possibilitiesQuantity) {
        boolean put = false;
        boolean notFoundYet = true;
        int j = 0;
        while (j < board.getRowsQuantity() && notFoundYet) {
            int i = 0;
            while (i < board.getRowsQuantity() && notFoundYet) {
                if (board.getElement(i, j).getValue() == EMPTY && board.getElement(i, j).countPossibilities() == possibilitiesQuantity) {
                    notFoundYet = false;
                    System.out.println("Próba PUT: x["+i+"], y["+j+"], val["+guessedValue+"]");
                    put = board.trySetValue(i, j, guessedValue);
                }
                i++;
            }
            j++;
        }
        return put;
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
                    if (board.getElement(i, j).getValue() == EMPTY && board.getElement(i, j).getPossibleValues().size() == 0) {
                        System.out.print("X ");
                    } else {
                        System.out.print("- ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
