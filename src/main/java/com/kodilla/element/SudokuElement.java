package com.kodilla.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SudokuElement {
    private static final int EMPTY = -1;
    private static final int POSSIBILITIES_QUANTITY = 9;
    private int value;
    private List<Integer> possibleValues = new ArrayList<>();

    public SudokuElement() {
        this.value = EMPTY;
        for (int i = 1; i <= POSSIBILITIES_QUANTITY; i++) {
            possibleValues.add(i);
        }
    }

    public void removeMinPossibility() {
        System.out.print("Mozliwosci PRZED usunieciem min: ");
        possibleValues.forEach(e -> System.out.print(e+", "));
        System.out.println();
        int min = possibleValues.stream()
                .filter(e -> e > EMPTY)
                .mapToInt(e -> e)
                .min()
                .orElse(EMPTY);
        try {
            possibleValues.set(possibleValues.indexOf(min), EMPTY);
            System.out.println("Usunalem z mozliwosci wartosc "+min+".");
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Blad. Brak mozliwosci do usuwania.");
            System.out.println(possibleValues);
        }
        System.out.print("Mozliwosci PO usunieciu min: ");
        possibleValues.forEach(e -> System.out.print(e+", "));
        System.out.println();
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

    public boolean haveSinglePossibility() {
        return possibleValues.stream().filter(e -> e != EMPTY).count() == 1;
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

    public void setPossibleValues(List<Integer> entryPossibilities) {
        for (int i = 0; i < POSSIBILITIES_QUANTITY; i++) {
            possibleValues.set(i, entryPossibilities.get(i));
        }
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
