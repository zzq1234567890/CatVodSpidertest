package com.github.catvod.spider;

import android.content.Context;
import android.text.TextUtils;
import com.github.catvod.crawler.Spider;
import com.github.catvod.utils.Json;
import com.github.catvod.utils.Util;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ColaMint & Adam & FongMi
 */
public class Cloud extends Spider {
    private Quark quark = null;
    private Ali ali = null;
    private UC uc = null;

    @Override
    public void init(Context context, String extend) throws Exception {
        JsonObject ext = Json.safeObject(extend);
        quark = new Quark();
        uc = new UC();
        ali = new Ali();
        quark.init(context, ext.has("cookie") ? ext.get("cookie").getAsString() : "");
        uc.init(context, ext.has("uccookie") ? ext.get("uccookie").getAsString() : "");
        ali.init(context, ext.has("token") ? ext.get("token").getAsString() : "");
    }

    @Override
    public String detailContent(List<String> shareUrl) throws Exception {
        if (shareUrl.get(0).matches(Util.patternAli)) {
            return ali.detailContent(shareUrl);
        } else if (shareUrl.get(0).matches(Util.patternQuark)) {
            return quark.detailContent(shareUrl);
        } else if (shareUrl.get(0).matches(Util.patternUC)) {
            return uc.detailContent(shareUrl);
        }
        return null;
    }

    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) throws Exception {
        if (flag.contains("quark")) {
            return quark.playerContent(flag, id, vipFlags);
        } else if (flag.contains("uc")) {
            return uc.playerContent(flag, id, vipFlags);
        } else {
            return ali.playerContent(flag, id, vipFlags);
        }
    }

    protected String detailContentVodPlayFrom(List<String> shareLinks) {
        List<String> from = new ArrayList<>();

        for (String shareLink : shareLinks) {
            if (shareLink.matches(Util.patternUC)) {
                from.add(uc.detailContentVodPlayFrom(List.of(shareLink)));
            } else if (shareLink.matches(Util.patternQuark)) {
                from.add(quark.detailContentVodPlayFrom(List.of(shareLink)));
            } else if (shareLink.matches(Util.patternAli)) {
                from.add(ali.detailContentVodPlayFrom(List.of(shareLink)));
            }
        }

        return TextUtils.join("$$$", from);
    }

    protected String detailContentVodPlayUrl(List<String> shareLinks) throws Exception {
        List<String> urls = new ArrayList<>();
        for (String shareLink : shareLinks) {
            if (shareLink.matches(Util.patternUC)) {
                urls.add(uc.detailContentVodPlayUrl(List.of(shareLink)));
            } else if (shareLink.matches(Util.patternQuark)) {
                urls.add(quark.detailContentVodPlayUrl(List.of(shareLink)));
            } else if (shareLink.matches(Util.patternAli)) {
                urls.add(ali.detailContentVodPlayUrl(List.of(shareLink)));
            }
        }
        return TextUtils.join("$$$", urls);
    }
}
