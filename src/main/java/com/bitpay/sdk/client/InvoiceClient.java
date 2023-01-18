/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.exceptions.InvoiceCancellationException;
import com.bitpay.sdk.exceptions.InvoiceCreationException;
import com.bitpay.sdk.exceptions.InvoiceQueryException;
import com.bitpay.sdk.exceptions.InvoiceUpdateException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.Invoice.Invoice;
import com.bitpay.sdk.util.AccessTokens;
import com.bitpay.sdk.util.GuidGenerator;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.bitpay.sdk.util.ParameterAdder;
import com.fasterxml.jackson.core.JsonProcessingException;
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
 * The type Invoice client.
 */
public class InvoiceClient {

    private final BitPayClient bitPayClient;
    private AccessTokens accessTokens;
    private GuidGenerator guidGenerator;

    /**
     * Instantiates a new Invoice client.
     *
     * @param bitPayClient  the bit pay client
     * @param accessTokens  the access tokens
     * @param guidGenerator the Guid generator
     */
    public InvoiceClient(
        BitPayClient bitPayClient,
        AccessTokens accessTokens,
        GuidGenerator guidGenerator
    ) {
        this.bitPayClient = bitPayClient;
        this.accessTokens = accessTokens;
        this.guidGenerator = guidGenerator;
    }

    /**
     * Create a BitPay invoice.
     *
     * @param invoice     An Invoice object with request parameters defined.
     * @param facade      The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay generated Invoice object.
     * @throws BitPayException          BitPayException class
     * @throws InvoiceCreationException InvoiceCreationException class
     */
    public Invoice createInvoice(Invoice invoice, Facade facade, Boolean signRequest) throws BitPayException,
        InvoiceCreationException {
        if (Objects.isNull(invoice) || Objects.isNull(facade)) {
            throw new InvoiceCreationException(null, "missing required parameter");
        }

        invoice.setToken(this.accessTokens.getAccessToken(facade));
        invoice.setGuid(this.guidGenerator.execute());
        JsonMapper mapper = JsonMapperFactory.create();
        String json;

        try {
            json = mapper.writeValueAsString(invoice);
        } catch (JsonProcessingException e) {
            throw new InvoiceCreationException(null, "failed to serialize Invoice object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.bitPayClient.post("invoices", json, signRequest);
            invoice = mapper.readerForUpdating(invoice).readValue(this.bitPayClient.responseToJsonString(response));
            this.accessTokens.put(invoice.getId(), invoice.getToken());

        } catch (BitPayException ex) {
            throw new InvoiceCreationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceCreationException(null, "failed to deserialize BitPay server response (Invoice) : " + e.getMessage());
        }

        return invoice;
    }

    /**
     * Retrieve a BitPay invoice by invoice id using the specified facade.  The client must have been previously authorized for the specified facade.
     *
     * @param invoiceId   The id of the invoice to retrieve.
     * @param facade      The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay Invoice object.
     * @throws BitPayException       BitPayException class
     * @throws InvoiceQueryException InvoiceQueryException class
     */
    public Invoice getInvoice(String invoiceId, Facade facade, Boolean signRequest) throws BitPayException,
        InvoiceQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(facade));

        Invoice invoice;

        try {
            HttpResponse response = this.bitPayClient.get("invoices/" + invoiceId, params, signRequest);
            invoice = new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Invoice.class);
        } catch (BitPayException ex) {
            throw new InvoiceQueryException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (JsonProcessingException e) {
            throw new InvoiceQueryException(null, "failed to deserialize BitPay server response (Invoice) : " + e.getMessage());
        } catch (Exception e) {
            throw new InvoiceQueryException(null, "failed to deserialize BitPay server response (Invoice) : " + e.getMessage());
        }

        return invoice;
    }

    /**
     * Retrieve a BitPay invoice by guid using the specified facade.  The client must have been previously authorized for the specified facade.
     *
     * @param guid        The guid of the invoice to retrieve.
     * @param facade      The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay Invoice object.
     * @throws InvoiceQueryException InvoiceQueryException class
     */
    public Invoice getInvoiceByGuid(String guid, Facade facade, Boolean signRequest) throws InvoiceQueryException {
        if (Objects.isNull(guid) || Objects.isNull(facade)) {
            throw new InvoiceQueryException(null, "missing required parameters");
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        Invoice invoice;

        try {
            ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(facade));
            HttpResponse response = this.bitPayClient.get("invoices/guid/" + guid, params, signRequest);
            invoice = new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Invoice.class);
        } catch (BitPayException ex) {
            throw new InvoiceQueryException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (JsonProcessingException e) {
            throw new InvoiceQueryException(null, "failed to deserialize BitPay server response (Invoice) : " + e.getMessage());
        } catch (Exception e) {
            throw new InvoiceQueryException(null, "failed to deserialize BitPay server response (Invoice) : " + e.getMessage());
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
     * @throws BitPayException       BitPayException class
     * @throws InvoiceQueryException InvoiceQueryException class
     */
    public List<Invoice> getInvoices(String dateStart, String dateEnd, String status, String orderId, Integer limit, Integer offset) throws BitPayException, InvoiceQueryException {
        if (Objects.isNull(dateStart) || Objects.isNull(dateEnd)) {
            throw new InvoiceQueryException(null, "missing required parameter");
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        ParameterAdder.execute(params,"dateStart", dateStart);
        ParameterAdder.execute(params,"dateEnd", dateEnd);
        ParameterAdder.execute(params,"orderId", orderId);
        ParameterAdder.execute(params,"status", status);
        if (Objects.nonNull(limit)) {
            ParameterAdder.execute(params, "limit", limit.toString());
        }
        if (Objects.nonNull(offset)) {
            ParameterAdder.execute(params, "offset", offset.toString());
        }

        List<Invoice> invoices;

        try {
            HttpResponse response = this.bitPayClient.get("invoices", params);
            invoices = Arrays.asList(new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Invoice[].class));
        } catch (BitPayException ex) {
            throw new InvoiceQueryException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (JsonProcessingException e) {
            throw new InvoiceQueryException(null, "failed to deserialize BitPay server response (Invoices) : " + e.getMessage());
        } catch (Exception e) {
            throw new InvoiceQueryException(null, "failed to deserialize BitPay server response (Invoices) : " + e.getMessage());
        }

        return invoices;
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
     * @throws BitPayException        BitPayException class
     * @throws InvoiceUpdateException InvoiceUpdateException class
     */
    public Invoice updateInvoice(
        String invoiceId,
        String buyerSms,
        String smsCode,
        String buyerEmail,
        Boolean autoVerify
    ) throws BitPayException, InvoiceUpdateException {
        if (Objects.isNull(invoiceId)) {
            throw new InvoiceUpdateException(null, "missing required parameter");
        }

        final Map<String, Object> params = new HashMap<>();
        params.put("token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        if (buyerSms == null && smsCode == null) {
            throw new InvoiceUpdateException(null,
                "Updating the invoice requires Mobile Phone Number for SMS reception.");
        }
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
        String json;
        Invoice invoice;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new InvoiceUpdateException(null, "failed to serialize object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.bitPayClient.update("invoices/" + invoiceId, json);
            invoice = new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Invoice.class);
        } catch (BitPayException ex) {
            throw new InvoiceUpdateException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceUpdateException(null,
                "failed to deserialize BitPay server response (Invoice) : " + e.getMessage());
        }

        return invoice;
    }

    /**
     * Pay a BitPay invoice with a mock transaction.
     *
     * @param invoiceId The id of the invoice to updated.
     * @param status    The status of the invoice to be updated, can be "confirmed" or "complete".
     * @return A BitPay generated Invoice object.
     * @throws BitPayException        BitPayException class
     * @throws InvoiceUpdateException InvoiceUpdateException class
     */
    public Invoice payInvoice(String invoiceId, String status) throws BitPayException, InvoiceUpdateException {
        final Map<String, Object> params = new HashMap<>();
        params.put("token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        if (status != null) {
            params.put("status", status);
        }
        JsonMapper mapper = JsonMapperFactory.create();
        String json;
        Invoice invoice;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new InvoiceUpdateException(null, "failed to serialize object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.bitPayClient.update("invoices/pay/" + invoiceId, json);
            invoice = JsonMapperFactory.create().readValue(this.bitPayClient.responseToJsonString(response), Invoice.class);
        } catch (BitPayException ex) {
            throw new InvoiceUpdateException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceUpdateException(null,
                "failed to deserialize BitPay server response (Invoice) : " + e.getMessage());
        }

        return invoice;
    }

    /**
     * Delete a previously created BitPay invoice.
     *
     * @param invoiceId The Id of the BitPay invoice to be canceled.
     * @return A BitPay generated Invoice object.
     * @throws InvoiceCancellationException InvoiceCancellationException class
     * @throws BitPayException              BitPayException class
     */
    public Invoice cancelInvoice(String invoiceId) throws InvoiceCancellationException, BitPayException {
        try {
            return this.cancelInvoice(invoiceId, false);
        } catch (BitPayException ex) {
            throw new InvoiceCancellationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceCancellationException(null, e.getMessage());
        }
    }

    /**
     * Delete a previously created BitPay invoice.
     *
     * @param invoiceId   The Id of the BitPay invoice to be canceled.
     * @param forceCancel If 'true' it will cancel the invoice even if no contact information is present.
     * @return A BitPay generated Invoice object.
     * @throws InvoiceCancellationException InvoiceCancellationException class
     * @throws BitPayException              BitPayException class
     */
    public Invoice cancelInvoice(String invoiceId, Boolean forceCancel)
        throws InvoiceCancellationException, BitPayException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params,"token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        if (forceCancel) {
            ParameterAdder.execute(params,"forceCancel", forceCancel.toString());
        }
        Invoice invoice;

        try {
            HttpResponse response = this.bitPayClient.delete("invoices/" + invoiceId, params);
            invoice = new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Invoice.class);
        } catch (BitPayException ex) {
            throw new InvoiceCancellationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceCancellationException(null,
                "failed to deserialize BitPay server response (Invoice) : " + e.getMessage());
        }

        return invoice;
    }

    public Invoice cancelInvoiceByGuid(String guid, Boolean forceCancel) throws BitPayException {
        if (Objects.isNull(guid) || Objects.isNull(forceCancel)) {
            throw new InvoiceCancellationException(null, "missing required parameter");
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params,"token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        if (forceCancel.equals(true)) {
            ParameterAdder.execute(params,"forceCancel", forceCancel.toString());
        }
        Invoice invoice;

        try {
            HttpResponse response = this.bitPayClient.delete("invoices/guid/" + guid, params);
            invoice = new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Invoice.class);
        } catch (BitPayException ex) {
            throw new InvoiceCancellationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceCancellationException(null,
                "failed to deserialize BitPay server response (Invoice) : " + e.getMessage());
        }

        return invoice;
    }

    public Boolean requestInvoiceWebhookToBeResent(String invoiceId) throws BitPayException {
        final Map<String, String> params = new HashMap<>();
        params.put("token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        JsonMapper mapper = JsonMapperFactory.create();
        String json;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new InvoiceUpdateException(null, "failed to serialize object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.bitPayClient.post("invoices/" + invoiceId + "/notifications", json);
            String jsonString = this.bitPayClient.responseToJsonString(response);
            return jsonString.replace("\"", "").toLowerCase(Locale.ROOT).equals("success");
        } catch (Exception e) {
            throw new BitPayException(null, "failed to process request : " + e.getMessage());
        }
    }
}
