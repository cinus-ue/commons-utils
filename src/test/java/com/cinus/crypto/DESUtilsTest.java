package com.cinus.crypto;

import org.junit.Assert;
import org.junit.Test;

public class DESUtilsTest {

    @Test
    public void test_encrypt() throws Exception {
        String key = "key";
        String data = "1234567";

        Assert.assertEquals(data, DESUtils.decrypt(key, DESUtils.encrypt(key, data)));
        Assert.assertEquals(data, new String(DESUtils.decrypt(key,
                DESUtils.encrypt(key, data.getBytes(), "DES/ECB/PKCS5Padding"), "DES/ECB/PKCS5Padding")));
        data = "12345678";

        Assert.assertEquals(data, new String(DESUtils.decrypt(key,
                DESUtils.encrypt(key, data.getBytes(), "DES/ECB/NoPadding"), "DES/ECB/NoPadding")));
        Assert.assertEquals(data, new String(DESUtils.decrypt(key,
                DESUtils.encrypt(key, data.getBytes(), "DES/CBC/NoPadding"), "DES/CBC/NoPadding")));


    }

}
