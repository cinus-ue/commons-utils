package com.cinus.net;


import org.junit.Assert;
import org.junit.Test;

import java.net.UnknownHostException;


public class NetUtilsTest {

    @Test
    public void test_ping() throws UnknownHostException {
        String hostAddress = NetUtils.getHostAddress();
        Assert.assertTrue(NetUtils.ping(hostAddress));
    }
}
