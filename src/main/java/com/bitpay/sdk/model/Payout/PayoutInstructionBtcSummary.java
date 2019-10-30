package com.bitpay.sdk.model.Payout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutInstructionBtcSummary {

    private Double _paid;
    private Double _unpaid;

    public PayoutInstructionBtcSummary(Double paid, Double unpaid) {
        this._paid = paid;
        this._unpaid = unpaid;
    }

    public Double getPaid() {
        return _paid;
    }

    public Double getUnpaid() {
        return _unpaid;
    }

}
