import android.app.Application;
import com.github.catvod.server.Server;
import com.github.catvod.spider.Init;
import com.github.catvod.spider.Quark;
import com.github.catvod.spider.Wogg;
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
public class QuarkTest {

    private Application mockContext;

    private Quark spider;

    @org.junit.Before
    public void setUp() throws Exception {
        mockContext = RuntimeEnvironment.application;
        Init.init(mockContext);
        spider = new Quark();
      //  spider.init(mockContext, "b-user-id=89ede34e-0efc-e1dd-c997-f16aaa792d0c; _UP_A4A_11_=wb9661c6dfb642f88f73d8e0c7edd398; b-user-id=89ede34e-0efc-e1dd-c997-f16aaa792d0c; ctoken=wla6p3EUOLyn1FSB8IKp1SEW; grey-id=5583e32b-39df-4bf0-f39f-1adf83f604a2; grey-id.sig=p8ReBIMG2BeZu1sYvsuOAZxYbx-MVrsfKEiCv87MsTM; isQuark=true; isQuark.sig=hUgqObykqFom5Y09bll94T1sS9abT1X-4Df_lzgl8nM; _UP_F7E_8D_=ZkyvVHnrBLp1A1NFJIjWi0PwKLOVbxJPcg0RzQPI6KmBtV6ZMgPh38l93pgubgHDQqhaZ2Sfc0qv%2BRantbfg1mWGAUpRMP4RqXP78Wvu%2FCfvkWWGc5NhCTV71tGOIGgDBR3%2Bu6%2Fjj44KlE5biSNDOWW7Bigcz27lvOTidzNw8s%2FWtKAIxWbnCzZn4%2FJMBUub1SIMcW89g57k4mfPmDlCgpZKzxwl6beSfdtZ4RUWXmZOn5v5NkxVKhU4wR0Pq7NklczEGdRq2nIAcu7v22Uw2o%2FxMY0xBdeC9Korm5%2FNHnxl6K%2Bd6FXSoT9a3XIMQO359auZPiZWzrNlZe%2BqnOahXcx7KAhQIRqSOapSmL4ygJor4r5isJhRuDoXy7vJAVuH%2FRDtEJJ8rZTq0BdC23Bz%2B0MrsdgbK%2BiW; _UP_D_=pc; __wpkreporterwid_=3d3f74a7-99b7-4916-3f78-911fc2eb9d87; tfstk=fIoZNxjnbhKwPOu0TWZ4LsaRqirTcudSSmNbnxD0C5VgClMm8xMyB-GsnSu4tjpOflAOmSD-9PNiGl120XrgkVNb1SrqHbJBN3tSBAEYoQOWVUUg9qZ8n1bGGkD3CqGYINKSBABhjnXgp3_Vywz6gSc0Syj3BWf0mr2DLW24eZfiiovEKWefj1q0swq3E82iNEMinMy7SLrcpA4Fh3z_ZAViCfih3PbtdW5N_DuU77AaTijmYRkL2Wq54ENoy5a7ZXxCbok33XzS7QSZgxD-oyoVsdGotql0p2dVu7umC4nLStbiLmParc4FELHrI-c0u2dPVRrs8zoZWKCnIbNZrlHfUCMUz2z8KyXVSlgSFmUojh58OzeqTzgwaGll4YCYKwctDV5coP2LL79eKHxpNTXHmre1kZU32JPWCR_AkP2LL79eLZQY-WeUNdw1.; __pus=2051c82285199d8be553be41dd5a2100AAQ+mmv35G4FDDZ5x+3Mhe2OMbNgweQ1ODbW8zDt9YuP1LQVqHUuAAz9KWLsPjpNtim0AVGHusN4MCosTmbq/khM; __kp=e6604120-6051-11ef-bfe4-c31b6cdd0766; __kps=AATcZArVgS76EPn0FMaV4HEj; __ktd=sii/iz4ePzEaoVirXul7QQ==; __uid=AATcZArVgS76EPn0FMaV4HEj; __itrace_wid=5829b95d-dac1-48d3-bfd5-f60cd9462786; __puus=7da0b96cb710fa1b376934485f977e05AATp/q8/QupT7IiBR1GWqZhxlIRT677smMvoHlLxQA0Lk6CkP0YJBOTl+p9DZgzlMz6w4hPXPgWsokukk8PW7ZfhFfPmv8tKMgLpCGLW+tk57luhNghmSdTeVPkAF59STtyCPBEtiNzNAd/zZJ6qILJDi5ywEBAAAg+gOyWHoLHNUR+QxeHRuQa8g5WWA95J8jebIlrr8rCvI1vjTbtiYktT");
        spider.init(mockContext, "_UP_28A_52_=386;_UP_BT_=html5;_UP_F7E_8D_=0z44HdIBxZZTFH3p1NV%2FwWJIkAWBTYaH20RoPCksvMmyhI6XxrMIHoi8gAqVoKf%2Bfw0hw4mmmcFLHpvA%2Fhicy1HUTu2LBlCP6GF%2FnM%2Bm0IJoj1BQdak3tm1o3OeN1OV9dQAEQ0UDfWTXDik4ZZxmO5Iwvj6IsFkb5GPrrCl5M87ivs0EP%2FjAQTQimMgEdat62Byd22%2BZGM703ymU3s8N9B3XRdiyy8E7vOTidzNw8s%2FWtKAIxWbnCzZn4%2FJMBUubLuroBmVIB9UVOMEdD6uzZJXMxBnUatpyAHLu79tlMNqP8TGNMQXXgvSqK5ufzR58ZeivnehV0qE%2FWt1yDEDt%2BfWrmT4mVs6zZWXvqpzmoV3MeygIUCEakh2GAn6rsLT1b2ZsrSkQkrM6F8u7yQFbh%2F0Q7RCSfK2U6tAXQttwc%2FtDK7HYGyvolg%3D%3D;_UP_6D1_64_=069;_UP_A4A_11_=wb9cc1693e1d486b8b2ac58e4839d64e;_UP_D_=mobilectoken=4IUaeDKAfn3pV-MKaWfg_GHG;__pus=76f683009a07bdd1c1a7c04f05838d4bAASVSWCD2jiL0GI43jmIC5x50sk6Tgbh4UtXFf/vqOUyaX8Aory/bsGsGCTl68Lo7sJPpZpBJdif81oItfZizhzH;__kp=d8da7a20-7522-11f0-8e98-7daef001221f;__kps=AATcZArVgS76EPn0FMaV4HEj;__ktd=sii/iz4ePzEaoVirXul7QQ==;__uid=AATcZArVgS76EPn0FMaV4HEj;__puus=b4617d4cdadeb05679ed39be924d99d8AATp/q8/QupT7IiBR1GWqZhx6c42nqKQR/5g49EMGJ5TE742htjk9EkPPibrpvSh8/N8ybuC/Wc4BP1YbLBb5ZFEEOPrmFBTCZtKVEh5HcqkSL6T6LoyeKfvGe32k9HckqoPn9MxbAThhpRlbHg8QgH8OwxLHp0V2cAYcsGY72XN2ZPL7JzZyOAwmSsJwpX9B0+LYNvco+Ixucn5KaaCTKND");
        Server.get().start();
    }

    @org.junit.Test
    public void init() throws Exception {
        spider.init(mockContext, "b-user-id=89ede34e-0efc-e1dd-c997-f16aaa792d0c; _UP_A4A_11_=wb9661c6dfb642f88f73d8e0c7edd398; b-user-id=89ede34e-0efc-e1dd-c997-f16aaa792d0c; ctoken=wla6p3EUOLyn1FSB8IKp1SEW; grey-id=5583e32b-39df-4bf0-f39f-1adf83f604a2; grey-id.sig=p8ReBIMG2BeZu1sYvsuOAZxYbx-MVrsfKEiCv87MsTM; isQuark=true; isQuark.sig=hUgqObykqFom5Y09bll94T1sS9abT1X-4Df_lzgl8nM; _UP_F7E_8D_=ZkyvVHnrBLp1A1NFJIjWi0PwKLOVbxJPcg0RzQPI6KmBtV6ZMgPh38l93pgubgHDQqhaZ2Sfc0qv%2BRantbfg1mWGAUpRMP4RqXP78Wvu%2FCfvkWWGc5NhCTV71tGOIGgDBR3%2Bu6%2Fjj44KlE5biSNDOWW7Bigcz27lvOTidzNw8s%2FWtKAIxWbnCzZn4%2FJMBUub1SIMcW89g57k4mfPmDlCgpZKzxwl6beSfdtZ4RUWXmZOn5v5NkxVKhU4wR0Pq7NklczEGdRq2nIAcu7v22Uw2o%2FxMY0xBdeC9Korm5%2FNHnxl6K%2Bd6FXSoT9a3XIMQO359auZPiZWzrNlZe%2BqnOahXcx7KAhQIRqSOapSmL4ygJor4r5isJhRuDoXy7vJAVuH%2FRDtEJJ8rZTq0BdC23Bz%2B0MrsdgbK%2BiW; _UP_D_=pc; __wpkreporterwid_=3d3f74a7-99b7-4916-3f78-911fc2eb9d87; tfstk=fIoZNxjnbhKwPOu0TWZ4LsaRqirTcudSSmNbnxD0C5VgClMm8xMyB-GsnSu4tjpOflAOmSD-9PNiGl120XrgkVNb1SrqHbJBN3tSBAEYoQOWVUUg9qZ8n1bGGkD3CqGYINKSBABhjnXgp3_Vywz6gSc0Syj3BWf0mr2DLW24eZfiiovEKWefj1q0swq3E82iNEMinMy7SLrcpA4Fh3z_ZAViCfih3PbtdW5N_DuU77AaTijmYRkL2Wq54ENoy5a7ZXxCbok33XzS7QSZgxD-oyoVsdGotql0p2dVu7umC4nLStbiLmParc4FELHrI-c0u2dPVRrs8zoZWKCnIbNZrlHfUCMUz2z8KyXVSlgSFmUojh58OzeqTzgwaGll4YCYKwctDV5coP2LL79eKHxpNTXHmre1kZU32JPWCR_AkP2LL79eLZQY-WeUNdw1.; __pus=2051c82285199d8be553be41dd5a2100AAQ+mmv35G4FDDZ5x+3Mhe2OMbNgweQ1ODbW8zDt9YuP1LQVqHUuAAz9KWLsPjpNtim0AVGHusN4MCosTmbq/khM; __kp=e6604120-6051-11ef-bfe4-c31b6cdd0766; __kps=AATcZArVgS76EPn0FMaV4HEj; __ktd=sii/iz4ePzEaoVirXul7QQ==; __uid=AATcZArVgS76EPn0FMaV4HEj; __itrace_wid=5829b95d-dac1-48d3-bfd5-f60cd9462786; __puus=7da0b96cb710fa1b376934485f977e05AATp/q8/QupT7IiBR1GWqZhxlIRT677smMvoHlLxQA0Lk6CkP0YJBOTl+p9DZgzlMz6w4hPXPgWsokukk8PW7ZfhFfPmv8tKMgLpCGLW+tk57luhNghmSdTeVPkAF59STtyCPBEtiNzNAd/zZJ6qILJDi5ywEBAAAg+gOyWHoLHNUR+QxeHRuQa8g5WWA95J8jebIlrr8rCvI1vjTbtiYktT");
        //Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @org.junit.Test
    public void detailContent() throws Exception {

        String content = spider.detailContent(Arrays.asList("https://pan.quark.cn/s/469c2acf8640"));
        System.out.println("detailContent--" + content);
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("detailContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @org.junit.Test
    public void playerContent() throws Exception {

        String content = spider.playerContent("quark原画", "a04522f504a742db8ebaf69e3b7f50d6++375807f3f1068a8fdabac127ec4db89f++469c2acf8640++PVTgPNXNtRFDDkE6SAYX4KPSjk9xl449JkTHl6mtu7k=", new ArrayList<>());
        System.out.println("playerContent--" + content);
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("playerContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonPrimitive("url").getAsString().isEmpty());
    }
}