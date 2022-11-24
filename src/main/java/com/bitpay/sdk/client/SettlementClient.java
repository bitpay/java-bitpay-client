/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.exceptions.SettlementQueryException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.Settlement.Settlement;
import com.bitpay.sdk.util.AccessTokenCache;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;

public class SettlementClient {

    private final BitPayClient bitPayClient;
    private final AccessTokenCache accessTokenCache;

    public SettlementClient(BitPayClient bitPayClient, AccessTokenCache accessTokenCache) {
        this.bitPayClient = bitPayClient;
        this.accessTokenCache = accessTokenCache;
    }

    /**
     * Retrieves settlement reports for the calling merchant filtered by query.
     * The `limit` and `offset` parameters
     * specify pages for large query sets.
     *
     * @param currency  The three digit currency string for the ledger to retrieve.
     * @param dateStart The start date for the query.
     * @param dateEnd   The end date for the query.
     * @param status    Can be `processing`, `completed`, or `failed`.
     * @param limit     Maximum number of settlements to retrieve.
     * @param offset    Offset for paging.
     * @return A list of BitPay Settlement objects.
     * @throws BitPayException          BitPayException class
     * @throws SettlementQueryException SettlementQueryException class
     */
    public List<Settlement> getSettlements(String currency, String dateStart, String dateEnd, String status,
                                           Integer limit, Integer offset) throws BitPayException,
        SettlementQueryException {
        status = status != null ? status : "";
        limit = limit != null ? limit : 100;
        offset = offset != null ? offset : 0;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.accessTokenCache.getAccessToken(Facade.MERCHANT)));
        params.add(new BasicNameValuePair("dateStart", dateStart));
        params.add(new BasicNameValuePair("dateEnd", dateEnd));
        params.add(new BasicNameValuePair("currency", currency));
        params.add(new BasicNameValuePair("status", status));
        params.add(new BasicNameValuePair("limit", limit.toString()));
        params.add(new BasicNameValuePair("offset", offset.toString()));

        List<Settlement> settlements;

        try {
            HttpResponse response = this.bitPayClient.get("settlements", params);
            settlements =
                Arrays.asList(
                    new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Settlement[].class));
        } catch (JsonProcessingException e) {
            throw new SettlementQueryException(null,
                "failed to deserialize BitPay server response (Settlement) : " + e.getMessage());
        } catch (Exception e) {
            throw new SettlementQueryException(null,
                "failed to deserialize BitPay server response (Settlement) : " + e.getMessage());
        }

        return settlements;
    }

    /**
     * Retrieves a summary of the specified settlement.
     *
     * @param settlementId Settlement Id.
     * @return A BitPay Settlement object.
     * @throws BitPayException          BitPayException class
     * @throws SettlementQueryException SettlementQueryException class
     */
    public Settlement getSettlement(String settlementId) throws BitPayException, SettlementQueryException {
        String token = this.accessTokenCache.getAccessToken(Facade.MERCHANT);
        final ObjectMapper objectMapper = JsonMapperFactory.create();
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", token));

        Settlement settlement;

        try {
            HttpResponse response = this.bitPayClient.get("settlements/" + settlementId, params);
            settlement =
                objectMapper.readValue(this.bitPayClient.responseToJsonString(response), Settlement.class);
        } catch (JsonProcessingException e) {
            throw new SettlementQueryException(null,
                "failed to deserialize BitPay server response (Settlement) : " + e.getMessage());
        } catch (Exception e) {
            throw new SettlementQueryException(null,
                "failed to deserialize BitPay server response (Settlement) : " + e.getMessage());
        }

        return settlement;
    }

    /**
     * Gets a detailed reconciliation report of the activity within the settlement period.
     *
     * @param settlement Settlement to generate report for.
     * @return A detailed BitPay Settlement object.
     * @throws SettlementQueryException SettlementQueryException class
     */
    public Settlement getSettlementReconciliationReport(Settlement settlement) throws SettlementQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", settlement.getToken()));

        Settlement reconciliationReport;

        try {
            HttpResponse response =
                this.bitPayClient.get("settlements/" + settlement.getId() + "/reconciliationreport", params);
            reconciliationReport =
                new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Settlement.class);
        } catch (JsonProcessingException e) {
            throw new SettlementQueryException(null,
                "failed to deserialize BitPay server response (ReconciliationReport) : " + e.getMessage());
        } catch (Exception e) {
            throw new SettlementQueryException(null,
                "failed to deserialize BitPay server response (ReconciliationReport) : " + e.getMessage());
        }

        return reconciliationReport;
    }
}
