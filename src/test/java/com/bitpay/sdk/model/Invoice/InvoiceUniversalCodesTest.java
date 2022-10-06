/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvoiceUniversalCodesTest {

    @Test
    public void it_should_manipulate_paymentString() {
        // given
        InvoiceUniversalCodes testedClass = this.getTestedClass();
        String expected = "SomePaymentString";

        // when
        testedClass.setBitpay(expected);

        // then
        Assertions.assertSame(expected, testedClass.getBitpay());
    }

    @Test
    public void it_should_manipulate_verificationLink() {
        // given
        InvoiceUniversalCodes testedClass = this.getTestedClass();
        String expected = "https://some-url.com";

        // when
        testedClass.setVerificationLink(expected);

        // then
        Assertions.assertSame(expected, testedClass.getVerificationLink());
    }

    private InvoiceUniversalCodes getTestedClass() {
        return new InvoiceUniversalCodes();
    }
}
