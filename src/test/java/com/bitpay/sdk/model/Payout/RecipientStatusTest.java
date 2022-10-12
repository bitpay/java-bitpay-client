/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Payout;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RecipientStatusTest {

    @Test
    public void it_should_return_invited_status() {
        Assertions.assertEquals("invited", RecipientStatus.INVITED);
    }

    @Test
    public void it_should_return_unverified_status() {
        Assertions.assertEquals("unverified", RecipientStatus.UNVERIFIED);
    }

    @Test
    public void it_should_return_verified_status() {
        Assertions.assertEquals("verified", RecipientStatus.VERIFIED);
    }

    @Test
    public void it_should_return_active_status() {
        Assertions.assertEquals("active", RecipientStatus.ACTIVE);
    }

    @Test
    public void it_should_return_paused_status() {
        Assertions.assertEquals("paused", RecipientStatus.PAUSED);
    }

    @Test
    public void it_should_return_removed_status() {
        Assertions.assertEquals("removed", RecipientStatus.REMOVED);
    }
}
