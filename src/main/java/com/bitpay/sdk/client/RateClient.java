/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.RateQueryException;
import com.bitpay.sdk.model.Rate.Rate;
import com.bitpay.sdk.model.Rate.Rates;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
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
     * Retrieve the rates for a cryptocurrency / fiat pair. See https://bitpay.com/bitcoin-exchange-rates.
     *
     * @param baseCurrency the cryptocurrency for which you want to fetch the rates.
     *                     Current supported values are BTC and BCH.
     * @param currency the fiat currency for which you want to fetch the baseCurrency rates.
     * @return A Rates object populated with the BitPay exchange rate table.
     * @throws RateQueryException RateQueryException class
     * @since 8.8.0
     */
    public Rate getRate(String baseCurrency, String currency) throws RateQueryException {
        try {
            HttpResponse response = this.bitPayClient.get("rates/" + baseCurrency + "/" + currency);
            final String content = this.bitPayClient.responseToJsonString(response);
            return JsonMapperFactory.create().readValue(content, Rate.class);
        } catch (JsonProcessingException e) {
            throw new RateQueryException(null,
                "failed to deserialize BitPay server response (Rates) : " + e.getMessage());
        } catch (Exception e) {
            throw new RateQueryException(null,
                "failed to deserialize BitPay server response (Rates) : " + e.getMessage());
        }
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
            rates = Arrays.asList(JsonMapperFactory.create().readValue(this.bitPayClient.responseToJsonString(response), Rate[].class));
        } catch (JsonProcessingException e) {
            throw new RateQueryException(null,
                "failed to deserialize BitPay server response (Rates) : " + e.getMessage());
        } catch (Exception e) {
            throw new RateQueryException(null,
                "failed to deserialize BitPay server response (Rates) : " + e.getMessage());
        }

        return new Rates(rates);
    }

    /**
     * Retrieve the exchange rate table maintained by BitPay by baseCurrency. See https://bitpay.com/bitcoin-exchange-rates.
     *
     * @param baseCurrency the cryptocurrency for which you want to fetch the rates.
     *                     Current supported values are BTC and BCH.
     * @return A Rates object populated with the BitPay exchange rate table.
     * @throws RateQueryException RateQueryException class
     * @since 8.8.0
     */
    public Rates getRates(String baseCurrency) throws RateQueryException {
        List<Rate> rates;

        try {
            HttpResponse response = this.bitPayClient.get("rates/" + baseCurrency);
            rates = Arrays.asList(JsonMapperFactory.create().readValue(this.bitPayClient.responseToJsonString(response), Rate[].class));
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
