package com.kodilla;

import java.util.List;

public class ElementDeepCopy {
    private SudokuBoard board;
    private List<Integer> grids;
    private int guessedValue;

    public ElementDeepCopy(SudokuBoard board, List<Integer> grids, int guessedValue) {
        this.board = board;
        this.grids = grids;
        this.guessedValue = guessedValue;
    }

//    public void setMinPossibilityNotPossibleAt(List<Integer> grids) {
//        System.out.print("MOZLIWOSCI PRZED: ");
//        board.getElement(grids.get(0), grids.get(1)).getPossibleValues().forEach(e -> System.out.print(e+", "));
//        System.out.println();
//        board.getElement(grids.get(0), grids.get(1)).removeMinPossibility();
//        System.out.print("MOZLIWOSCI PO: ");
//        board.getElement(grids.get(0), grids.get(1)).getPossibleValues().forEach(e -> System.out.print(e+", "));
//        System.out.println();
//    }

    public boolean setNextGuessedValue() {
        board.getElement(grids.get(0), grids.get(1)).setThisValueImpossible(guessedValue);
        List<Integer> possibilities = board.getElement(grids.get(0), grids.get(1)).getPossibleValues();
        if (possibilities.size() > 0) {
            guessedValue = possibilities
                    .stream()
                    .mapToInt(e -> e)
                    .min()
                    .orElse(-1);
            return true;
        } else {
            return false;
        }
    }

    public SudokuBoard getBoard() {
        return this.board;
    }

    public List<Integer> getGrids() {
        return grids;
    }

    public int getPossibilitiesQuantity() {
        return board.getElement(grids.get(0), grids.get(1)).countPossibilities();
    }

    public int getGuessedValue() {
        return guessedValue;
    }
}
