/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.wallet;

import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class WalletTest {

    @Test
    public void it_should_change_key() {
        // given
        String expected = "expectedString";
        Wallet testedClass = this.getTestedClass();

        // when
        testedClass.setKey(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getKey());
    }

    @Test
    public void it_should_change_displayName() {
        // given
        String expected = "expectedString";
        Wallet testedClass = this.getTestedClass();

        // when
        testedClass.setDisplayName(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getDisplayName());
    }

    @Test
    public void it_should_change_avatar() {
        // given
        String expected = "expectedString";
        Wallet testedClass = this.getTestedClass();

        // when
        testedClass.setAvatar(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAvatar());
    }

    @Test
    public void it_should_change_payPro() {
        // given
        Boolean expected = true;
        Wallet testedClass = this.getTestedClass();

        // when
        testedClass.setPayPro(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getPayPro());
    }

    @Test
    public void it_should_change_currencies() {
        // given
        ArrayList<Currencies> expected = new ArrayList<Currencies>();
        expected.add(Mockito.mock(Currencies.class));
        Wallet testedClass = this.getTestedClass();

        // when
        testedClass.setCurrencies(expected);

        // then
        Assertions.assertSame(expected, testedClass.getCurrencies());
    }

    @Test
    public void it_should_change_image() {
        // given
        String expected = "expectedString";
        Wallet testedClass = this.getTestedClass();

        // when
        testedClass.setImage(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getImage());
    }

    private Wallet getTestedClass() {
        return new Wallet();
    }
}
