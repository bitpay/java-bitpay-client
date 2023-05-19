/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.invoice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SupportedTransactionCurrenciesTest {

    @Test
    public void it_should_manipulate_btc() {
        // given
        SupportedTransactionCurrencies testedClass = this.getTestedClass();
        SupportedTransactionCurrency expected = Mockito.mock(SupportedTransactionCurrency.class);

        // when
        testedClass.setBtc(expected);

        // then
        Assertions.assertSame(expected, testedClass.getBtc());
    }

    @Test
    public void it_should_manipulate_bch() {
        // given
        SupportedTransactionCurrencies testedClass = this.getTestedClass();
        SupportedTransactionCurrency expected = Mockito.mock(SupportedTransactionCurrency.class);

        // when
        testedClass.setBch(expected);

        // then
        Assertions.assertSame(expected, testedClass.getBch());
    }

    @Test
    public void it_should_manipulate_eth() {
        // given
        SupportedTransactionCurrencies testedClass = this.getTestedClass();
        SupportedTransactionCurrency expected = Mockito.mock(SupportedTransactionCurrency.class);

        // when
        testedClass.setEth(expected);

        // then
        Assertions.assertSame(expected, testedClass.getEth());
    }

    @Test
    public void it_should_manipulate_usdc() {
        // given
        SupportedTransactionCurrencies testedClass = this.getTestedClass();
        SupportedTransactionCurrency expected = Mockito.mock(SupportedTransactionCurrency.class);

        // when
        testedClass.setUsdc(expected);

        // then
        Assertions.assertSame(expected, testedClass.getUsdc());
    }

    @Test
    public void it_should_manipulate_gusd() {
        // given
        SupportedTransactionCurrencies testedClass = this.getTestedClass();
        SupportedTransactionCurrency expected = Mockito.mock(SupportedTransactionCurrency.class);

        // when
        testedClass.setGusd(expected);

        // then
        Assertions.assertSame(expected, testedClass.getGusd());
    }

    @Test
    public void it_should_manipulate_pax() {
        // given
        SupportedTransactionCurrencies testedClass = this.getTestedClass();
        SupportedTransactionCurrency expected = Mockito.mock(SupportedTransactionCurrency.class);

        // when
        testedClass.setPax(expected);

        // then
        Assertions.assertSame(expected, testedClass.getPax());
    }

    @Test
    public void it_should_manipulate_xrp() {
        // given
        SupportedTransactionCurrencies testedClass = this.getTestedClass();
        SupportedTransactionCurrency expected = Mockito.mock(SupportedTransactionCurrency.class);

        // when
        testedClass.setXrp(expected);

        // then
        Assertions.assertSame(expected, testedClass.getXrp());
    }

    private SupportedTransactionCurrencies getTestedClass() {
        return new SupportedTransactionCurrencies();
    }
}
