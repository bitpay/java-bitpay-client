/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.logger;

public interface BitPayLogger {

    void logRequest(
        String method,
        String endpoint,
        String json
    );

    void logResponse(
        String method,
        String endpoint,
        String json
    );

    void logError(String message);
}
