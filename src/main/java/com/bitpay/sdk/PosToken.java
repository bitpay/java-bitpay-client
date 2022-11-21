/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk;

public class PosToken {

    private final String value;

    public PosToken(final String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
