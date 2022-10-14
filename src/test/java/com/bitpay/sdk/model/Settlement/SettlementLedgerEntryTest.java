/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Settlement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SettlementLedgerEntryTest {

    @Test
    public void it_should_change_code() {
        // given
        Integer expected = 12;
        SettlementLedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setCode(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getCode());
    }

    @Test
    public void it_should_change_invoiceId() {
        // given
        String expected = "expectedString";
        SettlementLedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setInvoiceId(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getInvoiceId());
    }

    @Test
    public void it_should_change_amount() {
        // given
        Float expected = 12.34F;
        SettlementLedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setAmount(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAmount());
    }

    @Test
    public void it_should_change_timestamp() {
        // given
        Long expected = 12L;
        SettlementLedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setTimestamp(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getTimestamp());
    }

    @Test
    public void it_should_change_description() {
        // given
        String expected = "expectedString";
        SettlementLedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setDescription(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getDescription());
    }

    @Test
    public void it_should_change_reference() {
        // given
        String expected = "expectedString";
        SettlementLedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setReference(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getReference());
    }

    @Test
    public void it_should_change_invoiceData() {
        // given
        InvoiceData expected = Mockito.mock(InvoiceData.class);
        SettlementLedgerEntry testedClass = this.getTestedClass();

        // when
        testedClass.setInvoiceData(expected);

        // then
        Assertions.assertSame(expected, testedClass.getInvoiceData());
    }

    private SettlementLedgerEntry getTestedClass() {
        return new SettlementLedgerEntry();
    }
}
