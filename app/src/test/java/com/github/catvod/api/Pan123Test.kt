package com.github.catvod.api

import com.github.catvod.utils.Util
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class Pan123Test {
    var pan123: Pan123Api? = null

    @Before
    fun setUp() {
        pan123 = Pan123Api
      //  pan123!!.login()
    }
    @Test
    @Throws(Exception::class)
    fun login() {
      //pan123!!.login()
        "https://123684.com/s/u9izjv-smUWv".matches(Pan123Api.regex.toRegex())
        "https://www.123865.com/s/u9izjv-6hSWv".matches(Util.patternQuark.toRegex())
    }

    @Test
    @Throws(Exception::class)
    fun processShareData() {
        val result: Map<String, String> = pan123!!.getShareData("https://www.123865.com/s/u9izjv-6hSWv")
        println(result)


        val files= pan123!!.getFilesByShareUrl(result["key"]!!, result["sharePwd"]!!);
        println(files)

       /* if (files != null) {
            for (file in files) {
                val playUrl = pan123!!.getDownload(result["key"]!!, file.fileId, file.S3KeyFlag, file.Size, file.Etag)
                println(playUrl)
            }
        }*/ if (files != null) {
            for (file in files) {
                val playUrl = pan123!!.getLiveTranscoding(result["key"]!!, file.fileId, file.S3KeyFlag, file.Size, file.Etag)
                println(playUrl)
            }
        }

        /*for (String s : result.keySet()) {
            for (Map<String, String> stringStringMap : result.get(s)) {
                String playUrl = pan123.fetchPlayUrl(stringStringMap.get("contentId"), "");
                System.out.println(stringStringMap.get("name") + ":" + playUrl);
            }


        }*/
    } /*   @Test
    public void download() throws Exception {

        Map<String, List<Map<String, String>>> result = pan123.processShareData("https://caiyun.139.com/w/i/2nQQVZWCR24yf");
        System.out.println(result);
        for (String s : result.keySet()) {
            for (Map<String, String> stringStringMap : result.get(s)) {
                String playUrl = pan123.fetchPlayUrl(stringStringMap.get("contentId"), stringStringMap.get("linkID"));
                String url2 = pan123.get4kVideoInfo(stringStringMap.get("linkID"), stringStringMap.get("path"));
                System.out.println(stringStringMap.get("name") + ":" + playUrl);
                System.out.println(stringStringMap.get("url2") + ":" + url2);
            }
        }
        //;


    }

*/
}