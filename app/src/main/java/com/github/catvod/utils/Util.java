package com.github.catvod.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.catvod.spider.Init;

import java.math.BigInteger;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static final String patternAli = "(https:\\/\\/www\\.aliyundrive\\.com\\/s\\/[^\"]+|https:\\/\\/www\\.alipan\\.com\\/s\\/[^\"]+)";
    public static final String patternQuark = "(https:\\/\\/pan\\.quark\\.cn\\/s\\/[^\"]+)";
    public static final String patternUC = "(https:\\/\\/drive\\.uc\\.cn\\/s\\/[^\"]+)";
    public static final Pattern RULE = Pattern.compile("http((?!http).){12,}?\\.(m3u8|mp4|mkv|flv|mp3|m4a|aac)\\?.*|http((?!http).){12,}\\.(m3u8|mp4|mkv|flv|mp3|m4a|aac)|http((?!http).)*?video/tos*");
    public static final Pattern THUNDER = Pattern.compile("(magnet|thunder|ed2k):.*");
    public static final String CHROME = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36";
    public static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7";
    public static final List<String> MEDIA = Arrays.asList("mp4", "mkv", "wmv", "flv", "avi", "iso", "mpg", "ts", "mp3", "aac", "flac", "m4a", "ape", "ogg");
    public static final List<String> SUB = Arrays.asList("srt", "ass", "ssa", "vtt");
    private static HashMap<String, String> webHttpHeaderMap;

    public static boolean isVip(String url) {
        List<String> hosts = Arrays.asList("iqiyi.com", "v.qq.com", "youku.com", "le.com", "tudou.com", "mgtv.com", "sohu.com", "acfun.cn", "bilibili.com", "baofeng.com", "pptv.com");
        for (String host : hosts) if (url.contains(host)) return true;
        return false;
    }

    public static boolean isBlackVodUrl(String url) {
        List<String> hosts = Arrays.asList("973973.xyz", ".fit:");
        for (String host : hosts) if (url.contains(host)) return true;
        return false;
    }

    public static boolean isThunder(String url) {
        return THUNDER.matcher(url).find() || isTorrent(url);
    }

    public static boolean isTorrent(String url) {
        return !url.startsWith("magnet") && url.split(";")[0].endsWith(".torrent");
    }

    public static boolean isVideoFormat(String url) {
        if (url.contains("url=http") || url.contains(".js") || url.contains(".css") || url.contains(".html"))
            return false;
        return RULE.matcher(url).find();
    }

    public static boolean isSub(String ext) {
        return SUB.contains(ext);
    }

    public static boolean isMedia(String text) {
        return MEDIA.contains(getExt(text).toLowerCase());
    }

    public static String getExt(String name) {
        return name.contains(".") ? name.substring(name.lastIndexOf(".") + 1) : name;
    }

    public static String getSize(double size) {
        if (size <= 0) return "";
        if (size > 1024 * 1024 * 1024 * 1024.0) {
            size /= (1024 * 1024 * 1024 * 1024.0);
            return String.format(Locale.getDefault(), "%.2f%s", size, "TB");
        } else if (size > 1024 * 1024 * 1024.0) {
            size /= (1024 * 1024 * 1024.0);
            return String.format(Locale.getDefault(), "%.2f%s", size, "GB");
        } else if (size > 1024 * 1024.0) {
            size /= (1024 * 1024.0);
            return String.format(Locale.getDefault(), "%.2f%s", size, "MB");
        } else {
            size /= 1024.0;
            return String.format(Locale.getDefault(), "%.2f%s", size, "KB");
        }
    }

    public static String fixUrl(String base, String src) {
        if (src.startsWith("//")) {
            Uri parse = Uri.parse(base);
            return parse.getScheme() + ":" + src;
        } else if (!src.contains("://")) {
            Uri parse = Uri.parse(base);
            return parse.getScheme() + "://" + parse.getHost() + src;
        } else {
            return src;
        }
    }

    public static String removeExt(String text) {
        return text.contains(".") ? text.substring(0, text.lastIndexOf(".")) : text;
    }

    public static String substring(String text) {
        return substring(text, 1);
    }

    public static String substring(String text, int num) {
        if (text != null && text.length() > num) {
            return text.substring(0, text.length() - num);
        } else {
            return text;
        }
    }

    public static String getVar(String data, String param) {
        for (String var : data.split("var")) if (var.contains(param)) return checkVar(var);
        return "";
    }

    private static String checkVar(String var) {
        if (var.contains("'")) return var.split("'")[1];
        if (var.contains("\"")) return var.split("\"")[1];
        return "";
    }

    public static String MD5(String src) {
        return MD5(src, "UTF-8");
    }

    public static String MD5(String src, String charset) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(src.getBytes(charset));
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder sb = new StringBuilder(no.toString(16));
            while (sb.length() < 32) sb.insert(0, "0");
            return sb.toString().toLowerCase();
        } catch (Exception e) {
            return "";
        }
    }

    public static void copy(String text) {
        ClipboardManager manager = (ClipboardManager) Init.context().getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText("fongmi", text));
        Notify.show("已複製 " + text);
    }

    public static void loadUrl(WebView webView, String script) {
        loadUrl(webView, script, null);
    }

    public static void loadUrl(WebView webView, String script, ValueCallback<String> callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript(script, callback);
        } else {
            webView.loadUrl("javascript:" + script);
        }
    }

    public static void addView(View view, ViewGroup.LayoutParams params) {
        try {
            ViewGroup group = Init.getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
            group.addView(view, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeView(View view) {
        try {
            ViewGroup group = Init.getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
            group.removeView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadWebView(String url, WebViewClient client) {
        Init.run(() -> {
            WebView webView = new WebView(Init.context());
            webView.getSettings().setDatabaseEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setJavaScriptEnabled(true);
            addView(webView, new ViewGroup.LayoutParams(0, 0));
            webView.setWebViewClient(client);
            webView.loadUrl(url);
        });
    }

    public static String getDigit(String text) {
        try {
            String newText = text;
            Matcher matcher = Pattern.compile(".*(1080|720|2160|4k|4K).*").matcher(text);
            if (matcher.find()) newText = matcher.group(1) + " " + text;
            matcher = Pattern.compile("^([0-9]+)").matcher(text);
            if (matcher.find()) newText = matcher.group(1) + " " + newText;
            return newText.replaceAll("\\D+", "") + " " + newText.replaceAll("\\d+", "");
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * @param referer
     * @param cookie  多个cookie name=value;name2=value2
     * @return
     */
    public static HashMap<String, String> webHeaders(String referer, String cookie) {
        HashMap<String, String> map = webHeaders(referer);
        map.put("Cookie", cookie);
        return map;
    }

    public static HashMap<String, String> webHeaders(String referer) {
        if (webHttpHeaderMap == null || webHttpHeaderMap.isEmpty()) {
            synchronized (Util.class) {
                if (webHttpHeaderMap == null || webHttpHeaderMap.isEmpty()) {
                    webHttpHeaderMap = new HashMap<>();
                    webHttpHeaderMap.put("Content-Type", "text/plain;charset=UTF-8");
                    webHttpHeaderMap.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
                    webHttpHeaderMap.put("Connection", "keep-alive");
                    webHttpHeaderMap.put("User-Agent", CHROME);
                    webHttpHeaderMap.put("Accept", "*/*");
                }
            }
        }
        URI uri = URI.create(referer);
        String u = uri.getScheme() + "://" + uri.getHost();
        webHttpHeaderMap.put("Referer", u);
        webHttpHeaderMap.put("Origin", u);
        return webHttpHeaderMap;
    }

    public static String getHost(String url) {
        URI uri = URI.create(url);
        return uri.getHost();
    }


    public static String findByRegex(String regex, String content, Integer groupCount) {
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(regex);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(content);
        if (m.find()) {
            return m.group(groupCount);
        } else {
            return "";
        }
    }

    public static String base64Decode(String s) {
        return new String(android.util.Base64.decode(s, Base64.NO_WRAP), Charset.defaultCharset());
    }

    public static String base64Encode(byte[] bytes) {
        return new String(android.util.Base64.encode(bytes, Base64.NO_WRAP), Charset.defaultCharset());
    }

    /**
     * 字符串相似度匹配
     *
     * @returns
     */

    public static LCSResult lcs(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return new LCSResult(0, "", 0);
        }

        StringBuilder sequence = new StringBuilder();
        int str1Length = str1.length();
        int str2Length = str2.length();
        int[][] num = new int[str1Length][str2Length];
        int maxlen = 0;
        int lastSubsBegin = 0;

        for (int i = 0; i < str1Length; i++) {
            for (int j = 0; j < str2Length; j++) {
                if (str1.charAt(i) != str2.charAt(j)) {
                    num[i][j] = 0;
                } else {
                    if (i == 0 || j == 0) {
                        num[i][j] = 1;
                    } else {
                        num[i][j] = 1 + num[i - 1][j - 1];
                    }

                    if (num[i][j] > maxlen) {
                        maxlen = num[i][j];
                        int thisSubsBegin = i - num[i][j] + 1;
                        if (lastSubsBegin == thisSubsBegin) {
                            // if the current LCS is the same as the last time this block ran
                            sequence.append(str1.charAt(i));
                        } else {
                            // this block resets the string builder if a different LCS is found
                            lastSubsBegin = thisSubsBegin;
                            sequence.setLength(0); // clear it
                            sequence.append(str1.substring(lastSubsBegin, i + 1));
                        }
                    }
                }
            }
        }
        return new LCSResult(maxlen, sequence.toString(), lastSubsBegin);
    }


    public static Integer findAllIndexes(List<String> arr, String value) {

        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equals(value)) {
                return i;
            }
        }
        return 0;
    }


    public static String sha1Hex(String input) throws NoSuchAlgorithmException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes(Charset.defaultCharset()));
            return bytesToHex(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


    public static class LCSResult {
        public int length;
        public String sequence;
        public int offset;

        public LCSResult(int length, String sequence, int offset) {
            this.length = length;
            this.sequence = sequence;
            this.offset = offset;
        }
    }

}
