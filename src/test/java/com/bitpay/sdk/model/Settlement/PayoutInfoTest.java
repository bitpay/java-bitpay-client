/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Settlement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PayoutInfoTest {

    @Test
    public void it_should_change_name() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setName(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getName());
    }

    @Test
    public void it_should_change_account() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setAccount(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAccount());
    }

    @Test
    public void it_should_change_routing() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setRouting(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getRouting());
    }

    @Test
    public void it_should_change_merchantEin() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setMerchantEin(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getMerchantEin());
    }

    @Test
    public void it_should_change_label() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setLabel(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getLabel());
    }

    @Test
    public void it_should_change_bankCountry() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setBankCountry(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getBankCountry());
    }

    @Test
    public void it_should_change_bank() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setBank(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getBank());
    }

    @Test
    public void it_should_change_swift() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setSwift(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getSwift());
    }

    @Test
    public void it_should_change_address() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setAddress(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAddress());
    }

    @Test
    public void it_should_change_city() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setCity(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getCity());
    }

    @Test
    public void it_should_change_postal() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setPostal(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getPostal());
    }

    @Test
    public void it_should_change_sort() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setSort(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getSort());
    }

    @Test
    public void it_should_change_wire() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setWire(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getWire());
    }

    @Test
    public void it_should_change_bankName() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setBankName(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getBankName());
    }

    @Test
    public void it_should_change_bankAddress() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setBankAddress(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getBankAddress());
    }

    @Test
    public void it_should_change_bankAddress2() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setBankAddress2(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getBankAddress2());
    }

    @Test
    public void it_should_change_iban() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setIban(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getIban());
    }

    @Test
    public void it_should_change_additionalInformation() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setAdditionalInformation(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAdditionalInformation());
    }

    @Test
    public void it_should_change_accountHolderName() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setAccountHolderName(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAccountHolderName());
    }

    @Test
    public void it_should_change_accountHolderAddress() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setAccountHolderAddress(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAccountHolderAddress());
    }

    @Test
    public void it_should_change_accountHolderAddress2() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setAccountHolderAddress2(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAccountHolderAddress2());
    }

    @Test
    public void it_should_change_accountHolderPostalCode() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setAccountHolderPostalCode(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAccountHolderPostalCode());
    }

    @Test
    public void it_should_change_accountHolderCity() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setAccountHolderCity(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAccountHolderCity());
    }

    @Test
    public void it_should_change_accountHolderCountry() {
        // given
        String expected = "expectedString";
        PayoutInfo testedClass = this.getTestedClass();

        // when
        testedClass.setAccountHolderCountry(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAccountHolderCountry());
    }

    private PayoutInfo getTestedClass() {
        return new PayoutInfo();
    }
}
