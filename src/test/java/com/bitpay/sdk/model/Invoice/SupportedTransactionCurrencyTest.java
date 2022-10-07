/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SupportedTransactionCurrencyTest {

    @Test
    public void it_should_change_enabled() {
        // given
        boolean expected = true;
        SupportedTransactionCurrency testedClass = this.getTestedClass();

        // when
        testedClass.setEnabled(expected);

        // then
        Assertions.assertSame(expected, testedClass.getEnabled());
    }

    @Test
    public void it_should_change_reason() {
        // given
        String expected = "expectedString";
        SupportedTransactionCurrency testedClass = this.getTestedClass();

        // when
        testedClass.setReason(expected);

        // then
        Assertions.assertSame(expected, testedClass.getReason());
    }

    private SupportedTransactionCurrency getTestedClass() {
        return new SupportedTransactionCurrency();
    }
}
