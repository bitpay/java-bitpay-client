/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.invoice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RefundStatusTest {

    @Test
    public void it_should_return_preview_status() {
        Assertions.assertSame("preview", RefundStatus.PREVIEW);
    }

    @Test
    public void it_should_return_created_status() {
        Assertions.assertSame("created", RefundStatus.CREATED);
    }

    @Test
    public void it_should_return_pending_status() {
        Assertions.assertSame("pending", RefundStatus.PENDING);
    }

    @Test
    public void it_should_return_canceled_status() {
        Assertions.assertSame("canceled", RefundStatus.CANCELED);
    }

    @Test
    public void it_should_return_success_status() {
        Assertions.assertSame("success", RefundStatus.SUCCESS);
    }

    @Test
    public void it_should_return_failure_status() {
        Assertions.assertSame("failure", RefundStatus.FAILURE);
    }
}
