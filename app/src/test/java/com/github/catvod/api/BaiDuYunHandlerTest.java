package com.github.catvod.api;

import cn.hutool.core.io.FileUtil;
import com.github.catvod.utils.Json;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.net.URLEncoder;

@RunWith(RobolectricTestRunner.class)
public class BaiDuYunHandlerTest {

    private BaiDuYunHandler baiDuYunHandler;


    @Before
    public void setUp() {
        baiDuYunHandler =  BaiDuYunHandler.get();

    }

    @Test
    public void startScan() throws Exception {
        // Mock the OkHttp.get method to return a predefined OkResult
        // Execute the method under test
        FileUtil.writeBytes(baiDuYunHandler.startScan(), "c://qrcode.png");

        while (true) {

        }

    }

   /* @Test
    public void refreshCookie() throws Exception {

        JsonObject obj = Json.safeObject("{\"open.e.189.cn\":{\"OPENINFO\":\"33c28688ef52ce9e3a9ef87388047efbde5e3e2e4c7ef6ef267632468c7dfaf294ff59fa59d34801\",\"pageOp\":\"f73420158c5c010491f1faa4fc91870e\",\"LT\":\"a8900fc0ecae0c59\",\"GUID\":\"b959026ffdf84080ae8567afd9ea4c32\",\"SSON\":\"dc466c8192e3109eaea837c1d136c1fd065253ce1c7d3a66ca1520d7d6d6307b10a1fe65c7becac73b95f24a6e681e654ec4f47c39533ebcc48bb78d6d6e63d1bbf3334e6e97eaa7092d34f87bf1209e256cd4822db68da051a0aeb532d94408c8e50486347fc713813dafc5776a7cfa665ddf96837151232745aa2957fb441d8a79ca7d86f46452060794e6f4b5873ab99ed476629aed2c7b36a44613c92f925dcfd221fce142cd1ecaab667236df697ece293e3ca24030918e5b357bc193118772278748606ade7262bf25ae7527d3c8a059bd48fc08b53b182e61e543a7e9bd1562b50bf80438\"},\"cloud.189.cn\":{\"JSESSIONID\":\"12088774C4B78E632EB944ECA2E6705F\",\"COOKIE_LOGIN_USER\":\"24DA4CBA27A8388982710C2F3D55EFAA84AEE67E9B3EF1B7AC1C565BEEF24C562052CB9B5EAC85E733C10C2704225133CD625407C352ED5D\"}}");
        baiDuYunHandler.setCookie(obj);
        baiDuYunHandler.refreshCookie();

        while (true) {

        }

    }

    @Test
    public void download() throws Exception {
        // Mock the OkHttp.get method to return a predefined OkResult
        // Execute the method under test


    }

    @Test
    public void testgetUUID() throws Exception {
        JsonObject uuid = tianYiHandler.getUUID();
        System.out.println(uuid);
    }

    @Test
    public void testdownloadQRCode() throws Exception {
*//*
        JsonObject uuidInfo = tianYiHandler.getUUID();
        String uuid = uuidInfo.get("uuid").getAsString();
        byte[] qrCode = tianYiHandler.downloadQRCode(uuid);
        FileUtil.writeBytes(qrCode, "c://qrcode.png");

        System.out.println(uuid);*//*

        String url = "https://cloud.189.cn/api/portal/callbackUnify.action?browserId=dff95dced0b03d9d972d920f03ddd05e&redirectURL=https%3A%2F%2Fcloud.189.cn%2Fweb%2Fredirect.html";

        String encode = "https%3A%2F%2Fcloud.189.cn%2Fapi%2Fportal%2FcallbackUnify.action%3FbrowserId%3Ddff95dced0b03d9d972d920f03ddd05e%26redirectURL%3Dhttps%253A%252F%252Fcloud.189.cn%252Fweb%252Fredirect.html";
        assert URLEncoder.encode(url, "UTF-8").equals(encode);
    }


    @Test
    public void loginWithPassword() throws Exception {
         tianYiHandler.loginWithPassword("18896781601","Lushunming@0526");
        System.out.println("1111");
    }
*/
}