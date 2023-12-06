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
import com.bitpay.sdk.model.payout.PayoutGroup;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.bitpay.sdk.util.ParameterAdder;
import com.bitpay.sdk.util.TokenContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.http.message.BasicNameValuePair;

/**
 * The type Payout Group Client.
 */
public class PayoutGroupClient implements ResourceClient {

    private static PayoutGroupClient instance;
    private final BitPayClient bitPayClient;
    private final TokenContainer accessTokens;

    /**
     * Instantiates a new Payout Group client.
     *
     * @param bitPayClient the bit pay client
     * @param accessTokens the access tokens
     */
    private PayoutGroupClient(BitPayClient bitPayClient, TokenContainer accessTokens) {
        this.bitPayClient = bitPayClient;
        this.accessTokens = accessTokens;
    }

    /**
     * Factory method for Payout Group Client.
     *
     * @param bitPayClient BitPay Client
     * @param accessTokens Access Tokens
     * @return PayoutGroupClient
     */
    public static PayoutGroupClient getInstance(BitPayClient bitPayClient, TokenContainer accessTokens) {
        if (Objects.isNull(instance)) {
            instance = new PayoutGroupClient(bitPayClient, accessTokens);
        }

        return instance;
    }

    /**
     * Submit a BitPay Payouts.
     *
     * @param payouts Collection of Payout objects with request parameters defined.
     * @return A BitPay PayoutGroup with generated Payout objects and information's about not created payouts.
     * @throws BitPayApiException         BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public PayoutGroup submit(Collection<Payout> payouts) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(payouts)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }
        String token = this.accessTokens.getAccessToken(Facade.PAYOUT);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("instructions", payouts);
        parameters.put("token", token);

        JsonMapper mapper = JsonMapperFactory.create();
        String json = null;
        PayoutGroup result = null;

        try {
            json = mapper.writeValueAsString(parameters);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwEncodeException(e.getMessage());
        }

        HttpResponse response = this.bitPayClient.post("payouts/group", json, true);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            result = JsonMapperFactory.create()
                .readValue(jsonResponse, PayoutGroup.class);
        } catch (Exception e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Payout Group", e.getMessage());
        }

        return result;
    }

    /**
     * Cancel a BitPay Payouts.
     *
     * @param groupId String The id of the payout group to cancel.
     * @return A BitPay PayoutGroup with cancelled Payout objects and information's about not cancelled payouts.
     * @throws BitPayApiException             BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public PayoutGroup cancel(String groupId)
        throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(groupId)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        PayoutGroup result = null;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.PAYOUT));

        HttpResponse response = this.bitPayClient.delete("payouts/group/" + groupId, params);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            result = JsonMapperFactory.create()
                .readValue(jsonResponse, PayoutGroup.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Payout Group", e.getMessage());
        }

        return result;
    }
}
