package com.github.catvod.net;

import com.github.catvod.crawler.Spider;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OkHttpWithCookie {

    public static final String POST = "POST";
    public static final String GET = "GET";

    private OkHttpClient client;


    private static class Loader {
        static volatile OkHttpWithCookie INSTANCE = new OkHttpWithCookie();
    }

    private static OkHttpWithCookie get() {
        return Loader.INSTANCE;
    }

    public static Response newCall(Request request, CookieJar cookieJar) throws IOException {
        return client(cookieJar).newCall(request).execute();
    }

    public static Response newCall(String url, CookieJar cookieJar) throws IOException {
        return client(cookieJar).newCall(new Request.Builder().url(url).build()).execute();
    }

    public static Response newCall(String url, Map<String, String> header, CookieJar cookieJar) throws IOException {
        return client(cookieJar).newCall(new Request.Builder().url(url).headers(Headers.of(header)).build()).execute();
    }

    public static String string(String url, CookieJar cookieJar) {
        return string(url, null, cookieJar);
    }

    public static String string(String url, Map<String, String> header, CookieJar cookieJar) {
        return string(url, null, header, cookieJar);
    }

    public static String string(String url, Map<String, String> params, Map<String, String> header, CookieJar cookieJar) {
        return url.startsWith("http") ? new OkRequest(GET, url, params, header).execute(client(cookieJar)).getBody() : "";
    }

    public static OkResult get(String url, Map<String, String> params, Map<String, String> header, CookieJar cookieJar) {
        return new OkRequest(GET, url, params, header).execute(client(cookieJar));
    }

    public static String post(String url, Map<String, String> params, CookieJar cookieJar) {
        return post(url, params, null, cookieJar).getBody();
    }

    public static OkResult post(String url, Map<String, String> params, Map<String, String> header, CookieJar cookieJar) {
        return new OkRequest(POST, url, params, header).execute(client(cookieJar));
    }

    public static String post(String url, String json, CookieJar cookieJar) {
        return post(url, json, null, cookieJar).getBody();
    }

    public static OkResult post(String url, String json, Map<String, String> header, CookieJar cookieJar) {
        return new OkRequest(POST, url, json, header).execute(client(cookieJar));
    }

    public static String getLocation(String url, Map<String, String> header, CookieJar cookieJar) throws IOException {
        return getLocation(client(cookieJar).newBuilder().followRedirects(false).followSslRedirects(false).build().newCall(new Request.Builder().url(url).headers(Headers.of(header)).build()).execute().headers().toMultimap());
    }

    public static Map<String, List<String>> getLocationHeader(String url, Map<String, String> header, CookieJar cookieJar) throws IOException {
        return client(cookieJar).newBuilder().followRedirects(false).followSslRedirects(false).build().newCall(new Request.Builder().url(url).headers(Headers.of(header)).build()).execute().headers().toMultimap();
    }

    public static String getLocation(Map<String, List<String>> headers) {
        if (headers == null) return null;
        if (headers.containsKey("location")) return headers.get("location").get(0);
        if (headers.containsKey("Location")) return headers.get("Location").get(0);
        return null;
    }

    private static OkHttpClient build(CookieJar cookieJar) {
        if (get().client != null) return get().client;
        return get().client = getBuilder(cookieJar).build();
    }

    private static OkHttpClient.Builder getBuilder(CookieJar cookieJar) {
        return new OkHttpClient.Builder().cookieJar(cookieJar).dns(safeDns()).connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).hostnameVerifier((hostname, session) -> true).sslSocketFactory(new SSLCompat(), SSLCompat.TM);
    }

    private static OkHttpClient client(CookieJar cookieJar) {
        try {
            return Objects.requireNonNull(Spider.client());
        } catch (Throwable e) {
            return build(cookieJar);
        }
    }

    private static Dns safeDns() {
        try {
            return Objects.requireNonNull(Spider.safeDns());
        } catch (Throwable e) {
            return Dns.SYSTEM;
        }
    }
}
