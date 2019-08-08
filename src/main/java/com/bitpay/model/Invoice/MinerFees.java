package com.bitpay.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MinerFees {

    private MinerFeesItem _btc = new MinerFeesItem();
    private MinerFeesItem _bch = new MinerFeesItem();

    public MinerFees() {}

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
}
