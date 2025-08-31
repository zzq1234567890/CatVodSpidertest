package com.github.catvod.spider;

import com.github.catvod.bean.Result;
import com.github.catvod.bean.Vod;
import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.net.OkHttp;
import com.github.catvod.utils.Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhixc
 */
public class TianYiSo extends Cloud {

    private final String URL = "https://www.tianyiso.com/";

    private Map<String, String> getHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", Util.CHROME);
        return header;
    }

    private Map<String, String> getSearchHeader() {
        Map<String, String> header = getHeader();
        header.put("referer", URL);
        header.put("Origin", URL);
        return header;
    }

    @Override
    public String searchContent(String key, boolean quick) throws Exception {

        List<Vod> list = new ArrayList<>();
        String url = URL + "search?k=" + URLEncoder.encode(key, Charset.defaultCharset().name());
        String result = OkHttp.string(url, getSearchHeader());
        Elements links = Jsoup.parse(result).select("a");
        for (Element link : links) {
            String path = link.attr("href");
            if (path.startsWith("/s/")) {
                String name = link.select("template").first().text().trim();
                list.add(new Vod(
                        path,
                        name,
                        "",  // vod_pic 留空
                        ""   // vod_remarks 留空
                ));
            }
        }
        return Result.string(list);
    }

    @Override
    public String detailContent(List<String> shareUrl) throws Exception {

        String html = OkHttp.string(URL + shareUrl.get(0), getHeader());
        String url = Util.findByRegex("\"(https://cloud\\.189\\.cn/t/.*)\",", html, 1);
        String result = super.detailContent(List.of(url));
        return result;
    }
}
