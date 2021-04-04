package com.cinus.digest;

import com.cinus.exception.UtilException;
import com.cinus.thirdparty.binary.Hex;

import java.security.MessageDigest;

public class DigestUtils extends com.cinus.thirdparty.digest.DigestUtils {

    public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA1";
    public static final String SHA224 = "SHA-224";
    public static final String SHA256 = "SHA-256";
    public static final String SHA384 = "SHA-384";
    public static final String SHA512 = "SHA-512";

    public static String shaHex(String text) {
        return shaHex(text.getBytes());
    }

    public static String sha256Hex(String text) {
        return sha256Hex(text.getBytes());
    }

    public static String sha512Hex(String text) {
        return sha512Hex(text.getBytes());
    }

    public static String hexdigest(String text, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(text.getBytes());
            byte[] digest = md.digest();
            return Hex.encodeHexString(digest);
        } catch (Exception e) {
            throw new UtilException(e);
        }
    }

}