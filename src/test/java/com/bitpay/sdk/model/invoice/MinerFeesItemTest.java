/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.invoice;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MinerFeesItemTest {

    @Test
    public void it_should_manipulate_satoshisPerByte() {
        // given
        Integer expected = 765;
        MinerFeesItem testedClass = this.getTestedClass();

        // when
        testedClass.setSatoshisPerByte(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getSatoshisPerByte());
    }

    @Test
    public void it_should_manipulate_totalFee() {
        // given
        Integer expected = 654;
        MinerFeesItem testedClass = this.getTestedClass();

        // when
        testedClass.setTotalFee(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getTotalFee());
    }

    @Test
    public void it_should_manipulate_fiatAmount() {
        // given
        double expected = 21.23;
        MinerFeesItem testedClass = this.getTestedClass();

        // when
        testedClass.setFiatAmount(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getFiatAmount());
    }

    private MinerFeesItem getTestedClass() {
        return new MinerFeesItem();
    }
}
