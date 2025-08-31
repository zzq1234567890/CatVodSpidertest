package com.github.catvod.spider;

import android.content.Context;
import com.github.catvod.bean.Class;
import com.github.catvod.bean.Result;
import com.github.catvod.bean.Vod;
import com.github.catvod.net.OkHttp;
import com.github.catvod.utils.Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhixc
 */
public class YunPanZiYuan extends Cloud {

    private final String siteUrl = "https://res.yunpan.win/";


    private Map<String, String> getHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", Util.CHROME);
        return header;
    }

    @Override
    public void init(Context context, String extend) throws Exception {
        //  JsonObject ext = Json.safeObject(extend);
        super.init(context, extend);
    }

    @Override
    public String homeContent(boolean filter) {
        List<Class> classes = new ArrayList<>();
        Document doc = Jsoup.parse(OkHttp.string(siteUrl, getHeader()));


        return Result.string(classes, parseVodListFromDoc(doc));
    }


    private List<Vod> parseVodListFromDoc(Document doc) {
        List<Vod> list = new ArrayList<>();
        Elements elements = doc.select("div.row > div.col > div.card  ");
        for (Element e : elements) {
            Elements links = e.select("div.card-footer a");
            String vodPic =siteUrl+ e.selectFirst(" img").attr("src");
            String vodName = e.selectFirst("img").attr("alt");
            String vodRemarks = e.selectFirst("p.card-text").text();
            String vodId = "";
            for (Element link : links) {
                if (link.attr("href").contains("Home/Detail")) {
                    vodId = link.attr("href");
                    break;
                }
            }
            list.add(new Vod(vodId, vodName, vodPic, vodRemarks));
        }
        return list;
    }

    @Override
    public String detailContent(List<String> ids) throws Exception {
        String vodId = ids.get(0);
        Document doc = Jsoup.parse(OkHttp.string(siteUrl + vodId, getHeader()));

        Vod item = new Vod();
        item.setVodId(vodId);
        item.setVodName(doc.selectFirst("h5.card-title").text());
        item.setVodPic(siteUrl+doc.selectFirst("img").attr("src"));
        item.setVodArea("");
        item.setTypeName(doc.selectFirst("p.card-text > a").text());
        String shareLink = Util.findByRegex("window.open\\('(.*?)'\\)", doc.select("div.card-footer > a").attr("onclick"), 1);
        List<String> shareLinks = List.of(shareLink);

        item.setVodPlayUrl(super.detailContentVodPlayUrl(shareLinks));
        item.setVodPlayFrom(super.detailContentVodPlayFrom(shareLinks));

        item.setVodRemarks(doc.selectFirst("p.card-text").text());

        item.setVodContent(doc.selectFirst("p.card-text").text());


        return Result.string(item);
    }

    @Override
    public String searchContent(String key, boolean quick) throws Exception {
        return searchContent(key, "1");
    }

    @Override
    public String searchContent(String key, boolean quick, String pg) throws Exception {
        return searchContent(key, pg);
    }


    private String searchContent(String key, String pg) {
        String searchURL = siteUrl + String.format("?PageIndex=%s&PageSize=12&Keyword=%s", pg, URLEncoder.encode(key));
        String html = OkHttp.string(searchURL, getHeader());

        return Result.string(parseVodListFromDoc(Jsoup.parse(html)));
    }
}