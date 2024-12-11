import android.app.Application;
import com.github.catvod.net.OkHttp;
import com.github.catvod.net.OkResult;
import com.github.catvod.server.Server;
import com.github.catvod.utils.Json;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(RobolectricTestRunner.class)
public class ProxyTest {
    // @Mock
    private Application mockContext;


    @org.junit.Before
    public void setUp() throws Exception {
        Server.get().start();
    }

    @org.junit.Test
    public void homeContent() throws Exception {


       // OkResult result = OkHttp.get("http://127.0.0.1:9978/proxy?do=quark&type=video&url=aHR0cHM6Ly92aWRlby1wbGF5LWgtemIuZHJpdmUucXVhcmsuY24vcXYvMTgzMkExMjlFMTBFMkM1ODdDMjY1RThBRkY4NkU0OENCNDk5NUZERl8xNTE1ODQ0MDZfX3NoYTFfc3ozXzliZTVkNzU4L2RiNGFhMDhiYjAwN2RmYWYvbWVkaWEubTN1OD9hdXRoX2tleT0xNzI2MTk5OTkyLTQ1OTk4LTEwODAwLWI1ODI4MWNlYzRkZDkzZWMwNjY4YzRkMTVhNmEzNjk4JnNwPTU1JnRva2VuPTItOC0yLTEwMC01NS0yNjllXzkyNTRkMmIzMTc3ZThlMTNhYzAxMDk3MGNlZjI0MWVmLTM1MTgxN2Y4MzNkMTBmOTliMmIxYjdhNzYxMmIxNmJiLWUwZjdkZjAyZmZkZGUyN2YwNjU3ZjJjNDc5ZTA4NjY3JnVkPTE2LTAtMS0yLTEtMC03LU4tMS0xNi0wLU4mbXQ9Mw==&header=eyJDb29raWUiOiJfVVBfQTRBXzExX1x1MDAzZHdiOTY4MWZiYWVkNjQ1NGE4MTEyZjMxZTUzYjVjMGJlOyBfX3B1c1x1MDAzZDQ1YmVlZmE5M2U4Nzc1YzkyMTE0ODdkMGM4ZGRkMmIxQUFTQ21WNVM3TFkwZGZYOTBOM3A0d1UvRzRmL29TMGdaSzZjcHhaTVppRHRYdDlzN0tpU3MzdFZaT1huSURlbDY5QzlLYVE2MUlRbG5MWUgyclM0TkdqTzsgX19rcFx1MDAzZGZlNjYzYTkwLTY4ZDUtMTFlZi04YjIzLWU3N2IwZWFhMzUyYzsgX19rcHNcdTAwM2RBQVQzMkZvYit2cTY2em5PNVVIU0hBUGk7IF9fa3RkXHUwMDNkMzlvWEUrQlQ1M1lsRmdVZkZWcTlrd1x1MDAzZFx1MDAzZDsgX191aWRcdTAwM2RBQVQzMkZvYit2cTY2em5PNVVIU0hBUGk7IF9fcHV1c1x1MDAzZGU5YmRlODQ1NDA4ZTkzYWYxNDY2OTgzZTZiN2JiNzdlQUFTVjFhS0pLTFJYR2p2SFJmd1FKNWd1bTh5OGk5Q0xEZy8xa1l0c1h3YURrbHJNRE9wQ2ZYVkV4SDdlWDRRTnVWRzFTdUhTVDBadFpheGJ0dTUwbDRzcVV6bVBLVkFNTUpFR3MrOUxoYUs3N0Exb1I2RUt4RjBLVTN4RHRacVh1ekxxMUY2clRwNGM2RnhDQUk4OFBuMmNBeWZqUlVLWGdXYTByYXl4cWEwNXhwTWI4ajJUb3pPcDMyRDU3clkxVmxNWjVjSWV5MDdTQmtVS2hFdTN3V0NiIiwiVXNlci1BZ2VudCI6Ik1vemlsbGEvNS4wIChXaW5kb3dzIE5UIDEwLjA7IFdpbjY0OyB4NjQpIEFwcGxlV2ViS2l0LzUzNy4zNiAoS0hUTUwsIGxpa2UgR2Vja28pIHF1YXJrLWNsb3VkLWRyaXZlLzIuNS4yMCBDaHJvbWUvMTAwLjAuNDg5Ni4xNjAgRWxlY3Ryb24vMTguMy41LjQtYjQ3ODQ5MTEwMCBTYWZhcmkvNTM3LjM2IENoYW5uZWwvcGNra19vdGhlcl9jaCIsIlJlZmVyZXIiOiJodHRwczovL3Bhbi5xdWFyay5jbi8ifQ==", null, null);
        //System.out.println(result);

        while (true) {
           // System.out.println("server is running ....");
            continue;

        }
    }


}