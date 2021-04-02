package com.cinus.system;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class SystemUtilsTest {

    @Test
    public void test_getUserDir() {
        File dir = SystemUtils.getUserDir();
        assertNotNull(dir);
        assertTrue(dir.exists());
    }
}
