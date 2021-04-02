package com.cinus.crypto;


import com.cinus.thirdparty.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;


public class DESUtils {

    private static final String ALGO_DES = "DES/CBC/PKCS5Padding";

    private static SecretKeySpec generateKey(String password) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("DES");
        kg.init(new SecureRandom(password.getBytes()));
        SecretKey secretKey = kg.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), "DES");
    }

    public static String encrypt(String data, String password) throws Exception {
        if (data == null) {
            return null;
        }
        return Hex.encodeHexString(encrypt(generateKey(password), data.getBytes()));
    }


    public static String decrypt(String data, String password) throws Exception {
        if (data == null) {
            return null;
        }
        return new String(decrypt(generateKey(password), Hex.decodeHex(data.toCharArray())));
    }


    private static byte[] encrypt(SecretKeySpec key, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGO_DES);
        byte[] iv = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
        iv = Arrays.copyOfRange(iv, 0, 8);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] ciphertext = cipher.doFinal(data);
        byte[] ret = new byte[ciphertext.length + iv.length];
        System.arraycopy(ciphertext, 0, ret, 0, ciphertext.length);
        System.arraycopy(iv, 0, ret, ciphertext.length, iv.length);
        return ret;
    }


    private static byte[] decrypt(SecretKeySpec key, byte[] data) throws Exception {
        byte[] iv = Arrays.copyOfRange(data, data.length - 8, data.length);
        byte[] ciphertext = Arrays.copyOfRange(data, 0, data.length - 8);
        Cipher cipher = Cipher.getInstance(ALGO_DES);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        return cipher.doFinal(ciphertext);
    }


}

