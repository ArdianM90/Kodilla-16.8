package com.kodilla;

import java.util.ArrayList;
import java.util.List;

public class SudokuBoard {
    private final int COLUMNS = 9;
    private final int ROWS = 9;
    private List<List<SudokuElement>> columns = new ArrayList<>();
    //DO ZROBIENIA: utworzyć klasę possibilitiesSetter() i przeniesc metody do niej

    public SudokuBoard() {
        for (int i = 0; i < COLUMNS; i++) {
            List<SudokuElement> singleColumn = new ArrayList<>();
            for (int j = 0; j < ROWS; j++) {
                singleColumn.add(new SudokuElement());
            }
            columns.add(singleColumn);
        }
    }

    public boolean trySetValue(int x, int y, int value) {
        if (getElement(x, y).valueIsSet()) {
            int oldValue = getElement(x, y).getValue();
            if (!getElement(x, y).setValue(value))
                return false;
            setOldValuePossible(x, y, oldValue);
        } else {
            if (!getElement(x, y).setValue(value))
                return false;
        }
        impossibleValuesSetter(x, y, value);
        return true;
    }

    private void impossibleValuesSetter(int x, int y, int value) {
        for (int i = 0; i < 9; i++) {
            try {
                getElement(i, y).setThisValueImpossible(value);
            }
            catch (IndexOutOfBoundsException e) {
                int[] grids = handleOutOfBoardException(i, y);
                getElement(grids[0], grids[1]).setThisValueImpossible(value);
            }
            try {
                getElement(x, i).setThisValueImpossible(value);
            }
            catch (IndexOutOfBoundsException e) {
                int[] grids = handleOutOfBoardException(x, i);
                getElement(grids[0], grids[1]).setThisValueImpossible(value);
            }
        }
        setPossibilitiesInBLock(false, x, y, value);
    }

    private void setOldValuePossible(int x, int y, int oldValue) {
        for (int i = 0; i < 9; i++) {
            try {
                getElement(i, y).setThisValuePossible(oldValue);
            }
            catch (IndexOutOfBoundsException e) {
                int[] grids = handleOutOfBoardException(i, y);
                getElement(grids[0], grids[1]).setThisValuePossible(oldValue);
            }
            try {
                getElement(x, i).setThisValuePossible(oldValue);
            }
            catch (IndexOutOfBoundsException e) {
                int[] grids = handleOutOfBoardException(x, i);
                getElement(grids[0], grids[1]).setThisValuePossible(oldValue);
            }
        }
        setPossibilitiesInBLock(true, x, y, oldValue);
    }

    private void setPossibilitiesInBLock(boolean possibility, int x, int y, int value) {
        int blockModifierX = (int)Math.ceil((double)x/3)-1;
        int blockModifierY = (int)Math.ceil((double)y/3)-1;
        int[] xModifier = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] yModifier = {1, 1, 1, 0, 0, -1, -1, -1};
        for (int i = 0; i < xModifier.length; i++) {
            int gridX = x+(blockModifierX*3)+xModifier[i];
            int gridY = y+(blockModifierY*3)+yModifier[i];
            try {
                if (possibility) {
                    getElement(gridX, gridY).setThisValuePossible(value);
                } else {
                    getElement(gridX, gridY).setThisValueImpossible(value);
                }
            }
            catch (IndexOutOfBoundsException e) {
                int[] grids = handleOutOfBoardException(gridX, gridY);
                if (possibility) {
                    getElement(grids[0], grids[1]).setThisValuePossible(value);
                } else {
                    getElement(grids[0], grids[1]).setThisValueImpossible(value);
                }
            }
        }
    }

    private int[] handleOutOfBoardException(int x, int y) {
        int newX = x;
        int newY = y;
        if (x < 0) {
            newX = 0;
        } else if (x > COLUMNS-1) {
            newX = COLUMNS-1;
        }
        if (y < 0) {
            newY = 0;
        } else if (y > ROWS-1) {
            newY = ROWS-1;
        }
        return new int[]{newX, newY};
    }

    public int[][] getValuesArray() {
        int[][] valuesArray = new int[COLUMNS][ROWS];
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                valuesArray[i][j] = getElement(i, j).getValue();
            }
        }
        return valuesArray;
    }

    public SudokuElement getElement(int x, int y) {
        return columns.get(x).get(y);
    }

    public int getColumnsQuantity() {
        return COLUMNS;
    }

    public int getRowsQuantity() {
        return ROWS;
    }
}
