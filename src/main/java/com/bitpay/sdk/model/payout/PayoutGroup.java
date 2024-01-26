/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.payout;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;

/**
 * The Payout group response for create and cancel.
 *
 * @see <a href="https://bitpay.readme.io/reference/create-payout-group">REST API Create Payouts</a>
 * @see <a href="https://bitpay.readme.io/reference/cancel-a-payout-group">REST API Cancel Payouts</a>
 */
public class PayoutGroup {

    @JsonProperty("created")
    @JsonAlias("cancelled")
    protected List<Payout> payouts = Collections.emptyList();
    protected List<PayoutGroupFailed> failed = Collections.emptyList();

    public PayoutGroup() {
    }

    public PayoutGroup(
        final List<Payout> payouts,
        final List<PayoutGroupFailed> failed
    ) {
        this.payouts = payouts;
        this.failed = failed;
    }

    public List<Payout> getPayouts() {
        return this.payouts;
    }

    public List<PayoutGroupFailed> getFailed() {
        return this.failed;
    }
}
