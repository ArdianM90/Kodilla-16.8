package com.kodilla.mainmenu;

import java.util.Scanner;

public class InputConsole {
    private Scanner input = new Scanner(System.in);
    private PlayerChoiceDecoder choiceDecoder = new PlayerChoiceDecoder();
    private String playerChoice;

    public boolean goManual() {
        System.out.println("Wprowadz cyfre:");
        System.out.println("[1] aby wprowadzic recznie dane poczatkowe SUDOKU, lub");
        System.out.println("[2] aby losowac dane poczatkowe.");
        playerChoice = input.nextLine();
        while (choiceDecoder.manualOrRandomNotCorrect(playerChoice)) {
            System.out.println("Bledny wybor. Wprowadz cyfre:");
            System.out.println("[1] aby wprowadzic recznie dane poczatkowe SUDOKU, lub");
            System.out.println("[2] aby losowac dane poczatkowe.");
            playerChoice = input.nextLine();
        }
        if (playerChoice.equals("1"))
            return true;
        else
            return false;
    }

    public int howManyRnd() {
        System.out.println("Ile liczb poczatkowych losowac (wprowadz cyfre od 1 do 80):");
        playerChoice = input.nextLine();
        while (choiceDecoder.howManyRandomNotCorrect(playerChoice)) {
            System.out.println("Bledny wybor. Wprowadz cyfre od 1 do 80:");
            playerChoice = input.nextLine();
        }
        return Integer.parseInt(playerChoice);
    }
}
