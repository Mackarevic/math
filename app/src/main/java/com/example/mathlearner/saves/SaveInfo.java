package com.example.mathlearner.saves;

public class SaveInfo {

    private final int number1, number2, userInput;
    private final SaveType saveType;

    public SaveInfo(int number1, int number2, int userInput, SaveType saveType) {
        this.number1 = number1;
        this.number2 = number2;
        this.userInput = userInput;
        this.saveType = saveType;
    }

    public SaveType getSaveType() {
        return saveType;
    }

    public int getNumber1() {
        return number1;
    }

    public int getNumber2() {
        return number2;
    }

    public int getUserInput() {
        return userInput;
    }
}
