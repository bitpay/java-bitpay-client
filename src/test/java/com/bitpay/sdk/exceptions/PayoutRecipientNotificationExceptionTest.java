package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Test;

public class PayoutRecipientNotificationExceptionTest  extends AbstractTestException {

    private static final String MESSAGE = "Failed to send payout recipient notification.";
    private static final String CODE = "BITPAY-PAYOUT-RECIPIENT-NOTIFICATION";

    @Test
    public void it_should_create_exception_without_status() {
        this.testExceptionWithoutStatus(this.getTestedClass(null), MESSAGE, CODE);
    }

    @Test
    public void it_should_create_exception_with_status() {
        this.testExceptionWithStatus(this.getTestedClass(AbstractTestException.STATUS_CODE), MESSAGE, CODE);
    }

    private PayoutRecipientNotificationException getTestedClass(String status) {
        return new PayoutRecipientNotificationException(status, AbstractTestException.REASON_MESSAGE);
    }
}
