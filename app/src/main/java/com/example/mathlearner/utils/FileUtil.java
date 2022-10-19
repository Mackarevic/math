package com.example.mathlearner.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtil {

    public static boolean exists(String path) {
        return new File(path).exists();
    }

    public static final String EXTERNAL_STORAGE = Environment.getExternalStorageDirectory().getPath();

    @NonNull
    public static String getPublicAppData(Context context) {
        if (context == null)
            return "";

        return context.getExternalFilesDir(null).getPath();
    }

    @NonNull
    public static String getPublicAppCache(Context context) {
        if (context == null)
            return "";

        return context.getExternalCacheDir().getPath();
    }

    @NonNull
    public static String getPrivateAppData(Context context) {
        if (context == null)
            return "";

        return context.getFilesDir().getPath();
    }

    @NonNull
    public static String getPrivateAppCache(Context context) {
        if (context == null)
            return "";

        return context.getCacheDir().getPath();
    }

    public static void createFile(String filePath, boolean createDirs) throws IOException {
        File file = new File(filePath);
        File dirs = new File(filePath.substring(0, Utility.getLastPathSeparator(filePath, false)));

        if (!dirs.exists()) {
            if (createDirs)
                createDirectory(dirs.getPath());
            else
                throw new FileNotFoundException("No directories at \"" + dirs.getPath() + "\" were found");
        }

        if (!file.createNewFile())
            throw new IOException("File at \"" + filePath + "\" could not be created");
    }

    public static void createDirectory(String path) throws IOException {
        File dir = new File(path.substring(0, Utility.getLastPathSeparator(path, true)));

        if (dir.exists()) {
            if (dir.isFile()) {
                if (Build.VERSION.SDK_INT >= 26)
                    throw new FileAlreadyExistsException("File at \"" + path + "\" already exists");
                else
                    throw new IOException("File at \"" + path + "\" already exists");
            } else {
                if (Build.VERSION.SDK_INT >= 26)
                    throw new FileAlreadyExistsException("Directory at \"" + path + "\" already exists");
                else
                    throw new IOException("Directory at \"" + path + "\" already exists");
            }
        }

        if (!dir.mkdirs())
            throw new IOException("Could not create new directories at \"" + path + "\"");
    }

    @NonNull
    public static char[] read(String filePath) throws IOException {
        File file = new File(filePath);

        if (!file.exists())
            throw new FileNotFoundException("File at \"" + filePath + "\" not found");

        if (file.isDirectory())
            throw new IllegalStateException("\"" + filePath + "\" is a directory");

        if (!file.canRead()) {
            if (Build.VERSION.SDK_INT >= 26)
                throw new AccessDeniedException("Read-access for file \"" + filePath + "\" denied");
            else
                throw new IOException("Read-Access for file \"" + filePath + "\" denied");
        }

        FileInputStream fis = new FileInputStream(file);
        StringBuilder str = new StringBuilder();
        int data;

        while ((data = fis.read()) != -1)
            str.append((char) data);

        fis.close();
        return str.toString().toCharArray();
    }

    @NonNull
    public static String removeName(String path) {
        String lastSeg = Uri.parse(path).getLastPathSegment();
        return path.replace(lastSeg, "");
    }

    public static String removePath(String path) {
        return Uri.parse(path).getLastPathSegment();
    }

    public static void write(String filePath, char[] contents) throws IOException {
        File file = new File(filePath);

        if (!file.exists())
            createFile(filePath, true);

        if (file.exists() && file.isDirectory())
            throw new IllegalStateException("\"" + filePath + "\" is a directory");

        if (file.exists() && !file.canWrite()) {
            if (Build.VERSION.SDK_INT >= 26)
                throw new AccessDeniedException("\"" + filePath + "\" could not be overwritten");
            else
                throw new IOException("\"" + filePath + "\" could not be overwritten");
        }

        if (!file.canWrite())
            throw new AccessDeniedException("\"" + filePath + "\" could not be written");

        FileOutputStream fos = new FileOutputStream(file);
        for (char c : contents)
            fos.write(c);

        fos.close();
    }

    @NonNull
    public static List<String> listDirectory(String dirPath, boolean nameSort, boolean removePaths, boolean assignTypes, boolean ignoreErrors) throws IOException {
        List<String> data = new ArrayList<>();
        File dir = new File(dirPath);

        if (!dir.exists())
            throw new FileNotFoundException("\"" + dirPath + "\" does not exist");

        if (!dir.canRead()) {
            if (!ignoreErrors) {
                if (Build.VERSION.SDK_INT >= 26)
                    throw new AccessDeniedException("Cannot access \"" + dirPath + "\"");
                else
                    throw new IOException("Cannot access \"" + dirPath + "\"");
            } else
                return data;
        }

        if (dir.isFile()) {
            if (assignTypes)
                data.add("File: " + dirPath);
            else
                data.add(dirPath);

            if (removePaths)
                data.set(0, removePath(data.get(0)));

            return data;
        }

        File[] files = dir.listFiles();
        if (files == null)
            return data;

        for (File file : files) {
            if (assignTypes) {
                if (file.isFile())
                    data.add("File: " + file.getPath());
                else if (file.isDirectory())
                    data.add("Directory: " + file.getPath());
                else
                    data.add("Other: " + file.getPath());
            } else
                data.add(file.getPath());
        }

        if (removePaths) {
            if (!assignTypes) {
                if (data.size() != 0)
                    data.replaceAll(FileUtil::removePath);
            }
        }

        if (nameSort) {
            if (data.size() != 0)
                Collections.sort(data);
        }

        return data;
    }

    public static List<String> scanForSpecifiedName(String path, String name, boolean excludeAddingFolders, boolean ignoreErrors) throws IOException {
        List<String> results = new ArrayList<>();
        File root = new File(path);

        if (root.isFile()) {
            if (path.contains(name))
                results.add(path);

            return results;
        }

        List<String> data = listDirectory(path, true, false, false, ignoreErrors);

        for (String item : data) {
            File file = new File(item);

            if (excludeAddingFolders) {
                if (file.getPath().contains(name) && file.isFile())
                    results.add(file.getPath());
            } else {
                if (file.getPath().contains(name))
                    results.add(file.getPath());
            }

            if (file.isDirectory())
                results.addAll(scanForSpecifiedName(item, name, excludeAddingFolders, ignoreErrors));
        }

        return results;
    }

    public static void delete(String path) throws Exception {
        File inner = new File(path);

        if (!inner.exists())
            return;

        if (inner.isFile()) {
            if (!inner.delete())
                throw new IOException("Could not delete \"" + path + "\"");

            return;
        }

        if (listDirectory(path, false, false, false, false).size() == 0) {
            if (!inner.delete())
                throw new IOException("Could not delete \"" + path + "\"");

            return;
        }

        File[] files = inner.listFiles();
        if (files == null)
            return;

        for (File file : files) {
            if (Build.VERSION.SDK_INT >= 26) {
                BasicFileAttributes fileAttrib = Files.readAttributes(file.toPath(), BasicFileAttributes.class);

                if (fileAttrib.isDirectory())
                    delete(file.getPath());

                if (fileAttrib.isRegularFile()) {
                    if (!file.delete())
                        throw new IOException("Could not delete \"" + file.getPath() + "\"");
                }

                if (fileAttrib.isSymbolicLink()) {
                    if (!file.delete())
                        throw new IOException("Could not delete \"" + file.getPath() + "\"");
                }

                if (fileAttrib.isOther()) {
                    if (!file.delete())
                        throw new IOException("Could not delete \"" + file.getPath() + "\"");
                }
            } else {
                if (file.isFile()) {
                    if (!file.delete())
                        throw new IOException("Could not delete \"" + file.getPath() + "\"");
                }

                if (file.isDirectory())
                    delete(file.getPath());
            }
        }

        if (!inner.delete())
            throw new Exception("Could not delete \"" + path + "\"");
    }
}
