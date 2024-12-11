package com.github.catvod.spider;


import com.github.catvod.bean.Class;
import com.github.catvod.bean.Filter;
import com.github.catvod.bean.Result;
import com.github.catvod.bean.Vod;
import com.github.catvod.crawler.Spider;
import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.net.OkHttp;
import com.github.catvod.utils.Json;
import com.github.catvod.utils.ProxyVideo;
import com.github.catvod.utils.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NG extends Spider {
    private final String FIND_VIDEO_VOD_LIST = "/api.php/provide/vod_list";
    private final String FIND_CLASSIFICATION = "/api.php/provide/home_nav";
    private final String VIDEO_DETAIL = "/api.php/provide/vod_detail";
    private final String SEARCH_SEARCH = "/api.php/provide/search_result";
    private final List<Class> classList = new ArrayList<>();
    private final LinkedHashMap<String, List<Filter>> filters = new LinkedHashMap<>();
    private final String COMMON_URL = Util.base64Decode("aHR0cDovL3lzLmNoYW5nbWVuZ3l1bi5jb20=");

    private Map<String, String> getParams() {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("devices", "android");
        hashMap.put("deviceModel", "ASUS_I003DD");
        hashMap.put("deviceBrand", "ASUS");
        hashMap.put("deviceVersion", "9");
        hashMap.put("deviceScreen", "2340*1080");
        hashMap.put("appVersionCode", "9");
        hashMap.put("appVersionName", "1.0.9");
        hashMap.put("time", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("imei", "");
        hashMap.put("app", "ylys");
        return hashMap;
    }

    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        long currentTimeMillis = System.currentTimeMillis();
        headers.put("timeMillis", String.valueOf(currentTimeMillis));
        headers.put("sign", Util.MD5(Util.base64Decode("I3VCRnN6ZEVNMG9MMEpSbkA=") + currentTimeMillis));
        return headers;
    }

    @Override
    public String homeContent(boolean filter) {
        if (classList.isEmpty()) {
            String string = OkHttp.string(COMMON_URL + FIND_CLASSIFICATION, getParams(), getHeaders());

            JsonArray filterList = JsonParser.parseString(string).getAsJsonArray();
            for (int index = 0; index < filterList.size(); index++) {
                if (index == 0) continue;
                JsonObject obj = filterList.get(index).getAsJsonObject();
                int id = obj.get("id").getAsInt();
                String name = obj.get("name").getAsString();
                Class clazz = new Class(String.valueOf(id), name);
                classList.add(clazz);
            }
        }
        Map<String, String> params = new HashMap<>(getParams());
           /* for (String s : extend.keySet()) {
                params.put(s, URLEncoder.encode(extend.get(s), "UTF-8"));
            }*/
        params.put("page", "1");
        params.put("id", classList.get(0).getTypeId());
        String string = OkHttp.string(COMMON_URL + FIND_VIDEO_VOD_LIST, params, getHeaders());
        Type type = new TypeToken<Rst<It>>() {
        }.getType();
        Rst<It> resp = Json.parseSafe(string, type);
        List<Vod> vodList = new ArrayList<>();
        if (resp != null && resp.isSuccess()) {
            for (It it : resp.getList()) {
                vodList.add(it.toVod());
            }
        } else {
            SpiderDebug.log("ng cate error: " + string);
        }
        return Result.string(classList, vodList, filters);
    }

    @Override
    public String categoryContent(String tid, String pg, boolean filter, HashMap<String, String> extend) {
        try {
            Map<String, String> params = new HashMap<>(getParams());
           /* for (String s : extend.keySet()) {
                params.put(s, URLEncoder.encode(extend.get(s), "UTF-8"));
            }*/
            params.put("page", pg);
            params.put("id", tid);
            String string = OkHttp.string(COMMON_URL + FIND_VIDEO_VOD_LIST, params, getHeaders());
            Type type = new TypeToken<Rst<It>>() {
            }.getType();
            Rst<It> resp = Json.parseSafe(string, type);
            List<Vod> vodList = new ArrayList<>();
            if (resp != null && resp.isSuccess()) {
                for (It it : resp.getList()) {
                    vodList.add(it.toVod());
                }
            } else {
                SpiderDebug.log("ng cate error: " + string);
            }

            return Result.string(Integer.parseInt(pg), Integer.parseInt(pg) + 1, vodList.size(), Integer.MAX_VALUE, vodList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String detailContent(List<String> ids) {
        Map<String, String> map = new HashMap<>(getParams());
        map.put("id", ids.get(0));
        String string = OkHttp.string(COMMON_URL + VIDEO_DETAIL, map, getHeaders());
        Type type = new TypeToken<Rst<Dt>>() {
        }.getType();
        Rst<Dt> dt = Json.parseSafe(string, type);
        if (!dt.isSuccess()) {
            SpiderDebug.log("ng detail err: " + dt.getMsg());
            return Result.error(dt.getMsg());
        }
        return Result.string(dt.data.toVod());
    }

    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) {
        String string = OkHttp.string(id);
        Type type = new TypeToken<Rst<PlayRst>>() {
        }.getType();
        Rst<PlayRst> rst = Json.parseSafe(string, type);
        if (!rst.isSuccess()) {
            SpiderDebug.log("play err: " + rst.getMsg());
            return Result.error(rst.getMsg());
        }
        Map<String, String> filter = new HashMap<>();

        for (String s : rst.getData().getHeader().keySet()) {
            if (s.equals("User-Agent")) {
                filter.put(s, rst.getData().getHeader().get(s));
            }
        }

        return Result.get().url(ProxyVideo.buildCommonProxyUrl(rst.getData().getUrl(), filter)).string();

    }

    @Override
    public String searchContent(String key, boolean quick) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>(getParams());
        params.put("video_name", URLEncoder.encode(key, "UTF-8"));
        String string = OkHttp.string(COMMON_URL + SEARCH_SEARCH, params, getHeaders());
        Type type = new TypeToken<Rst<List<SearchRst>>>() {
        }.getType();
        Rst<List<SearchRst>> rst = Json.parseSafe(string, type);
        if (!rst.isSuccess()) {
            SpiderDebug.log("ng search error: " + rst.getMsg());
            return Result.error(rst.getMsg());
        }
        return Result.string(rst.getData().get(0).toVodList());

    }

    public static class Rst<T> {
        private int code;
        private String msg;
        private String limit;
        private int pagecount;
        private int total;
        private List<T> list;
        private T data;

        public boolean isSuccess() {
            return code == 1;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public int getPagecount() {
            return pagecount;
        }

        public void setPagecount(int pagecount) {
            this.pagecount = pagecount;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<T> getList() {
            return list;
        }

        public void setList(List<T> list) {
            this.list = list;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }

    public static class It {
        private int id;
        private String img;
        private String name;
        private String score;
        private String msg;

        public Vod toVod() {
            Vod vod = new Vod();
            vod.setVodId(String.valueOf(id));
            vod.setVodName(name);
            vod.setVodRemarks(score);
            vod.setVodPic(img);
            return vod;
        }
    }

    public static class Dt {
        private String name;
        private String year;
        private String score;
        private int hits;
        private String type;
        private String img;
        private String info;
        @SerializedName("total_count")
        private int totalCount;
        @SerializedName("player_info")
        private List<DtIt> playerInfo;

        public Vod toVod() {
            Vod vod = new Vod();
            vod.setVodId(name + score);
            vod.setVodName(name);
            vod.setVodPic(img);
            vod.setVodTag(type);
            vod.setVodRemarks(year);
            vod.setVodContent(info);

            StringBuilder playFrom = new StringBuilder();
            StringBuilder playUrl = new StringBuilder();

            for (DtIt info : playerInfo) {
                playFrom.append(info.getShow()).append("$$$");
                for (VtInfo vtInfo : info.getVideoInfo()) {

                    playUrl.append(vtInfo.getName()).append("$").append(vtInfo.getUrl().get(0));
                    playUrl.append("#");

                }
                playUrl.append("$$$");
            }

            vod.setVodPlayFrom(playFrom.toString());
            vod.setVodPlayUrl(playUrl.toString());
            return vod;
        }
    }

    public static class DtIt {
        private int id;
        private String from;
        private String show;
        @SerializedName("url_count")
        private int urlCount;
        @SerializedName("video_info")
        private List<VtInfo> videoInfo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getShow() {
            return show;
        }

        public void setShow(String show) {
            this.show = show;
        }

        public int getUrlCount() {
            return urlCount;
        }

        public void setUrlCount(int urlCount) {
            this.urlCount = urlCount;
        }

        public List<VtInfo> getVideoInfo() {
            return videoInfo;
        }

        public void setVideoInfo(List<VtInfo> videoInfo) {
            this.videoInfo = videoInfo;
        }
    }

    public static class VtInfo {
        private int id;
        private String name;
        private String pic;
        private List<String> url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public List<String> getUrl() {
            return url;
        }

        public void setUrl(List<String> url) {
            this.url = url;
        }
    }

    public static class PlayRst {
        private String url;
        private Map<String, String> header;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Map<String, String> getHeader() {
            return header;
        }

        public void setHeader(Map<String, String> header) {
            this.header = header;
        }
    }

    public static class SearchRst {
        private int id;
        private String name;
        private List<SearchRstItem> data;

        public List<Vod> toVodList() {
            List<Vod> list = new ArrayList<>();
            for (SearchRstItem datum : data) {
                list.add(datum.toVOd());
            }
            return list;
        }
    }

    public static class SearchRstItem {
        private int id;
        private int type;
        @SerializedName("video_name")
        private String videoName;
        private String qingxidu;
        private String img;
        private String director;
        @SerializedName("main_actor")
        private String mainActor;
        private String category;

        public Vod toVOd() {
            Vod vod = new Vod();
            vod.setVodId(String.valueOf(id));
            vod.setVodTag(qingxidu);
            vod.setVodPic(img);
            vod.setVodRemarks(category);
            vod.setVodName(videoName);
            vod.setVodActor(mainActor);
            return vod;
        }
    }
}
