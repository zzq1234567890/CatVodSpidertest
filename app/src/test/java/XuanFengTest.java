import android.app.Application;

import com.github.catvod.spider.ChangZhang;
import com.github.catvod.spider.Init;
import com.github.catvod.spider.XuanFeng;
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

@RunWith(RobolectricTestRunner.class)
public class XuanFengTest {
    // @Mock
    private Application mockContext;

    private XuanFeng  spider;

    @org.junit.Before
    public void setUp() throws Exception {
        mockContext = RuntimeEnvironment.application;
        Init.init(mockContext);
        spider = new XuanFeng();
        spider.init(mockContext, "https://www.czzy.site/");
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
        String content = spider.categoryContent("/tag/悬疑片/1", "2", true, null);
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("categoryContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @org.junit.Test
    public void detailContent() throws Exception {

        String content = spider.detailContent(Arrays.asList("/video/8dFoErWcVsJJLsJd7sFPMQ"));
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("detailContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @org.junit.Test
    public void playerContent() throws Exception {
        String froms = "量子$$$淘片";
        String urls ="第01集$https://v.cdnlz3.com/20240731/25186_490331f6/index.m3u8#第02集$https://v.cdnlz3.com/20240731/25187_c7e06160/index.m3u8#第03集$https://v.cdnlz3.com/20240731/25189_28570e77/index.m3u8#第04集$https://v.cdnlz3.com/20240731/25188_a0d85819/index.m3u8#第05集$https://v.cdnlz3.com/20240801/25231_ffa23109/index.m3u8#第06集$https://v.cdnlz3.com/20240801/25232_7cdfc398/index.m3u8#第07集$https://v.cdnlz12.com/20240802/16173_42dcc85e/index.m3u8#第08集$https://v.cdnlz12.com/20240802/16174_ff0b89d2/index.m3u8#第09集$https://v.cdnlz3.com/20240803/25279_d5059500/index.m3u8#第10集$https://v.cdnlz3.com/20240803/25280_cda61290/index.m3u8#第11集$https://v.cdnlz12.com/20240804/16205_5bc22654/index.m3u8#第12集$https://v.cdnlz12.com/20240804/16206_3bcaf472/index.m3u8#第13集$https://v.cdnlz3.com/20240805/25321_bc51b2b3/index.m3u8#第14集$https://v.cdnlz3.com/20240805/25322_cadf8e24/index.m3u8#第15集$https://v.cdnlz12.com/20240806/16229_bee0732e/index.m3u8#第16集$https://v.cdnlz12.com/20240806/16228_a9b865e1/index.m3u8#第17集$https://v.cdnlz3.com/20240807/25396_52a2dde7/index.m3u8#第18集$https://v.cdnlz3.com/20240807/25397_4bf5838a/index.m3u8#第19集$https://v.cdnlz3.com/20240808/25421_08a90c9a/index.m3u8#第20集$https://v.cdnlz3.com/20240808/25422_d6341248/index.m3u8#第21集$https://v.cdnlz3.com/20240809/25447_34159679/index.m3u8#第22集$https://v.cdnlz3.com/20240809/25448_66e070eb/index.m3u8#第23集$https://v.cdnlz3.com/20240810/25478_d1ef4ec0/index.m3u8#第24集$https://v.cdnlz3.com/20240810/25479_c7db7c2b/index.m3u8#第25集$https://v.cdnlz3.com/20240811/25518_08a2d2dd/index.m3u8#第26集$https://v.cdnlz3.com/20240811/25519_dfeb62f9/index.m3u8#第27集$https://v.cdnlz3.com/20240812/25553_fc7bd8f6/index.m3u8#第28集$https://v.cdnlz3.com/20240812/25554_20b4b783/index.m3u8#第29集$https://v.cdnlz3.com/20240813/25575_7df14a3e/index.m3u8#第30集$https://v.cdnlz3.com/20240813/25576_a012c969/index.m3u8$$$第01集$https://v.cdnlz3.com/20240731/25186_490331f6/index.m3u8#第02集$https://v.cdnlz3.com/20240731/25187_c7e06160/index.m3u8#第03集$https://v.cdnlz3.com/20240731/25189_28570e77/index.m3u8#第04集$https://v.cdnlz3.com/20240731/25188_a0d85819/index.m3u8#第05集$https://v.cdnlz3.com/20240801/25231_ffa23109/index.m3u8#第06集$https://v.cdnlz3.com/20240801/25232_7cdfc398/index.m3u8#第07集$https://v.cdnlz12.com/20240802/16173_42dcc85e/index.m3u8#第08集$https://v.cdnlz12.com/20240802/16174_ff0b89d2/index.m3u8#第09集$https://v.cdnlz3.com/20240803/25279_d5059500/index.m3u8#第10集$https://v.cdnlz3.com/20240803/25280_cda61290/index.m3u8#第11集$https://v.cdnlz12.com/20240804/16205_5bc22654/index.m3u8#第12集$https://v.cdnlz12.com/20240804/16206_3bcaf472/index.m3u8#第13集$https://v.cdnlz3.com/20240805/25321_bc51b2b3/index.m3u8#第14集$https://v.cdnlz3.com/20240805/25322_cadf8e24/index.m3u8#第15集$https://v.cdnlz12.com/20240806/16229_bee0732e/index.m3u8#第16集$https://v.cdnlz12.com/20240806/16228_a9b865e1/index.m3u8#第17集$https://v.cdnlz3.com/20240807/25396_52a2dde7/index.m3u8#第18集$https://v.cdnlz3.com/20240807/25397_4bf5838a/index.m3u8#第19集$https://v.cdnlz3.com/20240808/25421_08a90c9a/index.m3u8#第20集$https://v.cdnlz3.com/20240808/25422_d6341248/index.m3u8#第21集$https://v.cdnlz3.com/20240809/25447_34159679/index.m3u8#第22集$https://v.cdnlz3.com/20240809/25448_66e070eb/index.m3u8#第23集$https://v.cdnlz3.com/20240810/25478_d1ef4ec0/index.m3u8#第24集$https://v.cdnlz3.com/20240810/25479_c7db7c2b/index.m3u8#第25集$https://v.cdnlz3.com/20240811/25518_08a2d2dd/index.m3u8#第26集$https://v.cdnlz3.com/20240811/25519_dfeb62f9/index.m3u8#第27集$https://v.cdnlz3.com/20240812/25553_fc7bd8f6/index.m3u8#第28集$https://v.cdnlz3.com/20240812/25554_20b4b783/index.m3u8#第29集$https://v.cdnlz3.com/20240813/25575_7df14a3e/index.m3u8#第30集$https://v.cdnlz3.com/20240813/25576_a012c969/index.m3u8";
        for (int i = 0; i < urls.split("\\$\\$\\$").length; i++) {
            String content = spider.playerContent(froms.split("\\$\\$\\$")[i], urls.split("\\$\\$\\$")[i].split("\\$")[1], new ArrayList<>());
            JsonObject map = Json.safeObject(content);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println("playerContent--" + gson.toJson(map));
            Assert.assertFalse(map.getAsJsonPrimitive("url").getAsString().isEmpty());
        }
    }

    @org.junit.Test
    public void searchContent() throws Exception {
        String content = spider.searchContent("红", false);
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("searchContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }
}