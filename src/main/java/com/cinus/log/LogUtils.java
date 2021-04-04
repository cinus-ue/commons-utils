package com.cinus.log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {

    private static boolean enable = true;

    private static LogLevel minLevel = LogLevel.ALL;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");


    public static void setEnable(boolean enable) {
        LogUtils.enable = enable;
    }

    public static void setLogLevel(LogLevel level) {
        LogUtils.minLevel = level;
    }

    public static void debug(String msg) {
        record(LogLevel.DEBUG, msg);
    }

    public static void debug(String format, Object... arguments) {
        record(LogLevel.DEBUG, formatMessage(format, arguments));
    }

    public static void info(String msg) {
        record(LogLevel.INFO, msg);
    }

    public static void info(String format, Object... arguments) {
        record(LogLevel.INFO, formatMessage(format, arguments));
    }

    public static void warn(String msg) {
        record(LogLevel.WARN, msg);
    }

    public static void warn(String format, Object... arguments) {
        record(LogLevel.WARN, formatMessage(format, arguments));
    }

    public static void error(String msg) {
        record(LogLevel.ERROR, msg);
    }

    public static void error(String msg, Throwable t) {
        record(LogLevel.ERROR, msg);
        t.printStackTrace();
    }

    public static void error(String format, Object... arguments) {
        record(LogLevel.ERROR, formatMessage(format, arguments));
    }

    private static void record(LogLevel logLevel, String msg) {
        if (!enable) {
            return;
        }
        if (logLevel.isAllow(minLevel)) {
            StringBuilder builder = new StringBuilder();
            builder.append(formatCurrentTime()).append(" ").append(logLevel.getName());
            StackTraceElement traceElement = getStackTraceElement();
            if (traceElement != null) {
                builder.append("(").append(traceElement.getFileName()).append(":").append(traceElement.getLineNumber())
                        .append(")").append("  ");
            }
            builder.append(msg);
            System.out.println(builder.toString());
        }
    }

    private static String formatCurrentTime() {
        return sdf.format(new Date());
    }

    private static String formatMessage(String messagePattern, Object[] argArray) {
        int i = 0;
        int s = 0;
        StringBuilder sb = new StringBuilder(messagePattern.length() + 50);
        for (int L = 0; L < argArray.length; ++L) {
            int j = messagePattern.indexOf("{}", i);
            if (j == -1) {
                return sb.toString();
            }
            sb.append(messagePattern.substring(s, j + 2).replace("{}", argArray[L].toString()));
            i++;
            s = j + 2;
        }
        return sb.toString();
    }

    private static StackTraceElement getStackTraceElement() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int methodCount = 1;
        int stackOffset = getStackOffset(trace);

        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            StackTraceElement element = trace[stackIndex];
            return element;
        }

        return null;
    }

    private static int getStackOffset(StackTraceElement[] trace) {
        for (int i = 2; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(LogUtils.class.getName())) {
                return --i;
            }
        }
        return -1;
    }
}
