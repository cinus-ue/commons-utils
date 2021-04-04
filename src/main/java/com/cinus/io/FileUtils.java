package com.cinus.io;


import com.cinus.array.ArrayUtils;
import com.cinus.thirdparty.binary.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtils {

    public final static String[] IMG_EXTS = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
    public final static String[] VIDEO_EXTS = new String[]{"avi", "rm", "3gp", "wmv", "mpg", "asf"};
    public final static String[] AUDIO_EXTS = new String[]{"wma", "mp3", "arm", "mid", "aac", "imy"};
    public final static String[] FLASH_EXTS = new String[]{"swf"};
    public final static String[] DOCS_EXTS = new String[]{"txt", "htm", "html", "pdf", "doc", "rtf", "xls", "ppt"};

    public static boolean isImageFile(String fn) {
        String ext = getExtension(fn);
        if (StringUtils.isBlank(ext)) return false;
        return ArrayUtils.contains(IMG_EXTS, ext.toLowerCase());
    }

    public static boolean isAudioFile(String fn) {
        String ext = getExtension(fn);
        if (StringUtils.isBlank(ext)) return false;
        return ArrayUtils.contains(AUDIO_EXTS, ext.toLowerCase());
    }

    public static boolean isVideoFile(String fn) {
        String ext = getExtension(fn);
        if (StringUtils.isBlank(ext)) return false;
        return ArrayUtils.contains(VIDEO_EXTS, ext.toLowerCase());
    }

    public static boolean isFlashFile(String fn) {
        String ext = getExtension(fn);
        if (StringUtils.isBlank(ext)) return false;
        return ArrayUtils.contains(FLASH_EXTS, ext.toLowerCase());
    }

    public static boolean isDocumentFile(String fn) {
        String ext = getExtension(fn);
        if (StringUtils.isBlank(ext)) return false;
        return ArrayUtils.contains(DOCS_EXTS, ext.toLowerCase());
    }


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


    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        } else {
            int index = indexOfExtension(filename);
            return index == -1 ? "" : filename.substring(index + 1);
        }
    }

    public static int indexOfExtension(String filename) {
        if (filename == null) {
            return -1;
        } else {
            int extensionPos = filename.lastIndexOf(46);
            int lastSeparator = indexOfLastSeparator(filename);
            return lastSeparator > extensionPos ? -1 : extensionPos;
        }
    }

    public static int indexOfLastSeparator(String filename) {
        if (filename == null) {
            return -1;
        } else {
            int lastUnixPos = filename.lastIndexOf(47);
            int lastWindowsPos = filename.lastIndexOf(92);
            return Math.max(lastUnixPos, lastWindowsPos);
        }
    }
}
