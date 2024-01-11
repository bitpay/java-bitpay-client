package com.bitpay.sdk.examples.Public;

import com.bitpay.sdk.Client;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.rate.Rate;
import com.bitpay.sdk.model.rate.Rates;
import com.bitpay.sdk.examples.ClientProvider;

public class RateRequests {

    public void getRate() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        Rates rates = client.getRates();

        Rates currencyRates = client.getRates("BTC");

        Rate currencyPairRate = client.getRate("BTC", "USD");
    }
}
