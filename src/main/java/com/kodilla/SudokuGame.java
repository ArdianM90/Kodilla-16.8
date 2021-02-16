package com.kodilla;

import com.kodilla.mainmenu.EntryDataCreator;
import com.kodilla.mainmenu.InputConsole;

public class SudokuGame {
    private final InputConsole inputConsole = new InputConsole();
    private final EntryDataCreator entryDataCreator = new EntryDataCreator();
    protected SudokuBoard board;

    public void play() {
        board = new SudokuBoard();
        if (inputConsole.goManual()) {
            board = entryDataCreator.manual(board);
        } else {
            int inputDigitsCount = inputConsole.howManyRnd();
            board = entryDataCreator.create(inputDigitsCount, board);
        }
        AiMachine aiMachine = new AiMachine(board);
        aiMachine.fillSudoku();
    }
}
