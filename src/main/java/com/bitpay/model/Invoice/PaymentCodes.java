package com.bitpay.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentCodes {

    private PaymentCode _btc = new PaymentCode();
    private PaymentCode _bch = new PaymentCode();

    public PaymentCodes() {}

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
}
