/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk;

public class ConfigFilePath {

    private final String value;

    public ConfigFilePath(final String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
