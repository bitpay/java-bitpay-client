/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RefundWebhookTest {

    @Test
    public void it_should_manipulate_id() {
        // given
        String expected = "expectedString";
        RefundWebhook testedClass = this.getTestedClass();

        // when
        testedClass.setId(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getId());
    }

    @Test
    public void it_should_manipulate_invoice() {
        // given
        String expected = "expectedString";
        RefundWebhook testedClass = this.getTestedClass();

        // when
        testedClass.setInvoice(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getInvoice());
    }

    @Test
    public void it_should_manipulate_supportRequest() {
        // given
        String expected = "expectedString";
        RefundWebhook testedClass = this.getTestedClass();

        // when
        testedClass.setSupportRequest(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getSupportRequest());
    }

    @Test
    public void it_should_manipulate_status() {
        // given
        String expected = "expectedString";
        RefundWebhook testedClass = this.getTestedClass();

        // when
        testedClass.setStatus(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getStatus());
    }

    @Test
    public void it_should_manipulate_amount() {
        // given
        Double expected = 12.34;
        RefundWebhook testedClass = this.getTestedClass();

        // when
        testedClass.setAmount(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAmount());
    }

    @Test
    public void it_should_manipulate_currency() {
        // given
        String expected = "expectedString";
        RefundWebhook testedClass = this.getTestedClass();

        // when
        testedClass.setCurrency(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getCurrency());
    }

    @Test
    public void it_should_manipulate_lastRefundNotification() {
        // given
        Date expected = new Date();
        RefundWebhook testedClass = this.getTestedClass();

        // when
        testedClass.setLastRefundNotification(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getLastRefundNotification());
    }

    @Test
    public void it_should_manipulate_refundFee() {
        // given
        Double expected = 12.34;
        RefundWebhook testedClass = this.getTestedClass();

        // when
        testedClass.setRefundFee(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getRefundFee());
    }

    @Test
    public void it_should_manipulate_immediate() {
        // given
        boolean expected = true;
        RefundWebhook testedClass = this.getTestedClass();

        // when
        testedClass.setImmediate(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getImmediate());
    }

    @Test
    public void it_should_manipulate_buyerPaysRefundFee() {
        // given
        boolean expected = true;
        RefundWebhook testedClass = this.getTestedClass();

        // when
        testedClass.setBuyerPaysRefundFee(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getBuyerPaysRefundFee());
    }

    @Test
    public void it_should_manipulate_requestDate() {
        // given
        Date expected = new Date();
        RefundWebhook testedClass = this.getTestedClass();

        // when
        testedClass.setRequestDate(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getRequestDate());
    }

    private RefundWebhook getTestedClass() {
        return new RefundWebhook();
    }
}
