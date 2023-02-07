/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.exceptions.RefundCancellationException;
import com.bitpay.sdk.exceptions.RefundCreationException;
import com.bitpay.sdk.exceptions.RefundQueryException;
import com.bitpay.sdk.exceptions.RefundUpdateException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.Invoice.Refund;
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
 * The type Refund client.
 */
public class RefundClient {

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
    public RefundClient(BitPayClient bitPayClient, TokenContainer accessTokens, GuidGenerator guidGenerator) {
        this.bitPayClient = bitPayClient;
        this.accessTokens = accessTokens;
        this.guidGenerator = guidGenerator;
    }

    /**
     * Create a refund for a BitPay invoice.
     *
     * @param refund Refund request data
     * @return Refund
     * @throws BitPayException BitPayException
     */
    public Refund create(Refund refund) throws BitPayException {
        if (Objects.isNull(refund)) {
            throw new RefundCreationException(null, "missing required parameter");
        }

        final Map<String, Object> params = createBasicParamsForCreate(refund);
        Refund result;
        JsonMapper mapper = JsonMapperFactory.create();
        String json;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new RefundCreationException(null, "failed to serialize Refund object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.bitPayClient.post("refunds/", json, true);
            result = new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Refund.class);
        } catch (BitPayException ex) {
            throw new RefundCreationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
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
    public Refund getById(String refundId) throws RefundQueryException, BitPayException {
        if (Objects.isNull(refundId)) {
            throw new RefundQueryException(null, "missing required parameter");
        }

        Refund refund;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params,"token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        try {
            HttpResponse response = this.bitPayClient.get("refunds/" + refundId, params, true);
            refund = new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Refund.class);
        } catch (JsonProcessingException e) {
            throw new RefundQueryException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        } catch (BitPayException ex) {
            throw new RefundQueryException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new RefundQueryException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refund;
    }

    /**
     * Retrieve refund request on a BitPay invoice by Guid.
     * @param guid Guid
     * @return Refund
     * @throws BitPayException BitPayException
     */
    public Refund getByGuid(String guid) throws BitPayException {
        if (Objects.isNull(guid)) {
            throw new RefundQueryException(null, "missing required parameter");
        }

        Refund refund;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params,"token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        try {
            HttpResponse response = this.bitPayClient.get("refunds/guid/" + guid, params, true);
            refund = new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Refund.class);
        } catch (JsonProcessingException e) {
            throw new RefundQueryException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        } catch (BitPayException ex) {
            throw new RefundQueryException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
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
    public List<Refund> getRefundsByInvoiceId(String invoiceId) throws RefundQueryException, BitPayException {
        if (Objects.isNull(invoiceId)) {
            throw new RefundQueryException(null, "missing required parameter");
        }

        List<Refund> refunds;
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        ParameterAdder.execute(params, "invoiceId", invoiceId);

        try {
            HttpResponse response = this.bitPayClient.get("refunds/", params, true);
            refunds = Arrays.asList(
                JsonMapperFactory.create().readValue(this.bitPayClient.responseToJsonString(response), Refund[].class)
            );
        } catch (JsonProcessingException e) {
            throw new RefundQueryException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        } catch (BitPayException ex) {
            throw new RefundQueryException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
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
    public Refund update(String refundId, String status) throws RefundUpdateException, BitPayException {
        if (Objects.isNull(refundId) || Objects.isNull(status)) {
            throw new RefundUpdateException(null,
                "Updating the refund requires a refund ID and a new status to be set.");
        }

        String json = getUpdateRefundJson(status);
        Refund refund;

        try {
            HttpResponse response = this.bitPayClient.update("refunds/" + refundId, json);
            refund = new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Refund.class);
        } catch (BitPayException ex) {
            throw new RefundUpdateException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
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
    public Refund updateByGuid(String guid, String status) throws RefundUpdateException, BitPayException {
        if (Objects.isNull(guid) || Objects.isNull(status)) {
            throw new RefundUpdateException(null,
                "Updating the refund requires a refund ID and a new status to be set.");
        }

        String json = getUpdateRefundJson(status);
        Refund refund;

        try {
            HttpResponse response = this.bitPayClient.update("refunds/guid/" + guid, json);
            refund = new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Refund.class);
        } catch (BitPayException ex) {
            throw new RefundUpdateException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
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
    public Boolean sendRefundNotification(String refundId) throws RefundCreationException, BitPayException {
        if (Objects.isNull(refundId)) {
            throw new RefundCreationException(null, "missing required parameter");
        }

        final Map<String, String> params = new HashMap<>();
        params.put("token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        JsonMapper mapper = JsonMapperFactory.create();
        Boolean result;
        String json;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new RefundCreationException(null, "failed to serialize Refund object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.bitPayClient.post("refunds/" + refundId + "/notifications", json, true);
            String jsonString = this.bitPayClient.responseToJsonString(response);
            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode node = rootNode.get("status");
            result = node.toString().replace("\"", "").toLowerCase(Locale.ROOT).equals("success");
        } catch (BitPayException ex) {
            throw new RefundCreationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
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
    public Refund cancel(String refundId) throws RefundCancellationException, BitPayException {
        if (Objects.isNull(refundId)) {
            throw new RefundCancellationException(null, "missing required parameter");
        }

        Refund refund;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        try {
            HttpResponse response = this.bitPayClient.delete("refunds/" + refundId, params);
            refund = new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Refund.class);
        } catch (BitPayException ex) {
            throw new RefundCancellationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
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
    public Refund cancelByGuid(String guid) throws RefundCancellationException, BitPayException {
        if (Objects.isNull(guid)) {
            throw new RefundCancellationException(null, "missing required parameter");
        }

        Refund refund;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        try {
            HttpResponse response = this.bitPayClient.delete("refunds/guid/" + guid, params);
            refund = new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Refund.class);
        } catch (BitPayException ex) {
            throw new RefundCancellationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new RefundCancellationException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refund;
    }

    private String getUpdateRefundJson(String status) throws BitPayException {
        final Map<String, String> params = new HashMap<>();
        params.put("token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        if (Objects.nonNull(status)) {
            params.put("status", status);
        }

        JsonMapper mapper = JsonMapperFactory.create();
        String json;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new RefundUpdateException(null, "failed to serialize object : " + e.getMessage());
        }

        return json;
    }

    private Map<String, Object> createBasicParamsForCreate(Refund refund) throws BitPayException {
        String guid = Objects.isNull(refund.getGuid()) ? this.guidGenerator.execute() : refund.getGuid();
        String invoiceId = refund.getInvoice();
        Double amount = refund.getAmount();
        Boolean preview = refund.getPreview();
        Boolean immediate = refund.getImmediate();
        Boolean buyerPaysRefundFee = refund.getBuyerPaysRefundFee();
        String reference = refund.getReference();

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
}
