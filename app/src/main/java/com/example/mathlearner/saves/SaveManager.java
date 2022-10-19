package com.example.mathlearner.saves;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.mathlearner.utils.FileUtil;
import com.example.mathlearner.utils.Utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveManager {

    @NonNull
    public static List<SaveInfo> loadSaves(@NonNull Context context) throws IOException {
        if (!new File(FileUtil.getPrivateAppData(context) + "/saves").exists()) {
            FileUtil.createFile(FileUtil.getPrivateAppData(context) + "/saves", true);
            return new ArrayList<>();
        }

        String contents = new String(FileUtil.read(FileUtil.getPrivateAppData(context) + "/saves"));
        String[] lines = contents.split(Utility.getLineSeparator(contents));

        List<SaveInfo> saveList = new ArrayList<>();

        for (String line : lines) {
            if (line.isEmpty())
                continue;

            String[] opts = line.split(" : ");

            switch (opts[3]) {
                case "new_task":
                    saveList.add(new SaveInfo(Integer.parseInt(opts[0]), Integer.parseInt(opts[1]), -1, SaveType.NEW_TASK));
                    break;
                case "correct":
                    saveList.add(new SaveInfo(Integer.parseInt(opts[0]), Integer.parseInt(opts[1]), Integer.parseInt(opts[2]), SaveType.CORRECT));
                    break;
                case "incorrect":
                    saveList.add(new SaveInfo(Integer.parseInt(opts[0]), Integer.parseInt(opts[1]), Integer.parseInt(opts[2]), SaveType.INCORRECT));
                    break;
                case "blank_field":
                    saveList.add(new SaveInfo(Integer.parseInt(opts[0]), Integer.parseInt(opts[1]), Integer.parseInt(opts[2]), SaveType.BLANK_FIELD));
                    break;
            }
        }

        return saveList;
    }

    public static void remove(Context context, int line) throws IOException {
        if (!new File(FileUtil.getPrivateAppData(context) + "/saves").exists()) {
            FileUtil.createFile(FileUtil.getPrivateAppData(context) + "/saves", true);
            return;
        }

        String contents = new String(FileUtil.read(FileUtil.getPrivateAppData(context) + "/saves"));
        String[] linesArray = contents.split(Utility.getLineSeparator(contents));
        String lineSep = Utility.getLineSeparator(contents);

        List<String> lines = new ArrayList<>(Arrays.asList(linesArray));
        lines.remove(line);

        StringBuilder mContents = new StringBuilder();
        for (String mLine : lines)
            mContents.append(mLine).append(lineSep);

        FileUtil.write(FileUtil.getPrivateAppData(context) + "/saves", mContents.toString().toCharArray());
    }

    public static void wipeSave(@NonNull Context context) throws IOException {
        if (!new File(FileUtil.getPrivateAppData(context) + "/saves").exists()) {
            FileUtil.createFile(FileUtil.getPrivateAppData(context) + "/saves", true);
            return;
        }

        FileUtil.write(FileUtil.getPrivateAppData(context) + "/saves", new char[0]);
    }

    public static void writeSave(@NonNull Context context, int number1, int number2, int userInput, SaveType type) throws IOException {
        String lineSep = null;

        if (!new File(FileUtil.getPrivateAppData(context) + "/saves").exists()) {
            FileUtil.createFile(FileUtil.getPrivateAppData(context) + "/saves", true);
            lineSep = System.lineSeparator();
        }

        String contents = new String(FileUtil.read(FileUtil.getPrivateAppData(context) + "/saves"));

        if (lineSep == null)
            lineSep = Utility.getLineSeparator(contents);

        String[] linesArray = contents.split(Utility.getLineSeparator(contents));

        List<String> lines = new ArrayList<>(Arrays.asList(linesArray));
        switch (type) {
            case CORRECT:
                lines.add(number1 + " : " + number2 + " : " + userInput + " : correct");
                break;
            case INCORRECT:
                lines.add(number1 + " : " + number2 + " : " + userInput + " : incorrect");
                break;
            case NEW_TASK:
                lines.add(number1 + " : " + number2 + " : " + userInput + " : new_task");
                break;
            case BLANK_FIELD:
                lines.add(number1 + " : " + number2 + " : -1 : blank_field");
                break;
        }

        StringBuilder mContents = new StringBuilder();
        for (String line : lines)
            mContents.append(line).append(lineSep);

        FileUtil.write(FileUtil.getPrivateAppData(context) + "/saves", mContents.substring(0, mContents.length() - lineSep.length()).toCharArray());
    }

}
