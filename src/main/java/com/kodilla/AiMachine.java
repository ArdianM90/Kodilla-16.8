package com.kodilla;

import com.kodilla.exception.ElementNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AiMachine {
    private static final int EMPTY = -1;
    private SudokuBoard board;
    private PrevMovesRepo boardRepository = new PrevMovesRepo();
    private final SoutMachine display = new SoutMachine();

    public AiMachine(SudokuBoard getBoard) {
        this.board = getBoard;
    }

    public void fillSudoku() {
        System.out.println();
        System.out.println("====ROZPOCZYNAM WYPELNIANIE TABLICY====");
        System.out.println("=======================================");
        System.out.println();
        boolean notFilledYet = true;
        int loopCounter = 0;
        while (notFilledYet && loopCounter < 100) {
            if (board.hasObviousValue()) {
                tryPutObviousValue();
            } else {
                try {
                    SudokuElement theElement = findElementToGuess();
                    List<Integer> grids = new ArrayList<>();
                    grids.add(theElement.getRow());
                    grids.add(theElement.getCol());
                    int guessedValue = theElement.getPossibleValues().stream()
                            .mapToInt(e -> e)
                            .filter(e -> e != EMPTY)
                            .min()
                            .orElseThrow(IllegalArgumentException::new);
                    boardRepository.save(board, grids, guessedValue);
                    System.out.println("Proba wstawienia przez losowanie: val "+guessedValue+", wiersz "+(grids.get(0)+1)+", kolumna "+(grids.get(1)+1)+".");
                    board.trySetValue(grids.get(0), grids.get(1), guessedValue);
                }
                catch (ElementNotFoundException e) {
                    try {
                        board = boardRepository.loadPrevBoard();
                    } catch (CloneNotSupportedException e2) {
                        System.out.println("ERROR - blad ladowania wczesniejszej wersji planszy: "+e2.getMessage());
                    }
                }
            }
            notFilledYet = !board.isFilled();
            display.soutValues(board);
            display.soutPossibilities(board);
            System.out.println("=============================");
            loopCounter++;
        }
        if (board.isFilled()) {
            System.out.println("Sudoku wypelnione w "+ loopCounter +" petlach.");
        } else {
            System.out.println("Zrobiłem "+ loopCounter +" petli i sie zmeczylem.");
        }
        System.out.println();
        display.soutValues(board);
        display.soutPossibilities(board);
    }

    private SudokuElement findElementToGuess() throws ElementNotFoundException {
        int theLowestPoss = findTheLowestPossibilitiesQuantity();
        return findFirstElementWithPossibility(theLowestPoss);
    }

    private SudokuElement findFirstElementWithPossibility(int theLowestPoss) throws ElementNotFoundException {
        boolean notFoundYet = true;
        int row = 0;
        while (row < board.getRowsQuantity() && notFoundYet) {
            int col = 0;
            while (col < board.getRowsQuantity() && notFoundYet) {
                SudokuElement theElement = board.getElement(row, col);
                if (theElement.isEmpty() && theElement.countPossibilities() == theLowestPoss) {
                    notFoundYet = false;
                    return theElement;
                }
                col++;
            }
            row++;
        }
        throw new ElementNotFoundException();
    }

    private boolean tryPutObviousValue() {
        boolean didSomeOperation = false;
        int row = 0;
        while (row < board.getRowsQuantity()) {
            int col = 0;
            while (col < board.getColumnsQuantity()) {
                SudokuElement theElement = board.getElement(row, col);
                if (theElement.getValue() == EMPTY && theElement.haveSinglePossibility()) {
                    int val = theElement.getPossibleValues().stream()
                            .filter(e -> e != EMPTY)
                            .collect(Collectors.toList())
                            .get(0);
                    didSomeOperation = board.trySetValue(row, col, val);
                    if (didSomeOperation) {
                        System.out.println("Wstawiono oczywistosc: val "+val+", wiersz "+(row+1)+", kolumna "+(col+1)+".");
                    }
                }
                col++;
            }
            row++;
        }
        return didSomeOperation;
    }

//    private List<Integer> theSmallestPossibilitiesList() {
//        List<Integer> possibilities = new ArrayList<>();
//        int theLowestQuantity = findTheLowestPossibilitiesQuantity();
//        boolean notFoundYet = true;
//        int j = 0;
//        while (j < board.getRowsQuantity() && notFoundYet) {
//            int i = 0;
//            while (i < board.getRowsQuantity() && notFoundYet) {
//                if (board.getElement(i, j).getValue() == EMPTY && board.getElement(i, j).countPossibilities() == theLowestQuantity) {
//                    notFoundYet = false;
//                    board.getElement(i, j).getPossibleValues().stream().filter(e -> e != EMPTY).forEach(possibilities::add);
//                }
//                i++;
//            }
//            j++;
//        }
//        return possibilities;
//    }

//    private List<Integer> guessedElementGrids() {
//        List<Integer> grids = new ArrayList<>();
//        int theLowestQuantity = findTheLowestPossibilitiesQuantity();
//        boolean notFoundYet = true;
//        int j = 0;
//        while (j < board.getRowsQuantity() && notFoundYet) {
//            int i = 0;
//            while (i < board.getRowsQuantity() && notFoundYet) {
//                if (board.getElement(i, j).getValue() == EMPTY && board.getElement(i, j).countPossibilities() == theLowestQuantity) {
//                    notFoundYet = false;
//                    grids.add(i);
//                    grids.add(j);
//                }
//                i++;
//            }
//            j++;
//        }
//        return grids;
//    }

    private int findTheLowestPossibilitiesQuantity() {
        int theLowestPossibility = 10;
        for (int row = 0; row < board.getColumnsQuantity(); row++) {
            for (int col = 0; col < board.getRowsQuantity(); col++) {
                if (board.getElement(row, col).getValue() == EMPTY && board.getElement(row, col).countPossibilities() < theLowestPossibility) {
                    theLowestPossibility = board.getElement(row, col).countPossibilities();
                }
            }
        }
        return theLowestPossibility;
    }
}
