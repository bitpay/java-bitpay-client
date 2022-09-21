/*
 * Copyright 2020 MarketNation, Inc.
 * All rights reserved.
 */

package com.bitpay.sdk.model.Rate;

import com.bitpay.sdk.Client;
import com.bitpay.sdk.exceptions.RateQueryException;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RatesTest {

    @Mock
    private Client client;

    @Mock
    private Rate rate1;

    @Mock
    private Rate rate2;

    @Test
    public void it_should_return_rates() {
        // given
        List<Rate> rates = List.of(rate1);
        Rates testedClass = getTestedClass(rates);

        // when
        List<Rate> result = testedClass.getRates();

        // then
        Assert.assertEquals(rates, result);
    }

    @Test
    public void it_should_get_specific_rate() {
        // given
        final String btc = "BTC";
        final double btcValue = 1.0;

        Mockito.when(rate1.getCode()).thenReturn("ETH");
        Mockito.when(rate2.getCode()).thenReturn(btc);
        Mockito.when(rate2.getValue()).thenReturn(btcValue);

        List<Rate> rates = List.of(rate1, rate2);
        Rates testedClass = getTestedClass(rates);

        // when
        final double result = testedClass.getRate(btc);

        // then
        Assert.assertEquals(btcValue, result, 0);
    }

    @Test
    public void it_should_update_rates() throws RateQueryException {
        // given
        List<Rate> rates = List.of(rate1, rate2);
        List<Rate> expectedRates = List.of(rate1);
        Rates expectedRatesObject = new Rates(expectedRates, this.client);
        Rates testedClass = this.getTestedClass(rates);
        Mockito.when(this.client.getRates()).thenReturn(expectedRatesObject);

        // when
        testedClass.update();

        // then
        Assert.assertSame(expectedRates, testedClass.getRates());

    }

    private Rates getTestedClass(List<Rate> rates) {
        return new Rates(rates, this.client);
    }
}
