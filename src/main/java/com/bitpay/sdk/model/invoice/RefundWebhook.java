/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.invoice;

import com.bitpay.sdk.model.ModelConfiguration;
import com.bitpay.sdk.util.serializer.Iso8601ToZonedDateTimeDeserializer;
import com.bitpay.sdk.util.serializer.ZonedDateTimeToIso8601Serializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.ZonedDateTime;

/**
 * The type Refund webhook.
 *
 * @see <a href="https://bitpay.readme.io/reference/refunds-1">Webhooks refunds</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundWebhook {

    protected String id;
    protected String invoice = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String supportRequest = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String status = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected Double amount;
    protected String currency = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected ZonedDateTime lastRefundNotification;
    protected Double refundFee;
    protected Boolean immediate;
    protected Boolean buyerPaysRefundFee;
    protected ZonedDateTime requestDate;
    protected String reference;
    protected String guid;
    protected String refundAddress;
    protected String type;
    protected String txid;
    protected String transactionCurrency;
    protected Double transactionAmount;
    protected Double transactionRefundFee;

    /**
     * Instantiates a new Refund webhook.
     */
    public RefundWebhook() {
    }

    /**
     * Gets BitPay refund ID.
     *
     * @return the id
     */
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getId() {
        return this.id;
    }

    /**
     * Sets BitPay refund ID.
     *
     * @param id the id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets BitPay invoice ID associated to the refund.
     *
     * @return the invoice
     */
    @JsonProperty("invoice")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getInvoice() {
        return this.invoice;
    }

    /**
     * Sets BitPay invoice ID associated to the refund.
     *
     * @param invoice the invoice
     */
    @JsonProperty("invoice")
    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    /**
     * Gets BitPay support request ID associated to the refund.
     *
     * @return the support request
     */
    @JsonProperty("supportRequest")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getSupportRequest() {
        return this.supportRequest;
    }

    /**
     * Sets BitPay support request ID associated to the refund.
     *
     * @param supportRequest the support request
     */
    @JsonProperty("supportRequest")
    public void setSupportRequest(String supportRequest) {
        this.supportRequest = supportRequest;
    }

    /**
     * Gets refund lifecycle status of the request (refer to status field in refunds resource).
     *
     * @return the status
     * @see <a href="https://bitpay.readme.io/reference/refunds-resource">Refunds resource</a>
     */
    @JsonProperty("status")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets refund lifecycle status of the request (refer to status field in refunds resource).
     *
     * @param status the status
     * @see <a href="https://bitpay.readme.io/reference/refunds-resource">Refunds resource</a>
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets amount to be refunded in the currency of the associated invoice.
     *
     * @return the amount
     */
    @JsonProperty("amount")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Double getAmount() {
        return this.amount;
    }

    /**
     * Sets amount to be refunded in the currency of the associated invoice.
     *
     * @param amount the amount
     */
    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * Gets currency used for the refund, the same as the currency used to create the associated invoice.
     *
     * @return the currency
     */
    @JsonProperty("currency")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCurrency() {
        return this.currency;
    }

    /**
     * Sets currency used for the refund, the same as the currency used to create the associated invoice.
     *
     * @param currency the currency
     */
    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets timestamp of last notification sent to customer about refund.
     *
     * @return the last refund notification
     */
    @JsonProperty("lastRefundNotification")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonSerialize(using = ZonedDateTimeToIso8601Serializer.class)
    public ZonedDateTime getLastRefundNotification() {
        return this.lastRefundNotification;
    }

    /**
     * Sets timestamp of last notification sent to customer about refund.
     *
     * @param lastRefundNotification the last refund notification
     */
    @JsonProperty("lastRefundNotification")
    @JsonDeserialize(using = Iso8601ToZonedDateTimeDeserializer.class)
    public void setLastRefundNotification(ZonedDateTime lastRefundNotification) {
        this.lastRefundNotification = lastRefundNotification;
    }

    /**
     * Gets the amount of refund fee expressed in terms of pricing currency.
     *
     * @return the refund fee
     */
    @JsonProperty("refundFee")
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
    public void setRefundFee(Double refundFee) {
        this.refundFee = refundFee;
    }

    /**
     * Gets immediate. Whether funds should be removed from merchant ledger immediately on submission or
     * at time of processing.
     *
     * @return the immediate
     */
    @JsonProperty("immediate")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Boolean getImmediate() {
        return this.immediate;
    }

    /**
     * Sets immediate. Whether funds should be removed from merchant ledger immediately on submission or
     * at time of processing
     *
     * @param immediate the immediate
     */
    @JsonProperty("immediate")
    public void setImmediate(Boolean immediate) {
        this.immediate = immediate;
    }

    /**
     * Gets buyer pays refund fee. Whether the buyer should pay the refund fee (default is the merchant).
     *
     * @return the buyer pays refund fee
     */
    @JsonProperty("buyerPaysRefundFee")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Boolean getBuyerPaysRefundFee() {
        return this.buyerPaysRefundFee;
    }

    /**
     * Sets buyer pays refund fee. Whether the buyer should pay the refund fee (default is the merchant).
     *
     * @param buyerPaysRefundFee the buyer pays refund fee
     */
    @JsonProperty("buyerPaysRefundFee")
    public void setBuyerPaysRefundFee(Boolean buyerPaysRefundFee) {
        this.buyerPaysRefundFee = buyerPaysRefundFee;
    }

    /**
     * Gets request date. Timestamp the refund request was created.
     *
     * @return the request date
     */
    @JsonProperty("requestDate")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonSerialize(using = ZonedDateTimeToIso8601Serializer.class)
    public ZonedDateTime getRequestDate() {
        return this.requestDate;
    }

    /**
     * Sets request date. Timestamp the refund request was created.
     *
     * @param requestDate the request date
     */
    @JsonProperty("requestDate")
    @JsonDeserialize(using = Iso8601ToZonedDateTimeDeserializer.class)
    public void setRequestDate(ZonedDateTime requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * Gets reference.
     *
     * @return String
     */
    @JsonProperty("reference")
    public String getReference() {
        return this.reference;
    }

    /**
     * Sets reference.
     *
     * @param reference string
     */
    @JsonProperty("reference")
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Gets guid.
     *
     * @return string
     */
    @JsonProperty("guid")
    public String getGuid() {
        return this.guid;
    }

    /**
     * Sets guid.
     *
     * @param guid string
     */
    @JsonProperty("guid")
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * Gets refund address.
     *
     * @return string
     */
    @JsonProperty("refundAddress")
    public String getRefundAddress() {
        return this.refundAddress;
    }

    /**
     * Sets refund address.
     *
     * @param refundAddress string
     */
    @JsonProperty("refundAddress")
    public void setRefundAddress(String refundAddress) {
        this.refundAddress = refundAddress;
    }

    /**
     * Gets type.
     *
     * @return string
     */
    @JsonProperty("type")
    public String getType() {
        return this.type;
    }

    /**
     * Sets type.
     *
     * @param type string
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets txid.
     *
     * @return string
     */
    @JsonProperty("txid")
    public String getTxid() {
        return this.txid;
    }

    /**
     * Sets txid.
     *
     * @param txid string
     */
    @JsonProperty("txid")
    public void setTxid(String txid) {
        this.txid = txid;
    }

    /**
     * Gets transaction currency.
     *
     * @return string
     */
    @JsonProperty("transactionCurrency")
    public String getTransactionCurrency() {
        return this.transactionCurrency;
    }

    /**
     * Sets transaction currency.
     *
     * @param transactionCurrency string
     */
    @JsonProperty("transactionCurrency")
    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    /**
     * Gets transaction amount.
     *
     * @return double
     */
    @JsonProperty("transactionAmount")
    public Double getTransactionAmount() {
        return this.transactionAmount;
    }

    /**
     * Sets transaction amount.
     *
     * @param transactionAmount double
     */
    @JsonProperty("transactionAmount")
    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * Gets transaction refund fee.
     *
     * @return double
     */
    @JsonProperty("transactionRefundFee")
    public Double getTransactionRefundFee() {
        return this.transactionRefundFee;
    }

    /**
     * Sets transaction refund fee.
     *
     * @param transactionRefundFee double
     */
    @JsonProperty("transactionRefundFee")
    public void setTransactionRefundFee(Double transactionRefundFee) {
        this.transactionRefundFee = transactionRefundFee;
    }
}
