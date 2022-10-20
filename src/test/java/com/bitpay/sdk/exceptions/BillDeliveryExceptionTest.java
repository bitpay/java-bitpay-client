package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BillDeliveryExceptionTest  extends AbstractTestException {

    @Test
    public void it_should_create_exception_without_status() {
        // given
        String message = "reasonMsg";

        // when
        BillDeliveryException testedClass = this.getTestedClass(null, message);

        // then
        Assertions
            .assertEquals("BITPAY-BILL-DELIVERY: Failed to deliver bill -> reasonMsg", testedClass.getReasonPhrase());
        Assertions.assertEquals("Status: 000000 -> Reason: BITPAY-BILL-DELIVERY: Failed to deliver bill -> reasonMsg",
            testedClass.getMessage());
        Assertions.assertNull(testedClass.getStatusCode());
    }

    @Test
    public void it_should_create_exception_with_status() {
        // given
        String message = "reasonMsg";
        String status = "123456";

        // when
        BillDeliveryException testedClass = this.getTestedClass(status, message);

        // then
        Assertions
            .assertEquals("BITPAY-BILL-DELIVERY: Failed to deliver bill -> reasonMsg", testedClass.getReasonPhrase());
        Assertions.assertEquals("Status: 123456 -> Reason: BITPAY-BILL-DELIVERY: Failed to deliver bill -> reasonMsg",
            testedClass.getMessage());
        Assertions.assertSame("123456", testedClass.getStatusCode());
    }

    private BillDeliveryException getTestedClass(String status, String message) {
        return new BillDeliveryException(status, message);
    }
}