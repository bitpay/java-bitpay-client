package com.bitpay.sdk.model.Settlement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RefundInfo {
    private String _supportRequest;
    private String _currency;
    private RefundAmount _amounts;

    public RefundInfo() {
    }

    @JsonIgnore
    public String getSupportRequest() {
        return _supportRequest;
    }

    @JsonProperty("supportRequest")
    public void setSupportRequest(String supportRequest) {
        this._supportRequest = supportRequest;
    }

    @JsonIgnore
    public String getCurrency() {
        return _currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this._currency = currency;
    }

    @JsonIgnore
    public RefundAmount getAmount() {
        return _amounts;
    }

    @JsonProperty("amounts")
    public void setAmount(RefundAmount amounts) {
        this._amounts = amounts;
    }
}
