/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.ledger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;

/**
 * The type Ledger entry.
 *
 * @see <a href="https://bitpay.readme.io/reference/ledgers">REST API Ledgers</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LedgerEntry {

    private String type;
    private String amount;
    private Integer code;
    private String description;
    private ZonedDateTime timestamp;
    private String txType;
    private Integer scale;
    private String invoiceId;
    private Buyer buyer;
    private Double invoiceAmount;
    private String invoiceCurrency;
    private String transactionCurrency;
    private String id;
    private String supportRequest;
    private String currency;

    /**
     * Instantiates a new Ledger entry.
     */
    public LedgerEntry() {
    }

    /**
     * Gets type of Ledger entry name.
     *
     * @return the type
     *
     * @see <a href="https://bitpay.readme.io/reference/ledger-entry-codes">Ledger entry codes</a>
     */
    @JsonIgnore
    public String getType() {
        return this.type;
    }

    /**
     * Sets type of Ledger entry name.
     *
     * @param type the type
     *
     * @see <a href="https://bitpay.readme.io/reference/ledger-entry-codes">Ledger entry codes</a>
     */
    @JsonProperty("type")
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Gets amount.
     * Ledger entry amount, relative to the scale.
     * The decimal amount can be obtained by dividing the amount field by the scale parameter.
     *
     * @return the amount
     */
    @JsonIgnore
    public String getAmount() {
        return this.amount;
    }

    /**
     * Sets amount.
     * Ledger entry amount, relative to the scale.
     * The decimal amount can be obtained by dividing the amount field by the scale parameter.
     *
     * @param amount the amount
     *
     *
     */
    @JsonProperty("amount")
    public void setAmount(final String amount) {
        this.amount = amount;
    }

    /**
     * Gets Ledger entry code.
     * Contains the Ledger entry code. See the list of Ledger Entry Codes.
     *
     * @return the code
     *
     * @see <a href="https://bitpay.readme.io/reference/ledger-entry-codes">Ledger entry codes</a>
     */
    @JsonIgnore
    public Integer getCode() {
        return this.code;
    }

    /**
     * Sets Ledger entry code.
     * Contains the Ledger entry code. See the list of Ledger Entry Codes.
     *
     * @param code the code
     *
     * @see <a href="https://bitpay.readme.io/reference/ledger-entry-codes">Ledger entry codes</a>
     */
    @JsonProperty("code")
    public void setCode(final Integer code) {
        this.code = code;
    }

    /**
     * Gets description.
     * Ledger entry description.
     * Also contains an id depending on the type of entry
     * (for instance payout id, settlement id, invoice orderId etc...).
     *
     * @return the description
     */
    @JsonIgnore
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets description.
     * Ledger entry description.
     * Also contains an id depending on the type of entry
     * (for instance payout id, settlement id, invoice orderId etc...).
     *
     * @param description the description
     */
    @JsonProperty("description")
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Gets date and time of the ledger entry (UTC). ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @return the timestamp
     */
    @JsonIgnore
    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * Sets date and time of the ledger entry (UTC). ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @param timestamp the timestamp
     */
    @JsonProperty("timestamp")
    public void setTimestamp(final ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets tx type.
     *
     * @return the tx type
     * @deprecated see type
     */
    @Deprecated
    @JsonIgnore
    public String getTxType() {
        return this.txType;
    }

    /**
     * Sets tx type.
     *
     * @param txType the tx type
     * @deprecated see type
     */
    @JsonProperty("txType")
    public void setTxType(final String txType) {
        this.txType = txType;
    }

    /**
     * Gets power of 10 used for conversion.
     *
     * @return the scale
     */
    @JsonIgnore
    public Integer getScale() {
        return this.scale;
    }

    /**
     * Sets power of 10 used for conversion.
     *
     * @param scale the scale
     */
    @JsonProperty("scale")
    public void setScale(final Integer scale) {
        this.scale = scale;
    }

    /**
     * Gets BitPay invoice Id.
     *
     * @return the invoice id
     */
    @JsonIgnore
    public String getInvoiceId() {
        return this.invoiceId;
    }

    /**
     * Sets BitPay invoice Id.
     *
     * @param invoiceId the invoice id
     */
    @JsonProperty("invoiceId")
    public void setInvoiceId(final String invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * Gets Buyer.
     *
     * @return the buyer
     */
    @JsonIgnore
    public Buyer getBuyer() {
        return this.buyer;
    }

    /**
     * Sets Buyer.
     *
     * @param buyer the buyer
     */
    @JsonProperty("buyerFields")
    public void setBuyer(final Buyer buyer) {
        this.buyer = buyer;
    }

    /**
     * Gets invoice price in the invoice original currency.
     *
     * @return the invoice amount
     */
    @JsonIgnore
    public Double getInvoiceAmount() {
        return this.invoiceAmount;
    }

    /**
     * Sets invoice price in the invoice original currency.
     *
     * @param invoiceAmount the invoice amount
     */
    @JsonProperty("invoiceAmount")
    public void setInvoiceAmount(final Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     * Gets currency used for invoice creation.
     *
     * @return the invoice currency
     */
    @JsonIgnore
    public String getInvoiceCurrency() {
        return this.invoiceCurrency;
    }

    /**
     * Sets currency used for invoice creation.
     *
     * @param invoiceCurrency the invoice currency
     */
    @JsonProperty("invoiceCurrency")
    public void setInvoiceCurrency(final String invoiceCurrency) {
        this.invoiceCurrency = invoiceCurrency;
    }

    /**
     * Gets transaction currency.
     * Cryptocurrency selected by the consumer when paying an invoice.
     *
     * @return the transaction currency
     */
    @JsonIgnore
    public String getTransactionCurrency() {
        return this.transactionCurrency;
    }

    /**
     * Sets transaction currency.
     * Cryptocurrency selected by the consumer when paying an invoice.
     *
     * @param transactionCurrency the transaction currency
     */
    @JsonProperty("transactionCurrency")
    public void setTransactionCurrency(final String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    /**
     * Gets Ledger resource Id.
     *
     * @return the id
     */
    @JsonIgnore
    public String getId() {
        return this.id;
    }

    /**
     * Sets Ledger resource Id.
     *
     * @param id the id
     */
    @JsonProperty("id")
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Gets the refund requestId.
     *
     * @return refund requestId
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getSupportRequest() {
        return this.supportRequest;
    }

    /**
     * Sets the refund requestId.
     *
     * @param supportRequest refund request id
     */
    @JsonProperty("supportRequest")
    public void setSupportRequest(final String supportRequest) {
        this.supportRequest = supportRequest;
    }

    /**
     * Gets ledger entry currency for the corresponding amount.
     *
     * @return currency
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCurrency() {
        return this.currency;
    }

    /**
     * Sets ledger entry currency for the corresponding amount.
     *
     * @param currency currency
     */
    @JsonProperty("currency")
    public void setCurrency(final String currency) {
        this.currency = currency;
    }
}
