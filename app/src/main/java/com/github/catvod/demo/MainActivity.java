package com.github.catvod.demo;

import android.app.Activity;
import android.os.Bundle;

import org.json.JSONObject;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 创建 Spider 对象实例，调用 Spider 对象的 init() 方法进行初始化，然后就可以执行其他的方法进行测试了
                // Spider spider = new Spider();
                // spider.init();
                // spider.homeContent();
            }
        }).start();
    }
}


