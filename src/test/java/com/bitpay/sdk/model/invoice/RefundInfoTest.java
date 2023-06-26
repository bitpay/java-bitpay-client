/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.invoice;

import com.bitpay.sdk.exceptions.BitPayException;
import java.util.Hashtable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RefundInfoTest {

    @Test
    public void it_should_manipulate_supportRequest() {
        // given
        String expected = "expectedString";
        RefundInfo testedClass = this.getTestedClass();

        // when
        testedClass.setSupportRequest(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getSupportRequest());
    }

    @Test
    public void it_should_manipulate_currency() throws BitPayException {
        // given
        String expected = "BTC";
        RefundInfo testedClass = this.getTestedClass();

        // when
        testedClass.setCurrency(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getCurrency());
    }

    @Test
    public void it_should_not_allow_to_set_invalid_currency() {
        Assertions.assertThrows(BitPayException.class, () -> {
            final String invalidCurrency = "INVALID_CURRENCY";
            RefundInfo testedClass = this.getTestedClass();

            testedClass.setCurrency(invalidCurrency);
        });
    }

    @Test
    public void it_should_manipulate_amounts() {
        // given
        Hashtable<String, Double> expected = new Hashtable<String, Double>();
        expected.put("someKey", 12.45);
        RefundInfo testedClass = this.getTestedClass();

        // when
        testedClass.setAmounts(expected);

        // then
        Assertions.assertSame(expected, testedClass.getAmounts());
    }

    private RefundInfo getTestedClass() {
        return new RefundInfo();
    }
}
