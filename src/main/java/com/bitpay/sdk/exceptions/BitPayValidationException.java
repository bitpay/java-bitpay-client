/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.exceptions;

public class BitPayValidationException extends BitPayGenericException {

    private static final long serialVersionUID = -6721535639566485766L;

    public BitPayValidationException(String message) {
        super(message);
    }
}
