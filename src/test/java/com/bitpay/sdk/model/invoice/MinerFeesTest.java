/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.invoice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MinerFeesTest {

    @Test
    public void it_should_manipulate_btc() {
        // given
        MinerFees testedClass = this.getTestedClass();
        MinerFeesItem expected = Mockito.mock(MinerFeesItem.class);

        // when
        testedClass.setBtc(expected);

        // then
        Assertions.assertSame(expected, testedClass.getBtc());
    }

    @Test
    public void it_should_manipulate_bch() {
        // given
        MinerFees testedClass = this.getTestedClass();
        MinerFeesItem expected = Mockito.mock(MinerFeesItem.class);

        // when
        testedClass.setBch(expected);

        // then
        Assertions.assertSame(expected, testedClass.getBch());
    }

    @Test
    public void it_should_manipulate_eth() {
        // given
        MinerFees testedClass = this.getTestedClass();
        MinerFeesItem expected = Mockito.mock(MinerFeesItem.class);

        // when
        testedClass.setEth(expected);

        // then
        Assertions.assertSame(expected, testedClass.getEth());
    }

    @Test
    public void it_should_manipulate_usdc() {
        // given
        MinerFees testedClass = this.getTestedClass();
        MinerFeesItem expected = Mockito.mock(MinerFeesItem.class);

        // when
        testedClass.setUsdc(expected);

        // then
        Assertions.assertSame(expected, testedClass.getUsdc());
    }

    @Test
    public void it_should_manipulate_gusd() {
        // given
        MinerFees testedClass = this.getTestedClass();
        MinerFeesItem expected = Mockito.mock(MinerFeesItem.class);

        // when
        testedClass.setGusd(expected);

        // then
        Assertions.assertSame(expected, testedClass.getGusd());
    }

    @Test
    public void it_should_manipulate_pax() {
        // given
        MinerFees testedClass = this.getTestedClass();
        MinerFeesItem expected = Mockito.mock(MinerFeesItem.class);

        // when
        testedClass.setPax(expected);

        // then
        Assertions.assertSame(expected, testedClass.getPax());
    }

    @Test
    public void it_should_manipulate_xrp() {
        // given
        MinerFees testedClass = this.getTestedClass();
        MinerFeesItem expected = Mockito.mock(MinerFeesItem.class);

        // when
        testedClass.setXrp(expected);

        // then
        Assertions.assertSame(expected, testedClass.getXrp());
    }

    @Test
    public void it_should_manipulate_matic() {
        // given
        MinerFees testedClass = this.getTestedClass();
        MinerFeesItem expected = Mockito.mock(MinerFeesItem.class);

        // when
        testedClass.setMatic(expected);

        // then
            Assertions.assertSame(expected, testedClass.getMatic());
    }

    @Test
    public void it_should_manipulate_usdcm() {
        // given
        MinerFees testedClass = this.getTestedClass();
        MinerFeesItem expected = Mockito.mock(MinerFeesItem.class);

        // when
        testedClass.setUsdcM(expected);

        // then
        Assertions.assertSame(expected, testedClass.getUsdcM());
    }

    private MinerFees getTestedClass() {
        return new MinerFees();
    }
}
