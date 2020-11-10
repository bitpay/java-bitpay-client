package com.bitpay.sdk.model.Payout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutInstructionBtcSummary {

    private final Double _paid;
    private final Double _unpaid;

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
