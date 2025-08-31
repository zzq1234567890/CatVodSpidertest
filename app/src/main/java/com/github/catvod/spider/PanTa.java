package com.github.catvod.spider;

import android.content.Context;
import com.github.catvod.bean.Class;
import com.github.catvod.bean.Result;
import com.github.catvod.bean.Vod;
import com.github.catvod.net.OkHttp;
import com.github.catvod.utils.Util;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PanTa extends Cloud {
    private static final String HOST = "https://www.91panta.cn/";


    @Override
    public void init(Context context, String extend) throws Exception {
        //  JsonObject ext = Json.safeObject(extend);
        super.init(context, extend);
    }

    @Override
    public String homeContent(boolean filter) {


        List<Class> classes = new ArrayList<>();
        Document doc = Jsoup.parse(OkHttp.string(HOST));
        Elements elements = doc.select("#tabNavigation > a.tab");
        for (Element element : elements) {

            classes.add(new Class(element.attr("href"), element.text().trim()));

        }


        return Result.string(classes, parseVodListFromDoc(doc));
    }

    @Override
    public String categoryContent(String tid, String pg, boolean filter, HashMap<String, String> extend) {
        String url = HOST + tid + "&page=" + pg;
        Document doc = Jsoup.parse(OkHttp.string(url));
        int page = Integer.parseInt(pg), limit = 30, total = Integer.MAX_VALUE;
        return Result.get().vod(parseVodListFromDoc(doc)).page(page, 99999, limit, total).string();
    }

    private List<Vod> parseVodListFromDoc(Document doc) {
        List<Vod> list = new ArrayList<>();
        Elements elements = doc.select(".topicList > .topicItem");
        for (Element e : elements) {
            String pic = Objects.isNull(e.selectFirst(".tm-m-photos-thumb li")) ? "" : e.selectFirst(".tm-m-photos-thumb li").attr("data-src");
            pic = StringUtils.isAllBlank(pic) ? e.selectFirst("a.avatarLink img").attr("src") : pic;
            Element content = e.selectFirst(".content > h2 > a");
            String vodId = content.attr("href");
            String vodPic = HOST + pic;
            String vodName = content.text();

            list.add(new Vod(vodId, vodName, vodPic));
        }
        return list;
    }
    // 获取视频信息

    @Override
    public String detailContent(List<String> ids) throws Exception {
        String vodId = ids.get(0);
        Document doc = Jsoup.parse(OkHttp.string(HOST + vodId));

        Vod item = new Vod();

        Element titleElement = doc.selectFirst(".title");
        if (titleElement != null) {
            item.setVodName(titleElement.text().trim());
        }
        item.setVodId("/" + vodId);


        // 解析链接
        String contentHtml = doc.selectFirst(".topicContent").html();
        String link = null;

        // 第一种匹配模式：<a href>
        Pattern aPattern = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href=[\"'](https://caiyun\\.139\\.com/[^\"']*)[\"'][^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher aMatcher = aPattern.matcher(contentHtml);
        if (aMatcher.find()) {
            link = aMatcher.group(1);
        }

        // 第二种匹配模式：<span>中的文本
        if (StringUtils.isAllBlank(link)) {
            Pattern spanPattern = Pattern.compile("<span\\s+style=\"color:\\s*#0070C0;\\s*\">(https://caiyun\\.139\\.com/[^<]*)</span>", Pattern.CASE_INSENSITIVE);
            Matcher spanMatcher = spanPattern.matcher(contentHtml);
            if (spanMatcher.find()) {
                link = spanMatcher.group(1);
            }
        }

        // 第三种匹配模式：纯文本
        if (StringUtils.isAllBlank(link)) {
            Pattern textPattern = Pattern.compile("https://caiyun\\.139\\.com/[^<]*");
            Matcher textMatcher = textPattern.matcher(contentHtml);
            if (textMatcher.find()) {
                link = textMatcher.group();
            }
        }


        item.setVodPlayUrl(super.detailContentVodPlayUrl(List.of(link)));
        item.setVodPlayFrom(super.detailContentVodPlayFrom(List.of(link)));

        String text = doc.select("div.topicContent > p").text().trim();

        String director = Util.findByRegex("导演:(.*)主演", text, 1);
        String actor = Util.findByRegex("主演:(.*)类型", text, 1);
        String cat = Util.findByRegex("类型:(.*)制片", text, 1);
        String area = Util.findByRegex("地区:(.*)语言", text, 1);
        String year = Util.findByRegex("上映日期:(.*)片长", text, 1);
        String remark = Util.findByRegex("简介:(.*)", text, 1);
        item.setVodDirector(director);
        item.setVodActor(actor);
        item.setTypeName(cat);
        item.setVodArea(area);
        item.setVodYear(year);
        item.setVodContent(remark);
        return Result.string(item);
    }


    @Override
    public String searchContent(String key, boolean quick, String pg) throws Exception {
        return searchContent(key, pg);
    }

    @Override
    public String searchContent(String key, boolean quick) throws Exception {
        return searchContent(key, "1");
    }


    private String searchContent(String key, String pg) {
        String searchURL = HOST + String.format("search?keyword=%s&page=%s", URLEncoder.encode(key), pg);
        String html = OkHttp.string(searchURL);
        return Result.string(parseVodListFromDoc(Jsoup.parse(html)));
    }
}
