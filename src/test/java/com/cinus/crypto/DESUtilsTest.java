package com.cinus.crypto;

import org.junit.Assert;
import org.junit.Test;

public class DESUtilsTest {

    @Test
    public void test_encrypt() throws Exception {
        String key = "key1234567";
        String str = "test";
        String ciphertext = DESUtils.encrypt(str, key);
        String plaintext = DESUtils.decrypt(ciphertext, key);
        Assert.assertEquals(str, plaintext);
    }

}
