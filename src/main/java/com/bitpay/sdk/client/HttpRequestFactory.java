/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

public class HttpRequestFactory {

    public HttpGet createHttpGet(String fullUrl) {
        return new HttpGet(fullUrl);
    }

    public HttpDelete createHttpDelete(String fullUrl) {
        return new HttpDelete(fullUrl);
    }

    public HttpPost createHttpPost(String fullUrl) {
        return new HttpPost(fullUrl);
    }

    public HttpPut createHttpPut(String fullUrl) {
        return new HttpPut(fullUrl);
    }
}
