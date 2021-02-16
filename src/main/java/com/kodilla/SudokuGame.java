package com.kodilla;

import java.util.Scanner;

public class SudokuGame {
    private Scanner input = new Scanner(System.in);
    private EntryDataCreator entryDataCreator = new EntryDataCreator();
    protected SudokuBoard board;

    public void play() {
        board = new SudokuBoard();
        PlayerChoiceDecoder choiceDecoder = new PlayerChoiceDecoder();
        InputConsole inputConsole = new InputConsole();
        ParamsCollector paramsCollector = new ParamsCollector();
        String playerChoice = "";
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
