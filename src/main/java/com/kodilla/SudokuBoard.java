package com.kodilla;

import java.util.ArrayList;
import java.util.List;

public class SudokuBoard {
    private List<List<SudokuElement>> columns = new ArrayList<>();

    public SudokuBoard() {
        for (int i = 0; i < 9; i++) {
            List<SudokuElement> singleColumn = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
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
                int[] grids = handleOutOfBoardExcetion(i, y);
                getElement(grids[0], grids[1]).setThisValueImpossible(value);
            }
            try {
                getElement(x, i).setThisValueImpossible(value);
            }
            catch (IndexOutOfBoundsException e) {
                int[] grids = handleOutOfBoardExcetion(x, i);
                getElement(grids[0], grids[1]).setThisValueImpossible(value);
            }
        }
        int[] xModifier = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] yModifier = {1, 1, 1, 0, 0, -1, -1, -1};
        for (int i = 0; i < xModifier.length; i++) {
            int gridX = x+xModifier[i];
            int gridY = y+yModifier[i];
            try {
                getElement(gridX, gridY).setThisValueImpossible(value);
            }
            catch (IndexOutOfBoundsException e) {
                int[] grids = handleOutOfBoardExcetion(gridX, gridY);
                getElement(grids[0], grids[1]).setThisValueImpossible(value);
            }
        }
    }

    private void setOldValuePossible(int x, int y, int oldValue) {
        System.out.println("Setting old values as possible");
        for (int i = 0; i < 9; i++) {
            try {
                getElement(i, y).setThisValuePossible(oldValue);
            }
            catch (IndexOutOfBoundsException e) {
                int[] grids = handleOutOfBoardExcetion(i, y);
                getElement(grids[0], grids[1]).setThisValuePossible(oldValue);
            }
            try {
                getElement(x, i).setThisValuePossible(oldValue);
            }
            catch (IndexOutOfBoundsException e) {
                int[] grids = handleOutOfBoardExcetion(x, i);
                getElement(grids[0], grids[1]).setThisValuePossible(oldValue);
            }
        }
        int[] xModifier = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] yModifier = {1, 1, 1, 0, 0, -1, -1, -1};
        for (int i = 0; i < xModifier.length; i++) {
            int gridX = x+xModifier[i];
            int gridY = y+yModifier[i];
            try {
                getElement(gridX, gridY).setThisValuePossible(oldValue);
            }
            catch (IndexOutOfBoundsException e) {
                int[] grids = handleOutOfBoardExcetion(gridX, gridY);
                getElement(grids[0], grids[1]).setThisValuePossible(oldValue);
            }
        }
    }

    private int[] handleOutOfBoardExcetion(int x, int y) {
        int newX = x;
        int newY = y;
        if (x < 0) {
            newX = 0;
        } else if (x > 8) {
            newX = 8;
        }
        if (y < 0) {
            newY = 0;
        } else if (y > 8) {
            newY = 8;
        }
        return new int[]{newX, newY};
    }

    public int[][] getValuesArray() {
        int[][] valuesArray = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                valuesArray[i][j] = getElement(i, j).getValue();
            }
        }
        return valuesArray;
    }

    public SudokuElement getElement(int x, int y) {
        return columns.get(x).get(y);
    }
}
