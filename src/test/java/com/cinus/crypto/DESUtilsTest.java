package com.cinus.crypto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class DESUtilsTest {

    @Test
    public void test_encrypt() throws Exception {
        String key = "key1234567";
        String str = "test";
        String ciphertext = DESUtils.encrypt(str, key);
        String plaintext = DESUtils.decrypt(ciphertext, key);
        assertEquals(str, plaintext);
    }

}
