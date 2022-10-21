package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SettlementExceptionTest  extends AbstractTestException {

    private static final String MESSAGE = "An unexpected error occurred while trying to manage the settlements";
    private static final String CODE = "BITPAY-SETTLEMENTS-GENERIC";

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
        SettlementException exception = new SettlementException(null, message);
        Assertions.assertEquals(message, exception.getReasonPhrase());
    }

    private SettlementException getTestedClass(String status) {
        return new SettlementException(status, AbstractTestException.REASON_MESSAGE);
    }
}