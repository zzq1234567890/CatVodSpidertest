import android.app.Application;
import com.github.catvod.server.Server;
import com.github.catvod.spider.Init;
import com.github.catvod.spider.Quark;
import com.github.catvod.spider.TianYi;
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
public class TianYiTest {

    private Application mockContext;

    private TianYi spider;

    @org.junit.Before
    public void setUp() throws Exception {
        mockContext = RuntimeEnvironment.application;
        Init.init(mockContext);
        spider = new TianYi();
      //  spider.init(mockContext, "b-user-id=89ede34e-0efc-e1dd-c997-f16aaa792d0c; _UP_A4A_11_=wb9661c6dfb642f88f73d8e0c7edd398; b-user-id=89ede34e-0efc-e1dd-c997-f16aaa792d0c; ctoken=wla6p3EUOLyn1FSB8IKp1SEW; grey-id=5583e32b-39df-4bf0-f39f-1adf83f604a2; grey-id.sig=p8ReBIMG2BeZu1sYvsuOAZxYbx-MVrsfKEiCv87MsTM; isQuark=true; isQuark.sig=hUgqObykqFom5Y09bll94T1sS9abT1X-4Df_lzgl8nM; _UP_F7E_8D_=ZkyvVHnrBLp1A1NFJIjWi0PwKLOVbxJPcg0RzQPI6KmBtV6ZMgPh38l93pgubgHDQqhaZ2Sfc0qv%2BRantbfg1mWGAUpRMP4RqXP78Wvu%2FCfvkWWGc5NhCTV71tGOIGgDBR3%2Bu6%2Fjj44KlE5biSNDOWW7Bigcz27lvOTidzNw8s%2FWtKAIxWbnCzZn4%2FJMBUub1SIMcW89g57k4mfPmDlCgpZKzxwl6beSfdtZ4RUWXmZOn5v5NkxVKhU4wR0Pq7NklczEGdRq2nIAcu7v22Uw2o%2FxMY0xBdeC9Korm5%2FNHnxl6K%2Bd6FXSoT9a3XIMQO359auZPiZWzrNlZe%2BqnOahXcx7KAhQIRqSOapSmL4ygJor4r5isJhRuDoXy7vJAVuH%2FRDtEJJ8rZTq0BdC23Bz%2B0MrsdgbK%2BiW; _UP_D_=pc; __wpkreporterwid_=3d3f74a7-99b7-4916-3f78-911fc2eb9d87; tfstk=fIoZNxjnbhKwPOu0TWZ4LsaRqirTcudSSmNbnxD0C5VgClMm8xMyB-GsnSu4tjpOflAOmSD-9PNiGl120XrgkVNb1SrqHbJBN3tSBAEYoQOWVUUg9qZ8n1bGGkD3CqGYINKSBABhjnXgp3_Vywz6gSc0Syj3BWf0mr2DLW24eZfiiovEKWefj1q0swq3E82iNEMinMy7SLrcpA4Fh3z_ZAViCfih3PbtdW5N_DuU77AaTijmYRkL2Wq54ENoy5a7ZXxCbok33XzS7QSZgxD-oyoVsdGotql0p2dVu7umC4nLStbiLmParc4FELHrI-c0u2dPVRrs8zoZWKCnIbNZrlHfUCMUz2z8KyXVSlgSFmUojh58OzeqTzgwaGll4YCYKwctDV5coP2LL79eKHxpNTXHmre1kZU32JPWCR_AkP2LL79eLZQY-WeUNdw1.; __pus=2051c82285199d8be553be41dd5a2100AAQ+mmv35G4FDDZ5x+3Mhe2OMbNgweQ1ODbW8zDt9YuP1LQVqHUuAAz9KWLsPjpNtim0AVGHusN4MCosTmbq/khM; __kp=e6604120-6051-11ef-bfe4-c31b6cdd0766; __kps=AATcZArVgS76EPn0FMaV4HEj; __ktd=sii/iz4ePzEaoVirXul7QQ==; __uid=AATcZArVgS76EPn0FMaV4HEj; __itrace_wid=5829b95d-dac1-48d3-bfd5-f60cd9462786; __puus=7da0b96cb710fa1b376934485f977e05AATp/q8/QupT7IiBR1GWqZhxlIRT677smMvoHlLxQA0Lk6CkP0YJBOTl+p9DZgzlMz6w4hPXPgWsokukk8PW7ZfhFfPmv8tKMgLpCGLW+tk57luhNghmSdTeVPkAF59STtyCPBEtiNzNAd/zZJ6qILJDi5ywEBAAAg+gOyWHoLHNUR+QxeHRuQa8g5WWA95J8jebIlrr8rCvI1vjTbtiYktT");
        spider.init(mockContext, "{\"open.e.189.cn\":[{\"name\":\"SSON\",\"value\":\"dc466c8192e3109eaea837c1d136c1fd065253ce1c7d3a66ca1520d7d6d6307b10a1fe65c7becac73b95f24a6e681e654ec4f47c39533ebcc48bb78d6d6e63d1bbf3334e6e97eaa7092d34f87bf1209ee35f344871bc5a329eac34ae948d399d4a6b3b28a929c4f353ade0981657e9e0f09ce27cc1c15d8322c6e45a8ebb21eb431509f1dd7dc3a7856b32b0991d654d5ced73dd20b764ca8737600cbe699c37ccf59b3c610893fc42bdc08b477c5d394e290c14d175d1ca0ee9fa61a1a8dcac7007e9219fd0ae6ccd5dc760524213f85b6b8c6166af01a31336dab797d9118010b81a5a3c26e08e\",\"expiresAt\":253402300799999,\"domain\":\"e.189.cn\",\"path\":\"/\",\"secure\":true,\"httpOnly\":true,\"persistent\":true,\"hostOnly\":false},{\"name\":\"GUID\",\"value\":\"525d8874e53e46a7ba3ed8907e9fef1f\",\"expiresAt\":1775176321000,\"domain\":\"e.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":false,\"persistent\":true,\"hostOnly\":false},{\"name\":\"pageOp\",\"value\":\"336b9ddc820212fa6c9b5a0cfd7bf5b3\",\"expiresAt\":253402300799999,\"domain\":\"e.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":false,\"persistent\":false,\"hostOnly\":false},{\"name\":\"OPENINFO\",\"value\":\"33c28688ef52ce9e3a9ef87388047efbde5e3e2e4c7ef6ef267632468c7dfaf294ff59fa59d34801\",\"expiresAt\":253402300799999,\"domain\":\"e.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":true,\"persistent\":false,\"hostOnly\":false},{\"name\":\"GRAYNUMBER\",\"value\":\"319DE3F68C8730862F3BEF66F3D635B7\",\"expiresAt\":1775177653000,\"domain\":\"e.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":false,\"persistent\":true,\"hostOnly\":false}],\"cloud.189.cn\":[{\"name\":\"JSESSIONID\",\"value\":\"431787526C43DF21B6373E914FE597EC\",\"expiresAt\":253402300799999,\"domain\":\"cloud.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":true,\"persistent\":false,\"hostOnly\":true},{\"name\":\"COOKIE_LOGIN_USER\",\"value\":\"0C7407F59A6E5896EB6B777056E160DB020BAE67B121B5930CCD4777073744055308F7E8CD03F2FC2399E4823F60ECDD74120CEE4C529017\",\"expiresAt\":253402300799999,\"domain\":\"cloud.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":true,\"persistent\":false,\"hostOnly\":false}]}");
      //  Server.get().start();
    }

    @org.junit.Test
    public void init() throws Exception {
        spider.init(mockContext, "{\"open.e.189.cn\":[{\"name\":\"SSON\",\"value\":\"dc466c8192e3109eaea837c1d136c1fd065253ce1c7d3a66ca1520d7d6d6307b10a1fe65c7becac73b95f24a6e681e654ec4f47c39533ebcc48bb78d6d6e63d1bbf3334e6e97eaa7092d34f87bf1209ee35f344871bc5a329eac34ae948d399d4a6b3b28a929c4f353ade0981657e9e0f09ce27cc1c15d8322c6e45a8ebb21eb431509f1dd7dc3a7856b32b0991d654d5ced73dd20b764ca8737600cbe699c37ccf59b3c610893fc42bdc08b477c5d394e290c14d175d1ca0ee9fa61a1a8dcac7007e9219fd0ae6ccd5dc760524213f85b6b8c6166af01a31336dab797d9118010b81a5a3c26e08e\",\"expiresAt\":253402300799999,\"domain\":\"e.189.cn\",\"path\":\"/\",\"secure\":true,\"httpOnly\":true,\"persistent\":true,\"hostOnly\":false},{\"name\":\"GUID\",\"value\":\"525d8874e53e46a7ba3ed8907e9fef1f\",\"expiresAt\":1775176321000,\"domain\":\"e.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":false,\"persistent\":true,\"hostOnly\":false},{\"name\":\"pageOp\",\"value\":\"336b9ddc820212fa6c9b5a0cfd7bf5b3\",\"expiresAt\":253402300799999,\"domain\":\"e.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":false,\"persistent\":false,\"hostOnly\":false},{\"name\":\"OPENINFO\",\"value\":\"33c28688ef52ce9e3a9ef87388047efbde5e3e2e4c7ef6ef267632468c7dfaf294ff59fa59d34801\",\"expiresAt\":253402300799999,\"domain\":\"e.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":true,\"persistent\":false,\"hostOnly\":false},{\"name\":\"GRAYNUMBER\",\"value\":\"319DE3F68C8730862F3BEF66F3D635B7\",\"expiresAt\":1775177653000,\"domain\":\"e.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":false,\"persistent\":true,\"hostOnly\":false}],\"cloud.189.cn\":[{\"name\":\"JSESSIONID\",\"value\":\"431787526C43DF21B6373E914FE597EC\",\"expiresAt\":253402300799999,\"domain\":\"cloud.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":true,\"persistent\":false,\"hostOnly\":true},{\"name\":\"COOKIE_LOGIN_USER\",\"value\":\"0C7407F59A6E5896EB6B777056E160DB020BAE67B121B5930CCD4777073744055308F7E8CD03F2FC2399E4823F60ECDD74120CEE4C529017\",\"expiresAt\":253402300799999,\"domain\":\"cloud.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":true,\"persistent\":false,\"hostOnly\":false}]}");
        //Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @org.junit.Test
    public void detailContent() throws Exception {

        String content = spider.detailContent(Arrays.asList("https://cloud.189.cn/web/share?code=ZvEjUvq6FNr2"));
        System.out.println("detailContent--" + content);
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("detailContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @org.junit.Test
    public void playerContent() throws Exception {

        String content = spider.playerContent("天意","523693138438437163++12314111132976",new ArrayList<>());
        System.out.println("playerContent--" + content);
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("playerContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonPrimitive("url").getAsString().isEmpty());
    }
}