/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.exceptions.PayoutCancellationException;
import com.bitpay.sdk.exceptions.PayoutCreationException;
import com.bitpay.sdk.exceptions.PayoutNotificationException;
import com.bitpay.sdk.exceptions.PayoutQueryException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.Payout.Payout;
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
import org.apache.http.HttpResponse;
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
     * @throws BitPayException         BitPayException class
     * @throws PayoutCreationException PayoutCreationException class
     */
    public Payout submit(Payout payout) throws BitPayException, PayoutCreationException {
        if (Objects.isNull(payout)) {
            throw new PayoutCreationException(null, "missing required parameter");
        }
        String token = this.accessTokens.getAccessToken(Facade.PAYOUT);
        payout.setToken(token);

        JsonMapper mapper = JsonMapperFactory.create();
        String json;

        try {
            json = mapper.writeValueAsString(payout);
        } catch (JsonProcessingException e) {
            throw new PayoutCreationException(null, "failed to serialize Payout object : " + e.getMessage());
        }
        try {
            HttpResponse response = this.bitPayClient.post("payouts", json, true);
            payout = JsonMapperFactory.create()
                .readValue(this.bitPayClient.responseToJsonString(response), Payout.class);
        } catch (Exception e) {
            throw new PayoutCreationException(null,
                "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
        }

        return payout;
    }

    /**
     * Retrieve a BitPay payout by payout id using. The client must have been
     * previously authorized for the payout facade.
     *
     * @param payoutId String The id of the payout to retrieve.
     * @return A BitPay Payout object.
     * @throws BitPayException      BitPayException class
     * @throws PayoutQueryException PayoutQueryException class
     */
    public Payout get(String payoutId) throws BitPayException, PayoutQueryException {
        if (Objects.isNull(payoutId)) {
            throw new PayoutQueryException(null, "missing required parameter");
        }

        String token = this.accessTokens.getAccessToken(Facade.PAYOUT);
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params,"token", token);

        Payout payout;

        try {
            HttpResponse response = this.bitPayClient.get("payouts/" + payoutId, params, true);
            payout = JsonMapperFactory.create()
                .readValue(this.bitPayClient.responseToJsonString(response), Payout.class);
        } catch (JsonProcessingException e) {
            throw new PayoutQueryException(null,
                "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutQueryException(null,
                "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
        }

        return payout;
    }

    /**
     * Cancel a BitPay Payout.
     *
     * @param payoutId String The id of the payout to cancel.
     * @return True if the refund was successfully canceled, false otherwise.
     * @throws BitPayException             BitPayException class
     * @throws PayoutCancellationException PayoutCancellationException class
     */
    public Boolean cancel(String payoutId) throws BitPayException, PayoutCancellationException {
        if (Objects.isNull(payoutId)) {
            throw new PayoutCancellationException(null, "missing required parameter");
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.PAYOUT));

        boolean result;
        JsonMapper mapper = JsonMapperFactory.create();

        try {
            HttpResponse response = this.bitPayClient.delete("payouts/" + payoutId, params);
            String jsonString = this.bitPayClient.responseToJsonString(response);
            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode node = rootNode.get("status");
            result = node.toString().replace("\"", "").toLowerCase(Locale.ROOT).equals("success");

        } catch (JsonProcessingException e) {
            throw new PayoutCancellationException(null,
                "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutCancellationException(null,
                "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
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
     * @throws BitPayException      BitPayException class
     * @throws PayoutQueryException PayoutQueryException class
     */
    public List<Payout> getPayouts(
        String startDate,
        String endDate,
        String status,
        String reference,
        Integer limit,
        Integer offset,
        String groupId
    ) throws BitPayException, PayoutQueryException {
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

        List<Payout> payouts;

        try {
            HttpResponse response = this.bitPayClient.get("payouts", params, true);
            payouts = Arrays
                .asList(JsonMapperFactory.create()
                    .readValue(this.bitPayClient.responseToJsonString(response), Payout[].class));
        } catch (JsonProcessingException e) {
            throw new PayoutQueryException(null,
                "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutQueryException(null,
                "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
        }

        return payouts;
    }

    /**
     * Request a payout notification
     *
     * @param payoutId String The id of the payout to notify..
     * @return True if the notification was successfully sent, false otherwise.
     * @throws BitPayException             BitPayException class
     * @throws PayoutNotificationException PayoutNotificationException class
     */
    public Boolean requestNotification(String payoutId)
        throws BitPayException, PayoutNotificationException {
        if (Objects.isNull(payoutId)) {
            throw new PayoutNotificationException(null, "missing required parameter");
        }

        final Map<String, String> params = new HashMap<>();
        params.put("token", this.accessTokens.getAccessToken(Facade.PAYOUT));

        JsonMapper mapper = JsonMapperFactory.create();

        boolean result;
        String json;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new PayoutNotificationException(null, "failed to serialize payout batch object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.bitPayClient.post("payouts/" + payoutId + "/notifications", json, true);
            String jsonString = this.bitPayClient.responseToJsonString(response);
            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode node = rootNode.get("status");
            result = node.toString().replace("\"", "").toLowerCase(Locale.ROOT).equals("success");
        } catch (BitPayException ex) {
            throw new PayoutNotificationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new PayoutNotificationException(null,
                "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
        }

        return result;
    }
}
