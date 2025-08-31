package com.github.catvod.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(RobolectricTestRunner.class)
public class ProxyVideoTest {

    @Test
    public void proxyMultiThread() {
        //  ProxyVideo.proxyMultiThread()
      /*  Server.get().start();
        String url = ProxyVideo.buildCommonProxyUrl(
               // "https://js.shipin520.com/pc/images/new/banner20250225.mp4", new HashMap<>());
                "http://172.16.1.217:18089/ng-grid/video.mp4", new HashMap<>());
        System.out.println(url);*/
        System.out.println(ProxyServer.INSTANCE.buildProxyUrl("https://media.w3.org/2010/05/sintel/trailer.mp4", Map.of("header","2","header2","2")));
        ProxyServer.INSTANCE.start();

        while (true) {

        }
    }
}

