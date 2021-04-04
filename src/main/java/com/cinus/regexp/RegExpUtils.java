package com.cinus.regexp;

public class RegExpUtils {


    public static boolean isEmail(String str) {
        String expr = ("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        return str.matches(expr);

    }


    public static boolean isEmail2(String str) {
        String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return str.matches(expr);

    }


    public static boolean isNumberLetter(String str) {
        String expr = "^[A-Za-z0-9]+$";
        return str.matches(expr);

    }


    public static boolean isNumber(String str) {
        String expr = "^[0-9]+$";
        return str.matches(expr);

    }


    public static boolean isLetter(String str) {

        String expr = "^[A-Za-z]+$";
        return str.matches(expr);

    }
}
