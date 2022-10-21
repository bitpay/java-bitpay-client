package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Test;

public class RefundUpdateExceptionTest  extends AbstractTestException {

    private static final String MESSAGE = "Failed to update refund";
    private static final String CODE = "BITPAY-REFUND-UPDATE";

    @Test
    public void it_should_create_exception_without_status() {
        this.testExceptionWithoutStatus(this.getTestedClass(null), MESSAGE, CODE);
    }

    @Test
    public void it_should_create_exception_with_status() {
        this.testExceptionWithStatus(this.getTestedClass(AbstractTestException.STATUS_CODE), MESSAGE, CODE);
    }

    private RefundUpdateException getTestedClass(String status) {
        return new RefundUpdateException(status, AbstractTestException.REASON_MESSAGE);
    }
}