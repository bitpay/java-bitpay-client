package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Test;

public class PayoutBatchNotificationExceptionTest  extends AbstractTestException {

    private static final String MESSAGE = "Failed to send payout batch notification.";
    private static final String CODE = "BITPAY-PAYOUT-BATCH-NOTIFICATION";

    @Test
    public void it_should_create_exception_without_status() {
        this.testExceptionWithoutStatus(this.getTestedClass(null), MESSAGE, CODE);
    }

    @Test
    public void it_should_create_exception_with_status() {
        this.testExceptionWithStatus(this.getTestedClass(AbstractTestException.STATUS_CODE), MESSAGE, CODE);
    }

    private PayoutBatchNotificationException getTestedClass(String status) {
        return new PayoutBatchNotificationException(status, AbstractTestException.REASON_MESSAGE);
    }
}
