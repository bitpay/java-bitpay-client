package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentCode {

    private String _bip72b;
    private String _bip73;

    public PaymentCode() {
    }

    @JsonIgnore
    public String getBip72b() {
        return _bip72b;
    }

    @JsonProperty("BIP72b")
    public void setBip72b(String bip72b) {
        this._bip72b = bip72b;
    }

    @JsonIgnore
    public String getBip73() {
        return _bip73;
    }

    @JsonProperty("BIP73")
    public void setBip73(String bip73) {
        this._bip73 = bip73;
    }
}
