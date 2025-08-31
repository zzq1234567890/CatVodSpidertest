/*
package com.github.catvod.utils

import com.github.catvod.crawler.SpiderDebug
import com.github.catvod.net.OkHttp
import com.google.gson.Gson
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.response.respondBytesWriter
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import okhttp3.Response
import java.nio.ByteBuffer
import java.nio.charset.Charset

object KtorServer {

    private val THREAD_NUM = Runtime.getRuntime().availableProcessors() * 2
    private val infos = mutableMapOf<String, Array<Any>>()
    var ser: io.ktor.server.engine.ApplicationEngine? = null
    var port = 10010

    //每个片1MB
    private val partSize = 1024 * 1024 * 1
    fun init() {

        do {
            try {
                ser = embeddedServer(Netty, port) {
                    install(CallLogging)


                    routing {
                        get("/") {
                            call.respondText("ktor running on $port", ContentType.Text.Plain)
                        }
                        get("/proxy") {
                            SpiderDebug.log("代理中: ${call.parameters["url"]}")


                            val url = Util.base64Decode(call.parameters["url"])
                            val header: Map<String, String> = Gson().fromJson<Map<String, String>>(
                                Util.base64Decode(call.parameters["headers"]),
                                MutableMap::class.java
                            )
                            proxyAsync(
                                url, header, call
                            )
                        }
                    }
                }.start(wait = true)

            } catch (e: Exception) {
                SpiderDebug.log("start server e:" + e.message)
                ++port
                ser?.stop()
            }
        } while (port < 13000)
        SpiderDebug.log("ktorServer start on $port")
    }

    */
/** 启动服务器 *//*

    fun start() {

        CoroutineScope(Dispatchers.IO).launch { init() }
    }


    */
/** 停止服务器 *//*

    fun stop() {
        ser?.stop(1_000, 2_000)
    }

    fun buildProxyUrl(url: String, headers: Map<String, String>): String {
        return "http://127.0.0.1:$port/proxy?url=${Util.base64Encode(url.toByteArray(Charset.defaultCharset()))}&headers=${
            Util.base64Encode(
                Gson().toJson(headers).toByteArray(
                    Charset.defaultCharset()
                )
            )
        }"
    }

    */
/**
     * 获取是否分片信息，顺带请求一个1MB块
     *//*

    @Throws(java.lang.Exception::class)
    fun getInfo(url: String?, headers: Map<String, String>): Array<Any> {
        val newHeaders: MutableMap<String, String> = java.util.HashMap(headers)
        newHeaders["Range"] = "bytes=0-" + (1024 * 1024 - 1)
        newHeaders["range"] = "bytes=0-" + (1024 * 1024 - 1)
        val info = ProxyVideo.proxy(url, newHeaders)
        return info
    }

    private suspend fun proxyAsync(
        url: String, headers: Map<String, String>, call: ApplicationCall
    ) {
        val channels = List(THREAD_NUM) { Channel<ByteArray>() }
        try {
            SpiderDebug.log("--proxyMultiThread: THREAD_NUM: $THREAD_NUM")


            var rangeHeader = call.request.headers[HttpHeaders.Range]
            //没有range头
            if (rangeHeader.isNullOrEmpty()) {
                // 处理初始请求
                rangeHeader = "bytes=0-"
            }
            headers.toMutableMap().apply {
                put(HttpHeaders.Range, rangeHeader)
            }

            // 解析范围请求
            val (startPoint, endPoint) = parseRangePoint(
                rangeHeader
            )
            SpiderDebug.log("startPoint: $startPoint; endPoint: $endPoint")
            val contentLength = getContentLength(url, headers)
            SpiderDebug.log("contentLength: $contentLength")
            val finalEndPoint = if (endPoint == -1L) contentLength - 1 else endPoint

            call.response.headers.apply {
                append(HttpHeaders.Connection, "keep-alive")
                append(HttpHeaders.ContentLength, (finalEndPoint - startPoint + 1).toString())
                append(HttpHeaders.ContentRange, "bytes $startPoint-$finalEndPoint/$contentLength")
            }
            call.response.status(HttpStatusCode.PartialContent)

            // 使用流式响应
            call.respondBytesWriter() {
                var currentStart = startPoint


                // 启动生产者协程下载数据

                val producerJob = mutableListOf<Job>()

                while (currentStart <= finalEndPoint) {
                    producerJob.clear()
                    // 创建通道用于接收数据块

                    for (i in 0 until THREAD_NUM) {

                        if (currentStart > finalEndPoint) break
                        val chunkStart = currentStart
                        val chunkEnd = minOf(currentStart + partSize - 1, finalEndPoint)
                        producerJob += CoroutineScope(Dispatchers.IO).launch {
                            // 异步下载数据块
                            val data = getVideoStream(chunkStart, chunkEnd, url, headers)
                            channels[i].send(data)

                        }
                        currentStart = chunkEnd + 1
                    }
                    for ((index, job) in producerJob.withIndex()) {

                        val data = channels[index].receive()
                        SpiderDebug.log("Received chunk: ${data.size} bytes")
                        writeFully(ByteBuffer.wrap(data))
                    }
                }


            }
        } catch (e: Exception) {
            SpiderDebug.log("error: ${e.message}")
            call.respondText("error: ${e.message}", ContentType.Text.Plain)
        } finally {
            channels.forEach { it.close() }
        }
    }


    // 辅助函数（需要实现）
    private fun parseRangePoint(rangeHeader: String): Pair<Long, Long> {
        // 实现范围解析逻辑
        val regex = """bytes=(\d+)-(\d*)""".toRegex()
        val match = regex.find(rangeHeader) ?: return 0L to -1L
        val start = match.groupValues[1].toLong()
        val end = match.groupValues[2].takeIf { it.isNotEmpty() }?.toLong() ?: -1L
        return start to end
    }

    private fun getContentLength(url: String, headers: Map<String, String>): Long {
        // 实现获取内容长度逻辑
        val res = OkHttp.newCall(url, headers)
        res.body()?.close()
        return res.headers(HttpHeaders.ContentLength)[0]?.toLong() ?: 0L
    }

    private suspend fun getVideoStream(
        start: Long, end: Long, url: String, headers: Map<String, String>
    ): ByteArray {
        val header = headers.toMutableMap()
        // 实现分段下载逻辑
        SpiderDebug.log("getVideoStream: $start-$end; ")
        header[HttpHeaders.Range] = "bytes=$start-$end"
        val res = OkHttp.newCall(url, header)
        val body = res.body()
        return body?.bytes() ?: ByteArray(0)
    }


    private fun downloadRange(
        url: String, headerNew: Map<String, String>
    ): Response? = OkHttp.newCall(url, headerNew)
}
*/
