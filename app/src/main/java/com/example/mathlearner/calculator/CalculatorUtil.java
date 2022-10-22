package com.example.mathlearner.calculator;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CalculatorUtil {

    private static boolean nextLine = false;
    private static boolean calculated = false;

    private static final List<Integer> lines = new ArrayList<>();
    private static final List<CalculationMethod> calculationMethods = new ArrayList<>();

    public static void clearOut(TextView textView) {
        if (textView == null)
            return;

        textView.setText("0");

        calculated = false;

        lines.clear();
        calculationMethods.clear();
    }

    public static void addNumber(int number, TextView textView) {
        if (textView == null)
            return;

        String txt = textView.getText().toString();

        switch (number) {
            case 0:
                String tempText = txt;

                if (nextLine) {
                    txt = "";
                    nextLine = false;
                }

                if (txt.isEmpty()) {
                    txt = tempText;
                    textView.setText(txt);

                    if (calculated)
                        calculated = false;

                    return;
                }

                if (calculated)
                    calculated = false;

                txt += "0";
                textView.setText(txt);
                break;
            case 1:
                if (nextLine) {
                    txt = "";
                    nextLine = false;
                }

                if (txt.equals("0") || calculated) {
                    txt = "1";
                    calculated = false;
                } else
                    txt += "1";

                textView.setText(txt);
                break;
            case 2:
                if (nextLine) {
                    txt = "";
                    nextLine = false;
                }

                if (txt.equals("0") || calculated) {
                    txt = "2";
                    calculated = false;
                } else
                    txt += "2";

                textView.setText(txt);
                break;
            case 3:
                if (nextLine) {
                    txt = "";
                    nextLine = false;
                }

                if (txt.equals("0") || calculated) {
                    txt = "3";
                    calculated = false;
                } else
                    txt += "3";

                textView.setText(txt);
                break;
            case 4:
                if (nextLine) {
                    txt = "";
                    nextLine = false;
                }

                if (txt.equals("0") || calculated) {
                    txt = "4";
                    calculated = false;
                } else
                    txt += "4";

                textView.setText(txt);
                break;
            case 5:
                if (nextLine) {
                    txt = "";
                    nextLine = false;
                }

                if (txt.equals("0") || calculated) {
                    txt = "5";
                    calculated = false;
                } else
                    txt += "5";

                textView.setText(txt);
                break;
            case 6:
                if (nextLine) {
                    txt = "";
                    nextLine = false;
                }

                if (txt.equals("0") || calculated) {
                    txt = "6";
                    calculated = false;
                } else
                    txt += "6";

                textView.setText(txt);
                break;
            case 7:
                if (nextLine) {
                    txt = "";
                    nextLine = false;
                }

                if (txt.equals("0") || calculated) {
                    txt = "7";
                    calculated = false;
                } else
                    txt += "7";

                textView.setText(txt);
                break;
            case 8:
                if (nextLine) {
                    txt = "";
                    nextLine = false;
                }

                if (txt.equals("0") || calculated) {
                    txt = "8";
                    calculated = false;
                } else
                    txt += "8";

                textView.setText(txt);
                break;
            case 9:
                if (nextLine) {
                    txt = "";
                    nextLine = false;
                }

                if (txt.equals("0") || calculated) {
                    txt = "9";
                    calculated = false;
                } else
                    txt += "9";

                textView.setText(txt);
                break;
        }
    }

    public static void addCalculationMethod(CalculationMethod method, TextView textView) {
        if (textView == null)
            return;

        int numbers = Integer.parseInt(textView.getText().toString());

        switch (method) {
            case ADD:
                nextLine = true;
                calculationMethods.add(CalculationMethod.ADD);
                lines.add(numbers);
                break;
            case DIVIDE:
                nextLine = true;
                calculationMethods.add(CalculationMethod.DIVIDE);
                lines.add(numbers);
                break;
            case MULTIPLY:
                nextLine = true;
                calculationMethods.add(CalculationMethod.MULTIPLY);
                lines.add(numbers);
                break;
            case SUBTRACT:
                nextLine = true;
                calculationMethods.add(CalculationMethod.SUBTRACT);
                lines.add(numbers);
                break;
        }
    }

    private static int resultCalculate(String input) throws ScriptException {
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("rhino");

        Object result = scriptEngine.eval(input);
        String[] opt = String.valueOf(result).split("\\.");

        return Integer.parseInt(opt[0]);
    }

    public static void calculate(TextView textView, CalculationResult result) throws ScriptException {
        if (lines.size() == 0 && calculationMethods.size() == 0) {
            result.onResult(0);

            if (textView != null)
                textView.setText("0");
        }

        if (textView == null)
            return;

        if (lines.size() == 0 && calculationMethods.size() == 0)
            return;

        lines.add(Integer.parseInt(textView.getText().toString()));

        StringBuilder string = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            string.append(lines.get(i));

            if (i != calculationMethods.size()) {
                switch (calculationMethods.get(i)) {
                    case ADD:
                        string.append("+");
                        break;
                    case DIVIDE:
                        string.append("/");
                        break;
                    case MULTIPLY:
                        string.append("*");
                        break;
                    case SUBTRACT:
                        string.append("-");
                        break;
                }
            }
        }

        if (result != null)
            result.onResult(resultCalculate(string.toString()));

        lines.clear();
        calculationMethods.clear();

        calculated = true;
    }

}
