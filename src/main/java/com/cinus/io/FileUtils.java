package com.cinus.io;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtils {


    public static long folderSize(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile())
                length += file.length();
            else
                length += folderSize(file);
        }
        return length;
    }


    public static void writeFile(byte[] fileData, String fileName) throws IOException {
        File file = null;
        if (fileName != null) {
            file = new File(fileName);
        }
        writeFile(fileData, file);
    }

    public static void writeFile(byte[] fileData, File file) throws IOException {
        OutputStream os;
        if (file == null) {
            os = System.out;
        } else {
            os = new FileOutputStream(file);
        }
        os.write(fileData);
        os.close();
    }

    public static boolean forceDeletePath(File path) {
        if (path == null) {
            return false;
        }
        if (path.exists() && path.isDirectory()) {
            File[] files = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    forceDeletePath(file);
                } else {
                    file.delete();
                }
            }
        }
        return path.delete();
    }

}
