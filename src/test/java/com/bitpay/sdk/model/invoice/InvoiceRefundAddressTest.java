/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.invoice;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvoiceRefundAddressTest {

    @Test
    public void it_should_manipulate_type() {
        String expected = "exampleType";
        InvoiceRefundAddress testedClass = this.getTestedClass();

        testedClass.setType(expected);
        Assertions.assertSame(expected, testedClass.getType());
    }

    @Test
    public void it_should_manipulate_date() {
        ZonedDateTime expected = ZonedDateTime.now();
        InvoiceRefundAddress testedClass = this.getTestedClass();

        testedClass.setDate(expected);
        Assertions.assertSame(expected, testedClass.getDate());
    }

    @Test
    public void it_should_manipulate_tag() {
        Integer expected = 123;
        InvoiceRefundAddress testedClass = this.getTestedClass();

        testedClass.setTag(expected);
        Assertions.assertSame(expected, testedClass.getTag());
    }

    @Test
    public void it_should_manipulate_email() {
        String expected = "example@email.com";
        InvoiceRefundAddress testedClass = this.getTestedClass();

        testedClass.setEmail(expected);
        Assertions.assertSame(expected, testedClass.getEmail());
    }

    private InvoiceRefundAddress getTestedClass() {
        return new InvoiceRefundAddress();
    }
}
