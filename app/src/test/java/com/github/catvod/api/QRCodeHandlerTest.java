package com.github.catvod.api;

import com.github.catvod.net.OkHttp;
import com.github.catvod.net.OkResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.HashMap;

@RunWith(RobolectricTestRunner.class)
public class QRCodeHandlerTest {

    private UCTokenHandler qrCodeHandler;


    @Before
    public void setUp() {
        qrCodeHandler = new UCTokenHandler();

    }

    @Test
    public void testStartUC_TOKENScan() throws Exception {
        // Mock the OkHttp.get method to return a predefined OkResult
        // Execute the method under test
        String result = qrCodeHandler.startUC_TOKENScan();
        System.out.println(result);
        while (true) {

        }

    }

    @Test
    public void download() throws Exception {
        // Mock the OkHttp.get method to return a predefined OkResult
        // Execute the method under test
        qrCodeHandler.download("eyJhbGciOiJIUzI1NiIsIlR5cGUiOiJKd3QiLCJ0eXAiOiJKV1QifQ.eyJvcGVuSWQiOiJkMTQ4MjM1MmFiMWU0NmYwOGQ3M2VmYzQyYWRiOTgxNSIsImV4cCI6MTc0MjUyNjU1MiwidG9rZW4iOiJkZmEwMjI2YzJmNDQ0ZTlmYTAxNzYxNGVhMDVkODczOCJ9.mUlFZdgSfACjZaBsmYNdgZSY7eSy4_hI3oZ8niq36Xs", "c31c86354605487cbb077a59d3bfa8ad");


    }




}