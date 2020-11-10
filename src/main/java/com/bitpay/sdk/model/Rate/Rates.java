package com.bitpay.sdk.model.Rate;

import com.bitpay.sdk.Client;
import com.bitpay.sdk.exceptions.RateQueryException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rates {

    private final Client _bp;
    private List<Rate> _rates;

    public Rates(List<Rate> rates, Client bp) {
        _bp = bp;
        _rates = rates;
    }

    public List<Rate> getRates() {
        return _rates;
    }

    public void update() throws RateQueryException {
        try {
            _rates = _bp.getRates().getRates();
        } catch (Exception e) {
            throw new RateQueryException(e.getMessage());
        }
    }

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
