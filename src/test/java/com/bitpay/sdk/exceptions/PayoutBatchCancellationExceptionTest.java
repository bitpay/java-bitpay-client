package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Test;

public class PayoutBatchCancellationExceptionTest extends AbstractTestException {

    private static final String MESSAGE = "Failed to cancel payout batch.";
    private static final String CODE = "BITPAY-PAYOUT-BATCH-CANCELLATION";

    @Test
    public void it_should_create_exception_without_status() {
        this.testExceptionWithoutStatus(this.getTestedClass(null), MESSAGE, CODE);
    }

    @Test
    public void it_should_create_exception_with_status() {
        this.testExceptionWithStatus(this.getTestedClass(AbstractTestException.STATUS_CODE), MESSAGE, CODE);
    }

    private PayoutBatchCancellationException getTestedClass(String status) {
        return new PayoutBatchCancellationException(status, AbstractTestException.REASON_MESSAGE);
    }
}
