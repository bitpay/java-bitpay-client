package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentTotal {

    private BigDecimal _btc;
    private BigDecimal _bch;
    private BigDecimal _eth;
    private BigDecimal _usdc;
    private BigDecimal _gusd;
    private BigDecimal _pax;
    private BigDecimal _xrp;

    public PaymentTotal() {
    }

    @JsonIgnore
    public BigDecimal getBtc() {
        return _btc;
    }

    @JsonProperty("BTC")
    public void setBtc(BigDecimal btc) {
        this._btc = btc;
    }

    @JsonIgnore
    public BigDecimal getBch() {
        return _bch;
    }

    @JsonProperty("BCH")
    public void setBch(BigDecimal bch) {
        this._bch = bch;
    }

    @JsonIgnore
    public BigDecimal getEth() { return _eth; }

    @JsonProperty("ETH")
    public void setEth(BigDecimal eth) {
        this._eth = eth;
    }

    @JsonIgnore
    public BigDecimal getUsdc() { return _usdc; }

    @JsonProperty("USDC")
    public void setUsdc(BigDecimal usdc) { this._usdc = usdc; }

    @JsonIgnore
    public BigDecimal getGusd() { return _gusd; }

    @JsonProperty("GUSD")
    public void setGusd(BigDecimal gusd) { this._gusd = gusd; }

    @JsonIgnore
    public BigDecimal getPax() { return _pax; }

    @JsonProperty("PAX")
    public void setPax(BigDecimal pax) { this._pax = pax; }

    @JsonIgnore
    public BigDecimal getXrp() { return _xrp; }

    @JsonProperty("XRP")
    public void setXrp(BigDecimal xrp) { this._xrp = xrp; }
}
