package com.cinus.charset;

import com.cinus.exception.ExceptionUtils;
import com.cinus.thirdparty.binary.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class CharsetUtils {

    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final String UTF_8 = "UTF-8";
    public static final String GBK = "GBK";


    public static String convert(String source, String srcCharset, String newCharset) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(srcCharset)) {
            srcCharset = ISO_8859_1;
        }
        if (StringUtils.isBlank(newCharset)) {
            srcCharset = UTF_8;
        }
        if (StringUtils.isBlank(source) || srcCharset.equals(newCharset)) {
            return source;
        }
        return new String(source.getBytes(srcCharset), newCharset);

    }


    public static String str(byte[] data, String charset) throws UnsupportedEncodingException {
        if (data == null) {
            return null;
        }
        if (StringUtils.isBlank(charset)) {
            return new String(data);
        }
        return new String(data, charset);

    }


    public static String str(ByteBuffer data, String charset) {
        if (data == null) {
            return null;
        }
        Charset cs;
        if (StringUtils.isBlank(charset)) {
            cs = Charset.defaultCharset();
        } else {
            cs = Charset.forName(charset);
        }
        return cs.decode(data).toString();
    }

    public static ByteBuffer toByteBuffer(String str, String charset) {
        return ByteBuffer.wrap(encode(str, charset));
    }

    public static byte[] encode(String str, String charset) {
        if (str == null) {
            return null;
        }
        try {
            return str.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            ExceptionUtils.throwUtilException(String.format("Charset [%s] unsupported!", charset));
        }
        return null;
    }
}
