package com.kodilla;

import com.kodilla.element.SudokuElement;

import java.util.ArrayList;
import java.util.Arrays;
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
        System.out.println("=======================================");
        int loopCounter = 0;
        boolean notFilledYet = true;
        boolean noNeedToGuess = true;
        int testCounter = 0;
        while (notFilledYet && testCounter < 1000) {
            if (noNeedToGuess) {
                noNeedToGuess = tryPutObviousValue();
            } else {
                List<Integer> possibilities = theSmallestPossibilitiesList();
                List<Integer> grids = guessedElementGrids();
                try {
                    int guessedValue = possibilities
                            .stream()
                            .mapToInt(e -> e)
                            .min()
                            .orElseThrow(IllegalArgumentException::new);
                    try {
                        boardRepository.save(board.getCopy(), grids, guessedValue);
                    } catch (CloneNotSupportedException e) {
                        System.out.println(e);
                    }
                    noNeedToGuess = board.trySetValue(grids.get(0), grids.get(1), guessedValue);
                    System.out.println("Il pozostalych mozliwosci: "+boardRepository.getLastBoard().getElement(grids.get(0), grids.get(1)).countPossibilities());
                }
                catch (IllegalArgumentException e) {
                    System.out.println();
                    System.out.println("Błąd - brak możliwych cyfr do wstawienia. Tablica SUDOKU nie jest możliwa do wykonania.");
                    int possibilitiesQuantity = boardRepository.getLastBoard().getElement(grids.get(0), grids.get(1)).countPossibilities();
                    System.out.println("W elemencie na wsp. "+boardRepository.getLastSave().getGrids().get(0)+", "+boardRepository.getLastSave().getGrids().get(1)+", val. "+boardRepository.getLastSave().getGuessedValue()+", mam jeszcze "+boardRepository.getLastSave().getPossibilitiesQuantity()+" innych mozliwosci. Gridy: "+boardRepository.getLastSave().getGrids());
                    System.out.println(boardRepository.getLastSave().getBoard().getElement(boardRepository.getLastSave().getGrids().get(0), boardRepository.getLastSave().getGrids().get(1)).getPossibleValues());
                    if (possibilitiesQuantity > 1) {
                        System.out.println("Mam wiecej niz 1 mozliowsc -> usuwam poprzednia mozliwosc, probuje nastepnej.");
                        boardRepository.updateLastSaveNextPossibility();
                    } else {
                        System.out.println("Mam tylko 1 mozliowsc -> usuwam save i laduje poprzedni.");
                        boardRepository.removeLastSave();
                        boardRepository.updateLastSaveNextPossibility();
                    }
                    board = boardRepository.getLastBoard();
                    System.out.println("Probuje wstawic wartosc "+boardRepository.getLastSave().getGuessedValue()+" na gridy "+grids.get(0)+", "+grids.get(1)+".");
                    boolean tempSuccess = board.trySetValue(grids.get(0), grids.get(1), boardRepository.getLastSave().getGuessedValue());
                    noNeedToGuess = tempSuccess;
                    if(tempSuccess) {
                        System.out.println("Wstawianie zgadywanej wartosci zakonczone SUKCESEM.");
                    } else {
                        System.out.println("Wstawianie zgadywanej wartosci zakonczone PORAZKA.");
                    }
                }
            }
            notFilledYet = !board.isFilled();
            loopCounter++;
            display.soutValues(board);
            display.soutPossibilities(board);
            System.out.println("=============================");
            System.out.println("=============================");
            testCounter++;
        }
        if (board.isFilled()) {
            System.out.println("Sudoku wypelnione w "+loopCounter+" petlach.");
        } else {
            System.out.println("Zrobiłem "+loopCounter+" petli i sie zatrzymalem na braku oczywistych wyborow.");
        }
        System.out.println();
        display.soutValues(board);
        display.soutPossibilities(board);
    }

    private boolean tryPutObviousValue() {
        boolean didSomeOperation = false;
        int row = 0;
        while (row < board.getRowsQuantity()) {
            int col = 0;
            while (col < board.getColumnsQuantity()) {
                SudokuElement theElement = board.getElement(col, row);
                if (theElement.getValue() == EMPTY && theElement.haveSinglePossibility()) {
                    int val = theElement.getPossibleValues().stream()
                            .filter(e -> e != EMPTY)
                            .collect(Collectors.toList())
                            .get(0);
                    SudokuBoard boardSave = new SudokuBoard(board);
                    List<Integer> gridsSave = Arrays.asList(col, row);
                    didSomeOperation = board.trySetValue(col, row, val);
                    boardRepository.save(boardSave, gridsSave, val);
                }
                col++;
            }
            row++;
        }
        return didSomeOperation;
    }

    private List<Integer> theSmallestPossibilitiesList() {
        List<Integer> possibilities = new ArrayList<>();
        int theLowestQuantity = findTheLowestPossibilitiesQuantity();
        boolean notFoundYet = true;
        int j = 0;
        while (j < board.getRowsQuantity() && notFoundYet) {
            int i = 0;
            while (i < board.getRowsQuantity() && notFoundYet) {
                if (board.getElement(i, j).getValue() == EMPTY && board.getElement(i, j).countPossibilities() == theLowestQuantity) {
                    notFoundYet = false;
                    board.getElement(i, j).getPossibleValues().stream().filter(e -> e != EMPTY).forEach(possibilities::add);
                }
                i++;
            }
            j++;
        }
        return possibilities;
    }

    private List<Integer> guessedElementGrids() {
        List<Integer> grids = new ArrayList<>();
        int theLowestQuantity = findTheLowestPossibilitiesQuantity();
        boolean notFoundYet = true;
        int j = 0;
        while (j < board.getRowsQuantity() && notFoundYet) {
            int i = 0;
            while (i < board.getRowsQuantity() && notFoundYet) {
                if (board.getElement(i, j).getValue() == EMPTY && board.getElement(i, j).countPossibilities() == theLowestQuantity) {
                    notFoundYet = false;
                    grids.add(i);
                    grids.add(j);
                }
                i++;
            }
            j++;
        }
        return grids;
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
}
