package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentTotal {

    private Double _btc;
    private Double _bch;
    private Double _eth;
    private Double _usdc;
    private Double _gusd;
    private Double _pax;

    public PaymentTotal() {
    }

    @JsonIgnore
    public Double getBtc() {
        return _btc;
    }

    @JsonProperty("BTC")
    public void setBtc(Double btc) {
        this._btc = btc;
    }

    @JsonIgnore
    public Double getBch() {
        return _bch;
    }

    @JsonProperty("BCH")
    public void setBch(Double bch) {
        this._bch = bch;
    }

    @JsonIgnore
    public Double getEth() { return _eth; }

    @JsonProperty("ETH")
    public void setEth(Double eth) {
        this._eth = eth;
    }

    @JsonIgnore
    public Double getUsdc() { return _usdc; }

    @JsonProperty("USDC")
    public void setUsdc(Double usdc) { this._usdc = usdc; }

    @JsonIgnore
    public Double getGusd() { return _gusd; }

    @JsonProperty("GUSD")
    public void setPax(Double gusd) { this._gusd = gusd; }

    @JsonIgnore
    public Double getPax() { return _pax; }

    @JsonProperty("PAX")
    public void set(Double pax) { this._pax = pax; }
}
