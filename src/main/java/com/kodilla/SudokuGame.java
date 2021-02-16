package com.kodilla;

import com.kodilla.mainmenu.EntryDataCreator;
import com.kodilla.mainmenu.InputConsole;

public class SudokuGame {
    protected SudokuBoard board = new SudokuBoard();
    private final InputConsole inputConsole = new InputConsole();
    private final EntryDataCreator entryDataCreator = new EntryDataCreator(board);

    public void play() {
        if (inputConsole.testArray()) {
            board = entryDataCreator.testArray();
        } else {
            int inputDigitsCount = inputConsole.howManyRnd();
            board = entryDataCreator.random(inputDigitsCount);
        }
        AiMachine aiMachine = new AiMachine(board);
        aiMachine.fillSudoku();
    }
}
