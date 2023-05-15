/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.client;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

/**
 * The type Http request factory.
 */
public class HttpRequestFactory {

    /**
     * Create http get.
     *
     * @param fullUrl the full url
     * @return the http get
     */
    public HttpGet createHttpGet(String fullUrl) {
        return new HttpGet(fullUrl);
    }

    /**
     * Create http delete.
     *
     * @param fullUrl the full url
     * @return the http delete
     */
    public HttpDelete createHttpDelete(String fullUrl) {
        return new HttpDelete(fullUrl);
    }

    /**
     * Create http post.
     *
     * @param fullUrl the full url
     * @return the http post
     */
    public HttpPost createHttpPost(String fullUrl) {
        return new HttpPost(fullUrl);
    }

    /**
     * Create http put.
     *
     * @param fullUrl the full url
     * @return the http put
     */
    public HttpPut createHttpPut(String fullUrl) {
        return new HttpPut(fullUrl);
    }
}
