package com.github.catvod.spider

import com.github.catvod.bean.Class
import com.github.catvod.bean.Result
import com.github.catvod.bean.Vod
import com.github.catvod.bean.Vod.VodPlayBuilder
import com.github.catvod.net.OkHttp
import com.github.catvod.utils.ProxyVideo
import com.github.catvod.utils.Util
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.net.URLEncoder

class DiDa : Cloud() {
    private val headers: HashMap<String?, String?>
        get() {
            val headers = java.util.HashMap<String?, String?>()
            headers.put("User-Agent", Util.CHROME)
            return headers
        }

    private fun parseVodFromDoc(doc: Document): MutableList<Vod?> {
        val list: MutableList<Vod?> = ArrayList<Vod?>()
        for (element in doc.select("div.myui-vodlist__box")) {
            var pic = element.selectFirst("img")?.attr("src")
            if (pic.isNullOrBlank()) {
                pic = element.selectFirst("a")?.attr("data-original")
            }
            val url = element.selectFirst("a")?.attr("href")
            val name = element.select("h4 > a").text()


            list.add(Vod(url, name, pic))

        }
        return list
    }

    @Throws(Exception::class)
    override fun homeContent(filter: Boolean): String? {

        val doc = Jsoup.parse(
            OkHttp.string(
                siteUrl, this.headers
            )
        )
        var classes: List<Class?>
        val menuList = doc.select("ul.nav-menu > li > a")
        classes = menuList.map {
            val url = it.attr("href")
            val title = it.text()
            Class(url, title)
        }

        val list: MutableList<Vod?> = parseVodFromDoc(doc)
        return Result.string(classes, list)
    }

    @Throws(Exception::class)
    override fun categoryContent(
        tid: String, pg: String, filter: Boolean, extend: java.util.HashMap<String?, String?>?
    ): String? {

        val type = tid.replace("/type/", "").replace(".html", "")
        val target: String = siteUrl + "/show/${type}--------${pg}---.html"

        val doc = Jsoup.parse(OkHttp.string(target, this.headers))
        val list = parseVodFromDoc(doc)
        val total = (pg.toInt() + 1) * 24
        return Result.get().vod(list).page(pg.toInt(), pg.toInt() + 1, 48, total).string()
    }

    @Throws(Exception::class)
    override fun detailContent(ids: MutableList<String?>): String? {
        val doc = Jsoup.parse(
            OkHttp.string(
                siteUrl + ids.get(0), this.headers
            )
        )
        val name = doc.select("h1").text()
        val img = doc.select("a.myui-vodlist__thumb > img")
        val pic = img.attr("src") ?: img.attr("data-original")

        val desc = doc.select("p.data").text()
        val year = Util.findByRegex("年份：(.*)又名", desc, 1).trim()
        val area = Util.findByRegex("地区：(.*)语言", desc, 1).trim()
        val actor = Util.findByRegex("主演：(.*)导演", desc, 1).trim()

        val builder = VodPlayBuilder()
        val playFromTab = doc.selectFirst("div.myui-panel__head  > ul.nav ")
        val playFromEles = playFromTab?.select("ul > li > a") ?: Elements()
        for (ele in playFromEles) {
            val playUrlList = mutableListOf<Vod.VodPlayBuilder.PlayUrl>()

            val id = ele.attr("href")
            val playFrom = ele.text()
            if (playFrom.contains("网盘")) {
                continue
            }
            val playElement = doc.select("$id")
            for (element in playElement.select("ul > li > a")) {
                val playUrl = VodPlayBuilder.PlayUrl()
                playUrl.url = element.attr("href")
                playUrl.name = element.text()
                playUrlList.add(playUrl)

            }
            builder.append(playFrom, playUrlList)
        }
        var panFrom = ""
        var panURl = ""

        for (element in doc.select(" div.myui-panel_bd.clearfix > p > a")) {
            if (element.attr("href").matches(Util.patternQuark.toRegex())) {
                panFrom = super.detailContentVodPlayFrom(listOf<String>(element.attr("href")))
                panURl = super.detailContentVodPlayUrl(listOf<String>(element.attr("href")))
            }
        }


        val result = builder.build()
        val vod = Vod()
        vod.setVodId(ids[0])
        vod.setVodPic(ProxyVideo.buildCommonProxyUrl(pic, Util.webHeaders(pic)))
        vod.setVodYear(year)
        vod.setVodActor(actor)
        vod.setVodArea(area)
        vod.setVodName(name)
        vod.setVodPlayFrom(result.vodPlayFrom + "$$$" + panFrom)
        vod.setVodPlayUrl(result.vodPlayUrl + "$$$" + panURl)
        return Result.string(vod)
    }

    @Throws(Exception::class)
    override fun searchContent(key: String?, quick: Boolean): String? {
        val doc = Jsoup.parse(
            OkHttp.string(
                searchUrl + URLEncoder.encode(key), this.headers
            )
        )
        val list: MutableList<Vod?> = ArrayList<Vod?>()
        for (element in doc.select("a.cover-link")) {
            val pic = element.select("img").attr("data-src")
            val url = element.attr("href")
            val name = element.select("img").attr("alt")
            val id: String? = url.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[2]

            list.add(Vod(id, name, ProxyVideo.buildCommonProxyUrl(pic, Util.webHeaders(pic))))
        }
        return Result.string(list)
    }

    @Throws(Exception::class)
    override fun playerContent(flag: String?, id: String?, vipFlags: MutableList<String?>?): String? {
        if (flag == null) {
            return Result.get().url("").header(this.headers).string()
        } else if (flag.contains("quark")) {
            super.playerContent(flag, id, vipFlags)
        } else {




        }




        return Result.get().url(id).header(this.headers).string()
    }


    companion object {
        private const val siteUrl = "https://www.didahd.pro"

        private val searchUrl: String = siteUrl + "/search?q="
    }
}

