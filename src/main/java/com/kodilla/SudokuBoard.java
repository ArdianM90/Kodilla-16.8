package com.kodilla;

import java.util.ArrayList;
import java.util.List;

public class SudokuBoard {
    public static int EMPTY = -1;
    private List<List<SudokuElement>> columns = new ArrayList<>();

    public SudokuBoard() {
        for (int i = 0; i < 9; i++) {
            List<SudokuElement> singleColumn = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                singleColumn.add(new SudokuElement(EMPTY));
            }
            columns.add(singleColumn);
            System.out.println(columns);
        }
    }

    public int[][] getValuesArray() {
        int[][] valuesArray = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                valuesArray[i][j] = columns.get(i).get(j).getValue();
            }
        }
        return valuesArray;
    }

    public void setValueAt(int x, int y, int value) {
        columns.get(x).get(y).setValue(value);
    }
}
