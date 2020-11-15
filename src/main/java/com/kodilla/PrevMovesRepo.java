package com.kodilla;

import java.util.ArrayList;
import java.util.List;

public class PrevMovesRepo {
    private static final int EMPTY = -1;
    private List<ElementDeepCopy> prevMovesList = new ArrayList<>();

    public void save(SudokuBoard board, List<Integer> grids, int guessedValue) {
        if (board.getElement(grids.get(0), grids.get(1)).countPossibilities() > 1) {
            prevMovesList.add(new ElementDeepCopy(board, grids, guessedValue));
            System.out.println("Zapisuje do REPO elemnt nr "+prevMovesList.size()+": val "+guessedValue+", grids "+grids.get(0)+", "+grids.get(1));
            System.out.println("Ilość pozostalych mozliwosci we wstawianym elemencie: "+prevMovesList.get(prevMovesList.size()-1).getPossibilitiesQuantity());
            System.out.println("======================================="+prevMovesList.size()+"=======================================");
            System.out.println();
        } else {
            System.out.println("NIE zapisuje do REPO, nie warto - ilosc mozliwosci: "+board.getElement(grids.get(0), grids.get(1)).countPossibilities());
        }
    }

    public SudokuBoard getLastBoard() {
        return new SudokuBoard(prevMovesList.get(prevMovesList.size()-1).getBoard());
    }

    public boolean setNextPossibleValue() {
        ElementDeepCopy lastMove = prevMovesList.get(prevMovesList.size()-1);
        return lastMove.setNextGuessedValue();
    }

    public ElementDeepCopy getLastSave() {
        return prevMovesList.get(prevMovesList.size()-1);
    }

    public void removeLastSaveMinPossibilityAt(List<Integer> grids) {
//        while (getLastSave().getPossibilitiesQuantity() <= 1) {
//            removeLastSave();
//        }
//        getLastSave().removeMinPossibility(grids);
        getLastSave().setMinPossibilityNotPossibleAt(grids);
    }

    public void removeLastSave() {
        System.out.println();
        System.out.println("======REMOVING LAST SAVE, DLUGOSC LISTY PRZED: "+prevMovesList.size()+"======");
        prevMovesList.remove(prevMovesList.size()-1);
        System.out.println("======DLUGOSC LISTY PO: "+prevMovesList.size()+"======");
        System.out.println();
    }

    public void updateLastSaveNextPossibility() {
        SudokuBoard board = this.getLastSave().getBoard();
        List<Integer> grids = this.getLastSave().getGrids();
        System.out.println("Usuwam min mozliwosc z elementu na wsp "+(grids.get(0)+1)+", "+(grids.get(1)+1)+": val "+board.getElement(grids.get(0), grids.get(1)).getValue()+".");
        board.getElement(grids.get(0), grids.get(1)).removeMinPossibility();
        int guessedValue = this.getLastSave().getGuessedValue();
        this.removeLastSave();
        this.save(board, grids, guessedValue);
    }
}
