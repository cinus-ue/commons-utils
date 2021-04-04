package com.cinus.net.http;

import com.cinus.exception.UtilException;
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
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpUtils {

    private static int timeout = 20000;
    private static Map<String, String> cookieMap = new HashMap<>();
    private static String charset = "UTF-8";


    public static void invalidCookieMap() {
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
        CloseableHttpClient httpClient = null;
        String content = Constants.EMPTY;
        try {
            httpClient = HttpClientBuilder.create().build();
            HttpClientContext context = HttpClientContext.create();
            CloseableHttpResponse response = httpClient.execute(httpGet, context);
            getCookiesFromCookieStore(context.getCookieStore(), cookieMap);
            int code = response.getStatusLine().getStatusCode();
            if (code != HttpStatus.SC_OK) {
                return content;
            }
            try {
                HttpEntity entity = response.getEntity();
                content = EntityUtils.toString(entity, charset);
            } finally {
                response.close();
            }
        } catch (IOException e) {
            throw new UtilException(e);
        } finally {
            try {
                if (httpClient != null)
                    httpClient.close();
            } catch (IOException e) {
                throw new UtilException(e);
            }
        }
        return content;
    }


    public static String executeGetWithSSL(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Cookie", convertCookieMapToString(cookieMap));
        httpGet.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
        CloseableHttpClient httpClient = null;
        String content = Constants.EMPTY;
        try {
            httpClient = createSSLInsecureClient();
            HttpClientContext context = HttpClientContext.create();
            CloseableHttpResponse response = httpClient.execute(httpGet, context);
            int code = response.getStatusLine().getStatusCode();
            if (code != HttpStatus.SC_OK) {
                return content;
            }
            try {
                HttpEntity entity = response.getEntity();
                content = EntityUtils.toString(entity, charset);
                getCookiesFromCookieStore(context.getCookieStore(), cookieMap);
            } finally {
                response.close();
            }
        } catch (IOException e) {
            throw new UtilException(e);
        } catch (GeneralSecurityException ex) {
            throw new UtilException(ex);
        } finally {
            try {
                if (httpClient != null)
                    httpClient.close();
            } catch (IOException e) {
                throw new UtilException(e);
            }
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
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(parameters));
            HttpClientContext clientContext = HttpClientContext.create();
            response = httpClient.execute(httpPost, clientContext);
            int code = response.getStatusLine().getStatusCode();
            if (code != HttpStatus.SC_OK) {
                return content;
            }
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity, charset);
            getCookiesFromCookieStore(clientContext.getCookieStore(), cookieMap);
        } catch (IOException e) {
            throw new UtilException(e);
        } finally {
            httpPost.releaseConnection();
        }
        return content;
    }


    public static String executePostWithSSL(String url, Map<String, String> params) {
        String content = Constants.EMPTY;
        HttpPost post = new HttpPost(url);
        List<NameValuePair> parameters = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        post.setHeader("Cookie", convertCookieMapToString(cookieMap));
        post.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
        CloseableHttpResponse response;
        try {
            CloseableHttpClient httpClientRe = createSSLInsecureClient();
            HttpClientContext clientContext = HttpClientContext.create();
            post.setEntity(new UrlEncodedFormEntity(parameters));
            response = httpClientRe.execute(post, clientContext);
            int code = response.getStatusLine().getStatusCode();
            if (code != HttpStatus.SC_OK) {
                return content;
            }
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity, charset);
            getCookiesFromCookieStore(clientContext.getCookieStore(), cookieMap);
        } catch (Exception e) {
            throw new UtilException(e);
        }
        return content;
    }


    public static String executePostWithJson(String url, String jsonBody) {
        String content = Constants.EMPTY;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
        httpPost.setHeader("Cookie", convertCookieMapToString(cookieMap));
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response;
        try {
            httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
            HttpClientContext clientContext = HttpClientContext.create();
            response = httpClient.execute(httpPost, clientContext);
            int code = response.getStatusLine().getStatusCode();
            if (code != HttpStatus.SC_OK) {
                return content;
            }
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity, charset);
            getCookiesFromCookieStore(clientContext.getCookieStore(), cookieMap);
        } catch (IOException e) {
            throw new UtilException(e);
        } finally {
            httpPost.releaseConnection();
        }
        return content;
    }


    public static String executePostWithJsonAndSSL(String url, String jsonBody) {
        String content = Constants.EMPTY;
        HttpPost post = new HttpPost(url);
        post.setHeader("Cookie", convertCookieMapToString(cookieMap));
        post.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
        CloseableHttpResponse response;
        try {
            CloseableHttpClient httpClient = createSSLInsecureClient();
            HttpClientContext clientContext = HttpClientContext.create();
            post.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
            response = httpClient.execute(post, clientContext);
            int code = response.getStatusLine().getStatusCode();
            if (code != HttpStatus.SC_OK) {
                return content;
            }
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity, charset);
            getCookiesFromCookieStore(clientContext.getCookieStore(), cookieMap);
        } catch (Exception e) {
            throw new UtilException(e);
        }
        return content;
    }


    private static void getCookiesFromCookieStore(CookieStore cookieStore, Map<String, String> cookieMap) {
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


    private static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    (s, sslContextL) -> true);
            return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
        } catch (GeneralSecurityException e) {
            throw e;
        }
    }
}
