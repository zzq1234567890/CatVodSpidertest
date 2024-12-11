package com.github.catvod.spider;/*
 * @File     : changzhang.js
 * @Author   : jade
 * @Date     : 2024/2/2 16:02
 * @Email    : jadehh@1ive.com
 * @Software : Samples
 * @Desc     :
 */


import android.content.Context;

import com.github.catvod.bean.Class;
import com.github.catvod.bean.Filter;
import com.github.catvod.bean.Result;
import com.github.catvod.bean.Vod;
import com.github.catvod.crawler.Spider;
import com.github.catvod.net.OkHttp;
import com.github.catvod.utils.AESEncryption;
import com.github.catvod.utils.Notify;
import com.github.catvod.utils.ProxyVideo;
import com.github.catvod.utils.Util;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangZhang extends Spider {

    private String siteUrl = "https://www.czys.pro";

    @Override
    public void init(Context context, String extend) throws Exception {
        super.init(context, extend);
        Document doc = Jsoup.parse(OkHttp.string(extend));

        siteUrl = doc.select("h2 > a").attr("href");

    }

    private Map<String, String> getHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("Cookie", "myannoun=1; Hm_lvt_0653ba1ead8a9aabff96252e70492497=2718862211; Hm_lvt_06341c948291d8e90aac72f9d64905b3=2718862211; Hm_lvt_07305e6f6305a01dd93218c7fe6bc9c3=2718862211; Hm_lpvt_07305e6f6305a01dd93218c7fe6bc9c3=2718867254; Hm_lpvt_06341c948291d8e90aac72f9d64905b3=2718867254; Hm_lpvt_0653ba1ead8a9aabff96252e70492497=2718867254");
        header.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 16_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/100.0.4896.77 Mobile/15E148 Safari/604.1");
        header.put("Connection", "keep-alive");
        URI uri = URI.create(siteUrl);
        header.put("Host", uri.getHost());
        header.put("Referer", siteUrl + "/");
        return header;
    }

    private Map<String, String> getIframeHeader(String url) {
        Map<String, String> header = new HashMap<>();
        header.put("Cookie", "myannoun=1; Hm_lvt_0653ba1ead8a9aabff96252e70492497=2718862211; Hm_lvt_06341c948291d8e90aac72f9d64905b3=2718862211; Hm_lvt_07305e6f6305a01dd93218c7fe6bc9c3=2718862211; Hm_lpvt_07305e6f6305a01dd93218c7fe6bc9c3=2718867254; Hm_lpvt_06341c948291d8e90aac72f9d64905b3=2718867254; Hm_lpvt_0653ba1ead8a9aabff96252e70492497=2718867254");
        header.put("User-Agent", Util.CHROME);
        header.put("Connection", "keep-alive");
        URI uri = URI.create(url);
        header.put("Host", uri.getHost());
        header.put("Sec-Fetch-Dest", "iframe");
        header.put("sec-fetch-mode", "navigate");
        header.put("Referer", siteUrl + "/");
        return header;
    }

    private Map<String, String> getVideoHeader(String url) {
        Map<String, String> header = new HashMap<>();

        header.put("Accept", "*/*");
        header.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7,de;q=0.6");
        header.put("Cache-Control", "no-cache");
        header.put("Connection", "keep-alive");
        header.put("Pragma", "no-cache");
        URI uri = URI.create(url);
        header.put("Host", uri.getHost());
        header.put("Sec-Fetch-Dest", "video");
        header.put("Sec-Fetch-Mode", "no-cors");
        header.put("Sec-Fetch-Site", "cross-site");
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
       /* header.put("sec-ch-ua", "\"Chromium\";v=\"124\", \"Google Chrome\";v=\"124\", \"Not-A.Brand\";v=\"99\"");
        header.put("sec-ch-ua-mobile", "?0");
        header.put("sec-ch-ua-platform", "\"Windows\"");*/
        return header;
    }

    @Override
    public String homeContent(boolean filter) throws Exception {

        List<Vod> list = new ArrayList<>();
        List<Class> classes = new ArrayList<>();
        LinkedHashMap<String, List<Filter>> filters = new LinkedHashMap<>();
        Document doc = Jsoup.parse(OkHttp.string(siteUrl));

        for (Element div : doc.select(".navlist > li ")) {
            classes.add(new Class(div.select(" a").attr("href"), div.select(" a").text()));
        }

        getVods(list, doc);
        return Result.string(classes, list);
    }

    private void getVods(List<Vod> list, Document doc) {
        for (Element div : doc.select(".bt_img.mi_ne_kd > ul >li")) {
            String id = div.select(".dytit > a").attr("href");
            String name = div.select(".dytit > a").text();
            String pic = div.select("img").attr("data-original");
            if (pic.isEmpty()) pic = div.select("img").attr("src");
            String remark = div.select(".hdinfo > span").text();

            list.add(new Vod(id, name, pic, remark));
        }
    }

    @Override
    public String categoryContent(String tid, String pg, boolean filter, HashMap<String, String> extend) throws Exception {
        List<Vod> list = new ArrayList<>();
        String target = siteUrl + tid + "/page/" + pg;
        //String filters = extend.get("filters");
        String html = OkHttp.string(target);
        Document doc = Jsoup.parse(html);
        getVods(list, doc);
        String total = "" + Integer.MAX_VALUE;
        return Result.get().vod(list).page(Integer.parseInt(pg), Integer.parseInt(total) / 25 + 1, 25, Integer.parseInt(total)).string();
    }

    @Override
    public String detailContent(List<String> ids) throws Exception {

        Document doc = Jsoup.parse(OkHttp.string(ids.get(0), getHeader()));

        Elements sources = doc.select("div.paly_list_btn > a");
        StringBuilder vod_play_url = new StringBuilder();
        String vod_play_from = "厂长" + "$$$";

        for (int i = 0; i < sources.size(); i++) {
            String href = sources.get(i).attr("href");
            String text = sources.get(i).text();
            vod_play_url.append(text).append("$").append(href);
            boolean notLastEpisode = i < sources.size() - 1;
            vod_play_url.append(notLastEpisode ? "#" : "$$$");
        }

        String title = doc.select(" div.dytext.fl > div > h1").text();
        String classifyName = doc.select(".moviedteail_list > li:nth-child(1)  > a").text();
        String year = doc.select(".moviedteail_list > li:nth-child(3)  > a").text();
        String area = doc.select(".moviedteail_list > li:nth-child(2)  > a").text();
        String remark = doc.select(".yp_context").text();
        String vodPic = doc.select(" div.dyxingq > div > div.dyimg.fl > img").attr("src");

        String director = doc.select(".moviedteail_list > li:nth-child(6)  > a").text();

        String actor = doc.select(".moviedteail_list > li:nth-child(8)  > a").text();

        String brief = doc.select(".yp_context").text();
        Vod vod = new Vod();
        vod.setVodId(ids.get(0));
        vod.setVodYear(year);
        vod.setVodName(title);
        vod.setVodArea(area);
        vod.setVodActor(actor);
        vod.setVodPic(vodPic);
        vod.setVodRemarks(remark);
        vod.setVodContent(brief);
        vod.setVodDirector(director);
        vod.setTypeName(classifyName);
        vod.setVodPlayFrom(vod_play_from);
        vod.setVodPlayUrl(vod_play_url.toString());
        return Result.string(vod);
    }

    @Override
    public String searchContent(String key, boolean quick) throws Exception {
        String searchUrl = siteUrl + "/daoyongjiekoshibushiyoubing?q=";
        String html = OkHttp.string(searchUrl + key);
        if (html.contains("Just a moment")) {
            Notify.show("厂长资源需要人机验证");
        }
        Document document = Jsoup.parse(html);
        List<Vod> list = new ArrayList<>();
        getVods(list, document);

        return Result.string(list);
    }


    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) throws Exception {
        String content = OkHttp.string(id, getHeader());
        Document document = Jsoup.parse(content);
        Elements iframe = document.select("iframe");
        if (!iframe.isEmpty()) {
            String videoContent = OkHttp.string(iframe.get(0).attr("src"), getIframeHeader(iframe.get(0).attr("src")));


            Matcher matcher2 = Pattern.compile("result_v2 =(.*?);").matcher(videoContent);
            String json2 = matcher2.find() ? matcher2.group(1) : "";
            org.json.JSONObject jsonObject = new JSONObject(json2);
            String encodedStr = jsonObject.getString("data");
            String realUrl = new String(new BigInteger(StringUtils.reverse(encodedStr), 16).toByteArray());

            String temp = decodeStr(realUrl);
            Map<String, String> header = getVideoHeader(temp);
            return Result.get().url(ProxyVideo.buildCommonProxyUrl(temp, header)).string();
        } else {
            for (Element script : document.select("script")) {
                String scriptText = script.html();
                if (scriptText.contains("wp_nonce")) {
                    String reg = "var(.*?)=\"(.*?)\"";
                    Pattern pattern = Pattern.compile(reg);
                    Matcher matcher = pattern.matcher(scriptText);

                    if (matcher.find()) {
                        String data = matcher.group(2);
                        String result = dncry(data);
                        String regex = "url:.*?['\"](.*?)['\"]";
                        Pattern pattern1 = Pattern.compile(regex);
                        Matcher matcher1 = pattern1.matcher(result);
                        if (matcher1.find()) {
                            String playUrl = matcher1.group(0).replace("\"", "").replace("url:", "").trim();
                            return Result.get().url(playUrl).string();
                        }
                    }
                }

            }
        }
        return null;
    }

    String dncry(String data) {
        String kc8a64 = "336460fdcb76a597";
        String iv = "1234567890983456";

        return AESEncryption.decrypt(data, kc8a64, iv,AESEncryption.CBC_PKCS_7_PADDING);
    }

    ;

    String decodeStr(String _0x267828) {
        int _0x5cd2b5 = (_0x267828.length() - 7) / 2;
        String _0x2191ed = _0x267828.substring(0, _0x5cd2b5);
        String _0x35a256 = _0x267828.substring(_0x5cd2b5 + 7);
        return _0x2191ed + _0x35a256;
    }


}