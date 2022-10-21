package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BitPayExceptionTest  extends AbstractTestException {

    @Test
    public void it_should_create_exception_without_status() {
        // given
        String message = "reasonMsg";

        // when
        BitPayException testedClass = this.getTestedClass(null, message);

        // then
        Assertions.assertEquals("reasonMsg", testedClass.getReasonPhrase());
        Assertions.assertEquals(
            "Status: 000000 -> Reason: BITPAY-GENERIC: Unexpected Bitpay exeption. -> reasonMsg",
            testedClass.getMessage());
        Assertions.assertNull(testedClass.getStatusCode());
    }

    @Test
    public void it_should_create_exception_with_status() {
        // given
        String message = "reasonMsg";
        String status = "123456";

        // when
        BitPayException testedClass = this.getTestedClass(status, message);

        // then
        Assertions.assertEquals(
            "reasonMsg",
            testedClass.getReasonPhrase());
        Assertions.assertEquals(
            "Status: 123456 -> Reason: BITPAY-GENERIC: Unexpected Bitpay exeption. -> reasonMsg",
            testedClass.getMessage());
        Assertions.assertSame("123456", testedClass.getStatusCode());
    }

    private BitPayException getTestedClass(String status, String message) {
        return new BitPayException(status, message);
    }
}
