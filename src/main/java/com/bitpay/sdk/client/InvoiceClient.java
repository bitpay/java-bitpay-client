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
import com.bitpay.sdk.model.invoice.Invoice;
import com.bitpay.sdk.model.invoice.InvoiceEventToken;
import com.bitpay.sdk.util.GuidGenerator;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.bitpay.sdk.util.ParameterAdder;
import com.bitpay.sdk.util.TokenContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
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
 * The type Invoice client.
 */
public class InvoiceClient implements ResourceClient {

    private static InvoiceClient instance;
    private final BitPayClient bitPayClient;
    private final TokenContainer accessTokens;
    private final GuidGenerator guidGenerator;

    /**
     * Instantiates a new Invoice client.
     *
     * @param bitPayClient  the bit pay client
     * @param accessTokens  the access tokens
     * @param guidGenerator the Guid generator
     */
    private InvoiceClient(
        BitPayClient bitPayClient,
        TokenContainer accessTokens,
        GuidGenerator guidGenerator
    ) {
        this.bitPayClient = bitPayClient;
        this.accessTokens = accessTokens;
        this.guidGenerator = guidGenerator;
    }

    /**
     * Factory method for Invoice Client.
     *
     * @param bitPayClient BitPay Client
     * @param accessTokens Access Tokens
     * @param guidGenerator Guid Generator
     * @return InvoiceClient
     */
    public static InvoiceClient getInstance(
        BitPayClient bitPayClient,
        TokenContainer accessTokens,
        GuidGenerator guidGenerator
    ) {
        if (Objects.isNull(instance)) {
            instance = new InvoiceClient(bitPayClient, accessTokens, guidGenerator);
        }

        return instance;
    }

    /**
     * Create a BitPay invoice.
     *
     * @param invoice     An Invoice object with request parameters defined.
     * @param facade      The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay generated Invoice object.
     * @throws BitPayApiException BitPayApiException class
     * @throws BitPayValidationException BitPayValidationException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Invoice create(
        Invoice invoice,
        Facade facade,
        Boolean signRequest
    ) throws BitPayApiException, BitPayValidationException, BitPayGenericException {
        if (Objects.isNull(invoice) || Objects.isNull(facade)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        invoice.setToken(this.accessTokens.getAccessToken(facade));
        invoice.setGuid(Objects.isNull(invoice.getGuid()) ? this.guidGenerator.execute() : invoice.getGuid());
        JsonMapper mapper = JsonMapperFactory.create();
        String json = null;

        try {
            json = mapper.writeValueAsString(invoice);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwSerializeResourceException("Invoice", e.getMessage());
        }

        HttpResponse response = this.bitPayClient.post("invoices", json, signRequest);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            invoice = mapper.readerForUpdating(invoice).readValue(jsonResponse);
        } catch (Exception e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Invoice", e.getMessage());
        }

        this.accessTokens.put(invoice.getId(), invoice.getToken());

        return invoice;
    }

    /**
     * Retrieve a BitPay invoice by invoice id using the specified facade.
     * The client must have been previously authorized for the specified facade.
     *
     * @param invoiceId   The id of the invoice to retrieve.
     * @param facade      The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay Invoice object.
     * @throws BitPayApiException BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Invoice get(
        String invoiceId,
        Facade facade,
        Boolean signRequest
    ) throws BitPayApiException, BitPayGenericException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(facade));

        Invoice invoice = null;

        HttpResponse response = this.bitPayClient.get("invoices/" + invoiceId, params, signRequest);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            invoice = JsonMapperFactory.create().readValue(jsonResponse, Invoice.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Invoice", e.getMessage());
        }

        return invoice;
    }

    /**
     * Retrieve a BitPay invoice by guid using the specified facade.
     * The client must have been previously authorized for the specified facade.
     *
     * @param guid        The guid of the invoice to retrieve.
     * @param facade      The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay Invoice object.
     * @throws BitPayValidationException BitPayValidationException class
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     */
    public Invoice getByGuid(
        String guid,
        Facade facade,
        Boolean signRequest
    ) throws BitPayValidationException, BitPayGenericException, BitPayApiException {
        if (Objects.isNull(guid) || Objects.isNull(facade)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        Invoice invoice = null;

        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(facade));
        HttpResponse response = this.bitPayClient.get("invoices/guid/" + guid, params, signRequest);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            invoice = JsonMapperFactory.create().readValue(jsonResponse, Invoice.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Invoice", e.getMessage());
        }

        return invoice;
    }

    /**
     * Retrieve a collection of BitPay invoices.
     *
     * @param dateStart The first date for the query filter.
     * @param dateEnd   The last date for the query filter.
     * @param status    The invoice status you want to query on.
     * @param orderId   The optional order id specified at time of invoice creation.
     * @param limit     Maximum results that the query will return (useful for paging results).
     * @param offset    Number of results to offset (ex. skip 10 will give you results starting with the 11th
     * @return A list of BitPay Invoice objects.
     * @throws BitPayApiException       BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public List<Invoice> getInvoices(
        String dateStart,
        String dateEnd,
        String status,
        String orderId,
        Integer limit,
        Integer offset
    ) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(dateStart) || Objects.isNull(dateEnd)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        ParameterAdder.execute(params, "dateStart", dateStart);
        ParameterAdder.execute(params, "dateEnd", dateEnd);
        ParameterAdder.execute(params, "orderId", orderId);
        ParameterAdder.execute(params, "status", status);
        if (Objects.nonNull(limit)) {
            ParameterAdder.execute(params, "limit", limit.toString());
        }
        if (Objects.nonNull(offset)) {
            ParameterAdder.execute(params, "offset", offset.toString());
        }

        List<Invoice> invoices = null;
        HttpResponse response = this.bitPayClient.get("invoices", params);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            invoices = Arrays.asList(JsonMapperFactory.create().readValue(jsonResponse, Invoice[].class));
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Invoice", e.getMessage());
        }

        return invoices;
    }

    /**
     * Retrieves a bus token which can be used to subscribe to invoice events.
     *
     * @param invoiceId the id of the invoice for which you want to fetch an event token
     * @return InvoiceEventToken event token
     * @throws BitPayApiException BitPayException
     * @throws BitPayGenericException BitPayGenericException
     */
    public InvoiceEventToken getInvoiceEventToken(String invoiceId) throws BitPayApiException, BitPayGenericException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        HttpResponse response = this.bitPayClient.get("invoices/" + invoiceId + "/events", params);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());
        InvoiceEventToken result = null;

        try {
            result = JsonMapperFactory.create()
                .readValue(jsonResponse, InvoiceEventToken.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Invoice", e.getMessage());
        }

        return result;
    }

    /**
     * Update a BitPay invoice with communication method.
     *
     * @param invoiceId  The id of the invoice to updated.
     * @param buyerSms   The buyer's cell number.
     * @param smsCode    The buyer's received verification code.
     * @param buyerEmail The buyer's email address.
     * @param autoVerify Skip the user verification on sandbox ONLY.
     * @return A BitPay generated Invoice object.
     * @throws BitPayApiException        BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Invoice update(
        String invoiceId,
        String buyerSms,
        String smsCode,
        String buyerEmail,
        Boolean autoVerify
    ) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(invoiceId)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        final Map<String, Object> params = new HashMap<>();
        params.put("token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        validateRequiredField(buyerSms, buyerEmail);
        validateSmsCode(buyerSms, smsCode, autoVerify);

        if (buyerSms != null) {
            params.put("buyerSms", buyerSms);
        }
        if (smsCode != null) {
            params.put("smsCode", smsCode);
        }
        if (buyerEmail != null) {
            params.put("buyerEmail", buyerEmail);
        }
        if (autoVerify != null) {
            params.put("autoVerify", autoVerify);
        }

        JsonMapper mapper = JsonMapperFactory.create();
        String json = null;
        Invoice invoice = null;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwEncodeException(e.getMessage());
        }

        HttpResponse response = this.bitPayClient.update("invoices/" + invoiceId, json);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            invoice =
                JsonMapperFactory.create().readValue(jsonResponse, Invoice.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Invoice", e.getMessage());
        }

        return invoice;
    }

    /**
     * Pay a BitPay invoice with a mock transaction.
     *
     * @param invoiceId The id of the invoice to updated.
     * @param status    The status of the invoice to be updated, can be "confirmed" or "complete".
     * @return A BitPay generated Invoice object.
     * @throws BitPayApiException        BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Invoice pay(
        String invoiceId,
        String status
    ) throws BitPayApiException, BitPayGenericException {
        final Map<String, Object> params = new HashMap<>();
        params.put("token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        if (status != null) {
            params.put("status", status);
        }
        JsonMapper mapper = JsonMapperFactory.create();
        String json = null;
        Invoice invoice = null;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwEncodeException(e.getMessage());
        }

        HttpResponse response = this.bitPayClient.update("invoices/pay/" + invoiceId, json);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            invoice =
                JsonMapperFactory.create().readValue(jsonResponse, Invoice.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Invoice", e.getMessage());
        }

        return invoice;
    }

    /**
     * Delete a previously created BitPay invoice.
     *
     * @param invoiceId The Id of the BitPay invoice to be canceled.
     * @return A BitPay generated Invoice object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException              BitPayApiException class
     */
    public Invoice cancel(String invoiceId) throws BitPayApiException, BitPayGenericException {
        return this.cancel(invoiceId, false);
    }

    /**
     * Delete a previously created BitPay invoice.
     *
     * @param invoiceId   The Id of the BitPay invoice to be canceled.
     * @param forceCancel If 'true' it will cancel the invoice even if no contact information is present.
     * @return A BitPay generated Invoice object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayException class
     */
    public Invoice cancel(
        String invoiceId,
        Boolean forceCancel
    ) throws BitPayApiException, BitPayGenericException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        if (forceCancel) {
            ParameterAdder.execute(params, "forceCancel", forceCancel.toString());
        }
        Invoice invoice = null;

        HttpResponse response = this.bitPayClient.delete("invoices/" + invoiceId, params);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            invoice =
                JsonMapperFactory.create().readValue(jsonResponse, Invoice.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Invoice", e.getMessage());
        }

        return invoice;
    }

    /**
     * Cancel Invoice by Guid.
     *
     * @param guid Guid
     * @param forceCancel Force Cancel
     * @return Invoice
     * @throws BitPayApiException BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Invoice cancelByGuid(String guid, Boolean forceCancel) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(guid) || Objects.isNull(forceCancel)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        if (forceCancel.equals(true)) {
            ParameterAdder.execute(params, "forceCancel", forceCancel.toString());
        }
        Invoice invoice = null;

        HttpResponse response = this.bitPayClient.delete("invoices/guid/" + guid, params);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            invoice =
                JsonMapperFactory.create().readValue(jsonResponse, Invoice.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Invoice", e.getMessage());
        }

        return invoice;
    }

    /**
     * Request an Invoice Webhook to be Resent.
     *
     * @param invoiceId    Invoice ID
     * @param invoiceToken The resource token for the invoiceId.
     *                     This token can be retrieved from the Bitpay's invoice object.
     * @return Boolean
     * @throws BitPayApiException BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Boolean requestInvoiceWebhookToBeResent(
        String invoiceId,
        String invoiceToken
    ) throws BitPayApiException, BitPayGenericException {
        final Map<String, String> params = new HashMap<>();
        params.put("token", invoiceToken);

        JsonMapper mapper = JsonMapperFactory.create();
        String json = null;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwEncodeException(e.getMessage());
        }

        HttpResponse response = this.bitPayClient.post("invoices/" + invoiceId + "/notifications", json);
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        return jsonResponse.replace("\"", "").toLowerCase(Locale.ROOT).equals("success");
    }

    private void validateRequiredField(
        String buyerSms,
        String buyerEmail
    ) throws BitPayValidationException {
        if (buyerSms == null && buyerEmail == null) {
            BitPayExceptionProvider.throwValidationException(
                "Updating the invoice requires Mobile Phone Number for SMS reception.");
        }

        if (Objects.nonNull(buyerSms) && Objects.nonNull(buyerEmail)) {
            BitPayExceptionProvider.throwValidationException(
                "Updating an invoice will require EITHER an SMS or E-mail)");
        }
    }

    private void validateSmsCode(
        String buyerSms,
        String smsCode,
        Boolean autoVerify
    ) throws BitPayValidationException {
        if (Objects.isNull(autoVerify)) {
            return;
        }

        if (autoVerify) {
            return;
        }

        if (Objects.nonNull(buyerSms) && Objects.nonNull(smsCode)) {
            return;
        }

        if (Objects.isNull(buyerSms) && Objects.isNull(smsCode)) {
            return;
        }

        BitPayExceptionProvider.throwValidationException(
            "If provided alongside a valid SMS, will bypass the need to complete an SMS challenge");
    }
}
