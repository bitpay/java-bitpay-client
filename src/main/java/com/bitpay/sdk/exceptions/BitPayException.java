/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.exceptions;

/**
 * General BitPay Exception which is inherited by all other exceptions.
 *
 * @see <a href="https://bitpay.readme.io/reference/error-codes">Rest API Error Codes</a>
 */
public class BitPayException extends Exception {

    private static final long serialVersionUID = -5407556346434827903L;

    /**
     * Construct the BitPayException.
     *
     * @param message String [optional] The Exception message.
     */
    public BitPayException(String message) {
        super(message);
    }
}
