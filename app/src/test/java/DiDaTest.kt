import android.app.Application
import com.github.catvod.spider.DiDa
import com.github.catvod.spider.Init
import com.github.catvod.utils.Json
import com.google.gson.GsonBuilder
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class DiDaTest {
    // @Mock
    private var mockContext: Application? = null

    private var spider: DiDa? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mockContext = RuntimeEnvironment.application
        Init.init(mockContext)
        spider = DiDa()
        spider!!.init(mockContext, "")
    }

    @Test
    @Throws(Exception::class)
    fun homeContent() {
        val content = spider!!.homeContent(true)
        val map = Json.safeObject(content)
        val gson = GsonBuilder().setPrettyPrinting().create()

        println("homeContent--" + gson.toJson(map))

        //Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @Test
    @Throws(Exception::class)
    fun homeVideoContent() {
        val content = spider!!.homeVideoContent()
        val map = Json.safeObject(content)
        val gson = GsonBuilder().setPrettyPrinting().create()

        println("homeVideoContent--" + gson.toJson(map))

        //Assert.assertFalse(map.getAsJsonArray("list").isEmpty());
    }

    @Test
    @Throws(Exception::class)
    fun categoryContent() {
        val content = spider!!.categoryContent("/type/1.html", "2", true, null)
        val map = Json.safeObject(content)
        val gson = GsonBuilder().setPrettyPrinting().create()
        println("categoryContent--" + gson.toJson(map))
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun detailContent() {
        val content = spider!!.detailContent(mutableListOf<String?>("/detail/1370.html"))
        val map = Json.safeObject(content)
        val gson = GsonBuilder().setPrettyPrinting().create()
        println("detailContent--" + gson.toJson(map))
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun playerContent() {
        val froms ="BD3$$\$超清F$$\$超清A$$\$超清B$$\$夸克网盘$$\$百度网盘"

        val urls ="HD1080P（英语）$/play/1370-1-1.html$$$1080P$/play/1370-6-1.html$$\$HD$/play/1370-3-1.html$$$1080P$/play/1370-4-1.html$$$1080P$/play/1370-2-1.html$$\$合集$/play/1370-5-1.html"
        for (i in urls.split("\\$\\$\\$".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().indices) {
            val content = spider!!.playerContent(
                froms.split("\\$\\$\\$".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()[i],
                urls.split("\\$\\$\\$".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[i].split("\\$".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1],
                ArrayList<String?>()
            )
            val map = Json.safeObject(content)
            val gson = GsonBuilder().setPrettyPrinting().create()
            println("playerContent--" + gson.toJson(map))
            Assert.assertFalse(map.getAsJsonPrimitive("url").getAsString().isEmpty())
        }
    }

    @Test
    @Throws(Exception::class)
    fun searchContent() {
        val content = spider!!.searchContent("红海", false)
        val map = Json.safeObject(content)
        val gson = GsonBuilder().setPrettyPrinting().create()
        println("searchContent--" + gson.toJson(map))
        Assert.assertFalse(map.getAsJsonArray("list").isEmpty())
    }
}