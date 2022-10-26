/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Initially empty when the invoice is created.
 * It will be populated with the crypto currency transaction hashes linked to the invoice.
 * For instance the consumer's transaction hash if the invoice is paid, but also the refund transaction hash
 * if the merchant decide to issue a refund to the purchaser.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-invoices-resource">REST API Invoices</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceTransaction {

    private BigDecimal _amount;
    private int _confirmations;
    private Date _time;
    private Date _receivedTime;
    private String _txid;

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
        return _amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    @JsonProperty("amount")
    public void setAmount(BigDecimal amount) {
        this._amount = amount;
    }

    /**
     * Gets confirmations.
     *
     * @return the confirmations
     */
    @JsonIgnore
    public int getConfirmations() {
        return _confirmations;
    }

    /**
     * Sets confirmations.
     *
     * @param confirmations the confirmations
     */
    @JsonProperty("confirmations")
    public void setConfirmations(int confirmations) {
        this._confirmations = confirmations;
    }

    /**
     * Gets received time.
     *
     * @return the received time
     */
    @JsonIgnore
    public Date getReceivedTime() {
        return _receivedTime;
    }

    /**
     * Sets received time.
     *
     * @param receivedTime the received time
     */
    @JsonProperty("receivedTime")
    public void setReceivedTime(Date receivedTime) {
        this._receivedTime = receivedTime;
    }

    /**
     * Gets transaction id.
     *
     * @return the transaction id
     */
    @JsonIgnore
    public String getTransactionId() {
        return _txid;
    }

    /**
     * Sets transaction id.
     *
     * @param txid the txid
     */
    @JsonProperty("txid")
    public void setTransactionId(String txid) {
        this._txid = txid;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    @JsonIgnore
    public Date getTime() {
        return _time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    @JsonProperty("time")
    public void setTime(Date time) {
        this._time = time;
    }

}
