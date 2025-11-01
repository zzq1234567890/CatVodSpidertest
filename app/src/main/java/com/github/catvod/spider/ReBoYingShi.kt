package com.github.catvod.spider

import android.content.Context
import com.github.catvod.bean.Class
import com.github.catvod.bean.Result
import com.github.catvod.bean.Vod
import com.github.catvod.net.OkHttp
import com.github.catvod.utils.Json
import com.github.catvod.utils.Util
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URLEncoder
import java.nio.charset.Charset

/**
 * 电影云集
 *
 * @author lushunming
 * @createdate 2024-12-03
 */
class ReBoYingShi : Cloud() {
    private val siteUrl = "https://reboys.cn"




    private val headerWithCookie: MutableMap<String?, String?>
        get() {
            val header: MutableMap<String?, String?> = HashMap<String?, String?>()
            header.put("User-Agent", Util.CHROME)

            return header
        }

    @Throws(Exception::class)
    override fun init(context: Context?, extend: String?) {
        super.init(context, extend)
    }





    @Throws(Exception::class)
    override fun searchContent(key: String?, quick: Boolean): String? {
        return searchContent(key, "1")
    }

    @Throws(Exception::class)
    override fun searchContent(key: String?, quick: Boolean, pg: String?): String? {
        return searchContent(key, pg)
    }

    private fun searchContent(key: String?, pg: String?): String? {
        val searchPageURL = siteUrl + "/s/${URLEncoder.encode(key, Charset.defaultCharset().name())}.html"
        val html = OkHttp.string(searchPageURL, this.headerWithCookie)
        val apiToken = Util.findByRegex("const apiToken = \"(.*?)\";", html, 1)

        val searchURL = siteUrl + "/search?keyword=${URLEncoder.encode(key, Charset.defaultCharset().name())}"
        val header = headerWithCookie.toMutableMap()
        header.put("API-TOKEN", apiToken)
        val json = OkHttp.string(searchURL, header)
        val jsonObj = Json.safeObject(json)
        var vodList = emptyList<Vod>()
        if (jsonObj.get("code").asInt == 0) {
            val results = jsonObj.get("data").asJsonObject.get("data").asJsonObject.get("results").asJsonArray
            vodList = results.map {
                val title = it.asJsonObject.get("title").asString
                val vodId = it.asJsonObject.get("links").asJsonArray[0].asJsonObject.get("url").asString
                Vod(vodId, title, "", "")
            }
        }
        return Result.string(vodList)
    }
}