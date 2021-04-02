package com.cinus.expression.expr;

public class str {

    public static boolean endsWith(String str, String suffix) {
        if (str == null || !str.endsWith(suffix)) {
            return false;
        }
        return true;
    }
}
