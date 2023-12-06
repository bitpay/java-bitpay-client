/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.ledger.Ledger;
import com.bitpay.sdk.model.ledger.LedgerEntry;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.bitpay.sdk.util.ParameterAdder;
import com.bitpay.sdk.util.TokenContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.apache.http.message.BasicNameValuePair;

/**
 * The type Ledger client.
 */
public class LedgerClient implements ResourceClient {

    private static LedgerClient instance;
    private final BitPayClient bitPayClient;
    private final TokenContainer accessTokens;

    /**
     * Instantiates a new Ledger client.
     *
     * @param bitPayClient the bit pay client
     * @param accessTokens the access tokens
     */
    private LedgerClient(
        BitPayClient bitPayClient,
        TokenContainer accessTokens
    ) {
        this.bitPayClient = bitPayClient;
        this.accessTokens = accessTokens;
    }

    /**
     * Factory method for Ledger Client.
     *
     * @param bitPayClient BitPay Client
     * @param accessTokens Access Tokens
     * @return LedgerClient
     */
    public static LedgerClient getInstance(
        BitPayClient bitPayClient,
        TokenContainer accessTokens
    ) {
        if (Objects.isNull(instance)) {
            instance = new LedgerClient(bitPayClient, accessTokens);
        }

        return instance;
    }

    /**
     * Retrieve a list of ledgers entries by currency and date range using the merchant facade.
     *
     * @param currency  The three digit currency string for the ledger to retrieve.
     * @param dateStart The first date for the query filter.
     * @param dateEnd   The last date for the query filter.
     * @return A list of Ledger entries.
     * @throws BitPayApiException      BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public List<LedgerEntry> getEntries(
        String currency,
        String dateStart,
        String dateEnd
    ) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(currency) || Objects.isNull(dateStart) || Objects.isNull(dateEnd)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        ParameterAdder.execute(params, "startDate", dateStart);
        ParameterAdder.execute(params, "endDate", dateEnd);

        HttpResponse response = this.bitPayClient.get("ledgers/" + currency, params);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        List<LedgerEntry> entries = null;

        try {
            entries = Arrays.asList(JsonMapperFactory.create()
                .readValue(jsonResponse, LedgerEntry[].class));
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Ledger", e.getMessage());
        }

        return entries;
    }

    /**
     * Ledgers are records of money movement.
     * Retrieve a list of ledgers using the merchant facade.
     *
     * @return A list of Ledger objects populated with the currency and current balance of each one.
     * @throws BitPayApiException      BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public List<Ledger> getLedgers() throws BitPayApiException, BitPayGenericException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        List<Ledger> ledgers = null;

        HttpResponse response = this.bitPayClient.get("ledgers", params);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            ledgers = Arrays
                .asList(JsonMapperFactory.create()
                    .readValue(jsonResponse, Ledger[].class));
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Ledger", e.getMessage());
        }

        return ledgers;
    }
}
