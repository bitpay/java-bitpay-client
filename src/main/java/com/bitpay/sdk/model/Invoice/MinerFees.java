package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MinerFees {

    private MinerFeesItem _btc = new MinerFeesItem();
    private MinerFeesItem _bch = new MinerFeesItem();
    private MinerFeesItem _eth = new MinerFeesItem();
    private MinerFeesItem _usdc = new MinerFeesItem();
    private MinerFeesItem _gusd = new MinerFeesItem();
    private MinerFeesItem _pax = new MinerFeesItem();
    private MinerFeesItem _xrp = new MinerFeesItem();

    public MinerFees() {
    }

    @JsonIgnore
    public MinerFeesItem getBtc() {
        return _btc;
    }

    @JsonProperty("BTC")
    public void setBtc(MinerFeesItem btc) {
        this._btc = btc;
    }

    @JsonIgnore
    public MinerFeesItem getBch() {
        return _bch;
    }

    @JsonProperty("BCH")
    public void setBch(MinerFeesItem bch) {
        this._bch = bch;
    }

    @JsonIgnore
    public MinerFeesItem getEth() {
        return _eth;
    }

    @JsonProperty("ETH")
    public void setEth(MinerFeesItem eth) {
        this._eth = eth;
    }

    @JsonIgnore
    public MinerFeesItem getUsdc() { return _usdc; }

    @JsonProperty("USDC")
    public void setUsdc(MinerFeesItem usdc) { this._usdc = usdc; }

    @JsonIgnore
    public MinerFeesItem getGusd() { return _gusd; }

    @JsonProperty("GUSD")
    public void setGusd(MinerFeesItem gusd) { this._gusd = gusd; }

    @JsonIgnore
    public MinerFeesItem getPax() { return _pax; }

    @JsonProperty("PAX")
    public void setPax(MinerFeesItem pax) { this._pax = pax; }

    @JsonIgnore
    public MinerFeesItem getXrp() { return _xrp; }

    @JsonProperty("XRP")
    public void setXrp(MinerFeesItem xrp) { this._xrp = xrp; }
}
