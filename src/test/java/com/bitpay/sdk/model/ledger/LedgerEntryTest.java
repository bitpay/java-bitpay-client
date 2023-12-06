/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.ledger;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LedgerEntryTest {

    @Test
    public void it_should_change_type() {
        // given
        String expected = "expectedString";
        LedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setType(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getType());
    }

    @Test
    public void it_should_change_amount() {
        // given
        String expected = "expectedString";
        LedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setAmount(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAmount());
    }

    @Test
    public void it_should_change_code() {
        // given
        Integer expected = 123;
        LedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setCode(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getCode());
    }

    @Test
    public void it_should_change_description() {
        // given
        String expected = "expectedString";
        LedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setDescription(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getDescription());
    }

    @Test
    public void it_should_change_timestamp() {
        // given
        ZonedDateTime expected = ZonedDateTime.now();
        LedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setTimestamp(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getTimestamp());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void it_should_change_txType() {
        // given
        String expected = "expectedString";
        LedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setTxType(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getTxType());
    }

    @Test
    public void it_should_change_scale() {
        // given
        Integer expected = 13;
        LedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setScale(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getScale());
    }

    @Test
    public void it_should_change_invoiceId() {
        // given
        String expected = "expectedString";
        LedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setInvoiceId(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getInvoiceId());
    }

    @Test
    public void it_should_change_buyer() {
        // given
        Buyer expected = Mockito.mock(Buyer.class);
        LedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setBuyer(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getBuyer());
    }

    @Test
    public void it_should_change_invoiceAmount() {
        // given
        Double expected = 65.34;
        LedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setInvoiceAmount(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getInvoiceAmount());
    }

    @Test
    public void it_should_change_invoiceCurrency() {
        // given
        String expected = "expectedString";
        LedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setInvoiceCurrency(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getInvoiceCurrency());
    }

    @Test
    public void it_should_change_transactionCurrency() {
        // given
        String expected = "expectedString";
        LedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setTransactionCurrency(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getTransactionCurrency());
    }

    @Test
    public void it_should_change_id() {
        // given
        String expected = "expectedString";
        LedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setId(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getId());
    }

    @Test
    public void it_should_change_support_request() {
        // given
        String expected = "expectedString";
        LedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setSupportRequest(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getSupportRequest());
    }

    private LedgerEntry getTestedClass() {
        return new LedgerEntry();
    }
}
