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
import com.bitpay.sdk.client.PayoutBatchClient;
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
import com.bitpay.sdk.model.Invoice.Refund;
import com.bitpay.sdk.model.Ledger.Ledger;
import com.bitpay.sdk.model.Payout.Payout;
import com.bitpay.sdk.model.Payout.PayoutBatch;
import com.bitpay.sdk.model.Payout.PayoutRecipient;
import com.bitpay.sdk.model.Payout.PayoutRecipients;
import com.bitpay.sdk.model.Rate.Rates;
import com.bitpay.sdk.model.Settlement.Settlement;
import com.bitpay.sdk.model.Wallet.Wallet;
import com.bitpay.sdk.util.AccessTokenCache;
import com.bitpay.sdk.util.KeyUtils;
import com.bitpay.sdk.util.UuidGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.bitcoinj.core.ECKey;

public class Client {

    private UuidGenerator uuidGenerator;
    private Config configuration;
    private String environment;
    private ECKey ecKey;
    private BitPayClient bitPayClient;
    private AccessTokenCache accessTokenCache;

    /**
     * Return the identity of this client (i.e. the public key).
     */
    private String identity;

    /**
     * Constructor for use if the keys and SIN are managed by this library.
     *
     * @param environment      Target environment. Options: Env.Test / Env.Prod
     * @param privateKey       The full path to the securely located private key or the HEX key value.
     * @param tokens           Env.Tokens containing the available tokens.
     * @param proxyDetails     HttpHost Optional Proxy setting (set to NULL to ignore)
     * @param proxyCredentials CredentialsProvider Optional Proxy Basic Auth Credentials (set to NULL to ignore)
     * @throws BitPayException BitPayException class
     */
    public Client(
        String environment,
        String privateKey,
        Env.Tokens tokens,
        HttpHost proxyDetails,
        CredentialsProvider proxyCredentials
    ) throws BitPayException {
        try {
            this.environment = environment;
            this.buildConfig(privateKey, tokens);
            this.accessTokenCache = new AccessTokenCache(this.configuration);
            this.initKeys();
            this.init();
            this.bitPayClient = new BitPayClient(
                getHttpClient(proxyDetails, proxyCredentials),
                this.ecKey,
                new HttpRequestFactory(),
                getBaseUrl(environment)
            );
            this.uuidGenerator = new UuidGenerator();
        } catch (JsonProcessingException e) {
            throw new BitPayException(null,
                "failed to deserialize BitPay server response (Config) : " + e.getMessage());
        } catch (URISyntaxException e) {
            throw new BitPayException(null,
                "failed to deserialize BitPay server response (Config) : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null,
                "failed to deserialize BitPay server response (Config) : " + e.getMessage());
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
            this.buildConfigFromFile(configFilePath);
            this.accessTokenCache = new AccessTokenCache(this.configuration);
            this.initKeys();
            this.init();
            this.bitPayClient = new BitPayClient(
                getHttpClient(proxy, proxyCredentials),
                this.ecKey,
                new HttpRequestFactory(),
                getBaseUrl(this.configuration.getEnvironment())
            );
            this.uuidGenerator = new UuidGenerator();
        } catch (JsonProcessingException e) {
            throw new BitPayException(null, "failed to deserialize BitPay server response (Config) : " + e.getMessage());
        } catch (URISyntaxException e) {
            throw new BitPayException(null, "failed to deserialize BitPay server response (Config) : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null, "failed to deserialize BitPay server response (Config) : " + e.getMessage());
        }
    }

    public Client(
        Config configuration,
        BitPayClient bitPayClient,
        ECKey ecKey,
        String identity,
        AccessTokenCache accessTokenCache,
        UuidGenerator uuidGenerator
    ) {
        this.configuration = configuration;
        this.environment = configuration.getEnvironment();
        this.bitPayClient = bitPayClient;
        this.ecKey = ecKey;
        this.identity = identity;
        this.accessTokenCache = accessTokenCache;
        this.uuidGenerator = uuidGenerator;
    }

    /**
     * Authorize (pair) this client with the server using the specified pairing code.
     *
     * @param pairingCode A code obtained from the server; typically from bitpay.com/api-tokens.
     * @throws BitPayException BitPayException class
     */
    public void authorizeClient(String pairingCode) throws BitPayException {
        this.getAuthorizationClient().authorizeClient(pairingCode);
    }

    /**
     * Request a pairing code from the BitPay server.
     *
     * @param facade Defines the level of API access being requested
     * @return A pairing code for claim at https://bitpay.com/dashboard/merchant/api-tokens.
     * @throws BitPayException BitPayException class
     */
    public String requestClientAuthorization(String facade) throws BitPayException {
        return this.getAuthorizationClient().requestClientAuthorization(facade);
    }

    /**
     * Retrieve a token associated with a known resource. The token is used to access other related resources.
     *
     * @param key The identifier for the desired resource.
     * @return The token associated with resource.
     * @throws BitPayException BitPayException class
     */
    public String getAccessToken(String key) throws BitPayException {
        return this.accessTokenCache.getAccessToken(key);
    }

    /**
     * Gets info for specific currency.
     *
     * @param currencyCode String Currency code for which the info will be retrieved.
     * @return Map|null
     */
    public Map getCurrencyInfo(String currencyCode) throws BitPayException {
        CurrencyClient client = new CurrencyClient(this.bitPayClient);
        return client.getCurrencyInfo(currencyCode);
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
            InvoiceClient client = getInvoiceClient();
            return client.createInvoice(invoice, Facade.Merchant, true, this.accessTokenCache);
        } catch (BitPayException ex) {
            throw new InvoiceCreationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceCreationException(null, e.getMessage());
        }
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
            return this.getInvoiceClient().getInvoice(invoiceId, Facade.Merchant, true);
        } catch (BitPayException ex) {
            throw new InvoiceQueryException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new InvoiceQueryException(null, e.getMessage());
        }
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
    public Invoice getInvoiceByGuid(String guid, String facade, Boolean signRequest) throws InvoiceQueryException {
        return this.getInvoiceClient().getInvoiceByGuid(guid, facade, signRequest);
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
        return this.getInvoiceClient().updateInvoice(invoiceId, buyerSms, smsCode, buyerEmail, autoVerify);
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
        return this.getInvoiceClient().payInvoice(invoiceId, status);
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
        return this.getInvoiceClient().cancelInvoice(invoiceId);
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
        return this.getInvoiceClient().cancelInvoice(invoiceId, forceCancel);
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
     * @throws BitPayException         BitPayException class
     */
    public Refund createRefund(String invoiceId, Double amount, Boolean preview, Boolean immediate,
                               Boolean buyerPaysRefundFee, String reference) throws
        RefundCreationException, BitPayException {
        return this.getRefundClient().createRefund(invoiceId, amount, preview, immediate, buyerPaysRefundFee, reference);
    }

    /**
     * Retrieve a previously made refund request on a BitPay invoice.
     *
     * @param refundId The BitPay refund ID.
     * @return A BitPay Refund object with the associated Refund object.
     * @throws RefundQueryException RefundQueryException class
     * @throws BitPayException      BitPayException class
     */
    public Refund getRefund(String refundId) throws RefundQueryException, BitPayException {
        return this.getRefundClient().getRefund(refundId);
    }

    /**
     * Retrieve all refund requests on a BitPay invoice.
     *
     * @param invoiceId The BitPay invoice object having the associated refunds.
     * @return A list of BitPay Refund objects with the associated Refund objects.
     * @throws RefundQueryException RefundQueryException class
     * @throws BitPayException      BitPayException class
     */
    public List<Refund> getRefunds(String invoiceId) throws RefundQueryException, BitPayException {
        return this.getRefundClient().getRefunds(invoiceId);
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
        return this.getRefundClient().updateRefund(refundId, status);
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
        return this.getRefundClient().sendRefundNotification(refundId);
    }

    /**
     * Cancel a previously submitted refund request on a BitPay invoice.
     *
     * @param refundId The refund Id for the refund to be canceled.
     * @return An updated Refund Object.
     * @throws RefundCancellationException RefundCancellationException class
     * @throws BitPayException             BitPayException class
     */
    public Refund cancelRefund(String refundId) throws RefundCancellationException, BitPayException {
        return this.getRefundClient().cancelRefund(refundId);
    }

    /**
     * Create a BitPay bill using the POS facade.
     *
     * @param bill An Bill object with request parameters defined.
     * @return A BitPay generated Bill object.
     * @throws BillCreationException BillCreationException class
     */
    public Bill createBill(Bill bill) throws BillCreationException {
        return this.getBillClient().createBill(bill);
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
    public Bill createBill(Bill bill, String facade, boolean signRequest)
        throws BitPayException, BillCreationException {
        return this.getBillClient().createBill(bill, facade, signRequest);
    }

    /**
     * Retrieve a BitPay bill by bill id using the public facade.
     *
     * @param billId The id of the bill to retrieve.
     * @return A BitPay Bill object.
     * @throws BillQueryException BillQueryException class
     */
    public Bill getBill(String billId) throws BillQueryException {
        return this.getBillClient().getBill(billId);
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
        return this.getBillClient().getBill(billId, facade, signRequest);
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
        return this.getBillClient().getBills(status);
    }

    /**
     * Retrieve a collection of BitPay bills.
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
     * @param bill   A Bill object with the parameters to update defined.
     * @param billId The Id of the Bill to udpate.
     * @return An updated Bill object.
     * @throws BitPayException     BitPayException class
     * @throws BillUpdateException BillUpdateException class
     */
    public Bill updateBill(Bill bill, String billId) throws BitPayException, BillUpdateException {
        return this.getBillClient().updateBill(bill, billId);
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
        return this.getBillClient().deliverBill(billId, billToken);
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
        return this.getBillClient().deliverBill(billId, billToken, signRequest);
    }

    /**
     * Retrieve the exchange rate table maintained by BitPay.  See https://bitpay.com/bitcoin-exchange-rates.
     *
     * @return A Rates object populated with the BitPay exchange rate table.
     * @throws RateQueryException RateQueryException class
     */
    public Rates getRates() throws RateQueryException {
        return this.getRatesClient().getRates();
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
    public Ledger getLedger(String currency, String dateStart, String dateEnd) throws BitPayException,
        LedgerQueryException {
        return this.getLedgerClient().getLedger(currency, dateStart, dateEnd);
    }

    /**
     * Retrieve a list of ledgers using the merchant facade.
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
     * @param recipients PayoutRecipients A PayoutRecipients object with request parameters defined.
     * @return array A list of BitPay PayoutRecipients objects..
     * @throws BitPayException                  BitPayException class
     * @throws PayoutRecipientCreationException PayoutRecipientCreationException class
     */
    public List<PayoutRecipient> submitPayoutRecipients(PayoutRecipients recipients) throws BitPayException,
        PayoutRecipientCreationException {
        return this.getPayoutRecipientsClient().submitPayoutRecipients(recipients);
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
        return this.getPayoutRecipientsClient().getPayoutRecipients(status, limit, offset);
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
    public PayoutRecipient getPayoutRecipient(String recipientId)
        throws BitPayException, PayoutRecipientQueryException {
        return this.getPayoutRecipientsClient().getPayoutRecipient(recipientId);
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
        return this.getPayoutRecipientsClient().updatePayoutRecipient(recipientId, recipient);
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
        return this.getPayoutRecipientsClient().deletePayoutRecipient(recipientId);
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
        return this.getPayoutRecipientsClient().requestPayoutRecipientNotification(recipientId);
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
        return this.getPayoutClient().submitPayout(payout);
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
        return this.getPayoutClient().getPayout(payoutId);
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
        return this.getPayoutClient().cancelPayout(payoutId);
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
        return this.getPayoutClient().getPayouts(startDate, endDate, status, reference, limit, offset);
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
        return this.getPayoutClient().requestPayoutNotification(payoutId);
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
        return this.getPayoutBatchClient().submitPayoutBatch(batch);
    }

    /**
     * Retrieve a collection of BitPay payout batches.
     *
     * @return A list of BitPay PayoutBatch objects.
     * @throws BitPayException           BitPayException class
     * @throws PayoutBatchQueryException PayoutBatchQueryException class
     */
    public List<PayoutBatch> getPayoutBatches() throws BitPayException, PayoutBatchQueryException {
        return this.getPayoutBatchClient().getPayoutBatches();
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
        return this.getPayoutBatchClient().getPayoutBatches(status);
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
        return this.getPayoutBatchClient().getPayoutBatches(startDate, endDate, status, limit, offset);
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
        return this.getPayoutBatchClient().getPayoutBatch(payoutBatchId);
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
        return this.getPayoutBatchClient().cancelPayoutBatch(payoutBatchId);
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
        return this.getPayoutBatchClient().requestPayoutBatchNotification(payoutBatchId);
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
     * @param settlementId Settlement Id.
     * @return A BitPay Settlement object.
     * @throws BitPayException          BitPayException class
     * @throws SettlementQueryException SettlementQueryException class
     */
    public Settlement getSettlement(String settlementId) throws BitPayException, SettlementQueryException {
        return this.getSettlementClient().getSettlement(settlementId);
    }

    /**
     * Gets a detailed reconciliation report of the activity within the settlement period.
     *
     * @param settlement Settlement to generate report for.
     * @return A detailed BitPay Settlement object.
     * @throws SettlementQueryException SettlementQueryException class
     */
    public Settlement getSettlementReconciliationReport(Settlement settlement) throws SettlementQueryException {
        return this.getSettlementClient().getSettlementReconciliationReport(settlement);
    }

    /**
     * Retrieve all supported wallets.
     *
     * @return A list of wallet objets.
     * @throws WalletQueryException WalletQueryException class
     * @throws BitPayException      BitPayException class
     */
    public List<Wallet> getSupportedWallets() throws WalletQueryException, BitPayException {
        return this.getWalletClient().getSupportedWallets();
    }

    public RateClient getRatesClient() {
        return new RateClient(this.bitPayClient);
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
     * Initialize this object with the client name and the environment Url.
     *
     * @throws Exception
     * @throws URISyntaxException
     */
    protected void init() throws BitPayException {
        try {
            deriveIdentity();
            loadAccessTokens();
        } catch (Exception e) {
            throw new BitPayException(null,
                "failed to deserialize BitPay server response (Token array) : " + e.getMessage());
        }
    }

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
     * Initialize the public/private key pair by either loading the existing one or by creating a new one.
     *
     * @throws Exception
     * @throws URISyntaxException
     */
    protected void initKeys() throws Exception, URISyntaxException {
        if (!Objects.isNull(this.ecKey)) {
            return;
        }

        try {
            if (KeyUtils.privateKeyExists(
                this.configuration.getEnvConfig(this.environment).path("PrivateKeyPath").toString().replace("\"", ""))) {
                ecKey = KeyUtils.loadEcKey();
            } else {
                String keyHex =
                    this.configuration.getEnvConfig(this.environment).path("PrivateKey").toString().replace("\"", "");
                if (!keyHex.isEmpty()) {
                    ecKey = KeyUtils.createEcKeyFromHexString(keyHex);
                }
            }
        } catch (Exception e) {
            throw new BitPayException(null,
                "When trying to load private key. Make sure the configuration details are correct and the private key and tokens are valid : " +
                    e.getMessage());
        }
    }

    protected void deriveIdentity() throws IllegalArgumentException {
        // Identity in this implementation is defined to be the SIN.
        this.identity = KeyUtils.deriveSIN(this.ecKey);
    }

    protected void clearAccessTokenCache() {
        this.accessTokenCache.clear();
    }

    /**
     * Load tokens from configuration.
     *
     * @throws BitPayException BitPayException class
     */
    protected void loadAccessTokens() throws BitPayException {
        try {
            this.clearAccessTokenCache();

            Iterator<Map.Entry<String, JsonNode>> tokens =
                this.configuration.getEnvConfig(this.environment).path("ApiTokens").fields();
            while (tokens.hasNext()) {
                Map.Entry<String, JsonNode> next = tokens.next();
                if (!next.getValue().asText().isEmpty()) {
                    accessTokenCache.put(next.getKey(), next.getValue().asText());
                }
            }
        } catch (Exception e) {
            throw new BitPayException(null, "When trying to load the tokens : " + e.getMessage());
        }
    }

    protected String getBaseUrl(String environment) {
        return environment.equals(Env.Test) ? Env.TestUrl : Env.ProdUrl;
    }

    /**
     * Loads the configuration file (JSON).
     *
     * @throws BitPayException BitPayException class
     */
    protected void buildConfigFromFile(String configFilePath) throws BitPayException {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(configFilePath));
            //create ObjectMapper instance
            ObjectMapper mapper = new ObjectMapper();
            //read JSON like DOM Parser
            JsonNode rootNode = mapper.readTree(jsonData);
            JsonNode bitPayConfiguration = rootNode.path("BitPayConfiguration");
            this.configuration = new ObjectMapper().readValue(bitPayConfiguration.toString(), Config.class);
            this.environment = this.configuration.getEnvironment();
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
    protected void buildConfig(String privateKey, Env.Tokens tokens) throws BitPayException {
        try {
            String keyHex = "", keyFile = "";
            File privateKeyFile = new File(privateKey);
            if (!privateKeyFile.exists()) {
                try {
                    ecKey = KeyUtils.createEcKeyFromHexString(privateKey);
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
            config.setEnvironment(this.environment);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode ApiTokens = mapper.valueToTree(tokens);

            ObjectNode envConfig = mapper.createObjectNode();
            envConfig.put("PrivateKeyPath", keyFile);
            envConfig.put("PrivateKey", keyHex);
            envConfig.put("ApiTokens", ApiTokens);

            ObjectNode envTarget = mapper.createObjectNode();
            envTarget.put(this.environment, envConfig);

            config.setEnvConfig(envTarget);
            this.configuration = config;
        } catch (Exception e) {
            throw new BitPayException(null, "failed to process configuration : " + e.getMessage());
        }
    }

    protected AuthorizationClient getAuthorizationClient() {
        return new AuthorizationClient(this.bitPayClient, this.uuidGenerator, this.accessTokenCache, this.identity);
    }

    protected InvoiceClient getInvoiceClient() {
        return new InvoiceClient(this.bitPayClient, this.accessTokenCache, uuidGenerator);
    }

    protected RefundClient getRefundClient() {
        return new RefundClient(this.bitPayClient, this.accessTokenCache);
    }

    protected BillClient getBillClient() {
        return new BillClient(this.bitPayClient, this.accessTokenCache);
    }

    protected LedgerClient getLedgerClient() {
        return new LedgerClient(this.bitPayClient, this.accessTokenCache);
    }

    protected PayoutRecipientsClient getPayoutRecipientsClient() {
        return new PayoutRecipientsClient(this.bitPayClient, this.accessTokenCache, this.uuidGenerator);
    }

    protected PayoutClient getPayoutClient() {
        return new PayoutClient(this.bitPayClient, this.accessTokenCache);
    }

    protected PayoutBatchClient getPayoutBatchClient() {
        return new PayoutBatchClient(this.bitPayClient, this.accessTokenCache, this.uuidGenerator);
    }

    protected SettlementClient getSettlementClient() {
        return new SettlementClient(this.bitPayClient, this.accessTokenCache);
    }

    protected WalletClient getWalletClient() {
        return new WalletClient(this.bitPayClient);
    }
}
