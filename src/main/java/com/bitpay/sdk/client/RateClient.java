/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.rate.Rate;
import com.bitpay.sdk.model.rate.Rates;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * The type Rate client.
 */
public class RateClient implements ResourceClient {

    private static RateClient instance;
    private final BitPayClient bitPayClient;

    /**
     * Instantiates a new Rate client.
     *
     * @param bitPayClient the bit pay client
     */
    private RateClient(BitPayClient bitPayClient) {
        this.bitPayClient = bitPayClient;
    }

    /**
     * Factory method for Rate Client.
     *
     * @param bitPayClient BitPay Client
     * @return RateClient
     */
    public static RateClient getInstance(BitPayClient bitPayClient) {
        if (Objects.isNull(instance)) {
            instance = new RateClient(bitPayClient);
        }

        return instance;
    }

    /**
     * Retrieve the rates for a cryptocurrency / fiat pair.
     *
     * @param baseCurrency the cryptocurrency for which you want to fetch the rates.
     *                     Current supported values are BTC and BCH.
     * @param currency the fiat currency for which you want to fetch the baseCurrency rates.
     * @return A Rates object populated with the BitPay exchange rate table.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @since 8.8.0
     * @see <a href="https://bitpay.com/bitcoin-exchange-rates">Exchange rates</a>
     */
    public Rate get(
        String baseCurrency,
        String currency
    ) throws BitPayGenericException, BitPayApiException {
        HttpResponse response = this.bitPayClient.get("rates/" + baseCurrency + "/" + currency);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        Rate rate = null;

        try {
            rate = JsonMapperFactory.create().readValue(jsonResponse, Rate.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Rate", e.getMessage());
        }

        return rate;
    }

    /**
     * Retrieve the exchange rate table maintained by BitPay.
     *
     * @return A Rates object populated with the BitPay exchange rate table.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://bitpay.com/bitcoin-exchange-rates">Exchange rates</a>
     */
    public Rates getRates() throws BitPayGenericException, BitPayApiException {
        List<Rate> rates = null;

        HttpResponse response = this.bitPayClient.get("rates");
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            rates = Arrays.asList(
                JsonMapperFactory.create().readValue(jsonResponse, Rate[].class)
            );
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Rates", e.getMessage());
        }

        return new Rates(rates);
    }

    /**
     * Retrieve the exchange rate table maintained by BitPay by baseCurrency.
     *
     * @param baseCurrency the cryptocurrency for which you want to fetch the rates.
     *                     Current supported values are BTC and BCH.
     * @return A Rates object populated with the BitPay exchange rate table.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @since 8.8.0
     * @see <a href="https://bitpay.com/bitcoin-exchange-rates">Exchange rates</a>
     */
    public Rates getRates(String baseCurrency) throws BitPayGenericException, BitPayApiException {
        List<Rate> rates = null;

        HttpResponse response = this.bitPayClient.get("rates/" + baseCurrency);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            rates = Arrays.asList(
                JsonMapperFactory.create().readValue(jsonResponse, Rate[].class)
            );
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Rates", e.getMessage());
        }

        return new Rates(rates);
    }
}
