/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.invoice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvoiceBuyerProvidedInfoTest {

    @Test
    public void it_should_allow_to_manipulate_name() {
        String expected = "SomeString";
        InvoiceBuyerProvidedInfo testedClass = this.getTestedClass();

        testedClass.setName(expected);
        Assertions.assertSame(expected, testedClass.getName());
    }

    @Test
    public void it_should_allow_to_manipulate_phoneNumber() {
        String expected = "555-555-555";
        InvoiceBuyerProvidedInfo testedClass = this.getTestedClass();

        testedClass.setPhoneNumber(expected);
        Assertions.assertSame(expected, testedClass.getPhoneNumber());
    }

    @Test
    public void it_should_allow_to_manipulate_selectedTransactionCurrency() {
        String expected = "USD";
        InvoiceBuyerProvidedInfo testedClass = this.getTestedClass();

        testedClass.setSelectedTransactionCurrency(expected);
        Assertions.assertSame(expected, testedClass.getSelectedTransactionCurrency());
    }

    @Test
    public void it_should_allow_to_manipulate_emailAddress() {
        String expected = "some@email.com";
        InvoiceBuyerProvidedInfo testedClass = this.getTestedClass();

        testedClass.setEmailAddress(expected);
        Assertions.assertSame(expected, testedClass.getEmailAddress());
    }

    @Test
    public void it_should_allow_to_manipulate_selectedWallet() {
        String expected = "SomeString";
        InvoiceBuyerProvidedInfo testedClass = this.getTestedClass();

        testedClass.setSelectedWallet(expected);
        Assertions.assertSame(expected, testedClass.getSelectedWallet());
    }

    @Test
    public void it_should_allow_to_manipulate_sms() {
        String expected = "SomeString";
        InvoiceBuyerProvidedInfo testedClass = this.getTestedClass();

        testedClass.setSms(expected);
        Assertions.assertSame(expected, testedClass.getSms());
    }

    @Test
    public void it_should_allow_to_manipulate_smsVerified() {
        boolean expected = true;
        InvoiceBuyerProvidedInfo testedClass = this.getTestedClass();

        testedClass.setSmsVerified(expected);
        Assertions.assertSame(expected, testedClass.getSmsVerified());
    }

    private InvoiceBuyerProvidedInfo getTestedClass() {
        return new InvoiceBuyerProvidedInfo();
    }
}
