import android.app.Application;

import com.github.catvod.spider.DaGongRen;
import com.github.catvod.spider.Ikanbot;
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
public class IkanbotTest {
    // @Mock
    private Application mockContext;

    private Ikanbot spider;

    @org.junit.Before
    public void setUp() throws Exception {
        mockContext = RuntimeEnvironment.application;
        Init.init(mockContext);
        spider = new Ikanbot();
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
        String content = spider.categoryContent("/index-movie-热门", "2", true, null);
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("categoryContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @org.junit.Test
    public void detailContent() throws Exception {

        String content = spider.detailContent(Arrays.asList("853785"));
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("detailContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @org.junit.Test
    public void playerContent() throws Exception {
        String froms = "gsm3u8$$$xlm3u8$$$jsm3u8$$$tpm3u8$$$wolong$$$bfzym3u8$$$lzm3u8$$$1080zyk$$$kcm3u8$$$kuaikan$$$tkm3u8$$$sdm3u8$$$wjm3u8$$$hhm3u8$$$ikm3u8$$$snm3u8$$$hym3u8$$$ffm3u8$$$fsm3u8$$$zuidam3u8$$$jinyingm3u8$$$ukm3u8$$$bjm3u8$$$360zy$$$hw8$$$subm3u8$$$yhm3u8";
        String urls = "正片$https://v.gsuus.com/play/5eVR4Qoe/index.m3u8$$$正片$https://play.xluuss.com/play/NbWV5qoa/index.m3u8$$$正片$https://vv.jisuzyv.com/play/QeZ1k0gd/index.m3u8$$$犯罪都市4$https://sd8.taopianplay1.com:43333/c56b1bc09da3/HD/2024-07-19/1/de5459c7a07b/ff4eb0c51ec7/playlist.m3u8$$$正片$https://cdn.wlcdn99.com:777/6c7f15f2/index.m3u8$$$中字$https://s3.bfengbf.com/video/fanzuidushi4/中字/index.m3u8$$$HD中字$https://v.cdnlz21.com/20240701/790_6dc95607/index.m3u8$$$HD人工中文$https://svip.high22-playback.com/20240701/4372_9052ee7b/index.m3u8$$$中字$https://v1.longshengtea.com/yyv1/202405/18/FLxwP9Hqmp1/video/index.m3u8#HD$https://v8.longshengtea.com/yyv8/202407/01/3XNQBep5ix14/video/index.m3u8$$$俄版机翻中字$https://vip.kuaikan-play3.com/20240710/BainGasF/index.m3u8#俄语机翻中字$https://vip.kuaikan-play2.com/20240522/BAEnKmHY/index.m3u8#正片$https://vip.kuaikan-play3.com/20240710/EmydKWIH/index.m3u8$$$HD$https://v10.dious.cc/20240716/9M2dnF2Z/index.m3u8$$$中字$https://v1.fentvoss.com/sdv1/202405/18/FLxwP9Hqmp1/video/index.m3u8#HD$https://v8.fentvoss.com/sdv8/202407/01/3XNQBep5ix14/video/index.m3u8$$$HD$https://v11.tlkqc.com/wjv11/202407/02/H9pYM8zuu383/video/index.m3u8$$$正片$https://play.hhuus.com/play/QbY8j5Ya/index.m3u8$$$正片$https://bfikuncdn.com/20240716/i9cQ7Ayr/index.m3u8$$$HD$https://v8.mzxay.com/202407/01/3XNQBep5ix14/video/index.m3u8$$$正片$https://1080p.huyall.com/play/BeXWg5Vd/index.m3u8$$$HD中字$https://super.ffzy-online6.com/20240702/33974_183d0e8f/index.m3u8$$$HD$https://s10.fsvod1.com/20240716/PqZ8bfiD/index.m3u8$$$中字$https://v1.daayee.com/yyv1/202405/18/FLxwP9Hqmp1/video/index.m3u8#HD$https://v8.daayee.com/yyv8/202407/01/3XNQBep5ix14/video/index.m3u8$$$正片$https://hd.ijycnd.com/play/BeXWg5Vd/index.m3u8$$$720P$https://ukzy.ukubf3.com/20240702/rrroUpHe/index.m3u8$$$HD$https://v1.hdslb.pro/share/ydXlDNTEWgM46Bmx$$$犯罪都市4$https://vod.lyhuicheng.com/20240606/8w8PDkzh/index.m3u8$$$俄版机翻中字$https://m3u.nikanba.live/share/f3533edd778d62b756c4d2278c8252a6.m3u8#正片$https://m3u.nikanba.live/share/725902890b41ec1e1ea688afb0adf555.m3u8$$$正片$https://play.subokk.com/play/NbWV5qoa/index.m3u8$$$HD$https://vod12.wgslsw.com/20240708/H9pYM8zuu383/index.m3u8#";
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