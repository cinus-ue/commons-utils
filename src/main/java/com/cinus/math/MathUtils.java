package com.cinus.math;

import com.cinus.thirdparty.Constants;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.UUID;


public class MathUtils {

    private static DecimalFormat df2 = new DecimalFormat("#,##0.00");
    private static DecimalFormat df4 = new DecimalFormat("0.0000");
    private static DecimalFormat df6 = new DecimalFormat("0.000000");

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final String NUMBER_STRING = "0123456789";

    private static final String CHAR_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String BASE_CHAR_NUMBER = CHAR_STRING.concat(NUMBER_STRING);


    public static int randomInt() {
        return RANDOM.nextInt();
    }

    public static int randomInt(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static String randomCode(int size) {
        String code = Constants.EMPTY;
        for (int i = 0; i < size; i++) {
            code += RANDOM.nextInt(10);
        }
        return code;
    }

    public static int randomInt(int limit) {
        return RANDOM.nextInt(limit);
    }

    public static String randomString(int length) {
        return randomString(BASE_CHAR_NUMBER, length);
    }

    public static String randomNumbers(int length) {
        return randomString(NUMBER_STRING, length);
    }

    public static String randomString(String baseString, int length) {
        StringBuffer sb = new StringBuffer();
        if (length < 1) {
            length = 1;
        }
        int baseLength = baseString.length();
        for (int i = 0; i < length; i++) {
            int number = RANDOM.nextInt(baseLength);
            sb.append(baseString.charAt(number));
        }
        return sb.toString();
    }


    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }


    public static long minimum(long... values) {
        int len = values.length;
        long current = values[0];
        for (int i = 1; i < len; i++) {
            current = Math.min(values[i], current);
        }
        return current;
    }


    public static long maximum(long... values) {
        int len = values.length;
        long current = values[0];
        for (int i = 1; i < len; i++) {
            current = Math.max(values[i], current);
        }
        return current;
    }


    public static double minimum(double... values) {
        int len = values.length;
        double current = values[0];
        for (int i = 1; i < len; i++) {
            current = Math.min(values[i], current);
        }
        return current;
    }


    public static double maximum(double... values) {
        int len = values.length;
        double current = values[0];
        for (int i = 1; i < len; i++) {
            current = Math.max(values[i], current);

        }
        return current;
    }


    public static BigInteger minimum(BigInteger... values) {
        int len = values.length;
        if (len == 1) {
            if (values[0] == null) {
                throw new IllegalArgumentException("Cannot passed null BigInteger entry to minimum()");
            }
            return values[0];

        }
        BigInteger current = values[0];
        for (int i = 1; i < len; i++) {
            if (values[i] == null) {
                throw new IllegalArgumentException("Cannot passed null BigInteger entry to minimum()");
            }
            current = values[i].min(current);
        }
        return current;
    }


    public static BigInteger maximum(BigInteger... values) {
        int len = values.length;
        if (len == 1) {
            if (values[0] == null) {
                throw new IllegalArgumentException("Cannot passed null BigInteger entry to maximum()");
            }
            return values[0];
        }
        BigInteger current = values[0];
        for (int i = 1; i < len; i++) {
            if (values[i] == null) {
                throw new IllegalArgumentException("Cannot passed null BigInteger entry to maximum()");
            }
            current = values[i].max(current);
        }
        return current;

    }


    public static BigDecimal minimum(BigDecimal... values) {
        int len = values.length;
        if (len == 1) {
            if (values[0] == null) {
                throw new IllegalArgumentException("Cannot passed null BigDecimal entry to minimum()");
            }
            return values[0];
        }
        BigDecimal current = values[0];
        for (int i = 1; i < len; i++) {
            if (values[i] == null) {
                throw new IllegalArgumentException("Cannot passed null BigDecimal entry to minimum()");
            }
            current = values[i].min(current);
        }
        return current;
    }


    public static BigDecimal maximum(BigDecimal... values) {
        int len = values.length;
        if (len == 1) {
            if (values[0] == null) {
                throw new IllegalArgumentException("Cannot passed null BigDecimal entry to maximum()");
            }

            return values[0];
        }
        BigDecimal current = values[0];
        for (int i = 1; i < len; i++) {
            if (values[i] == null) {
                throw new IllegalArgumentException("Cannot passed null BigDecimal entry to maximum()");
            }
            current = values[i].max(current);
        }
        return current;
    }


    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be  a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public static float div(long v1, long v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be  a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }


    public static float div(float v1, float v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be  a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static String formatToString2(BigDecimal b) {
        return df2.format(b);
    }

    public static String formatToString4(BigDecimal b) {
        return df4.format(b);
    }

    public static String formatToString6(BigDecimal b) {
        return df6.format(b);
    }

    public static BigDecimal nvl(BigDecimal bigDecimal) {
        return null == bigDecimal ? new BigDecimal("0") : bigDecimal;
    }

    public static Double nvl(Double double1) {
        return null == double1 ? new Double("0") : double1;
    }

    public static BigDecimal add(BigDecimal bd1, BigDecimal bd2) {
        return nvl(bd1).add(nvl(bd2));
    }

    public static String add(String bd1, String bd2) {
        BigDecimal b1 = new BigDecimal(bd1);
        BigDecimal b2 = new BigDecimal(bd2);
        return nvl(b1).add(nvl(b2)).toString();
    }

    public static BigDecimal subtract(BigDecimal bd1, BigDecimal bd2) {
        return nvl(bd1).subtract(nvl(bd2));
    }


}
