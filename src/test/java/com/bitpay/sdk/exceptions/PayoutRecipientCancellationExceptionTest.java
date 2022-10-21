package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Test;

public class PayoutRecipientCancellationExceptionTest  extends AbstractTestException {

    private static final String MESSAGE = "Failed to cancel payout recipient";
    private static final String CODE = "BITPAY-PAYOUT-RECIPIENT-CANCELLATION";

    @Test
    public void it_should_create_exception_without_status() {
        this.testExceptionWithoutStatus(this.getTestedClass(null), MESSAGE, CODE);
    }

    @Test
    public void it_should_create_exception_with_status() {
        this.testExceptionWithStatus(this.getTestedClass(AbstractTestException.STATUS_CODE), MESSAGE, CODE);
    }

    private PayoutRecipientCancellationException getTestedClass(String status) {
        return new PayoutRecipientCancellationException(status, AbstractTestException.REASON_MESSAGE);
    }
}
