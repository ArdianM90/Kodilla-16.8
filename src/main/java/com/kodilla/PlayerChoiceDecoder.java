package com.kodilla;

public class PlayerChoiceDecoder {
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
        if (playerChoice.trim().length() != 5)
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
