package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvoiceCreationExceptionTest  extends AbstractTestException {

    @Test
    public void it_should_create_exception_without_status() {
        // given
        String message = "reasonMsg";

        // when
        InvoiceCreationException testedClass = this.getTestedClass(null, message);

        // then
        Assertions.assertEquals(
            "BITPAY-INVOICE-CREATE: Failed to create invoice -> reasonMsg",
            testedClass.getReasonPhrase());
        Assertions.assertEquals(
            "Status: 000000 -> Reason: BITPAY-INVOICE-CREATE: Failed to create invoice -> reasonMsg",
            testedClass.getMessage());
        Assertions.assertNull(testedClass.getStatusCode());
    }

    @Test
    public void it_should_create_exception_with_status() {
        // given
        String message = "reasonMsg";
        String status = "123456";

        // when
        InvoiceCreationException testedClass = this.getTestedClass(status, message);

        // then
        Assertions.assertEquals(
            "BITPAY-INVOICE-CREATE: Failed to create invoice -> reasonMsg",
            testedClass.getReasonPhrase());
        Assertions.assertEquals(
            "Status: 123456 -> Reason: BITPAY-INVOICE-CREATE: Failed to create invoice -> reasonMsg",
            testedClass.getMessage());
        Assertions.assertSame("123456", testedClass.getStatusCode());
    }

    private InvoiceCreationException getTestedClass(String status, String message) {
        return new InvoiceCreationException(status, message);
    }
}