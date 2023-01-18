/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.exceptions.SettlementQueryException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.Settlement.Settlement;
import com.bitpay.sdk.util.AccessTokens;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.bitpay.sdk.util.ParameterAdder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;

/**
 * The type Settlement client.
 */
public class SettlementClient {

    private final BitPayClient bitPayClient;
    private final AccessTokens accessTokens;

    /**
     * Instantiates a new Settlement client.
     *
     * @param bitPayClient the bit pay client
     * @param accessTokens the access tokens
     */
    public SettlementClient(BitPayClient bitPayClient, AccessTokens accessTokens) {
        this.bitPayClient = bitPayClient;
        this.accessTokens = accessTokens;
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
        limit = limit != null ? limit : 100;
        offset = offset != null ? offset : 0;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        ParameterAdder.execute(params, "startDate", dateStart);
        ParameterAdder.execute(params, "endDate", dateEnd);
        ParameterAdder.execute(params, "currency", currency);
        ParameterAdder.execute(params, "status", status);
        if (Objects.nonNull(limit)) {
            ParameterAdder.execute(params, "limit", limit.toString());
        }
        if (Objects.nonNull(offset)) {
            ParameterAdder.execute(params, "offset", offset.toString());
        }

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
        if (Objects.isNull(settlementId)) {
            throw new SettlementQueryException(null, "missing required parameter");
        }

        Settlement settlement;
        String token = this.accessTokens.getAccessToken(Facade.MERCHANT);
        final ObjectMapper objectMapper = JsonMapperFactory.create();
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", token);

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
     * @param settlementId Settlement ID.
     * @param token Settlement token.
     * @return A detailed BitPay Settlement object.
     * @throws SettlementQueryException SettlementQueryException class
     */
    public Settlement getSettlementReconciliationReport(String settlementId, String token) throws SettlementQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        if (Objects.isNull(settlementId) || Objects.isNull(token)) {
            throw new SettlementQueryException(null, "missing id/token");
        }

        ParameterAdder.execute(params, "token", token);

        Settlement reconciliationReport;

        try {
            HttpResponse response =
                this.bitPayClient.get("settlements/" + settlementId + "/reconciliationreport", params);
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
