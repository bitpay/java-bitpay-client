/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import static com.bitpay.sdk.model.ModelConfiguration.DEFAULT_NON_SENT_VALUE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Initially empty when the invoice is created.
 * It will be populated with the crypto currency transaction hashes linked to the invoice.
 * For instance the consumer's transaction hash if the invoice is paid, but also the refund transaction hash
 * if the merchant decide to issue a refund to the purchaser.
 *
 * @see <a href="https://bitpay.readme.io/reference/invoices">REST API Invoices</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceTransaction {

    private BigDecimal amount;
    private int confirmations;
    private Date time;
    private Date receivedTime;
    private String txid = DEFAULT_NON_SENT_VALUE;
    private Map<String, BigDecimal> exRates;
    private Integer outputIndex;

    /**
     * Instantiates a new Invoice transaction.
     */
    public InvoiceTransaction() {
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    @JsonIgnore
    public BigDecimal getAmount() {
        return this.amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    @JsonProperty("amount")
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets confirmations.
     *
     * @return the confirmations
     */
    @JsonIgnore
    public int getConfirmations() {
        return this.confirmations;
    }

    /**
     * Sets confirmations.
     *
     * @param confirmations the confirmations
     */
    @JsonProperty("confirmations")
    public void setConfirmations(int confirmations) {
        this.confirmations = confirmations;
    }

    /**
     * Gets received time.
     *
     * @return the received time
     */
    @JsonIgnore
    public Date getReceivedTime() {
        return this.receivedTime;
    }

    /**
     * Sets received time.
     *
     * @param receivedTime the received time
     */
    @JsonProperty("receivedTime")
    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    /**
     * Gets transaction id.
     *
     * @return the transaction id
     */
    @JsonIgnore
    public String getTransactionId() {
        return this.txid;
    }

    /**
     * Sets transaction id.
     *
     * @param txid the txid
     */
    @JsonProperty("txid")
    public void setTransactionId(String txid) {
        this.txid = txid;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    @JsonIgnore
    public Date getTime() {
        return this.time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    @JsonProperty("time")
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * Gets exchange rates.
     *
     * @return ex rates
     */
    public Map<String, BigDecimal> getExRates() {
        return exRates;
    }

    /**
     * Sets exchange rates.
     */
    @JsonProperty("exRates")
    public void setExRates(Map<String, BigDecimal> exRates) {
        this.exRates = exRates;
    }

    /**
     * Gets output index.
     * @return output index
     */
    public Integer getOutputIndex() {
        return outputIndex;
    }

    /**
     * @param outputIndex output index
     */
    @JsonProperty("outputIndex")
    public void setOutputIndex(Integer outputIndex) {
        this.outputIndex = outputIndex;
    }
}
