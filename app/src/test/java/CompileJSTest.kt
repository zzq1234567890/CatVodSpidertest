import android.app.Application
import com.dokar.quickjs.QuickJs
import com.dokar.quickjs.binding.function
import com.dokar.quickjs.quickJs
import com.github.catvod.net.OkHttp
import com.github.catvod.spider.ChangZhang
import com.github.catvod.spider.Init
import com.github.catvod.utils.FileUtil
import com.github.catvod.utils.Json
import com.github.catvod.utils.Util
import com.google.common.collect.ImmutableMap
import com.google.common.collect.Maps
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.io.FileWriter
import kotlin.io.encoding.Base64


class CompileJSTest {
    // @Mock


    @Before
    @Throws(Exception::class)
    fun setUp() {

    }

    @Test
    @Throws(Exception::class)
    fun homeContent(): Unit {

        val content = OkHttp.string("https://androidcatvodspider.netlify.app/json/js/jpyy2.js")/* val bytes = context!!.compileModule(content, "newvision.js")
         val result = "//bb" + Util.base64Encode(bytes)*/

        val scope = CoroutineScope(Dispatchers.Default)

        fun startTask() = runBlocking {
            launch {
                val json = Json.toJson(ImmutableMap.of("url", "https://androidcatvodspider.netlify.app/json/js/jpyy2.js"));
                quickJs {
                    val helloModuleCode = """
                        export function greeting(to) {
                            return "Hi from the hello module!"+ to.url;
                        }
                         """.trimIndent()
                    addModule(name = "hello", code = helloModuleCode)
                    var result: Any? = null
                    function("returns") { result = it.first() }
                    val to = "mike";
                    evaluate<Any?>(
                        """ 
                              import * as hello from "hello";
                              let map= JSON.parse('${json}')
                              returns(hello.greeting(map))
            
                         """.trimIndent(),
                        asModule = true,
                    )
                    println("result:" + result)
                }


            }
        }
        startTask()

    }
}