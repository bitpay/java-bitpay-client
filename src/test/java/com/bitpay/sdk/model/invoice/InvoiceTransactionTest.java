/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.invoice;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    @Test
    public void it_should_manipulate_ex_rates() {
        // given
        Map<String, BigDecimal> expected = new HashMap<>();
        expected.put("BTC", BigDecimal.valueOf(0.000234234));
        InvoiceTransaction testedClass = this.getTestedClass();

        // when
        testedClass.setExRates(expected);

        // then
        Assertions.assertSame(expected, testedClass.getExRates());
    }

    @Test
    public void it_should_manipulate_output_index() {
        // given
        Integer expected = 1;
        InvoiceTransaction testedClass = this.getTestedClass();

        // when
        testedClass.setOutputIndex(expected);

        // then
        Assertions.assertSame(expected, testedClass.getOutputIndex());
    }

    private InvoiceTransaction getTestedClass() {
        return new InvoiceTransaction();
    }
}
