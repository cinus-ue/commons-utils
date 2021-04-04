package com.cinus.crypto;

import org.junit.Assert;
import org.junit.Test;


public class AESUtilsTest {


    @Test
    public void test_encrypt() throws Exception {
        String key = "key123456";
        String str = "test";
        String ciphertext = AESUtils.encrypt(str, key);
        String plaintext = AESUtils.decrypt(ciphertext, key);
        Assert.assertEquals(str, plaintext);
    }

}
