package com.github.catvod.api;

import static org.junit.Assert.*;

import com.github.catvod.bean.quark.ShareData;
import com.github.catvod.net.OkHttp;
import com.github.catvod.net.OkResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(RobolectricTestRunner.class)
public class QuarkApiTest {

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
    public void testdownload() throws Exception {

        String url = "https://video-play-p-zb.cdn.yun.cn/P7r95SEr/1997440970/7986fbd7419840ba83d70e7ec36f933867d2fadf/67d2fadf98f5dd83fcd64481858236a79b4c3384?auth_key=1741952223-3304496-16098-d233ccbc65c0321102d36db56f3db9c2&sp=642&token=3-08917a23ee79367eab5e9dcfbd898751-3-2-963-5cbf_3bd039d6d54ec6a8737515d6f20a488c-0-0-0-0-e9fc047ff0aa9b590be130819e1e82f2&ud=9-0-1-2-1-5-8-N-0-4-0-N";
        OkResult okResult1 = OkHttp.get(url, new HashMap<>(), Map.of("Range", "bytes=0-"));
        assert okResult1.getCode() == 206;


    }
}