/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk;

public class PrivateKey {

    private final String value;

    public PrivateKey(final String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
