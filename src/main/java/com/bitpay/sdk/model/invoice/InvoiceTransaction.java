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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
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
    private Integer confirmations;
    private ZonedDateTime time;
    private ZonedDateTime receivedTime;
    private String txid = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
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
    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets confirmations.
     *
     * @return the confirmations
     */
    @JsonIgnore
    public Integer getConfirmations() {
        return this.confirmations;
    }

    /**
     * Sets confirmations.
     *
     * @param confirmations the confirmations
     */
    @JsonProperty("confirmations")
    public void setConfirmations(final Integer confirmations) {
        this.confirmations = confirmations;
    }

    /**
     * Gets received time.
     *
     * @return the received time
     */
    @JsonIgnore
    @JsonSerialize(using = ZonedDateTimeToIso8601Serializer.class)
    public ZonedDateTime getReceivedTime() {
        return this.receivedTime;
    }

    /**
     * Sets received time.
     *
     * @param receivedTime the received time
     */
    @JsonProperty("receivedTime")
    @JsonDeserialize(using = Iso8601ToZonedDateTimeDeserializer.class)
    public void setReceivedTime(final ZonedDateTime receivedTime) {
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
    public void setTransactionId(final String txid) {
        this.txid = txid;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    @JsonIgnore
    @JsonSerialize(using = ZonedDateTimeToIso8601Serializer.class)
    public ZonedDateTime getTime() {
        return this.time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    @JsonProperty("time")
    @JsonDeserialize(using = Iso8601ToZonedDateTimeDeserializer.class)
    public void setTime(final ZonedDateTime time) {
        this.time = time;
    }

    /**
     * Gets exchange rates.
     *
     * @return ex rates
     */
    public Map<String, BigDecimal> getExRates() {
        return this.exRates;
    }

    /**
     * Sets exchange rates.
     *
     * @param exRates Exchange rates
     */
    @JsonProperty("exRates")
    public void setExRates(final Map<String, BigDecimal> exRates) {
        this.exRates = exRates;
    }

    /**
     * Gets output index.
     *
     * @return output index
     */
    public Integer getOutputIndex() {
        return this.outputIndex;
    }

    /**
     * Sets output index.
     *
     * @param outputIndex output index
     */
    @JsonProperty("outputIndex")
    public void setOutputIndex(final Integer outputIndex) {
        this.outputIndex = outputIndex;
    }
}
