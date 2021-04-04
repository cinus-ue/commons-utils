package com.cinus.log;

import org.junit.Test;

public class LogUtilsTest {
    @Test
    public void test_log() throws Exception {
        LogUtils.info("MESSAGE: %s", "this is a test msg!");
    }
}
