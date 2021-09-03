package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceTransactionDetails {
    private Double _amount;
    private String _description;
    private Boolean _isFee;

    public InvoiceTransactionDetails(Double amount, String description, Boolean isFee) {
        this._amount = amount;
        this._description = description;
        this._isFee = isFee;
    }

    @JsonProperty("amount")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Double getAmount() {
        return _amount;
    }

    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this._amount = amount;
    }

    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getDescription() {
        return _description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this._description = description;
    }

    @JsonProperty("isFee")
    public Boolean getIsFee() {
        return _isFee;
    }

    @JsonProperty("isFee")
    public void setIsFee(Boolean isFee) {
        this._isFee = isFee;
    }
}
