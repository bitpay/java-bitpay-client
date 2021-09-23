package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceUniversalCodes {
    private String _paymentString;
    private String _verificationLink;

    public InvoiceUniversalCodes() {
    }

    @JsonIgnore
    public String getBitpay() {
        return _paymentString;
    }

    @JsonProperty("paymentString")
    public void setBitpay(String paymentString) {
        this._paymentString = paymentString;
    }

    @JsonIgnore
    public String getVerificationLink() {
        return _verificationLink;
    }

    @JsonProperty("verificationLink")
    public void setVerificationLink(String verificationLink) {
        this._verificationLink = verificationLink;
    }
}
