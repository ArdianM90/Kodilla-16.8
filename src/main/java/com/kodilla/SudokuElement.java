package com.kodilla;

import java.util.ArrayList;
import java.util.List;

public class SudokuElement {
    private int value;
    private List<Integer> possibleValues = new ArrayList<>();

    public SudokuElement(int value) {
        this.value = value;
        for (int i = 0; i < 9; i++) {
            possibleValues.add(i, i+1);
        }
    }

    public boolean setValue(int value) {
        if (!possibleValues.contains(value))
            return false;
        this.value = value;
        int valIndex = possibleValues.indexOf(value);
        possibleValues.set(valIndex, null);
        return true;
    }

    public int getValue() {
        return value;
    }
}
