package com.cinus.crypto;

import com.cinus.thirdparty.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class AESUtils {


    private static final String ALGO_AES = "AES/GCM/NoPadding";
    private static final int ITERATION_COUNT = 4096;
    private static final int KEY_LENGTH = 256;


    private static SecretKey generateKey(char[] password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(password, salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKey secret = factory.generateSecret(spec);
        SecretKey secretKey = new SecretKeySpec(secret.getEncoded(), "AES");
        return secretKey;
    }


    public static String encrypt(String data, String password) throws Exception {
        if (data == null) {
            return null;
        }
        return Hex.encodeHexString(encrypt(data.getBytes(), password.toCharArray()));
    }


    public static String decrypt(String data, String password) throws Exception {
        if (data == null) {
            return null;
        }
        return new String(decrypt(Hex.decodeHex(data.toCharArray()), password.toCharArray()));
    }


    public static byte[] encrypt(byte[] data, char[] password) throws Exception {
        byte[] salt = randomSalt();
        SecretKey sk = generateKey(password, salt);
        Cipher cipher = Cipher.getInstance(ALGO_AES);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, salt);
        cipher.init(Cipher.ENCRYPT_MODE, sk, gcmParameterSpec);
        byte[] ciphertext = cipher.doFinal(data);
        byte[] ret = new byte[ciphertext.length + salt.length];
        System.arraycopy(ciphertext, 0, ret, 0, ciphertext.length);
        System.arraycopy(salt, 0, ret, ciphertext.length, salt.length);
        return ret;
    }


    public static byte[] decrypt(byte[] data, char[] password) throws Exception {
        byte[] salt = Arrays.copyOfRange(data, data.length - 12, data.length);
        SecretKey sk = generateKey(password, salt);
        Cipher cipher = Cipher.getInstance(ALGO_AES);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, salt);
        cipher.init(Cipher.DECRYPT_MODE, sk, gcmParameterSpec);
        byte[] ciphertext = Arrays.copyOf(data, data.length - 12);
        return cipher.doFinal(ciphertext);
    }


    private static byte[] randomSalt() {
        byte[] salt = new byte[12];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }


}

