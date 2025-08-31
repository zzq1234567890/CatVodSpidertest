import android.app.Application;
import com.github.catvod.server.Server;
import com.github.catvod.spider.Init;
import com.github.catvod.spider.YiDongYun;
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
public class YiDongYunTest {

    private Application mockContext;

    private YiDongYun spider;

    @org.junit.Before
    public void setUp() throws Exception {
        mockContext = RuntimeEnvironment.application;
        Init.init(mockContext);
        spider = new YiDongYun();
      //  spider.init(mockContext, "b-user-id=89ede34e-0efc-e1dd-c997-f16aaa792d0c; _UP_A4A_11_=wb9661c6dfb642f88f73d8e0c7edd398; b-user-id=89ede34e-0efc-e1dd-c997-f16aaa792d0c; ctoken=wla6p3EUOLyn1FSB8IKp1SEW; grey-id=5583e32b-39df-4bf0-f39f-1adf83f604a2; grey-id.sig=p8ReBIMG2BeZu1sYvsuOAZxYbx-MVrsfKEiCv87MsTM; isYiDongYun=true; isYiDongYun.sig=hUgqObykqFom5Y09bll94T1sS9abT1X-4Df_lzgl8nM; _UP_F7E_8D_=ZkyvVHnrBLp1A1NFJIjWi0PwKLOVbxJPcg0RzQPI6KmBtV6ZMgPh38l93pgubgHDQqhaZ2Sfc0qv%2BRantbfg1mWGAUpRMP4RqXP78Wvu%2FCfvkWWGc5NhCTV71tGOIGgDBR3%2Bu6%2Fjj44KlE5biSNDOWW7Bigcz27lvOTidzNw8s%2FWtKAIxWbnCzZn4%2FJMBUub1SIMcW89g57k4mfPmDlCgpZKzxwl6beSfdtZ4RUWXmZOn5v5NkxVKhU4wR0Pq7NklczEGdRq2nIAcu7v22Uw2o%2FxMY0xBdeC9Korm5%2FNHnxl6K%2Bd6FXSoT9a3XIMQO359auZPiZWzrNlZe%2BqnOahXcx7KAhQIRqSOapSmL4ygJor4r5isJhRuDoXy7vJAVuH%2FRDtEJJ8rZTq0BdC23Bz%2B0MrsdgbK%2BiW; _UP_D_=pc; __wpkreporterwid_=3d3f74a7-99b7-4916-3f78-911fc2eb9d87; tfstk=fIoZNxjnbhKwPOu0TWZ4LsaRqirTcudSSmNbnxD0C5VgClMm8xMyB-GsnSu4tjpOflAOmSD-9PNiGl120XrgkVNb1SrqHbJBN3tSBAEYoQOWVUUg9qZ8n1bGGkD3CqGYINKSBABhjnXgp3_Vywz6gSc0Syj3BWf0mr2DLW24eZfiiovEKWefj1q0swq3E82iNEMinMy7SLrcpA4Fh3z_ZAViCfih3PbtdW5N_DuU77AaTijmYRkL2Wq54ENoy5a7ZXxCbok33XzS7QSZgxD-oyoVsdGotql0p2dVu7umC4nLStbiLmParc4FELHrI-c0u2dPVRrs8zoZWKCnIbNZrlHfUCMUz2z8KyXVSlgSFmUojh58OzeqTzgwaGll4YCYKwctDV5coP2LL79eKHxpNTXHmre1kZU32JPWCR_AkP2LL79eLZQY-WeUNdw1.; __pus=2051c82285199d8be553be41dd5a2100AAQ+mmv35G4FDDZ5x+3Mhe2OMbNgweQ1ODbW8zDt9YuP1LQVqHUuAAz9KWLsPjpNtim0AVGHusN4MCosTmbq/khM; __kp=e6604120-6051-11ef-bfe4-c31b6cdd0766; __kps=AATcZArVgS76EPn0FMaV4HEj; __ktd=sii/iz4ePzEaoVirXul7QQ==; __uid=AATcZArVgS76EPn0FMaV4HEj; __itrace_wid=5829b95d-dac1-48d3-bfd5-f60cd9462786; __puus=7da0b96cb710fa1b376934485f977e05AATp/q8/QupT7IiBR1GWqZhxlIRT677smMvoHlLxQA0Lk6CkP0YJBOTl+p9DZgzlMz6w4hPXPgWsokukk8PW7ZfhFfPmv8tKMgLpCGLW+tk57luhNghmSdTeVPkAF59STtyCPBEtiNzNAd/zZJ6qILJDi5ywEBAAAg+gOyWHoLHNUR+QxeHRuQa8g5WWA95J8jebIlrr8rCvI1vjTbtiYktT");
       // spider.init(mockContext, "_UP_A4A_11_=wb9681fbaed6454a8112f31e53b5c0be; __pus=45beefa93e8775c9211487d0c8ddd2b1AASCmV5S7LY0dfX90N3p4wU/G4f/oS0gZK6cpxZMZiDtXt9s7KiSs3tVZOXnIDel69C9KaQ61IQlnLYH2rS4NGjO; __kp=fe663a90-68d5-11ef-8b23-e77b0eaa352c; __kps=AAT32Fob+vq66znO5UHSHAPi; __ktd=39oXE+BT53YlFgUfFVq9kw==; __uid=AAT32Fob+vq66znO5UHSHAPi; xlly_s=1; b-user-id=91d551ad-db9e-f092-2b42-aa4db35b8ed0; isg=BNXVFRgkH_dXwTuJ8PizGl2W5NGP0onkjXrhLld60cybrvegHyMutYcsfLIYrqGc; tfstk=fNlSqGqmrgj57RpyCwL4hfk8bfFCODOw9waKS2CPJ7F-JWib0zWyEJ-IhDn42_P82ZEQ7caRpHf8M9aTxuFEUuzKc2nJry8uwiCY7PzLvzeLHsUUP6Ud9WQoncuOabJuT6NutWKwbCRZz4V39rUtZ-SukyUWT_U8JbtmmvxwbCR2eZFBVhkeml_RcyqYyzEdwENYSyFdv_nLD-UT7gFKvWLbHy4G2aCLJoUYJoEL9WnKkjXR5y97q4TY2O9qutVEyo1soja-eT0zc6CKGxw7XBrf96hbPqfsiZf6LlHg4RrtDI57OqUIcRkWf_iIJVDLhXsJzcnxdDUmnH6Qfv3rIj2RJT3jOuwtw-_9SVg8JDeInh1aP7kbCbMk-i3-buMTZ2764qwshR4YHw68aAuZtRhJNGq4IyibQYt1NcIzH1r_KcBClRfQll8Xl9Xh_6YQJFIaDQy8ozw2lEs-K8U0ll8Xl9X3er477ETf2vf..; __puus=514ad4334da84f912529719d557085b2AASV1aKJKLRXGjvHRfwQJ5gupjOzlxgeAImozKKYdppZduMrKS7Q5+3hUZZ0f6zk7YpAGu7p0GVPYojTpZdhvnamXzCBLryM3ULhlqkw9yR6oVeTr3b1MituYgqfeFM4jHi4ASNiLk22pCNKteAtD6aowAM0K1ZFVc7j7xlpxLEgS1CoNSttupAb56Zf+ruuTkDPsjZPiRW1S4yM/kduA247");
        Server.get().start();
    }

    @org.junit.Test
    public void init() throws Exception {
      //  spider.init(mockContext, "b-user-id=89ede34e-0efc-e1dd-c997-f16aaa792d0c; _UP_A4A_11_=wb9661c6dfb642f88f73d8e0c7edd398; b-user-id=89ede34e-0efc-e1dd-c997-f16aaa792d0c; ctoken=wla6p3EUOLyn1FSB8IKp1SEW; grey-id=5583e32b-39df-4bf0-f39f-1adf83f604a2; grey-id.sig=p8ReBIMG2BeZu1sYvsuOAZxYbx-MVrsfKEiCv87MsTM; isYiDongYun=true; isYiDongYun.sig=hUgqObykqFom5Y09bll94T1sS9abT1X-4Df_lzgl8nM; _UP_F7E_8D_=ZkyvVHnrBLp1A1NFJIjWi0PwKLOVbxJPcg0RzQPI6KmBtV6ZMgPh38l93pgubgHDQqhaZ2Sfc0qv%2BRantbfg1mWGAUpRMP4RqXP78Wvu%2FCfvkWWGc5NhCTV71tGOIGgDBR3%2Bu6%2Fjj44KlE5biSNDOWW7Bigcz27lvOTidzNw8s%2FWtKAIxWbnCzZn4%2FJMBUub1SIMcW89g57k4mfPmDlCgpZKzxwl6beSfdtZ4RUWXmZOn5v5NkxVKhU4wR0Pq7NklczEGdRq2nIAcu7v22Uw2o%2FxMY0xBdeC9Korm5%2FNHnxl6K%2Bd6FXSoT9a3XIMQO359auZPiZWzrNlZe%2BqnOahXcx7KAhQIRqSOapSmL4ygJor4r5isJhRuDoXy7vJAVuH%2FRDtEJJ8rZTq0BdC23Bz%2B0MrsdgbK%2BiW; _UP_D_=pc; __wpkreporterwid_=3d3f74a7-99b7-4916-3f78-911fc2eb9d87; tfstk=fIoZNxjnbhKwPOu0TWZ4LsaRqirTcudSSmNbnxD0C5VgClMm8xMyB-GsnSu4tjpOflAOmSD-9PNiGl120XrgkVNb1SrqHbJBN3tSBAEYoQOWVUUg9qZ8n1bGGkD3CqGYINKSBABhjnXgp3_Vywz6gSc0Syj3BWf0mr2DLW24eZfiiovEKWefj1q0swq3E82iNEMinMy7SLrcpA4Fh3z_ZAViCfih3PbtdW5N_DuU77AaTijmYRkL2Wq54ENoy5a7ZXxCbok33XzS7QSZgxD-oyoVsdGotql0p2dVu7umC4nLStbiLmParc4FELHrI-c0u2dPVRrs8zoZWKCnIbNZrlHfUCMUz2z8KyXVSlgSFmUojh58OzeqTzgwaGll4YCYKwctDV5coP2LL79eKHxpNTXHmre1kZU32JPWCR_AkP2LL79eLZQY-WeUNdw1.; __pus=2051c82285199d8be553be41dd5a2100AAQ+mmv35G4FDDZ5x+3Mhe2OMbNgweQ1ODbW8zDt9YuP1LQVqHUuAAz9KWLsPjpNtim0AVGHusN4MCosTmbq/khM; __kp=e6604120-6051-11ef-bfe4-c31b6cdd0766; __kps=AATcZArVgS76EPn0FMaV4HEj; __ktd=sii/iz4ePzEaoVirXul7QQ==; __uid=AATcZArVgS76EPn0FMaV4HEj; __itrace_wid=5829b95d-dac1-48d3-bfd5-f60cd9462786; __puus=7da0b96cb710fa1b376934485f977e05AATp/q8/QupT7IiBR1GWqZhxlIRT677smMvoHlLxQA0Lk6CkP0YJBOTl+p9DZgzlMz6w4hPXPgWsokukk8PW7ZfhFfPmv8tKMgLpCGLW+tk57luhNghmSdTeVPkAF59STtyCPBEtiNzNAd/zZJ6qILJDi5ywEBAAAg+gOyWHoLHNUR+QxeHRuQa8g5WWA95J8jebIlrr8rCvI1vjTbtiYktT");
        //Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @org.junit.Test
    public void detailContent() throws Exception {

        String content = spider.detailContent(Arrays.asList("https://caiyun.139.com/w/i/2nQQVZWCR24yf"));
        System.out.println("detailContent--" + content);
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("detailContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    /**
     *  "vod_play_from": "移动(极速)$$$移动(原画)",
     *       "vod_play_url": "01.mp4$FtgoS0iBwhPFnG-daRU5HJKQ2yBYj8I3x++2nQQVZWCR24yf#02.mp4$FiPG4ttnS-78swp-1sTFG4prNYnLaUvK_++2nQQVZWCR24yf$$$01.mp4$FtB-r8kHUbJFKbzW_r9tJt6YjcTZCVGWR/FtgoS0iBwhPFnG-daRU5HJKQ2yBYj8I3x++2nQQVZWCR24yf#02.mp4$FtB-r8kHUbJFKbzW_r9tJt6YjcTZCVGWR/FiPG4ttnS-78swp-1sTFG4prNYnLaUvK_++2nQQVZWCR24yf"
     * @throws Exception
     */
    @org.junit.Test
    public void playerContent() throws Exception {

        String content = spider.playerContent("移动(原画)","FtB-r8kHUbJFKbzW_r9tJt6YjcTZCVGWR/FiPG4ttnS-78swp-1sTFG4prNYnLaUvK_++2nQQVZWCR24yf",new ArrayList<>());
        System.out.println("playerContent--" + content);
        JsonObject map = Json.safeObject(content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("playerContent--" + gson.toJson(map));
        Assert.assertFalse(map.getAsJsonPrimitive("url").getAsString().isEmpty());
    }
}