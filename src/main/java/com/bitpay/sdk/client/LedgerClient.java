/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.exceptions.LedgerQueryException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.Ledger.Ledger;
import com.bitpay.sdk.model.Ledger.LedgerEntry;
import com.bitpay.sdk.util.AccessTokenCache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;

public class LedgerClient {

    private final BitPayClient bitPayClient;
    private final AccessTokenCache accessTokenCache;

    public LedgerClient(BitPayClient bitPayClient, AccessTokenCache accessTokenCache) {
        this.bitPayClient = bitPayClient;
        this.accessTokenCache = accessTokenCache;
    }

    /**
     * Retrieve a list of ledgers by date range using the merchant facade.
     *
     * @param currency  The three digit currency string for the ledger to retrieve.
     * @param dateStart The first date for the query filter.
     * @param dateEnd   The last date for the query filter.
     * @return A Ledger object populated with the BitPay ledger entries list.
     * @throws BitPayException      BitPayException class
     * @throws LedgerQueryException LedgerQueryException class
     */
    public Ledger getLedger(String currency, String dateStart, String dateEnd) throws BitPayException,
        LedgerQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.accessTokenCache.getAccessToken(Facade.MERCHANT)));
        params.add(new BasicNameValuePair("startDate", dateStart));
        params.add(new BasicNameValuePair("endDate", dateEnd));

        Ledger ledger = new Ledger();

        try {
            HttpResponse response = this.bitPayClient.get("ledgers/" + currency, params);
            List<LedgerEntry> ledgerEntries;
            ledgerEntries = Arrays.asList(new ObjectMapper()
                .readValue(this.bitPayClient.responseToJsonString(response), LedgerEntry[].class));
            ledgerEntries.remove(null);
            ledgerEntries.remove("");
            ledger.setEntries(ledgerEntries);
        } catch (JsonProcessingException e) {
            throw new LedgerQueryException(null,
                "failed to deserialize BitPay server response (Ledger) : " + e.getMessage());
        } catch (Exception e) {
            throw new LedgerQueryException(null,
                "failed to deserialize BitPay server response (Ledger) : " + e.getMessage());
        }

        return ledger;
    }

    /**
     * Retrieve a list of ledgers using the merchant facade.
     *
     * @return A list of Ledger objects populated with the currency and current balance of each one.
     * @throws BitPayException      BitPayException class
     * @throws LedgerQueryException LedgerQueryException class
     */
    public List<Ledger> getLedgers() throws BitPayException, LedgerQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.accessTokenCache.getAccessToken(Facade.MERCHANT)));

        List<Ledger> ledgers;

        try {
            HttpResponse response = this.bitPayClient.get("ledgers", params);
            ledgers = Arrays
                .asList(new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Ledger[].class));
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
