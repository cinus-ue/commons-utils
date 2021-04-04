package com.cinus.crypto;

import com.cinus.thirdparty.binary.Hex;
import com.cinus.thirdparty.binary.StringUtils;

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


    public static final String DEFAULT_AES_ALGORITHM = "AES/GCM/NoPadding";
    private static final int ITERATION_COUNT = 4096;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 12;


    private static SecretKey generateKey(char[] password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(password, salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKey secret = factory.generateSecret(spec);
        SecretKey secretKey = new SecretKeySpec(secret.getEncoded(), "AES");
        return secretKey;
    }


    public static String encrypt(String data, String password) throws Exception {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        return Hex.encodeHexString(encrypt(data.getBytes(), password.toCharArray(), DEFAULT_AES_ALGORITHM));
    }


    public static String decrypt(String data, String password) throws Exception {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        return new String(decrypt(Hex.decodeHex(data.toCharArray()), password.toCharArray(), DEFAULT_AES_ALGORITHM));
    }


    public static byte[] encrypt(byte[] data, char[] password, String algorithm) throws Exception {
        byte[] salt = randomSalt();
        SecretKey sk = generateKey(password, salt);
        Cipher cipher = Cipher.getInstance(algorithm);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, salt);
        cipher.init(Cipher.ENCRYPT_MODE, sk, gcmParameterSpec);
        byte[] ciphertext = cipher.doFinal(data);
        byte[] bytes = new byte[ciphertext.length + salt.length];
        System.arraycopy(ciphertext, 0, bytes, 0, ciphertext.length);
        System.arraycopy(salt, 0, bytes, ciphertext.length, salt.length);
        return bytes;
    }


    public static byte[] decrypt(byte[] data, char[] password, String algorithm) throws Exception {
        byte[] salt = Arrays.copyOfRange(data, data.length - SALT_LENGTH, data.length);
        SecretKey sk = generateKey(password, salt);
        Cipher cipher = Cipher.getInstance(algorithm);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, salt);
        cipher.init(Cipher.DECRYPT_MODE, sk, gcmParameterSpec);
        byte[] ciphertext = Arrays.copyOf(data, data.length - SALT_LENGTH);
        return cipher.doFinal(ciphertext);
    }


    private static byte[] randomSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }


}

