/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Payout;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PayoutInstructionBtcSummaryTest {

    @Test
    public void it_should_get_paid() {
        // given
        Double expected = 12.34;

        // when
        PayoutInstructionBtcSummary testedClass = this.getTestedClass(expected, 444.44);

        // then
        Assertions.assertEquals(expected, testedClass.getPaid());
    }

    @Test
    public void it_should_change_paid() {
        // given
        Double expected = 12.34;

        // when
        PayoutInstructionBtcSummary testedClass = this.getTestedClass(34.34, expected);

        // then
        Assertions.assertEquals(expected, testedClass.getUnpaid());
    }

    private PayoutInstructionBtcSummary getTestedClass(Double paid, Double unpaid) {
        return new PayoutInstructionBtcSummary(paid, unpaid);
    }
}
