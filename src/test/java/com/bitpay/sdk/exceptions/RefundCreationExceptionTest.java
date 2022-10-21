package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Test;

public class RefundCreationExceptionTest  extends AbstractTestException {

    private static final String MESSAGE = "Failed to create refund";
    private static final String CODE = "BITPAY-REFUND-CREATE";

    @Test
    public void it_should_create_exception_without_status() {
        this.testExceptionWithoutStatus(this.getTestedClass(null), MESSAGE, CODE);
    }

    @Test
    public void it_should_create_exception_with_status() {
        this.testExceptionWithStatus(this.getTestedClass(AbstractTestException.STATUS_CODE), MESSAGE, CODE);
    }

    private RefundCreationException getTestedClass(String status) {
        return new RefundCreationException(status, AbstractTestException.REASON_MESSAGE);
    }
}