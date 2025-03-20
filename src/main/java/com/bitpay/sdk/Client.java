/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
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
import com.bitpay.sdk.client.PayoutGroupClient;
import com.bitpay.sdk.client.PayoutRecipientsClient;
import com.bitpay.sdk.client.RateClient;
import com.bitpay.sdk.client.RefundClient;
import com.bitpay.sdk.client.SettlementClient;
import com.bitpay.sdk.client.WalletClient;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.bill.Bill;
import com.bitpay.sdk.model.invoice.Invoice;
import com.bitpay.sdk.model.invoice.InvoiceEventToken;
import com.bitpay.sdk.model.invoice.Refund;
import com.bitpay.sdk.model.ledger.Ledger;
import com.bitpay.sdk.model.ledger.LedgerEntry;
import com.bitpay.sdk.model.payout.Payout;
import com.bitpay.sdk.model.payout.PayoutGroup;
import com.bitpay.sdk.model.payout.PayoutRecipient;
import com.bitpay.sdk.model.payout.PayoutRecipients;
import com.bitpay.sdk.model.rate.Rate;
import com.bitpay.sdk.model.rate.Rates;
import com.bitpay.sdk.model.settlement.Settlement;
import com.bitpay.sdk.model.wallet.Wallet;
import com.bitpay.sdk.util.GuidGenerator;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.bitpay.sdk.util.KeyUtils;
import com.bitpay.sdk.util.TokenContainer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
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

    protected static final String LOAD_PRIVATE_KEY_EXCEPTION =
        "When trying to load private key. Make sure the configuration details are correct "
            + "and the private key and tokens are valid : ";
    protected GuidGenerator guidGenerator;
    protected BitPayClient bitPayClient;
    protected TokenContainer tokenContainer;

    /**
     * Return the identity of this client (i.e. the public key).
     */
    private String identity;

    /**
     * Constructor for POS facade.
     *
     * @param token POS token
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Client(PosToken token) throws BitPayGenericException {
        this(token, Environment.PROD);
    }

    /**
     * Constructor for POS facade.
     *
     * @param token        POS token
     * @param platformInfo Platform info
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Client(PosToken token, String platformInfo) throws BitPayGenericException {
        this(token, Environment.PROD, platformInfo);
    }

    /**
     * Constructor for POS facade.
     *
     * @param token       POS token
     * @param environment Environment
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Client(
        PosToken token,
        Environment environment
    ) throws BitPayGenericException {
        if (Objects.isNull(token) || Objects.isNull(environment)) {
            BitPayExceptionProvider.throwMissingParameterException();
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
     * Constructor for POS facade.
     *
     * @param token        POS token
     * @param environment  Environment
     * @param platformInfo Platform info
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Client(
        PosToken token,
        Environment environment,
        String platformInfo
    ) throws BitPayGenericException {
        if (Objects.isNull(token) || Objects.isNull(environment)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        this.tokenContainer = new TokenContainer();
        this.tokenContainer.addPos(token.value());
        this.bitPayClient = new BitPayClient(
            getHttpClient(null, null),
            new HttpRequestFactory(),
            getBaseUrl(environment),
            null,
            platformInfo
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
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Client(
        Environment environment,
        PrivateKey privateKey,
        TokenContainer tokenContainer,
        HttpHost proxyDetails,
        CredentialsProvider proxyCredentials
    ) throws BitPayGenericException {
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
    }

    /**
     * Constructor for use if the keys and SIN are managed by this library.
     *
     * @param environment      Target environment. Options: Env.Test / Env.Prod
     * @param privateKey       The full path to the securely located private key or the HEX key value.
     * @param tokenContainer   Object containing the available tokens.
     * @param proxyDetails     HttpHost Optional Proxy setting (set to NULL to ignore)
     * @param proxyCredentials CredentialsProvider Optional Proxy Basic Auth Credentials (set to NULL to ignore)
     * @param platformInfo     Platform Info
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Client(
        Environment environment,
        PrivateKey privateKey,
        TokenContainer tokenContainer,
        HttpHost proxyDetails,
        CredentialsProvider proxyCredentials,
        String platformInfo
    ) throws BitPayGenericException {
        ECKey ecKey = getEcKey(privateKey);
        this.tokenContainer = tokenContainer;
        this.deriveIdentity(ecKey);
        this.bitPayClient = new BitPayClient(
            getHttpClient(proxyDetails, proxyCredentials),
            new HttpRequestFactory(),
            getBaseUrl(environment),
            ecKey,
            platformInfo
        );
        this.guidGenerator = new GuidGenerator();
    }

    /**
     * Constructor for use if the keys and SIN are managed by this library.
     *
     * @param configFilePath   The path to the configuration file.
     * @param proxy            HttpHost Optional Proxy setting (set to NULL to ignore)
     * @param proxyCredentials CredentialsProvider Optional Proxy Basic Auth Credentials (set to NULL to ignore)
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Client(
        ConfigFilePath configFilePath,
        HttpHost proxy,
        CredentialsProvider proxyCredentials
    ) throws BitPayGenericException {
        Config config = this.buildConfigFromFile(configFilePath);
        this.tokenContainer = new TokenContainer(config);
        ECKey ecKey = this.getEcKey(config);
        if (Objects.isNull(ecKey)) {
            BitPayExceptionProvider.throwValidationException("Missing ECKey");
        }

        this.deriveIdentity(ecKey);
        this.bitPayClient = new BitPayClient(
            getHttpClient(proxy, proxyCredentials),
            new HttpRequestFactory(),
            getBaseUrl(config.getEnvironment()),
            ecKey
        );
        this.guidGenerator = new GuidGenerator();
    }


    /**
     * Constructor for use if the keys and SIN are managed by this library.
     *
     * @param configFilePath   The path to the configuration file.
     * @param proxy            HttpHost Optional Proxy setting (set to NULL to ignore)
     * @param proxyCredentials CredentialsProvider Optional Proxy Basic Auth Credentials (set to NULL to ignore)
     * @param platformInfo     Platform Info
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Client(
        ConfigFilePath configFilePath,
        HttpHost proxy,
        CredentialsProvider proxyCredentials,
        String platformInfo
    ) throws BitPayGenericException {
        Config config = this.buildConfigFromFile(configFilePath);
        this.tokenContainer = new TokenContainer(config);
        ECKey ecKey = this.getEcKey(config);
        if (Objects.isNull(ecKey)) {
            BitPayExceptionProvider.throwValidationException("Missing ECKey");
        }

        this.deriveIdentity(ecKey);
        this.bitPayClient = new BitPayClient(
            getHttpClient(proxy, proxyCredentials),
            new HttpRequestFactory(),
            getBaseUrl(config.getEnvironment()),
            ecKey,
            platformInfo
        );
        this.guidGenerator = new GuidGenerator();
    }

    /**
     * Constructor for all injected classes.
     *
     * @param bitPayClient   BitPayClient
     * @param identity       Identity
     * @param tokenContainer TokenContainer
     * @param guidGenerator  GuidGenerator
     */
    public Client(
        BitPayClient bitPayClient,
        String identity,
        TokenContainer tokenContainer,
        GuidGenerator guidGenerator
    ) {
        this.bitPayClient = bitPayClient;
        this.identity = identity;
        this.tokenContainer = tokenContainer;
        this.guidGenerator = guidGenerator;
    }

    /**
     * Create pos (light) client.
     *
     * @param token the token
     * @return the client
     * @throws BitPayGenericException BitPayGenericException class
     */
    public static Client createPosClient(PosToken token) throws BitPayGenericException {
        return new Client(token);
    }

    /**
     * Create pos (light) client.
     *
     * @param token the token
     * @param platformInfo the platform info
     * @return the client
     * @throws BitPayGenericException BitPayGenericException class
     */
    public static Client createPosClient(PosToken token, String platformInfo) throws BitPayGenericException {
        return new Client(token, platformInfo);
    }

    /**
     * Create pos (light) client.
     *
     * @param token the token
     * @param environment environment
     * @return the client
     * @throws BitPayGenericException BitPayGenericException class
     */
    public static Client createPosClient(
        PosToken token,
        Environment environment
    ) throws BitPayGenericException {
        return new Client(token, environment);
    }

    /**
     * Create pos (light) client.
     *
     * @param token the token
     * @param environment environment
     * @param platformInfo the platform info
     * @return the client
     * @throws BitPayGenericException BitPayGenericException class
     */
    public static Client createPosClient(
        PosToken token,
        Environment environment,
        String platformInfo
    ) throws BitPayGenericException {
        return new Client(token, environment, platformInfo);
    }

    /**
     * Create standard client.
     *
     * @param privateKey the private key
     * @param tokenContainer the token container
     * @param environment environment
     * @return Client Client
     * @throws BitPayGenericException BitPayGenericException class
     */
    public static Client createClientByPrivateKey(
        PrivateKey privateKey,
        TokenContainer tokenContainer,
        Environment environment
    ) throws BitPayGenericException {
        Environment env = Objects.isNull(environment) ? Environment.PROD : environment;

        return new Client(env, privateKey, tokenContainer, null, null);
    }

    /**
     * Create standard client.
     *
     * @param privateKey the private key
     * @param tokenContainer the token container
     * @param environment environment
     * @param platformInfo the platform info
     * @return Client Client
     * @throws BitPayGenericException BitPayGenericException class
     */
    public static Client createClientByPrivateKey(
        PrivateKey privateKey,
        TokenContainer tokenContainer,
        Environment environment,
        String platformInfo
    ) throws BitPayGenericException {
        Environment env = Objects.isNull(environment) ? Environment.PROD : environment;

        return new Client(env, privateKey, tokenContainer, null, null, platformInfo);
    }

    /**
     * Create standard client.
     *
     * @param configFilePath the config file path
     * @return the client
     * @throws BitPayGenericException BitPayGenericException class
     */
    public static Client createClientByConfigFilePath(ConfigFilePath configFilePath) throws BitPayGenericException {
        return new Client(configFilePath, null, null);
    }

    /**
     * Create standard client.
     *
     * @param configFilePath the config file path
     * @param platformInfo the platform info
     * @return the client
     * @throws BitPayGenericException BitPayGenericException class
     */
    public static Client createClientByConfigFilePath(
        ConfigFilePath configFilePath,
        String platformInfo
    ) throws BitPayGenericException {
        return new Client(configFilePath, null, null, platformInfo);
    }


    /**
     * Authorize this client with the server using the specified pairing code (Server Initiated Pairing).
     *
     * @param pairingCode A code obtained from the server; typically from bitpay.com/api-tokens.
     * @throws BitPayApiException BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     * @see <a href="https://developer.bitpay.com/reference/get-token">Request an API Token</a>
     */
    public void authorizeClient(String pairingCode) throws BitPayApiException, BitPayGenericException {
        this.createAuthorizationClient().authorizeClient(pairingCode);
    }

    /**
     * Request a pairing code from the BitPay server (Client Initiated Pairing).
     *
     * @param facade Defines the level of API access being requested
     * @return A pairing code for claim at https://bitpay.com/dashboard/merchant/api-tokens.
     * @throws BitPayApiException BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     * @see <a href="https://developer.bitpay.com/reference/get-token">Request an API Token</a>
     */
    public String authorizeClient(Facade facade) throws BitPayApiException, BitPayGenericException {
        return this.createAuthorizationClient().authorizeClient(facade);
    }

    /**
     * Retrieve a token associated with a known resource. The token is used to access other related resources.
     *
     * @param facade The identifier for the desired resource.
     * @return The token associated with resource.
     * @throws BitPayGenericException BitPayGenericException class
     */
    public String getAccessToken(Facade facade) throws BitPayGenericException {
        return this.tokenContainer.getAccessToken(facade);
    }

    /**
     * Retrieve a token associated with a known resource. The token is used to access other related resources.
     *
     * @param key The identifier for the desired resource.
     * @return The token associated with resource.
     * @throws BitPayGenericException BitPayGenericException class
     */
    public String getAccessToken(String key) throws BitPayGenericException {
        return this.tokenContainer.getAccessToken(key);
    }

    /**
     * Gets info for specific currency.
     *
     * @param currencyCode String Currency code for which the info will be retrieved.
     * @return Map|null
     * @throws BitPayGenericException BitPayGenericException class
     */
    public Map<String, Object> getCurrencyInfo(String currencyCode) throws BitPayGenericException {
        CurrencyClient client = CurrencyClient.getInstance(this.bitPayClient);
        return client.getInfo(currencyCode);
    }

    /**
     * Create a BitPay invoice.
     *
     * @param invoice An Invoice object with request parameters defined.
     * @return A BitPay generated Invoice object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/create-an-invoice">Create an Invoice</a>
     */
    public Invoice createInvoice(Invoice invoice) throws BitPayGenericException, BitPayApiException {
        InvoiceClient client = getInvoiceClient();
        Facade facade = getFacadeBasedOnAccessToken();
        boolean signRequest = isSignRequest(facade);

        return client.create(invoice, facade, signRequest);
    }

    /**
     * Create a BitPay invoice.
     *
     * @param invoice An Invoice object with request parameters defined.
     * @param facade The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay generated Invoice object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/create-an-invoice">Create an Invoice</a>
     */
    public Invoice createInvoice(
        Invoice invoice,
        Facade facade,
        boolean signRequest
    ) throws BitPayGenericException, BitPayApiException {
        InvoiceClient client = getInvoiceClient();

        return client.create(invoice, facade, signRequest);
    }

    /**
     * Retrieve a BitPay invoice by invoice id using the public facade.
     *
     * @param invoiceId The id of the invoice to retrieve.
     * @return A BitPay Invoice object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-an-invoice">Retrieve an Invoice</a>
     */
    public Invoice getInvoice(String invoiceId) throws BitPayGenericException, BitPayApiException {
        Facade facade = getFacadeBasedOnAccessToken();
        boolean signRequest = isSignRequest(facade);

        return this.getInvoiceClient().get(invoiceId, facade, signRequest);
    }

    /**
     * Retrieve a BitPay invoice by invoice id.
     *
     * @param invoiceId The id of the invoice to retrieve.
     * @param facade The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay Invoice object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-an-invoice">Retrieve an Invoice</a>
     */
    public Invoice getInvoice(
        String invoiceId,
        Facade facade,
        boolean signRequest
    ) throws BitPayGenericException, BitPayApiException {
        return this.getInvoiceClient().get(invoiceId, facade, signRequest);
    }

    /**
     * Retrieve a BitPay invoice by guid using the specified facade.
     * The client must have been previously authorized for the specified facade.
     *
     * @param guid The guid of the invoice to retrieve.
     * @return A BitPay Invoice object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-an-invoice-by-guid">Retrieve an Invoice by GUID</a>
     */
    public Invoice getInvoiceByGuid(String guid) throws BitPayGenericException, BitPayApiException {
        Facade facade = getFacadeBasedOnAccessToken();
        boolean signRequest = isSignRequest(facade);
        return this.getInvoiceClient().getByGuid(guid, facade, signRequest);
    }

    /**
     * Retrieve a BitPay invoice by guid using the specified facade.
     * The client must have been previously authorized for the specified facade.
     *
     * @param guid        The guid of the invoice to retrieve.
     * @param facade      The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay Invoice object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-an-invoice-by-guid">Retrieve an Invoice by GUID</a>
     */
    public Invoice getInvoiceByGuid(
        String guid,
        Facade facade,
        Boolean signRequest
    ) throws BitPayGenericException, BitPayApiException {
        return this.getInvoiceClient().getByGuid(guid, facade, signRequest);
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
     * @throws BitPayGenericException       BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-invoices-filtered-by-query">Retrieve Invoices Filtered by Query</a>
     */
    public List<Invoice> getInvoices(
        String dateStart,
        String dateEnd,
        String status,
        String orderId,
        Integer limit,
        Integer offset
    ) throws BitPayGenericException, BitPayApiException {
        return this.getInvoiceClient().getInvoices(dateStart, dateEnd, status, orderId, limit, offset);
    }

    /**
     * Retrieves a bus token which can be used to subscribe to invoice events.
     *
     * @param invoiceId the id of the invoice for which you want to fetch an event token.
     * @return InvoiceEventToken event token
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @since 8.8.0
     * @see <a href="https://developer.bitpay.com/reference/retrieve-an-event-token">Retrieve an Event Token</a>
     */
    public InvoiceEventToken getInvoiceEventToken(String invoiceId) throws BitPayGenericException, BitPayApiException {
        return this.getInvoiceClient().getInvoiceEventToken(invoiceId);
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
     * @throws BitPayGenericException        BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/update-an-invoice">Update an Invoice</a>
     */
    public Invoice updateInvoice(
        String invoiceId,
        String buyerSms,
        String smsCode,
        String buyerEmail,
        Boolean autoVerify
    ) throws BitPayGenericException, BitPayApiException {
        return this.getInvoiceClient().update(invoiceId, buyerSms, smsCode, buyerEmail, autoVerify);
    }

    /**
     * Pay a BitPay invoice with a mock transaction. Available only on test env.
     *
     * @param invoiceId The id of the invoice to updated.
     * @param status    The status of the invoice to be updated, can be "confirmed" or "complete".
     * @return A BitPay generated Invoice object.
     * @throws BitPayGenericException        BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     */
    public Invoice payInvoice(
        String invoiceId,
        String status
    ) throws BitPayGenericException, BitPayApiException {
        return this.getInvoiceClient().pay(invoiceId, status);
    }

    /**
     * Delete a previously created BitPay invoice.
     *
     * @param invoiceId The Id of the BitPay invoice to be canceled.
     * @return A BitPay generated Invoice object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException              BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/cancel-an-invoice">Cancel an Invoice</a>
     */
    public Invoice cancelInvoice(String invoiceId) throws BitPayGenericException, BitPayApiException {
        return this.getInvoiceClient().cancel(invoiceId);
    }

    /**
     * Cancellation will require EITHER an SMS or E-mail to have already been set if the invoice has proceeded to
     * the point where it may have been paid, unless using forceCancel parameter.
     *
     * @param invoiceId   The Id of the BitPay invoice to be canceled.
     * @param forceCancel If 'true' it will cancel the invoice even if no contact information is present.
     * @return A BitPay generated Invoice object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException              BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/cancel-an-invoice">Cancel an Invoice</a>
     */
    public Invoice cancelInvoice(
        String invoiceId,
        Boolean forceCancel
    ) throws BitPayGenericException, BitPayApiException {
        return this.getInvoiceClient().cancel(invoiceId, forceCancel);
    }

    /**
     * Cancellation will require EITHER an SMS or E-mail to have already been set if the invoice has proceeded to
     * the point where it may have been paid, unless using forceCancel parameter.
     *
     * @param guid GUID A passthru variable provided by the merchant and designed to be used by the merchant to
     *             correlate the invoice with an order ID in their system, which can be used as a lookup variable
     *             in Retrieve Invoice by GUID.
     * @return Invoice Invoice
     * @throws BitPayApiException BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     * @see <a href="https://developer.bitpay.com/reference/cancel-an-invoice-by-guid">Cancel an Invoice by GUID</a>
     */
    public Invoice cancelInvoiceByGuid(String guid) throws BitPayApiException, BitPayGenericException {
        return this.getInvoiceClient().cancelByGuid(guid, false);
    }

    /**
     * Cancellation will require EITHER an SMS or E-mail to have already been set if the invoice has proceeded to
     * the point where it may have been paid, unless using forceCancel parameter.
     *
     * @param guid GUID A passthru variable provided by the merchant and designed to be used by the merchant to
     *             correlate the invoice with an order ID in their system, which can be used as a lookup variable
     *             in Retrieve Invoice by GUID.
     * @param forceCancel Parameter that will cancel the invoice even if no contact information is present.
     *                    Note: Canceling a paid invoice without contact information requires a manual support
     *                    process and is not recommended.
     * @return Invoice Invoice
     * @throws BitPayApiException BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     * @see <a href="https://developer.bitpay.com/reference/cancel-an-invoice-by-guid">Cancel an Invoice by GUID</a>
     */
    public Invoice cancelInvoiceByGuid(
        String guid,
        Boolean forceCancel
    ) throws BitPayApiException, BitPayGenericException {
        return this.getInvoiceClient().cancelByGuid(guid, forceCancel);
    }

    /**
     * The intent of this call is to address issues when BitPay sends a webhook but the client doesn't receive it,
     * so the client can request that BitPay resend it.
     *
     * @param invoiceId    The id of the invoice for which you want the last webhook to be resent.
     * @param invoiceToken The resource token for the invoiceId.
     *                     This token can be retrieved from the Bitpay's invoice object.
     * @return Boolean status of request
     * @throws BitPayApiException BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     * @see <a href="https://developer.bitpay.com/reference/request-an-invoice-webhook-to-be-resent">Request an Invoice Webhook to be Resent</a>
     */
    public Boolean requestInvoiceWebhookToBeResent(
        String invoiceId,
        String invoiceToken
    ) throws BitPayApiException, BitPayGenericException {
        return this.getInvoiceClient().requestInvoiceWebhookToBeResent(invoiceId, invoiceToken);
    }

    /**
     * Create a refund for a BitPay invoice.
     *
     * @param invoiceId          The BitPay invoice Id having the associated refund to be created.
     * @param amount             Amount to be refunded in the currency indicated.
     * @param preview            Whether to create the refund request as a preview
     *                           (which will not be acted on until status is updated)
     * @param immediate          Whether funds should be removed from merchant ledger immediately on submission
     *                           or at time of processing
     * @param buyerPaysRefundFee Whether the buyer should pay the refund fee (default is merchant)
     * @param reference          Present only if specified. Used as reference label for the refund. Max str length = 100
     * @return An updated Refund Object
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException         BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/create-a-refund-request">Create a Refund Request</a>
     */
    public Refund createRefund(
        String invoiceId,
        Double amount,
        Boolean preview,
        Boolean immediate,
        Boolean buyerPaysRefundFee,
        String reference
    ) throws BitPayGenericException, BitPayApiException {
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
     * @param invoiceId          The BitPay invoice Id having the associated refund to be created.
     * @param amount             Amount to be refunded in the currency indicated.
     * @param preview            Whether to create the refund request as a preview
     *                           (which will not be acted on until status is updated)
     * @param immediate          Whether funds should be removed from merchant ledger immediately on submission
     *                           or at time of processing
     * @param buyerPaysRefundFee Whether the buyer should pay the refund fee (default is merchant)
     * @param reference          Present only if specified. Used as reference label for the refund. Max str length = 100
     * @param guid               Variable provided by the merchant and designed to be used by the merchant
     *                           to correlate the refund with a refund ID in their system
     * @return An updated Refund Object
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException         BitPayApiException class
     * @since 8.7.0
     * @see <a href="https://developer.bitpay.com/reference/create-a-refund-request">Create a Refund Request</a>
     */
    public Refund createRefund(
        String invoiceId,
        Double amount,
        Boolean preview,
        Boolean immediate,
        Boolean buyerPaysRefundFee,
        String reference,
        String guid
    ) throws BitPayGenericException, BitPayApiException {
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
     * @param refund Refund class which provided data - invoice id, amount, preview, immediate, buyerPaysRefundFee,
     *               reference and guid for create request
     * @return An updated Refund Object
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException         BitPayApiException class
     * @since 8.7.0
     * @see <a href="https://developer.bitpay.com/reference/create-a-refund-request">Create a Refund Request</a>
     */
    public Refund createRefund(Refund refund) throws BitPayGenericException, BitPayApiException {
        return this.getRefundClient().create(refund);
    }

    /**
     * Retrieve a previously made refund request on a BitPay invoice.
     *
     * @param refundId The BitPay refund ID.
     * @return A BitPay Refund object with the associated Refund object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException      BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-a-refund-request">Retrieve a Refund Request</a>
     */
    public Refund getRefund(String refundId) throws BitPayGenericException, BitPayApiException {
        return this.getRefundClient().getById(refundId);
    }

    /**
     * Retrieve a previously made refund request on a BitPay invoice.
     *
     * @param guid The BitPay refund GUID.
     * @return A BitPay Refund object with the associated Refund object.
     * @throws BitPayApiException BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     * @since 8.7.0
     * @see <a href="https://developer.bitpay.com/reference/retrieve-a-refund-by-guid-request">Retrieve a Refund by GUID Request</a>
     */
    public Refund getRefundByGuid(String guid) throws BitPayGenericException, BitPayApiException {
        return this.getRefundClient().getByGuid(guid);
    }

    /**
     * Retrieve all refund requests on a BitPay invoice.
     *
     * @param invoiceId The BitPay invoice object having the associated refunds.
     * @return A list of BitPay Refund objects with the associated Refund objects.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-refunds-of-an-invoice">Retrieve Refunds of an Invoice</a>
     */
    public List<Refund> getRefunds(String invoiceId) throws BitPayGenericException, BitPayApiException {
        return this.getRefundClient().getRefundsByInvoiceId(invoiceId);
    }

    /**
     * Update the status of a BitPay invoice.
     *
     * @param refundId A BitPay refund ID.
     * @param status   The new status for the refund to be updated.
     * @return A BitPay generated Refund object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/update-a-refund-request">Update a Refund Request</a>
     */
    public Refund updateRefund(String refundId, String status) throws BitPayGenericException, BitPayApiException {
        return this.getRefundClient().update(refundId, status);
    }

    /**
     * Update the status of a BitPay invoice.
     *
     * @param guid A BitPay refund Guid.
     * @param status   The new status for the refund to be updated.
     * @return A BitPay generated Refund object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @since 8.7.0
     * @see <a href="https://developer.bitpay.com/reference/update-a-refund-by-guid-request">Update a Refund by GUID Request</a>
     */
    public Refund updateRefundByGuid(String guid, String status) throws BitPayGenericException, BitPayApiException {
        return this.getRefundClient().updateByGuid(guid, status);
    }

    /**
     * Send a refund notification.
     *
     * @param refundId    A BitPay refund ID.
     * @param refundToken The resource token for the refundId.
     *                    This token can be retrieved from the Bitpay's refund object.
     * @return An updated Refund Object
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/request-a-refund-notification-to-be-resent">Request a Refund Notification to be Resent</a>
     */
    public Boolean sendRefundNotification(
        String refundId,
        String refundToken
    ) throws BitPayGenericException, BitPayApiException {
        return this.getRefundClient().sendRefundNotification(refundId, refundToken);
    }

    /**
     * Cancel a previously submitted refund request on a BitPay invoice.
     *
     * @param refundId The refund Id for the refund to be canceled.
     * @return An updated Refund Object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/cancel-a-refund-request">Cancel a Refund Request</a>
     */
    public Refund cancelRefund(String refundId) throws BitPayGenericException, BitPayApiException {
        return this.getRefundClient().cancel(refundId);
    }

    /**
     * Cancel a previously submitted refund request on a BitPay invoice.
     *
     * @param guid The refund Guid for the refund to be canceled.
     * @return An updated Refund Object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @since 8.7.0
     * @see <a href="https://developer.bitpay.com/reference/cancel-a-refund-by-guid-request">Cancel a Refund by GUID Request</a>
     */
    public Refund cancelRefundByGuid(String guid) throws BitPayGenericException, BitPayApiException {
        return this.getRefundClient().cancelByGuid(guid);
    }

    /**
     * Create a BitPay bill.
     *
     * @param bill An Bill object with request parameters defined.
     * @return A BitPay generated Bill object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/create-a-bill">Create a Bill</a>
     */
    public Bill createBill(Bill bill) throws BitPayGenericException, BitPayApiException {
        Facade facade = this.getFacadeBasedOnAccessToken();
        boolean signRequest = isSignRequest(facade);

        return this.createBill(bill, facade, signRequest);
    }

    /**
     * Create a BitPay Bill.
     *
     * @param bill        A Bill object with request parameters defined.
     * @param facade      The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay generated Bill object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/create-a-bill">Create a Bill</a>
     */
    public Bill createBill(
        Bill bill,
        Facade facade,
        boolean signRequest
    ) throws BitPayGenericException, BitPayApiException {
        return this.getBillClient().create(bill, facade, signRequest);
    }

    /**
     * Retrieve a BitPay bill by bill id using the public facade.
     *
     * @param billId The id of the bill to retrieve.
     * @return A BitPay Bill object.
     * @throws BitPayApiException BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-a-bill">Retrieve a Bill</a>
     */
    public Bill getBill(String billId) throws BitPayGenericException, BitPayApiException {
        Facade facade = this.getFacadeBasedOnAccessToken();
        boolean signRequest = isSignRequest(facade);

        return this.getBill(billId, facade, signRequest);
    }

    /**
     * Retrieve a BitPay bill by bill id using the specified facade.
     *
     * @param billId      The id of the bill to retrieve.
     * @param facade      The facade used to retrieve it.
     * @param signRequest Signed request.
     * @return A BitPay Bill object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-a-bill">Retrieve a Bill</a>
     */
    public Bill getBill(
        String billId,
        Facade facade,
        boolean signRequest
    ) throws BitPayGenericException, BitPayApiException {
        return this.getBillClient().get(billId, facade, signRequest);
    }

    /**
     * Retrieve a collection of BitPay bills.
     *
     * @param status The status to filter the bills.
     * @return A list of BitPay Bill objects.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-bills-by-status">Retrieve Bills by Status</a>
     */
    public List<Bill> getBills(String status) throws BitPayGenericException, BitPayApiException {
        return this.getBillClient().getBills(status);
    }

    /**
     * Retrieve a collection of BitPay bills.
     *
     * @return A list of BitPay Bill objects.
     * @throws BitPayGenericException BitPayApiException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-bills-by-status">Retrieve Bills by Status</a>
     */
    public List<Bill> getBills() throws BitPayGenericException, BitPayApiException {
        return this.getBillClient().getBills();
    }

    /**
     * Update a BitPay Bill.
     *
     * @param bill   A Bill object with the parameters to update defined.
     * @param billId The Id of the Bill to udpate.
     * @return An updated Bill object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/update-a-bill">Update a Bill</a>
     */
    public Bill updateBill(Bill bill, String billId) throws BitPayGenericException, BitPayApiException {
        return this.getBillClient().update(bill, billId);
    }

    /**
     * Deliver a BitPay Bill.
     *
     * @param billId    The id of the requested bill.
     * @param billToken The token of the requested bill.
     * @return A response status returned from the API.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/deliver-a-bill-via-email">Deliver a Bill Via Email</a>
     */
    public String deliverBill(
        String billId,
        String billToken
    ) throws BitPayGenericException, BitPayApiException {
        Facade facade = this.getFacadeBasedOnAccessToken();
        boolean signRequest = isSignRequest(facade);

        return this.deliverBill(billId, billToken, signRequest);
    }

    /**
     * Deliver a BitPay Bill.
     *
     * @param billId      The id of the requested bill.
     * @param billToken   The token of the requested bill.
     * @param signRequest Allow unsigned request
     * @return A response status returned from the API.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/deliver-a-bill-via-email">Deliver a Bill Via Email</a>
     */
    public String deliverBill(
        String billId,
        String billToken,
        boolean signRequest
    ) throws BitPayGenericException, BitPayApiException {
        return this.getBillClient().deliver(billId, billToken, signRequest);
    }

    /**
     * Retrieve the rates for a cryptocurrency / fiat pair. See https://bitpay.com/bitcoin-exchange-rates.
     *
     * @param baseCurrency the cryptocurrency for which you want to fetch the rates.
     *                     Current supported values are BTC and BCH.
     * @param currency the fiat currency for which you want to fetch the baseCurrency rates
     * @return A Rate object populated with the BitPay exchange rate table.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @since 8.8.0
     * @see <a href="https://developer.bitpay.com/reference/retrieve-the-rates-for-a-cryptocurrency-fiat-pair">Retrieve the rates for a cryptocurrency / fiat pair</a>
     */
    public Rate getRate(
        String baseCurrency,
        String currency
    ) throws BitPayGenericException, BitPayApiException {
        return this.getRateClient().get(baseCurrency, currency);
    }

    /**
     * Retrieve the exchange rate table maintained by BitPay.  See https://bitpay.com/bitcoin-exchange-rates.
     *
     * @return A Rates object populated with the BitPay exchange rate table.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     */
    public Rates getRates() throws BitPayGenericException, BitPayApiException {
        return this.getRateClient().getRates();
    }

    /**
     * Retrieve the exchange rate table maintained by BitPay by baseCurrency. See https://bitpay.com/bitcoin-exchange-rates.
     *
     * @param baseCurrency the cryptocurrency for which you want to fetch the rates.
     *                     Current supported values are BTC and BCH.
     * @return A Rates object populated with the BitPay exchange rate table.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @since 8.8.0
     * @see <a href="https://developer.bitpay.com/reference/retrieve-all-the-rates-for-a-given-cryptocurrency">Retrieve all the rates for a given cryptocurrency</a>
     */
    public Rates getRates(String baseCurrency) throws BitPayGenericException, BitPayApiException {
        return this.getRateClient().getRates(baseCurrency);
    }

    /**
     * Retrieve a list of ledgers entries by currency and date range using the merchant facade.
     *
     * @param currency  The three digit currency string for the ledger to retrieve.
     * @param dateStart The first date for the query filter.
     * @param dateEnd   The last date for the query filter.
     * @return Ledger entries list.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-ledger-entries">Retrieve Ledger Entries</a>
     */
    public List<LedgerEntry> getLedgerEntries(
        String currency,
        String dateStart,
        String dateEnd
    ) throws BitPayGenericException, BitPayApiException {
        return this.getLedgerClient().getEntries(currency, dateStart, dateEnd);
    }

    /**
     * Retrieve a list of ledgers using the merchant facade.
     *
     * @return A list of Ledger objects populated with the currency and current balance of each one.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-account-balances">Retrieve Account Balances</a>
     */
    public List<Ledger> getLedgers() throws BitPayGenericException, BitPayApiException {
        return this.getLedgerClient().getLedgers();
    }

    /**
     * Submit BitPay Payout Recipients.
     *
     * @param recipients PayoutRecipients A PayoutRecipients object with request parameters defined.
     * @return array A list of BitPay PayoutRecipients objects.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/invite-recipients">Invite Recipients</a>
     */
    public List<PayoutRecipient> submitPayoutRecipients(PayoutRecipients recipients)
        throws BitPayGenericException, BitPayApiException {
        return this.getPayoutRecipientsClient().submit(recipients);
    }

    /**
     * Retrieve a collection of BitPay Payout Recipients.
     *
     * @param status String|null The recipient status you want to query on.
     * @param limit  int Maximum results that the query will return (useful for
     *               paging results). result).
     * @param offset int Offset for paging.
     * @return array A list of BitPayRecipient objects.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-recipients-by-status">Retrieve Recipients by Status</a>
     */
    public List<PayoutRecipient> getPayoutRecipients(
        String status,
        Integer limit,
        Integer offset
    ) throws BitPayGenericException, BitPayApiException {
        return this.getPayoutRecipientsClient().getRecipientsByFilters(status, limit, offset);
    }

    /**
     * Retrieve a BitPay payout recipient by batch id using.  The client must have been previously authorized for the
     * payout facade.
     *
     * @param recipientId String The id of the recipient to retrieve.
     * @return PayoutRecipient A BitPay PayoutRecipient object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-a-recipient">Retrieve a Recipient</a>
     */
    public PayoutRecipient getPayoutRecipient(String recipientId) throws BitPayGenericException, BitPayApiException {
        return this.getPayoutRecipientsClient().get(recipientId);
    }

    /**
     * Update a Payout Recipient.
     *
     * @param recipientId String The recipient id for the recipient to be updated.
     * @param recipient   PayoutRecipients A PayoutRecipient object with updated
     *                    parameters defined.
     * @return The updated recipient object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/update-a-recipient">Update a Recipient</a>
     */
    public PayoutRecipient updatePayoutRecipient(
        String recipientId,
        PayoutRecipient recipient
    ) throws BitPayGenericException, BitPayApiException {
        return this.getPayoutRecipientsClient().update(recipientId, recipient);
    }

    /**
     * Cancel a BitPay Payout recipient.
     *
     * @param recipientId String The id of the recipient to cancel.
     * @return True if the delete operation was successful, false otherwise.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/remove-a-recipient">Remove a Recipient</a>
     */
    public Boolean deletePayoutRecipient(String recipientId) throws BitPayGenericException, BitPayApiException {
        return this.getPayoutRecipientsClient().delete(recipientId);
    }

    /**
     * Request a payout recipient notification.
     *
     * @param recipientId String A BitPay recipient ID.
     * @return True if the notification was successfully sent, false otherwise.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/request-a-recipient-webhook-to-be-resent">Request a Recipient Webhook to be Resent</a>
     */
    public Boolean requestPayoutRecipientNotification(String recipientId)
        throws BitPayGenericException, BitPayApiException {
        return this.getPayoutRecipientsClient().requestNotification(recipientId);
    }

    /**
     * Submit a BitPay Payout.
     *
     * @param payout Payout A Payout object with request parameters defined.
     * @return A BitPay generated Payout object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/create-a-payout">Create a Payout</a>
     */
    public Payout submitPayout(Payout payout) throws BitPayGenericException, BitPayApiException {
        return this.getPayoutClient().submit(payout);
    }

    /**
     * Submit a BitPay Payouts.
     *
     * @param payouts Collection of Payout objects with request parameters defined.
     * @return A BitPay PayoutGroup with generated Payout objects and information's about not created payouts.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/create-payout-group">Create a Payouts</a>
     */
    public PayoutGroup submitPayouts(Collection<Payout> payouts) throws BitPayGenericException, BitPayApiException {
        return this.getPayoutGroupClient().submit(payouts);
    }

    /**
     * Retrieve a BitPay payout by payout id using. The client must have been
     * previously authorized for the payout facade.
     *
     * @param payoutId String The id of the payout to retrieve.
     * @return A BitPay Payout object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-a-payout">Retrieve a Payout</a>
     */
    public Payout getPayout(String payoutId) throws BitPayGenericException, BitPayApiException {
        return this.getPayoutClient().get(payoutId);
    }

    /**
     * Cancel a BitPay Payout.
     *
     * @param payoutId String The id of the payout to cancel.
     * @return True if the refund was successfully canceled, false otherwise.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/cancel-a-payout">Cancel a Payout</a>
     */
    public Boolean cancelPayout(String payoutId) throws BitPayGenericException, BitPayApiException {
        return this.getPayoutClient().cancel(payoutId);
    }

    /**
     * Cancel a BitPay Payouts.
     *
     * @param groupId String The id of the payout group to cancel.
     * @return A BitPay PayoutGroup with cancelled Payout objects and information's about not cancelled payouts.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/cancel-a-payout-group">Cancel a Payouts</a>
     */
    public PayoutGroup cancelPayouts(String groupId) throws BitPayGenericException, BitPayApiException {
        return this.getPayoutGroupClient().cancel(groupId);
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
     * @param groupId   String The optional group id assigned to payout.
     * @return A list of BitPay Payout objects.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-payouts-filtered-by-query">Retrieve Payouts Filtered by Query</a>
     */
    public List<Payout> getPayouts(
        String startDate,
        String endDate,
        String status,
        String reference,
        Integer limit,
        Integer offset,
        String groupId
    ) throws BitPayGenericException, BitPayApiException {
        return this.getPayoutClient().getPayouts(startDate, endDate, status, reference, limit, offset, groupId);
    }

    /**
     * Request a payout notification.
     *
     * @param payoutId String The id of the payout to notify.
     * @return True if the notification was successfully sent, false otherwise.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/request-a-payout-webhook-to-be-resent">Request a Payout Webhook to be Resent</a>
     */
    public Boolean requestPayoutNotification(String payoutId) throws BitPayGenericException, BitPayApiException {
        return this.getPayoutClient().requestNotification(payoutId);
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
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-settlements">Retrieve Settlements</a>
     */
    public List<Settlement> getSettlements(
        String currency,
        String dateStart,
        String dateEnd,
        String status,
        Integer limit,
        Integer offset
    ) throws BitPayGenericException, BitPayApiException {
        return this.getSettlementClient().getSettlements(currency, dateStart, dateEnd, status, limit, offset);
    }

    /**
     * Retrieves a summary of the specified settlement.
     *
     * @param settlementId Settlement Id.
     * @return A BitPay Settlement object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-a-settlement">Retrieve a Settlement</a>
     */
    public Settlement getSettlement(String settlementId) throws BitPayGenericException, BitPayApiException {
        return this.getSettlementClient().get(settlementId);
    }

    /**
     * Gets a detailed reconciliation report of the activity within the settlement period.
     * Required id and settlement token.
     *
     * @param settlementId Settlement ID.
     * @param token Settlement token.
     * @return A detailed BitPay Settlement object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     * @see <a href="https://developer.bitpay.com/reference/fetch-a-reconciliation-report">Fetch a Reconciliation Report</a>
     */
    public Settlement getSettlementReconciliationReport(String settlementId, String token)
        throws BitPayGenericException, BitPayApiException {
        return this.getSettlementClient().getSettlementReconciliationReport(settlementId, token);
    }

    /**
     * Retrieve all supported wallets.
     *
     * @return A list of wallet objets.
     * @throws BitPayApiException BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     * @see <a href="https://developer.bitpay.com/reference/retrieve-the-supported-wallets">Retrieve the Supported Wallets</a>
     */
    public List<Wallet> getSupportedWallets() throws BitPayGenericException, BitPayApiException {
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Gets http client.
     *
     * @param proxyDetails the proxy details
     * @param proxyCreds   the proxy creds
     * @return the http client
     */
    protected HttpClient getHttpClient(
        HttpHost proxyDetails,
        CredentialsProvider proxyCreds
    ) {
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
     * @throws BitPayGenericException BitPayGenericException class
     */
    protected ECKey getEcKey(PrivateKey privateKey) throws BitPayGenericException {
        File privateKeyFile = new File(privateKey.value());
        if (privateKeyFile.exists() && KeyUtils.privateKeyExists(privateKey.value().replace("\"", ""))) {
            try {
                return KeyUtils.loadEcKey();
            } catch (Exception e) {
                BitPayExceptionProvider.throwGenericExceptionWithMessage(LOAD_PRIVATE_KEY_EXCEPTION + e.getMessage());
            }
        } else {
            try {
                return KeyUtils.createEcKeyFromHexString(privateKey.value());
            } catch (Exception e) {
                BitPayExceptionProvider.throwGenericExceptionWithMessage("Private Key file not found");
            }
        }

        return null;
    }

    /**
     * Initialize the public/private key pair by either loading the existing one or by creating a new one.
     *
     * @param config the config
     * @return ECKey
     * @throws BitPayGenericException BitPayGenericException class
     */
    protected ECKey getEcKey(Config config) throws BitPayGenericException {
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
        } catch (Exception e) {
            BitPayExceptionProvider.throwGenericExceptionWithMessage(LOAD_PRIVATE_KEY_EXCEPTION + e.getMessage());
        }

        return null;
    }

    /**
     * Derive identity.
     *
     * @param ecKey ECKey
     * @throws IllegalArgumentException the illegal argument exception
     * @throws BitPayGenericException          BitPayGenericException class
     */
    protected void deriveIdentity(ECKey ecKey) throws BitPayGenericException {
        // Identity in this implementation is defined to be the SIN.
        try {
            this.identity = KeyUtils.deriveSin(ecKey);
        } catch (Exception e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Token array", e.getMessage());
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
     * @throws BitPayGenericException BitPayGenericException class
     */
    protected Config buildConfigFromFile(ConfigFilePath configFilePath) throws BitPayGenericException {
        Config config = null;
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(configFilePath.value()));
            JsonMapper mapper = JsonMapperFactory.create();
            //read JSON like DOM Parser
            JsonNode rootNode = mapper.readTree(jsonData);
            JsonNode bitPayConfiguration = rootNode.path("BitPayConfiguration");
            config = mapper.readValue(bitPayConfiguration.toString(), Config.class);
        } catch (Exception e) {
            BitPayExceptionProvider.throwGenericExceptionWithMessage(
                "Failed to read configuration file : " + e.getMessage());
        }

        return config;
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

    protected PayoutGroupClient getPayoutGroupClient() {
        return PayoutGroupClient.getInstance(this.bitPayClient, this.tokenContainer);
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
