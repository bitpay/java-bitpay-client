/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.ledger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BuyerTest {

    @Test
    public void it_should_change_name() {
        // given
        String expected = "expectedString";
        Buyer testedClass = this.getTestedClass();

        // when
        testedClass.setName(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getName());
    }

    @Test
    public void it_should_change_address1() {
        // given
        String expected = "expectedString";
        Buyer testedClass = this.getTestedClass();

        // when
        testedClass.setAddress1(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAddress1());
    }

    @Test
    public void it_should_change_address2() {
        // given
        String expected = "expectedString";
        Buyer testedClass = this.getTestedClass();

        // when
        testedClass.setAddress2(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAddress2());
    }

    @Test
    public void it_should_change_city() {
        // given
        String expected = "expectedString";
        Buyer testedClass = this.getTestedClass();

        // when
        testedClass.setCity(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getCity());
    }

    @Test
    public void it_should_change_state() {
        // given
        String expected = "expectedString";
        Buyer testedClass = this.getTestedClass();

        // when
        testedClass.setState(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getState());
    }

    @Test
    public void it_should_change_zip() {
        // given
        String expected = "expectedString";
        Buyer testedClass = this.getTestedClass();

        // when
        testedClass.setZip(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getZip());
    }

    @Test
    public void it_should_change_country() {
        // given
        String expected = "expectedString";
        Buyer testedClass = this.getTestedClass();

        // when
        testedClass.setCountry(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getCountry());
    }

    @Test
    public void it_should_change_phone() {
        // given
        String expected = "expectedString";
        Buyer testedClass = this.getTestedClass();

        // when
        testedClass.setPhone(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getPhone());
    }

    @Test
    public void it_should_change_notify() {
        // given
        boolean expected = true;
        Buyer testedClass = this.getTestedClass();

        // when
        testedClass.setNotify(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getNotify());
    }

    @Test
    public void it_should_change_email() {
        // given
        String expected = "expected@email.com";
        Buyer testedClass = this.getTestedClass();

        // when
        testedClass.setEmail(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getEmail());
    }

    private Buyer getTestedClass() {
        return new Buyer();
    }
}
