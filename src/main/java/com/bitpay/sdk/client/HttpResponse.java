/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.client;

import java.util.Map;

public class HttpResponse {

    private final Integer code;
    private final String body;
    private final Map<String, String> headers;
    private final String locale;
    private final String httpVersion;

    public HttpResponse(
        Integer code,
        String body,
        Map<String, String> headers,
        String locale,
        String httpVersion
    ) {
        this.code = code;
        this.body = body;
        this.headers = headers;
        this.locale = locale;
        this.httpVersion = httpVersion;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getBody() {
        return this.body;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getLocale() {
        return this.locale;
    }

    public String getHttpVersion() {
        return this.httpVersion;
    }
}
