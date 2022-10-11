/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Payout;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RecipientReferenceMethodTest {

    @Test
    public void it_should_return_email() {
        Assertions.assertEquals(1, RecipientReferenceMethod.EMAIL);
    }

    @Test
    public void it_should_return_recipient_id() {
        Assertions.assertEquals(2, RecipientReferenceMethod.RECIPIENT_ID);
    }

    @Test
    public void it_should_return_shopper_id() {
        Assertions.assertEquals(3, RecipientReferenceMethod.SHOPPER_ID);
    }
}
