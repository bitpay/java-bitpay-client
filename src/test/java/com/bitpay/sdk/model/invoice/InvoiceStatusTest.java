/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.invoice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvoiceStatusTest {

    @Test
    public void it_should_return_new_status() {
        Assertions.assertSame("new", InvoiceStatus.New);
    }

    @Test
    public void it_should_return_paid_status() {
        Assertions.assertSame("paid", InvoiceStatus.Paid);
    }

    @Test
    public void it_should_return_confirmed_status() {
        Assertions.assertSame("confirmed", InvoiceStatus.Confirmed);
    }

    @Test
    public void it_should_return_complete_status() {
        Assertions.assertSame("complete", InvoiceStatus.Complete);
    }

    @Test
    public void it_should_return_expired_status() {
        Assertions.assertSame("expired", InvoiceStatus.Expired);
    }

    @Test
    public void it_should_return_invalid_status() {
        Assertions.assertSame("invalid", InvoiceStatus.Invalid);
    }

    @Test
    public void it_should_return_declined_status() {
        Assertions.assertSame("declined", InvoiceStatus.Declined);
    }
}
