/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.exceptions.PayoutCancellationException;
import com.bitpay.sdk.exceptions.PayoutCreationException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.Payout.Payout;
import com.bitpay.sdk.model.Payout.PayoutGroup;
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
import org.apache.http.HttpResponse;
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
     * @throws BitPayException         BitPayException class
     * @throws PayoutCreationException PayoutCreationException class
     */
    public PayoutGroup submit(Collection<Payout> payouts) throws BitPayException, PayoutCreationException {
        if (Objects.isNull(payouts)) {
            throw new PayoutCreationException(null, "missing required parameter");
        }
        String token = this.accessTokens.getAccessToken(Facade.PAYOUT);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("instructions", payouts);
        parameters.put("token", token);

        JsonMapper mapper = JsonMapperFactory.create();
        String json;

        try {
            json = mapper.writeValueAsString(parameters);
        } catch (JsonProcessingException e) {
            throw new PayoutCreationException(null, "failed to serialize Payouts object : " + e.getMessage());
        }
        try {
            HttpResponse response = this.bitPayClient.post("payouts/group", json, true);
            return JsonMapperFactory.create()
                .readValue(this.bitPayClient.responseToJsonString(response), PayoutGroup.class);
        } catch (Exception e) {
            throw new PayoutCreationException(null,
                "failed to deserialize BitPay server response (Payout Group) : " + e.getMessage());
        }
    }

    /**
     * Cancel a BitPay Payouts.
     *
     * @param groupId String The id of the payout group to cancel.
     * @return A BitPay PayoutGroup with cancelled Payout objects and information's about not cancelled payouts.
     * @throws BitPayException             BitPayException class
     * @throws PayoutCancellationException PayoutCancellationException class
     */
    public PayoutGroup cancel(String groupId) throws BitPayException, PayoutCancellationException {
        if (Objects.isNull(groupId)) {
            throw new PayoutCancellationException(null, "missing required parameter");
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.PAYOUT));

        try {
            HttpResponse response = this.bitPayClient.delete("payouts/group/" + groupId, params);
            return JsonMapperFactory.create()
                .readValue(this.bitPayClient.responseToJsonString(response), PayoutGroup.class);
        } catch (JsonProcessingException e) {
            throw new PayoutCancellationException(null,
                "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutCancellationException(null,
                "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
        }
    }
}
