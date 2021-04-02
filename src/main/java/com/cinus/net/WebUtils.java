package com.cinus.net;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class WebUtils {

    private static final int TIME_OUT = 3000;

    public static final String getRemoteAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String localIP = "127.0.0.1";
        if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    public static final String getServerHostname(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String contextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
        return contextUrl;
    }


    public static final String getServerHostnameRoot(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String path = request.getServletPath() + request.getPathInfo();
        String contextUrl = url.delete(url.length() - path.length(), url.length()).toString();
        return contextUrl;
    }


    public static boolean ping(String host) {
        boolean reach;
        try {
            InetAddress address = InetAddress.getByName(host);
            reach = address.isReachable(TIME_OUT);
        } catch (IOException ioe) {
            System.err.println("Unable to reach " + host + " (" + ioe.getMessage() + ")");
            return false;
        }
        return reach;
    }


    public static String getHostAddress() throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        return address.getHostAddress();
    }
}
