package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentCodes {

    private PaymentCode _btc = new PaymentCode();
    private PaymentCode _bch = new PaymentCode();
    private PaymentCode _eth = new PaymentCode();

    public PaymentCodes() {
    }

    @JsonIgnore
    public PaymentCode getBtc() {
        return _btc;
    }

    @JsonProperty("BTC")
    public void setBtc(PaymentCode btc) {
        this._btc = btc;
    }

    @JsonIgnore
    public PaymentCode getBch() {
        return _bch;
    }

    @JsonProperty("BCH")
    public void setBch(PaymentCode bch) {
        this._bch = bch;
    }

    @JsonIgnore
    public PaymentCode getEth() {
        return _eth;
    }

    @JsonProperty("ETH")
    public void setEth(PaymentCode eth) {
        this._eth = eth;
    }
}
