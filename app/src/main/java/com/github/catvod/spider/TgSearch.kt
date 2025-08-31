package com.github.catvod.spider

import com.github.catvod.bean.Result
import com.github.catvod.bean.Vod
import com.github.catvod.net.OkHttp
import com.github.catvod.utils.Util
import org.jsoup.Jsoup
import java.net.URLEncoder
import java.nio.charset.Charset

/**
 * @author zhixc
 */
class TgSearch : Cloud() {
    private val URL = "https://tg.252035.xyz/"

    private val header: Map<String, String>
        get() {
            val header: MutableMap<String, String> = HashMap()
            header["User-Agent"] = Util.CHROME
            return header
        }


    @Throws(Exception::class)
    override fun searchContent(key: String, quick: Boolean): String {
        val url =
            URL + "?channelUsername=tianyirigeng,tyypzhpd,XiangxiuNB,yunpanpan,kuakeyun,zaihuayun,Quark_Movies,alyp_4K_Movies,vip115hot,yunpanshare,dianyingshare&pic=true&keyword=" + URLEncoder.encode(
                key, Charset.defaultCharset().name()
            )
        val list: MutableList<Vod> = ArrayList()
        val html = OkHttp.string(url, header)
        val arr = html.split(":I".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (arr.size > 0) {
            for (s in arr) {
                val doc = Jsoup.parse(s)
                val id = doc.select(" a").eachAttr("href")
                    .first { it.contains("189") || it.contains("139") || it.contains("quark") }
                val name = doc.select("strong").text()
                list.add(Vod(id, name, "", ""))
            }
        }


        return Result.string(list)
    }
}
