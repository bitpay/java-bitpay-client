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
import com.bitpay.sdk.util.AccessTokens;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;

/**
 * The type Payout client.
 */
public class PayoutClient {

    private final BitPayClient bitPayClient;
    private final AccessTokens accessTokens;

    /**
     * Instantiates a new Payout client.
     *
     * @param bitPayClient the bit pay client
     * @param accessTokens the access tokens
     */
    public PayoutClient(BitPayClient bitPayClient, AccessTokens accessTokens) {
        this.bitPayClient = bitPayClient;
        this.accessTokens = accessTokens;
    }

    /**
     * Submit a BitPay Payout.
     *
     * @param payout Payout A Payout object with request parameters defined.
     * @return A BitPay generated Payout object.
     * @throws BitPayException         BitPayException class
     * @throws PayoutCreationException PayoutCreationException class
     */
    public Payout create(Payout payout) throws BitPayException, PayoutCreationException {
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
            payout = new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Payout.class);
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
        String token = this.accessTokens.getAccessToken(Facade.PAYOUT);
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", token));

        Payout payout;

        try {
            HttpResponse response = this.bitPayClient.get("payouts/" + payoutId, params, true);
            payout = new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Payout.class);
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

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.accessTokens.getAccessToken(Facade.PAYOUT)));
        Boolean result;
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
     * @return A list of BitPay Payout objects.
     * @throws BitPayException      BitPayException class
     * @throws PayoutQueryException PayoutQueryException class
     */
    public List<Payout> getPayouts(String startDate, String endDate, String status, String reference, Integer limit,
                                   Integer offset) throws BitPayException, PayoutQueryException {

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.accessTokens.getAccessToken(Facade.PAYOUT)));
        if (startDate != null) {
            params.add(new BasicNameValuePair("startDate", startDate));
        }
        if (endDate != null) {
            params.add(new BasicNameValuePair("endDate", endDate));
        }
        if (reference != null) {
            params.add(new BasicNameValuePair("reference", reference));
        }
        if (status != null) {
            params.add(new BasicNameValuePair("status", status));
        }
        if (limit != null) {
            params.add(new BasicNameValuePair("limit", limit.toString()));
        }
        if (offset != null) {
            params.add(new BasicNameValuePair("offset", offset.toString()));
        }

        List<Payout> payouts;

        try {
            HttpResponse response = this.bitPayClient.get("payouts", params, true);
            payouts = Arrays
                .asList(new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Payout[].class));
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
    public Boolean requestPayoutNotification(String payoutId)
        throws BitPayException, PayoutNotificationException {
        final Map<String, String> params = new HashMap<>();
        params.put("token", this.accessTokens.getAccessToken(Facade.PAYOUT));

        JsonMapper mapper = JsonMapperFactory.create();

        Boolean result;
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
