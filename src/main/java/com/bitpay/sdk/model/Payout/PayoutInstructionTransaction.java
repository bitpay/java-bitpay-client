package com.bitpay.sdk.model.Payout;

import com.bitpay.sdk.util.DateDeserializer;
import com.bitpay.sdk.util.DateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * The type Payout instruction transaction.
 *
 * @see <a href="https://bitpay.readme.io/reference/payouts">REST API Payouts</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutInstructionTransaction {

    private String txid;
    private Double amount;
    private Long date;

    /**
     * Instantiates a new Payout instruction transaction.
     */
    public PayoutInstructionTransaction() {
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
    public void setTxid(String txid) {
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
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * Gets date and time (UTC) when the cryptocurrency transaction is broadcasted.
     * ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @return the date
     */
    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public Long getDate() {
        return this.date;
    }

    /**
     * Sets date and time (UTC) when the cryptocurrency transaction is broadcasted.
     * ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @param date the date
     */
    @JsonProperty("date")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setDate(Long date) {
        this.date = date;
    }

}
