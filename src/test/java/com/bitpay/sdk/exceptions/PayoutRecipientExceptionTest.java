package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PayoutRecipientExceptionTest  extends AbstractTestException {

    private static final String MESSAGE = "An unexpected error occurred while trying to manage the payout recipient";
    private static final String CODE = "BITPAY-PAYOUT-RECIPIENT-GENERIC";

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
        PayoutRecipientException exception = new PayoutRecipientException(null, message);
        Assertions.assertEquals(message, exception.getReasonPhrase());
    }

    private PayoutRecipientException getTestedClass(String status) {
        return new PayoutRecipientException(status, AbstractTestException.REASON_MESSAGE);
    }
}