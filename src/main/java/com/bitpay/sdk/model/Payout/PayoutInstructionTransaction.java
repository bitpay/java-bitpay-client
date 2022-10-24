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
 * @see <a href="https://bitpay.com/api/#rest-api-resources-payouts">REST API Payouts</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutInstructionTransaction {

    private String _txid;
    private Double _amount;
    private Long _date;

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
        return _txid;
    }

    /**
     * Sets cryptocurrency transaction hash for the executed payout.
     *
     * @param txid the txid
     */
    @JsonProperty("txid")
    public void setTxid(String txid) {
        this._txid = txid;
    }

    /**
     * Gets amount of cryptocurrency sent to the requested address.
     *
     * @return the amount
     */
    @JsonIgnore
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
     * Gets date and time (UTC) when the cryptocurrency transaction is broadcasted.
     * ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @return the date
     */
    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public Long getDate() {
        return _date;
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
        this._date = date;
    }

}
