package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceTransaction {

    private BigDecimal _amount;
    private int _confirmations;
    private Date _time;
    private Date _receivedTime;
    private String _txid;

    public InvoiceTransaction() {
    }

    @JsonIgnore
    public BigDecimal getAmount() {
        return _amount;
    }

    @JsonProperty("amount")
    public void setAmount(BigDecimal amount) {
        this._amount = amount;
    }

    @JsonIgnore
    public int getConfirmations() {
        return _confirmations;
    }

    @JsonProperty("confirmations")
    public void setConfirmations(int confirmations) {
        this._confirmations = confirmations;
    }

    @JsonIgnore
    public Date getReceivedTime() {
        return _receivedTime;
    }

    @JsonProperty("receivedTime")
    public void setReceivedTime(Date receivedTime) {
        this._receivedTime = receivedTime;
    }

    @JsonIgnore
    public String getTransactionId() {
        return _txid;
    }

    @JsonProperty("txid")
    public void setTransactionId(String txid) {
        this._txid = txid;
    }

    @JsonIgnore
    public Date getTime() {
        return _time;
    }

    @JsonProperty("time")
    public void setTime(Date time) {
        this._time = time;
    }

}
