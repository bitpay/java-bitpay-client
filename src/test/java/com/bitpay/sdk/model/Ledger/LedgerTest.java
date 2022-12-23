/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Ledger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LedgerTest {

    @Test
    public void it_should_change_currency() {
        // given
        String expected = "expectedString";
        Ledger testedClass = this.getTestedClass();

        // when
        testedClass.setCurrency(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getCurrency());
    }

    @Test
    public void it_should_change_balance() {
        // given
        Double expected = 12.34;
        Ledger testedClass = this.getTestedClass();

        // when
        testedClass.setBalance(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getBalance());
    }

    private Ledger getTestedClass() {
        return new Ledger();
    }
}
