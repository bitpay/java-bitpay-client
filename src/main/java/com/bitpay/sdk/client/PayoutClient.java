/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.payout.Payout;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.bitpay.sdk.util.ParameterAdder;
import com.bitpay.sdk.util.TokenContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import org.apache.http.message.BasicNameValuePair;

/**
 * The type Payout client.
 */
public class PayoutClient implements ResourceClient {

    private static PayoutClient instance;
    private final BitPayClient bitPayClient;
    private final TokenContainer accessTokens;

    /**
     * Instantiates a new Payout client.
     *
     * @param bitPayClient the bit pay client
     * @param accessTokens the access tokens
     */
    private PayoutClient(BitPayClient bitPayClient, TokenContainer accessTokens) {
        this.bitPayClient = bitPayClient;
        this.accessTokens = accessTokens;
    }

    /**
     * Factory method for Bill Client.
     *
     * @param bitPayClient BitPay Client
     * @param accessTokens Access Tokens
     * @return PayoutClient
     */
    public static PayoutClient getInstance(BitPayClient bitPayClient, TokenContainer accessTokens) {
        if (Objects.isNull(instance)) {
            instance = new PayoutClient(bitPayClient, accessTokens);
        }

        return instance;
    }

    /**
     * Submit a BitPay Payout.
     *
     * @param payout Payout A Payout object with request parameters defined.
     * @return A BitPay generated Payout object.
     * @throws BitPayApiException         BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Payout submit(Payout payout) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(payout)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        String token = this.accessTokens.getAccessToken(Facade.PAYOUT);
        payout.setToken(token);

        JsonMapper mapper = JsonMapperFactory.create();
        String json = null;

        try {
            json = mapper.writeValueAsString(payout);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwSerializeResourceException("Payout", e.getMessage());
        }

        HttpResponse response = this.bitPayClient.post("payouts", json, true);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            payout = JsonMapperFactory.create()
                .readValue(jsonResponse, Payout.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Payout", e.getMessage());
        }

        return payout;
    }

    /**
     * Retrieve a BitPay payout by payout id using. The client must have been
     * previously authorized for the payout facade.
     *
     * @param payoutId String The id of the payout to retrieve.
     * @return A BitPay Payout object.
     * @throws BitPayApiException      BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Payout get(String payoutId) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(payoutId)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        String token = this.accessTokens.getAccessToken(Facade.PAYOUT);
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", token);

        Payout payout = null;

        HttpResponse response = this.bitPayClient.get("payouts/" + payoutId, params, true);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            payout = JsonMapperFactory.create()
                .readValue(jsonResponse, Payout.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Payout", e.getMessage());
        }

        return payout;
    }

    /**
     * Cancel a BitPay Payout.
     *
     * @param payoutId String The id of the payout to cancel.
     * @return True if the refund was successfully canceled, false otherwise.
     * @throws BitPayApiException BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Boolean cancel(String payoutId)
        throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(payoutId)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.PAYOUT));

        boolean result = false;
        JsonMapper mapper = JsonMapperFactory.create();

        HttpResponse response = this.bitPayClient.delete("payouts/" + payoutId, params);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode node = rootNode.get("status");
            result = node.toString().replace("\"", "").toLowerCase(Locale.ROOT).equals("success");
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeException(e.getMessage());
        }

        return result;
    }

    /**
     * Retrieve a collection of BitPay payouts.
     *
     * @param startDate String The start date for the query.
     * @param endDate   String The end date for the query.
     * @param status    String The status to filter(optional).
     * @param reference String The optional reference specified at payout request creation.
     * @param limit     int Maximum results that the query will return (useful for
     *                  paging results).
     * @param offset    int Offset for paging.
     * @param groupId   String The optional group id assigned to payout.
     * @return A list of BitPay Payout objects.
     * @throws BitPayApiException      BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public List<Payout> getPayouts(
        String startDate,
        String endDate,
        String status,
        String reference,
        Integer limit,
        Integer offset,
        String groupId
    ) throws BitPayApiException, BitPayGenericException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.PAYOUT));
        ParameterAdder.execute(params, "startDate", startDate);
        ParameterAdder.execute(params, "endDate", endDate);
        ParameterAdder.execute(params, "reference", reference);
        ParameterAdder.execute(params, "status", status);
        if (Objects.nonNull(limit)) {
            ParameterAdder.execute(params, "limit", limit.toString());
        }
        if (Objects.nonNull(offset)) {
            ParameterAdder.execute(params, "offset", offset.toString());
        }
        if (Objects.nonNull(groupId)) {
            ParameterAdder.execute(params, "groupId", groupId);
        }

        List<Payout> payouts = null;

        HttpResponse response = this.bitPayClient.get("payouts", params, true);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            payouts = Arrays
                .asList(JsonMapperFactory.create()
                    .readValue(jsonResponse, Payout[].class));
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Payout", e.getMessage());
        }

        return payouts;
    }

    /**
     * Request a payout notification.
     *
     * @param payoutId String The id of the payout to notify..
     * @return True if the notification was successfully sent, false otherwise.
     * @throws BitPayApiException             BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Boolean requestNotification(String payoutId)
        throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(payoutId)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        final Map<String, String> params = new HashMap<>();
        params.put("token", this.accessTokens.getAccessToken(Facade.PAYOUT));

        JsonMapper mapper = JsonMapperFactory.create();

        boolean result = false;
        String json = null;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwEncodeException(e.getMessage());
        }

        HttpResponse response = this.bitPayClient.post("payouts/" + payoutId + "/notifications", json, true);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode node = rootNode.get("status");
            result = node.toString().replace("\"", "").toLowerCase(Locale.ROOT).equals("success");
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeException(e.getMessage());
        }

        return result;
    }
}
