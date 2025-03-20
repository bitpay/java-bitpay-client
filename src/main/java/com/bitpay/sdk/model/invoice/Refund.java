/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.invoice;

import com.bitpay.sdk.model.ModelConfiguration;
import com.bitpay.sdk.util.serializer.Iso8601ToZonedDateTimeDeserializer;
import com.bitpay.sdk.util.serializer.ZonedDateTimeToIso8601Serializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Fully paid invoices can be refunded via the merchant's authorization to issue a refund,
 * while underpaid and overpaid invoices are automatically executed by BitPay to issue the underpayment
 * or overpayment amount to the customer.
 *
 * @see <a href="https://bitpay.readme.io/reference/refunds">REST API Refunds</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Refund {

    protected String guid;
    protected Double amount;
    protected String currency = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String invoice = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected Boolean preview;
    protected Boolean immediate;
    protected Boolean buyerPaysRefundFee;
    protected String reference = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected Double refundFee;
    protected ZonedDateTime requestDate;
    protected ZonedDateTime lastRefundNotification;
    protected String notificationUrl = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String refundAddress = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String supportRequest = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String txid = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String type = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected BigDecimal transactionAmount;
    protected BigDecimal transactionRefundFee;
    protected String transactionCurrency = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String id;
    protected String status = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String token;


    /**
     * Instantiates a new Refund.
     */
    public Refund() {
    }

    // Request fields
    //

    /**
     * Gets a variable provided by the merchant and designed to be used by the merchant
     * to correlate the refund with a refund ID in their system.
     *
     * @return the guid
     */
    @JsonProperty("guid")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getGuid() {
        return this.guid;
    }

    /**
     * Sets a variable provided by the merchant and designed to be used by the merchant
     * to correlate the refund with a refund ID in their system.
     *
     * @param guid the guid
     */
    @JsonProperty("guid")
    public void setGuid(final String guid) {
        this.guid = guid;
    }

    /**
     * Gets the amount to be refunded, denominated in the invoice original currency - partial refunds are supported.
     *
     * @return the amount
     */
    @JsonProperty("amount")
    public Double getAmount() {
        return this.amount;
    }

    /**
     * Sets the amount to be refunded, denominated in the invoice original currency - partial refunds are supported.
     *
     * @param amount the amount
     */
    @JsonProperty("amount")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    /**
     * Gets used for the refund, the same as the currency used to create the invoice..
     *
     * @return the currency
     */
    @JsonProperty("currency")
    public String getCurrency() {
        return this.currency;
    }

    /**
     * Sets used for the refund, the same as the currency used to create the invoice..
     *
     * @param currency the currency
     */
    @JsonProperty("currency")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    /**
     * Gets the ID of the invoice being refunded..
     *
     * @return the invoice
     */
    @JsonProperty("invoice")
    public String getInvoice() {
        return this.invoice;
    }

    /**
     * Sets the ID of the invoice being refunded..
     *
     * @param invoice the invoice
     */
    @JsonProperty("invoice")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setInvoice(final String invoice) {
        this.invoice = invoice;
    }

    /**
     * Gets preview. Whether to create the refund request as a preview
     * (which will not be acted on until status is updated) - parameter defaults to false if not passed.
     *
     * @return the preview
     */
    @JsonProperty("preview")
    public Boolean getPreview() {
        return this.preview;
    }

    /**
     * Sets preview.
     *
     * @param preview the preview
     */
    @JsonProperty("preview")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setPreview(final Boolean preview) {
        this.preview = preview;
    }

    /**
     * Gets immediate. Whether funds should be removed from merchant ledger immediately on submission or at
     * time of processing - parameter defaults to false if not passed.
     *
     * @return the immediate
     */
    @JsonProperty("immediate")
    public Boolean getImmediate() {
        return this.immediate;
    }

    /**
     * Sets immediate.
     *
     * @param immediate the immediate
     */
    @JsonProperty("immediate")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setImmediate(final Boolean immediate) {
        this.immediate = immediate;
    }

    /**
     * Gets buyer pays refund fee. Whether the buyer should pay the refund fee rather than the merchant -
     * parameter defaults to false if not passed.
     *
     * @return the buyer pays refund fee
     */
    @JsonProperty("buyerPaysRefundFee")
    public Boolean getBuyerPaysRefundFee() {
        return this.buyerPaysRefundFee;
    }

    /**
     * Sets buyer pays refund fee.
     *
     * @param buyerPaysRefundFee the buyer pays refund fee
     */
    @JsonProperty("buyerPaysRefundFee")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setBuyerPaysRefundFee(final Boolean buyerPaysRefundFee) {
        this.buyerPaysRefundFee = buyerPaysRefundFee;
    }

    /**
     * Gets reference. Present only if specified in the request to create the refund.
     * This is your reference label for this refund.
     * It will be passed-through on each response for you to identify the refund in your system.
     * Maximum string length is 100 characters.
     *
     * @return the reference
     */
    @JsonProperty("reference")
    public String getReference() {
        return this.reference;
    }

    /**
     * Sets reference. This is your reference label for this refund.
     * It will be passed-through on each response for you to identify the refund in your system.
     * Maximum string length is 100 characters.
     *
     * @param reference the reference
     */
    @JsonProperty("reference")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setReference(final String reference) {
        this.reference = reference;
    }

    // Response fields
    //

    /**
     * Gets the ID of the refund.
     *
     * @return the id
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getId() {
        return this.id;
    }

    /**
     * Sets the ID of the refund.
     *
     * @param id the id
     */
    @JsonProperty("id")
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Gets the date the refund was requested.
     *
     * @return the request date
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonSerialize(using = ZonedDateTimeToIso8601Serializer.class)
    public ZonedDateTime getRequestDate() {
        return this.requestDate;
    }

    /**
     * Sets the date the refund was requested.
     *
     * @param requestDate the request date
     */
    @JsonProperty("requestDate")
    @JsonDeserialize(using = Iso8601ToZonedDateTimeDeserializer.class)
    public void setRequestDate(final ZonedDateTime requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * Gets the refund lifecycle status of the request.
     * {@link com.bitpay.sdk.model.invoice.InvoiceStatus}
     *
     * @return the status
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets the refund lifecycle status of the request.
     * Set to `created` in order to confirm the refund request and initiate the flow to send it to shopper.
     * {@link com.bitpay.sdk.model.invoice.InvoiceStatus}
     *
     * @param status the status
     */
    @JsonProperty("status")
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Gets amount to be refunded in terms of the transaction currency.
     *
     * @return the transaction amount of the Refund
     * @see Refund
     */
    @JsonProperty("transactionAmount")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public BigDecimal getTransactionAmount() {
        return this.transactionAmount;
    }

    /**
     * Sets amount to be refunded in terms of the transaction currency.
     *
     * @param transactionAmount Amount to be refunded in terms of the transaction currency
     * @see Refund
     */
    @JsonProperty("transactionAmount")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setTransactionAmount(final BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * Gets the refund fee expressed in terms of transaction currency.
     *
     * @return the transaction refund fee of the Refund
     * @see Refund
     */
    @JsonProperty("transactionRefundFee")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public BigDecimal getTransactionRefundFee() {
        return this.transactionRefundFee;
    }

    /**
     * Sets the refund fee expressed in terms of transaction currency.
     *
     * @param transactionRefundFee The refund fee expressed in terms of transaction currency
     * @see Refund
     */
    @JsonProperty("transactionRefundFee")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setTransactionRefundFee(final BigDecimal transactionRefundFee) {
        this.transactionRefundFee = transactionRefundFee;
    }

    /**
     * Gets the currency used for the invoice transaction.
     *
     * @return the transaction currency of the Refund
     * @see Refund
     */
    @JsonProperty("transactionCurrency")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getTransactionCurrency() {
        return this.transactionCurrency;
    }

    /**
     * Sets the currency used for the invoice transaction.
     *
     * @param transactionCurrency The currency used for the invoice transaction
     * @see Refund
     */
    @JsonProperty("transactionCurrency")
    public void setTransactionCurrency(final String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    /**
     * Gets the last time notification of buyer was attempted.
     *
     * @return the last refund notification
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonSerialize(using = ZonedDateTimeToIso8601Serializer.class)
    public ZonedDateTime getLastRefundNotification() {
        return this.lastRefundNotification;
    }

    /**
     * Sets the last time notification of buyer was attempted.
     *
     * @param lastRefundNotification the last refund notification
     */
    @JsonProperty("lastRefundNotification")
    @JsonDeserialize(using = Iso8601ToZonedDateTimeDeserializer.class)
    public void setLastRefundNotification(final ZonedDateTime lastRefundNotification) {
        this.lastRefundNotification = lastRefundNotification;
    }

    /**
     * Gets the amount of refund fee expressed in terms of pricing currency.
     *
     * @return the refund fee
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Double getRefundFee() {
        return this.refundFee;
    }

    /**
     * Sets the amount of refund fee expressed in terms of pricing currency.
     *
     * @param refundFee the refund fee
     */
    @JsonProperty("refundFee")
    public void setRefundFee(final Double refundFee) {
        this.refundFee = refundFee;
    }

    /**
     * Gets URL to which BitPay sends webhook notifications. HTTPS is mandatory.
     *
     * @return notification url
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getNotificationUrl() {
        return this.notificationUrl;
    }

    /**
     * Sets URL to which BitPay sends webhook notifications. HTTPS is mandatory.
     *
     * @param notificationUrl Notification url.
     */
    @JsonProperty("notificationURL")
    public void setNotificationUrl(final String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

    /**
     * Gets the wallet address that the refund will return the funds to, added by the customer.
     *
     * @return refund address
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getRefundAddress() {
        return this.refundAddress;
    }

    /**
     * Sets the wallet address that the refund will return the funds to, added by the customer.
     *
     * @param refundAddress refund address
     */
    @JsonProperty("refundAddress")
    public void setRefundAddress(final String refundAddress) {
        this.refundAddress = refundAddress;
    }

    /**
     * Gets the ID of the associated support request for the refund.
     *
     * @return support request
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getSupportRequest() {
        return this.supportRequest;
    }

    /**
     * Sets the ID of the associated support request for the refund.
     *
     * @param supportRequest support request
     */
    @JsonProperty("supportRequest")
    public void setSupportRequest(final String supportRequest) {
        this.supportRequest = supportRequest;
    }

    /**
     *
     * Gets the transaction ID of the refund once executed.
     *
     * @return transaction id
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getTxid() {
        return this.txid;
    }

    /**
     * Sets the transaction ID of the refund once executed.
     *
     * @param txid transaction id
     */
    @JsonProperty("txid")
    public void setTxid(final String txid) {
        this.txid = txid;
    }

    /**
     * <p>Gets the type of refund.</p>
     * <ul>
     *    <li>full (current rate): A full refund of the amount paid at the current rate.</li>
     *    <li>full (fixed rate): A full refund of the amount paid at the fixed rate.
     *    Note: deprecated refund implementation only.</li>
     *    <li>partial: Part of the invoice is being refunded, rather than the full invoie amount.</li>
     *    <li>underpayment: The payment was underpaid, a refund in the amount paid will be executed.</li>
     *    <li>overpayment: The payment was overpaid, a refund in the amount that was overpaid from the invoice price
     *    will be executed.</li>
     *    <li>declined: The payment was declined, a refund in the full amount paid will be excuted.</li>
     * </ul>
     *
     * @return type
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getType() {
        return this.type;
    }

    /**
     * <p>Sets the type of refund.</p>
     * <ul>
     *    <li>full (current rate): A full refund of the amount paid at the current rate.</li>
     *    <li>full (fixed rate): A full refund of the amount paid at the fixed rate.
     *    Note: deprecated refund implementation only.</li>
     *    <li>partial: Part of the invoice is being refunded, rather than the full invoie amount.</li>
     *    <li>underpayment: The payment was underpaid, a refund in the amount paid will be executed.</li>
     *    <li>overpayment: The payment was overpaid, a refund in the amount that was overpaid from the invoice price
     *    will be executed.</li>
     *    <li>declined: The payment was declined, a refund in the full amount paid will be excuted.</li>
     * </ul>
     *
     * @param type type
     */
    @JsonProperty("type")
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Gets refund resource token.
     * This token is derived from the API token initially used to create the refund and is tied
     * to the specific resource id created.
     *
     * @return the token
     */
    @JsonProperty("token")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getToken() {
        return this.token;
    }

    /**
     * Sets refund resource token.
     * This token is derived from the API token initially used to create the refund and is tied
     * to the specific resource id created.
     *
     * @param token the token
     */
    @JsonProperty("token")
    public void setToken(final String token) {
        this.token = token;
    }
}
