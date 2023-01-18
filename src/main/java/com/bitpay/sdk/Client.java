/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk;

import com.bitpay.sdk.exceptions.BillCreationException;
import com.bitpay.sdk.exceptions.BillDeliveryException;
import com.bitpay.sdk.exceptions.BillQueryException;
import com.bitpay.sdk.exceptions.BillUpdateException;
import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.exceptions.InvoiceCancellationException;
import com.bitpay.sdk.exceptions.InvoiceCreationException;
import com.bitpay.sdk.exceptions.InvoiceQueryException;
import com.bitpay.sdk.exceptions.InvoiceUpdateException;
import com.bitpay.sdk.exceptions.LedgerQueryException;
import com.bitpay.sdk.exceptions.PayoutBatchCancellationException;
import com.bitpay.sdk.exceptions.PayoutBatchCreationException;
import com.bitpay.sdk.exceptions.PayoutBatchNotificationException;
import com.bitpay.sdk.exceptions.PayoutBatchQueryException;
import com.bitpay.sdk.exceptions.PayoutCancellationException;
import com.bitpay.sdk.exceptions.PayoutCreationException;
import com.bitpay.sdk.exceptions.PayoutNotificationException;
import com.bitpay.sdk.exceptions.PayoutQueryException;
import com.bitpay.sdk.exceptions.PayoutRecipientCancellationException;
import com.bitpay.sdk.exceptions.PayoutRecipientCreationException;
import com.bitpay.sdk.exceptions.PayoutRecipientNotificationException;
import com.bitpay.sdk.exceptions.PayoutRecipientQueryException;
import com.bitpay.sdk.exceptions.PayoutRecipientUpdateException;
import com.bitpay.sdk.exceptions.RateQueryException;
import com.bitpay.sdk.exceptions.RefundCancellationException;
import com.bitpay.sdk.exceptions.RefundCreationException;
import com.bitpay.sdk.exceptions.RefundQueryException;
import com.bitpay.sdk.exceptions.RefundUpdateException;
import com.bitpay.sdk.exceptions.SettlementQueryException;
import com.bitpay.sdk.exceptions.WalletQueryException;
import com.bitpay.sdk.model.Bill.Bill;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.Invoice.Invoice;
import com.bitpay.sdk.model.Invoice.InvoiceEventToken;
import com.bitpay.sdk.model.Invoice.Refund;
import com.bitpay.sdk.model.Ledger.Ledger;
import com.bitpay.sdk.model.Ledger.LedgerEntry;
import com.bitpay.sdk.model.Payout.Payout;
import com.bitpay.sdk.model.Payout.PayoutBatch;
import com.bitpay.sdk.model.Payout.PayoutRecipient;
import com.bitpay.sdk.model.Payout.PayoutRecipients;
import com.bitpay.sdk.model.Rate.Rate;
import com.bitpay.sdk.model.Rate.Rates;
import com.bitpay.sdk.model.Settlement.Settlement;
import com.bitpay.sdk.model.Token;
import com.bitpay.sdk.model.Wallet.Wallet;
import com.bitpay.sdk.util.BitPayLogger;
import com.bitpay.sdk.util.KeyUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bitcoinj.core.ECKey;

/**
 * The type Client. Class is responsible for API calls.
 *
 * @version 8.7.0
 * @see <a href="https://bitpay.com/api/#rest-api">REST API</a>
 *
 */

public class Client {

    private static BitPayLogger _log = new BitPayLogger(BitPayLogger.OFF);
    private Config _configuration;
    private String _env;
    private Hashtable<String, String> _tokenCache; // {facade, token}
    private String _configFilePath;
    private String _baseUrl;
    private ECKey _ecKey;
    private HttpClient _httpClient = null;
    public static ArrayList<Object> _currenciesInfo = null;

    /**
     * Return the identity of this client (i.e. the public key).
     */
    private String _identity;

    /**
     * Constructor for use if the keys and SIN are managed by this library.
     *
     * @param environment       Target environment. Options: Env.Test / Env.Prod
     * @param privateKey        The full path to the securely located private key or the HEX key value.
     * @param tokens            Env.Tokens containing the available tokens.
     * @param proxy             HttpHost Optional Proxy setting (set to NULL to ignore)
     * @param proxyCredentials  CredentialsProvider Optional Proxy Basic Auth Credentials (set to NULL to ignore)
     * @throws BitPayException  BitPayException class
     */
    public Client(String environment, String privateKey, Env.Tokens tokens, HttpHost proxy, CredentialsProvider proxyCredentials) throws BitPayException {
        try {
            this._env = environment;
            this.BuildConfig(privateKey, tokens);
            this.initKeys();
            this.init(proxy, proxyCredentials);
        } catch (JsonProcessingException e) {
            throw new BitPayException(null, "failed to deserialize BitPay server response (Config) : " + e.getMessage());
        } catch (URISyntaxException e) {
            throw new BitPayException(null, "failed to deserialize BitPay server response (Config) : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null, "failed to deserialize BitPay server response (Config) : " + e.getMessage());
        }
    }

    /**
     * Constructor for use if the keys and SIN are managed by this library.
     *
     * @param configFilePath The path to the configuration file.
     * @param proxy          HttpHost Optional Proxy setting (set to NULL to ignore)
     * @param proxyCredentials  CredentialsProvider Optional Proxy Basic Auth Credentials (set to NULL to ignore)
     * @throws BitPayException BitPayException class
     */
    public Client(String configFilePath, HttpHost proxy, CredentialsProvider proxyCredentials) throws BitPayException {
        try {
            this._configFilePath = configFilePath;
            this.GetConfig();
            this.initKeys();
            this.init(proxy, proxyCredentials);
        } catch (JsonProcessingException e) {
            throw new BitPayException(null, "failed to deserialize BitPay server response (Config) : " + e.getMessage());
        } catch (URISyntaxException e) {
            throw new BitPayException(null, "failed to deserialize BitPay server response (Config) : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null, "failed to deserialize BitPay server response (Config) : " + e.getMessage());
        }
    }

    /**
     * Authorize (pair) this client with the server using the specified pairing code.
     *
     * @param pairingCode A code obtained from the server; typically from bitpay.com/api-tokens.
     * @throws BitPayException BitPayException class
     */
    public void authorizeClient(String pairingCode) throws BitPayException {
        Token token = new Token();
        token.setId(_identity);
        token.setGuid(this.getGuid());
        token.setPairingCode(pairingCode);

        ObjectMapper mapper = new ObjectMapper();

        String json;

        try {
            json = mapper.writeValueAsString(token);
        } catch (JsonProcessingException e) {
            throw new BitPayException(null, "failed to serialize Token object : " + e.getMessage());
        }

        HttpResponse response = this.post("tokens", json);

        List<Token> tokens;

        try {
            tokens = Arrays.asList(mapper.readValue(this.responseToJsonString(response), Token[].class));
        } catch (JsonProcessingException e) {
            throw new BitPayException(null, "failed to deserialize BitPay server response (Tokens) : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null, "failed to deserialize BitPay server response (Tokens) : " + e.getMessage());
        }

        for (Token t : tokens) {
            _tokenCache.put(t.getFacade(), t.getValue());
        }
    }

    /**
     * Request a pairing code from the BitPay server.
     *
     * @param facade Defines the level of API access being requested
     * @return A pairing code for claim at https://bitpay.com/dashboard/merchant/api-tokens.
     * @throws BitPayException BitPayException class
     */
    public String requestClientAuthorization(String facade) throws BitPayException {
        Token token = new Token();
        token.setId(_identity);
        token.setGuid(this.getGuid());
        token.setFacade(facade);
        token.setCount(1);

        ObjectMapper mapper = new ObjectMapper();

        String json;

        try {
            json = mapper.writeValueAsString(token);
        } catch (JsonProcessingException e) {
            throw new BitPayException(null, "failed to serialize Token object : " + e.getMessage());
        }

        HttpResponse response = this.post("tokens", json);

        List<Token> tokens;

        try {
            tokens = Arrays.asList(mapper.readValue(this.responseToJsonString(response), Token[].class));

            // Expecting a single token resource.
            if (tokens.size() != 1) {
                throw new BitPayException(null, "failed to get token resource; expected 1 token, got " + tokens.size());
            }

        } catch (JsonProcessingException e) {
            throw new BitPayException(null, "failed to deserialize BitPay server response (Tokens) : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null, "failed to deserialize BitPay server response (Tokens) : " + e.getMessage());
        }

        _tokenCache.put(tokens.get(0).getFacade(), tokens.get(0).getValue());

        return tokens.get(0).getPairingCode();
    }

    /**
     * Specified whether the client has authorization (a token) for the specified facade.
     *
     * @param facade The facade name for which authorization is tested.
     * @return True if this client is authorized, false otherwise.
     */
    public boolean tokenExist(String facade) {
        return _tokenCache.containsKey(facade);
    }

    /**
     * Returns the token for the specified facade.
     *
     * @param facade The facade name for which the token is requested.
     * @return The token for the given facade.
     */
    public String GetTokenByFacade(String facade) {
        if (!_tokenCache.containsKey(facade))
            return "";

        return _tokenCache.get(facade);
    }

    /**
     * Create a BitPay invoice using the Merchant facade.
     *
     * @param invoice An Invoice object with request parameters defined.
     * @return A BitPay generated Invoice object.
     * @throws InvoiceCreationException InvoiceCreationException class
     */
    public Invoice createInvoice(Invoice invoice) throws InvoiceCreationException {
        try {
            return this.createInvoice(invoice, Facade.Merchant, true);
        } catch (BitPayException ex) {
            throw new InvoiceCreationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceCreationException(null, e.getMessage());
        }
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
    public Invoice createInvoice(Invoice invoice, String facade, Boolean signRequest) throws BitPayException, InvoiceCreationException {
        invoice.setToken(this.getAccessToken(facade));
        invoice.setGuid(this.getGuid());
        ObjectMapper mapper = new ObjectMapper();
        String json;

        try {
            json = mapper.writeValueAsString(invoice);
        } catch (JsonProcessingException e) {
            throw new InvoiceCreationException(null, "failed to serialize Invoice object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.post("invoices", json, signRequest);
            invoice = mapper.readerForUpdating(invoice).readValue(this.responseToJsonString(response));
        } catch (BitPayException ex) {
            throw new InvoiceCreationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceCreationException(null, "failed to deserialize BitPay server response (Invoice) : " + e.getMessage());
        }

        this.cacheToken(invoice.getId(), invoice.getToken());
        return invoice;
    }

    /**
     * Retrieve a BitPay invoice by invoice id using the public facade.
     *
     * @param invoiceId The id of the invoice to retrieve.
     * @return A BitPay Invoice object.
     * @throws InvoiceQueryException InvoiceQueryException class
     */
    public Invoice getInvoice(String invoiceId) throws InvoiceQueryException {
        try {
            return this.getInvoice(invoiceId, Facade.Merchant, true);
        } catch (BitPayException ex) {
            throw new InvoiceQueryException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceQueryException(null, e.getMessage());
        }
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
    public Invoice getInvoice(String invoiceId, String facade, Boolean signRequest) throws BitPayException, InvoiceQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(facade)));

        Invoice invoice;

        try {
            HttpResponse response = this.get("invoices/" + invoiceId, params, signRequest);
            invoice = new ObjectMapper().readValue(this.responseToJsonString(response), Invoice.class);
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
     * @throws BitPayException       BitPayException class
     * @throws InvoiceQueryException InvoiceQueryException class
     */
    public Invoice getInvoiceByGuid(String guid, String facade, Boolean signRequest) throws BitPayException, InvoiceQueryException {
    	final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(facade)));
        Invoice invoice;

        try {
            HttpResponse response = this.get("invoices/guid/" + guid, params, signRequest);
            invoice = new ObjectMapper().readValue(this.responseToJsonString(response), Invoice.class);
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
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));
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
            HttpResponse response = this.get("invoices", params);
            invoices = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), Invoice[].class));
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
     * Retrieves a bus token which can be used to subscribe to invoice events.
     *
     * @param invoiceId the id of the invoice for which you want to fetch an event token
     * @return InvoiceEventToken event token
     * @throws BitPayException BitPayException
     * @since 8.8.0
     */
    public InvoiceEventToken getInvoiceEventToken(String invoiceId) throws BitPayException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));
        ObjectMapper mapper = JsonMapper
            .builder()
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .build();

        try {
            HttpResponse response = this.get("invoices/" + invoiceId + "/events", params);
            return mapper.readValue(this.responseToJsonString(response), InvoiceEventToken.class);
        } catch (BitPayException ex) {
            throw new InvoiceQueryException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (JsonProcessingException e) {
            throw new InvoiceQueryException(null, "failed to deserialize BitPay server response (Invoices) : " + e.getMessage());
        } catch (Exception e) {
            throw new InvoiceQueryException(null, "failed to deserialize BitPay server response (Invoices) : " + e.getMessage());
        }
    }

    /**
     * Update a BitPay invoice with communication method.
     *
     * @param invoiceId The id of the invoice to updated.
     * @param buyerSms  The buyer's cell number.
     * @param smsCode   The buyer's received verification code.
     * @param buyerEmail   The buyer's email address.
     * @param autoVerify   Skip the user verification on sandbox ONLY.
     * @return A BitPay generated Invoice object.
     * @throws BitPayException        BitPayException class
     * @throws InvoiceUpdateException InvoiceUpdateException class
     */
    public Invoice updateInvoice(String invoiceId, String buyerSms, String smsCode, String buyerEmail, Boolean autoVerify) throws BitPayException, InvoiceUpdateException {
        final Map<String, Object> params = new HashMap<>();
        params.put("token", this.getAccessToken(Facade.Merchant));
        if (buyerSms == null && smsCode == null) {
            throw new InvoiceUpdateException(null, "Updating the invoice requires Mobile Phone Number for SMS reception.");
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

        ObjectMapper mapper = new ObjectMapper();
        String json;
        Invoice invoice;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new InvoiceUpdateException(null, "failed to serialize object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.update("invoices/" + invoiceId, json);
            invoice = new ObjectMapper().readValue(this.responseToJsonString(response), Invoice.class);
        } catch (BitPayException ex) {
            throw new InvoiceUpdateException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceUpdateException(null, "failed to deserialize BitPay server response (Invoice) : " + e.getMessage());
        }

        return invoice;
    }

    /**
     * Pay a BitPay invoice with a mock transaction.
     *
     * @param invoiceId The id of the invoice to updated.
     * @param status  The status of the invoice to be updated, can be "confirmed" or "complete".
     * @return A BitPay generated Invoice object.
     * @throws BitPayException        BitPayException class
     * @throws InvoiceUpdateException InvoiceUpdateException class
     */
    public Invoice payInvoice(String invoiceId, String status) throws BitPayException, InvoiceUpdateException {
        final Map<String, Object> params = new HashMap<>();
        params.put("token", this.getAccessToken(Facade.Merchant));
        if (status != null) {
        	params.put("status", status);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json;
        Invoice invoice;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new InvoiceUpdateException(null, "failed to serialize object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.update("invoices/pay/" + invoiceId, json);
            invoice = new ObjectMapper().readValue(this.responseToJsonString(response), Invoice.class);
        } catch (BitPayException ex) {
            throw new InvoiceUpdateException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceUpdateException(null, "failed to deserialize BitPay server response (Invoice) : " + e.getMessage());
        }

        return invoice;
    }

    /**
     * Delete a previously created BitPay invoice.
     *
     * @param invoiceId The Id of the BitPay invoice to be canceled.
     * @return A BitPay generated Invoice object.
     * @throws InvoiceCancellationException InvoiceCancellationException class
     * @throws BitPayException       BitPayException class
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
     * Cancellation will require EITHER an SMS or E-mail to have already been set if the invoice has proceeded to
     * the point where it may have been paid, unless using forceCancel parameter.
     *
     * @param invoiceId The Id of the BitPay invoice to be canceled.
     * @param forceCancel If 'true' it will cancel the invoice even if no contact information is present.
     * @return A BitPay generated Invoice object.
     * @throws InvoiceCancellationException InvoiceCancellationException class
     * @throws BitPayException       BitPayException class
     */
    public Invoice cancelInvoice(String invoiceId, Boolean forceCancel) throws InvoiceCancellationException, BitPayException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));
        if (forceCancel) {
            params.add(new BasicNameValuePair("forceCancel", forceCancel.toString()));
        }
        Invoice invoice;

        try {
            HttpResponse response = this.delete("invoices/" + invoiceId, params);
            invoice = new ObjectMapper().readValue(this.responseToJsonString(response), Invoice.class);
        } catch (BitPayException ex) {
            throw new InvoiceCancellationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceCancellationException(null, "failed to deserialize BitPay server response (Invoice) : " + e.getMessage());
        }

        return invoice;
    }

    /**
     * Cancellation will require EITHER an SMS or E-mail to have already been set if the invoice has proceeded to
     * the point where it may have been paid, unless using forceCancel parameter.
     *
     * @param guid GUID A passthru variable provided by the merchant and designed to be used by the merchant to
     *             correlate the invoice with an order ID in their system, which can be used as a lookup variable
     *             in Retrieve Invoice by GUID.
     * @return Invoice Invoice
     * @throws BitPayException BitPayException class
     */
    public Invoice cancelInvoiceByGuid(String guid) throws BitPayException {
        return this.cancelInvoiceByGuid(guid, false);
    }

    /**
     * Cancellation will require EITHER an SMS or E-mail to have already been set if the invoice has proceeded to
     * the point where it may have been paid, unless using forceCancel parameter.
     * @param guid GUID A passthru variable provided by the merchant and designed to be used by the merchant to
     *             correlate the invoice with an order ID in their system, which can be used as a lookup variable
     *             in Retrieve Invoice by GUID.
     * @param forceCancel Parameter that will cancel the invoice even if no contact information is present.
     *                    Note: Canceling a paid invoice without contact information requires a manual support
     *                    process and is not recommended.
     * @return Invoice Invoice
     * @throws BitPayException BitPayException class
     */
    public Invoice cancelInvoiceByGuid(String guid, Boolean forceCancel) throws BitPayException {
        if (Objects.isNull(guid) || Objects.isNull(forceCancel)) {
            throw new InvoiceCancellationException(null, "missing required parameter");
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));
        if (forceCancel.equals(true)) {
            params.add(new BasicNameValuePair("forceCancel", forceCancel.toString()));
        }
        Invoice invoice;

        try {
            HttpResponse response = this.delete("invoices/guid/" + guid, params);
            invoice = new ObjectMapper().readValue(this.responseToJsonString(response), Invoice.class);
        } catch (BitPayException ex) {
            throw new InvoiceCancellationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceCancellationException(null,
                "failed to deserialize BitPay server response (Invoice) : " + e.getMessage());
        }

        return invoice;
    }

    /**
     * Create a refund for a BitPay invoice.
     *
     * @param invoiceId          The BitPay invoice Id having the associated refund to be created.
     * @param amount             Amount to be refunded in the currency indicated.
     * @param preview            Whether to create the refund request as a preview (which will not be acted on until status is updated)
     * @param immediate          Whether funds should be removed from merchant ledger immediately on submission or at time of processing
     * @param buyerPaysRefundFee Whether the buyer should pay the refund fee (default is merchant)
     * @param reference          Present only if specified. Used as reference label for the refund. Max str length = 100
     * @return An updated Refund Object
     * @throws RefundCreationException RefundCreationException class
     * @throws BitPayException       BitPayException class
     */
    public Refund createRefund(String invoiceId, Double amount, Boolean preview, Boolean immediate, Boolean buyerPaysRefundFee, String reference) throws RefundCreationException, BitPayException {
        Refund refund = new Refund();
        refund.setInvoice(invoiceId);
        refund.setAmount(amount);
        refund.setPreview(preview);
        refund.setImmediate(immediate);
        refund.setBuyerPaysRefundFee(buyerPaysRefundFee);
        refund.setReference(reference);

        final Map<String, Object> params = this.createBasicParamsForCreateRefund(refund);

        return createRefundByParams(params);
    }

    /**
     * Create a refund for a BitPay invoice.
     *
     * @param invoiceId          The BitPay invoice Id having the associated refund to be created.
     * @param amount             Amount to be refunded in the currency indicated.
     * @param preview            Whether to create the refund request as a preview (which will not be acted on until status is updated)
     * @param immediate          Whether funds should be removed from merchant ledger immediately on submission or at time of processing
     * @param buyerPaysRefundFee Whether the buyer should pay the refund fee (default is merchant)
     * @param reference          Present only if specified. Used as reference label for the refund. Max str length = 100
     * @param guid               Guid
     * @return An updated Refund Object
     * @throws RefundCreationException RefundCreationException class
     * @throws BitPayException       BitPayException class
     * @since 8.7.0
     */
    public Refund createRefund(
        String invoiceId,
        Double amount,
        Boolean preview,
        Boolean immediate,
        Boolean buyerPaysRefundFee,
        String reference,
        String guid
    ) throws RefundCreationException, BitPayException {
        Refund refund = new Refund();
        refund.setInvoice(invoiceId);
        refund.setAmount(amount);
        refund.setPreview(preview);
        refund.setImmediate(immediate);
        refund.setBuyerPaysRefundFee(buyerPaysRefundFee);
        refund.setReference(reference);
        refund.setGuid(guid);

        final Map<String, Object> params = this.createBasicParamsForCreateRefund(refund);

        return createRefundByParams(params);
    }

    /**
     * Create a refund for a BitPay invoice.
     *
     * @param refund Refund class which provided data - invoice id, amount, preview, immediate, buyerPaysRefundFee, reference and guid for create request
     * @return An updated Refund Object
     * @throws RefundCreationException RefundCreationException class
     * @throws BitPayException         BitPayException class
     * @since 8.7.0
     */
    public Refund createRefund(Refund refund) throws
        RefundCreationException, BitPayException {
        final Map<String, Object> params = this.createBasicParamsForCreateRefund(refund);
        return createRefundByParams(params);
    }

    /**
     * Retrieve a previously made refund request on a BitPay invoice.
     *
     * @param refundId The BitPay refund ID.
     * @return A BitPay Refund object with the associated Refund object.
     * @throws RefundQueryException RefundQueryException class
     * @throws BitPayException       BitPayException class
     */
    public Refund getRefund(String refundId) throws RefundQueryException, BitPayException {
        Refund refund;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));

        try {
            HttpResponse response = this.get("refunds/" + refundId, params, true);
            refund = new ObjectMapper().readValue(this.responseToJsonString(response), Refund.class);
        } catch (JsonProcessingException e) {
            throw new RefundQueryException(null, "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        } catch (BitPayException ex) {
            throw new RefundQueryException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new RefundQueryException(null, "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refund;
    }

    /**
     * Retrieve a previously made refund request on a BitPay invoice.
     *
     * @param guid The BitPay refund Guid.
     * @return A BitPay Refund object with the associated Refund object.
     * @throws RefundQueryException RefundQueryException class
     * @throws BitPayException       BitPayException class
     * @since 8.7.0
     */
    public Refund getRefundByGuid(String guid) throws RefundQueryException, BitPayException {
        Refund refund;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));

        try {
            HttpResponse response = this.get("refunds/guid/" + guid, params, true);
            refund = new ObjectMapper().readValue(this.responseToJsonString(response), Refund.class);
        } catch (JsonProcessingException e) {
            throw new RefundQueryException(null, "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        } catch (BitPayException ex) {
            throw new RefundQueryException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new RefundQueryException(null, "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refund;
    }

    /**
     * Retrieve all refund requests on a BitPay invoice.
     *
     * @param invoiceId The BitPay invoice object having the associated refunds.
     * @return A list of BitPay Refund objects with the associated Refund objects.
     * @throws RefundQueryException RefundQueryException class
     * @throws BitPayException       BitPayException class
     */
    public List<Refund> getRefunds(String invoiceId) throws RefundQueryException, BitPayException {
        List<Refund> refunds;
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));
        params.add(new BasicNameValuePair("invoiceId", invoiceId));

        try {
            HttpResponse response = this.get("refunds/", params, true);
            refunds = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), Refund[].class));
        } catch (JsonProcessingException e) {
            throw new RefundQueryException(null, "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        } catch (BitPayException ex) {
            throw new RefundQueryException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new RefundQueryException(null, "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
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
    public Refund updateRefund(String refundId, String status) throws RefundUpdateException, BitPayException {
        final Map<String, String> params = new HashMap<>();
        params.put("token", this.getAccessToken(Facade.Merchant));
        if (refundId == null || status == null) {
            throw new RefundUpdateException(null, "Updating the refund requires a refund ID and a new status to be set.");
        }
        if (status != null) {
            params.put("status", status);
        }

        ObjectMapper mapper = new ObjectMapper();
        String json;
        Refund refund;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new RefundUpdateException(null, "failed to serialize object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.update("refunds/" + refundId, json);
            refund = new ObjectMapper().readValue(this.responseToJsonString(response), Refund.class);
        } catch (BitPayException ex) {
            throw new RefundUpdateException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new RefundUpdateException(null, "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refund;
    }

    /**
     * Update the status of a BitPay invoice.
     *
     * @param guid A BitPay refund Guid.
     * @param status   The new status for the refund to be updated.
     * @return A BitPay generated Refund object.
     * @throws RefundUpdateException RefundUpdateException class
     * @throws BitPayException       BitPayException class
     */
    public Refund updateRefundByGuid(String guid, String status) throws RefundUpdateException, BitPayException {
        final Map<String, String> params = new HashMap<>();
        params.put("token", this.getAccessToken(Facade.Merchant));
        if (guid == null || status == null) {
            throw new RefundUpdateException(null, "Updating the refund requires a refund ID and a new status to be set.");
        }
        if (status != null) {
            params.put("status", status);
        }

        ObjectMapper mapper = new ObjectMapper();
        String json;
        Refund refund;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new RefundUpdateException(null, "failed to serialize object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.update("refunds/guid/" + guid, json);
            refund = new ObjectMapper().readValue(this.responseToJsonString(response), Refund.class);
        } catch (BitPayException ex) {
            throw new RefundUpdateException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new RefundUpdateException(null, "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refund;
    }

    /**
     * Send a refund notification.
     *
     * @param refundId A BitPay refund ID.
     * @return An updated Refund Object
     * @throws RefundCreationException RefundCreationException class
     * @throws BitPayException       BitPayException class
     */
    public Boolean sendRefundNotification(String refundId) throws RefundCreationException, BitPayException {
        final Map<String, String> params = new HashMap<>();
        params.put("token", this.getAccessToken(Facade.Merchant));

        Refund refund;
        ObjectMapper mapper = new ObjectMapper();

        Boolean result;
        String json;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new RefundCreationException(null ,"failed to serialize Refund object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.post("refunds/" + refundId + "/notifications", json, true);
            String jsonString = this.responseToJsonString(response);
            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode node = rootNode.get("status");
            result = node.toString().replace("\"", "").toLowerCase(Locale.ROOT).equals("success");
        } catch (BitPayException ex) {
            throw new RefundCreationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new RefundCreationException(null ,"failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return result;
    }

    /**
     * Cancel a previously submitted refund request on a BitPay invoice.
     *
     * @param refundId The refund Id for the refund to be canceled.
     * @return An updated Refund Object.
     * @throws RefundCancellationException RefundCancellationException class
     * @throws BitPayException       BitPayException class
     */
    public Refund cancelRefund(String refundId) throws RefundCancellationException, BitPayException {
        Refund refund;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));

        try {
            HttpResponse response = this.delete("refunds/" + refundId, params);
            refund = new ObjectMapper().readValue(this.responseToJsonString(response), Refund.class);
        } catch (BitPayException ex) {
            throw new RefundCancellationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new RefundCancellationException(null, "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refund;
    }

    /**
     * Cancel a previously submitted refund request on a BitPay invoice.
     *
     * @param guid The refund Guid for the refund to be canceled.
     * @return An updated Refund Object.
     * @throws RefundCancellationException RefundCancellationException class
     * @throws BitPayException       BitPayException class
     * @since 8.7.0
     */
    public Refund cancelRefundByGuid(String guid) throws RefundCancellationException, BitPayException {
        Refund refund;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));

        try {
            HttpResponse response = this.delete("refunds/guid/" + guid, params);
            refund = new ObjectMapper().readValue(this.responseToJsonString(response), Refund.class);
        } catch (BitPayException ex) {
            throw new RefundCancellationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new RefundCancellationException(null, "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refund;
    }

    /**
     * Create a BitPay bill using the POS facade.
     *
     * @param bill An Bill object with request parameters defined.
     * @return A BitPay generated Bill object.
     * @throws BillCreationException BillCreationException class
     */
    public Bill createBill(Bill bill) throws BillCreationException {
        try {
            return this.createBill(bill, Facade.Merchant, true);
        } catch (Exception e) {
            throw new BillCreationException(null, e.getMessage());
        }
    }

    /**
     * Create a BitPay Bill.
     *
     * @param bill        A Bill object with request parameters defined.
     * @param facade      The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay generated Bill object.
     * @throws BitPayException       BitPayException class
     * @throws BillCreationException BillCreationException class
     */
    public Bill createBill(Bill bill, String facade, boolean signRequest) throws BitPayException, BillCreationException {
        String token = this.getAccessToken(facade);
        bill.setToken(token);
        ObjectMapper mapper = new ObjectMapper();
        String json;

        try {
            json = mapper.writeValueAsString(bill);
        } catch (JsonProcessingException e) {
            throw new BillCreationException(null, "failed to serialize Bill object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.post("bills", json, signRequest);
            bill = mapper.readerForUpdating(bill).readValue(this.responseToJsonString(response));
        } catch (Exception e) {
            throw new BillCreationException(null, "failed to deserialize BitPay server response (Bill) : " + e.getMessage());
        }

        this.cacheToken(bill.getId(), bill.getToken());
        return bill;
    }

    /**
     * Retrieve a BitPay bill by bill id using the public facade.
     *
     * @param billId The id of the bill to retrieve.
     * @return A BitPay Bill object.
     * @throws BillQueryException BillQueryException class
     */
    public Bill getBill(String billId) throws BillQueryException {
        try {
            return this.getBill(billId, Facade.Merchant, true);
        } catch (Exception e) {
            throw new BillQueryException(null, e.getMessage());
        }
    }

    /**
     * Retrieve a BitPay bill by bill id using the specified facade.
     *
     * @param billId      The id of the bill to retrieve.
     * @param facade      The facade used to retrieve it.
     * @param signRequest Signed request.
     * @return A BitPay Bill object.
     * @throws BitPayException    BitPayException class
     * @throws BillQueryException BillQueryException class
     */
    public Bill getBill(String billId, String facade, boolean signRequest) throws BitPayException, BillQueryException {
        String token = this.getAccessToken(facade);
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", token));

        Bill bill;

        try {
            HttpResponse response = this.get("bills/" + billId, params, signRequest);
            bill = new ObjectMapper().readValue(this.responseToJsonString(response), Bill.class);
        } catch (JsonProcessingException e) {
            throw new BillQueryException(null, "failed to deserialize BitPay server response (Bill) : " + e.getMessage());
        } catch (Exception e) {
            throw new BillQueryException(null, "failed to deserialize BitPay server response (Bill) : " + e.getMessage());
        }

        return bill;
    }

    /**
     * Retrieve a collection of BitPay bills.
     *
     * @param status The status to filter the bills.
     * @return A list of BitPay Bill objects.
     * @throws BitPayException    BitPayException class
     * @throws BillQueryException BillQueryException class
     */
    public List<Bill> getBills(String status) throws BitPayException, BillQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));
        params.add(new BasicNameValuePair("status", status));

        List<Bill> bills;

        try {
            HttpResponse response = this.get("bills", params);
            bills = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), Bill[].class));
        } catch (JsonProcessingException e) {
            throw new BillQueryException(null, "failed to deserialize BitPay server response (Bills) : " + e.getMessage());
        } catch (Exception e) {
            throw new BillQueryException(null, "failed to deserialize BitPay server response (Bills) : " + e.getMessage());
        }

        return bills;
    }

    /**
     * Retrieve a collection of BitPay bills.
     *
     * @return A list of BitPay Bill objects.
     * @throws BitPayException    BitPayException class
     * @throws BillQueryException BillQueryException class
     */
    public List<Bill> getBills() throws BitPayException, BillQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));

        List<Bill> bills;

        try {
            HttpResponse response = this.get("bills", params);
            bills = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), Bill[].class));
        } catch (JsonProcessingException e) {
            throw new BillQueryException(null, "failed to deserialize BitPay server response (Bills) : " + e.getMessage());
        } catch (Exception e) {
            throw new BillQueryException(null, "failed to deserialize BitPay server response (Bills) : " + e.getMessage());
        }

        return bills;
    }

    /**
     * Update a BitPay Bill.
     *
     * @param bill   A Bill object with the parameters to update defined.
     * @param billId The Id of the Bill to udpate.
     * @return An updated Bill object.
     * @throws BitPayException     BitPayException class
     * @throws BillUpdateException BillUpdateException class
     */
    public Bill updateBill(Bill bill, String billId) throws BitPayException, BillUpdateException {
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(bill);
        } catch (JsonProcessingException e) {
            throw new BillUpdateException(null, "failed to serialize Bill object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.update("bills/" + billId, json);
            bill = mapper.readerForUpdating(bill).readValue(this.responseToJsonString(response));
        } catch (Exception e) {
            throw new BillUpdateException(null, "failed to deserialize BitPay server response (Bill) : " + e.getMessage());
        }

        this.cacheToken(bill.getId(), bill.getToken());
        return bill;
    }

    /**
     * Deliver a BitPay Bill.
     *
     * @param billId    The id of the requested bill.
     * @param billToken The token of the requested bill.
     * @return A response status returned from the API.
     * @throws BillDeliveryException BillDeliveryException class
     */
    public String deliverBill(String billId, String billToken) throws BillDeliveryException {
        try {
            return this.deliverBill(billId, billToken, true);
        } catch (Exception e) {
            throw new BillDeliveryException(null, e.getMessage());
        }
    }

    /**
     * Deliver a BitPay Bill.
     *
     * @param billId      The id of the requested bill.
     * @param billToken   The token of the requested bill.
     * @param signRequest Allow unsigned request
     * @return A response status returned from the API.
     * @throws BillDeliveryException BillDeliveryException class
     */
    public String deliverBill(String billId, String billToken, boolean signRequest) throws BillDeliveryException {
        Map<String, String> map = new HashMap<>();
        map.put("token", billToken);
        ObjectMapper mapper = new ObjectMapper();
        String json;
        String result;
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new BillDeliveryException(null, "failed to serialize Bill object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.post("bills/" + billId + "/deliveries", json, signRequest);
            result = this.responseToJsonString(response).replace("\"", "");
        } catch (Exception e) {
            throw new BillDeliveryException(null, "failed to deserialize BitPay server response (Bill) : " + e.getMessage());
        }

        return result;
    }

    /**
     * Retrieve the rates for a cryptocurrency / fiat pair. See https://bitpay.com/bitcoin-exchange-rates.
     *
     * @param baseCurrency the cryptocurrency for which you want to fetch the rates.
     *                     Current supported values are BTC and BCH.
     * @param currency the fiat currency for which you want to fetch the baseCurrency rates
     * @return A Rate object populated with the BitPay exchange rate table.
     * @throws RateQueryException RateQueryException class
     * @since 8.8.0
     */
    public Rate getRate(String baseCurrency, String currency) throws RateQueryException {
        try {
            HttpResponse response = this.get("rates/" + baseCurrency + "/" + currency);
            return new ObjectMapper().readValue(this.responseToJsonString(response), Rate.class);
        } catch (JsonProcessingException e) {
            throw new RateQueryException(null, "failed to deserialize BitPay server response (Rates) : " + e.getMessage());
        } catch (Exception e) {
            throw new RateQueryException(null, "failed to deserialize BitPay server response (Rates) : " + e.getMessage());
        }
    }

    /**
     * Retrieve the exchange rate table maintained by BitPay.  See https://bitpay.com/bitcoin-exchange-rates.
     *
     * @return A Rates object populated with the BitPay exchange rate table.
     * @throws RateQueryException RateQueryException class
     */
    public Rates getRates() throws RateQueryException {
        List<Rate> rates;

        try {
            HttpResponse response = this.get("rates");
            rates = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), Rate[].class));
        } catch (JsonProcessingException e) {
            throw new RateQueryException(null, "failed to deserialize BitPay server response (Rates) : " + e.getMessage());
        } catch (Exception e) {
            throw new RateQueryException(null, "failed to deserialize BitPay server response (Rates) : " + e.getMessage());
        }

        return new Rates(rates, this);
    }

    /**
     * Retrieve the exchange rate table maintained by BitPay by baseCurrency. See https://bitpay.com/bitcoin-exchange-rates.
     *
     * @param baseCurrency the cryptocurrency for which you want to fetch the rates.
     *                     Current supported values are BTC and BCH.
     * @return A Rates object populated with the BitPay exchange rate table.
     * @throws RateQueryException RateQueryException class
     * @since 8.8.0
     */
    public Rates getRates(String baseCurrency) throws RateQueryException {
        List<Rate> rates;

        try {
            HttpResponse response = this.get("rates/" + baseCurrency);
            rates = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), Rate[].class));
        } catch (JsonProcessingException e) {
            throw new RateQueryException(null,
                "failed to deserialize BitPay server response (Rates) : " + e.getMessage());
        } catch (Exception e) {
            throw new RateQueryException(null,
                "failed to deserialize BitPay server response (Rates) : " + e.getMessage());
        }

        return new Rates(rates, this);
    }

    /**
     * Retrieve a list of ledgers by date range using the merchant facade.
     *
     * @param currency  The three digit currency string for the ledger to retrieve.
     * @param dateStart The first date for the query filter.
     * @param dateEnd   The last date for the query filter.
     * @return A Ledger object populated with the BitPay ledger entries list.
     * @throws BitPayException      BitPayException class
     * @throws LedgerQueryException LedgerQueryException class
     */
    public Ledger getLedger(String currency, String dateStart, String dateEnd) throws BitPayException, LedgerQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));
        params.add(new BasicNameValuePair("startDate", dateStart));
        params.add(new BasicNameValuePair("endDate", dateEnd));

        Ledger ledger = new Ledger();

        try {
            HttpResponse response = this.get("ledgers/" + currency, params);
            List<LedgerEntry> ledgerEntries;
            ledgerEntries = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), LedgerEntry[].class));
            ledgerEntries.remove(null);
            ledgerEntries.remove("");
            ledger.setEntries(ledgerEntries);
        } catch (JsonProcessingException e) {
            throw new LedgerQueryException(null, "failed to deserialize BitPay server response (Ledger) : " + e.getMessage());
        } catch (Exception e) {
            throw new LedgerQueryException(null, "failed to deserialize BitPay server response (Ledger) : " + e.getMessage());
        }

        return ledger;
    }

    /**
     * Retrieve a list of ledgers using the merchant facade.
     *
     * @return A list of Ledger objects populated with the currency and current balance of each one.
     * @throws BitPayException      BitPayException class
     * @throws LedgerQueryException LedgerQueryException class
     */
    public List<Ledger> getLedgers() throws BitPayException, LedgerQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));

        List<Ledger> ledgers;

        try {
            HttpResponse response = this.get("ledgers", params);
            ledgers = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), Ledger[].class));
        } catch (JsonProcessingException e) {
            throw new LedgerQueryException(null, "failed to deserialize BitPay server response (Ledger) : " + e.getMessage());
        } catch (Exception e) {
            throw new LedgerQueryException(null, "failed to deserialize BitPay server response (Ledger) : " + e.getMessage());
        }

        return ledgers;
    }

    /**
     * Submit BitPay Payout Recipients.
     *
     * @param recipients PayoutRecipients A PayoutRecipients object with request parameters defined.
     * @return array A list of BitPay PayoutRecipients objects..
     * @throws BitPayException                  BitPayException class
     * @throws PayoutRecipientCreationException PayoutRecipientCreationException class
     */
    public List<PayoutRecipient> submitPayoutRecipients(PayoutRecipients recipients) throws BitPayException, PayoutRecipientCreationException {
        recipients.setToken(this.getAccessToken(Facade.Payout));
        recipients.setGuid(this.getGuid());
        ObjectMapper mapper = new ObjectMapper();
        String json;

        try {
            json = mapper.writeValueAsString(recipients);
        } catch (JsonProcessingException e) {
            throw new PayoutRecipientCreationException(null, "failed to serialize PayoutRecipients object : " + e.getMessage());
        }

        List<PayoutRecipient> recipientsList;

        try {
            HttpResponse response = this.post("recipients", json, true);
            recipientsList = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), PayoutRecipient[].class));
        } catch (JsonProcessingException e) {
            throw new PayoutRecipientCreationException(null, "failed to deserialize BitPay server response (PayoutRecipients) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutRecipientCreationException(null, "failed to deserialize BitPay server response (PayoutRecipients) : " + e.getMessage());
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
    public List<PayoutRecipient> getPayoutRecipients(String status, Integer limit, Integer offset)
            throws BitPayException, PayoutRecipientQueryException {
        
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Payout)));
        if (status != null) {
            params.add(new BasicNameValuePair("status", status));
        }
        if (limit != null) {
            params.add(new BasicNameValuePair("limit", limit.toString()));
        }
        if (offset != null) {
            params.add(new BasicNameValuePair("offset", offset.toString()));
        }

        List<PayoutRecipient> recipientsList;

        try {
            HttpResponse response = this.get("recipients", params, true);
            recipientsList = Arrays
                    .asList(new ObjectMapper().readValue(this.responseToJsonString(response), PayoutRecipient[].class));
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
    public PayoutRecipient getPayoutRecipient(String recipientId) throws BitPayException, PayoutRecipientQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Payout)));

        PayoutRecipient recipient;

        try {
            HttpResponse response = this.get("recipients/" + recipientId, params, true);
            recipient = new ObjectMapper().readValue(this.responseToJsonString(response), PayoutRecipient.class);
        } catch (JsonProcessingException e) {
            throw new PayoutRecipientQueryException(null, "failed to deserialize BitPay server response (PayoutRecipient) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutRecipientQueryException(null, "failed to deserialize BitPay server response (PayoutRecipient) : " + e.getMessage());
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
    public PayoutRecipient updatePayoutRecipient(String recipientId, PayoutRecipient recipient)
            throws BitPayException, PayoutRecipientUpdateException {
        recipient.setToken(this.getAccessToken(Facade.Payout));
        recipient.setGuid(this.getGuid());
        ObjectMapper mapper = new ObjectMapper();
        String json;

        try {
        	json = mapper.writeValueAsString(recipient);
        } catch (JsonProcessingException e) {
            throw new PayoutRecipientUpdateException(null,
                    "failed to serialize PayoutRecipient object : " + e.getMessage());
        }

        PayoutRecipient updateRecipient;

        try {
            HttpResponse response = this.update("recipients/" + recipientId, json);
            updateRecipient = new ObjectMapper().readValue(this.responseToJsonString(response), PayoutRecipient.class);
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
     * @throws PayoutRecipientCancellationException PayoutRecipientCancellationException
     *                                              class
     */
    public Boolean deletePayoutRecipient(String recipientId)
            throws BitPayException, PayoutRecipientCancellationException {
        
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Payout)));
        
        ObjectMapper mapper = new ObjectMapper();    
        Boolean result;
        
        try {
            HttpResponse response = this.delete("recipients/" + recipientId, params);
            String jsonString = this.responseToJsonString(response);
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
     * @throws BitPayException                      BitPayException class
     * @throws PayoutRecipientNotificationException PayoutRecipientNotificationException
     *                                              class
     */
    public Boolean requestPayoutRecipientNotification(String recipientId)
            throws PayoutRecipientNotificationException, BitPayException {
        final Map<String, String> params = new HashMap<>();
        params.put("token", this.getAccessToken(Facade.Payout));

        ObjectMapper mapper = new ObjectMapper();
        Boolean result;
        String json;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new PayoutRecipientNotificationException(null,
                    "failed to serialize PayoutRecipient object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.post("recipients/" + recipientId + "/notifications", json, true);
            String jsonString = this.responseToJsonString(response);
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

    /**
     * Submit a BitPay Payout.
     *
     * @param payout Payout A Payout object with request parameters defined.
     * @return A BitPay generated Payout object.
     * @throws BitPayException         BitPayException class
     * @throws PayoutCreationException PayoutCreationException class
     */
    public Payout submitPayout(Payout payout) throws BitPayException, PayoutCreationException {
        String token = this.getAccessToken(Facade.Payout);
        payout.setToken(token);

        ObjectMapper mapper = new ObjectMapper();
        String json;
        
        try {
            json = mapper.writeValueAsString(payout);
        } catch (JsonProcessingException e) {
            throw new PayoutCreationException(null, "failed to serialize Payout object : " + e.getMessage());
        }
        try {
            HttpResponse response = this.post("payouts", json, true);
            payout = new ObjectMapper().readValue(this.responseToJsonString(response), Payout.class);
        } catch (Exception e) {
            throw new PayoutCreationException(null,
                    "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
        }

        return payout;
    }

    /**
     * Retrieve a BitPay payout by payout id using. The client must have been
     * previously authorized for the payout facade.
     *
     * @param payoutId String The id of the payout to retrieve.
     * @return A BitPay Payout object.
     * @throws BitPayException      BitPayException class
     * @throws PayoutQueryException PayoutQueryException class
     */
    public Payout getPayout(String payoutId) throws BitPayException, PayoutQueryException {
        String token = this.getAccessToken(Facade.Payout);
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", token));

        Payout payout;

        try {
            HttpResponse response = this.get("payouts/" + payoutId, params, true);
            payout = new ObjectMapper().readValue(this.responseToJsonString(response), Payout.class);
        } catch (JsonProcessingException e) {
            throw new PayoutQueryException(null,
                    "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutQueryException(null,
                    "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
        }

        return payout;
    }

    /**
     * Cancel a BitPay Payout.
     *
     * @param payoutId String The id of the payout to cancel.
     * @return True if the refund was successfully canceled, false otherwise.
     * @throws BitPayException             BitPayException class
     * @throws PayoutCancellationException PayoutCancellationException class
     */
    public Boolean cancelPayout(String payoutId) throws BitPayException, PayoutCancellationException {

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Payout)));
        Boolean result;
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            HttpResponse response = this.delete("payouts/" + payoutId, params);
            String jsonString = this.responseToJsonString(response);
            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode node = rootNode.get("status");
            result = node.toString().replace("\"", "").toLowerCase(Locale.ROOT).equals("success");

        } catch (JsonProcessingException e) {
            throw new PayoutCancellationException(null,
                    "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutCancellationException(null,
                    "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
        }

        return result;
    }

    /**
     * Retrieve a collection of BitPay payouts.
     *
     * @param startDate String The start date for the query.
     * @param endDate   String The end date for the query.
     * @param status    String The status to filter(optional).
     * @param reference String The optional reference specified at payout request creation.
     * @param limit     int Maximum results that the query will return (useful for
     *                  paging results).
     * @param offset    int Offset for paging.
     * @return A list of BitPay Payout objects.
     * @throws BitPayException      BitPayException class
     * @throws PayoutQueryException PayoutQueryException class
     */
    public List<Payout> getPayouts(String startDate, String endDate, String status, String reference, Integer limit,
            Integer offset) throws BitPayException, PayoutQueryException {

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Payout)));
        if (startDate != null) {
            params.add(new BasicNameValuePair("startDate", startDate));
        }
        if (endDate != null) {
            params.add(new BasicNameValuePair("endDate", endDate));
        }
        if (reference != null) {
            params.add(new BasicNameValuePair("reference", reference));
        }
        if (status != null) {
            params.add(new BasicNameValuePair("status", status));
        }
        if (limit != null) {
            params.add(new BasicNameValuePair("limit", limit.toString()));
        }
        if (offset != null) {
            params.add(new BasicNameValuePair("offset", offset.toString()));
        }
        
        List<Payout> payouts;

        try {
            HttpResponse response = this.get("payouts", params, true);
            payouts = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), Payout[].class));
        } catch (JsonProcessingException e) {
            throw new PayoutQueryException(null,
                    "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutQueryException(null,
                    "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
        }

        return payouts;
    }

    /**
     * Request a payout notification
     *
     * @param payoutId String The id of the payout to notify..
     * @return True if the notification was successfully sent, false otherwise.
     * @throws BitPayException             BitPayException class
     * @throws PayoutNotificationException PayoutNotificationException class
     */
    public Boolean requestPayoutNotification(String payoutId)
            throws BitPayException, PayoutNotificationException {
        final Map<String, String> params = new HashMap<>();
        params.put("token", this.getAccessToken(Facade.Payout));

        ObjectMapper mapper = new ObjectMapper();

        Boolean result;
        String json;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new PayoutNotificationException(null, "failed to serialize payout batch object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.post("payouts/" + payoutId + "/notifications", json, true);
            String jsonString = this.responseToJsonString(response);
            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode node = rootNode.get("status");
            result = node.toString().replace("\"", "").toLowerCase(Locale.ROOT).equals("success");
        } catch (BitPayException ex) {
            throw new PayoutNotificationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new PayoutNotificationException(null,
                    "failed to deserialize BitPay server response (Payout) : " + e.getMessage());
        }

        return result;
    }


    /**
     * Submit a BitPay Payout batch.
     *
     * @param batch PayoutBatch A PayoutBatch object with request parameters defined.
     * @return A BitPay generated PayoutBatch object.
     * @throws BitPayException              BitPayException class
     * @throws PayoutBatchCreationException PayoutBatchCreationException class
     */
    public PayoutBatch submitPayoutBatch(PayoutBatch batch) throws BitPayException, PayoutBatchCreationException {
        String token = this.getAccessToken(Facade.Payout);
        batch.setToken(token);
        batch.setGuid(this.getGuid());

        ObjectMapper mapper = new ObjectMapper();
        String json;
        
        try {
            json = mapper.writeValueAsString(batch);
        } catch (JsonProcessingException e) {
            throw new PayoutBatchCreationException(null, "failed to serialize PayoutBatch object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.post("payoutBatches", json, true);
            batch = mapper.readerForUpdating(batch).readValue(this.responseToJsonString(response));
        } catch (Exception e) {
            throw new PayoutBatchCreationException(null,
                    "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        }

        this.cacheToken(batch.getId(), batch.getToken());

        return batch;
    }

    /**
     * Retrieve a collection of BitPay payout batches.
     *
     * @return A list of BitPay PayoutBatch objects.
     * @throws BitPayException           BitPayException class
     * @throws PayoutBatchQueryException PayoutBatchQueryException class
     */
    public List<PayoutBatch> getPayoutBatches() throws BitPayException, PayoutBatchQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Payout)));

        List<PayoutBatch> batches;

        try {
            HttpResponse response = this.get("payouts", params);
            batches = Arrays
                    .asList(new ObjectMapper().readValue(this.responseToJsonString(response), PayoutBatch[].class));
        } catch (JsonProcessingException e) {
            throw new PayoutBatchQueryException(null,
                    "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutBatchQueryException(null,
                    "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        }

        return batches;
    }

    /**
     * Retrieve a collection of BitPay payout batches.
     *
     * @param status String The status to filter the Payout Batches.
     * @return A list of BitPay PayoutBatch objects.
     * @throws BitPayException      BitPayException class
     * @throws PayoutQueryException PayoutQueryException class
     */
    public List<PayoutBatch> getPayoutBatches(String status) throws BitPayException, PayoutQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Payout)));
        params.add(new BasicNameValuePair("status", status));

        List<PayoutBatch> batches;

        try {
            HttpResponse response = this.get("payouts", params);
            batches = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), PayoutBatch[].class));
        } catch (JsonProcessingException e) {
            throw new PayoutQueryException(null, "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutQueryException(null, "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        }

        return batches;
    }

    /**
     * Retrieve a collection of BitPay payout batches.
     *
     * @param startDate String The start date for the query.
     * @param endDate   String The end date for the query.
     * @param status    String The status to filter(optional).
     * @param limit     int Maximum results that the query will return (useful for
     *                  paging results).
     * @param offset    int Offset for paging.
     * @return A list of BitPay PayoutBatch objects.
     * @throws BitPayException           BitPayException class
     * @throws PayoutBatchQueryException PayoutBatchQueryException class
     */
    public List<PayoutBatch> getPayoutBatches(String startDate, String endDate, String status, Integer limit,
            Integer offset) throws BitPayException, PayoutBatchQueryException {
        

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Payout)));
        if (startDate != null) {
            params.add(new BasicNameValuePair("startDate", startDate));
        }
        if (endDate != null) {
            params.add(new BasicNameValuePair("endDate", endDate));
        }
        if (status != null) {
            params.add(new BasicNameValuePair("status", status));
        }
        if (limit != null) {
            params.add(new BasicNameValuePair("limit", limit.toString()));
        }
        if (offset != null) {
            params.add(new BasicNameValuePair("offset", offset.toString()));
        }

        List<PayoutBatch> batches;

        try {
            HttpResponse response = this.get("payoutBatches", params);
            batches = Arrays
                    .asList(new ObjectMapper().readValue(this.responseToJsonString(response), PayoutBatch[].class));
        } catch (JsonProcessingException e) {
            throw new PayoutBatchQueryException(null,
                    "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutBatchQueryException(null,
                    "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        }

        return batches;
    }

    /**
     * Retrieve a BitPay payout batch by batch id using. The client must have been
     * previously authorized for the payout facade.
     *
     * @param payoutBatchId String The id of the batch to retrieve.
     * @return A BitPay PayoutBatch object.
     * @throws BitPayException           BitPayException class
     * @throws PayoutBatchQueryException PayoutBatchQueryException class
     */
    public PayoutBatch getPayoutBatch(String payoutBatchId) throws BitPayException, PayoutBatchQueryException {
        String token = this.getAccessToken(Facade.Payout);
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", token));

        PayoutBatch batch;

        try {
            HttpResponse response = this.get("payoutBatches/" + payoutBatchId, params, true);
            batch = new ObjectMapper().readValue(this.responseToJsonString(response), PayoutBatch.class);
        } catch (JsonProcessingException e) {
            throw new PayoutBatchQueryException(null,
                    "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutBatchQueryException(null,
                    "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        }

        return batch;
    }

    /**
     * Cancel a BitPay Payout batch.
     *
     * @param payoutBatchId String The id of the payout batch to cancel.
     * @return True if the refund was successfully canceled, false otherwise.
     * @throws BitPayException                  BitPayException class
     * @throws PayoutBatchCancellationException PayoutBatchCancellationException
     *                                          class
     */
    public Boolean cancelPayoutBatch(String payoutBatchId) throws BitPayException, PayoutBatchCancellationException {

        Boolean result;
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Payout)));

        ObjectMapper mapper = new ObjectMapper();
        
        try {
            HttpResponse response = this.delete("payoutBatches/" + payoutBatchId, params);
            String jsonString = this.responseToJsonString(response);
            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode node = rootNode.get("status");
            result = node.toString().replace("\"", "").toLowerCase(Locale.ROOT).equals("success");

        } catch (JsonProcessingException e) {
            throw new PayoutBatchCancellationException(null,
                    "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutBatchCancellationException(null,
                    "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        }

        return result;
    }

    /**
     * Request a payout batch notification
     *
     * @param payoutBatchId String The id of the payout batch to notify..
     * @return True if the notification was successfully sent, false otherwise.
     * @throws BitPayException                  BitPayException class
     * @throws PayoutBatchNotificationException PayoutBatchNotificationException class
     */
    public Boolean requestPayoutBatchNotification(String payoutBatchId)
            throws BitPayException, PayoutBatchNotificationException {
        final Map<String, String> params = new HashMap<>();
        params.put("token", this.getAccessToken(Facade.Payout));

        ObjectMapper mapper = new ObjectMapper();

        Boolean result;
        String json;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new PayoutBatchNotificationException(null,
                    "failed to serialize payout batch object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.post("payoutBatches/" + payoutBatchId + "/notifications", json, true);
            String jsonString = this.responseToJsonString(response);
            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode node = rootNode.get("status");
            result = node.toString().replace("\"", "").toLowerCase(Locale.ROOT).equals("success");
        } catch (BitPayException ex) {
            throw new PayoutBatchNotificationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new PayoutBatchNotificationException(null,
                    "failed to deserialize BitPay server response (Payoutbatch) : " + e.getMessage());
        }

        return result;
    }

    /**
     * Retrieves settlement reports for the calling merchant filtered by query.
     * The `limit` and `offset` parameters
     * specify pages for large query sets.
     *
     * @param currency  The three digit currency string for the ledger to retrieve.
     * @param dateStart The start date for the query.
     * @param dateEnd   The end date for the query.
     * @param status    Can be `processing`, `completed`, or `failed`.
     * @param limit     Maximum number of settlements to retrieve.
     * @param offset    Offset for paging.
     * @return A list of BitPay Settlement objects.
     * @throws BitPayException          BitPayException class
     * @throws SettlementQueryException SettlementQueryException class
     */
    public List<Settlement> getSettlements(String currency, String dateStart, String dateEnd, String status, Integer limit, Integer offset) throws BitPayException, SettlementQueryException {
        status = status != null ? status : "";
        limit = limit != null ? limit : 100;
        offset = offset != null ? offset : 0;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));
        params.add(new BasicNameValuePair("dateStart", dateStart));
        params.add(new BasicNameValuePair("dateEnd", dateEnd));
        params.add(new BasicNameValuePair("currency", currency));
        params.add(new BasicNameValuePair("status", status));
        params.add(new BasicNameValuePair("limit", limit.toString()));
        params.add(new BasicNameValuePair("offset", offset.toString()));

        List<Settlement> settlements;

        try {
            HttpResponse response = this.get("settlements", params);
            settlements = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), Settlement[].class));
        } catch (JsonProcessingException e) {
            throw new SettlementQueryException(null, "failed to deserialize BitPay server response (Settlement) : " + e.getMessage());
        } catch (Exception e) {
            throw new SettlementQueryException(null, "failed to deserialize BitPay server response (Settlement) : " + e.getMessage());
        }

        return settlements;
    }

    /**
     * Retrieves a summary of the specified settlement.
     *
     * @param settlementId Settlement Id.
     * @return A BitPay Settlement object.
     * @throws BitPayException          BitPayException class
     * @throws SettlementQueryException SettlementQueryException class
     */
    public Settlement getSettlement(String settlementId) throws BitPayException, SettlementQueryException {
        String token = this.getAccessToken(Facade.Merchant);
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", token));

        Settlement settlement;

        try {
            HttpResponse response = this.get("settlements/" + settlementId, params);
            settlement = new ObjectMapper().readValue(this.responseToJsonString(response), Settlement.class);
        } catch (JsonProcessingException e) {
            throw new SettlementQueryException(null, "failed to deserialize BitPay server response (Settlement) : " + e.getMessage());
        } catch (Exception e) {
            throw new SettlementQueryException(null, "failed to deserialize BitPay server response (Settlement) : " + e.getMessage());
        }

        return settlement;
    }

    /**
     * Gets a detailed reconciliation report of the activity within the settlement period.
     *
     * @param settlement Settlement to generate report for.
     * @return A detailed BitPay Settlement object.
     * @throws SettlementQueryException SettlementQueryException class
     */
    public Settlement getSettlementReconciliationReport(Settlement settlement) throws SettlementQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", settlement.getToken()));

        Settlement reconciliationReport;

        try {
            HttpResponse response = this.get("settlements/" + settlement.getId() + "/reconciliationReport", params);
            reconciliationReport = new ObjectMapper().readValue(this.responseToJsonString(response), Settlement.class);
        } catch (JsonProcessingException e) {
            throw new SettlementQueryException(null, "failed to deserialize BitPay server response (ReconciliationReport) : " + e.getMessage());
        } catch (Exception e) {
            throw new SettlementQueryException(null, "failed to deserialize BitPay server response (ReconciliationReport) : " + e.getMessage());
        }

        return reconciliationReport;
    }

    /**
     * Retrieve all supported wallets.
     *
     * @return A list of wallet objets.
     * @throws WalletQueryException WalletQueryException class
     * @throws BitPayException       BitPayException class
     */
    public List<Wallet> getSupportedWallets() throws WalletQueryException, BitPayException {
        List<Wallet> wallets;

        try {
            HttpResponse response = this.get("supportedWallets/");
            wallets = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), Wallet[].class));
        } catch (JsonProcessingException e) {
            throw new WalletQueryException(null, "failed to deserialize BitPay server response (Wallet) : " + e.getMessage());
        } catch (Exception e) {
            throw new WalletQueryException(null, "failed to deserialize BitPay server response (Wallet) : " + e.getMessage());
        }

        return wallets;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Initialize this object with the client name and the environment Url.
     *
     * @param proxyDetails HttpHost Optional Proxy setting
     * @throws Exception
     * @throws URISyntaxException
     */
    private void init(HttpHost proxyDetails, CredentialsProvider proxyCreds) throws BitPayException {
        try {
            this._baseUrl = this._env.equals(Env.Test) ? Env.TestUrl : Env.ProdUrl;
            if (proxyDetails != null) {
                if (proxyCreds != null) {
                    _httpClient = HttpClientBuilder.create().setProxy(proxyDetails).setDefaultCredentialsProvider(proxyCreds).build();
                } else {
                    _httpClient = HttpClientBuilder.create().setProxy(proxyDetails).build();
                }
            } else {
                _httpClient = HttpClientBuilder.create().build();
            }
            deriveIdentity();
            LoadAccessTokens();
            loadCurrencies();
        } catch (Exception e) {
            throw new BitPayException(null, "failed to deserialize BitPay server response (Token array) : " + e.getMessage());
        }
    }

    /**
     * Initialize the public/private key pair by either loading the existing one or by creating a new one.
     *
     * @throws Exception
     * @throws URISyntaxException
     */
    private void initKeys() throws Exception, URISyntaxException {
        if (_ecKey == null) {
            try {
                if (KeyUtils.privateKeyExists(this._configuration.getEnvConfig(this._env).path("PrivateKeyPath").toString().replace("\"", ""))) {
                    _ecKey = KeyUtils.loadEcKey();
                } else {
                    String keyHex = this._configuration.getEnvConfig(this._env).path("PrivateKey").toString().replace("\"", "");
                    if (!keyHex.isEmpty()) {
                        _ecKey = KeyUtils.createEcKeyFromHexString(keyHex);
                    }
                }
            } catch (Exception e) {
                throw new BitPayException(null, "When trying to load private key. Make sure the configuration details are correct and the private key and tokens are valid : " + e.getMessage());
            }
        }
    }

    private void deriveIdentity() throws IllegalArgumentException {
        // Identity in this implementation is defined to be the SIN.
        _identity = KeyUtils.deriveSIN(_ecKey);
    }

    private void clearAccessTokenCache() {
        _tokenCache = new Hashtable<String, String>();
    }

    /**
     * Add this token to the token cache.
     *
     * @param key   The token type.
     * @param token The token value.
     * @return The token associated with resource.
     */
    private void cacheToken(String key, String token) throws BitPayException {
        // we add the token to the runtime dictionary
        if (tokenExist(key)) {
            _tokenCache.put(key, token);
        }

        // we also persist the token
        WriteTokenCache();
    }

    /**
     * Persist the token cache to disk.
     *
     * @throws BitPayException BitPayException class
     */
    private void WriteTokenCache() throws BitPayException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode tokens = mapper.valueToTree(this._tokenCache);
            ((ObjectNode) this._configuration.getEnvConfig(this._env)).put("ApiTokens", tokens);
        } catch (Exception e) {
            throw new BitPayException(null, "When trying to write the tokens : " + e.getMessage());
        }
    }

    /**
     * Load tokens from configuration.
     *
     * @throws BitPayException BitPayException class
     */
    private void LoadAccessTokens() throws BitPayException {
        try {
            this.clearAccessTokenCache();

            Iterator<Map.Entry<String, JsonNode>> tokens = this._configuration.getEnvConfig(this._env).path("ApiTokens").fields();
            while (tokens.hasNext()) {
                Map.Entry<String, JsonNode> next = tokens.next();
                if (!next.getValue().asText().isEmpty()) {
                    _tokenCache.put(next.getKey(), next.getValue().asText());
                }
            }
        } catch (Exception e) {
            throw new BitPayException(null, "When trying to load the tokens : " + e.getMessage());
        }
    }

    private Map<String, Object> createBasicParamsForCreateRefund(Refund refund) throws BitPayException {
        String guid = Objects.isNull(refund.getGuid()) ? this.getGuid() : refund.getGuid();
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
        params.put("token", this.getAccessToken(Facade.Merchant));
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

    private Refund createRefundByParams(Map<String, Object> params) throws RefundCreationException {
        Refund refund;
        ObjectMapper mapper = new ObjectMapper();

        String json;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new RefundCreationException(null, "failed to serialize Refund object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.post("refunds/", json, true);
            refund = new ObjectMapper().readValue(this.responseToJsonString(response), Refund.class);
        } catch (BitPayException ex) {
            throw new RefundCreationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new RefundCreationException(null,
                "failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refund;
    }

    /**
     * Retrieve a token associated with a known resource. The token is used to access other related resources.
     *
     * @param key The identifier for the desired resource.
     * @return The token associated with resource.
     * @throws BitPayException BitPayException class
     */
    public String getAccessToken(String key) throws BitPayException {
        try {
            return _tokenCache.get(key);
        } catch (Exception e) {
            throw new BitPayException(null, "There is no token for the specified key : " + e.getMessage());
        }
    }


    /**
     * Send GET request.
     *
     * @param uri        the uri
     * @param parameters the parameters
     * @return the http response
     * @throws BitPayException the bit pay exception
     */
    public HttpResponse get(String uri, List<BasicNameValuePair> parameters) throws BitPayException {
        return get(uri, parameters, true);
    }

    /**
     * Send GET request.
     *
     * @param uri               the uri
     * @param parameters        the parameters
     * @param signatureRequired the signature required
     * @return the http response
     * @throws BitPayException the bit pay exception
     */
    public HttpResponse get(String uri, List<BasicNameValuePair> parameters, boolean signatureRequired) throws BitPayException {
        try {

            String fullURL = _baseUrl + uri;
            HttpGet get = new HttpGet(fullURL);

            if (parameters != null) {

                fullURL += "?" + URLEncodedUtils.format(parameters, "UTF-8");
                get.setURI(new URI(fullURL));
            }
            if (signatureRequired) {
                get.addHeader("x-signature", KeyUtils.sign(_ecKey, fullURL));
                get.addHeader("x-identity", KeyUtils.bytesToHex(_ecKey.getPubKey()));
            }
            get.addHeader("X-BitPay-Plugin-Info", Env.BitpayPluginInfo);
            get.addHeader("x-accept-version", Env.BitpayApiVersion);
            get.addHeader("x-bitpay-api-frame", Env.BitpayApiFrame);
            get.addHeader("x-bitpay-api-frame-version", Env.BitpayApiFrameVersion);


            _log.info(get.toString());
            return _httpClient.execute(get);

        } catch (URISyntaxException e) {
            throw new BitPayException(null, "Error: GET failed\n" + e.getMessage());
        } catch (ClientProtocolException e) {
            throw new BitPayException(null, "Error: GET failed\n" + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null, "Error: GET failed\n" + e.getMessage());
        }
    }

    /**
     * Send GET request.
     *
     * @param uri the uri
     * @return the http response
     * @throws BitPayException the bit pay exception
     */
    public HttpResponse get(String uri) throws BitPayException {
        return this.get(uri, null, false);
    }

    private HttpResponse delete(String uri, List<BasicNameValuePair> parameters) throws BitPayException {
        try {

            String fullURL = _baseUrl + uri;
            HttpDelete delete = new HttpDelete(fullURL);

            if (parameters != null) {

                fullURL += "?" + URLEncodedUtils.format(parameters, "UTF-8");

                delete.setURI(new URI(fullURL));

                delete.addHeader("X-BitPay-Plugin-Info", Env.BitpayPluginInfo);
                delete.addHeader("x-accept-version", Env.BitpayApiVersion);
                delete.addHeader("x-bitpay-api-frame", Env.BitpayApiFrame);
                delete.addHeader("x-bitpay-api-frame-version", Env.BitpayApiFrameVersion);
                delete.addHeader("x-signature", KeyUtils.sign(_ecKey, fullURL));
                delete.addHeader("x-identity", KeyUtils.bytesToHex(_ecKey.getPubKey()));
            }

            _log.info(delete.toString());
            return _httpClient.execute(delete);

        } catch (URISyntaxException e) {
            throw new BitPayException(null, "Error: DELETE failed\n" + e.getMessage());
        } catch (ClientProtocolException e) {
            throw new BitPayException(null, "Error: DELETE failed\n" + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null, "Error: DELETE failed\n" + e.getMessage());
        }
    }

    /**
     * Send POST request.
     *
     * @param uri  the uri
     * @param json the json
     * @return the http response
     * @throws BitPayException the bit pay exception
     */
    public HttpResponse post(String uri, String json) throws BitPayException {
        return this.post(uri, json, false);
    }

    /**
     * Send POST request.
     *
     * @param uri               the uri
     * @param json              the json
     * @param signatureRequired the signature required
     * @return the http response
     * @throws BitPayException the bit pay exception
     */
    public HttpResponse post(String uri, String json, boolean signatureRequired) throws BitPayException {
        try {
            HttpPost post = new HttpPost(_baseUrl + uri);

            post.setEntity(new ByteArrayEntity(json.getBytes(StandardCharsets.UTF_8)));

            if (signatureRequired) {
                post.addHeader("x-signature", KeyUtils.sign(_ecKey, _baseUrl + uri + json));
                post.addHeader("x-identity", KeyUtils.bytesToHex(_ecKey.getPubKey()));
            }

            post.addHeader("x-accept-version", Env.BitpayApiVersion);
            post.addHeader("x-bitpay-api-frame", Env.BitpayApiFrame);
            post.addHeader("x-bitpay-api-frame-version", Env.BitpayApiFrameVersion);
            post.addHeader("X-BitPay-Plugin-Info", Env.BitpayPluginInfo);
            post.addHeader("Content-Type", "application/json");

            _log.info(post.toString());
            return _httpClient.execute(post);

        } catch (UnsupportedEncodingException e) {
            throw new BitPayException(null, "Error: POST failed\n" + e.getMessage());
        } catch (ClientProtocolException e) {
            throw new BitPayException(null, "Error: POST failed\n" + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null, "Error: POST failed\n" + e.getMessage());
        }
    }

    /**
     * Send PUT request.
     *
     * @param uri  the uri
     * @param json the json
     * @return the http response
     * @throws BitPayException the bit pay exception
     */
    public HttpResponse update(String uri, String json) throws BitPayException {
        try {
            HttpPut put = new HttpPut(_baseUrl + uri);

            put.setEntity(new ByteArrayEntity(json.getBytes(StandardCharsets.UTF_8)));

            put.addHeader("x-signature", KeyUtils.sign(_ecKey, _baseUrl + uri + json));
            put.addHeader("x-identity", KeyUtils.bytesToHex(_ecKey.getPubKey()));
            put.addHeader("x-accept-version", Env.BitpayApiVersion);
            put.addHeader("X-BitPay-Plugin-Info", Env.BitpayPluginInfo);
            put.addHeader("Content-Type", "application/json");
            put.addHeader("x-bitpay-api-frame", Env.BitpayApiFrame);
            put.addHeader("x-bitpay-api-frame-version", Env.BitpayApiFrameVersion);

            _log.info(put.toString());
            return _httpClient.execute(put);

        } catch (UnsupportedEncodingException e) {
            throw new BitPayException(null, "Error: PUT failed\n" + e.getMessage());
        } catch (ClientProtocolException e) {
            throw new BitPayException(null, "Error: PUT failed\n" + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null, "Error: PUT failed\n" + e.getMessage());
        }
    }

    /**
     * Convert HttpResponse for Json string.
     *
     * @param response the response
     * @return the string
     * @throws BitPayException the bit pay exception
     */
    public String responseToJsonString(HttpResponse response) throws BitPayException {
        if (response == null) {
            throw new BitPayException(null, "Error: HTTP response is null");
        }

        try {
            // Get the JSON string from the response.
            HttpEntity entity = response.getEntity();

            String jsonString;

            jsonString = EntityUtils.toString(entity, "UTF-8");
            _log.info("RESPONSE: " + jsonString);
            ObjectMapper mapper = new ObjectMapper();

            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode node = rootNode.get("status");
            if (node != null) {
                if (node.toString().replace("\"", "").equals("error")) {
                    throw new BitPayException(rootNode.get("code").textValue(), rootNode.get("message").textValue());
                }
            }

            node = rootNode.get("error");

            if (node != null) {
                throw new BitPayException(null, "Error: " + node.asText());
            }

            node = rootNode.get("errors");

            if (node != null) {
                String message = "Multiple errors:";

                if (node.isArray()) {
                    for (final JsonNode errorNode : node) {
                        message += "\n" + errorNode.asText();
                    }

                    throw new BitPayException(null, message);
                }
            }

            node = rootNode.get("status");
            if (node != null) {
                if (node.toString().replace("\"", "").equals("error")) {
                    throw new BitPayException(rootNode.get("code").textValue(), rootNode.get("message").textValue());
                }
                if (node.toString().replace("\"", "").equals("success")) {
                	node = rootNode.get("data");
                	
                	if (node.toString().equals("{}")) {
                		return rootNode.toString();
                	}
                }
            }
            
            node = rootNode.get("data");

            if (node != null) {
                jsonString = node.toString();
            }

            return jsonString;

        } catch (ParseException e) {
            throw new BitPayException(null, "failed to retrieve HTTP response body : " + e.getMessage());
        } catch (JsonMappingException e) {
            throw new BitPayException(null, "failed to parse json response to map : " + e.getMessage());
        } catch (BitPayException e) {
            throw new BitPayException(e.getStatusCode(), e.getReasonPhrase());
        } catch (Exception e) {
            throw new BitPayException(null, "failed to retrieve HTTP response body : " + e.getMessage());
        }
    }

    private String getGuid() {
        UUID uuid = UUID.randomUUID();

        return uuid.toString();
    }

    /**
     * Loads the configuration file (JSON).
     *
     * @throws BitPayException BitPayException class
     */
    public void GetConfig() throws BitPayException {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(this._configFilePath));
            //create ObjectMapper instance
            ObjectMapper mapper = new ObjectMapper();
            //read JSON like DOM Parser
            JsonNode rootNode = mapper.readTree(jsonData);
            JsonNode bitPayConfiguration = rootNode.path("BitPayConfiguration");
            this._configuration = new ObjectMapper().readValue(bitPayConfiguration.toString(), Config.class);
            this._env = this._configuration.getEnvironment();
        } catch (JsonProcessingException e) {
            throw new BitPayException(null, "failed to read configuration file : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null, "failed to read configuration file : " + e.getMessage());
        }
    }

    /**
     * Builds the configuration object.
     *
     * @param privateKey The full path to the securely located private key.
     * @param tokens     Env.Tokens object containing the BitPay's API tokens.
     * @throws BitPayException BitPayException class
     */
    public void BuildConfig(String privateKey, Env.Tokens tokens) throws BitPayException {
        try {
            String keyHex = "", keyFile = "";
            File privateKeyFile = new File(privateKey);
            if (!privateKeyFile.exists()) {
                try {
                    _ecKey = KeyUtils.createEcKeyFromHexString(privateKey);
                    keyHex = privateKey;
                } catch (Exception e) {
                    throw new BitPayException(null, "Private Key file not found");
                }
            } else {
                try {
                    keyFile = privateKey;
                } catch (Exception e) {
                    throw new BitPayException(null, "Could not read private Key file");
                }
            }

            Config config = new Config();
            config.setEnvironment(this._env);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode ApiTokens = mapper.valueToTree(tokens);

            ObjectNode envConfig = mapper.createObjectNode();
            envConfig.put("PrivateKeyPath", keyFile);
            envConfig.put("PrivateKey", keyHex);
            envConfig.put("ApiTokens", ApiTokens);

            ObjectNode envTarget = mapper.createObjectNode();
            envTarget.put(this._env, envConfig);

            config.setEnvConfig(envTarget);
            this._configuration = config;
        } catch (Exception e) {
            throw new BitPayException(null, "failed to process configuration : " + e.getMessage());
        }
    }

    /**
     * Load currencies info.
     *
     * @throws BitPayException BitPayException class //TODO test and integrate
     */
    private void loadCurrencies() throws BitPayException {
        try {
            HttpEntity newEntity = this.get("currencies").getEntity();

            String jsonString;

            jsonString = EntityUtils.toString(newEntity, "UTF-8");

            ObjectMapper mapper = new ObjectMapper();

            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode node = rootNode.get("data");

            if (node != null) {
                jsonString = node.toString();
            }

            _currenciesInfo = new ArrayList(Arrays.asList(new ObjectMapper().readValue(jsonString, Map[].class)));

        } catch (Exception e) {
            // No action required
        }
    }



    /**
     * Gets info for specific currency.
     *
     * @param currencyCode String Currency code for which the info will be retrieved.
     * @return Map|null
     */
    public static Map getCurrencyInfo(String currencyCode) {
    	
        for (int i = 0; i < _currenciesInfo.size(); i++) {
            Map currencyInfo = new ObjectMapper().convertValue(_currenciesInfo.get(i), Map.class);

            if (currencyInfo.get("code").toString().equals(currencyCode)) {
                return currencyInfo;
            }
        }

        return null;
    }

    /**
     * Sets the logger level of reporting.
     *
     * @param loggerLevel int BitPayLogger constant (OFF, INFO, WARN, ERR, DEBUG)
     */
    public void setLoggerLevel(int loggerLevel) {
        _log = new BitPayLogger(loggerLevel);
    }
}
