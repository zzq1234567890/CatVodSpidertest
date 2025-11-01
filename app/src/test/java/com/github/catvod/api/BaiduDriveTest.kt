package com.github.catvod.api

import com.github.catvod.utils.Json
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class BaiduDriveTest {


    @Test
    fun getShareList() {

        runBlocking {
            val reslut =
                BaiduDrive.processShareLinks(listOf("https://pan.baidu.com/s/1So5RhSmNts0rWKEzjqinhQ?pwd=9527"))
            System.out.println(Json.toJson(reslut))
        }
    }

    @Test
    fun getBdUid() {

        runBlocking {
            val reslut = BaiduDrive.getBdUid()
            System.out.println(reslut)
        }
    }

    @Test
    fun _getSign() {
        val jsonStr =
            com.github.catvod.utils.Util.base64Decode("eyJ1ayI6IjExMDI4NjAxODI4OTgiLCJmaWQiOjE1NTY5Mzk0MDE4MjQ2NCwic2hhcmVpZCI6IjUzNDc0MDY5NzI0Iiwic3VybCI6IjFmMHk2MFJrWWNTc3A0RXdXb2xLZ0pnIiwicG5hbWUiOiIwNeWbveivrS00Sy7pq5jnoIHnjocubXA0IiwicXR5cGUiOiJwcmV2aWV3In0=")
        val obj = Json.safeObject(jsonStr)
        runBlocking {
            val reslut = BaiduDrive._getSign(obj)
            System.out.println(reslut)
        }


    }

    @Test
    fun getVideoUrl() {/* val jsonStr =
             com.github.catvod.utils.Util.base64Decode("eyJ1ayI6IjExMDI4NjAxODI4OTgiLCJmaWQiOjE1NTY5Mzk0MDE4MjQ2NCwic2hhcmVpZCI6IjUzNDc0MDY5NzI0Iiwic3VybCI6IjFmMHk2MFJrWWNTc3A0RXdXb2xLZ0pnIiwicG5hbWUiOiIwNeWbveivrS00Sy7pq5jnoIHnjocubXA0IiwicXR5cGUiOiJwcmV2aWV3In0=")
         val obj = Json.safeObject(jsonStr)
         runBlocking {
             val reslut = yunDrive?.getVideoUrl(obj)
             System.out.println(reslut)
         }*/
        val jsonStr =
            com.github.catvod.utils.Util.base64Decode("eyJ1ayI6IjI0MDAxMjE2NzIiLCJzaGFyZWlkIjoiMjc2NTk2OTA4MTAiLCJmaWQiOjcxNzUwMDM4OTg1MjYzOSwicmFuZHNrIjoiNEdjMzFTejVsZHNpdHcwRW12ZDNzam9XYWFuVjFEQlFsUHk3VkdESHklMkI0JTNEIiwicG5hbWUiOiJUaGUuUmV0dXJuLm9mLnRoZS5MYW1lLkhlcm8uMjAyNS4yMTYwcC5XRUItREwuSDI2NS5IRFIuNjBmcHMuRERQNS4xLURyZWFtSEQubWt2IiwicXR5cGUiOiJvcmlnaW5hbCJ9")
        val obj = Json.safeObject(jsonStr)
        runBlocking {
            val reslut = BaiduDrive.getVideoUrl(obj, "BD原画1")
            System.out.println(reslut)
        }
    }

    @Test
    fun createSaveDir() {/* val jsonStr =
             com.github.catvod.utils.Util.base64Decode("eyJ1ayI6IjExMDI4NjAxODI4OTgiLCJmaWQiOjE1NTY5Mzk0MDE4MjQ2NCwic2hhcmVpZCI6IjUzNDc0MDY5NzI0Iiwic3VybCI6IjFmMHk2MFJrWWNTc3A0RXdXb2xLZ0pnIiwicG5hbWUiOiIwNeWbveivrS00Sy7pq5jnoIHnjocubXA0IiwicXR5cGUiOiJwcmV2aWV3In0=")
         val obj = Json.safeObject(jsonStr)
         runBlocking {
             val reslut = yunDrive?.getVideoUrl(obj)
             System.out.println(reslut)
         }*/

        runBlocking {
            val reslut = BaiduDrive.createSaveDir()
            System.out.println(reslut)
        }
    }


}