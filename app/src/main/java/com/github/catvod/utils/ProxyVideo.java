package com.github.catvod.utils;

import android.os.SystemClock;
import android.text.TextUtils;
import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.net.OkHttp;
import com.github.catvod.spider.Proxy;
import com.google.gson.Gson;
import okhttp3.Response;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProxyVideo {

    private static final String GO_SERVER = "http://127.0.0.1:7777/";

    public static String buildCommonProxyUrl(String url, Map<String, String> headers) {
        return Proxy.getUrl() + "?do=proxy&url=" + Util.base64Encode(url.getBytes(Charset.defaultCharset())) + "&header=" + Util.base64Encode((new Gson().toJson(headers)).getBytes(Charset.defaultCharset()));
    }

    public static void go() {
        boolean close = OkHttp.string(GO_SERVER).isEmpty();
        if (close) OkHttp.string("http://127.0.0.1:" + Proxy.getPort() + "/go");
        if (close) while (OkHttp.string(GO_SERVER).isEmpty()) SystemClock.sleep(20);
    }

    public static String goVer() {
        try {
            go();
            String result = OkHttp.string(GO_SERVER + "version");
            return new JSONObject(result).optString("version");
        } catch (Exception e) {
            return "";
        }
    }

    public static String url(String url, int thread) {
        if (!TextUtils.isEmpty(goVer()) && url.contains("/proxy?")) url += "&response=url";
        return String.format(Locale.getDefault(), "%s?url=%s&thread=%d", GO_SERVER, URLEncoder.encode(url), thread);
    }

    public static Object[] proxy(String url, Map<String, String> headers) throws Exception {
        SpiderDebug.log(" ++start proxy:");
        SpiderDebug.log(" ++proxy url:" + url);
        SpiderDebug.log(" ++proxy header:" + Json.toJson(headers));

        Response response = OkHttp.newCall(url, headers);
        SpiderDebug.log(" ++end proxy:");
        SpiderDebug.log(" ++proxy res code:" + response.code());
        SpiderDebug.log(" ++proxy res header:" + Json.toJson(response.headers()));
        //    SpiderDebug.log(" ++proxy res data:" + Json.toJson(response.body()));
        String contentType = response.headers().get("Content-Type");
        String contentDisposition = response.headers().get("Content-Disposition");
        if (contentDisposition != null) contentType = getMimeType(contentDisposition);
        Map<String, String> respHeaders = new HashMap<>();
       /* respHeaders.put("Access-Control-Allow-Credentials", "true");
        respHeaders.put("Access-Control-Allow-Origin", "*");*/

        for (String key : response.headers().names()) {
            respHeaders.put(key, response.headers().get(key));
        }
        SpiderDebug.log("++proxy res contentType:" + contentType);
        //   SpiderDebug.log("++proxy res body:" + response.body());
        SpiderDebug.log("++proxy res respHeaders:" + Json.toJson(respHeaders));
        return new Object[]{response.code(), contentType, response.body().byteStream(), respHeaders};
    }

    private static String getMimeType(String contentDisposition) {
        if (contentDisposition.endsWith(".mp4")) {
            return "video/mp4";
        } else if (contentDisposition.endsWith(".webm")) {
            return "video/webm";
        } else if (contentDisposition.endsWith(".avi")) {
            return "video/x-msvideo";
        } else if (contentDisposition.endsWith(".wmv")) {
            return "video/x-ms-wmv";
        } else if (contentDisposition.endsWith(".flv")) {
            return "video/x-flv";
        } else if (contentDisposition.endsWith(".mov")) {
            return "video/quicktime";
        } else if (contentDisposition.endsWith(".mkv")) {
            return "video/x-matroska";
        } else if (contentDisposition.endsWith(".mpeg")) {
            return "video/mpeg";
        } else if (contentDisposition.endsWith(".3gp")) {
            return "video/3gpp";
        } else if (contentDisposition.endsWith(".ts")) {
            return "video/MP2T";
        } else if (contentDisposition.endsWith(".mp3")) {
            return "audio/mp3";
        } else if (contentDisposition.endsWith(".wav")) {
            return "audio/wav";
        } else if (contentDisposition.endsWith(".aac")) {
            return "audio/aac";
        } else {
            return null;
        }
    }
}
