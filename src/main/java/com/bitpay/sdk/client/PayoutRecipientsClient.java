/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.exceptions.PayoutRecipientCancellationException;
import com.bitpay.sdk.exceptions.PayoutRecipientCreationException;
import com.bitpay.sdk.exceptions.PayoutRecipientNotificationException;
import com.bitpay.sdk.exceptions.PayoutRecipientQueryException;
import com.bitpay.sdk.exceptions.PayoutRecipientUpdateException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.Payout.PayoutRecipient;
import com.bitpay.sdk.model.Payout.PayoutRecipients;
import com.bitpay.sdk.util.TokenContainer;
import com.bitpay.sdk.util.GuidGenerator;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.bitpay.sdk.util.ParameterAdder;
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
import java.util.Objects;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;

/**
 * The type Payout recipients client.
 */
public class PayoutRecipientsClient {

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
    public PayoutRecipientsClient(
        BitPayClient bitPayClient,
        TokenContainer accessTokens,
        GuidGenerator guidGenerator
    ) {
        this.bitPayClient = bitPayClient;
        this.accessTokens = accessTokens;
        this.guidGenerator = guidGenerator;
    }

    /**
     * Submit BitPay Payout Recipients.
     *
     * @param recipients PayoutRecipients A PayoutRecipients object with request parameters defined.
     * @return array A list of BitPay PayoutRecipients objects..
     * @throws BitPayException                  BitPayException class
     * @throws PayoutRecipientCreationException PayoutRecipientCreationException class
     */
    public List<PayoutRecipient> submit(PayoutRecipients recipients)
        throws BitPayException, PayoutRecipientCreationException {
        if (Objects.isNull(recipients)) {
            throw new PayoutRecipientCreationException(null, "missing required parameter");
        }

        recipients.setToken(this.accessTokens.getAccessToken(Facade.PAYOUT));
        recipients.setGuid(Objects.isNull(recipients.getGuid()) ? this.guidGenerator.execute() : recipients.getGuid());
        JsonMapper mapper = JsonMapperFactory.create();
        String json;

        try {
            json = mapper.writeValueAsString(recipients);
        } catch (JsonProcessingException e) {
            throw new PayoutRecipientCreationException(null,
                "failed to serialize PayoutRecipients object : " + e.getMessage());
        }

        List<PayoutRecipient> recipientsList;

        try {
            HttpResponse response = this.bitPayClient.post("recipients", json, true);
            recipientsList = Arrays
                .asList(JsonMapperFactory.create()
                    .readValue(this.bitPayClient.responseToJsonString(response), PayoutRecipient[].class));
        } catch (JsonProcessingException e) {
            throw new PayoutRecipientCreationException(null,
                "failed to deserialize BitPay server response (PayoutRecipients) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutRecipientCreationException(null,
                "failed to deserialize BitPay server response (PayoutRecipients) : " + e.getMessage());
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
     * @throws BitPayException               BitPayException class
     * @throws PayoutRecipientQueryException PayoutRecipientQueryException class
     */
    public List<PayoutRecipient> getByFilters(String status, Integer limit, Integer offset)
        throws BitPayException, PayoutRecipientQueryException {

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.PAYOUT));
        ParameterAdder.execute(params, "status", status);
        if (Objects.nonNull(limit)) {
            ParameterAdder.execute(params, "limit", limit.toString());
        }
        if (Objects.nonNull(offset)) {
            ParameterAdder.execute(params, "offset", offset.toString());
        }

        List<PayoutRecipient> recipientsList;

        try {
            HttpResponse response = this.bitPayClient.get("recipients", params, true);
            recipientsList = Arrays
                .asList(new ObjectMapper()
                    .readValue(this.bitPayClient.responseToJsonString(response), PayoutRecipient[].class));
        } catch (JsonProcessingException e) {
            throw new PayoutRecipientQueryException(null,
                "failed to deserialize BitPay server response (PayoutRecipients) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutRecipientQueryException(null,
                "failed to deserialize BitPay server response (PayoutRecipients) : " + e.getMessage());
        }

        return recipientsList;
    }

    /**
     * Retrieve a BitPay payout recipient by batch id using.  The client must have been previously authorized for the
     * payout facade.
     *
     * @param recipientId String The id of the recipient to retrieve.
     * @return PayoutRecipient A BitPay PayoutRecipient object.
     * @throws BitPayException               BitPayException class
     * @throws PayoutRecipientQueryException PayoutRecipientQueryException class
     */
    public PayoutRecipient get(String recipientId)
        throws BitPayException, PayoutRecipientQueryException {
        if (Objects.isNull(recipientId)) {
            throw new PayoutRecipientQueryException(null, "missing required parameter");
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.PAYOUT));

        PayoutRecipient recipient;

        try {
            HttpResponse response = this.bitPayClient.get("recipients/" + recipientId, params, true);
            recipient =
                new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), PayoutRecipient.class);
        } catch (JsonProcessingException e) {
            throw new PayoutRecipientQueryException(null,
                "failed to deserialize BitPay server response (PayoutRecipient) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutRecipientQueryException(null,
                "failed to deserialize BitPay server response (PayoutRecipient) : " + e.getMessage());
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
     * @throws BitPayException                BitPayException class
     * @throws PayoutRecipientUpdateException PayoutRecipientUpdateException class
     */
    public PayoutRecipient update(String recipientId, PayoutRecipient recipient)
        throws BitPayException, PayoutRecipientUpdateException {
        if (Objects.isNull(recipient) || Objects.isNull(recipientId)) {
            throw new PayoutRecipientUpdateException(null, "missing required parameter");
        }
        recipient.setToken(this.accessTokens.getAccessToken(Facade.PAYOUT));
        recipient.setGuid(this.guidGenerator.execute());
        JsonMapper mapper = JsonMapperFactory.create();
        String json;

        try {
            json = mapper.writeValueAsString(recipient);
        } catch (JsonProcessingException e) {
            throw new PayoutRecipientUpdateException(null,
                "failed to serialize PayoutRecipient object : " + e.getMessage());
        }

        PayoutRecipient updateRecipient;

        try {
            HttpResponse response = this.bitPayClient.update("recipients/" + recipientId, json);
            updateRecipient =
                new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), PayoutRecipient.class);
        } catch (JsonProcessingException e) {
            throw new PayoutRecipientUpdateException(null,
                "failed to deserialize BitPay server response (PayoutRecipients) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutRecipientUpdateException(null,
                "failed to deserialize BitPay server response (PayoutRecipients) : " + e.getMessage());
        }

        return updateRecipient;
    }

    /**
     * Cancel a BitPay Payout recipient.
     *
     * @param recipientId String The id of the recipient to cancel.
     * @return True if the delete operation was successfull, false otherwise.
     * @throws BitPayException                      BitPayException class
     * @throws PayoutRecipientCancellationException PayoutRecipientCancellationException                                              class
     */
    public Boolean delete(String recipientId)
        throws BitPayException, PayoutRecipientCancellationException {
        if (Objects.isNull(recipientId)) {
            throw new PayoutRecipientCancellationException(null, "missing required parameter");
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.PAYOUT));

        JsonMapper mapper = JsonMapperFactory.create();
        Boolean result;

        try {
            HttpResponse response = this.bitPayClient.delete("recipients/" + recipientId, params);
            String jsonString = this.bitPayClient.responseToJsonString(response);
            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode node = rootNode.get("status");
            result = node.toString().replace("\"", "").toLowerCase(Locale.ROOT).equals("success");
        } catch (BitPayException ex) {
            throw new PayoutRecipientCancellationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new PayoutRecipientCancellationException(null,
                "failed to deserialize BitPay server response (PayoutRecipients) : " + e.getMessage());
        }

        return result;
    }

    /**
     * Request a payout recipient notification
     *
     * @param recipientId String A BitPay recipient ID.
     * @return True if the notification was successfully sent, false otherwise.
     * @throws PayoutRecipientNotificationException PayoutRecipientNotificationException                                              class
     * @throws BitPayException                      BitPayException class
     */
    public Boolean requestPayoutRecipientNotification(String recipientId)
        throws PayoutRecipientNotificationException, BitPayException {
        if (Objects.isNull(recipientId)) {
            throw new PayoutRecipientNotificationException(null, "missing required parameter");
        }

        final Map<String, String> params = new HashMap<>();
        params.put("token", this.accessTokens.getAccessToken(Facade.PAYOUT));

        JsonMapper mapper = JsonMapperFactory.create();
        Boolean result;
        String json;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new PayoutRecipientNotificationException(null,
                "failed to serialize PayoutRecipient object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.bitPayClient.post("recipients/" + recipientId + "/notifications", json, true);
            String jsonString = this.bitPayClient.responseToJsonString(response);
            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode node = rootNode.get("status");
            result = node.toString().replace("\"", "").toLowerCase(Locale.ROOT).equals("success");
        } catch (BitPayException ex) {
            throw new PayoutRecipientNotificationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new PayoutRecipientNotificationException(null,
                "failed to deserialize BitPay server response (PayoutRecipients) : " + e.getMessage());
        }

        return result;
    }
}
