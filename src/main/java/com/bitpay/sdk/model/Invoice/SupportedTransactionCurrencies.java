package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SupportedTransactionCurrencies {

    private SupportedTransactionCurrency _btc = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency _bch = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency _eth = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency _usdc = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency _gusd = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency _pax = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency _xrp = new SupportedTransactionCurrency();

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

    @JsonIgnore
    public SupportedTransactionCurrency getUsdc() { return _usdc; }

    @JsonProperty("USDC")
    public void setUsdc(SupportedTransactionCurrency usdc) { this._usdc = usdc; }

    @JsonIgnore
    public SupportedTransactionCurrency getGusd() { return _gusd; }

    @JsonProperty("GUSD")
    public void setGusd(SupportedTransactionCurrency gusd) { this._gusd = gusd; }

    @JsonIgnore
    public SupportedTransactionCurrency getPax() { return _pax; }

    @JsonProperty("PAX")
    public void setPax(SupportedTransactionCurrency pax) { this._pax = pax; }

    @JsonIgnore
    public SupportedTransactionCurrency getXrp() { return _xrp; }

    @JsonProperty("XRP")
    public void setXrp(SupportedTransactionCurrency xrp) { this._xrp = xrp; }
}
