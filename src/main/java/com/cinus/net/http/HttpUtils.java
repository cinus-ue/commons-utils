package com.cinus.net.http;

import com.cinus.exception.ExceptionUtils;
import com.cinus.thirdparty.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpUtils {

    private static int timeout = 20000;
    private static Map<String, String> cookieMap = new HashMap<>();
    private static String charset = "UTF-8";
    private static CloseableHttpClient httpClient;
    private static CloseableHttpClient insecureHttpClient;


    public static void invalidCookies() {
        cookieMap.clear();
    }

    public static void setTimeout(int timeout) {
        HttpUtils.timeout = timeout;
    }

    public static void setCharset(String charset) {
        HttpUtils.charset = charset;
    }

    public static String executeGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Cookie", convertCookieMapToString(cookieMap));
        httpGet.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
        String content = Constants.EMPTY;
        HttpClientContext context = HttpClientContext.create();
        try (CloseableHttpResponse response = getHttpClient().execute(httpGet, context)) {
            int code = response.getStatusLine().getStatusCode();
            if (code != HttpStatus.SC_OK) {
                return content;
            }
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity, charset);
            getCookiesFromCookieStore(context.getCookieStore());
        } catch (Exception e) {
            ExceptionUtils.throwUtilException(e);
        }
        return content;
    }


    public static String executeInsecureGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Cookie", convertCookieMapToString(cookieMap));
        httpGet.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
        String content = Constants.EMPTY;
        HttpClientContext context = HttpClientContext.create();
        try (CloseableHttpResponse response = getSSLInsecureClient().execute(httpGet, context)) {
            int code = response.getStatusLine().getStatusCode();
            if (code != HttpStatus.SC_OK) {
                return content;
            }
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity, charset);
            getCookiesFromCookieStore(context.getCookieStore());
        } catch (Exception e) {
            ExceptionUtils.throwUtilException(e);
        }
        return content;
    }


    public static String executePost(String url, Map<String, String> params) {
        String content = Constants.EMPTY;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
        httpPost.setHeader("Cookie", convertCookieMapToString(cookieMap));
        List<NameValuePair> parameters = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        HttpClientContext context = HttpClientContext.create();
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(parameters));
            try (CloseableHttpResponse response = getHttpClient().execute(httpPost, context)) {
                int code = response.getStatusLine().getStatusCode();
                if (code != HttpStatus.SC_OK) {
                    return content;
                }
                HttpEntity entity = response.getEntity();
                content = EntityUtils.toString(entity, charset);
                getCookiesFromCookieStore(context.getCookieStore());
            }
        } catch (Exception e) {
            ExceptionUtils.throwUtilException(e);
        }
        return content;
    }


    public static String executeInsecurePost(String url, Map<String, String> params) {
        String content = Constants.EMPTY;
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> parameters = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        httpPost.setHeader("Cookie", convertCookieMapToString(cookieMap));
        httpPost.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
        HttpClientContext context = HttpClientContext.create();
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(parameters));
            try (CloseableHttpResponse response = getSSLInsecureClient().execute(httpPost, context)) {
                int code = response.getStatusLine().getStatusCode();
                if (code != HttpStatus.SC_OK) {
                    return content;
                }
                HttpEntity entity = response.getEntity();
                content = EntityUtils.toString(entity, charset);
                getCookiesFromCookieStore(context.getCookieStore());
            }
        } catch (Exception e) {
            ExceptionUtils.throwUtilException(e);
        }
        return content;
    }


    public static String executePost(String url, String jsonBody) {
        String content = Constants.EMPTY;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
        httpPost.setHeader("Cookie", convertCookieMapToString(cookieMap));
        HttpClientContext context = HttpClientContext.create();
        try {
            httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
            try (CloseableHttpResponse response = getHttpClient().execute(httpPost, context)) {
                int code = response.getStatusLine().getStatusCode();
                if (code != HttpStatus.SC_OK) {
                    return content;
                }
                HttpEntity entity = response.getEntity();
                content = EntityUtils.toString(entity, charset);
                getCookiesFromCookieStore(context.getCookieStore());
            }
        } catch (Exception e) {
            ExceptionUtils.throwUtilException(e);
        }
        return content;
    }


    public static String executeInsecurePost(String url, String jsonBody) {
        String content = Constants.EMPTY;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Cookie", convertCookieMapToString(cookieMap));
        httpPost.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
        HttpClientContext context = HttpClientContext.create();
        try {
            httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
            try (CloseableHttpResponse response = getSSLInsecureClient().execute(httpPost, context)) {
                int code = response.getStatusLine().getStatusCode();
                if (code != HttpStatus.SC_OK) {
                    return content;
                }
                HttpEntity entity = response.getEntity();
                content = EntityUtils.toString(entity, charset);
                getCookiesFromCookieStore(context.getCookieStore());
            }
        } catch (Exception e) {
            ExceptionUtils.throwUtilException(e);
        }
        return content;
    }


    private static void getCookiesFromCookieStore(CookieStore cookieStore) {
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            cookieMap.put(cookie.getName(), cookie.getValue());
        }
    }

    private static String convertCookieMapToString(Map<String, String> map) {
        String cookie = Constants.EMPTY;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            cookie += (entry.getKey() + "=" + entry.getValue() + "; ");
        }
        if (map.size() > 0) {
            cookie = cookie.substring(0, cookie.length() - 2);
        }
        return cookie;
    }


    private static CloseableHttpClient getSSLInsecureClient() {
        if (insecureHttpClient != null) {
            return insecureHttpClient;
        } else {
            try {
                SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                        new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build(), (s, sslContextL) -> true);
                insecureHttpClient = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
            } catch (GeneralSecurityException e) {
                ExceptionUtils.throwUtilException(e);
            }
        }
        return insecureHttpClient;
    }

    private static CloseableHttpClient getHttpClient() {
        if (httpClient != null) {
            return httpClient;
        } else {
            httpClient = HttpClients.createDefault();
        }
        return httpClient;
    }
}
