package com.cinus.crypto;


import com.cinus.exception.ExceptionUtils;
import com.cinus.thirdparty.binary.Base64;
import com.cinus.thirdparty.binary.Hex;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtils {

    private static final String RSA = "RSA";

    private static final String RSA_MODE = "RSA/ECB/PKCS1Padding";

    public static KeyPair generateRSAKey() throws NoSuchAlgorithmException {
        return generateRSAKey(1024);
    }


    public static KeyPair generateRSAKey(int keysize) throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
        kpg.initialize(keysize);
        return kpg.genKeyPair();

    }

    public static String encrypt(String data, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Hex.encodeHexString(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw ExceptionUtils.utilException(e);
        }

    }

    public static byte[] encrypt(byte[] data, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw ExceptionUtils.utilException(e);
        }
    }

    public static String decrypt(String data, PrivateKey privateKey) {
        try {
            byte[] buf = Hex.decodeHex(data.toCharArray());
            Cipher cipher = Cipher.getInstance(RSA_MODE);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(buf));
        } catch (Exception e) {
            throw ExceptionUtils.utilException(e);
        }
    }

    public static byte[] decrypt(byte[] data, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_MODE);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw ExceptionUtils.utilException(e);
        }
    }


    public static PublicKey getPublicKey(byte[] keyBytes) {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (Exception e) {
            throw ExceptionUtils.utilException(e);
        }
    }


    public static PrivateKey getPrivateKey(byte[] keyBytes) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (Exception e) {
            throw ExceptionUtils.utilException(e);
        }
    }


    public static PublicKey getPublicKey(String modulus, String publicExponent) {
        try {
            BigInteger bigIntModulus = new BigInteger(modulus);
            BigInteger bigIntPrivateExponent = new BigInteger(publicExponent);
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (Exception e) {
            throw ExceptionUtils.utilException(e);
        }
    }


    public static PrivateKey getPrivateKey(String modulus, String privateExponent) {
        try {
            BigInteger bigIntModulus = new BigInteger(modulus);
            BigInteger bigIntPrivateExponent = new BigInteger(privateExponent);
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (Exception e) {
            throw ExceptionUtils.utilException(e);
        }
    }


    public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
        byte[] buffer = Base64.decodeBase64(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        return keyFactory.generatePublic(keySpec);
    }


    public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {

        byte[] buffer = Base64.decodeBase64(privateKeyStr);
        // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(keySpec);

    }


    public static PublicKey loadPublicKey(InputStream in) throws Exception {
        return loadPublicKey(readKey(in));
    }


    public static PrivateKey loadPrivateKey(InputStream in) throws Exception {
        return loadPrivateKey(readKey(in));
    }


    private static String readKey(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String readLine;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null) {
            if (readLine.charAt(0) == '-') {
                continue;
            } else {
                sb.append(readLine);
                sb.append('\r');
            }
        }

        return sb.toString();
    }


    public static String publicKeyInfo(PublicKey publicKey) {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        StringBuilder sb = new StringBuilder("----------RSAPublicKey----------\n");
        sb.append("Modulus.length=" + rsaPublicKey.getModulus().bitLength() + "\n");
        sb.append("Modulus=" + rsaPublicKey.getModulus().toString() + "\n");
        sb.append("PublicExponent.length=" + rsaPublicKey.getPublicExponent().bitLength() + "\n");
        sb.append("PublicExponent=" + rsaPublicKey.getPublicExponent().toString() + "\n");
        return sb.toString();
    }

    public static String privateKeyInfo(PrivateKey privateKey) {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        StringBuilder sb = new StringBuilder("----------RSAPrivateKey ----------\n");
        sb.append("Modulus.length=" + rsaPrivateKey.getModulus().bitLength() + "\n");
        sb.append("Modulus=" + rsaPrivateKey.getModulus().toString() + "\n");
        sb.append("PrivateExponent.length=" + rsaPrivateKey.getPrivateExponent().bitLength() + "\n");
        sb.append("PrivatecExponent=" + rsaPrivateKey.getPrivateExponent().toString() + "\n");
        return sb.toString();
    }


}
