/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk;

/**
 * The type Private key.
 */
public class PrivateKey {

    private final String value;

    /**
     * Instantiates a new Private key.
     *
     * @param value the value
     */
    public PrivateKey(final String value) {
        this.value = value;
    }

    /**
     * Get Private Key value.
     *
     * @return the string
     */
    public String value() {
        return this.value;
    }
}
