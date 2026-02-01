package com.github.catvod.spider;

import android.content.Context;
import android.text.TextUtils;
import com.github.catvod.api.Pan123Api;
import com.github.catvod.crawler.Spider;
import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.utils.Json;
import com.github.catvod.utils.Util;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;
import java.util.concurrent.*;

import static com.github.catvod.api.TianyiApi.URL_CONTAIN;

/**
 * @author ColaMint & Adam & FongMi
 */
public class Cloud extends Spider {
    private Quark quark = null;
    /*   private Ali ali = null;*/
    private UC uc = null;
    private TianYi tianYi = null;
    private YiDongYun yiDongYun = null;
    private BaiDuPan baiDuPan = null;
    private Pan123 pan123 = null;
    private static final Map<String, ImmutablePair<List<String>, List<String>>> resultMap = new HashMap<>();

    @Override
    public void init(Context context, String extend) throws Exception {
        JsonObject ext = Json.safeObject(extend);
        quark = new Quark();
        uc = new UC();
        /*  ali = new Ali();*/
        tianYi = new TianYi();
        yiDongYun = new YiDongYun();
        baiDuPan = new BaiDuPan();
        pan123 = new Pan123();
        boolean first = Objects.nonNull(ext);
        quark.init(context, first && ext.has("cookie") ? ext.get("cookie").getAsString() : "");
        uc.init(context, first && ext.has("uccookie") ? ext.get("uccookie").getAsString() : "");
        /*   ali.init(context, first && ext.has("token") ? ext.get("token").getAsString() : "");*/
        tianYi.init(context, first && ext.has("tianyicookie") ? ext.get("tianyicookie").getAsString() : "");
        yiDongYun.init(context, "");
        baiDuPan.init(context, "");
        pan123.init(context, "");

    }

    @Override
    public String detailContent(List<String> shareUrl) throws Exception {
        SpiderDebug.log("cloud detailContent shareUrl：" + Json.toJson(shareUrl));

       /* if (shareUrl.get(0).matches(Util.patternAli)) {
            return ali.detailContent(shareUrl);
        } else */
        if (shareUrl.get(0).matches(Util.patternQuark)) {
            return quark.detailContent(shareUrl);
        } else if (shareUrl.get(0).matches(Util.patternUC)) {
            return uc.detailContent(shareUrl);
        } else if (shareUrl.get(0).contains(URL_CONTAIN)) {
            return tianYi.detailContent(shareUrl);
        } else if (shareUrl.get(0).contains(YiDongYun.URL_START)) {
            return yiDongYun.detailContent(shareUrl);
        } else if (shareUrl.get(0).contains(BaiDuPan.URL_START)) {
            return baiDuPan.detailContent(shareUrl);
        } else if (shareUrl.get(0).matches(Pan123Api.regex)) {
            SpiderDebug.log("Pan123Api shareUrl：" + Json.toJson(shareUrl));
            return pan123.detailContent(shareUrl);
        }
        return null;
    }

    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) throws Exception {
        SpiderDebug.log("cloud playerContent flag：" + flag + " id：" + id);

        if (flag.contains("quark")) {
            return quark.playerContent(flag, id, vipFlags);
        } else if (flag.contains("uc")) {
            return uc.playerContent(flag, id, vipFlags);
        } else if (flag.contains("天意")) {
            return tianYi.playerContent(flag, id, vipFlags);
        } else if (flag.contains("移动")) {
            return yiDongYun.playerContent(flag, id, vipFlags);
        }/* else {
            return ali.playerContent(flag, id, vipFlags);
        }*/ else if (flag.contains("BD")) {
            return baiDuPan.playerContent(flag, id, vipFlags);
        } else if (flag.contains("pan123")) {
            return pan123.playerContent(flag, id, vipFlags);
        }
        return flag;
    }

    protected String detailContentVodPlayFrom(List<String> shareLinks) {
        ImmutablePair<List<String>, List<String>> pairs = resultMap.get(Util.MD5(Json.toJson(shareLinks)));
        if (pairs != null && pairs.left != null && !pairs.left.isEmpty()) {
            return TextUtils.join("$$$", pairs.right);
        }

        getPlayFromAndUrl(shareLinks);
        pairs = resultMap.get(Util.MD5(Json.toJson(shareLinks)));
        if (pairs != null && pairs.left != null && !pairs.left.isEmpty()) {
            return TextUtils.join("$$$", pairs.right);
        }
        return "";

    }

    protected String detailContentVodPlayUrl(List<String> shareLinks) {
        ImmutablePair<List<String>, List<String>> pairs = resultMap.get(Util.MD5(Json.toJson(shareLinks)));
        if (pairs != null && pairs.left != null && !pairs.left.isEmpty()) {
            return TextUtils.join("$$$", pairs.left);
        }

        getPlayFromAndUrl(shareLinks);
        pairs = resultMap.get(Util.MD5(Json.toJson(shareLinks)));
        if (pairs != null && pairs.left != null && !pairs.left.isEmpty()) {
            return TextUtils.join("$$$", pairs.left);
        }
        return "";
    }


    //同時获取from 和url ，放入缓存，只要一个函数执行就行，避免重复执行
    private void getPlayFromAndUrl(List<String> shareLinks) {
        ExecutorService service = Executors.newFixedThreadPool(4);
        try {  //首先清空缓存，避免太多缓存
            resultMap.clear();
            List<String> urls = new ArrayList<>();
            List<String> froms = new ArrayList<>();
            Map<String, String> map = new ConcurrentHashMap<>(shareLinks.size());


            CountDownLatch latch = new CountDownLatch(shareLinks.size());
            int i = 0;
            for (String shareLink : shareLinks) {

                int finalI = ++i;
                service.submit(() -> {

                    String url = "";
                    String from = "";
                    if (shareLink.matches(Util.patternUC)) {
                        url = uc.detailContentVodPlayUrl(List.of(shareLink));
                        from = uc.detailContentVodPlayFrom(List.of(shareLink), finalI);
                    } else if (shareLink.matches(Util.patternQuark)) {
                        url = quark.detailContentVodPlayUrl(List.of(shareLink));
                        from = quark.detailContentVodPlayFrom(List.of(shareLink), finalI);
                    }/* else if (shareLink.matches(Util.patternAli)) {
                urls.add(ali.detailContentVodPlayUrl(List.of(shareLink)));
            } */ else if (shareLink.contains(URL_CONTAIN)) {
                        url = tianYi.detailContentVodPlayUrl(List.of(shareLink));
                        from = tianYi.detailContentVodPlayFrom(List.of(shareLink), finalI);
                    } else if (shareLink.contains(YiDongYun.URL_START)) {
                        url = yiDongYun.detailContentVodPlayUrl(List.of(shareLink));
                        from = yiDongYun.detailContentVodPlayFrom(List.of(shareLink), finalI);
                    } else if (shareLink.contains(BaiDuPan.URL_START)) {
                        url = baiDuPan.detailContentVodPlayUrl(List.of(shareLink));
                        from = baiDuPan.detailContentVodPlayFrom(List.of(shareLink), finalI);
                    } else if (shareLink.matches(Pan123Api.regex)) {
                        url = pan123.detailContentVodPlayUrl(List.of(shareLink));
                        from = pan123.detailContentVodPlayFrom(List.of(shareLink), finalI);
                    }
                    //只有连接不为空才放入进去
                    if (StringUtils.isNoneBlank(url)) {

                        map.put(url, from);
                    }
                    latch.countDown();

                });


            }

            latch.await();

            for (Map.Entry<String, String> entry : map.entrySet()) {
                urls.add(entry.getKey());
                froms.add(entry.getValue());
            }

            resultMap.put(Util.MD5(Json.toJson(shareLinks)), new ImmutablePair<>(urls, froms));

            SpiderDebug.log("---urls：" + Json.toJson(urls));
            SpiderDebug.log("---froms：" + Json.toJson(froms));
        } catch (Exception e) {
            SpiderDebug.log("获取异步结果出错：" + e);
        } finally {
            service.shutdown();
        }


    }
}
