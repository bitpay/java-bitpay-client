/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.payout;

import com.bitpay.sdk.util.serializer.Iso8601ToZonedDateTimeDeserializer;
import com.bitpay.sdk.util.serializer.ZonedDateTimeToIso8601Serializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.ZonedDateTime;

/**
 * The type Payout instruction transaction.
 *
 * @see <a href="https://bitpay.readme.io/reference/payouts">REST API Payouts</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutTransaction {

    protected String txid;
    protected Double amount;
    protected ZonedDateTime date;
    protected Integer confirmations;

    /**
     * Instantiates a new Payout instruction transaction.
     */
    public PayoutTransaction() {
    }

    /**
     * Gets cryptocurrency transaction hash for the executed payout.
     *
     * @return the txid
     */
    @JsonIgnore
    public String getTxid() {
        return this.txid;
    }

    /**
     * Sets cryptocurrency transaction hash for the executed payout.
     *
     * @param txid the txid
     */
    @JsonProperty("txid")
    public void setTxid(final String txid) {
        this.txid = txid;
    }

    /**
     * Gets amount of cryptocurrency sent to the requested address.
     *
     * @return the amount
     */
    @JsonIgnore
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
     * Gets date and time (UTC) when the cryptocurrency transaction is broadcasted.
     * ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @return the date
     */
    @JsonIgnore
    @JsonSerialize(using = ZonedDateTimeToIso8601Serializer.class)
    public ZonedDateTime getDate() {
        return this.date;
    }

    /**
     * Sets date and time (UTC) when the cryptocurrency transaction is broadcasted.
     * ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @param date the date
     */
    @JsonProperty("date")
    @JsonDeserialize(using = Iso8601ToZonedDateTimeDeserializer.class)
    public void setDate(final ZonedDateTime date) {
        this.date = date;
    }

    /**
     * Gets the number of confirmations the transaction has received.
     *
     * @return confirmations
     */
    @JsonIgnore
    public Integer getConfirmations() {
        return this.confirmations;
    }

    /**
     * Sets the number of confirmations the transaction has received.
     *
     * @param confirmations confirmations
     */
    @JsonProperty("confirmations")
    public void setConfirmations(final Integer confirmations) {
        this.confirmations = confirmations;
    }
}
