package com.bitpay.sdk.model.Payout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Payout instruction btc summary.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutInstructionBtcSummary {

    private final Double _paid;
    private final Double _unpaid;

    /**
     * Instantiates a new Payout instruction btc summary.
     *
     * @param paid   the paid
     * @param unpaid the unpaid
     */
    public PayoutInstructionBtcSummary(Double paid, Double unpaid) {
        this._paid = paid;
        this._unpaid = unpaid;
    }

    /**
     * Gets paid.
     *
     * @return the paid
     */
    public Double getPaid() {
        return _paid;
    }

    /**
     * Gets unpaid.
     *
     * @return the unpaid
     */
    public Double getUnpaid() {
        return _unpaid;
    }

}
