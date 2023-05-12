/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk;

import com.bitpay.sdk.client.AuthorizationClient;
import com.bitpay.sdk.client.BillClient;
import com.bitpay.sdk.client.BitPayClient;
import com.bitpay.sdk.client.CurrencyClient;
import com.bitpay.sdk.client.HttpRequestFactory;
import com.bitpay.sdk.client.InvoiceClient;
import com.bitpay.sdk.client.LedgerClient;
import com.bitpay.sdk.client.PayoutClient;
import com.bitpay.sdk.client.PayoutRecipientsClient;
import com.bitpay.sdk.client.RateClient;
import com.bitpay.sdk.client.RefundClient;
import com.bitpay.sdk.client.SettlementClient;
import com.bitpay.sdk.client.WalletClient;
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
import com.bitpay.sdk.model.bill.Bill;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.invoice.Invoice;
import com.bitpay.sdk.model.invoice.InvoiceEventToken;
import com.bitpay.sdk.model.invoice.Refund;
import com.bitpay.sdk.model.Ledger.Ledger;
import com.bitpay.sdk.model.Ledger.LedgerEntry;
import com.bitpay.sdk.model.Payout.Payout;
import com.bitpay.sdk.model.Payout.PayoutRecipient;
import com.bitpay.sdk.model.Payout.PayoutRecipients;
import com.bitpay.sdk.model.Rate.Rate;
import com.bitpay.sdk.model.Rate.Rates;
import com.bitpay.sdk.model.Settlement.Settlement;
import com.bitpay.sdk.model.Wallet.Wallet;
import com.bitpay.sdk.util.GuidGenerator;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.bitpay.sdk.util.KeyUtils;
import com.bitpay.sdk.util.TokenContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.bitcoinj.core.ECKey;

/**
 * The type Client.
 */
public class Client {

    private final GuidGenerator guidGenerator;
    private final BitPayClient bitPayClient;
    private final TokenContainer tokenContainer;

    /**
     * Return the identity of this client (i.e. the public key).
     */
    private String identity;

    /**
     * Constructor for POS facade.
     *
     * @param token POS token
     * @throws BitPayException the bit pay exception
     */
    public Client(PosToken token) throws BitPayException {
        this(token, Environment.PROD);
    }

    /**
     * Constructor for POS facade.
     *
     * @param token       POS token
     * @param environment Environment
     * @throws BitPayException the bit pay exception
     */
    public Client(PosToken token, Environment environment) throws BitPayException {
        if (Objects.isNull(token) || Objects.isNull(environment)) {
            throw new BitPayException(null, "Missing required constructor parameters");
        }

        this.tokenContainer = new TokenContainer();
        this.tokenContainer.addPos(token.value());
        this.bitPayClient = new BitPayClient(
            getHttpClient(null, null),
            new HttpRequestFactory(),
            getBaseUrl(environment),
            null
        );
        this.guidGenerator = new GuidGenerator();
    }

    /**
     * Constructor for use if the keys and SIN are managed by this library.
     *
     * @param environment      Target environment. Options: Env.Test / Env.Prod
     * @param privateKey       The full path to the securely located private key or the HEX key value.
     * @param tokenContainer   Object containing the available tokens.
     * @param proxyDetails     HttpHost Optional Proxy setting (set to NULL to ignore)
     * @param proxyCredentials CredentialsProvider Optional Proxy Basic Auth Credentials (set to NULL to ignore)
     * @throws BitPayException BitPayException class
     */
    public Client(
        Environment environment,
        PrivateKey privateKey,
        TokenContainer tokenContainer,
        HttpHost proxyDetails,
        CredentialsProvider proxyCredentials
    ) throws BitPayException {
        try {
            ECKey ecKey = getEcKey(privateKey);
            this.tokenContainer = tokenContainer;
            this.deriveIdentity(ecKey);
            this.bitPayClient = new BitPayClient(
                getHttpClient(proxyDetails, proxyCredentials),
                new HttpRequestFactory(),
                getBaseUrl(environment),
                ecKey
            );
            this.guidGenerator = new GuidGenerator();
        } catch (Exception e) {
            throw new BitPayException(null,
                "failed to deserialize BitPay server response (Config) : " + e.getMessage());
        }
    }

    /**
     * Constructor for use if the keys and SIN are managed by this library.
     *
     * @param configFilePath   The path to the configuration file.
     * @param proxy            HttpHost Optional Proxy setting (set to NULL to ignore)
     * @param proxyCredentials CredentialsProvider Optional Proxy Basic Auth Credentials (set to NULL to ignore)
     * @throws BitPayException BitPayException class
     */
    public Client(ConfigFilePath configFilePath, HttpHost proxy, CredentialsProvider proxyCredentials) throws BitPayException {
        try {
            Config config = this.buildConfigFromFile(configFilePath);
            this.tokenContainer = new TokenContainer(config);
            ECKey ecKey = this.getEcKey(config);
            if (Objects.isNull(ecKey)) {
                throw new BitPayException(null, "Missing ECKey");
            }

            this.deriveIdentity(ecKey);
            this.bitPayClient = new BitPayClient(
                getHttpClient(proxy, proxyCredentials),
                new HttpRequestFactory(),
                getBaseUrl(config.getEnvironment()),
                ecKey
            );
            this.guidGenerator = new GuidGenerator();
        } catch (JsonProcessingException | URISyntaxException e) {
            throw new BitPayException(null,
                "failed to deserialize BitPay server response (Config) : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null,
                "failed to deserialize BitPay server response (Config) : " + e.getMessage());
        }
    }

    /**
     * Constructor for all injected classes.
     *
     * @param bitPayClient   BitPayClient
     * @param identity       Identity
     * @param tokenContainer TokenContainer
     * @param GuidGenerator  GuidGenerator
     */
    public Client(
        BitPayClient bitPayClient,
        String identity,
        TokenContainer tokenContainer,
        GuidGenerator GuidGenerator
    ) {
        this.bitPayClient = bitPayClient;
        this.identity = identity;
        this.tokenContainer = tokenContainer;
        this.guidGenerator = GuidGenerator;
    }

    /**
     * Create pos (light) client.
     *
     * @param token the token
     * @return the client
     * @throws BitPayException the bit pay exception
     */
    public static Client createPosClient(PosToken token) throws BitPayException {
        return new Client(token);
    }

    /**
     * Create pos (light) client.
     *
     * @param token the token
     * @param environment environment
     * @return the client
     * @throws BitPayException the bit pay exception
     */
    public static Client createPosClient(PosToken token, Environment environment) throws BitPayException {
        return new Client(token, environment);
    }

    /**
     * Create standard client.
     *
     * @param privateKey the private key
     * @param tokenContainer the token container
     * @param environment environment
     * @return Client Client
     * @throws BitPayException BitPayException
     */
    public static Client createClientByPrivateKey(
        PrivateKey privateKey,
        TokenContainer tokenContainer,
        Environment environment
    ) throws BitPayException {
        Environment env = Objects.isNull(environment) ? Environment.PROD : environment;

        return new Client(env, privateKey, tokenContainer, null, null);
    }

    /**
     * Create standard client.
     *
     * @param configFilePath the config file path
     * @return the client
     * @throws BitPayException the bit pay exception
     */
    public static Client createClientByConfigFilePath(ConfigFilePath configFilePath) throws BitPayException {
        return new Client(configFilePath, null, null);
    }


    /**
     * Authorize this client with the server using the specified pairing code (Server Initiated Pairing).
     *
     * @see <a href="https://developer.bitpay.com/reference/get-token">Request an API Token</a>
     *
     * @param pairingCode A code obtained from the server; typically from bitpay.com/api-tokens.
     * @throws BitPayException BitPayException class
     */
    public void authorizeClient(String pairingCode) throws BitPayException {
        this.createAuthorizationClient().authorizeClient(pairingCode);
    }

    /**
     * Request a pairing code from the BitPay server (Client Initiated Pairing).
     *
     * @see <a href="https://developer.bitpay.com/reference/get-token">Request an API Token</a>
     *
     * @param facade Defines the level of API access being requested
     * @return A pairing code for claim at https://bitpay.com/dashboard/merchant/api-tokens.
     * @throws BitPayException BitPayException class
     */
    public String authorizeClient(Facade facade) throws BitPayException {
        return this.createAuthorizationClient().authorizeClient(facade);
    }

    /**
     * Retrieve a token associated with a known resource. The token is used to access other related resources.
     *
     * @param facade The identifier for the desired resource.
     * @return The token associated with resource.
     * @throws BitPayException BitPayException class
     */
    public String getAccessToken(Facade facade) throws BitPayException {
        return this.tokenContainer.getAccessToken(facade);
    }

    /**
     * Retrieve a token associated with a known resource. The token is used to access other related resources.
     *
     * @param key The identifier for the desired resource.
     * @return The token associated with resource.
     * @throws BitPayException BitPayException class
     */
    public String getAccessToken(String key) throws BitPayException {
        return this.tokenContainer.getAccessToken(key);
    }

    /**
     * Gets info for specific currency.
     *
     * @param currencyCode String Currency code for which the info will be retrieved.
     * @return Map|null
     * @throws BitPayException the bit pay exception
     */
    public Map<String, Object> getCurrencyInfo(String currencyCode) throws BitPayException {
        CurrencyClient client = CurrencyClient.getInstance(this.bitPayClient);
        return client.getInfo(currencyCode);
    }

    /**
     * Create a BitPay invoice.
     *
     * @see <a href="https://developer.bitpay.com/reference/create-an-invoice">Create an Invoice</a>
     *
     * @param invoice An Invoice object with request parameters defined.
     * @return A BitPay generated Invoice object.
     * @throws InvoiceCreationException InvoiceCreationException class
     */
    public Invoice createInvoice(Invoice invoice) throws InvoiceCreationException {
        try {
            InvoiceClient client = getInvoiceClient();
            Facade facade = getFacadeBasedOnAccessToken();
            boolean signRequest = isSignRequest(facade);

            return client.create(invoice, facade, signRequest);
        } catch (BitPayException ex) {
            throw new InvoiceCreationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceCreationException(null, e.getMessage());
        }
    }

    /**
     * Create a BitPay invoice.
     *
     * @see <a href="https://developer.bitpay.com/reference/create-an-invoice">Create an Invoice</a>
     *
     * @param invoice An Invoice object with request parameters defined.
     * @param facade The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay generated Invoice object.
     * @throws InvoiceCreationException InvoiceCreationException class
     */
    public Invoice createInvoice(Invoice invoice, Facade facade, boolean signRequest) throws InvoiceCreationException {
        try {
            InvoiceClient client = getInvoiceClient();

            return client.create(invoice, facade, signRequest);
        } catch (BitPayException ex) {
            throw new InvoiceCreationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceCreationException(null, e.getMessage());
        }
    }

    /**
     * Retrieve a BitPay invoice by invoice id using the public facade.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-an-invoice">Retrieve an Invoice</a>
     *
     * @param invoiceId The id of the invoice to retrieve.
     * @return A BitPay Invoice object.
     * @throws BitPayException the bit pay exception
     */
    public Invoice getInvoice(String invoiceId) throws BitPayException {
        Facade facade = getFacadeBasedOnAccessToken();
        boolean signRequest = isSignRequest(facade);

        try {
            return this.getInvoiceClient().get(invoiceId, facade, signRequest);
        } catch (BitPayException ex) {
            throw new InvoiceQueryException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceQueryException(null, e.getMessage());
        }
    }

    /**
     * Retrieve a BitPay invoice by invoice id.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-an-invoice">Retrieve an Invoice</a>
     *
     * @param invoiceId The id of the invoice to retrieve.
     * @param facade The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay Invoice object.
     * @throws BitPayException the bit pay exception
     */
    public Invoice getInvoice(String invoiceId, Facade facade, boolean signRequest) throws BitPayException {
        try {
            return this.getInvoiceClient().get(invoiceId, facade, signRequest);
        } catch (BitPayException ex) {
            throw new InvoiceQueryException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceQueryException(null, e.getMessage());
        }
    }

    /**
     * Retrieve a BitPay invoice by guid using the specified facade.
     * The client must have been previously authorized for the specified facade.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-an-invoice-by-guid">Retrieve an Invoice by GUID</a>
     *
     * @param guid        The guid of the invoice to retrieve.
     * @return A BitPay Invoice object.
     * @throws InvoiceQueryException InvoiceQueryException class
     */
    public Invoice getInvoiceByGuid(String guid) throws InvoiceQueryException {
        Facade facade = getFacadeBasedOnAccessToken();
        boolean signRequest = isSignRequest(facade);
        return this.getInvoiceClient().getByGuid(guid, facade, signRequest);
    }

    /**
     * Retrieve a BitPay invoice by guid using the specified facade.
     * The client must have been previously authorized for the specified facade.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-an-invoice-by-guid">Retrieve an Invoice by GUID</a>
     *
     * @param guid        The guid of the invoice to retrieve.
     * @param facade      The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay Invoice object.
     * @throws InvoiceQueryException InvoiceQueryException class
     */
    public Invoice getInvoiceByGuid(String guid, Facade facade, Boolean signRequest) throws InvoiceQueryException {
        return this.getInvoiceClient().getByGuid(guid, facade, signRequest);
    }

    /**
     * Retrieve a collection of BitPay invoices.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-invoices-filtered-by-query">Retrieve Invoices Filtered by Query</a>
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
    public List<Invoice> getInvoices(
        String dateStart,
        String dateEnd,
        String status,
        String orderId,
        Integer limit,
        Integer offset
    ) throws BitPayException, InvoiceQueryException {
        return this.getInvoiceClient().getInvoices(dateStart, dateEnd, status, orderId, limit, offset);
    }

    /**
     * Retrieves a bus token which can be used to subscribe to invoice events.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-an-event-token">Retrieve an Event Token</a>
     *
     * @param invoiceId the id of the invoice for which you want to fetch an event token.
     * @return InvoiceEventToken event token
     * @throws BitPayException BitPayException
     * @since 8.8.0
     */
    public InvoiceEventToken getInvoiceEventToken(String invoiceId) throws BitPayException {
        return this.getInvoiceClient().getInvoiceEventToken(invoiceId);
    }

    /**
     * Update a BitPay invoice with communication method.
     *
     * @see <a href="https://developer.bitpay.com/reference/update-an-invoice">Update an Invoice</a>
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
        return this.getInvoiceClient().update(invoiceId, buyerSms, smsCode, buyerEmail, autoVerify);
    }

    /**
     * Pay a BitPay invoice with a mock transaction. Available only on test env.
     *
     * @param invoiceId The id of the invoice to updated.
     * @param status    The status of the invoice to be updated, can be "confirmed" or "complete".
     * @return A BitPay generated Invoice object.
     * @throws BitPayException        BitPayException class
     * @throws InvoiceUpdateException InvoiceUpdateException class
     */
    public Invoice payInvoice(String invoiceId, String status) throws BitPayException, InvoiceUpdateException {
        return this.getInvoiceClient().pay(invoiceId, status);
    }

    /**
     * Delete a previously created BitPay invoice.
     *
     * @see <a href="https://developer.bitpay.com/reference/cancel-an-invoice">Cancel an Invoice</a>
     *
     * @param invoiceId The Id of the BitPay invoice to be canceled.
     * @return A BitPay generated Invoice object.
     * @throws InvoiceCancellationException InvoiceCancellationException class
     * @throws BitPayException              BitPayException class
     */
    public Invoice cancelInvoice(String invoiceId) throws InvoiceCancellationException, BitPayException {
        return this.getInvoiceClient().cancel(invoiceId);
    }

    /**
     * Cancellation will require EITHER an SMS or E-mail to have already been set if the invoice has proceeded to
     * the point where it may have been paid, unless using forceCancel parameter.
     *
     * @see <a href="https://developer.bitpay.com/reference/cancel-an-invoice">Cancel an Invoice</a>
     *
     * @param invoiceId   The Id of the BitPay invoice to be canceled.
     * @param forceCancel If 'true' it will cancel the invoice even if no contact information is present.
     * @return A BitPay generated Invoice object.
     * @throws InvoiceCancellationException InvoiceCancellationException class
     * @throws BitPayException              BitPayException class
     */
    public Invoice cancelInvoice(String invoiceId, Boolean forceCancel)
        throws InvoiceCancellationException, BitPayException {
        return this.getInvoiceClient().cancel(invoiceId, forceCancel);
    }

    /**
     * Cancellation will require EITHER an SMS or E-mail to have already been set if the invoice has proceeded to
     * the point where it may have been paid, unless using forceCancel parameter.
     *
     * @see <a href="https://developer.bitpay.com/reference/cancel-an-invoice-by-guid">Cancel an Invoice by GUID</a>
     *
     * @param guid GUID A passthru variable provided by the merchant and designed to be used by the merchant to
     *             correlate the invoice with an order ID in their system, which can be used as a lookup variable
     *             in Retrieve Invoice by GUID.
     * @return Invoice Invoice
     * @throws BitPayException BitPayException class
     */
    public Invoice cancelInvoiceByGuid(String guid) throws BitPayException {
        return this.getInvoiceClient().cancelByGuid(guid, false);
    }

    /**
     * Cancellation will require EITHER an SMS or E-mail to have already been set if the invoice has proceeded to
     * the point where it may have been paid, unless using forceCancel parameter.
     *
     * @see <a href="https://developer.bitpay.com/reference/cancel-an-invoice-by-guid">Cancel an Invoice by GUID</a>
     *
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
        return this.getInvoiceClient().cancelByGuid(guid, forceCancel);
    }

    /**
     * The intent of this call is to address issues when BitPay sends a webhook but the client doesn't receive it,
     * so the client can request that BitPay resend it.
     *
     * @see <a href="https://developer.bitpay.com/reference/request-an-invoice-webhook-to-be-resent">Request an Invoice Webhook to be Resent</a>
     *
     * @param invoiceId The id of the invoice for which you want the last webhook to be resent.
     * @return Boolean status of request
     * @throws BitPayException BitPayException
     */
    public Boolean requestInvoiceWebhookToBeResent(String invoiceId) throws BitPayException {
        return this.getInvoiceClient().requestInvoiceWebhookToBeResent(invoiceId);
    }

    /**
     * Create a refund for a BitPay invoice.
     *
     * @see <a href="https://developer.bitpay.com/reference/create-a-refund-request">Create a Refund Request</a>
     *
     * @param invoiceId          The BitPay invoice Id having the associated refund to be created.
     * @param amount             Amount to be refunded in the currency indicated.
     * @param preview            Whether to create the refund request as a preview (which will not be acted on until status is updated)
     * @param immediate          Whether funds should be removed from merchant ledger immediately on submission or at time of processing
     * @param buyerPaysRefundFee Whether the buyer should pay the refund fee (default is merchant)
     * @param reference          Present only if specified. Used as reference label for the refund. Max str length = 100
     * @return An updated Refund Object
     * @throws RefundCreationException RefundCreationException class
     * @throws BitPayException         BitPayException class
     */
    public Refund createRefund(
        String invoiceId,
        Double amount,
        Boolean preview,
        Boolean immediate,
        Boolean buyerPaysRefundFee,
        String reference
    ) throws RefundCreationException, BitPayException {
        Refund refund = new Refund();
        refund.setInvoice(invoiceId);
        refund.setAmount(amount);
        refund.setPreview(preview);
        refund.setImmediate(immediate);
        refund.setBuyerPaysRefundFee(buyerPaysRefundFee);
        refund.setReference(reference);

        return this.getRefundClient().create(refund);
    }

    /**
     * Create a refund for a BitPay invoice.
     *
     * @see <a href="https://developer.bitpay.com/reference/create-a-refund-request">Create a Refund Request</a>
     *
     * @param invoiceId          The BitPay invoice Id having the associated refund to be created.
     * @param amount             Amount to be refunded in the currency indicated.
     * @param preview            Whether to create the refund request as a preview (which will not be acted on until status is updated)
     * @param immediate          Whether funds should be removed from merchant ledger immediately on submission or at time of processing
     * @param buyerPaysRefundFee Whether the buyer should pay the refund fee (default is merchant)
     * @param reference          Present only if specified. Used as reference label for the refund. Max str length = 100
     * @param guid               Variable provided by the merchant and designed to be used by the merchant to correlate the refund with a refund ID in their system
     * @return An updated Refund Object
     * @throws RefundCreationException RefundCreationException class
     * @throws BitPayException         BitPayException class
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

        return this.getRefundClient().create(refund);
    }

    /**
     * Create a refund for a BitPay invoice.
     *
     * @see <a href="https://developer.bitpay.com/reference/create-a-refund-request">Create a Refund Request</a>
     *
     * @param refund Refund class which provided data - invoice id, amount, preview, immediate, buyerPaysRefundFee, reference and guid for create request
     * @return An updated Refund Object
     * @throws RefundCreationException RefundCreationException class
     * @throws BitPayException         BitPayException class
     * @since 8.7.0
     */
    public Refund createRefund(Refund refund) throws
        RefundCreationException, BitPayException {
        return this.getRefundClient().create(refund);
    }

    /**
     * Retrieve a previously made refund request on a BitPay invoice.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-a-refund-request">Retrieve a Refund Request</a>
     *
     * @param refundId The BitPay refund ID.
     * @return A BitPay Refund object with the associated Refund object.
     * @throws RefundQueryException RefundQueryException class
     * @throws BitPayException      BitPayException class
     */
    public Refund getRefund(String refundId) throws RefundQueryException, BitPayException {
        return this.getRefundClient().getById(refundId);
    }

    /**
     * Retrieve a previously made refund request on a BitPay invoice.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-a-refund-by-guid-request">Retrieve a Refund by GUID Request</a>
     *
     * @param guid The BitPay refund GUID.
     * @return A BitPay Refund object with the associated Refund object.
     * @throws BitPayException      BitPayException class
     * @since 8.7.0
     */
    public Refund getRefundByGuid(String guid) throws BitPayException {
        return this.getRefundClient().getByGuid(guid);
    }

    /**
     * Retrieve all refund requests on a BitPay invoice.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-refunds-of-an-invoice">Retrieve Refunds of an Invoice</a>
     *
     * @param invoiceId The BitPay invoice object having the associated refunds.
     * @return A list of BitPay Refund objects with the associated Refund objects.
     * @throws RefundQueryException RefundQueryException class
     * @throws BitPayException      BitPayException class
     */
    public List<Refund> getRefunds(String invoiceId) throws RefundQueryException, BitPayException {
        return this.getRefundClient().getRefundsByInvoiceId(invoiceId);
    }

    /**
     * Update the status of a BitPay invoice.
     *
     * @see <a href="https://developer.bitpay.com/reference/update-a-refund-request">Update a Refund Request</a>
     *
     * @param refundId A BitPay refund ID.
     * @param status   The new status for the refund to be updated.
     * @return A BitPay generated Refund object.
     * @throws RefundUpdateException RefundUpdateException class
     * @throws BitPayException       BitPayException class
     */
    public Refund updateRefund(String refundId, String status) throws RefundUpdateException, BitPayException {
        return this.getRefundClient().update(refundId, status);
    }

    /**
     * Update the status of a BitPay invoice.
     *
     * @see <a href="https://developer.bitpay.com/reference/update-a-refund-by-guid-request">Update a Refund by GUID Request</a>
     *
     * @param guid A BitPay refund Guid.
     * @param status   The new status for the refund to be updated.
     * @return A BitPay generated Refund object.
     * @throws RefundUpdateException RefundUpdateException class
     * @throws BitPayException       BitPayException class
     * @since 8.7.0
     */
    public Refund updateRefundByGuid(String guid, String status) throws RefundUpdateException, BitPayException {
        return this.getRefundClient().updateByGuid(guid, status);
    }

    /**
     * Send a refund notification.
     *
     * @see <a href="https://developer.bitpay.com/reference/request-a-refund-notification-to-be-resent">Request a Refund Notification to be Resent</a>
     *
     * @param refundId A BitPay refund ID.
     * @return An updated Refund Object
     * @throws RefundCreationException RefundCreationException class
     * @throws BitPayException         BitPayException class
     */
    public Boolean sendRefundNotification(String refundId) throws RefundCreationException, BitPayException {
        return this.getRefundClient().sendRefundNotification(refundId);
    }

    /**
     * Cancel a previously submitted refund request on a BitPay invoice.
     *
     * @see <a href="https://developer.bitpay.com/reference/cancel-a-refund-request">Cancel a Refund Request</a>
     *
     * @param refundId The refund Id for the refund to be canceled.
     * @return An updated Refund Object.
     * @throws RefundCancellationException RefundCancellationException class
     * @throws BitPayException             BitPayException class
     */
    public Refund cancelRefund(String refundId) throws RefundCancellationException, BitPayException {
        return this.getRefundClient().cancel(refundId);
    }

    /**
     * Cancel a previously submitted refund request on a BitPay invoice.
     *
     * @see <a href="https://developer.bitpay.com/reference/cancel-a-refund-by-guid-request">Cancel a Refund by GUID Request</a>
     *
     * @param guid The refund Guid for the refund to be canceled.
     * @return An updated Refund Object.
     * @throws RefundCancellationException RefundCancellationException class
     * @throws BitPayException             BitPayException class
     * @since 8.7.0
     */
    public Refund cancelRefundByGuid(String guid) throws RefundCancellationException, BitPayException {
        return this.getRefundClient().cancelByGuid(guid);
    }

    /**
     * Create a BitPay bill.
     *
     * @see <a href="https://developer.bitpay.com/reference/create-a-bill">Create a Bill</a>
     *
     * @param bill An Bill object with request parameters defined.
     * @return A BitPay generated Bill object.
     * @throws BillCreationException BillCreationException class
     * @throws BitPayException       the bit pay exception
     */
    public Bill createBill(Bill bill) throws BillCreationException, BitPayException {
        Facade facade = this.getFacadeBasedOnAccessToken();
        boolean signRequest = isSignRequest(facade);

        return this.createBill(bill, facade, signRequest);
    }

    /**
     * Create a BitPay Bill.
     *
     * @see <a href="https://developer.bitpay.com/reference/create-a-bill">Create a Bill</a>
     *
     * @param bill        A Bill object with request parameters defined.
     * @param facade      The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay generated Bill object.
     * @throws BitPayException       BitPayException class
     * @throws BillCreationException BillCreationException class
     */
    public Bill createBill(Bill bill, Facade facade, boolean signRequest)
        throws BitPayException, BillCreationException {
        return this.getBillClient().create(bill, facade, signRequest);
    }

    /**
     * Retrieve a BitPay bill by bill id using the public facade.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-a-bill">Retrieve a Bill</a>
     *
     * @param billId The id of the bill to retrieve.
     * @return A BitPay Bill object.
     * @throws BitPayException the bit pay exception
     */
    public Bill getBill(String billId) throws BitPayException {
        Facade facade = this.getFacadeBasedOnAccessToken();
        boolean signRequest = isSignRequest(facade);

        return this.getBill(billId, facade, signRequest);
    }

    /**
     * Retrieve a BitPay bill by bill id using the specified facade.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-a-bill">Retrieve a Bill</a>
     *
     * @param billId      The id of the bill to retrieve.
     * @param facade      The facade used to retrieve it.
     * @param signRequest Signed request.
     * @return A BitPay Bill object.
     * @throws BitPayException    BitPayException class
     * @throws BillQueryException BillQueryException class
     */
    public Bill getBill(String billId, Facade facade, boolean signRequest) throws BitPayException, BillQueryException {
        return this.getBillClient().get(billId, facade, signRequest);
    }

    /**
     * Retrieve a collection of BitPay bills.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-bills-by-status">Retrieve Bills by Status</a>
     *
     * @param status The status to filter the bills.
     * @return A list of BitPay Bill objects.
     * @throws BitPayException    BitPayException class
     * @throws BillQueryException BillQueryException class
     */
    public List<Bill> getBills(String status) throws BitPayException, BillQueryException {
        return this.getBillClient().getBills(status);
    }

    /**
     * Retrieve a collection of BitPay bills.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-bills-by-status">Retrieve Bills by Status</a>
     *
     * @return A list of BitPay Bill objects.
     * @throws BitPayException    BitPayException class
     * @throws BillQueryException BillQueryException class
     */
    public List<Bill> getBills() throws BitPayException, BillQueryException {
        return this.getBillClient().getBills();
    }

    /**
     * Update a BitPay Bill.
     *
     * @see <a href="https://developer.bitpay.com/reference/update-a-bill">Update a Bill</a>
     *
     * @param bill   A Bill object with the parameters to update defined.
     * @param billId The Id of the Bill to udpate.
     * @return An updated Bill object.
     * @throws BitPayException     BitPayException class
     * @throws BillUpdateException BillUpdateException class
     */
    public Bill updateBill(Bill bill, String billId) throws BitPayException, BillUpdateException {
        return this.getBillClient().update(bill, billId);
    }

    /**
     * Deliver a BitPay Bill.
     *
     * @see <a href="https://developer.bitpay.com/reference/deliver-a-bill-via-email">Deliver a Bill Via Email</a>
     *
     * @param billId    The id of the requested bill.
     * @param billToken The token of the requested bill.
     * @return A response status returned from the API.
     * @throws BitPayException the bit pay exception
     */
    public String deliverBill(String billId, String billToken) throws BitPayException {
        Facade facade = this.getFacadeBasedOnAccessToken();
        boolean signRequest = isSignRequest(facade);

        return this.deliverBill(billId, billToken, signRequest);
    }

    /**
     * Deliver a BitPay Bill.
     *
     * @see <a href="https://developer.bitpay.com/reference/deliver-a-bill-via-email">Deliver a Bill Via Email</a>
     *
     * @param billId      The id of the requested bill.
     * @param billToken   The token of the requested bill.
     * @param signRequest Allow unsigned request
     * @return A response status returned from the API.
     * @throws BillDeliveryException BillDeliveryException class
     */
    public String deliverBill(String billId, String billToken, boolean signRequest) throws BillDeliveryException {
        return this.getBillClient().deliver(billId, billToken, signRequest);
    }

    /**
     * Retrieve the rates for a cryptocurrency / fiat pair. See https://bitpay.com/bitcoin-exchange-rates.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-the-rates-for-a-cryptocurrency-fiat-pair">Retrieve the rates for a cryptocurrency / fiat pair</a>
     *
     * @param baseCurrency the cryptocurrency for which you want to fetch the rates.
     *                     Current supported values are BTC and BCH.
     * @param currency the fiat currency for which you want to fetch the baseCurrency rates
     * @return A Rate object populated with the BitPay exchange rate table.
     * @throws RateQueryException RateQueryException class
     * @since 8.8.0
     */
    public Rate getRate(String baseCurrency, String currency) throws RateQueryException {
        return this.getRateClient().get(baseCurrency, currency);
    }

    /**
     * Retrieve the exchange rate table maintained by BitPay.  See https://bitpay.com/bitcoin-exchange-rates.
     *
     * @return A Rates object populated with the BitPay exchange rate table.
     * @throws RateQueryException RateQueryException class
     */
    public Rates getRates() throws RateQueryException {
        return this.getRateClient().getRates();
    }

    /**
     * Retrieve the exchange rate table maintained by BitPay by baseCurrency. See https://bitpay.com/bitcoin-exchange-rates.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-all-the-rates-for-a-given-cryptocurrency">Retrieve all the rates for a given cryptocurrency</a>
     *
     * @param baseCurrency the cryptocurrency for which you want to fetch the rates.
     *                     Current supported values are BTC and BCH.
     * @return A Rates object populated with the BitPay exchange rate table.
     * @throws RateQueryException RateQueryException class
     * @since 8.8.0
     */
    public Rates getRates(String baseCurrency) throws RateQueryException {
        return this.getRateClient().getRates(baseCurrency);
    }

    /**
     * Retrieve a list of ledgers entries by currency and date range using the merchant facade.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-ledger-entries">Retrieve Ledger Entries</a>
     *
     * @param currency  The three digit currency string for the ledger to retrieve.
     * @param dateStart The first date for the query filter.
     * @param dateEnd   The last date for the query filter.
     * @return Ledger entries list.
     * @throws BitPayException      BitPayException class
     * @throws LedgerQueryException LedgerQueryException class
     */
    public List<LedgerEntry> getLedgerEntries(String currency, String dateStart, String dateEnd) throws BitPayException,
        LedgerQueryException {
        return this.getLedgerClient().getEntries(currency, dateStart, dateEnd);
    }

    /**
     * Retrieve a list of ledgers using the merchant facade.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-account-balances">Retrieve Account Balances</a>
     *
     * @return A list of Ledger objects populated with the currency and current balance of each one.
     * @throws BitPayException      BitPayException class
     * @throws LedgerQueryException LedgerQueryException class
     */
    public List<Ledger> getLedgers() throws BitPayException, LedgerQueryException {
        return this.getLedgerClient().getLedgers();
    }

    /**
     * Submit BitPay Payout Recipients.
     *
     * @see <a href="https://developer.bitpay.com/reference/invite-recipients">Invite Recipients</a>
     *
     * @param recipients PayoutRecipients A PayoutRecipients object with request parameters defined.
     * @return array A list of BitPay PayoutRecipients objects.
     * @throws BitPayException                  BitPayException class
     * @throws PayoutRecipientCreationException PayoutRecipientCreationException class
     */
    public List<PayoutRecipient> submitPayoutRecipients(PayoutRecipients recipients) throws BitPayException,
        PayoutRecipientCreationException {
        return this.getPayoutRecipientsClient().submit(recipients);
    }

    /**
     * Retrieve a collection of BitPay Payout Recipients.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-recipients-by-status">Retrieve Recipients by Status</a>
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
        return this.getPayoutRecipientsClient().getRecipientsByFilters(status, limit, offset);
    }

    /**
     * Retrieve a BitPay payout recipient by batch id using.  The client must have been previously authorized for the
     * payout facade.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-a-recipient">Retrieve a Recipient</a>
     *
     * @param recipientId String The id of the recipient to retrieve.
     * @return PayoutRecipient A BitPay PayoutRecipient object.
     * @throws BitPayException               BitPayException class
     * @throws PayoutRecipientQueryException PayoutRecipientQueryException class
     */
    public PayoutRecipient getPayoutRecipient(String recipientId)
        throws BitPayException, PayoutRecipientQueryException {
        return this.getPayoutRecipientsClient().get(recipientId);
    }

    /**
     * Update a Payout Recipient.
     *
     * @see <a href="https://developer.bitpay.com/reference/update-a-recipient">Update a Recipient</a>
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
        return this.getPayoutRecipientsClient().update(recipientId, recipient);
    }

    /**
     * Cancel a BitPay Payout recipient.
     *
     * @see <a href="https://developer.bitpay.com/reference/remove-a-recipient">Remove a Recipient</a>
     *
     * @param recipientId String The id of the recipient to cancel.
     * @return True if the delete operation was successful, false otherwise.
     * @throws BitPayException                      BitPayException class
     * @throws PayoutRecipientCancellationException PayoutRecipientCancellationException
     *                                              class
     */
    public Boolean deletePayoutRecipient(String recipientId)
        throws BitPayException, PayoutRecipientCancellationException {
        return this.getPayoutRecipientsClient().delete(recipientId);
    }

    /**
     * Request a payout recipient notification
     *
     * @see <a href="https://developer.bitpay.com/reference/request-a-recipient-webhook-to-be-resent">Request a Recipient Webhook to be Resent</a>
     *
     * @param recipientId String A BitPay recipient ID.
     * @return True if the notification was successfully sent, false otherwise.
     * @throws BitPayException                      BitPayException class
     * @throws PayoutRecipientNotificationException PayoutRecipientNotificationException
     *                                              class
     */
    public Boolean requestPayoutRecipientNotification(String recipientId)
        throws PayoutRecipientNotificationException, BitPayException {
        return this.getPayoutRecipientsClient().requestNotification(recipientId);
    }

    /**
     * Submit a BitPay Payout.
     *
     * @see <a href="https://developer.bitpay.com/reference/create-a-payout">Create a Payout</a>
     *
     * @param payout Payout A Payout object with request parameters defined.
     * @return A BitPay generated Payout object.
     * @throws BitPayException         BitPayException class
     * @throws PayoutCreationException PayoutCreationException class
     */
    public Payout submitPayout(Payout payout) throws BitPayException, PayoutCreationException {
        return this.getPayoutClient().submit(payout);
    }

    /**
     * Retrieve a BitPay payout by payout id using. The client must have been
     * previously authorized for the payout facade.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-a-payout">Retrieve a Payout</a>
     *
     * @param payoutId String The id of the payout to retrieve.
     * @return A BitPay Payout object.
     * @throws BitPayException      BitPayException class
     * @throws PayoutQueryException PayoutQueryException class
     */
    public Payout getPayout(String payoutId) throws BitPayException, PayoutQueryException {
        return this.getPayoutClient().get(payoutId);
    }

    /**
     * Cancel a BitPay Payout.
     *
     * @see <a href="https://developer.bitpay.com/reference/cancel-a-payout">Cancel a Payout</a>
     *
     * @param payoutId String The id of the payout to cancel.
     * @return True if the refund was successfully canceled, false otherwise.
     * @throws BitPayException             BitPayException class
     * @throws PayoutCancellationException PayoutCancellationException class
     */
    public Boolean cancelPayout(String payoutId) throws BitPayException, PayoutCancellationException {
        return this.getPayoutClient().cancel(payoutId);
    }

    /**
     * Retrieve a collection of BitPay payouts.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-payouts-filtered-by-query">Retrieve Payouts Filtered by Query</a>
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
        return this.getPayoutClient().getPayouts(startDate, endDate, status, reference, limit, offset);
    }

    /**
     * Request a payout notification
     *
     * @see <a href="https://developer.bitpay.com/reference/request-a-payout-webhook-to-be-resent">Request a Payout Webhook to be Resent</a>
     *
     * @param payoutId String The id of the payout to notify..
     * @return True if the notification was successfully sent, false otherwise.
     * @throws BitPayException             BitPayException class
     * @throws PayoutNotificationException PayoutNotificationException class
     */
    public Boolean requestPayoutNotification(String payoutId)
        throws BitPayException, PayoutNotificationException {
        return this.getPayoutClient().requestNotification(payoutId);
    }

    /**
     * Retrieves settlement reports for the calling merchant filtered by query.
     * The `limit` and `offset` parameters
     * specify pages for large query sets.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-settlements">Retrieve Settlements</a>
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
    public List<Settlement> getSettlements(
        String currency,
        String dateStart,
        String dateEnd,
        String status,
        Integer limit,
        Integer offset
    ) throws BitPayException, SettlementQueryException {
        return this.getSettlementClient().getSettlements(currency, dateStart, dateEnd, status, limit, offset);
    }

    /**
     * Retrieves a summary of the specified settlement.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-a-settlement">Retrieve a Settlement</a>
     *
     * @param settlementId Settlement Id.
     * @return A BitPay Settlement object.
     * @throws BitPayException          BitPayException class
     * @throws SettlementQueryException SettlementQueryException class
     */
    public Settlement getSettlement(String settlementId) throws BitPayException, SettlementQueryException {
        return this.getSettlementClient().get(settlementId);
    }

    /**
     * Gets a detailed reconciliation report of the activity within the settlement period.
     * Required id and settlement token.
     *
     * @see <a href="https://developer.bitpay.com/reference/fetch-a-reconciliation-report">Fetch a Reconciliation Report</a>
     *
     * @param settlementId Settlement ID.
     * @param token Settlement token.
     * @return A detailed BitPay Settlement object.
     * @throws SettlementQueryException SettlementQueryException class
     */
    public Settlement getSettlementReconciliationReport(String settlementId, String token) throws SettlementQueryException {
        return this.getSettlementClient().getSettlementReconciliationReport(settlementId, token);
    }

    /**
     * Retrieve all supported wallets.
     *
     * @see <a href="https://developer.bitpay.com/reference/retrieve-the-supported-wallets">Retrieve the Supported Wallets</a>
     *
     * @return A list of wallet objets.
     * @throws WalletQueryException WalletQueryException class
     * @throws BitPayException      BitPayException class
     */
    public List<Wallet> getSupportedWallets() throws WalletQueryException, BitPayException {
        return this.getWalletClient().getSupportedWallets();
    }

    /**
     * Gets rates client.
     *
     * @return the rates client
     */
    public RateClient getRateClient() {
        return RateClient.getInstance(this.bitPayClient);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Sets the logger level of reporting.
     *
     * @param loggerLevel int BitPayLogger constant (OFF, INFO, WARN, ERR, DEBUG)
     */
    public void setLoggerLevel(int loggerLevel) {
        this.bitPayClient.setLoggerLevel(loggerLevel);
    }

    /**
     * Gets http client.
     *
     * @param proxyDetails the proxy details
     * @param proxyCreds   the proxy creds
     * @return the http client
     */
    protected HttpClient getHttpClient(HttpHost proxyDetails, CredentialsProvider proxyCreds) {
        if (proxyDetails != null) {
            if (proxyCreds != null) {
                return HttpClientBuilder.create().setProxy(proxyDetails).setDefaultCredentialsProvider(
                    proxyCreds).build();
            } else {
                return HttpClientBuilder.create().setProxy(proxyDetails).build();
            }
        } else {
            return HttpClientBuilder.create().build();
        }
    }

    /**
     * Gets ECKey.
     *
     * @param privateKey the private key
     * @return ECKey
     * @throws BitPayException the bit pay exception
     */
    protected ECKey getEcKey(PrivateKey privateKey) throws BitPayException {
        File privateKeyFile = new File(privateKey.value());
        if (privateKeyFile.exists() && KeyUtils.privateKeyExists(privateKey.value().replace("\"", ""))) {
            try {
                return KeyUtils.loadEcKey();
            } catch (Exception e) {
                throw new BitPayException(null,
                    "When trying to load private key. Make sure the configuration details are correct and the private key and tokens are valid : " +
                        e.getMessage());
            }
        } else {
            try {
                return KeyUtils.createEcKeyFromHexString(privateKey.value());
            } catch (Exception e) {
                throw new BitPayException(null, "Private Key file not found");
            }
        }
    }

    /**
     * Initialize the public/private key pair by either loading the existing one or by creating a new one.
     *
     * @param config the config
     * @return ECKey
     * @throws Exception the exception
     */
    protected ECKey getEcKey(Config config) throws Exception {
        try {
            if (KeyUtils.privateKeyExists(
                config.getEnvConfig(config.getEnvironment()).path("PrivateKeyPath").toString()
                    .replace("\"", ""))) {
                return KeyUtils.loadEcKey();
            } else {
                String keyHex =
                    config.getEnvConfig(config.getEnvironment()).path("PrivateKey").toString()
                        .replace("\"", "");
                if (!keyHex.isEmpty()) {
                    return KeyUtils.createEcKeyFromHexString(keyHex);
                }
            }
            return null;
        } catch (Exception e) {
            throw new BitPayException(null,
                "When trying to load private key. Make sure the configuration details are correct and the private key and tokens are valid : " +
                    e.getMessage());
        }
    }

    /**
     * Derive identity.
     *
     * @param ecKey ECKey
     * @throws IllegalArgumentException the illegal argument exception
     * @throws BitPayException          the bit pay exception
     */
    protected void deriveIdentity(ECKey ecKey) throws IllegalArgumentException, BitPayException {
        // Identity in this implementation is defined to be the SIN.
        try {
            this.identity = KeyUtils.deriveSIN(ecKey);
        } catch (Exception e) {
            throw new BitPayException(null,
                "failed to deserialize BitPay server response (Token array) : " + e.getMessage());
        }
    }

    /**
     * Gets facade based on access token.
     *
     * @return the facade based on access token
     */
    protected Facade getFacadeBasedOnAccessToken() {
        if (this.tokenContainer.tokenExists(Facade.MERCHANT)) {
            return Facade.MERCHANT;
        }

        return Facade.POS;
    }

    /**
     * Is sign request boolean.
     *
     * @param facade the facade
     * @return the boolean
     */
    protected boolean isSignRequest(Facade facade) {
        return !facade.equals(Facade.POS);
    }

    /**
     * Gets base url.
     *
     * @param environment the environment
     * @return the base url
     */
    protected String getBaseUrl(Environment environment) {
        return environment.equals(Environment.TEST) ? Config.TEST_URL : Config.PROD_URL;
    }

    /**
     * Loads the configuration file (JSON).
     *
     * @param configFilePath the config file path
     * @return the Config class
     * @throws BitPayException BitPayException class
     */
    protected Config buildConfigFromFile(ConfigFilePath configFilePath) throws BitPayException {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(configFilePath.value()));
            JsonMapper mapper = JsonMapperFactory.create();
            //read JSON like DOM Parser
            JsonNode rootNode = mapper.readTree(jsonData);
            JsonNode bitPayConfiguration = rootNode.path("BitPayConfiguration");
            return mapper.readValue(bitPayConfiguration.toString(), Config.class);
        } catch (JsonProcessingException e) {
            throw new BitPayException(null, "failed to read configuration file : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null, "failed to read configuration file : " + e.getMessage());
        }
    }

    /**
     * Create authorization client.
     *
     * @return the authorization client
     */
    protected AuthorizationClient createAuthorizationClient() {
        return new AuthorizationClient(this.bitPayClient, this.guidGenerator, this.tokenContainer, this.identity);
    }

    /**
     * Get invoice client.
     *
     * @return the invoice client
     */
    protected InvoiceClient getInvoiceClient() {
        return InvoiceClient.getInstance(this.bitPayClient, this.tokenContainer, this.guidGenerator);
    }

    /**
     * Get refund client.
     *
     * @return the refund client
     */
    protected RefundClient getRefundClient() {
        return RefundClient.getInstance(this.bitPayClient, this.tokenContainer, this.guidGenerator);
    }

    /**
     * Get bill client.
     *
     * @return the bill client
     */
    protected BillClient getBillClient() {
        return BillClient.getInstance(this.bitPayClient, this.tokenContainer);
    }

    /**
     * Get ledger client.
     *
     * @return the ledger client
     */
    protected LedgerClient getLedgerClient() {
        return LedgerClient.getInstance(this.bitPayClient, this.tokenContainer);
    }

    /**
     * Get payout recipients client.
     *
     * @return the payout recipients client
     */
    protected PayoutRecipientsClient getPayoutRecipientsClient() {
        return PayoutRecipientsClient.getInstance(this.bitPayClient, this.tokenContainer, this.guidGenerator);
    }

    /**
     * Get payout client.
     *
     * @return the payout client
     */
    protected PayoutClient getPayoutClient() {
        return PayoutClient.getInstance(this.bitPayClient, this.tokenContainer);
    }

    /**
     * Get settlement client.
     *
     * @return the settlement client
     */
    protected SettlementClient getSettlementClient() {
        return SettlementClient.getInstance(this.bitPayClient, this.tokenContainer);
    }

    /**
     * Get wallet client.
     *
     * @return the wallet client
     */
    protected WalletClient getWalletClient() {
        return WalletClient.getInstance(this.bitPayClient);
    }
}
