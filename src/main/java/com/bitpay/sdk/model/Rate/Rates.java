package com.bitpay.sdk.model.Rate;

import com.bitpay.sdk.Client;
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

    private final Client _bp;
    private List<Rate> _rates;

    /**
     * Instantiates a new Rates.
     *
     * @param rates the rates
     * @param bp    the bp
     */
    public Rates(List<Rate> rates, Client bp) {
        _bp = bp;
        _rates = rates;
    }

    /**
     * Gets rates.
     *
     * @return the rates
     */
    public List<Rate> getRates() {
        return _rates;
    }

    /**
     * Update rates.
     *
     * @throws RateQueryException the rate query exception
     */
    public void update() throws RateQueryException {
        try {
            _rates = _bp.getRates().getRates();
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
        for (Rate rateObj : _rates) {
            if (rateObj.getCode().equals(currencyCode)) {
                val = rateObj.getValue();
                break;
            }
        }
        return val;
    }
}
