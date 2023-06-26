/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.exceptions.LedgerQueryException;
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
import org.apache.http.HttpResponse;
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
     * @throws BitPayException      BitPayException class
     * @throws LedgerQueryException LedgerQueryException class
     */
    public List<LedgerEntry> getEntries(
        String currency,
        String dateStart,
        String dateEnd
    ) throws BitPayException, LedgerQueryException {
        if (Objects.isNull(currency) || Objects.isNull(dateStart) || Objects.isNull(dateEnd)) {
            throw new BitPayException(null, "missing mandatory fields");
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        ParameterAdder.execute(params, "startDate", dateStart);
        ParameterAdder.execute(params, "endDate", dateEnd);

        try {
            HttpResponse response = this.bitPayClient.get("ledgers/" + currency, params);
            return Arrays.asList(JsonMapperFactory.create()
                .readValue(this.bitPayClient.responseToJsonString(response), LedgerEntry[].class));
        } catch (JsonProcessingException e) {
            throw new LedgerQueryException(null,
                "failed to deserialize BitPay server response (Ledger) : " + e.getMessage());
        } catch (Exception e) {
            throw new LedgerQueryException(null,
                "failed to deserialize BitPay server response (Ledger) : " + e.getMessage());
        }
    }

    /**
     * Ledgers are records of money movement.
     * Retrieve a list of ledgers using the merchant facade.
     *
     * @return A list of Ledger objects populated with the currency and current balance of each one.
     * @throws BitPayException      BitPayException class
     * @throws LedgerQueryException LedgerQueryException class
     */
    public List<Ledger> getLedgers() throws BitPayException, LedgerQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        List<Ledger> ledgers;

        try {
            HttpResponse response = this.bitPayClient.get("ledgers", params);
            ledgers = Arrays
                .asList(JsonMapperFactory.create()
                    .readValue(this.bitPayClient.responseToJsonString(response), Ledger[].class));
        } catch (JsonProcessingException e) {
            throw new LedgerQueryException(null,
                "failed to deserialize BitPay server response (Ledger) : " + e.getMessage());
        } catch (Exception e) {
            throw new LedgerQueryException(null,
                "failed to deserialize BitPay server response (Ledger) : " + e.getMessage());
        }

        return ledgers;
    }
}
