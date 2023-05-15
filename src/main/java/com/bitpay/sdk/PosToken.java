/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk;

/**
 * The type Pos token.
 */
public class PosToken {

    private final String value;

    /**
     * Instantiates a new Pos token.
     *
     * @param value the value
     */
    public PosToken(final String value) {
        this.value = value;
    }

    /**
     * Get Pos token value.
     *
     * @return the string
     */
    public String value() {
        return this.value;
    }
}
