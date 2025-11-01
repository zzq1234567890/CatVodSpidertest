package com.github.catvod.utils

import com.github.catvod.crawler.SpiderDebug
import com.github.catvod.net.OkHttp
import com.github.catvod.utils.ProxyVideo.getMimeType
import com.github.catvod.utils.ProxyVideo.parseRange
import kotlinx.coroutines.*
import okhttp3.Response
import org.apache.commons.lang3.StringUtils
import java.io.InputStream
import java.io.SequenceInputStream
import java.util.*
import kotlin.math.min

object DownloadMT {
    private val THREAD_NUM: Int = 16

    private val infos = mutableMapOf<String, Array<Any>>();

    fun proxyMultiThread(url: String, headers: Map<String, String>): Array<out Any?>? = runBlocking {
        proxyAsync(url, headers)
    }

    /**
     * 获取是否分片信息，顺带请求一个1MB块
     */
    @Throws(java.lang.Exception::class)
    fun getInfo(url: String?, headers: Map<String, String>): Array<Any> {
        val newHeaders: MutableMap<String, String> = java.util.HashMap(headers)
        newHeaders["Range"] = "bytes=0-" + (1024 * 1024 - 1)
        newHeaders["range"] = "bytes=0-" + (1024 * 1024 - 1)
        val info = ProxyVideo.proxy(url, newHeaders)
        return info
    }

    private suspend fun proxyAsync(url: String, headers: Map<String, String>): Array<out Any?>? {

        /*  val service = Executors.newFixedThreadPool(THREAD_NUM)*/
        SpiderDebug.log("--proxyMultiThread: THREAD_NUM: $THREAD_NUM")


        try {
            //缓存，避免每次都请求total等信息
            var info = infos[url]
            //第一次请求，请求是否支持range，顺带返回一个1MB块
            if (info == null) {
                infos.clear()
                info = CoroutineScope(Dispatchers.IO).async { getInfo(url, headers) }.await()
                infos[url] = info/*    //支持分片，先返回这个1MB块
                    if (info[0] as Int == 206) {
                        return info
                    }*/
            }

            val code = info[0] as Int
            SpiderDebug.log("-----------code:$code")

            if (code != 206) {
                return ProxyVideo.proxy(url, headers)
            }
            val resHeader = info[3] as MutableMap<String, String>
            val contentRange =
                if (StringUtils.isAllBlank(resHeader["Content-Range"])) resHeader["content-range"] else resHeader["Content-Range"]

            SpiderDebug.log("--contentRange:$contentRange")
            //文件总大小
            val total = StringUtils.split(contentRange, "/")[1]
            SpiderDebug.log("--文件总大小:$total")

            //如果文件太小，也不走代理
            /* if (total.toLong() < 1024 * 1024 * 100) {
                 return proxy(url, headers)
             }*/
            var range = if (StringUtils.isAllBlank(headers["range"])) headers["Range"] else headers["range"]
            if (StringUtils.isAllBlank(range)) range = "bytes=0-";
            SpiderDebug.log("---proxyMultiThread,Range:$range")
            val rangeObj = parseRange(
                range!!
            )
            //没有range,无需分割

            val partList = generatePart(rangeObj, total)

            // 存储执行结果的List
            val jobs = mutableListOf<Deferred<InputStream>>()
            val inputStreams = mutableListOf<InputStream>()
            for ((index, part) in partList.withIndex()) {


                val newRange = "bytes=" + part[0] + "-" + part[1]
                val headerNew: MutableMap<String, String> = HashMap(headers)

                headerNew["range"] = newRange
                headerNew["Range"] = newRange
                jobs += CoroutineScope(Dispatchers.IO).async {
                    val res = downloadRange(url, headerNew)
                    SpiderDebug.log(
                        ("---第" + index + "块下载完成" + ";Content-Range:" + (res?.headers())?.get(
                            "Content-Range"
                        ))
                    )
                    res?.body()!!.byteStream()
                }
            }
            inputStreams += jobs.awaitAll()

            var contentType: String? = ""


            //    SpiderDebug.log(" ++proxy res data:" + Json.toJson(response.body()));
            contentType = resHeader["Content-Type"]
            if (StringUtils.isAllBlank(contentType)) {
                contentType = resHeader["content-type"]
            }

            if (StringUtils.isAllBlank(contentType) && StringUtils.isNoneBlank(resHeader["Content-Disposition"])) {
                contentType = getMimeType(resHeader["Content-Disposition"])
            }


            /* respHeaders.put("Access-Control-Allow-Credentials", "true");
        respHeaders.put("Access-Control-Allow-Origin", "*");*/
            resHeader["Content-Length"] = (partList[THREAD_NUM - 1][1] - partList[0][0] + 1).toString()
            resHeader.remove("content-length")

            resHeader["Content-Range"] = String.format(
                "bytes %s-%s/%s", partList[0][0], partList[THREAD_NUM - 1][1], total
            )
            resHeader.remove("content-range")

            SpiderDebug.log("----proxy res contentType:$contentType")
            //   SpiderDebug.log("++proxy res body:" + response.body());
            SpiderDebug.log("----proxy res respHeaders:" + Json.toJson(resHeader))

            return arrayOf(
                206, contentType, SequenceInputStream(Vector(inputStreams).elements()), resHeader
            )


        } catch (e: Exception) {
            SpiderDebug.log("proxyMultiThread error:" + e.message)
            e.printStackTrace()
            return null
        }
    }

    fun generatePart(rangeObj: Map<String?, String>, total: String): List<LongArray> {
        val totalSize = total.toLong()
        //超过10GB，分块是32Mb，不然是16MB
        val partSize = if (totalSize > 1024L * 1024L * 1024L * 10L) 1024 * 1024 * 8 * 4L else 1024 * 1024 * 8 * 2L

        var start = rangeObj["start"]!!.toLong()
        var end = if (StringUtils.isAllBlank(rangeObj["end"])) start + partSize else rangeObj["end"]!!.toLong()


        end = min(end.toDouble(), (totalSize - 1).toDouble()).toLong()
        val length = end - start + 1

        val size = length / THREAD_NUM
        val partList: MutableList<LongArray> = ArrayList()
        for (i in 0..<THREAD_NUM) {
            val partEnd = min((start + size).toDouble(), end.toDouble()).toLong()

            partList.add(longArrayOf(start, partEnd))
            start = partEnd + 1
        }
        return partList
    }

    private fun downloadRange(
        url: String, headerNew: MutableMap<String, String>
    ): Response? = OkHttp.newCall(url, headerNew)
}