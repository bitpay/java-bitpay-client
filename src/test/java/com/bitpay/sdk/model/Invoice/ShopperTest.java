/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShopperTest {

    @Test
    public void it_should_change_name() {
        // given
        String expectedName = "expectedName";
        Shopper testedClass = new Shopper();

        // when
        testedClass.setName(expectedName);

        // then
        Assertions.assertEquals(expectedName, testedClass.getName());
    }
}
