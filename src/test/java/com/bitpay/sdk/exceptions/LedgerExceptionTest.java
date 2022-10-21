package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LedgerExceptionTest extends AbstractTestException {

    @Test
    public void it_should_create_exception_without_status() {
        this.testExceptionWithoutStatus(
            this.getTestedClass(null),
            "An unexpected error occurred while trying to manage the ledger",
            "BITPAY-LEDGER-GENERIC"
        );
    }

    @Test
    public void it_should_create_exception_with_status() {
        this.testExceptionWithStatus(
            this.getTestedClass(AbstractTestException.STATUS_CODE),
            "An unexpected error occurred while trying to manage the ledger",
            "BITPAY-LEDGER-GENERIC"
        );
    }

    @Test
    public void it_should_build_exception_with_different_bitpay_message() {
        final String message = "BITPAY-CUSTOM";
        LedgerException exception = new LedgerException(null, message);
        Assertions.assertEquals(message, exception.getReasonPhrase());
    }

    private LedgerException getTestedClass(String status) {
        return new LedgerException(status, AbstractTestException.REASON_MESSAGE);
    }
}