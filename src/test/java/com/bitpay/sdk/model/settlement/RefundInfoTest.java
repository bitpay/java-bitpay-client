/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.settlement;

import java.util.Hashtable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RefundInfoTest {

    @Test
    public void it_should_change_supportRequest() {
        // given
        String expected = "expectedString";
        RefundInfo testedClass = this.getTestedClass();

        // when
        testedClass.setSupportRequest(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getSupportRequest());
    }

    @Test
    public void it_should_change_currency() {
        // given
        String expected = "expectedString";
        RefundInfo testedClass = this.getTestedClass();

        // when
        testedClass.setCurrency(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getCurrency());
    }

    @Test
    public void it_should_change_amounts() {
        // given
        Hashtable<String, Double> expected = new Hashtable<String, Double>();
        RefundInfo testedClass = this.getTestedClass();

        // when
        testedClass.setAmount(expected);

        // then
        Assertions.assertSame(expected, testedClass.getAmount());
    }

    private RefundInfo getTestedClass() {
        return new RefundInfo();
    }
}
