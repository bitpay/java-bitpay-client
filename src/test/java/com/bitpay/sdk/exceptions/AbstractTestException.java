/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Assertions;

abstract class AbstractTestException {

    protected static final String REASON_MESSAGE = "reasonMsg";
    protected static final String STATUS_CODE = "123456";

    protected void testExceptionWithoutStatus(
        final BitPayException exception,
        final String message,
        final String code
    ) {
        Assertions.assertEquals(
            code + ": " + message + " -> " + REASON_MESSAGE,
            exception.getReasonPhrase());
        Assertions.assertEquals(
            "Status: 000000 -> Reason: " + code + ": " + message + " -> " + REASON_MESSAGE,
            exception.getMessage());
        Assertions.assertNull(exception.getStatusCode());
    }

    protected void testExceptionWithStatus(
        final BitPayException exception,
        final String message,
        final String code
    ) {
        Assertions.assertEquals(
            code + ": " + message + " -> " + REASON_MESSAGE,
            exception.getReasonPhrase());
        Assertions.assertEquals(
            "Status: " + STATUS_CODE + " -> Reason: " + code + ": " + message + " -> " + REASON_MESSAGE,
            exception.getMessage());
    }
}
