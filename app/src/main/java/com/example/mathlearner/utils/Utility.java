package com.example.mathlearner.utils;

import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

public class Utility {

    public static final String SPECIAL_CHARS = "<([{\\^-=$!|]})?*+.,>";

    public static int convertString(String str) {
        return Integer.parseInt(str);
    }

    public static boolean hasSpecialCharacter(@NonNull String input) {
        char[] in = input.toCharArray();

        for (char c : in) {
            for (char special : SPECIAL_CHARS.toCharArray()) {
                if (c == special)
                    return true;
            }
        }

        return false;
    }

    @NonNull
    public static String getLineSeparator(String contents) {
        if (contents == null)
            return "";

        if (contents.isEmpty())
            return "";

        char[] chars = contents.toCharArray();

        long r = 0;
        long n = 0;

        for (char c : chars) {
            if (c == '\r')
                r++;

            if (c == '\n')
                n++;
        }

        if (r == n)
            return "\r\n";
        else if (r >= 1 && n == 0)
            return "\r";
        else if (n >= 1 && r == 0)
            return "\n";
        else
            return "";
    }

    public static int getLastPathSeparator(String path, boolean toEnd) {
        if (path == null)
            return 0;

        if (path.isEmpty())
            return 0;

        if (!(path.contains("\\") || path.contains("/")))
            return 0;

        int lastSep;
        if (path.contains("\\") && path.contains("/")) {
            path = path.replaceAll("\\\\", "/");
            lastSep = path.lastIndexOf('/');
        } else if (path.contains("\\"))
            lastSep = path.lastIndexOf('\\');
        else
            lastSep = path.lastIndexOf('/');

        lastSep++;

        if (toEnd) {
            String sub = path.substring(lastSep);
            if (!sub.isEmpty())
                lastSep = path.length();
        }

        return lastSep;
    }

    public static String removeStartSpaces(String line) {
        String results = line;

        if (results.startsWith(" "))
            results = results.replaceFirst(" ", "");

        if (results.startsWith("\t"))
            results = results.replaceFirst("\t", "");

        if (results.startsWith(" ") || results.startsWith("\t"))
            removeStartSpaces(results);

        return results;
    }

    public static int getRandomInteger(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public static String fromList(List<String> list, String spliterator) {
        if (list == null)
            return "";

        if (list.size() == 0)
            return "";

        StringBuilder str = new StringBuilder();
        for (String item : list)
            str.append(item).append(spliterator);

        return str.substring(0, str.toString().length() - spliterator.length());
    }

    public static String readAsset(AssetManager assetManager, String assetFile) throws IOException {
        if (assetManager == null)
            throw new NullPointerException("AssetManager is null");

        if (assetFile == null || assetFile.isEmpty())
            throw new NullPointerException("No Asset file specified");

        InputStream input = assetManager.open(assetFile);
        if (input == null)
            throw new NullPointerException("Could not open \"" + assetFile + "\"");

        StringBuilder str = new StringBuilder();
        int data;

        while ((data = input.read()) != -1)
            str.append((char) data);

        input.close();
        return str.toString();
    }

}
