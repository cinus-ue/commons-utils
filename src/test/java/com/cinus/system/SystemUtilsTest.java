package com.cinus.system;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class SystemUtilsTest {

    @Test
    public void test_getUserDir() {
        File dir = SystemUtils.getUserDir();
        Assert.assertNotNull(dir);
        Assert.assertTrue(dir.exists());
    }
}
