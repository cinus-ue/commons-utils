package com.cinus.crypto;

import org.junit.Assert;
import org.junit.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSAUtilsTest {


    @Test
    public void test_encrypt() throws Exception {

        KeyPair keyPair = RSAUtils.generateRSAKey();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        String str = "test";
        String ciphertext = RSAUtils.encrypt(str, publicKey);
        String plaintext = RSAUtils.decrypt(ciphertext, privateKey);
        Assert.assertEquals(str, plaintext);
    }
}
