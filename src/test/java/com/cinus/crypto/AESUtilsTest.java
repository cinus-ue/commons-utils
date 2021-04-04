package com.cinus.crypto;

import org.junit.Assert;
import org.junit.Test;


public class AESUtilsTest {


    @Test
    public void test_encrypt() throws Exception {
        String key = "key";
        String data = "encrypt";
        Assert.assertEquals(data, AESUtils.decrypt(AESUtils.encrypt(data, key), key));
    }

}
