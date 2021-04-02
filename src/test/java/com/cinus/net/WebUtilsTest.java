package com.cinus.net;


import org.junit.Test;

import java.net.UnknownHostException;

import static org.junit.Assert.assertTrue;

public class WebUtilsTest {

    @Test
    public void test_ping() throws UnknownHostException {
        String hostAddress = WebUtils.getHostAddress();
        assertTrue(WebUtils.ping(hostAddress));
    }
}
