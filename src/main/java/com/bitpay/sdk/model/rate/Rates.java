/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.rate;

import com.bitpay.sdk.client.RateClient;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Rates are exchange rates, representing the number of fiat currency units equivalent to one BTC.
 *
 * @see <a href="https://bitpay.readme.io/reference/rates">REST API Rates</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rates {

    protected List<Rate> rates;

    /**
     * Instantiates a new Rates.
     *
     * @param rates the rates
     */
    public Rates(List<Rate> rates) {
        this.rates = rates;
    }

    /**
     * Gets rates.
     *
     * @return the rates
     */
    public List<Rate> getRates() {
        return this.rates;
    }

    /**
     * Update rates.
     *
     * @param rateClient the rate client
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     */
    public void update(RateClient rateClient) throws BitPayGenericException, BitPayApiException {
        this.rates = rateClient.getRates().getRates();
    }

    /**
     * Gets rate for the requested baseCurrency /currency pair.
     *
     * @param currencyCode the currency code
     * @return the rate
     */
    public double getRate(String currencyCode) {
        double val = 0;
        for (Rate rateObj : this.rates) {
            if (rateObj.getCode().equals(currencyCode)) {
                val = rateObj.getValue();
                break;
            }
        }
        return val;
    }
}
