package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvoiceQueryExceptionTest  extends AbstractTestException {

    @Test
    public void it_should_create_exception_without_status() {
        // given
        String message = "reasonMsg";

        // when
        InvoiceQueryException testedClass = this.getTestedClass(null, message);

        // then
        Assertions.assertEquals(
            "BITPAY-INVOICE-GET: Failed to retrieve invoice -> reasonMsg",
            testedClass.getReasonPhrase());
        Assertions.assertEquals(
            "Status: 000000 -> Reason: BITPAY-INVOICE-GET: Failed to retrieve invoice -> reasonMsg",
            testedClass.getMessage());
        Assertions.assertNull(testedClass.getStatusCode());
    }

    @Test
    public void it_should_create_exception_with_status() {
        // given
        String message = "reasonMsg";
        String status = "123456";

        // when
        InvoiceQueryException testedClass = this.getTestedClass(status, message);

        // then
        Assertions.assertEquals(
            "BITPAY-INVOICE-GET: Failed to retrieve invoice -> reasonMsg",
            testedClass.getReasonPhrase());
        Assertions.assertEquals(
            "Status: 123456 -> Reason: BITPAY-INVOICE-GET: Failed to retrieve invoice -> reasonMsg",
            testedClass.getMessage());
        Assertions.assertSame("123456", testedClass.getStatusCode());
    }

    private InvoiceQueryException getTestedClass(String status, String message) {
        return new InvoiceQueryException(status, message);
    }

}