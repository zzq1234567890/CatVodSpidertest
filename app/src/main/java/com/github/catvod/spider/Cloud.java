package com.github.catvod.spider;

import android.content.Context;
import android.text.TextUtils;
import com.github.catvod.api.TianyiApi;
import com.github.catvod.crawler.Spider;
import com.github.catvod.utils.Json;
import com.github.catvod.utils.Util;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.catvod.api.TianyiApi.URL_START;

/**
 * @author ColaMint & Adam & FongMi
 */
public class Cloud extends Spider {
    private Quark quark = null;
   /* private Ali ali = null;
    private UC uc = null;*/
    private TianYi tianYi = null;
    private YiDongYun yiDongYun = null;

    @Override
    public void init(Context context, String extend) throws Exception {
        JsonObject ext = Json.safeObject(extend);
        quark = new Quark();
       /* uc = new UC();
        ali = new Ali();*/
        tianYi = new TianYi();
        yiDongYun = new YiDongYun();
        boolean first = Objects.nonNull(ext);
        quark.init(context, first && ext.has("cookie") ? ext.get("cookie").getAsString() : "");
      /*  uc.init(context, first && ext.has("uccookie") ? ext.get("uccookie").getAsString() : "");
        ali.init(context, first && ext.has("token") ? ext.get("token").getAsString() : "");*/
        tianYi.init(context, first && ext.has("tianyicookie") ? ext.get("tianyicookie").getAsString() : "");
        yiDongYun.init(context, "");

    }

    @Override
    public String detailContent(List<String> shareUrl) throws Exception {
       /* if (shareUrl.get(0).matches(Util.patternAli)) {
            return ali.detailContent(shareUrl);
        } else */if (shareUrl.get(0).matches(Util.patternQuark)) {
            return quark.detailContent(shareUrl);
        } /*else if (shareUrl.get(0).matches(Util.patternUC)) {
            return uc.detailContent(shareUrl);
        } */else if (shareUrl.get(0).startsWith(TianyiApi.URL_START)) {
            return tianYi.detailContent(shareUrl);
        } else if (shareUrl.get(0).contains(YiDongYun.URL_START)) {
            return yiDongYun.detailContent(shareUrl);
        }
        return null;
    }

    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) throws Exception {
        if (flag.contains("quark")) {
            return quark.playerContent(flag, id, vipFlags);
        } /*else if (flag.contains("uc")) {
            return uc.playerContent(flag, id, vipFlags);
        } */else if (flag.contains("天意")) {
            return tianYi.playerContent(flag, id, vipFlags);
        } else if (flag.contains("移动")) {
            return yiDongYun.playerContent(flag, id, vipFlags);
        }/* else {
            return ali.playerContent(flag, id, vipFlags);
        }*/
        return flag;
    }

    protected String detailContentVodPlayFrom(List<String> shareLinks) {
        List<String> from = new ArrayList<>();
        int i = 0;
        for (String shareLink : shareLinks) {
            i++;
            /*if (shareLink.matches(Util.patternUC)) {
                from.add(uc.detailContentVodPlayFrom(List.of(shareLink), i));
            } else*/ if (shareLink.matches(Util.patternQuark)) {
                from.add(quark.detailContentVodPlayFrom(List.of(shareLink), i));
            } /*else if (shareLink.matches(Util.patternAli)) {
                from.add(ali.detailContentVodPlayFrom(List.of(shareLink), i));
            } */else if (shareLink.startsWith(URL_START)) {
                from.add(tianYi.detailContentVodPlayFrom(List.of(shareLink), i));
            } else if (shareLink.contains(YiDongYun.URL_START)) {
                from.add(yiDongYun.detailContentVodPlayFrom(List.of(shareLink), i));
            }
        }

        return TextUtils.join("$$$", from);
    }

    protected String detailContentVodPlayUrl(List<String> shareLinks) throws Exception {
        List<String> urls = new ArrayList<>();
        for (String shareLink : shareLinks) {
           /* if (shareLink.matches(Util.patternUC)) {
                urls.add(uc.detailContentVodPlayUrl(List.of(shareLink)));
            } else */if (shareLink.matches(Util.patternQuark)) {
                urls.add(quark.detailContentVodPlayUrl(List.of(shareLink)));
            }/* else if (shareLink.matches(Util.patternAli)) {
                urls.add(ali.detailContentVodPlayUrl(List.of(shareLink)));
            } */else if (shareLink.startsWith(URL_START)) {
                urls.add(tianYi.detailContentVodPlayUrl(List.of(shareLink)));
            } else if (shareLink.contains(YiDongYun.URL_START)) {
                urls.add(yiDongYun.detailContentVodPlayUrl(List.of(shareLink)));
            }
        }
        return TextUtils.join("$$$", urls);
    }
}
