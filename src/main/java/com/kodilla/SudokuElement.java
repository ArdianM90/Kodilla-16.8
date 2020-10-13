package com.kodilla;

import java.util.ArrayList;
import java.util.List;

public class SudokuElement {
    public static int EMPTY = -1;
    private int value;
    private List<Integer> possibleValues = new ArrayList<>();

    public SudokuElement() {
        this.value = EMPTY;
        for (int i = 1; i < 10; i++) {
            possibleValues.add(i);
        }
    }

    public boolean setValue(int value) {
        if (!possibleValues.contains(value))
            return false;
        this.value = value;
        return true;
    }

    public void setThisValuePossible(int oldValue) {
        int valIndex = oldValue-1;
        possibleValues.set(valIndex, oldValue);
    }

    public boolean setThisValueImpossible(int value) {
        if (!possibleValues.contains(value))
            return false;
        int valIndex = possibleValues.indexOf(value);
        possibleValues.set(valIndex, EMPTY);
        return true;
    }

    public int getValue() {
        return value;
    }

    public boolean valueIsSet() {
        return value != EMPTY;
    }
}
