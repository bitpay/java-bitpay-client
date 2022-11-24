package com.bitpay.sdk.model.Payout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Payout instruction btc summary.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutInstructionBtcSummary {

    private final Double paid;
    private final Double unpaid;

    /**
     * Instantiates a new Payout instruction btc summary.
     *
     * @param paid   the paid
     * @param unpaid the unpaid
     */
    public PayoutInstructionBtcSummary(Double paid, Double unpaid) {
        this.paid = paid;
        this.unpaid = unpaid;
    }

    /**
     * Gets paid.
     *
     * @return the paid
     */
    public Double getPaid() {
        return this.paid;
    }

    /**
     * Gets unpaid.
     *
     * @return the unpaid
     */
    public Double getUnpaid() {
        return this.unpaid;
    }

}
