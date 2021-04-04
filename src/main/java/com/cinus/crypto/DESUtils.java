package com.cinus.crypto;


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

    public static final String DEFAULT_DES_ALGORITHM = "DES/CBC/PKCS5Padding";
    private static final int IV_LENGTH = 8;

    private static SecretKeySpec generateKey(String password) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("DES");
        kg.init(new SecureRandom(password.getBytes()));
        SecretKey secretKey = kg.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), "DES");
    }

    public static String encrypt(String data, String password) throws Exception {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        return Hex.encodeHexString(encrypt(generateKey(password), data.getBytes(), DEFAULT_DES_ALGORITHM));
    }


    public static String decrypt(String data, String password) throws Exception {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        return new String(decrypt(generateKey(password), Hex.decodeHex(data.toCharArray()), DEFAULT_DES_ALGORITHM));
    }


    private static byte[] encrypt(SecretKeySpec key, byte[] data, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        byte[] iv = randIV();
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] ciphertext = cipher.doFinal(data);
        byte[] bytes = new byte[ciphertext.length + iv.length];
        System.arraycopy(ciphertext, 0, bytes, 0, ciphertext.length);
        System.arraycopy(iv, 0, bytes, ciphertext.length, iv.length);
        return bytes;
    }


    private static byte[] decrypt(SecretKeySpec key, byte[] data, String algorithm) throws Exception {
        byte[] iv = Arrays.copyOfRange(data, data.length - IV_LENGTH, data.length);
        byte[] ciphertext = Arrays.copyOfRange(data, 0, data.length - IV_LENGTH);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        return cipher.doFinal(ciphertext);
    }


    private static byte[] randIV() {
        SecureRandom rand = new SecureRandom();
        byte[] iv = new byte[IV_LENGTH];
        rand.nextBytes(iv);
        return iv;
    }

}

