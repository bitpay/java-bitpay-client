/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.settlement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WithHoldingsTest {

    @Test
    public void it_should_change_amount() {
        // given
        Float expected = 12.34F;
        WithHoldings testedClass = this.getTestedClass();

        // when
        testedClass.setAmount(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAmount());
    }

    @Test
    public void it_should_change_code() {
        // given
        String expected = "expectedString";
        WithHoldings testedClass = this.getTestedClass();

        // when
        testedClass.setCode(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getCode());
    }

    @Test
    public void it_should_change_description() {
        // given
        String expected = "expectedString";
        WithHoldings testedClass = this.getTestedClass();

        // when
        testedClass.setDescription(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getDescription());
    }

    @Test
    public void it_should_change_notes() {
        // given
        String expected = "expectedString";
        WithHoldings testedClass = this.getTestedClass();

        // when
        testedClass.setNotes(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getNotes());
    }

    @Test
    public void it_should_change_label() {
        // given
        String expected = "expectedString";
        WithHoldings testedClass = this.getTestedClass();

        // when
        testedClass.setLabel(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getLabel());
    }

    @Test
    public void it_should_change_bankCountry() {
        // given
        String expected = "expectedString";
        WithHoldings testedClass = this.getTestedClass();

        // when
        testedClass.setBankCountry(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getBankCountry());
    }


    public WithHoldings getTestedClass() {
        return new WithHoldings();
    }
}
