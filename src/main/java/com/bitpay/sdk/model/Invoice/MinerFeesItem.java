package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MinerFeesItem {

    private Double _satoshisPerByte;
    private Double _totalFee;

    public MinerFeesItem() {
    }

    @JsonIgnore
    public Double getSatoshisPerByte() {
        return _satoshisPerByte;
    }

    @JsonProperty("satoshisPerByte")
    public void setSatoshisPerByte(Double satoshisPerByte) {
        this._satoshisPerByte = satoshisPerByte;
    }

    @JsonIgnore
    public Double getTotalFee() {
        return _totalFee;
    }

    @JsonProperty("totalFee")
    public void setTotalFee(Double totalFee) {
        this._totalFee = totalFee;
    }
}
