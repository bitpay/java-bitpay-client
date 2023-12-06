/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.payout;

import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.Currency;
import com.bitpay.sdk.model.ModelConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The type Payout.
 * This resource allows merchants to submit cryptocurrency payouts to active bitpay recipients.
 * The typical use cases for this resource would be a company
 * who wants to offer cryptocurrency withdrawals to their customers,
 * like marketplaces or affiliate networks,
 * or for payroll purposes by creating multiple payouts at a time.
 *
 * @see <a href="https://bitpay.readme.io/reference/payouts">REST API Payouts</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payout {

    private String token = ModelConfiguration.DEFAULT_NON_SENT_VALUE;

    private Double amount = 0.0;
    private String currency = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private ZonedDateTime effectiveDate;

    private String reference = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String notificationEmail = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String notificationUrl = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String ledgerCurrency = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String groupId = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String id;
    private String shopperId = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String recipientId;
    private Map<String, Map<String, Double>> exchangeRates;
    private String accountId = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String email;
    private String label;
    private String status;
    private String message;
    private ZonedDateTime requestDate;
    private ZonedDateTime dateExecuted;
    private Integer code;
    private List<PayoutTransaction> transactions = Collections.emptyList();
    public Boolean ignoreEmails;

    /**
     * Constructor, create an empty Payout object.
     */
    public Payout() {
        this.amount = 0.0;
        this.currency = "USD";
    }

    /**
     * Constructor, create an instruction-full request Payout object.
     *
     * @param amount         Date when request is effective. Note that the time of
     *                       day will automatically be set to 09:00:00.000 UTC time
     *                       for the given day. Only requests submitted before
     *                       09:00:00.000 UTC are guaranteed to be processed on the
     *                       same day.
     * @param currency       The three digit currency string for the PayoutBatch to
     *                       use.
     * @param ledgerCurrency Ledger currency code set for the payout request
     *                       (ISO4217 3-character currency code), it indicates on
     *                       which ledger the payoutrequest will be recorded. If not
     *                       provided in the request, this parameter will be set by
     *                       default to the active ledger currency on your
     *                       account,e.g. your settlement currency. Supported ledger
     *                       currency codes for payout requestsare EUR, USD, GBP,
     *                       CAD, NZD, AUD, ZAR, JPY, BTC, BCH, GUSD, USDC, PAX,XRP,
     *                       BUSD, DOGE,ETH, WBTC, DAI
     */
    public Payout(final Double amount, final String currency, final String ledgerCurrency) {
        this.amount = amount;
        this.currency = currency;
        this.ledgerCurrency = ledgerCurrency;
    }

    // API fields
    //

    /**
     * Gets resource token.
     * <p>
     * This token is actually derived from the API token -
     * used to submit the payout and is tied to the specific payout resource id created..
     * </p>
     *
     * @return the token
     */
    @JsonProperty("token")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getToken() {
        return this.token;
    }

    /**
     * Sets resource token.
     * This token is actually derived from the API token -
     * used to submit the payout and is tied to the specific payout resource id created..
     *
     * @param token the token
     */
    @JsonProperty("token")
    public void setToken(final String token) {
        this.token = token;
    }

    // Required fields
    //

    /**
     * Gets amount of cryptocurrency sent to the requested address.
     *
     * @return the amount
     */
    @JsonProperty("amount")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Double getAmount() {
        return this.amount;
    }

    /**
     * Sets amount of cryptocurrency sent to the requested address.
     *
     * @param amount the amount
     */
    @JsonProperty("amount")
    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    /**
     * Gets currency code set for the batch amount (ISO 4217 3-character currency code).
     *
     * @return the currency
     */
    @JsonProperty("currency")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCurrency() {
        return this.currency;
    }

    /**
     * Sets currency code set for the batch amount (ISO 4217 3-character currency code).
     *
     * @param currency the currency
     * @throws BitPayGenericException the bit pay exception
     */
    @JsonProperty("currency")
    public void setCurrency(final String currency) throws BitPayGenericException {
        if (!Currency.isValid(currency)) {
            BitPayExceptionProvider.throwInvalidCurrencyException(currency);
        }
        this.currency = currency;
    }

    /**
     * Gets Ledger currency code (ISO 4217 3-character currency code),
     * it indicates on which ledger the payout request will be recorded. If not provided in the request,
     * this parameter will be set by default to the active ledger currency on your account,
     * e.g. your settlement currency.
     *
     * @return the ledger currency
     * @see <a href="https://bitpay.readme.io/reference/payouts">Supported ledger currency codes</a>
     */
    @JsonProperty("ledgerCurrency")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getLedgerCurrency() {
        return this.ledgerCurrency;
    }

    /**
     * Sets Ledger currency code (ISO 4217 3-character currency code),
     * it indicates on which ledger the payout request will be recorded. If not provided in the request,
     * this parameter will be set by default to the active ledger currency on your account,
     * e.g. your settlement currency.
     *
     * @param ledgerCurrency the ledger currency
     * @throws BitPayGenericException the bit pay exception
     */
    @JsonProperty("ledgerCurrency")
    public void setLedgerCurrency(final String ledgerCurrency) throws BitPayGenericException {
        if (!Currency.isValid(ledgerCurrency)) {
            BitPayExceptionProvider.throwInvalidCurrencyException(ledgerCurrency);
        }
        this.ledgerCurrency = ledgerCurrency;
    }

    /**
     * Added to the payouts made at the same time through the `Create Payout Group` request.
     * Can be used for querying or deleting.
     *
     * @return group id
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getGroupId() {
        return this.groupId;
    }

    /**
     * Added to the payouts made at the same time through the `Create Payout Group` request.
     * Can be used for querying or deleting.
     *
     * @param groupId group id
     */
    @JsonProperty("groupId")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setGroupId(final String groupId) {
        this.groupId = groupId;
    }

    /**
     * Gets effective date and time (UTC) for the payout.
     * ISO-8601 format yyyy-mm-ddThh:mm:ssZ. If not provided, defaults to date and time of creation.
     *
     * @return the effective date
     */
    @JsonProperty("effectiveDate")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public ZonedDateTime getEffectiveDate() {
        return this.effectiveDate;
    }

    /**
     * Sets effective date and time (UTC) for the payout.
     * ISO-8601 format yyyy-mm-ddThh:mm:ssZ. If not provided, defaults to date and time of creation.
     *
     * @param effectiveDate the effective date
     */
    @JsonProperty("effectiveDate")
    public void setEffectiveDate(final ZonedDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Gets transactions. Contains the cryptocurrency transaction details for the executed payout request.
     *
     * @return the transactions
     */
    @JsonProperty("transactions")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<PayoutTransaction> getTransactions() {
        return this.transactions;
    }

    /**
     * Sets transactions. Contains the cryptocurrency transaction details for the executed payout request.
     *
     * @param transactions the transactions
     */
    @JsonProperty("transactions")
    public void setTransactions(final List<PayoutTransaction> transactions) {
        this.transactions = transactions;
    }

    // Optional fields
    //

    /**
     * Gets reference.
     * Present only if specified by the merchant in the request.
     * Merchants can pass their own unique identifier in this field for reconciliation purpose.
     * Maximum string length is 100 characters.
     *
     * @return the reference
     */
    @JsonProperty("reference")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getReference() {
        return this.reference;
    }

    /**
     * Sets reference.
     * Present only if specified by the merchant in the request.
     * Merchants can pass their own unique identifier in this field for reconciliation purpose.
     * Maximum string length is 100 characters.
     *
     * @param reference the reference
     */
    @JsonProperty("reference")
    public void setReference(final String reference) {
        this.reference = reference;
    }

    /**
     * Gets notification email.
     * Merchant email address for notification of payout status change.
     *
     * @return the notification email
     */
    @JsonProperty("notificationEmail")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getNotificationEmail() {
        return this.notificationEmail;
    }

    /**
     * Sets notification email.
     * Merchant email address for notification of payout status change.
     *
     * @param notificationEmail the notification email
     */
    @JsonProperty("notificationEmail")
    public void setNotificationEmail(final String notificationEmail) {
        this.notificationEmail = notificationEmail;
    }

    /**
     * Gets notification url.
     * URL to which BitPay sends webhook notifications. HTTPS is mandatory
     *
     * @return the notification url
     */
    @JsonProperty("notificationURL")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getNotificationUrl() {
        return this.notificationUrl;
    }

    /**
     * Sets notification url.
     * URL to which BitPay sends webhook notifications. HTTPS is mandatory
     *
     * @param notificationUrl the notification url
     */
    @JsonProperty("notificationURL")
    public void setNotificationUrl(final String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

    // Response fields
    //

    /**
     * Gets Payout request id..
     *
     * @return the id
     */
    @JsonIgnore
    public String getId() {
        return this.id;
    }

    /**
     * Sets Payout request id..
     *
     * @param id the id
     */
    @JsonProperty("id")
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Gets shopper id.
     * This is the unique id assigned by BitPay if the shopper used his personal BitPay account to authenticate and
     * pay an invoice. For customers signing up for a brand new BitPay personal account,
     * this id will only be created as part of the payout onboarding.
     * The same field would also be available on paid invoices for customers who signed in with their
     * BitPay personal accounts before completing the payment.
     * This can allow merchants to monitor the activity of a customer (deposits and payouts).
     *
     * @return the shopper id
     */
    @JsonProperty("shopperId")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getShopperId() {
        return this.shopperId;
    }

    /**
     * Sets shopper id.
     * This is the unique id assigned by BitPay if the shopper used his personal BitPay account to authenticate and
     * pay an invoice. For customers signing up for a brand new BitPay personal account,
     * this id will only be created as part of the payout onboarding.
     * The same field would also be available on paid invoices for customers who signed in with their
     * BitPay personal accounts before completing the payment.
     * This can allow merchants to monitor the activity of a customer (deposits and payouts).
     *
     * @param shopperId the shopper id
     */
    @JsonProperty("shopperId")
    public void setShopperId(final String shopperId) {
        this.shopperId = shopperId;
    }

    /**
     * Gets BitPay recipient id.
     * assigned by BitPay for a given recipient email during the onboarding process (see Recipients resource).
     *
     * @return the recipient id
     */
    @JsonProperty("recipientId")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getRecipientId() {
        return this.recipientId;
    }

    /**
     * Sets BitPay recipient id.
     * assigned by BitPay for a given recipient email during the onboarding process (see Recipients resource).
     *
     * @param recipientId the recipient id
     */
    @JsonProperty("recipientId")
    public void setRecipientId(final String recipientId) {
        this.recipientId = recipientId;
    }

    /**
     * Gets exchange rates keyed by source and target currencies.
     *
     * @return the exchange rates
     */
    @JsonProperty("exchangeRates")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Map<String, Map<String, Double>> getExchangeRates() {
        return this.exchangeRates;
    }

    /**
     * Sets exchange rates keyed by source and target currencies.
     *
     * @param exchangeRates the exchange rates
     */
    @JsonProperty("exchangeRates")
    public void setExchangeRates(final Map<String, Map<String, Double>> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    /**
     * Gets BitPay account id that is associated to the payout,
     * assigned by BitPay for a given account during the onboarding process.
     *
     * @return the account id
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getAccountId() {
        return this.accountId;
    }

    /**
     * Sets BitPay account id that is associated to the payout,
     * assigned by BitPay for a given account during the onboarding process.
     *
     * @param accountId the account id
     */
    @JsonProperty("accountId")
    public void setAccountId(final String accountId) {
        this.accountId = accountId;
    }

    /**
     * Gets message.
     * In case of error, this field will contain a description message. Set to `null` if the request is successful.
     *
     * @return the message
     */
    @JsonIgnore
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets message.
     * In case of error, this field will contain a description message. Set to `null` if the request is successful.
     *
     * @param message the message
     */
    @JsonProperty("message")
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Gets email.
     * Email address of an active recipient.
     * Note: In the future, BitPay may allow recipients to update the email address tied to their personal account.
     * BitPay encourages the use of `recipientId` or `shopperId` when programatically creating payouts requests.
     *
     * @return the email
     */
    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets email.
     * Email address of an active recipient.
     * Note: In the future, BitPay may allow recipients to update the email address tied to their personal account.
     * BitPay encourages the use of `recipientId` or `shopperId` when programatically creating payouts requests.
     *
     * @param email the email
     */
    @JsonProperty("email")
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets label.
     * For merchant use, pass through -
     * can be the customer name or unique merchant reference assigned by the merchant to the recipient.
     *
     * @return the label
     */
    @JsonIgnore
    public String getLabel() {
        return this.label;
    }

    /**
     * Sets label.
     * For merchant use, pass through -
     * can be the customer name or unique merchant reference assigned by the merchant to the recipient.
     *
     * @param label the label
     */
    @JsonProperty("label")
    public void setLabel(final String label) {
        this.label = label;
    }

    /**
     * Gets payout request status. The possible values are:
     * <ul>
     *     <li>new - initial status when the payout batch is created</li>
     *     <li>funded - if there are enough funds available on the merchant account,
     *      the payout batches are set to funded. This happens at the daily cutoff time for payout processing,
     *      e.g. 2pm and 9pm UTC
     *     </li>
     *     <li>processing - the payout batches switch to this status whenever the corresponding cryptocurrency
     *      transactions are broadcasted by BitPay
     *     </li>
     *     <li>complete - the payout batches are marked as complete when the cryptocurrency transaction has reached
     *      the typical target confirmation for the corresponding asset. For instance,
     *      6 confirmations for a bitcoin transaction.
     *     </li>
     *     <li>cancelled - when the merchant cancels a payout batch (only possible for requests in the status new</li>
     * </ul>
     *
     * @return the status
     */
    @JsonIgnore
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets payout request status. The possible values are:
     * <ul>
     *     <li>new - initial status when the payout batch is created</li>
     *     <li>funded - if there are enough funds available on the merchant account,
     *      the payout batches are set to funded. This happens at the daily cutoff time for payout processing,
     *      e.g. 2pm and 9pm UTC
     *     </li>
     *     <li>processing - the payout batches switch to this status whenever the corresponding cryptocurrency
     *      transactions are broadcasted by BitPay
     *     </li>
     *     <li>complete - the payout batches are marked as complete when the cryptocurrency transaction has reached
     *      the typical target confirmation for the corresponding asset. For instance,
     *      6 confirmations for a bitcoin transaction.
     *     </li>
     *     <li>cancelled - when the merchant cancels a payout batch (only possible for requests in the status new</li>
     * </ul>
     *
     * @param status the status
     */
    @JsonProperty("status")
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Gets date and time (UTC) when BitPay received the batch. ISO-8601 format `yyyy-mm-ddThh:mm:ssZ`.
     *
     * @return the request date
     */
    @JsonIgnore
    public ZonedDateTime getRequestDate() {
        return this.requestDate;
    }

    /**
     * Sets date and time (UTC) when BitPay received the batch. ISO-8601 format `yyyy-mm-ddThh:mm:ssZ`.
     *
     * @param requestDate the request date
     */
    @JsonProperty("requestDate")
    public void setRequestDate(final ZonedDateTime requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * Gets date and time (UTC) when BitPay executed the payout. ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @return the date executed
     */
    @JsonIgnore
    public ZonedDateTime getDateExecuted() {
        return this.dateExecuted;
    }

    /**
     * Sets date and time (UTC) when BitPay executed the payout. ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @param dateExecuted the date executed
     */
    @JsonProperty("dateExecuted")
    public void setDateExecuted(final ZonedDateTime dateExecuted) {
        this.dateExecuted = dateExecuted;
    }

    /**
     * This field will be returned in case of error and contain an error code.
     *
     * @return code
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Integer getCode() {
        return this.code;
    }

    /**
     * Sets error code.
     *
     * @param code code
     */
    public void setCode(final Integer code) {
        this.code = code;
    }

    @JsonProperty("ignoreEmails")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Boolean isIgnoreEmails() {
        return this.ignoreEmails;
    }

    public void setIgnoreEmails(final Boolean ignoreEmails) {
        this.ignoreEmails = ignoreEmails;
    }
}
