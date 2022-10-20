package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Test;

public class PayoutRecipientCreationExceptionTest  extends AbstractTestException {

    private static final String MESSAGE = "Failed to submit payout recipient.";
    private static final String CODE = "BITPAY-PAYOUT-RECIPIENT-CREATE";

    @Test
    public void it_should_create_exception_without_status() {
        this.testExceptionWithoutStatus(this.getTestedClass(null), MESSAGE, CODE);
    }

    @Test
    public void it_should_create_exception_with_status() {
        this.testExceptionWithStatus(this.getTestedClass(AbstractTestException.STATUS_CODE), MESSAGE, CODE);
    }

    private PayoutRecipientCreationException getTestedClass(String status) {
        return new PayoutRecipientCreationException(status, AbstractTestException.REASON_MESSAGE);
    }
}
