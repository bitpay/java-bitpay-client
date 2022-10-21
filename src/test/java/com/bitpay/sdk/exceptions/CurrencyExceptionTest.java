package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CurrencyExceptionTest  extends AbstractTestException {

    @Test
    public void it_should_create_exception_without_status() {
        // given
        String message = "reasonMsg";

        // when
        CurrencyException testedClass = this.getTestedClass(null, message);

        // then
        Assertions.assertEquals(
            "BITPAY-CURRENCY-GENERIC: An unexpected error occurred while trying to manage the currencies -> reasonMsg",
            testedClass.getReasonPhrase());
        Assertions.assertEquals(
            "Status: 000000 -> Reason: BITPAY-CURRENCY-GENERIC: An unexpected error occurred while trying to manage the currencies -> reasonMsg",
            testedClass.getMessage());
        Assertions.assertNull(testedClass.getStatusCode());
    }

    @Test
    public void it_should_create_exception_with_status() {
        // given
        String message = "reasonMsg";
        String status = "123456";

        // when
        CurrencyException testedClass = this.getTestedClass(status, message);

        // then
        Assertions.assertEquals(
            "BITPAY-CURRENCY-GENERIC: An unexpected error occurred while trying to manage the currencies -> reasonMsg",
            testedClass.getReasonPhrase());
        Assertions.assertEquals(
            "Status: 123456 -> Reason: BITPAY-CURRENCY-GENERIC: An unexpected error occurred while trying to manage the currencies -> reasonMsg",
            testedClass.getMessage());
        Assertions.assertSame("123456", testedClass.getStatusCode());
    }

    private CurrencyException getTestedClass(String status, String message) {
        return new CurrencyException(status, message);
    }
}