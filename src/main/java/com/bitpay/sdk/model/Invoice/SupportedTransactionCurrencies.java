package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SupportedTransactionCurrencies {

    private SupportedTransactionCurrency _btc = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency _bch = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency _eth = new SupportedTransactionCurrency();

    public SupportedTransactionCurrencies() {
    }

    @JsonIgnore
    public SupportedTransactionCurrency getBtc() {
        return _btc;
    }

    @JsonProperty("BTC")
    public void setBtc(SupportedTransactionCurrency btc) {
        this._btc = btc;
    }

    @JsonIgnore
    public SupportedTransactionCurrency getBch() {
        return _bch;
    }

    @JsonProperty("BCH")
    public void setBch(SupportedTransactionCurrency bch) {
        this._bch = bch;
    }

    @JsonIgnore
    public SupportedTransactionCurrency getEth() { return _eth; }

    @JsonProperty("ETH")
    public void setEth(SupportedTransactionCurrency eth) {
        this._eth = eth;
    }
}
