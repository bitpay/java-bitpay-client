package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentCode {

    private String _bip72b;
    private String _bip73;
    private String _eip681;
    private String _eip681b;

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

    @JsonIgnore
    public String getEip681() {
        return _eip681;
    }

    @JsonProperty("EIP681")
    public void setEip681(String eip681) {
        this._eip681 = eip681;
    }

    @JsonIgnore
    public String getEip681b() {
        return _eip681b;
    }

    @JsonProperty("EIP681b")
    public void setEip681b(String eip681b) {
        this._eip681b = eip681b;
    }
}
