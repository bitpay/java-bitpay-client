/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.RateQueryException;
import com.bitpay.sdk.model.Rate.Rate;
import com.bitpay.sdk.model.Rate.Rates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RateClientTest extends AbstractClientTest {

    @Test
    public void it_should_get_rate() throws RateQueryException {
        // given
        this.addServerJsonResponse(
            "/rates/BCH/USD",
            "GET",
            null,
            getPreparedJsonDataFromFile("getRateResponse.json")
        );

        // when
        Rate rate = this.getTestedClass().get("BCH", "USD");

        // then
        Assertions.assertEquals(100.99, rate.getValue());
    }

    @Test
    public void it_should_get_rates() throws RateQueryException {
        // given
        this.addServerJsonResponse(
            "/rates",
            "GET",
            null,
            getPreparedJsonDataFromFile("getRatesResponse.json")
        );

        // when
        Rates rates = this.getTestedClass().getRates();

        // then
        Assertions.assertEquals(183, rates.getRates().size());
        Assertions.assertEquals(17725.64, rates.getRate("USD"));
    }

    @Test
    public void it_should_get_rates_by_base_currency() throws RateQueryException {
        // given
        this.addServerJsonResponse(
            "/rates/BTC",
            "GET",
            null,
            getPreparedJsonDataFromFile("getRatesResponse.json")
        );

        // when
        Rates rates = this.getTestedClass().getRates("BTC");

        // then
        Assertions.assertEquals(183, rates.getRates().size());
        Assertions.assertEquals(17725.64, rates.getRate("USD"));
    }

    private RateClient getTestedClass() {
        return RateClient.getInstance(this.getBitPayClient());
    }

}
