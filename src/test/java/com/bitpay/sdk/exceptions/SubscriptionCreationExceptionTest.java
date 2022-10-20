package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Test;

public class SubscriptionCreationExceptionTest  extends AbstractTestException {

    private static final String MESSAGE = "Failed to create subscription";
    private static final String CODE = "BITPAY-SUBSCRIPTION-CREATE";

    @Test
    public void it_should_create_exception_without_status() {
        this.testExceptionWithoutStatus(this.getTestedClass(null), MESSAGE, CODE);
    }

    @Test
    public void it_should_create_exception_with_status() {
        this.testExceptionWithStatus(this.getTestedClass(AbstractTestException.STATUS_CODE), MESSAGE, CODE);
    }

    private SubscriptionCreationException getTestedClass(String status) {
        return new SubscriptionCreationException(status, AbstractTestException.REASON_MESSAGE);
    }
}