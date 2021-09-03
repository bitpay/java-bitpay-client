package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceUniversalCodes {
    private String _bitpay;

    public InvoiceUniversalCodes() {
    }

    @JsonIgnore
    public String getBitpay() {
        return _bitpay;
    }

    @JsonProperty("bitpay")
    public void setBitpay(String bitpay) {
        this._bitpay = bitpay;
    }
}
