package com.bitpay.sdk.model.Payout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutInstructionBtcSummary {

    private final Double paid;
    private final Double unpaid;

    public PayoutInstructionBtcSummary(Double paid, Double unpaid) {
        this.paid = paid;
        this.unpaid = unpaid;
    }

    public Double getPaid() {
        return this.paid;
    }

    public Double getUnpaid() {
        return this.unpaid;
    }

}
