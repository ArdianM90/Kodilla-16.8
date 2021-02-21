package com.kodilla;

import java.util.ArrayList;
import java.util.List;

public class PrevMovesRepo {
    private static final int EMPTY = -1;
    private List<ElementDeepCopy> prevMovesList = new ArrayList<>();

    public void save(SudokuBoard board, List<Integer> grids, int value) {
        System.out.println("Proba zapisu");
        try {
            SudokuBoard boardCopy = board.getCopy();
            boardCopy.getElement(grids.get(0), grids.get(1)).removePossibility(value);
            if (boardCopy.getElement(grids.get(0), grids.get(1)).countPossibilities() > 0) {
                prevMovesList.add(new ElementDeepCopy(boardCopy, grids, value));
                System.out.println("Zapisuje do REPO elemnt nr "+prevMovesList.size()+": val "+value+", grids "+grids.get(0)+", "+grids.get(1));
                System.out.println("Ilość pozostalych mozliwosci we wstawianym elemencie: "+prevMovesList.get(prevMovesList.size()-1).getPossibilitiesQuantity());
                System.out.println("========================ILOSC ZAPISOW: "+prevMovesList.size()+"=======================================");
                System.out.println();
            } else {
                System.out.println("NIE zapisuje do REPO.");
                System.out.println("Ilość pozostalych mozliwosci we wstawianym elemencie: "+boardCopy.getElement(grids.get(0), grids.get(1)).countPossibilities());
                System.out.println("========================ILOSC ZAPISOW: "+prevMovesList.size()+"=======================================");
                System.out.println();
            }
        }
        catch (CloneNotSupportedException e) {
            System.out.println("BLAD klonowania planszy: "+e.getMessage());
        }
    }

    public SudokuBoard loadPrevBoard() throws CloneNotSupportedException {
        ElementDeepCopy lastSave = prevMovesList.get(prevMovesList.size()-1);
        SudokuBoard savedBoard = lastSave.getBoard().getCopy();
        if (savedBoard.hasObviousValue()) {
            prevMovesList.remove(prevMovesList.size()-1);
        }
        return savedBoard;
    }

    public boolean setNextPossibleValue() {
        ElementDeepCopy lastMove = prevMovesList.get(prevMovesList.size()-1);
        return lastMove.setNextGuessedValue();
    }

    public ElementDeepCopy getLastSave() {
        return prevMovesList.get(prevMovesList.size()-1);
    }

//    public void removeLastSaveMinPossibilityAt(List<Integer> grids) {
//        while (getLastSave().getPossibilitiesQuantity() <= 1) {
//            removeLastSave();
//        }
//        getLastSave().removeMinPossibility(grids);
//        getLastSave().setMinPossibilityNotPossibleAt(grids);
//    }

    public void removeLastSave() {
        System.out.println();
        System.out.println("======REMOVING LAST SAVE, DLUGOSC LISTY PRZED: "+prevMovesList.size()+"======");
        prevMovesList.remove(prevMovesList.size()-1);
        System.out.println("======DLUGOSC LISTY PO: "+prevMovesList.size()+"======");
        System.out.println();
    }

//    public void updateLastSaveNextPossibility() {
//        SudokuBoard board = this.getLastSave().getBoard();
//        List<Integer> grids = this.getLastSave().getGrids();
//        System.out.println("Usuwam min mozliwosc z elementu na wsp "+(grids.get(0)+1)+", "+(grids.get(1)+1)+": val "+board.getElement(grids.get(0), grids.get(1)).getValue()+".");
//        board.getElement(grids.get(0), grids.get(1)).removeMinPossibility();
//        int guessedValue = this.getLastSave().getGuessedValue();
//        this.removeLastSave();
//        this.save(board, grids, guessedValue);
//    }
}
