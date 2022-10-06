/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import java.math.BigDecimal;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvoiceTransactionTest {

    @Test
    public void it_should_manipulate_amount() {
        // given
        BigDecimal expected = BigDecimal.TEN;
        InvoiceTransaction testedClass = this.getTestedClass();

        // when
        testedClass.setAmount(expected);

        // then
        Assertions.assertSame(expected, testedClass.getAmount());
    }

    @Test
    public void it_should_manipulate_confirmations() {
        // given
        int expected = 3;
        InvoiceTransaction testedClass = this.getTestedClass();

        // when
        testedClass.setConfirmations(expected);

        // then
        Assertions.assertSame(expected, testedClass.getConfirmations());
    }

    @Test
    public void it_should_manipulate_time() {
        // given
        Date expected = new Date();
        InvoiceTransaction testedClass = this.getTestedClass();

        // when
        testedClass.setTime(expected);

        // then
        Assertions.assertSame(expected, testedClass.getTime());
    }

    @Test
    public void it_should_manipulate_receivedTime() {
        // given
        Date expected = new Date();
        InvoiceTransaction testedClass = this.getTestedClass();

        // when
        testedClass.setReceivedTime(expected);

        // then
        Assertions.assertSame(expected, testedClass.getReceivedTime());
    }

    @Test
    public void it_should_manipulate_txid() {
        // given
        String expected = "SomeString";
        InvoiceTransaction testedClass = this.getTestedClass();

        // when
        testedClass.setTransactionId(expected);

        // then
        Assertions.assertSame(expected, testedClass.getTransactionId());
    }


    private InvoiceTransaction getTestedClass() {
        return new InvoiceTransaction();
    }
}
