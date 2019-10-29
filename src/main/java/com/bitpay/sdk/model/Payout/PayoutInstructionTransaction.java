package com.bitpay.sdk.model.Payout;

import com.bitpay.sdk.util.DateDeserializer;
import com.bitpay.sdk.util.DateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutInstructionTransaction {

    private String _txid;
    private Double _amount;
    private Long _date;

    public PayoutInstructionTransaction() {
    }

    @JsonIgnore
    public String getTxid() {
        return _txid;
    }

    @JsonProperty("txid")
    public void setTxid(String txid) {
        this._txid = txid;
    }

    @JsonIgnore
    public Double getAmount() {
        return _amount;
    }

    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this._amount = amount;
    }

    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public Long getDate() {
        return _date;
    }

    @JsonProperty("date")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setDate(Long date) {
        this._date = date;
    }

}
