/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.invoice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BuyerTest {

    @Test
    public void it_should_manipulate_name() {
        final String expected = "Satoshi";
        Buyer testedClass = this.getTestedClass();

        testedClass.setName(expected);
        Assertions.assertSame(expected, testedClass.getName());
    }

    @Test
    public void it_should_manipulate_address1() {
        final String expected = "Street";
        Buyer testedClass = this.getTestedClass();

        testedClass.setAddress1(expected);
        Assertions.assertSame(expected, testedClass.getAddress1());
    }

    @Test
    public void it_should_manipulate_address2() {
        final String expected = "911";
        Buyer testedClass = this.getTestedClass();

        testedClass.setAddress2(expected);
        Assertions.assertSame(expected, testedClass.getAddress2());
    }

    @Test
    public void it_should_manipulate_locality() {
        final String expected = "Washington";
        Buyer testedClass = this.getTestedClass();

        testedClass.setLocality(expected);
        Assertions.assertSame(expected, testedClass.getLocality());
    }

    @Test
    public void it_should_manipulate_region() {
        final String expected = "District of Columbia";
        Buyer testedClass = this.getTestedClass();

        testedClass.setRegion(expected);
        Assertions.assertSame(expected, testedClass.getRegion());
    }

    @Test
    public void it_should_manipulate_postalCode() {
        final String expected = "20000";
        Buyer testedClass = this.getTestedClass();

        testedClass.setPostalCode(expected);
        Assertions.assertSame(expected, testedClass.getPostalCode());
    }

    @Test
    public void it_should_manipulate_country() {
        final String expected = "USA";
        Buyer testedClass = this.getTestedClass();

        testedClass.setCountry(expected);
        Assertions.assertSame(expected, testedClass.getCountry());
    }

    @Test
    public void it_should_manipulate_email() {
        final String expected = "satoshi@buyeremaildomain.com";
        Buyer testedClass = this.getTestedClass();

        testedClass.setEmail(expected);
        Assertions.assertSame(expected, testedClass.getEmail());
    }

    @Test
    public void it_should_manipulate_phone() {
        final String expected = "555-555-555";
        Buyer testedClass = this.getTestedClass();

        testedClass.setPhone(expected);
        Assertions.assertSame(expected, testedClass.getPhone());
    }

    @Test
    public void it_should_manipulate_notify() {
        final boolean expected = true;
        Buyer testedClass = this.getTestedClass();

        Assertions.assertSame(false, testedClass.getNotify());
        testedClass.setNotify(expected);
        Assertions.assertSame(expected, testedClass.getNotify());
    }


    private Buyer getTestedClass() {
        return new Buyer();
    }
}
