/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.payout;

/**
 * The object with information about not created/cancelled payout.
 *
 * @see <a href="https://bitpay.readme.io/reference/create-payout-group">REST API Create Payouts</a>
 * @see <a href="https://bitpay.readme.io/reference/cancel-a-payout-group">REST API Cancel Payouts</a>
 */
public class PayoutGroupFailed {

    public String errMessage;
    public String payoutId;
    public String payee;

    public PayoutGroupFailed() {
    }

    public PayoutGroupFailed(
        final String errMessage,
        final String payoutId,
        final String payee
    ) {
        this.errMessage = errMessage;
        this.payoutId = payoutId;
        this.payee = payee;
    }

    public String getErrMessage() {
        return this.errMessage;
    }

    public String getPayoutId() {
        return this.payoutId;
    }

    public String getPayee() {
        return this.payee;
    }
}
