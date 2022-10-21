package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Test;

public class PayoutCancellationExceptionTest  extends AbstractTestException {

    private static final String MESSAGE = "Failed to cancel payout.";
    private static final String CODE = "BITPAY-PAYOUT-CANCEL";

    @Test
    public void it_should_create_exception_without_status() {
        this.testExceptionWithoutStatus(this.getTestedClass(null), MESSAGE, CODE);
    }

    @Test
    public void it_should_create_exception_with_status() {
        this.testExceptionWithStatus(this.getTestedClass(AbstractTestException.STATUS_CODE), MESSAGE, CODE);
    }

    private PayoutCancellationException getTestedClass(String status) {
        return new PayoutCancellationException(status, AbstractTestException.REASON_MESSAGE);
    }
}