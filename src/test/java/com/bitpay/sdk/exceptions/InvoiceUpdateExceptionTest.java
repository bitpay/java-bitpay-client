package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Test;

public class InvoiceUpdateExceptionTest extends AbstractTestException {

    @Test
    public void it_should_create_exception_without_status() {
        this.testExceptionWithoutStatus(
            this.getTestedClass(null),
            "Failed to update invoice",
            "BITPAY-INVOICE-UPDATE"
        );
    }

    @Test
    public void it_should_create_exception_with_status() {
        this.testExceptionWithStatus(
            this.getTestedClass(AbstractTestException.STATUS_CODE),
            "Failed to update invoice",
            "BITPAY-INVOICE-UPDATE"
        );
    }

    private InvoiceUpdateException getTestedClass(String status) {
        return new InvoiceUpdateException(status, AbstractTestException.REASON_MESSAGE);
    }
}