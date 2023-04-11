/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import java.math.BigDecimal;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RefundTest {

    @Test
    public void it_should_manipulate_guid() {
        // given
        String expected = "expectedString";
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setGuid(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getGuid());
    }

    @Test
    public void it_should_manipulate_amount() {
        // given
        Double expected = 12.34;
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setAmount(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAmount());
    }

    @Test
    public void it_should_manipulate_currency() {
        // given
        String expected = "USD";
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setCurrency(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getCurrency());
    }

    @Test
    public void it_should_manipulate_invoice() {
        // given
        String expected = "expectedString";
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setInvoice(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getInvoice());
    }

    @Test
    public void it_should_manipulate_preview() {
        // given
        Boolean expected = true;
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setPreview(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getPreview());
    }

    @Test
    public void it_should_manipulate_immediate() {
        // given
        Boolean expected = true;
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setImmediate(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getImmediate());
    }

    @Test
    public void it_should_manipulate_buyerPaysRefundFee() {
        // given
        Boolean expected = true;
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setBuyerPaysRefundFee(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getBuyerPaysRefundFee());
    }

    @Test
    public void it_should_manipulate_reference() {
        // given
        String expected = "expectedString";
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setReference(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getReference());
    }

    @Test
    public void it_should_manipulate_refundFee() {
        // given
        Double expected = 12.34;
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setRefundFee(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getRefundFee());
    }

    @Test
    public void it_should_manipulate_lastRefundNotification() {
        // given
        Date expected = new Date();
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setLastRefundNotification(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getLastRefundNotification());
    }

    @Test
    public void it_should_manipulate_transactionAmount() {
        // given
        BigDecimal expected = BigDecimal.TEN;
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setTransactionAmount(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getTransactionAmount());
    }

    @Test
    public void it_should_manipulate_transactionRefundFee() {
        // given
        BigDecimal expected = BigDecimal.TEN;
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setTransactionRefundFee(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getTransactionRefundFee());
    }

    @Test
    public void it_should_manipulate_transactionCurrency() {
        // given
        String expected = "SomeCurrency";
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setTransactionCurrency(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getTransactionCurrency());
    }

    @Test
    public void it_should_manipulate_id() {
        // given
        String expected = "expectedString";
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setId(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getId());
    }

    @Test
    public void it_should_manipulate_requestDate() {
        // given
        Date expected = new Date();
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setRequestDate(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getRequestDate());
    }

    @Test
    public void it_should_manipulate_status() {
        // given
        String expected = "expectedString";
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setStatus(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getStatus());
    }

    @Test
    public void it_should_manipulate_notification_url() {
        // given
        String expected = "expectedString";
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setNotificationUrl(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getNotificationUrl());
    }

    @Test
    public void it_should_manipulate_refund_address() {
        // given
        String expected = "expectedString";
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setRefundAddress(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getRefundAddress());
    }

    @Test
    public void it_should_manipulate_support_request() {
        // given
        String expected = "expectedString";
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setSupportRequest(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getSupportRequest());
    }

    @Test
    public void it_should_manipulate_tx_id() {
        // given
        String expected = "expectedString";
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setTxid(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getTxid());
    }

    @Test
    public void it_should_manipulate_type() {
        // given
        String expected = "expectedString";
        Refund testedClass = this.getTestedClass();

        // when
        testedClass.setType(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getType());
    }

    private Refund getTestedClass() {
        return new Refund();
    }
}
