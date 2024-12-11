import android.app.Application;

import com.github.catvod.spider.DaGongRen;
import com.github.catvod.spider.HkTv;
import com.github.catvod.spider.Init;
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
public class HKTvTest {
    // @Mock
    private Application mockContext;

    private HkTv spider;

    @org.junit.Before
    public void setUp() throws Exception {
        mockContext = RuntimeEnvironment.application;
        Init.init(mockContext);
        spider = new HkTv();
        spider.init(mockContext, "http://www.hktvyb.vip/");
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
        String content = spider.categoryContent("1", "2", true, null);
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("categoryContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @org.junit.Test
    public void detailContent() throws Exception {

        String content = spider.detailContent(Arrays.asList("174240.html"));
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("detailContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @org.junit.Test
    public void playerContent() throws Exception {
        String froms = "量子线路$$$暴风线路$$$非凡线路";
        String urls = "第01集$174240/sid/1/nid/1.html#第02集$174240/sid/1/nid/2.html#第03集$174240/sid/1/nid/3.html#第04集$174240/sid/1/nid/4.html#第05集$174240/sid/1/nid/5.html#第06集$174240/sid/1/nid/6.html#第07集$174240/sid/1/nid/7.html#第08集$174240/sid/1/nid/8.html#第09集$174240/sid/1/nid/9.html#第10集$174240/sid/1/nid/10.html$$$第01集$174240/sid/3/nid/1.html#第02集$174240/sid/3/nid/2.html#第03集$174240/sid/3/nid/3.html#第04集$174240/sid/3/nid/4.html#第05集$174240/sid/3/nid/5.html#第06集$174240/sid/3/nid/6.html#第07集$174240/sid/3/nid/7.html#第08集$174240/sid/3/nid/8.html#第09集$174240/sid/3/nid/9.html#第10集$174240/sid/3/nid/10.html$$$第01集$174240/sid/2/nid/1.html#第02集$174240/sid/2/nid/2.html#第03集$174240/sid/2/nid/3.html#第04集$174240/sid/2/nid/4.html#第05集$174240/sid/2/nid/5.html#第06集$174240/sid/2/nid/6.html#第07集$174240/sid/2/nid/7.html#第08集$174240/sid/2/nid/8.html#第09集$174240/sid/2/nid/9.html#第10集$174240/sid/2/nid/10.html";
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
        String content = spider.searchContent("红海", false);
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("searchContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }
}