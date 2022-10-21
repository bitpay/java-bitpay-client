package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RateExceptionTest  extends AbstractTestException {

    private static final String MESSAGE = "An unexpected error occurred while trying to manage the rates";
    private static final String CODE = "BITPAY-RATES-GENERIC";

    @Test
    public void it_should_create_exception_without_status() {
        this.testExceptionWithoutStatus(this.getTestedClass(null), MESSAGE, CODE);
    }

    @Test
    public void it_should_create_exception_with_status() {
        this.testExceptionWithStatus(this.getTestedClass(AbstractTestException.STATUS_CODE), MESSAGE, CODE);
    }

    @Test
    public void it_should_build_exception_with_different_bitpay_message() {
        final String message = "BITPAY-CUSTOM";
        RateException exception = new RateException(null, message);
        Assertions.assertEquals(message, exception.getReasonPhrase());
    }

    private RateException getTestedClass(String status) {
        return new RateException(status, AbstractTestException.REASON_MESSAGE);
    }
}