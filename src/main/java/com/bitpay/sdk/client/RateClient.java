/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.RateQueryException;
import com.bitpay.sdk.model.Rate.Rate;
import com.bitpay.sdk.model.Rate.Rates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpResponse;

/**
 * The type Rate client.
 */
public class RateClient {

    private final BitPayClient bitPayClient;

    /**
     * Instantiates a new Rate client.
     *
     * @param bitPayClient the bit pay client
     */
    public RateClient(BitPayClient bitPayClient) {
        this.bitPayClient = bitPayClient;
    }

    /**
     * Retrieve the exchange rate table maintained by BitPay.  See https://bitpay.com/bitcoin-exchange-rates.
     *
     * @return A Rates object populated with the BitPay exchange rate table.
     * @throws RateQueryException RateQueryException class
     */
    public Rates getRates() throws RateQueryException {
        List<Rate> rates;

        try {
            HttpResponse response = this.bitPayClient.get("rates");
            rates = Arrays.asList(new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Rate[].class));
        } catch (JsonProcessingException e) {
            throw new RateQueryException(null,
                "failed to deserialize BitPay server response (Rates) : " + e.getMessage());
        } catch (Exception e) {
            throw new RateQueryException(null,
                "failed to deserialize BitPay server response (Rates) : " + e.getMessage());
        }

        return new Rates(rates);
    }
}
