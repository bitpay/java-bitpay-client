package com.bitpay.sdk.model.Settlement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RefundAmount {
    private Float _btc;
    private Float _usd;
    private Float _eur;

    public RefundAmount() {
    }

    @JsonIgnore
    public Float getBtc() {
        return _btc;
    }

    @JsonProperty("btc")
    public void setBtc(Float btc) {
        this._btc = btc;
    }

    @JsonIgnore
    public Float getUsd() {
        return _usd;
    }

    @JsonProperty("usd")
    public void setUsd(Float usd) {
        this._usd = usd;
    }

    @JsonIgnore
    public Float getEur() {
        return _eur;
    }

    @JsonProperty("eur")
    public void setEur(Float eur) {
        this._eur = eur;
    }
}
