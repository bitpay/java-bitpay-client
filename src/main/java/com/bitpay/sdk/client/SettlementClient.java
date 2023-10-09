/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.settlement.Settlement;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.bitpay.sdk.util.ParameterAdder;
import com.bitpay.sdk.util.TokenContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.apache.http.message.BasicNameValuePair;

/**
 * The type Settlement client.
 */
public class SettlementClient implements ResourceClient {

    private static SettlementClient instance;
    private final BitPayClient bitPayClient;
    private final TokenContainer accessTokens;

    /**
     * Instantiates a new Settlement client.
     *
     * @param bitPayClient the bit pay client
     * @param accessTokens the access tokens
     */
    private SettlementClient(
        BitPayClient bitPayClient,
        TokenContainer accessTokens
    ) {
        this.bitPayClient = bitPayClient;
        this.accessTokens = accessTokens;
    }

    /**
     * Factory method for Settlement Client.
     *
     * @param bitPayClient BitPay Client
     * @param accessTokens Access Tokens
     * @return SettlementClient
     */
    public static SettlementClient getInstance(
        BitPayClient bitPayClient,
        TokenContainer accessTokens
    ) {
        if (Objects.isNull(instance)) {
            instance = new SettlementClient(bitPayClient, accessTokens);
        }

        return instance;
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
     * @throws BitPayApiException          BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public List<Settlement> getSettlements(
        String currency,
        String dateStart,
        String dateEnd,
        String status,
        Integer limit,
        Integer offset
    ) throws BitPayApiException, BitPayGenericException {
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

        List<Settlement> settlements = null;

        String jsonResponse = this.bitPayClient.get("settlements", params);

        try {
            settlements =
                Arrays.asList(JsonMapperFactory.create()
                    .readValue(jsonResponse, Settlement[].class));
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Settlement", e.getMessage());
        }

        return settlements;
    }

    /**
     * Retrieves a summary of the specified settlement.
     *
     * @param settlementId Settlement Id.
     * @return A BitPay Settlement object.
     * @throws BitPayApiException          BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Settlement get(String settlementId) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(settlementId)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        Settlement settlement = null;
        String token = this.accessTokens.getAccessToken(Facade.MERCHANT);
        final ObjectMapper objectMapper = JsonMapperFactory.create();
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", token);

        String response = this.bitPayClient.get("settlements/" + settlementId, params);

        try {
            settlement =
                objectMapper.readValue(response, Settlement.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Settlement", e.getMessage());
        }

        return settlement;
    }

    /**
     * Gets a detailed reconciliation report of the activity within the settlement period.
     *
     * @param settlementId Settlement ID.
     * @param token Settlement token.
     * @return A detailed BitPay Settlement object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     */
    public Settlement getSettlementReconciliationReport(
        String settlementId,
        String token
    ) throws BitPayGenericException, BitPayApiException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        if (Objects.isNull(settlementId) || Objects.isNull(token)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        ParameterAdder.execute(params, "token", token);

        Settlement reconciliationReport = null;

        String jsonResponse = this.bitPayClient.get("settlements/" + settlementId + "/reconciliationreport", params);

        try {
            reconciliationReport = JsonMapperFactory.create()
                .readValue(jsonResponse, Settlement.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Settlement", e.getMessage());
        }

        return reconciliationReport;
    }
}
