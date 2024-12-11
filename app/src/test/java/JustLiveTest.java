import android.app.Application;

import com.github.catvod.spider.DaGongRen;
import com.github.catvod.spider.Init;
import com.github.catvod.spider.JustLive;
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
public class JustLiveTest {
    // @Mock
    private Application mockContext;

    private JustLive spider;

    @org.junit.Before
    public void setUp() throws Exception {
        mockContext = RuntimeEnvironment.application;
        Init.init(mockContext);
        spider = new JustLive();
        spider.init(mockContext, "");
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
        String content = spider.categoryContent("单机", "2", true, new HashMap<>());
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("categoryContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @org.junit.Test
    public void detailContent() throws Exception {

        String content = spider.detailContent(Arrays.asList("platform\u003ddouyu\u0026roomId\u003d7919734"));
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("detailContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @org.junit.Test
    public void playerContent() throws Exception {
        String froms = "线路4$$$线路5";
        String urls = "原画1080P60$https://tc-tct.douyucdn2.cn/dyliveflv3a/7919734rJwfxf5Lm.flv?wsAuth\\u003d54bfefd1efbe2e2495b5e5651f20b13a\\u0026token\\u003dweb-h5-0-7919734-88e232aef1c8f3df8848e3a48ae4f039be5f4781178fceac\\u0026logo\\u003d0\\u0026expire\\u003d0\\u0026did\\u003d10000000000000000000000000001501\\u0026pt\\u003d2\\u0026st\\u003d1\\u0026sid\\u003d396675536\\u0026mcid2\\u003d0\\u0026origin\\u003dtct\\u0026mix\\u003d0\\u0026isp\\u003d$$$原画1080P60$https://tc-tct.douyucdn2.cn/dyliveflv3a/7919734rJwfxf5Lm.flv?wsAuth\\u003d1f62b5aa1feb1945ecbb09ecf1ef325d\\u0026token\\u003dweb-h5-0-7919734-88e232aef1c8f3df4586632dfc4aa7e3dba34f7a71bdb1be\\u0026logo\\u003d0\\u0026expire\\u003d0\\u0026did\\u003d10000000000000000000000000001501\\u0026pt\\u003d2\\u0026st\\u003d1\\u0026sid\\u003d396675536\\u0026mcid2\\u003d0\\u0026origin\\u003dtct\\u0026mix\\u003d0\\u0026isp\\u003d";
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