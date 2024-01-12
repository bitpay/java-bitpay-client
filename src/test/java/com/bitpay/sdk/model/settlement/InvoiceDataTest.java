/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.settlement;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class InvoiceDataTest {

    @Test
    public void it_should_change_orderId() {
        // given
        String expected = "expectedString";
        InvoiceData testedClass = this.getTestedClass();

        // when
        testedClass.setOrderId(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getOrderId());
    }

    @Test
    public void it_should_change_date() {
        // given
        ZonedDateTime expected = ZonedDateTime.now();
        InvoiceData testedClass = this.getTestedClass();

        // when
        testedClass.setDate(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getDate());
    }

    @Test
    public void it_should_change_price() {
        // given
        Float expected = 12.34F;
        InvoiceData testedClass = this.getTestedClass();

        // when
        testedClass.setPrice(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getPrice());
    }

    @Test
    public void it_should_change_currency() {
        // given
        String expected = "expectedString";
        InvoiceData testedClass = this.getTestedClass();

        // when
        testedClass.setCurrency(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getCurrency());
    }

    @Test
    public void it_should_change_transactionCurrency() {
        // given
        String expected = "expectedString";
        InvoiceData testedClass = this.getTestedClass();

        // when
        testedClass.setTransactionCurrency(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getTransactionCurrency());
    }

    @Test
    public void it_should_change_overPaidAmount() {
        // given
        Float expected = 12.34F;
        InvoiceData testedClass = this.getTestedClass();

        // when
        testedClass.setOverpaidAmount(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getOverpaidAmount());
    }

    @Test
    public void it_should_change_payoutPercentage() {
        // given
        Map<String, Double> expected = new HashMap<String, Double>();
        InvoiceData testedClass = this.getTestedClass();

        // when
        testedClass.setPayoutPercentage(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getPayoutPercentage());
    }

    @Test
    public void it_should_change_refundInfo() {
        // given
        RefundInfo expected = Mockito.mock(RefundInfo.class);
        InvoiceData testedClass = this.getTestedClass();

        // when
        testedClass.setRefundInfo(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getRefundInfo());
    }

    private InvoiceData getTestedClass() {
        return new InvoiceData();
    }
}
