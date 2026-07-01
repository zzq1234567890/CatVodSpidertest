package com.github.catvod.spider;

import android.content.Context;

import com.github.catvod.api.*;
import com.github.catvod.bean.Class;
import com.github.catvod.bean.Result;
import com.github.catvod.bean.Vod;
import com.github.catvod.crawler.Spider;
import com.github.catvod.utils.Launcher;
import com.github.catvod.utils.Notify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Introduce extends Spider {


    @Override
    public void init(Context context, String extend) throws Exception {
        super.init(context, extend);

    }

    @Override
    public String homeContent(boolean filter) throws Exception {
        List<Class> classes = new ArrayList<>();
        classes.add(new Class("1", "UC"));
        classes.add(new Class("2", "quark"));
        classes.add(new Class("3", "天翼"));
        classes.add(new Class("4", "移动"));
        classes.add(new Class("5", "百度"));
        classes.add(new Class("6", "pan123"));
        classes.add(new Class("7", "代理服务"));
        List<Vod> list = new ArrayList<>();
        String pic = "https://androidcatvodspider.netlify.app/wechat.png";
        String name = "关注公众号";
        list.add(new Vod("https://androidcatvodspider.netlify.app/wechat.png", name, pic));
        String pic2 = "https://androidcatvodspider.netlify.app/wechat.png";
        String name2 = "本接口不收费，请不要付费，谢谢！";
        list.add(new Vod("https://androidcatvodspider.netlify.app/wechat.png", name2, pic2));
        String pic3 = "https://androidcatvodspider.netlify.app/wechat.png";
        String name3 = "2025-04-14 11:00";
        list.add(new Vod("https://androidcatvodspider.netlify.app/wechat.png", name3, pic3));
        return Result.string(classes, list);
    }


    @Override
    public String categoryContent(String tid, String pg, boolean filter, HashMap<String, String> extend) {
        List<Vod> vodList = new ArrayList<>();
        //UC
        if (tid.equals("1")) {
            String pic1 = "https://androidcatvodspider.netlify.app/wechat.png";
            String name1 = "点击设置cookie";
            vodList.add(new Vod("UCCookie", name1, pic1));

            String pic = "https://androidcatvodspider.netlify.app/wechat.png";
            String name = "点击设置Token";
            vodList.add(new Vod("UCToken", name, pic));
            String pic3 = "https://androidcatvodspider.netlify.app/wechat.png";
            String name3 = "点击删除";
            vodList.add(new Vod("UCClean", name3, pic3));

        }
        if (tid.equals("2")) {
            String pic = "https://androidcatvodspider.netlify.app/wechat.png";
            String name = "点击设置Cookie";
            vodList.add(new Vod("QuarkCookie", name, pic));
            String pic3 = "https://androidcatvodspider.netlify.app/wechat.png";
            String name3 = "点击删除";
            vodList.add(new Vod("QuarkClean", name3, pic3));
        }
        if (tid.equals("3")) {
            String pic = "https://androidcatvodspider.netlify.app/wechat.png";
            String name = "点击设置账号";
            vodList.add(new Vod("TianYi", name, pic));
            String pic3 = "https://androidcatvodspider.netlify.app/wechat.png";
            String name3 = "点击删除";
            vodList.add(new Vod("TianYiClean", name3, pic3));
        }
        if (tid.equals("4")) {
            String pic = "https://androidcatvodspider.netlify.app/wechat.png";
            String name = "点击设置Cookie";
            vodList.add(new Vod("YiDongCookie", name, pic));
            String pic3 = "https://androidcatvodspider.netlify.app/wechat.png";
            String name3 = "点击删除";
            vodList.add(new Vod("YiDongClean", name3, pic3));
        }
        if (tid.equals("5")) {
            String pic = "https://androidcatvodspider.netlify.app/wechat.png";
            String name = "点击设置百度";
            vodList.add(new Vod("BDCookie", name, pic));
            String pic3 = "https://androidcatvodspider.netlify.app/wechat.png";
            String name3 = "点击删除";
            vodList.add(new Vod("BDClean", name3, pic3));
        }
        if (tid.equals("6")) {
            String pic = "https://androidcatvodspider.netlify.app/wechat.png";
            String name = "点击设置pan123";
            vodList.add(new Vod("Pan123Cookie", name, pic));
            String pic3 = "https://androidcatvodspider.netlify.app/wechat.png";
            String name3 = "点击删除";
            vodList.add(new Vod("Pan123Clean", name3, pic3));
        }
        if (tid.equals("7")) {

            String pic1 = "https://androidcatvodspider.netlify.app/wechat.png";
            String name1 = "删除代理服务文件";
            vodList.add(new Vod("删除代理服务文件", name1, pic1));
        }
        return Result.get().vod(vodList).page().string();
    }

    @Override
    public String detailContent(List<String> ids) throws Exception {
        String vodId = ids.get(0);
        Vod item = new Vod();
        item.setVodId(vodId);
        item.setVodName("公众号");
        item.setVodPic("https://androidcatvodspider.netlify.app/wechat.png");
        item.setVodRemarks("");
        item.setVodPlayFrom("公众号");
        item.setVodPlayUrl("https://test-streams.mux.dev/x36xhzz/url_6/193039199_mp4_h264_aac_hq_7.m3u8");
        item.setVodDirector("公众号");
        //UC Token 扫码
        if (vodId.equals("UCToken")) {
            UCTokenHandler qrCodeHandler = new UCTokenHandler();
            qrCodeHandler.startUC_TOKENScan();
            return Result.string(item);
        } else if (vodId.equals("UCCookie")) {
            UCApi.get().startFlow();
            return Result.string(item);
        } else if (vodId.equals("UCClean")) {
            UCApi.get().getCache().deleteOnExit();
            new UCTokenHandler().getCache().deleteOnExit();
            Notify.show("删除成功");
            return Result.string(item);
        } else if (vodId.equals("QuarkCookie")) {
            QuarkApi.get().initUserInfo();
            return Result.string(item);
        } else if (vodId.equals("QuarkClean")) {
            QuarkApi.get().getCache().deleteOnExit();
            Notify.show("删除成功");
            return Result.string(item);
        } else if (vodId.equals("TianYi")) {
            TianYiHandler tianYiHandler = TianYiHandler.get();
            tianYiHandler.startFlow();
            return Result.string(item);
        } else if (vodId.equals("TianYiClean")) {
            TianYiHandler tianYiHandler = TianYiHandler.get();
            tianYiHandler.getCache().deleteOnExit();
            Notify.show("删除成功");
            return Result.string(item);
        } else if (vodId.equals("YiDongCookie")) {
           /* YunTokenHandler yunTokenHandler=YunTokenHandler.get();
            yunTokenHandler.startFlow();*/
            return Result.string(item);
        } else if (vodId.equals("YiDongClean")) {
            YunTokenHandler yunTokenHandler = YunTokenHandler.get();
            yunTokenHandler.getCache().deleteOnExit();
            Notify.show("删除成功");
            return Result.string(item);
        } else if (vodId.equals("BDCookie")) {
            BaiDuYunHandler baiDuYunHandler = BaiDuYunHandler.get();
            baiDuYunHandler.startScan();
            return Result.string(item);
        } else if (vodId.equals("BDClean")) {
            BaiDuYunHandler baiDuYunHandler = BaiDuYunHandler.get();
            baiDuYunHandler.getCache().deleteOnExit();
            Notify.show("删除成功");
            return Result.string(item);
        } else if (vodId.equals("Pan123Cookie")) {
            Pan123Handler.INSTANCE.startFlow();
            return Result.string(item);
        } else if (vodId.equals("Pan123Clean")) {
            Pan123Handler.INSTANCE.getCache().deleteOnExit();
            Notify.show("删除成功");

            return Result.string(item);
        } else if (vodId.equals("删除代理服务文件")) {
            Launcher.deleteServerFiles();
        }

        return Result.string(item);
    }

    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) throws Exception {
        return Result.get().url("https://test-streams.mux.dev/x36xhzz/url_6/193039199_mp4_h264_aac_hq_7.m3u8").string();
    }
}