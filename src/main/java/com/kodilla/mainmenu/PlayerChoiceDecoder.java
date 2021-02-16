package com.kodilla.mainmenu;

public class PlayerChoiceDecoder {
    public boolean manualOrRandomNotCorrect(String playerChoice) {
        playerChoice = playerChoice.trim();
        if (playerChoice.trim().length() != 1)
            return true;
        if (playerChoice.equals("1") || playerChoice.equals("2"))
            return false;
        else
            return true;
    }

    public boolean howManyRandomNotCorrect(String playerChoice) {
        playerChoice = playerChoice.trim();
        if (playerChoice.length() < 1 || playerChoice.length() > 2)
            return true;
        int number;
        try {
            number = Integer.parseInt(playerChoice);
        } catch (NumberFormatException nfe) {
            return true;
        }
        if (number < 1 || number > 80)
            return true;
        else
            return false;
    }

    public int getX(String playerChoice) {
        return Character.getNumericValue(playerChoice.charAt(0))-1;
    }

    public int getY(String playerChoice) {
        return Character.getNumericValue(playerChoice.charAt(2))-1;
    }

    public int getVal(String playerChoice) {
        return Character.getNumericValue(playerChoice.charAt(4));
    }

    public boolean entryIsCorrect(String playerChoice) {
        playerChoice = playerChoice.trim();
        if (playerChoice.length() != 5)
            return false;
        if (playerChoice.charAt(1) != ',' || playerChoice.charAt(3) != ',')
            return false;
        if (!Character.isDigit(playerChoice.charAt(0)) || !Character.isDigit(playerChoice.charAt(2)) || !Character.isDigit(playerChoice.charAt(4)))
            return false;
        if (playerChoice.charAt(0) == '0' || playerChoice.charAt(2) == '0' || playerChoice.charAt(4) == '0')
            return false;
        return true;
    }
}
