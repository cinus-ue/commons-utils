package com.cinus.net;

import java.util.HashMap;

public class MultimediaUtils {

    public final static HashMap<String, String> mime_types = new HashMap<String, String>() {
        {
            put("jar", "application/java-archive");
            put("jad", "text/vnd.sun.j2me.app-descriptor");
            put("sis", "application/vnd.symbian.install");
            put("sisx", "x-epoc/x-sisx-app");
            put("thm", "application/vnd.eri.thm");
            put("nth", "application/vnd.nok-s40theme");
            put("zip", "application/zip");
            put("rar", "application/octet-stream");
            put("cab", "application/octet-stream");

            put("gif", "image/gif");
            put("jpg", "image/jpeg");
            put("jpeg", "image/jpeg");
            put("png", "image/png");
            put("bmp", "image/bmp");

            put("avi", "video/x-msvideo");
            put("rm", "application/vnd.rn-realmedia");
            put("3gp", "video/3gpp");
            put("wmv", "video/x-ms-wmv");
            put("mpg", "video/mpg");
            put("asf", "video/x-ms-asf");
            put("flv", "video/x-flv");
            put("mp4", "video/mp4");

            put("wma", "audio/x-ms-wma");
            put("mp3", "audio/mp3");
            put("arm", "audio/amr");
            put("mid", "audio/x-midi");
            put("aac", "audio/aac");
            put("imy", "audio/imelody");

            put("swf", "application/x-shockwave-flash");

            put("txt", "text/plain");
            put("htm", "text/html");
            put("html", "text/html");
            put("pdf", "application/pdf");
            put("doc", "application/msword");
            put("rtf", "application/msword");
            put("docx", "application/msword");
            put("xls", "application/vnd.ms-excel");
            put("ppt", "application/vnd.ms-powerpoint");
            put("xlsx", "application/vnd.ms-excel");
            put("pptx", "application/vnd.ms-powerpoint");
            put("chm", "application/octet-stream");
        }
    };
}
