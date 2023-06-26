/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.payout;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PayoutInstructionTransactionTest {

    @Test
    public void it_should_change_txid() {
        // given
        String expected = "expectedString";
        PayoutTransaction testedClass = this.getTestedClass();

        // when
        testedClass.setTxid(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getTxid());
    }

    @Test
    public void it_should_change_amount() {
        // given
        Double expected = 12.34;
        PayoutTransaction testedClass = this.getTestedClass();

        // when
        testedClass.setAmount(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAmount());
    }

    @Test
    public void it_should_change_date() {
        // given
        Long expected = 12L;
        PayoutTransaction testedClass = this.getTestedClass();

        // when
        testedClass.setDate(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getDate());
    }


    private PayoutTransaction getTestedClass() {
        return new PayoutTransaction();
    }
}
