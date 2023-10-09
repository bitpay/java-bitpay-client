/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.logger;

public class EmptyLogger implements BitPayLogger {

    @Override
    public void logRequest(String method, String endpoint, String json) {
    }

    @Override
    public void logResponse(String method, String endpoint, String json) {
    }

    @Override
    public void logError(String message) {
    }
}
