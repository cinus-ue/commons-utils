package com.cinus.crypto;

import com.cinus.array.ArrayUtils;
import com.cinus.exception.ExceptionUtils;
import com.cinus.thirdparty.binary.Hex;
import com.cinus.thirdparty.binary.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class AESUtils {


    private static final String DEFAULT_AES_ALGORITHM = "AES/GCM/NoPadding";
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;


    private static SecretKey generateKey(String password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKey secret = factory.generateSecret(spec);
        SecretKey secretKey = new SecretKeySpec(secret.getEncoded(), "AES");
        return secretKey;
    }


    public static String encrypt(String password, String data) throws Exception {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        return Hex.encodeHexString(encrypt(password, data.getBytes(), DEFAULT_AES_ALGORITHM));
    }


    public static String decrypt(String password, String data) throws Exception {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        return new String(decrypt(password, Hex.decodeHex(data.toCharArray()), DEFAULT_AES_ALGORITHM));
    }

    public static byte[] encrypt(String password, byte[] data, String algorithm) throws Exception {
        if (algorithm.contains("AES/GCM")) {
            return encryptGCM(password, data, algorithm);
        } else if (algorithm.contains("AES/CBC")) {
            return encryptCBC(password, data, algorithm);
        }
        throw ExceptionUtils.utilException("Algorithm not supported");
    }

    public static byte[] decrypt(String password, byte[] data, String algorithm) throws Exception {
        if (algorithm.contains("AES/GCM")) {
            return decryptGCM(password, data, algorithm);
        } else if (algorithm.contains("AES/CBC")) {
            return decryptCBC(password, data, algorithm);
        }
        throw ExceptionUtils.utilException("Algorithm not supported");
    }


    private static byte[] encryptGCM(String password, byte[] data, String algorithm) throws Exception {
        byte[] salt = randomSalt();
        SecretKey sk = generateKey(password, salt);
        Cipher cipher = Cipher.getInstance(algorithm);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, salt);
        cipher.init(Cipher.ENCRYPT_MODE, sk, gcmParameterSpec);
        return ArrayUtils.addAll(cipher.doFinal(data), salt);
    }


    private static byte[] decryptGCM(String password, byte[] data, String algorithm) throws Exception {
        byte[] salt = Arrays.copyOfRange(data, data.length - SALT_LENGTH, data.length);
        SecretKey sk = generateKey(password, salt);
        Cipher cipher = Cipher.getInstance(algorithm);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, salt);
        cipher.init(Cipher.DECRYPT_MODE, sk, gcmParameterSpec);
        return cipher.doFinal(Arrays.copyOf(data, data.length - SALT_LENGTH));
    }

    private static byte[] encryptCBC(String password, byte[] data, String algorithm) throws Exception {
        byte[] salt = randomSalt();
        SecretKey sk = generateKey(password, salt);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, sk, new IvParameterSpec(salt));
        return ArrayUtils.addAll(cipher.doFinal(data), salt);
    }


    private static byte[] decryptCBC(String password, byte[] data, String algorithm) throws Exception {
        byte[] salt = Arrays.copyOfRange(data, data.length - SALT_LENGTH, data.length);
        SecretKey sk = generateKey(password, salt);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, sk, new IvParameterSpec(salt));
        return cipher.doFinal(Arrays.copyOf(data, data.length - SALT_LENGTH));
    }


    private static byte[] randomSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }


}

