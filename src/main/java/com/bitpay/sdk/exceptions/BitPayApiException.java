/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.exceptions;

public class BitPayApiException extends BitPayException {

    private static final long serialVersionUID = 8211452784073681895L;

    private final String code;

    public BitPayApiException(String message, String code) {
        super(message);
        this.code = code;
    }

    /**
     * <p>An error code consists of 6 digits. </p>
     * <p>The first two digits of an error code represent the HTTP method that was used to call it.</p>
     * <p>The next two digits refer to the resource that was impacted.</p>
     * <p>The last two digits refer to the specific error.</p>
     * <p>eg. 010103 - Missing parameters for Invoice POST request.</p>
     *
     * @return String
     */
    public String getCode() {
        return this.code;
    }
}
