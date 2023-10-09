/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.payout.PayoutRecipient;
import com.bitpay.sdk.model.payout.PayoutRecipients;
import com.bitpay.sdk.util.GuidGenerator;
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
 * The type Payout recipients client.
 */
public class PayoutRecipientsClient implements ResourceClient {

    private static PayoutRecipientsClient instance;
    private final BitPayClient bitPayClient;
    private final TokenContainer accessTokens;
    private final GuidGenerator guidGenerator;

    /**
     * Instantiates a new Payout recipients client.
     *
     * @param bitPayClient  the bit pay client
     * @param accessTokens  the access tokens
     * @param guidGenerator the Guid generator
     */
    private PayoutRecipientsClient(
        BitPayClient bitPayClient,
        TokenContainer accessTokens,
        GuidGenerator guidGenerator
    ) {
        this.bitPayClient = bitPayClient;
        this.accessTokens = accessTokens;
        this.guidGenerator = guidGenerator;
    }

    /**
     * Factory method for Bill Client.
     *
     * @param bitPayClient BitPay Client
     * @param accessTokens Access Tokens
     * @param guidGenerator Guid Generator
     * @return PayoutRecipientsClient
     */
    public static PayoutRecipientsClient getInstance(
        BitPayClient bitPayClient,
        TokenContainer accessTokens,
        GuidGenerator guidGenerator
    ) {
        if (Objects.isNull(instance)) {
            instance = new PayoutRecipientsClient(bitPayClient, accessTokens, guidGenerator);
        }

        return instance;
    }

    /**
     * Submit BitPay Payout Recipients.
     *
     * @param recipients PayoutRecipients A PayoutRecipients object with request parameters defined.
     * @return array A list of BitPay PayoutRecipients objects..
     * @throws BitPayApiException  BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public List<PayoutRecipient> submit(PayoutRecipients recipients) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(recipients)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        recipients.setToken(this.accessTokens.getAccessToken(Facade.PAYOUT));
        recipients.setGuid(Objects.isNull(recipients.getGuid()) ? this.guidGenerator.execute() : recipients.getGuid());
        JsonMapper mapper = JsonMapperFactory.create();
        String json = null;

        try {
            json = mapper.writeValueAsString(recipients);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwSerializeResourceException("Payout Recipients", e.getMessage());
        }

        List<PayoutRecipient> recipientsList = null;

        String jsonResponse = this.bitPayClient.post("recipients", json, true);

        try {
            recipientsList = Arrays
                .asList(JsonMapperFactory.create()
                    .readValue(jsonResponse, PayoutRecipient[].class));
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Payout Recipient", e.getMessage());
        }

        return recipientsList;
    }

    /**
     * Retrieve a collection of BitPay Payout Recipients.
     *
     * @param status String|null The recipient status you want to query on.
     * @param limit  int Maximum results that the query will return (useful for
     *               paging results). result).
     * @param offset int Offset for paging.
     * @return array A list of BitPayRecipient objects.
     * @throws BitPayApiException               BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public List<PayoutRecipient> getRecipientsByFilters(String status, Integer limit, Integer offset)
        throws BitPayApiException, BitPayGenericException {

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.PAYOUT));
        ParameterAdder.execute(params, "status", status);
        if (Objects.nonNull(limit)) {
            ParameterAdder.execute(params, "limit", limit.toString());
        }
        if (Objects.nonNull(offset)) {
            ParameterAdder.execute(params, "offset", offset.toString());
        }

        List<PayoutRecipient> recipientsList = null;

        String jsonResponse = this.bitPayClient.get("recipients", params, true);

        try {
            recipientsList = Arrays
                .asList(JsonMapperFactory.create()
                    .readValue(jsonResponse, PayoutRecipient[].class));
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Payout Recipient", e.getMessage());
        }

        return recipientsList;
    }

    /**
     * Retrieve a BitPay payout recipient by batch id using.  The client must have been previously authorized for the
     * payout facade.
     *
     * @param recipientId String The id of the recipient to retrieve.
     * @return PayoutRecipient A BitPay PayoutRecipient object.
     * @throws BitPayApiException               BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public PayoutRecipient get(String recipientId)
        throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(recipientId)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.PAYOUT));

        PayoutRecipient recipient = null;

        String jsonResponse = this.bitPayClient.get("recipients/" + recipientId, params, true);

        try {
            recipient = JsonMapperFactory.create()
                .readValue(jsonResponse, PayoutRecipient.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Payout Recipient", e.getMessage());
        }

        return recipient;
    }

    /**
     * Update a Payout Recipient.
     *
     * @param recipientId String The recipient id for the recipient to be updated.
     * @param recipient   PayoutRecipients A PayoutRecipient object with updated
     *                    parameters defined.
     * @return The updated recipient object.
     * @throws BitPayApiException                BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public PayoutRecipient update(String recipientId, PayoutRecipient recipient)
        throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(recipient) || Objects.isNull(recipientId)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        recipient.setToken(this.accessTokens.getAccessToken(Facade.PAYOUT));
        recipient.setGuid(Objects.isNull(recipient.getGuid()) ? this.guidGenerator.execute() : recipient.getGuid());
        JsonMapper mapper = JsonMapperFactory.create();
        String json = null;

        try {
            json = mapper.writeValueAsString(recipient);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwSerializeResourceException("Payout Recipient", e.getMessage());
        }

        PayoutRecipient updateRecipient = null;

        String response = this.bitPayClient.update("recipients/" + recipientId, json);

        try {
            updateRecipient = JsonMapperFactory.create()
                .readValue(response, PayoutRecipient.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Payout Recipient", e.getMessage());
        }

        return updateRecipient;
    }

    /**
     * Cancel a BitPay Payout recipient.
     *
     * @param recipientId String The id of the recipient to cancel.
     * @return True if the delete operation was successful, false otherwise.
     * @throws BitPayApiException                      BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Boolean delete(String recipientId) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(recipientId)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.PAYOUT));

        JsonMapper mapper = JsonMapperFactory.create();
        Boolean result = null;
        String jsonResponse = this.bitPayClient.delete("recipients/" + recipientId, params);

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
     * Request a payout recipient notification.
     *
     * @param recipientId String A BitPay recipient ID.
     * @return True if the notification was successfully sent, false otherwise.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException                      BitPayApiException class
     */
    public Boolean requestNotification(String recipientId) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(recipientId)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        final Map<String, String> params = new HashMap<>();
        params.put("token", this.accessTokens.getAccessToken(Facade.PAYOUT));

        JsonMapper mapper = JsonMapperFactory.create();
        Boolean result = null;
        String json = null;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwSerializeParamsException(e.getMessage());
        }

        String jsonResponse = this.bitPayClient.post("recipients/" + recipientId + "/notifications", json, true);

        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(jsonResponse);
            JsonNode node = rootNode.get("status");
            result = node.toString().replace("\"", "").toLowerCase(Locale.ROOT).equals("success");
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeException(e.getMessage());
        }

        return result;
    }
}
