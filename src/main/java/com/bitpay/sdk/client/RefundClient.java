/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.exceptions.RefundCancellationException;
import com.bitpay.sdk.exceptions.RefundCreationException;
import com.bitpay.sdk.exceptions.RefundQueryException;
import com.bitpay.sdk.exceptions.RefundUpdateException;
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
import org.apache.http.HttpResponse;
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
     * @throws BitPayException BitPayException
     */
    public Refund create(final Refund refund) throws BitPayException {
        if (Objects.isNull(refund)) {
            throw new RefundCreationException(null, "missing required parameter");
        }

        final Map<String, Object> params = createBasicParamsForCreate(refund);
        final Refund result;
        final JsonMapper mapper = JsonMapperFactory.create();
        final String json;

        try {
            json = mapper.writeValueAsString(params);
        } catch (final JsonProcessingException e) {
            throw new RefundCreationException(null, "failed to serialize Refund object : " + e.getMessage());
        }

        try {
            final HttpResponse response = this.bitPayClient.post("refunds/", json, true);
            result = JsonMapperFactory.create()
                .readValue(this.bitPayClient.responseToJsonString(response), Refund.class);
        } catch (final BitPayException ex) {
            throw new RefundCreationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (final Exception e) {
            throw new RefundCreationException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return result;
    }

    /**
     * Retrieve a previously made refund request on a BitPay invoice.
     *
     * @param refundId The BitPay refund ID.
     * @return A BitPay Refund object with the associated Refund object.
     * @throws RefundQueryException RefundQueryException class
     * @throws BitPayException      BitPayException class
     */
    public Refund getById(final String refundId) throws RefundQueryException, BitPayException {
        validateRefundId(refundId);

        final Refund refund;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        try {
            final HttpResponse response = this.bitPayClient.get("refunds/" + refundId, params, true);
            refund = JsonMapperFactory.create()
                .readValue(this.bitPayClient.responseToJsonString(response), Refund.class);
        } catch (final JsonProcessingException e) {
            throw new RefundQueryException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        } catch (final BitPayException ex) {
            throw new RefundQueryException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (final Exception e) {
            throw new RefundQueryException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refund;
    }

    /**
     * Retrieve refund request on a BitPay invoice by Guid.
     *
     * @param guid Guid
     * @return Refund
     * @throws BitPayException BitPayException
     */
    public Refund getByGuid(final String guid) throws BitPayException {
        validateRefundId(guid);

        final Refund refund;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        try {
            final HttpResponse response = this.bitPayClient.get("refunds/guid/" + guid, params, true);
            refund = JsonMapperFactory.create()
                .readValue(this.bitPayClient.responseToJsonString(response), Refund.class);
        } catch (final JsonProcessingException e) {
            throw new RefundQueryException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        } catch (final BitPayException ex) {
            throw new RefundQueryException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (final Exception e) {
            throw new RefundQueryException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refund;
    }

    /**
     * Retrieve all refund requests on a BitPay invoice.
     *
     * @param invoiceId The BitPay invoice object having the associated refunds.
     * @return A list of BitPay Refund objects with the associated Refund objects.
     * @throws RefundQueryException RefundQueryException class
     * @throws BitPayException      BitPayException class
     */
    public List<Refund> getRefundsByInvoiceId(final String invoiceId) throws RefundQueryException, BitPayException {
        validateRefundId(invoiceId);

        final List<Refund> refunds;
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        ParameterAdder.execute(params, "invoiceId", invoiceId);

        try {
            final HttpResponse response = this.bitPayClient.get("refunds/", params, true);
            refunds = Arrays.asList(
                JsonMapperFactory.create().readValue(this.bitPayClient.responseToJsonString(response), Refund[].class)
            );
        } catch (final JsonProcessingException e) {
            throw new RefundQueryException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        } catch (final BitPayException ex) {
            throw new RefundQueryException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (final Exception e) {
            throw new RefundQueryException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refunds;
    }

    /**
     * Update the status of a BitPay invoice.
     *
     * @param refundId A BitPay refund ID.
     * @param status   The new status for the refund to be updated.
     * @return A BitPay generated Refund object.
     * @throws RefundUpdateException RefundUpdateException class
     * @throws BitPayException       BitPayException class
     */
    public Refund update(
        final String refundId,
        final String status
    ) throws RefundUpdateException, BitPayException {
        if (Objects.isNull(refundId) || Objects.isNull(status)) {
            throw new RefundUpdateException(null,
                "Updating the refund requires a refund ID and a new status to be set.");
        }

        final String json = getUpdateRefundJson(status);
        final Refund refund;

        try {
            final HttpResponse response = this.bitPayClient.update("refunds/" + refundId, json);
            refund = JsonMapperFactory.create()
                .readValue(this.bitPayClient.responseToJsonString(response), Refund.class);
        } catch (final BitPayException ex) {
            throw new RefundUpdateException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (final Exception e) {
            throw new RefundUpdateException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refund;
    }

    /**
     * Update status of refund request from preview to created.
     *
     * @param guid A BitPay refund Guid.
     * @param status   The new status for the refund to be updated.
     * @return A BitPay generated Refund object.
     * @throws RefundUpdateException RefundUpdateException class
     * @throws BitPayException       BitPayException class
     * @since 8.7.0
     */
    public Refund updateByGuid(
        final String guid,
        final String status
    ) throws RefundUpdateException, BitPayException {
        if (Objects.isNull(guid) || Objects.isNull(status)) {
            throw new RefundUpdateException(null,
                "Updating the refund requires a refund ID and a new status to be set.");
        }

        final String json = getUpdateRefundJson(status);
        final Refund refund;

        try {
            final HttpResponse response = this.bitPayClient.update("refunds/guid/" + guid, json);
            refund = JsonMapperFactory.create()
                .readValue(this.bitPayClient.responseToJsonString(response), Refund.class);
        } catch (final BitPayException ex) {
            throw new RefundUpdateException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (final Exception e) {
            throw new RefundUpdateException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refund;
    }

    /**
     * Send a refund notification.
     *
     * @param refundId A BitPay refund ID.
     * @return An updated Refund Object
     * @throws RefundCreationException RefundCreationException class
     * @throws BitPayException         BitPayException class
     */
    public Boolean sendRefundNotification(final String refundId) throws RefundCreationException, BitPayException {
        if (Objects.isNull(refundId)) {
            throw new RefundCreationException(null, "missing required parameter");
        }

        final Map<String, String> params = new HashMap<>();
        params.put("token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        final JsonMapper mapper = JsonMapperFactory.create();
        final Boolean result;
        final String json;

        try {
            json = mapper.writeValueAsString(params);
        } catch (final JsonProcessingException e) {
            throw new RefundCreationException(null, "failed to serialize Refund object : " + e.getMessage());
        }

        try {
            final HttpResponse response = this.bitPayClient.post("refunds/" + refundId + "/notifications", json, true);
            final String jsonString = this.bitPayClient.responseToJsonString(response);
            final JsonNode rootNode = mapper.readTree(jsonString);
            final JsonNode node = rootNode.get("status");
            result = "success".equals(node.toString().replace("\"", "").toLowerCase(Locale.ROOT));
        } catch (final BitPayException ex) {
            throw new RefundCreationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (final Exception e) {
            throw new RefundCreationException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return result;
    }

    /**
     * Cancel a previously submitted refund request on a BitPay invoice.
     *
     * @param refundId The refund Id for the refund to be canceled.
     * @return An updated Refund Object.
     * @throws RefundCancellationException RefundCancellationException class
     * @throws BitPayException             BitPayException class
     */
    public Refund cancel(final String refundId) throws RefundCancellationException, BitPayException {
        if (Objects.isNull(refundId)) {
            throw new RefundCancellationException(null, "missing required parameter");
        }

        final Refund refund;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        try {
            final HttpResponse response = this.bitPayClient.delete("refunds/" + refundId, params);
            refund = JsonMapperFactory.create()
                .readValue(this.bitPayClient.responseToJsonString(response), Refund.class);
        } catch (final BitPayException ex) {
            throw new RefundCancellationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (final Exception e) {
            throw new RefundCancellationException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refund;
    }

    /**
     * Cancel a previously submitted refund request on a BitPay invoice.
     *
     * @param guid The refund Guid for the refund to be canceled.
     * @return An updated Refund Object.
     * @throws RefundCancellationException RefundCancellationException class
     * @throws BitPayException             BitPayException class
     * @since 8.7.0
     */
    public Refund cancelByGuid(final String guid) throws RefundCancellationException, BitPayException {
        if (Objects.isNull(guid)) {
            throw new RefundCancellationException(null, "missing required parameter");
        }

        final Refund refund;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        try {
            final HttpResponse response = this.bitPayClient.delete("refunds/guid/" + guid, params);
            refund = JsonMapperFactory.create()
                .readValue(this.bitPayClient.responseToJsonString(response), Refund.class);
        } catch (final BitPayException ex) {
            throw new RefundCancellationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (final Exception e) {
            throw new RefundCancellationException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refund;
    }

    private String getUpdateRefundJson(final String status) throws BitPayException {
        final Map<String, String> params = new HashMap<>();
        params.put("token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        if (Objects.nonNull(status)) {
            params.put("status", status);
        }

        final JsonMapper mapper = JsonMapperFactory.create();
        final String json;

        try {
            json = mapper.writeValueAsString(params);
        } catch (final JsonProcessingException e) {
            throw new RefundUpdateException(null, "failed to serialize object : " + e.getMessage());
        }

        return json;
    }

    private Map<String, Object> createBasicParamsForCreate(final Refund refund) throws BitPayException {
        final String guid = Objects.isNull(refund.getGuid()) ? this.guidGenerator.execute() : refund.getGuid();
        final String invoiceId = refund.getInvoice();
        final Double amount = refund.getAmount();
        final Boolean preview = refund.getPreview();
        final Boolean immediate = refund.getImmediate();
        final Boolean buyerPaysRefundFee = refund.getBuyerPaysRefundFee();
        final String reference = refund.getReference();

        if (invoiceId == null && amount == null) {
            throw new RefundCreationException(null, "Invoice ID, amount and currency are required to issue a refund.");
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

    private void validateRefundId(final String refundId) throws RefundQueryException {
        if (Objects.isNull(refundId)) {
            throw new RefundQueryException(null, "missing required parameter");
        }
    }
}
