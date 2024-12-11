import android.app.Application;

import com.github.catvod.spider.DaGongRen;
import com.github.catvod.spider.Init;
import com.github.catvod.spider.Xb6v;
import com.github.catvod.utils.Json;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@RunWith(RobolectricTestRunner.class)
public class Xb6vTest {
    // @Mock
    private Application mockContext;

    private Xb6v spider;

    @org.junit.Before
    public void setUp() throws Exception {
        mockContext = RuntimeEnvironment.application;
        Init.init(mockContext);
        spider = new Xb6v();
        //spider.init(mockContext, "");
        spider.init(mockContext, "{\"cookie\":\"b-user-id=89ede34e-0efc-e1dd-c997-f16aaa792d0c; _UP_A4A_11_=wb9661c6dfb642f88f73d8e0c7edd398; b-user-id=89ede34e-0efc-e1dd-c997-f16aaa792d0c; ctoken=wla6p3EUOLyn1FSB8IKp1SEW; grey-id=5583e32b-39df-4bf0-f39f-1adf83f604a2; grey-id.sig=p8ReBIMG2BeZu1sYvsuOAZxYbx-MVrsfKEiCv87MsTM; isQuark=true; isQuark.sig=hUgqObykqFom5Y09bll94T1sS9abT1X-4Df_lzgl8nM; _UP_F7E_8D_=ZkyvVHnrBLp1A1NFJIjWi0PwKLOVbxJPcg0RzQPI6KmBtV6ZMgPh38l93pgubgHDQqhaZ2Sfc0qv%2BRantbfg1mWGAUpRMP4RqXP78Wvu%2FCfvkWWGc5NhCTV71tGOIGgDBR3%2Bu6%2Fjj44KlE5biSNDOWW7Bigcz27lvOTidzNw8s%2FWtKAIxWbnCzZn4%2FJMBUub1SIMcW89g57k4mfPmDlCgpZKzxwl6beSfdtZ4RUWXmZOn5v5NkxVKhU4wR0Pq7NklczEGdRq2nIAcu7v22Uw2o%2FxMY0xBdeC9Korm5%2FNHnxl6K%2Bd6FXSoT9a3XIMQO359auZPiZWzrNlZe%2BqnOahXcx7KAhQIRqSOapSmL4ygJor4r5isJhRuDoXy7vJAVuH%2FRDtEJJ8rZTq0BdC23Bz%2B0MrsdgbK%2BiW; _UP_D_=pc; __wpkreporterwid_=3d3f74a7-99b7-4916-3f78-911fc2eb9d87; tfstk=fIoZNxjnbhKwPOu0TWZ4LsaRqirTcudSSmNbnxD0C5VgClMm8xMyB-GsnSu4tjpOflAOmSD-9PNiGl120XrgkVNb1SrqHbJBN3tSBAEYoQOWVUUg9qZ8n1bGGkD3CqGYINKSBABhjnXgp3_Vywz6gSc0Syj3BWf0mr2DLW24eZfiiovEKWefj1q0swq3E82iNEMinMy7SLrcpA4Fh3z_ZAViCfih3PbtdW5N_DuU77AaTijmYRkL2Wq54ENoy5a7ZXxCbok33XzS7QSZgxD-oyoVsdGotql0p2dVu7umC4nLStbiLmParc4FELHrI-c0u2dPVRrs8zoZWKCnIbNZrlHfUCMUz2z8KyXVSlgSFmUojh58OzeqTzgwaGll4YCYKwctDV5coP2LL79eKHxpNTXHmre1kZU32JPWCR_AkP2LL79eLZQY-WeUNdw1.; __pus=2051c82285199d8be553be41dd5a2100AAQ+mmv35G4FDDZ5x+3Mhe2OMbNgweQ1ODbW8zDt9YuP1LQVqHUuAAz9KWLsPjpNtim0AVGHusN4MCosTmbq/khM; __kp=e6604120-6051-11ef-bfe4-c31b6cdd0766; __kps=AATcZArVgS76EPn0FMaV4HEj; __ktd=sii/iz4ePzEaoVirXul7QQ==; __uid=AATcZArVgS76EPn0FMaV4HEj; __itrace_wid=5829b95d-dac1-48d3-bfd5-f60cd9462786; __puus=7da0b96cb710fa1b376934485f977e05AATp/q8/QupT7IiBR1GWqZhxlIRT677smMvoHlLxQA0Lk6CkP0YJBOTl+p9DZgzlMz6w4hPXPgWsokukk8PW7ZfhFfPmv8tKMgLpCGLW+tk57luhNghmSdTeVPkAF59STtyCPBEtiNzNAd/zZJ6qILJDi5ywEBAAAg+gOyWHoLHNUR+QxeHRuQa8g5WWA95J8jebIlrr8rCvI1vjTbtiYktT\",\"token\":\"26fc6787afff43e78b78992e782502f1\"}");

    }

    @org.junit.Test
    public void homeContent() throws Exception {
        String content = spider.homeContent(true);
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println("homeContent--" + gson.toJson(map));

        //Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @org.junit.Test
    public void homeVideoContent() throws Exception {
        String content = spider.homeVideoContent();
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println("homeVideoContent--" + gson.toJson(map));

        //Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @org.junit.Test
    public void categoryContent() throws Exception {
        String content = spider.categoryContent("/xijupian/", "2", true, new HashMap<>());
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("categoryContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @org.junit.Test
    public void detailContent() throws Exception {

        String content = spider.detailContent(Arrays.asList("/juqingpian/22881.html"));
        System.out.println("detailContent--" + content);

        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("detailContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @org.junit.Test
    public void playerContent() throws Exception {
        String froms = "磁力线路$$$播放地址（无插件 极速播放）$$$播放地址（无需安装插件）$$$播放地址三$$$播放地址四$$$quark4K#01$$$quark超清#01$$$quark高清#01$$$quark普画#01$$$quark4K#02$$$quark超清#02$$$quark高清#02$$$quark普画#02";
        String urls = "2160p高码版.60fps.HD国语中字无水印.mkv$magnet:?xt=urn:btih:cd927d9627d84e692fddec55129af760e698ae0a&dn=%e5%ad%a4zy%e6%8e%b7.2160p%e9%ab%98%e7%a0%81%e7%89%88.60fps&tr=udp%3a%2f%2ftracker.altrosky.nl%3a6969%2fannounce&tr=udp%3a%2f%2fopentracker.i2p.rocks%3a6969%2fannounce#2160p高码版.HD国语中字无水印.mkv$magnet:?xt=urn:btih:6f051c3e59eb4a8d2d4c08bffdef4b9521f6833a&dn=%e5%ad%a4zy%e6%8e%b7.2160p%e9%ab%98%e7%a0%81%e7%89%88&tr=udp%3a%2f%2ftracker.altrosky.nl%3a6969%2fannounce&tr=udp%3a%2f%2fopentracker.i2p.rocks%3a6969%2fannounce#2160p.60fps.HD国语中字无水印.mkv$magnet:?xt=urn:btih:641475ad9d2ec323d4bf1f02d9d55cb2adf6ce41&dn=%e5%ad%a4zy%e6%8e%b7.2160p.60fps.6v%e7%94%b5%e5%bd%b1%20%e5%9c%b0%e5%9d%80%e5%8f%91%e5%b8%83%e9%a1%b5%20www.6v123.net%20%e6%94%b6%e8%97%8f%e4%b8%8d%e8%bf%b7%e8%b7%af&tr=udp%3a%2f%2fopentracker.i2p.rocks%3a6969%2fannounce&tr=udp%3a%2f%2ftracker.altrosky.nl%3a6969%2fannounce#1080p.HD国语中字无水印.mkv$magnet:?xt=urn:btih:b40b1e7da000af7b28e162dbbf86a93a577f2143&dn=%e5%ad%a4zy%e6%8e%b7.6v%e7%94%b5%e5%bd%b1%20%e5%9c%b0%e5%9d%80%e5%8f%91%e5%b8%83%e9%a1%b5%20www.6v123.net%20%e6%94%b6%e8%97%8f%e4%b8%8d%e8%bf%b7%e8%b7%af&tr=udp%3a%2f%2ftracker.altrosky.nl%3a6969%2fannounce&tr=udp%3a%2f%2fopentracker.i2p.rocks%3a6969%2fannounce$$$正片$/e/DownSys/play/?classid=17&id=22881&pathid1=0&bf=0$$$HD$/e/DownSys/play/?classid=17&id=22881&pathid2=0&bf=1$$$正片$/e/DownSys/play/?classid=17&id=22881&pathid3=0&bf=2$$$HD$/e/DownSys/play/?classid=17&id=22881&pathid4=0&bf=3$$$孤zy掷.1080p.HD国语中字无水印[电影港www.dygangs.me].mkv 2.21GB$0429fdb6884e4b28a3727a43d53cf900++3a38197cc1517cc80be43ee5a2334620++69cd2c019967++LKHD6uLQtQxaoC/oAyIRcxny8NqC9LAuSBP1E+3E31Q=#孤zy掷.2160p.60fps.HD国语中字无水印[电影港www.dygangs.me].mkv 4.94GB$b5508b7e6c534a6b853029498ee9a88c++474a903bfc4411e03bd3dba2f8ec9f4b++69cd2c019967++LKHD6uLQtQxaoC/oAyIRcxny8NqC9LAuSBP1E+3E31Q=$$$孤zy掷.1080p.HD国语中字无水印[电影港www.dygangs.me].mkv 2.21GB$0429fdb6884e4b28a3727a43d53cf900++3a38197cc1517cc80be43ee5a2334620++69cd2c019967++LKHD6uLQtQxaoC/oAyIRcxny8NqC9LAuSBP1E+3E31Q=#孤zy掷.2160p.60fps.HD国语中字无水印[电影港www.dygangs.me].mkv 4.94GB$b5508b7e6c534a6b853029498ee9a88c++474a903bfc4411e03bd3dba2f8ec9f4b++69cd2c019967++LKHD6uLQtQxaoC/oAyIRcxny8NqC9LAuSBP1E+3E31Q=$$$孤zy掷.1080p.HD国语中字无水印[电影港www.dygangs.me].mkv 2.21GB$0429fdb6884e4b28a3727a43d53cf900++3a38197cc1517cc80be43ee5a2334620++69cd2c019967++LKHD6uLQtQxaoC/oAyIRcxny8NqC9LAuSBP1E+3E31Q=#孤zy掷.2160p.60fps.HD国语中字无水印[电影港www.dygangs.me].mkv 4.94GB$b5508b7e6c534a6b853029498ee9a88c++474a903bfc4411e03bd3dba2f8ec9f4b++69cd2c019967++LKHD6uLQtQxaoC/oAyIRcxny8NqC9LAuSBP1E+3E31Q=$$$孤zy掷.1080p.HD国语中字无水印[电影港www.dygangs.me].mkv 2.21GB$0429fdb6884e4b28a3727a43d53cf900++3a38197cc1517cc80be43ee5a2334620++69cd2c019967++LKHD6uLQtQxaoC/oAyIRcxny8NqC9LAuSBP1E+3E31Q=#孤zy掷.2160p.60fps.HD国语中字无水印[电影港www.dygangs.me].mkv 4.94GB$b5508b7e6c534a6b853029498ee9a88c++474a903bfc4411e03bd3dba2f8ec9f4b++69cd2c019967++LKHD6uLQtQxaoC/oAyIRcxny8NqC9LAuSBP1E+3E31Q=$$$孤zy掷.2160p高码版.60fps.HD国语中字无水印[电影港www.dygangs.me].mkv 24.97GB$6ec63018d31e4f35889a7d0485c1a358++6f0894336dad8628e755a985e4f38a2e++9b816f82f18c++CsgPVU9R675wVokFx0dLsanmv00Xon3FLwBcjC6ugX0=#孤zy掷.2160p高码版.HD国语中字无水印[电影港www.dygangs.me].mkv 11.58GB$9e6e30b7a4144ca0bc9f5efd7f086089++5e3afc00c2d4a1e60418a39ae7094113++9b816f82f18c++CsgPVU9R675wVokFx0dLsanmv00Xon3FLwBcjC6ugX0=$$$孤zy掷.2160p高码版.60fps.HD国语中字无水印[电影港www.dygangs.me].mkv 24.97GB$6ec63018d31e4f35889a7d0485c1a358++6f0894336dad8628e755a985e4f38a2e++9b816f82f18c++CsgPVU9R675wVokFx0dLsanmv00Xon3FLwBcjC6ugX0=#孤zy掷.2160p高码版.HD国语中字无水印[电影港www.dygangs.me].mkv 11.58GB$9e6e30b7a4144ca0bc9f5efd7f086089++5e3afc00c2d4a1e60418a39ae7094113++9b816f82f18c++CsgPVU9R675wVokFx0dLsanmv00Xon3FLwBcjC6ugX0=$$$孤zy掷.2160p高码版.60fps.HD国语中字无水印[电影港www.dygangs.me].mkv 24.97GB$6ec63018d31e4f35889a7d0485c1a358++6f0894336dad8628e755a985e4f38a2e++9b816f82f18c++CsgPVU9R675wVokFx0dLsanmv00Xon3FLwBcjC6ugX0=#孤zy掷.2160p高码版.HD国语中字无水印[电影港www.dygangs.me].mkv 11.58GB$9e6e30b7a4144ca0bc9f5efd7f086089++5e3afc00c2d4a1e60418a39ae7094113++9b816f82f18c++CsgPVU9R675wVokFx0dLsanmv00Xon3FLwBcjC6ugX0=$$$孤zy掷.2160p高码版.60fps.HD国语中字无水印[电影港www.dygangs.me].mkv 24.97GB$6ec63018d31e4f35889a7d0485c1a358++6f0894336dad8628e755a985e4f38a2e++9b816f82f18c++CsgPVU9R675wVokFx0dLsanmv00Xon3FLwBcjC6ugX0=#孤zy掷.2160p高码版.HD国语中字无水印[电影港www.dygangs.me].mkv 11.58GB$9e6e30b7a4144ca0bc9f5efd7f086089++5e3afc00c2d4a1e60418a39ae7094113++9b816f82f18c++CsgPVU9R675wVokFx0dLsanmv00Xon3FLwBcjC6ugX0=";
        for (int i = 0; i < urls.split("\\$\\$\\$").length; i++) {
            for (String s : urls.split("\\$\\$\\$")[i].split("#")) {

                String content = spider.playerContent(froms.split("\\$\\$\\$")[i], s.split("\\$")[1], new ArrayList<>());
                System.out.println("playerContent--" + content);

                JsonObject map = Json.safeObject(content);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                System.out.println("playerContent--" + gson.toJson(map));
                Assert.assertFalse(map.getAsJsonPrimitive("url").getAsString().isEmpty());
            }

        }
    }

    @org.junit.Test
    public void searchContent() throws Exception {
        String content = spider.searchContent("红海", false);
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("searchContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }
}