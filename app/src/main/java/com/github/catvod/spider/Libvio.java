package com.github.catvod.spider;

import android.content.Context;
import com.github.catvod.bean.Class;
import com.github.catvod.bean.Result;
import com.github.catvod.bean.Vod;
import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.net.OkHttp;
import com.github.catvod.utils.Json;
import com.github.catvod.utils.Util;
import com.google.gson.JsonElement;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Libvio extends Cloud {

    private static String siteUrl = "https://www.libvio.link/";


    private static final String MOBILE_UA = "Mozilla/5.0 (Linux; Android 11; M2007J3SC Build/RKQ1.200826.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/77.0.3865.120 MQQBrowser/6.2 TBS/045714 Mobile Safari/537.36";

    private HashMap<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("User-Agent", MOBILE_UA);

        return headers;
    }

    private HashMap<String, String> getHeaders(String refer) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("User-Agent", MOBILE_UA);
        headers.put("Referer", refer);

        return headers;
    }

    @Override
    public void init(Context context, String extend) throws Exception {
        JsonElement json = Json.parse(extend);
        super.init(context, extend);
        String html = OkHttp.string(json.getAsJsonObject().get("site").getAsString());
        Document doc = Jsoup.parse(html);
        for (Element element : doc.select(" a")) {
            if (element.text().contains("可用")) {
                siteUrl = element.attr("href");
                break;
            }
        }
        SpiderDebug.log("libvio跳转地址 =====>" + siteUrl); // js_debug.log
    }

    @Override
    public String homeContent(boolean filter) throws Exception {
        List<Vod> list = new ArrayList<>();
        List<Class> classes = new ArrayList<>();


        String link = siteUrl;
        Document doc = Jsoup.parse(OkHttp.string(link, getHeaders()));
        for (Element element : doc.select("ul.stui-header__menu > li > a")) {
            classes.add(new Class(element.attr("href").replace(".html", ""), element.text()));
        }

        for (Element element : doc.select("ul.stui-vodlist > li > div > a")) {

            String pic = element.attr("data-original");
            String url = element.attr("href");
            String name = element.attr("title");
            if (!pic.startsWith("http")) {
                pic = siteUrl + pic;
            }
            String id = url.split("/")[2];
            list.add(new Vod(id, name, pic));

        }
        return Result.string(classes, list);
    }

    public String MD5(String string) {
        // 创建 MD5 实例
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算 MD5 哈希值
            byte[] hashBytes = md.digest(string.getBytes());

            // 将字节数组转换为十六进制字符串表示
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // 输出加密后的 MD5 字符串
            System.out.println("MD5 加密: " + hexString.toString());
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String categoryContent(String tid, String pg, boolean filter, HashMap<String, String> extend) throws Exception {
        List<Vod> list = new ArrayList<>();

        String target = siteUrl + tid + "-" + pg + ".html";
        Document doc = Jsoup.parse(OkHttp.string(target, getHeaders()));
        for (Element element : doc.select("ul.stui-vodlist > li > div > a")) {

            String pic = element.attr("data-original");
            String url = element.attr("href");
            String name = element.attr("title");
            if (!pic.startsWith("http")) {
                pic = siteUrl + pic;
            }
            String id = url.split("/")[2];
            list.add(new Vod(id, name, pic));

        }

        Integer total = (Integer.parseInt(pg) + 1) * 20;
        return Result.string(Integer.parseInt(pg), Integer.parseInt(pg) + 1, 20, total, list);
    }

    @Override
    public String detailContent(List<String> ids) throws Exception {
        Document doc = Jsoup.parse(OkHttp.string(siteUrl.concat("/detail/").concat(ids.get(0)), getHeaders()));
        String name = doc.select("body > div:nth-child(3) > div.row > div > div > div > div.stui-content > div.stui-content__detail > h1").text();
        String pic = doc.select("div.stui-content__thumb > a >img").attr("data-original");
        // 播放源
        Elements tabs = doc.select("div.stui-vodlist__head > div > h3");
        Elements list = doc.select("div.stui-vodlist__head > ul.stui-content__playlist  ");

        Vod.VodPlayBuilder builder = new Vod.VodPlayBuilder();
        List<String> quarkList = new ArrayList<>();
        for (int i = 0; i < tabs.size(); i++) {
            List<Vod.VodPlayBuilder.PlayUrl> playUrls = new ArrayList<>();

            String tabName = tabs.get(i).text();

            Elements li = list.get(i).select("a");
            for (Element element : li) {
                if (tabName.contains("夸克") || tabName.contains("UC")) {
                    quarkList.add(element.attr("href"));
                } else {
                    Vod.VodPlayBuilder.PlayUrl playUrl = new Vod.VodPlayBuilder.PlayUrl();
                    playUrl.name = element.text();
                    playUrl.url = element.attr("href").replace("/play/", "");
                    playUrls.add(playUrl);
                }

            }
            if (!tabName.contains("夸克") && !tabName.contains("UC")) {
                builder.append(tabName, playUrls);
            }
        }
        List<String> shareLinks = new ArrayList<>();
        String quarkNames = "";
        String quarkUrls = "";

        if (!quarkList.isEmpty()) {
            for (String s : quarkList) {
                Document detailPageDoc = Jsoup.parse(OkHttp.string(siteUrl.concat(s), getHeaders()));
                Matcher matcher = Pattern.compile("player_aaaa=(.*?)</script>").matcher(detailPageDoc.html());
                String json = matcher.find() ? matcher.group(1) : "";
                org.json.JSONObject player = new JSONObject(json);
                String url = player.getString("url");
                shareLinks.add(url);
            }
            quarkUrls = super.detailContentVodPlayUrl(shareLinks);
            quarkNames = super.detailContentVodPlayFrom(shareLinks);
        }

        Vod.VodPlayBuilder.BuildResult result = builder.build();

        Vod vod = new Vod();
        vod.setVodId(ids.get(0));
        vod.setVodPic(siteUrl + pic);
        vod.setVodName(name);
        vod.setVodPlayFrom(result.vodPlayFrom + "$$$" + quarkNames);
        vod.setVodPlayUrl(result.vodPlayUrl + "$$$" + quarkUrls);
        return Result.string(vod);
    }

    @Override
    public String searchContent(String key, boolean quick) throws Exception {
        List<Vod> list = new ArrayList<>();
        Document doc = Jsoup.parse(OkHttp.string(siteUrl.concat("/search/-------------.html?wd=").concat(URLEncoder.encode(key)), getHeaders()));
        for (Element element : doc.select("ul.stui-vodlist > li > div > a")) {

            String pic = element.attr("data-original");
            String url = element.attr("href");
            String name = element.attr("title");
            if (!pic.startsWith("http")) {
                pic = siteUrl + pic;
            }
            String id = url.split("/")[2];
            list.add(new Vod(id, name, pic));

        }
        return Result.string(list);
    }

    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) throws Exception {
        if (flag.contains("quark")) {
            return super.playerContent(flag, id, vipFlags);
        }
        String target = siteUrl.concat("/play/").concat(id);
        Document doc = Jsoup.parse(OkHttp.string(target));
        Matcher matcher = Pattern.compile("player_aaaa=(.*?)</script>").matcher(doc.html());
        String json = matcher.find() ? matcher.group(1) : "";
        org.json.JSONObject player = new JSONObject(json);
        String url = player.getString("url");
        String from = player.getString("from");
        String next = player.getString("link_next");
        String vodid = player.getString("id");
        String nid = player.getString("nid");
        String paurl = OkHttp.string(siteUrl + "/static/player/" + from + ".js");
        Matcher matcher1 = Pattern.compile(" src=\"(.*?)'").matcher(paurl);
        paurl = matcher1.find() ? matcher1.group(1) : "";
        String purl = paurl + url + "&next=" + next + "&id=" + vodid + "&nid=" + nid;
        if (!purl.startsWith("http")) {
            purl = siteUrl.replace("www.", "") + purl;
        }
        String playUrl = OkHttp.string(purl, getHeaders(target.replace("www.", "")));

        String realUrl = Util.getVar(playUrl, "vid");

        return Result.get().url(realUrl).header(getHeaders()).string();
    }
}