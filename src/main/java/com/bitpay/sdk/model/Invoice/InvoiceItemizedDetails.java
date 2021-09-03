package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceItemizedDetails {
    private double _amount;
    private String _description;
    private Boolean _isFee;

    public InvoiceItemizedDetails() {
    }

    @JsonIgnore
    public double getAmount() {
        return _amount;
    }

    @JsonProperty("amount")
    public void setAmount(double amount) {
        this._amount = amount;
    }

    @JsonIgnore
    public String getDescription() {
        return _description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this._description = description;
    }

    @JsonIgnore
    public Boolean getIsFee() { return _isFee; }

    @JsonProperty("IsFee")
    public void setIsFee(Boolean isFee) {
        this._isFee = isFee;
    }
}
