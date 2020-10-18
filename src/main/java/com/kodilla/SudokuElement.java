package com.kodilla;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SudokuElement {
    private static final int EMPTY = -1;
    private static final int POSSIBILITIES_QUANTITY = 10;
    private int value;
    private List<Integer> possibleValues = new ArrayList<>();

    public SudokuElement() {
        this.value = EMPTY;
        for (int i = 1; i < POSSIBILITIES_QUANTITY; i++) {
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

    public List<Integer> getPossibleValues() {
        return this.possibleValues;
    }

    public int countPossibilities() {
        return (int)possibleValues.stream().filter(e -> e != EMPTY).count();
    }

    public int getValue() {
        return value;
    }

    public boolean valueIsSet() {
        return value != EMPTY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuElement that = (SudokuElement) o;
        if (value != that.getValue())
            return false;
        for (int i = 0; i < POSSIBILITIES_QUANTITY; i++) {
            if (possibleValues.get(i) != that.getPossibleValues().get(i))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value)+Objects.hash(possibleValues)*100;
    }
}
