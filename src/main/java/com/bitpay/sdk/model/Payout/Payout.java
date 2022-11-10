package com.bitpay.sdk.model.Payout;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.Currency;
import com.bitpay.sdk.util.DateDeserializer;
import com.bitpay.sdk.util.DateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Collections;
import java.util.List;

/**
 * The type Payout.
 * This resource allows merchants to submit cryptocurrency payouts to active bitpay recipients.
 * The typical use cases for this resource would be a company
 * who wants to offer cryptocurrency withdrawals to their customers,
 * like marketplaces or affiliate networks,
 * or for payroll purposes by creating multiple payouts at a time.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-payouts">REST API Payouts</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payout {

    private String _token = "";

    private Double _amount = 0.0;
    private String _currency = "";
    private Long _effectiveDate;

    private String _reference = "";
    private String _notificationEmail = "";
    private String _notificationURL = "";
    private String _redirectUrl = "";
    private String _ledgerCurrency = "";

    private String _id;
    private String _shopperId;
    private String _recipientId;
    private PayoutInstruction _exchangeRates;
    private String _account;
    private String _email;
    private String _label;
    private String _supportPhone;
    private String _status;
    private String _message;
    private Double _percentFee;
    private Double _fee;
    private Double _depositTotal;
    private Double _rate;
    private Double _btc;
    private Long _requestDate;
    private Long _dateExecuted;
    private List<PayoutInstructionTransaction> _transactions = Collections.emptyList();

    /**
     * Constructor, create an empty Payout object.
     */
    public Payout() {
        _amount = 0.0;
        _currency = "USD";
        _notificationEmail = "";
        _notificationURL = "";
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
    public Payout(Double amount, String currency, String ledgerCurrency) {
        this._amount = amount;
        this._currency = currency;
        this._ledgerCurrency = ledgerCurrency;
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
        return _token;
    }

    /**
     * Sets resource token.
     * This token is actually derived from the API token -
     * used to submit the payout and is tied to the specific payout resource id created..
     *
     * @param token the token
     */
    @JsonProperty("token")
    public void setToken(String token) {
        this._token = token;
    }

    // Required fields
    //

    /**
     * Gets amount of cryptocurrency sent to the requested address.
     *
     * @return the amount
     */
    @JsonProperty("amount")
    public Double getAmount() {
        return _amount;
    }

    /**
     * Sets amount of cryptocurrency sent to the requested address.
     *
     * @param amount the amount
     */
    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this._amount = amount;
    }

    /**
     * Gets currency code set for the batch amount (ISO 4217 3-character currency code).
     *
     * @return the currency
     */
    @JsonProperty("currency")
    public String getCurrency() {
        return _currency;
    }

    /**
     * Sets currency code set for the batch amount (ISO 4217 3-character currency code).
     *
     * @param currency the currency
     * @throws BitPayException the bit pay exception
     */
    @JsonProperty("currency")
    public void setCurrency(String currency) throws BitPayException {
        if (!Currency.isValid(currency)) {
            throw new BitPayException(null, "Error: currency code must be a type of Model.Currency");
        }
        this._currency = currency;
    }

    /**
     * Gets Ledger currency code (ISO 4217 3-character currency code),
     * it indicates on which ledger the payout request will be recorded. If not provided in the request,
     * this parameter will be set by default to the active ledger currency on your account,
     * e.g. your settlement currency.
     *
     * @return the ledger currency
     * @see <a href="https://bitpay.com/api/#rest-api-resources-payouts">Supported ledger currency codes</a>
     */
    @JsonProperty("ledgerCurrency")
    public String getLedgerCurrency() {
        return _ledgerCurrency;
    }

    /**
     * Sets Ledger currency code (ISO 4217 3-character currency code),
     * it indicates on which ledger the payout request will be recorded. If not provided in the request,
     * this parameter will be set by default to the active ledger currency on your account,
     * e.g. your settlement currency.
     *
     * @param ledgerCurrency the ledger currency
     * @throws BitPayException the bit pay exception
     */
    @JsonProperty("ledgerCurrency")
    public void setLedgerCurrency(String ledgerCurrency) throws BitPayException {
        if (!Currency.isValid(ledgerCurrency)) {
            throw new BitPayException(null, "Error: currency code must be a type of Model.Currency");
        }
        this._ledgerCurrency = ledgerCurrency;
    }

    /**
     * Gets effective date and time (UTC) for the payout.
     * ISO-8601 format yyyy-mm-ddThh:mm:ssZ. If not provided, defaults to date and time of creation.
     *
     * @return the effective date
     */
    @JsonProperty("effectiveDate")
    @JsonSerialize(using = DateSerializer.class)
    public Long getEffectiveDate() {
        return _effectiveDate;
    }

    /**
     * Sets effective date and time (UTC) for the payout.
     * ISO-8601 format yyyy-mm-ddThh:mm:ssZ. If not provided, defaults to date and time of creation.
     *
     * @param effectiveDate the effective date
     */
    @JsonProperty("effectiveDate")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setEffectiveDate(Long effectiveDate) {
        this._effectiveDate = effectiveDate;
    }

    /**
     * Gets transactions. Contains the cryptocurrency transaction details for the executed payout request.
     *
     * @return the transactions
     */
    @JsonProperty("transactions")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<PayoutInstructionTransaction> getTransactions() {
        return _transactions;
    }

    /**
     * Sets transactions. Contains the cryptocurrency transaction details for the executed payout request.
     *
     * @param transactions the transactions
     */
    @JsonProperty("transactions")
    public void setTransactions(List<PayoutInstructionTransaction> transactions) {
        this._transactions = transactions;
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
    public String getReference() {
        return _reference;
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
    public void setReference(String reference) {
        this._reference = reference;
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
        return _notificationEmail;
    }

    /**
     * Sets notification email.
     * Merchant email address for notification of payout status change.
     *
     * @param notificationEmail the notification email
     */
    @JsonProperty("notificationEmail")
    public void setNotificationEmail(String notificationEmail) {
        this._notificationEmail = notificationEmail;
    }

    /**
     * Gets notification url.
     * URL to which BitPay sends webhook notifications. HTTPS is mandatory
     *
     * @return the notification url
     */
    @JsonProperty("notificationURL")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getNotificationURL() {
        return _notificationURL;
    }

    /**
     * Sets notification url.
     * URL to which BitPay sends webhook notifications. HTTPS is mandatory
     *
     * @param notificationURL the notification url
     */
    @JsonProperty("notificationURL")
    public void setNotificationURL(String notificationURL) {
        this._notificationURL = notificationURL;
    }

    /**
     * Gets redirect url.
     * The shopper will be redirected to this URL when clicking on the Return button after a successful payment or
     * when clicking on the Close button if a separate closeURL is not specified.
     * Be sure to include "http://" or "https://" in the url.
     *
     * @return the redirect url
     */
    @JsonProperty("redirectUrl")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getRedirectUrl() {
        return _redirectUrl;
    }

    /**
     * Sets redirect url.
     * The shopper will be redirected to this URL when clicking on the Return button after a successful payment or
     * when clicking on the Close button if a separate closeURL is not specified.
     * Be sure to include "http://" or "https://" in the url.
     *
     * @param redirectUrl the redirect url
     */
    @JsonProperty("redirectUrl")
    public void setRedirectUrl(String redirectUrl) {
        this._redirectUrl = redirectUrl;
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
        return _id;
    }

    /**
     * Sets Payout request id..
     *
     * @param id the id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this._id = id;
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
    public String getShopperId() {
        return _shopperId;
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
    public void setShopperId(String shopperId) {
        this._shopperId = shopperId;
    }

    /**
     * Gets BitPay recipient id.
     * assigned by BitPay for a given recipient email during the onboarding process (see Recipients resource).
     *
     * @return the recipient id
     */
    @JsonProperty("recipientId")
    public String getRecipientId() {
        return _recipientId;
    }

    /**
     * Sets BitPay recipient id.
     * assigned by BitPay for a given recipient email during the onboarding process (see Recipients resource).
     *
     * @param recipientId the recipient id
     */
    @JsonProperty("recipientId")
    public void setRecipientId(String recipientId) {
        this._recipientId = recipientId;
    }

    /**
     * Gets exchange rates keyed by source and target currencies.
     *
     * @return the exchange rates
     */
    @JsonProperty("exchangeRates")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public PayoutInstruction getExchangeRates() {
        return _exchangeRates;
    }

    /**
     * Sets exchange rates keyed by source and target currencies.
     *
     * @param exchangeRates the exchange rates
     */
    @JsonProperty("exchangeRates")
    public void setExchangeRates(PayoutInstruction exchangeRates) {
        this._exchangeRates = exchangeRates;
    }

    /**
     * Gets account.
     *
     * @return the account
     */
    @JsonIgnore
    public String getAccount() {
        return _account;
    }

    /**
     * Sets account.
     *
     * @param account the account
     */
    @JsonProperty("account")
    public void setAccount(String account) {
        this._account = account;
    }

    /**
     * Gets message.
     * In case of error, this field will contain a description message. Set to `null` if the request is successful.
     *
     * @return the message
     */
    @JsonIgnore
    public String getMessage() {
        return _message;
    }

    /**
     * Sets message.
     * In case of error, this field will contain a description message. Set to `null` if the request is successful.
     *
     * @param message the message
     */
    @JsonProperty("message")
    public void setMessage(String message) {
        this._message = message;
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
    public String getEmail() {
        return _email;
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
    public void setEmail(String email) {
        this._email = email;
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
        return _label;
    }

    /**
     * Sets label.
     * For merchant use, pass through -
     * can be the customer name or unique merchant reference assigned by the merchant to the recipient.
     *
     * @param label the label
     */
    @JsonProperty("label")
    public void setLabel(String label) {
        this._label = label;
    }

    /**
     * Gets support phone.
     *
     * @return the support phone
     */
    @JsonIgnore
    public String getSupportPhone() {
        return _supportPhone;
    }

    /**
     * Sets support phone.
     *
     * @param supportPhone the support phone
     */
    @JsonProperty("supportPhone")
    public void setSupportPhone(String supportPhone) {
        this._supportPhone = supportPhone;
    }

    /**
     * Gets payout request status, the possible values are:
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
        return _status;
    }

    /**
     * Sets payout request status, the possible values are:
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
    public void setStatus(String status) {
        this._status = status;
    }

    /**
     * Gets percent fee.
     *
     * @return the percent fee
     */
    @JsonIgnore
    public Double getPercentFee() {
        return _percentFee;
    }

    /**
     * Sets percent fee.
     *
     * @param percentFee the percent fee
     */
    @JsonProperty("percentFee")
    public void setPercentFee(Double percentFee) {
        this._percentFee = percentFee;
    }

    /**
     * Gets fee.
     *
     * @return the fee
     */
    @JsonIgnore
    public Double getFee() {
        return _fee;
    }

    /**
     * Sets fee.
     *
     * @param fee the fee
     */
    @JsonProperty("fee")
    public void setFee(Double fee) {
        this._fee = fee;
    }

    /**
     * Gets deposit total.
     *
     * @return the deposit total
     */
    @JsonIgnore
    public Double getDepositTotal() {
        return _depositTotal;
    }

    /**
     * Sets deposit total.
     *
     * @param depositTotal the deposit total
     */
    @JsonProperty("depositTotal")
    public void setDepositTotal(Double depositTotal) {
        this._depositTotal = depositTotal;
    }

    /**
     * Gets btc.
     *
     * @return the btc
     */
    @JsonIgnore
    public Double getBtc() {
        return _btc;
    }

    /**
     * Sets btc.
     *
     * @param btc the btc
     */
    @JsonProperty("btc")
    public void setBtc(Double btc) {
        this._btc = btc;
    }

    /**
     * Gets rate.
     *
     * @return the rate
     */
    @JsonIgnore
    public Double getRate() {
        return _rate;
    }

    /**
     * Sets rate.
     *
     * @param rate the rate
     */
    @JsonProperty("rate")
    public void setRate(Double rate) {
        this._rate = rate;
    }

    /**
     * Gets date and time (UTC) when BitPay received the batch. ISO-8601 format `yyyy-mm-ddThh:mm:ssZ`.
     *
     * @return the request date
     */
    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public long getRequestDate() {
        return _requestDate;
    }

    /**
     * Sets date and time (UTC) when BitPay received the batch. ISO-8601 format `yyyy-mm-ddThh:mm:ssZ`.
     *
     * @param requestDate the request date
     */
    @JsonProperty("requestDate")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setRequestDate(long requestDate) {
        this._requestDate = requestDate;
    }

    /**
     * Gets date and time (UTC) when BitPay executed the payout. ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @return the date executed
     */
    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public long getDateExecuted() {
        return _dateExecuted;
    }

    /**
     * Sets date and time (UTC) when BitPay executed the payout. ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @param dateExecuted the date executed
     */
    @JsonProperty("dateExecuted")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setDateExecuted(long dateExecuted) {
        this._dateExecuted = dateExecuted;
    }

}
