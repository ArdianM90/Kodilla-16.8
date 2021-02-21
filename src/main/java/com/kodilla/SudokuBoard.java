package com.kodilla;

import com.kodilla.prototype.Prototype;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SudokuBoard extends Prototype {
    private static final int ROWS = 9;
    private static final int COLUMNS = 9;
    private static final int EMPTY = -1;
    private List<List<SudokuElement>> rows = new ArrayList<>();

    public SudokuBoard() {
        for (int row = 0; row < ROWS; row++) {
            List<SudokuElement> singleRow = new ArrayList<>();
            for (int col = 0; col < COLUMNS; col++) {
                singleRow.add(new SudokuElement(row, col));
            }
            rows.add(singleRow);
        }
    }

    public SudokuBoard(SudokuBoard entryBoard) {
        for (int row = 0; row < ROWS; row++) {
            List<SudokuElement> singleColumn = new ArrayList<>();
            for (int col = 0; col < COLUMNS; col++) {
                SudokuElement newElement = new SudokuElement(row, col);
                newElement.setValue(entryBoard.getElement(row, col).getValue());
                newElement.setPossibleValues(entryBoard.getElement(row, col).getPossibleValues());
                singleColumn.add(newElement);
            }
            rows.add(singleColumn);
        }
    }

    public boolean trySetValue(int row, int col, int value) {
        if (getElement(row, col).isNotEmpty() || !getElement(row, col).setValue(value)) {
            return false;
        }
        impossibleValuesSetter(row, col, value);
        return true;
    }

    private void impossibleValuesSetter(int row, int col, int value) {
        for (int i = 0; i < 9; i++) {
            try {
                getElement(i, col).setThisValueImpossible(value);
            }
            catch (IndexOutOfBoundsException e) {
                int[] grids = handleOutOfBoardException(i, col);
                getElement(grids[0], grids[1]).setThisValueImpossible(value);
            }
            try {
                getElement(row, i).setThisValueImpossible(value);
            }
            catch (IndexOutOfBoundsException e) {
                int[] grids = handleOutOfBoardException(row, i);
                getElement(grids[0], grids[1]).setThisValueImpossible(value);
            }
        }
        setPossibilitiesInBlock(false, row, col, value);
    }

    private void setOldValuePossible(int row, int col, int oldValue) {
        for (int i = 0; i < 9; i++) {
            try {
                getElement(i, col).setThisValuePossible(oldValue);
            }
            catch (IndexOutOfBoundsException e) {
                int[] grids = handleOutOfBoardException(i, col);
                getElement(grids[0], grids[1]).setThisValuePossible(oldValue);
            }
            try {
                getElement(row, i).setThisValuePossible(oldValue);
            }
            catch (IndexOutOfBoundsException e) {
                int[] grids = handleOutOfBoardException(row, i);
                getElement(grids[0], grids[1]).setThisValuePossible(oldValue);
            }
        }
        setPossibilitiesInBlock(true, row, col, oldValue);
    }

    private void setPossibilitiesInBlock(boolean setPossible, int elementRow, int elementCol, int value) {
        int blockRow = elementRow/3;
        int blockCol = elementCol/3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int elementX = blockRow*3 + j;
                int elementY = blockCol*3 + i;
                if (setPossible) {
                    getElement(elementX, elementY).setThisValuePossible(value);
                } else {
                    getElement(elementX, elementY).setThisValueImpossible(value);
                }
            }
        }
    }

    private int[] handleOutOfBoardException(int row, int col) {
        int newRow = row;
        int newCol = col;
        if (row < 0) {
            newRow = 0;
        } else if (row > COLUMNS-1) {
            newRow = COLUMNS-1;
        }
        if (col < 0) {
            newCol = 0;
        } else if (col > ROWS-1) {
            newCol = ROWS-1;
        }
        return new int[]{newRow, newCol};
    }

    public boolean hasObviousValue() {
        boolean foundObviousVal = false;
        int row = 0;
        while (row < ROWS && !foundObviousVal) {
            int col = 0;
            while (col < COLUMNS && !foundObviousVal) {
                foundObviousVal = rows.get(row).get(col).countPossibilities() == 1;
                col++;
            }
            row++;
        }
        return foundObviousVal;
    }

    public boolean isFilled() {
        boolean boardIsFilled = true;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (rows.get(row).get(col).getValue() == EMPTY) {
                    boardIsFilled = false;
                }
            }
        }
        return boardIsFilled;
    }

    public SudokuBoard getCopy() throws CloneNotSupportedException {
        SudokuBoard boardCopy = (SudokuBoard)super.clone();
        boardCopy.rows = new ArrayList<>();
        for (int row = 0; row < ROWS; row++) {
            List<SudokuElement> singleRowCopy = new ArrayList<>();
            for (int col = 0; col < COLUMNS; col++) {
                SudokuElement newElementCopy = new SudokuElement(row, col);
                newElementCopy.setValue(rows.get(row).get(col).getValue());
                newElementCopy.setPossibleValues(rows.get(row).get(col).getPossibleValues());
                singleRowCopy.add(newElementCopy);
            }
            boardCopy.rows.add(singleRowCopy);
        }
        return boardCopy;
    }

    public int[][] getValuesArray() {
        int[][] valuesArray = new int[ROWS][COLUMNS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                valuesArray[row][col] = getElement(row, col).getValue();
            }
        }
        return valuesArray;
    }

    public SudokuElement getElement(int row, int col) {
        return rows.get(row).get(col);
    }

    public int getColumnsQuantity() {
        return COLUMNS;
    }

    public int getRowsQuantity() {
        return ROWS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuBoard that = (SudokuBoard) o;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (!rows.get(row).get(col).equals(that.rows.get(row).get(col))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        for (int row = 0; row < COLUMNS; row++) {
            for (int col = 0; col < ROWS; col++) {
                hash = hash * Objects.hash(rows.get(row).get(col));
            }
        }
        return hash;
    }
}
