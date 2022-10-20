package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Test;

public class RateQueryExceptionTest  extends AbstractTestException {

    private static final String MESSAGE = "Failed to retrieve rates";
    private static final String CODE = "BITPAY-RATES-GET";

    @Test
    public void it_should_create_exception_without_status() {
        this.testExceptionWithoutStatus(this.getTestedClass(null), MESSAGE, CODE);
    }

    @Test
    public void it_should_create_exception_with_status() {
        this.testExceptionWithStatus(this.getTestedClass(AbstractTestException.STATUS_CODE), MESSAGE, CODE);
    }

    private RateQueryException getTestedClass(String status) {
        return new RateQueryException(status, AbstractTestException.REASON_MESSAGE);
    }
}