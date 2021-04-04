package com.cinus.crypto;

import org.junit.Assert;
import org.junit.Test;

public class DESUtilsTest {

    @Test
    public void test_encrypt() throws Exception {
        String key = "key";
        String data = "encrypt";
        Assert.assertEquals(data, DESUtils.decrypt(DESUtils.encrypt(data, key), key));
    }

}
