package com.cinus.crypto;


import com.cinus.array.ArrayUtils;
import com.cinus.exception.ExceptionUtils;
import com.cinus.thirdparty.binary.Hex;
import com.cinus.thirdparty.binary.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;


public class DESUtils {

    private static final String DEFAULT_DES_ALGORITHM = "DES/CBC/PKCS5Padding";
    private static final int IV_LENGTH = 8;

    private static SecretKeySpec generateKey(String password) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("DES");
        kg.init(new SecureRandom(password.getBytes()));
        SecretKey secretKey = kg.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), "DES");
    }

    public static String encrypt(String password, String data) throws Exception {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        return Hex.encodeHexString(encrypt(password, data.getBytes(), DEFAULT_DES_ALGORITHM));
    }


    public static String decrypt(String password, String data) throws Exception {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        return new String(decrypt(password, Hex.decodeHex(data.toCharArray()), DEFAULT_DES_ALGORITHM));
    }

    public static byte[] encrypt(String password, byte[] data, String algorithm) throws Exception {
        if (algorithm.contains("DES/ECB")) {
            return encryptECB(password, data, algorithm);
        } else if (algorithm.contains("DES/CBC")) {
            return encryptCBC(password, data, algorithm);
        }
        throw ExceptionUtils.utilException("Algorithm not supported");
    }

    public static byte[] decrypt(String password, byte[] data, String algorithm) throws Exception {
        if (algorithm.contains("DES/ECB")) {
            return decryptECB(password, data, algorithm);
        } else if (algorithm.contains("DES/CBC")) {
            return decryptCBC(password, data, algorithm);
        }
        throw ExceptionUtils.utilException("Algorithm not supported");
    }

    private static byte[] encryptECB(String password, byte[] data, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, generateKey(password));
        return cipher.doFinal(data);
    }

    private static byte[] decryptECB(String password, byte[] data, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, generateKey(password));
        return cipher.doFinal(data);
    }

    private static byte[] encryptCBC(String password, byte[] data, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        byte[] iv = randIV();
        cipher.init(Cipher.ENCRYPT_MODE, generateKey(password), new IvParameterSpec(iv));
        return ArrayUtils.addAll(cipher.doFinal(data), iv);
    }


    private static byte[] decryptCBC(String password, byte[] data, String algorithm) throws Exception {
        byte[] iv = Arrays.copyOfRange(data, data.length - IV_LENGTH, data.length);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, generateKey(password), new IvParameterSpec(iv));
        return cipher.doFinal(Arrays.copyOfRange(data, 0, data.length - IV_LENGTH));
    }


    private static byte[] randIV() {
        SecureRandom rand = new SecureRandom();
        byte[] iv = new byte[IV_LENGTH];
        rand.nextBytes(iv);
        return iv;
    }

}

