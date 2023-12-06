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

    @Test
    public void it_should_manipulate_euroc() {
        // given
        SupportedTransactionCurrencies testedClass = new SupportedTransactionCurrencies();
        SupportedTransactionCurrency expected = Mockito.mock(SupportedTransactionCurrency.class);

        testedClass.setEuroc(expected);

        Assertions.assertSame(expected, testedClass.getEuroc());
    }

    @Test
    public void it_should_manipulate_matic() {
        // given
        SupportedTransactionCurrencies testedClass = new SupportedTransactionCurrencies();
        SupportedTransactionCurrency expected = Mockito.mock(SupportedTransactionCurrency.class);

        testedClass.setMatic(expected);

        Assertions.assertSame(expected, testedClass.getMatic());
    }

    @Test
    public void it_should_manipulate_maticE() {
        // given
        SupportedTransactionCurrencies testedClass = new SupportedTransactionCurrencies();
        SupportedTransactionCurrency expected = Mockito.mock(SupportedTransactionCurrency.class);

        testedClass.setMaticE(expected);

        Assertions.assertSame(expected, testedClass.getMaticE());
    }

    @Test
    public void it_should_manipulate_ethM() {
        // given
        SupportedTransactionCurrencies testedClass = new SupportedTransactionCurrencies();
        SupportedTransactionCurrency expected = Mockito.mock(SupportedTransactionCurrency.class);

        testedClass.setEthM(expected);

        Assertions.assertSame(expected, testedClass.getEthM());
    }

    @Test
    public void it_should_manipulate_usdcM() {
        // given
        SupportedTransactionCurrencies testedClass = new SupportedTransactionCurrencies();
        SupportedTransactionCurrency expected = Mockito.mock(SupportedTransactionCurrency.class);

        testedClass.setUsdcM(expected);

        Assertions.assertSame(expected, testedClass.getUsdcM());
    }

    @Test
    public void it_should_manipulate_busdM() {
        // given
        SupportedTransactionCurrencies testedClass = new SupportedTransactionCurrencies();
        SupportedTransactionCurrency expected = Mockito.mock(SupportedTransactionCurrency.class);

        testedClass.setBusdM(expected);

        Assertions.assertSame(expected, testedClass.getBusdM());
    }

    @Test
    public void it_should_manipulate_daiM() {
        // given
        SupportedTransactionCurrencies testedClass = new SupportedTransactionCurrencies();
        SupportedTransactionCurrency expected = Mockito.mock(SupportedTransactionCurrency.class);

        testedClass.setDaiM(expected);

        Assertions.assertSame(expected, testedClass.getDaiM());
    }

    @Test
    public void it_should_manipulate_wbtcM() {
        // given
        SupportedTransactionCurrencies testedClass = new SupportedTransactionCurrencies();
        SupportedTransactionCurrency expected = Mockito.mock(SupportedTransactionCurrency.class);

        testedClass.setWbtcM(expected);

        Assertions.assertSame(expected, testedClass.getWbtcM());
    }

    @Test
    public void it_should_manipulate_shibM() {
        // given
        SupportedTransactionCurrencies testedClass = new SupportedTransactionCurrencies();
        SupportedTransactionCurrency expected = Mockito.mock(SupportedTransactionCurrency.class);

        testedClass.setShibM(expected);

        Assertions.assertSame(expected, testedClass.getShibM());
    }

    private SupportedTransactionCurrencies getTestedClass() {
        return new SupportedTransactionCurrencies();
    }
}
