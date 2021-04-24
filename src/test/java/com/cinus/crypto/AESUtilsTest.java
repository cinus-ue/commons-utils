package com.cinus.crypto;

import org.junit.Assert;
import org.junit.Test;


public class AESUtilsTest {


    @Test
    public void test_encrypt() throws Exception {
        String key = "key";
        String data = "1234567";
        Assert.assertEquals(data, AESUtils.decrypt(key, AESUtils.encrypt(key, data)));
        Assert.assertEquals(data, new String(AESUtils.decrypt(key,
                AESUtils.encrypt(key, data.getBytes(), "AES/GCM/PKCS5Padding"), "AES/GCM/PKCS5Padding")));
        Assert.assertEquals(data, new String(AESUtils.decrypt(key,
                AESUtils.encrypt(key, data.getBytes(), "AES/CBC/PKCS5Padding"), "AES/CBC/PKCS5Padding")));


        data = "1234567812345678";
        Assert.assertEquals(data, new String(AESUtils.decrypt(key,
                AESUtils.encrypt(key, data.getBytes(), "AES/CBC/NoPadding"), "AES/CBC/NoPadding")));

    }

}
