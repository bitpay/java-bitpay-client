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
import com.bitpay.sdk.util.AccessTokenCache;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.bitpay.sdk.util.UuidGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;

public class InvoiceClient {

    private final BitPayClient bitPayClient;
    private AccessTokenCache accessTokenCache;
    private UuidGenerator uuidGenerator;

    public InvoiceClient(
        BitPayClient bitPayClient,
        AccessTokenCache accessTokenCache,
        UuidGenerator uuidGenerator
    ) {
        this.bitPayClient = bitPayClient;
        this.accessTokenCache = accessTokenCache;
        this.uuidGenerator = uuidGenerator;
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
    public Invoice createInvoice(Invoice invoice, String facade, Boolean signRequest, AccessTokenCache tokenCache) throws BitPayException,
        InvoiceCreationException {
        invoice.setToken(this.accessTokenCache.getAccessToken(facade));
        invoice.setGuid(this.uuidGenerator.execute());
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
            tokenCache.cacheToken(invoice.getId(), invoice.getToken());

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
    public Invoice getInvoice(String invoiceId, String facade, Boolean signRequest) throws BitPayException,
        InvoiceQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.accessTokenCache.getAccessToken(facade)));

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
     * @throws BitPayException BitPayException class
     * @throws InvoiceQueryException InvoiceQueryException class
     */
    public Invoice getInvoiceByGuid(String guid, String facade, Boolean signRequest) throws InvoiceQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        Invoice invoice;

        try {
            params.add(new BasicNameValuePair("token", this.accessTokenCache.getAccessToken(facade)));
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
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.accessTokenCache.getAccessToken(Facade.Merchant)));
        params.add(new BasicNameValuePair("dateStart", dateStart));
        params.add(new BasicNameValuePair("dateEnd", dateEnd));
        if (status != null) {
            params.add(new BasicNameValuePair("status", status));
        }
        if (orderId != null) {
            params.add(new BasicNameValuePair("orderId", orderId));
        }
        if (limit != null) {
            params.add(new BasicNameValuePair("limit", limit.toString()));
        }
        if (offset != null) {
            params.add(new BasicNameValuePair("offset", offset.toString()));
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
        final Map<String, Object> params = new HashMap<>();
        params.put("token", this.accessTokenCache.getAccessToken(Facade.Merchant));
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
        params.put("token", this.accessTokenCache.getAccessToken(Facade.Merchant));
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
        params.add(new BasicNameValuePair("token", this.accessTokenCache.getAccessToken(Facade.Merchant)));
        if (forceCancel) {
            params.add(new BasicNameValuePair("forceCancel", forceCancel.toString()));
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
}
