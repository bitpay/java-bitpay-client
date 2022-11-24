/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HttpRequestFactoryTest {

    protected static final String FULL_URL = "anyString";

    @Test
    public void it_should_create_httpGet_object() {
        Assertions.assertSame(HttpGet.class, this.getTestedClass().createHttpGet(FULL_URL).getClass());
    }

    @Test
    public void it_should_create_httpDelete_object() {
        Assertions.assertSame(HttpDelete.class, this.getTestedClass().createHttpDelete(FULL_URL).getClass());
    }

    @Test
    public void it_should_create_httpPost_object() {
        Assertions.assertSame(HttpPost.class, this.getTestedClass().createHttpPost(FULL_URL).getClass());
    }

    @Test
    public void it_should_create_httpPut_object() {
        Assertions.assertSame(HttpPut.class, this.getTestedClass().createHttpPut(FULL_URL).getClass());
    }

    private HttpRequestFactory getTestedClass() {
        return new HttpRequestFactory();
    }
}
