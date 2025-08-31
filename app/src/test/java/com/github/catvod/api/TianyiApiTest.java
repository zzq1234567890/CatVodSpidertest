package com.github.catvod.api;

import com.github.catvod.bean.tianyi.ShareData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class TianyiApiTest {

//    @Test
//    public void getShareData() {
//
//
//        ShareData shareData = QuarkApi.get().getShareData("https://pan.quark.cn/s/1e386295b8ca");
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
//        System.out.println("getShareData--" + gson.toJson(shareData));
//    }

    @Test
    public void getShareData() throws Exception {

        com.github.catvod.bean.tianyi.ShareData shareData = TianyiApi.get().getShareData("https://cloud.189.cn/web/share?code=ZvEjUvq6FNr2", "");
        // TianyiApi.get().getVod(shareData);
        com.github.catvod.bean.tianyi.ShareData shareData1 = TianyiApi.get().getShareData("https://cloud.189.cn/web/share?code=2eyARfBzURZj（访问码：kz6y）", "");

        //  TianyiApi.get().getVod(shareData1);
        ShareData shareData2 = TianyiApi.get().getShareData("https://cloud.189.cn/t/ZvEjUvq6FNr2", "");
        // TianyiApi.get().getVod(shareData2);


    }


    @Test
    public void getVod() throws Exception {

        com.github.catvod.bean.tianyi.ShareData shareData1 = TianyiApi.get().getShareData("https://cloud.189.cn/web/share?code=qEVVjyqM7bY3（访问码：6iel）", "");
        TianyiApi api = TianyiApi.get();
        api.setCookie("{\"open.e.189.cn\":[{\"name\":\"SSON\",\"value\":\"dc466c8192e3109eaea837c1d136c1fd065253ce1c7d3a66ca1520d7d6d6307b10a1fe65c7becac73b95f24a6e681e654ec4f47c39533ebcc48bb78d6d6e63d1bbf3334e6e97eaa7092d34f87bf1209ee35f344871bc5a329eac34ae948d399d4a6b3b28a929c4f353ade0981657e9e0f09ce27cc1c15d8322c6e45a8ebb21eb431509f1dd7dc3a7856b32b0991d654d5ced73dd20b764ca8737600cbe699c37ccf59b3c610893fc42bdc08b477c5d394e290c14d175d1ca0ee9fa61a1a8dcac7007e9219fd0ae6ccd5dc760524213f85b6b8c6166af01a31336dab797d9118010b81a5a3c26e08e\",\"expiresAt\":253402300799999,\"domain\":\"e.189.cn\",\"path\":\"/\",\"secure\":true,\"httpOnly\":true,\"persistent\":true,\"hostOnly\":false},{\"name\":\"GUID\",\"value\":\"525d8874e53e46a7ba3ed8907e9fef1f\",\"expiresAt\":1775176321000,\"domain\":\"e.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":false,\"persistent\":true,\"hostOnly\":false},{\"name\":\"pageOp\",\"value\":\"336b9ddc820212fa6c9b5a0cfd7bf5b3\",\"expiresAt\":253402300799999,\"domain\":\"e.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":false,\"persistent\":false,\"hostOnly\":false},{\"name\":\"OPENINFO\",\"value\":\"33c28688ef52ce9e3a9ef87388047efbde5e3e2e4c7ef6ef267632468c7dfaf294ff59fa59d34801\",\"expiresAt\":253402300799999,\"domain\":\"e.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":true,\"persistent\":false,\"hostOnly\":false},{\"name\":\"GRAYNUMBER\",\"value\":\"319DE3F68C8730862F3BEF66F3D635B7\",\"expiresAt\":1775177653000,\"domain\":\"e.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":false,\"persistent\":true,\"hostOnly\":false}],\"cloud.189.cn\":[{\"name\":\"JSESSIONID\",\"value\":\"431787526C43DF21B6373E914FE597EC\",\"expiresAt\":253402300799999,\"domain\":\"cloud.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":true,\"persistent\":false,\"hostOnly\":true},{\"name\":\"COOKIE_LOGIN_USER\",\"value\":\"0C7407F59A6E5896EB6B777056E160DB020BAE67B121B5930CCD4777073744055308F7E8CD03F2FC2399E4823F60ECDD74120CEE4C529017\",\"expiresAt\":253402300799999,\"domain\":\"cloud.189.cn\",\"path\":\"/\",\"secure\":false,\"httpOnly\":true,\"persistent\":false,\"hostOnly\":false}]}");
        api.getVod(shareData1);


    }
}