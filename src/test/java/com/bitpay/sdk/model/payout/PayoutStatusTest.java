/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.payout;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PayoutStatusTest {

    @Test
    public void it_should_return_new_payout_status() {
        Assertions.assertEquals("new", PayoutStatus.New);
    }

    @Test
    public void it_should_return_funded_payout_status() {
        Assertions.assertEquals("funded", PayoutStatus.Funded);
    }

    @Test
    public void it_should_return_processing_payout_status() {
        Assertions.assertEquals("processing", PayoutStatus.Processing);
    }

    @Test
    public void it_should_return_complete_payout_status() {
        Assertions.assertEquals("complete", PayoutStatus.Complete);
    }

    @Test
    public void it_should_return_failed_payout_status() {
        Assertions.assertEquals("failed", PayoutStatus.Failed);
    }

    @Test
    public void it_should_return_cancelled_payout_status() {
        Assertions.assertEquals("cancelled", PayoutStatus.Cancelled);
    }

    @Test
    public void it_should_return_paid_payout_status() {
        Assertions.assertEquals("paid", PayoutStatus.Paid);
    }

    @Test
    public void it_should_return_unpaid_payout_status() {
        Assertions.assertEquals("unpaid", PayoutStatus.Unpaid);
    }
}
