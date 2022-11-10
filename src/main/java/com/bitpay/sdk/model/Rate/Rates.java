package com.bitpay.sdk.model.Rate;

import com.bitpay.sdk.client.RateClient;
import com.bitpay.sdk.exceptions.RateQueryException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Rates are exchange rates, representing the number of fiat currency units equivalent to one BTC.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-rates">REST API Rates</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rates {

    private List<Rate> rates;

    /**
     * Instantiates a new Rates.
     *
     * @param rates the rates
     * @param rateClient    the rateClient
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
     * @throws RateQueryException the rate query exception
     */
    public void update(RateClient rateClient) throws RateQueryException {
        try {
            this.rates = rateClient.getRates().getRates();
        } catch (Exception e) {
            throw new RateQueryException(null, e.getMessage());
        }
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
