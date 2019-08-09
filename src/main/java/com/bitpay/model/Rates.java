package com.bitpay.model;

import java.util.List;

import com.bitpay.Client;
import com.bitpay.BitPayException;
import com.bitpay.model.Rate;

public class Rates {

    private Client _bp;
    private List<Rate> _rates;

    public Rates(List<Rate> rates, Client bp)
    {
        _bp = bp;
        _rates = rates;
    }

    public List<Rate> getRates()
    {
	    return _rates;
    }

    public void update() throws BitPayException
    {
	    _rates = _bp.getRates().getRates();
    }

    public double getRate(String currencyCode)
    {
	    double val = 0;
	    for (Rate rateObj : _rates)
        {
		    if (rateObj.getCode().equals(currencyCode))
            {
                val = rateObj.getValue();
                break;
		    }
	    }
		return val;
    }
}
