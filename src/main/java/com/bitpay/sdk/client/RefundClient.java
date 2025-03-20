/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.exceptions.BitPayValidationException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.invoice.Refund;
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
 * The type Refund client.
 */
public class RefundClient implements ResourceClient {

    private static RefundClient instance;
    private final BitPayClient bitPayClient;
    private final TokenContainer accessTokens;
    private final GuidGenerator guidGenerator;

    /**
     * Instantiates a new Refund client.
     *
     * @param bitPayClient the bit pay client
     * @param accessTokens the access tokens
     * @param guidGenerator GUID generator
     */
    private RefundClient(
        final BitPayClient bitPayClient,
        final TokenContainer accessTokens,
        final GuidGenerator guidGenerator
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
     * @param guidGenerator Guid generator
     * @return RefundClient
     */
    public static RefundClient getInstance(
        final BitPayClient bitPayClient,
        final TokenContainer accessTokens,
        final GuidGenerator guidGenerator
    ) {
        if (Objects.isNull(instance)) {
            instance = new RefundClient(bitPayClient, accessTokens, guidGenerator);
        }

        return instance;
    }

    /**
     * Create a refund for a BitPay invoice.
     *
     * @param refund Refund request data
     * @return Refund
     * @throws BitPayApiException BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Refund create(final Refund refund) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(refund)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        final Map<String, Object> params = createBasicParamsForCreate(refund);
        final JsonMapper mapper = JsonMapperFactory.create();
        Refund result = null;
        String json = null;

        try {
            json = mapper.writeValueAsString(params);
        } catch (final JsonProcessingException e) {
            BitPayExceptionProvider.throwEncodeException(e.getMessage());
        }

        final HttpResponse response = this.bitPayClient.post("refunds/", json, true);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            result = JsonMapperFactory.create()
                .readValue(jsonResponse, Refund.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Refund", e.getMessage());
        }

        return result;
    }

    /**
     * Retrieve a previously made refund request on a BitPay invoice.
     *
     * @param refundId The BitPay refund ID.
     * @return A BitPay Refund object with the associated Refund object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException      BitPayApiException class
     */
    public Refund getById(final String refundId) throws BitPayApiException, BitPayGenericException {
        validateRefundId(refundId);

        Refund refund = null;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        final HttpResponse response = this.bitPayClient.get("refunds/" + refundId, params, true);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            refund = JsonMapperFactory.create()
                .readValue(jsonResponse, Refund.class);
        } catch (final JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Refund", e.getMessage());
        }

        return refund;
    }

    /**
     * Retrieve refund request on a BitPay invoice by Guid.
     *
     * @param guid Guid
     * @return Refund
     * @throws BitPayApiException BitPayException
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Refund getByGuid(final String guid) throws BitPayApiException, BitPayGenericException {
        validateRefundId(guid);

        Refund refund = null;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        final HttpResponse response = this.bitPayClient.get("refunds/guid/" + guid, params, true);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            refund = JsonMapperFactory.create()
                .readValue(jsonResponse, Refund.class);
        } catch (final JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Refund", e.getMessage());
        }

        return refund;
    }

    /**
     * Retrieve all refund requests on a BitPay invoice.
     *
     * @param invoiceId The BitPay invoice object having the associated refunds.
     * @return A list of BitPay Refund objects with the associated Refund objects.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException      BitPayApiException class
     */
    public List<Refund> getRefundsByInvoiceId(final String invoiceId)
        throws BitPayApiException, BitPayGenericException {
        validateRefundId(invoiceId);

        List<Refund> refunds = null;
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        ParameterAdder.execute(params, "invoiceId", invoiceId);

        HttpResponse response = this.bitPayClient.get("refunds/", params, true);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            refunds = Arrays.asList(
                JsonMapperFactory.create().readValue(jsonResponse, Refund[].class)
            );
        } catch (final JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Refund", e.getMessage());
        }

        return refunds;
    }

    /**
     * Update the status of a BitPay invoice.
     *
     * @param refundId A BitPay refund ID.
     * @param status   The new status for the refund to be updated.
     * @return A BitPay generated Refund object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException       BitPayApiException class
     */
    public Refund update(
        final String refundId,
        final String status
    ) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(refundId) || Objects.isNull(status)) {
            BitPayExceptionProvider.throwGenericExceptionWithMessage(
                "Updating the refund requires a refund ID and a new status to be set.");
        }

        final String json = getUpdateRefundJson(status);
        Refund refund = null;

        final HttpResponse response = this.bitPayClient.update("refunds/" + refundId, json);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            refund = JsonMapperFactory.create()
                .readValue(jsonResponse, Refund.class);
        } catch (final JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Refund", e.getMessage());
        }

        return refund;
    }

    /**
     * Update status of refund request from preview to created.
     *
     * @param guid A BitPay refund Guid.
     * @param status   The new status for the refund to be updated.
     * @return A BitPay generated Refund object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException       BitPayApiException class
     * @since 8.7.0
     */
    public Refund updateByGuid(
        final String guid,
        final String status
    ) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(guid) || Objects.isNull(status)) {
            BitPayExceptionProvider.throwGenericExceptionWithMessage(
                "Updating the refund requires a refund ID and a new status to be set.");
        }

        final String json = getUpdateRefundJson(status);
        Refund refund = null;

        HttpResponse response = this.bitPayClient.update("refunds/guid/" + guid, json);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            refund = JsonMapperFactory.create()
                .readValue(jsonResponse, Refund.class);
        } catch (final JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Refund", e.getMessage());
        }

        return refund;
    }

    /**
     * Send a refund notification.
     *
     * @param refundId    A BitPay refund ID.
     * @param refundToken The resource token for the refundId.
     *                    This token can be retrieved from the Bitpay's refund object.
     * @return An updated Refund Object
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException         BitPayApiException class
     */
    public Boolean sendRefundNotification(
        final String refundId,
        final String refundToken
    ) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(refundId)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        final Map<String, String> params = new HashMap<>();
        params.put("token", refundToken);

        final JsonMapper mapper = JsonMapperFactory.create();
        String json = null;
        Boolean result = null;

        try {
            json = mapper.writeValueAsString(params);
        } catch (final JsonProcessingException e) {
            BitPayExceptionProvider.throwEncodeException(e.getMessage());
        }

        HttpResponse response = this.bitPayClient.post("refunds/" + refundId + "/notifications", json, true);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            final JsonNode rootNode = mapper.readTree(jsonResponse);
            final JsonNode node = rootNode.get("status");
            result = "success".equals(node.toString().replace("\"", "").toLowerCase(Locale.ROOT));
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeException(e.getMessage());
        }

        return result;
    }

    /**
     * Cancel a previously submitted refund request on a BitPay invoice.
     *
     * @param refundId The refund Id for the refund to be canceled.
     * @return An updated Refund Object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException             BitPayApiException class
     */
    public Refund cancel(final String refundId) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(refundId)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        Refund refund = null;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        HttpResponse response = this.bitPayClient.delete("refunds/" + refundId, params);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            refund = JsonMapperFactory.create()
                .readValue(jsonResponse, Refund.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Refund", e.getMessage());
        }

        return refund;
    }

    /**
     * Cancel a previously submitted refund request on a BitPay invoice.
     *
     * @param guid The refund Guid for the refund to be canceled.
     * @return An updated Refund Object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException             BitPayApiException class
     * @since 8.7.0
     */
    public Refund cancelByGuid(final String guid) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(guid)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        Refund refund = null;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        HttpResponse response = this.bitPayClient.delete("refunds/guid/" + guid, params);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            refund = JsonMapperFactory.create()
                .readValue(jsonResponse, Refund.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Refund", e.getMessage());
        }

        return refund;
    }

    private String getUpdateRefundJson(final String status) throws BitPayGenericException {
        final Map<String, String> params = new HashMap<>();
        params.put("token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        if (Objects.nonNull(status)) {
            params.put("status", status);
        }

        final JsonMapper mapper = JsonMapperFactory.create();
        String json = null;

        try {
            json = mapper.writeValueAsString(params);
        } catch (final JsonProcessingException e) {
            BitPayExceptionProvider.throwEncodeException(e.getMessage());
        }

        return json;
    }

    private Map<String, Object> createBasicParamsForCreate(final Refund refund) throws BitPayGenericException {
        final String guid = Objects.isNull(refund.getGuid()) ? this.guidGenerator.execute() : refund.getGuid();
        final String invoiceId = refund.getInvoice();
        final Double amount = refund.getAmount();
        final Boolean preview = refund.getPreview();
        final Boolean immediate = refund.getImmediate();
        final Boolean buyerPaysRefundFee = refund.getBuyerPaysRefundFee();
        final String reference = refund.getReference();

        if (invoiceId == null && amount == null) {
            BitPayExceptionProvider.throwValidationException(
                "Invoice ID, amount and currency are required to issue a refund.");
        }

        final Map<String, Object> params = new HashMap<>();
        params.put("token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        if (invoiceId != null) {
            params.put("invoiceId", invoiceId);
        }
        if (amount != null) {
            params.put("amount", amount);
        }
        if (preview != null) {
            params.put("preview", preview);
        }
        if (immediate != null) {
            params.put("immediate", immediate);
        }
        if (buyerPaysRefundFee != null) {
            params.put("buyerPaysRefundFee", buyerPaysRefundFee);
        }
        if (reference != null) {
            params.put("reference", reference);
        }
        params.put("guid", guid);

        return params;
    }

    private void validateRefundId(final String refundId) throws BitPayValidationException {
        if (Objects.isNull(refundId)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }
    }
}
