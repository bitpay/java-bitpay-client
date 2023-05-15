/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.payout;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PayoutRecipientsTest {

    @Test
    public void it_should_set_default_values() {
        // given
        List<PayoutRecipient> expected = Collections.singletonList(Mockito.mock(PayoutRecipient.class));
        PayoutRecipients testedClass = this.getTestedClass(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getInstructions());
    }

    @Test
    public void it_should_change_guid() {
        // given
        String expected = "expectedString";
        PayoutRecipients testedClass = this.getTestedClass();

        // when
        testedClass.setGuid(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getGuid());
    }

    @Test
    public void it_should_change_recipients() {
        // given
        List<PayoutRecipient> expected = Collections.singletonList(Mockito.mock(PayoutRecipient.class));
        PayoutRecipients testedClass = this.getTestedClass();

        // when
        testedClass.setInstructions(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getInstructions());
    }

    @Test
    public void it_should_change_token() {
        // given
        String expected = "expectedString";
        PayoutRecipients testedClass = this.getTestedClass();

        // when
        testedClass.setToken(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getToken());
    }

    private PayoutRecipients getTestedClass() {
        return new PayoutRecipients(Collections.singletonList(Mockito.mock(PayoutRecipient.class)));
    }

    private PayoutRecipients getTestedClass(List<PayoutRecipient> recipients) {
        return new PayoutRecipients(recipients);
    }
}
