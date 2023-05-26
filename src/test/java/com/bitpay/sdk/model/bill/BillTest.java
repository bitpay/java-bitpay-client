/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.bill;

import com.bitpay.sdk.exceptions.BitPayException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BillTest {


    @Test
    public void it_should_manipulate_currency() throws BitPayException {
        final String currency = "PLN";
        Bill testedClass = this.getTestedClass();

        testedClass.setCurrency(currency);
        Assertions.assertSame(currency, testedClass.getCurrency());
    }

    @Test
    public void it_should_not_allow_to_set_invalid_currency() {
        Assertions.assertThrows(BitPayException.class, () -> {
            final String invalidCurrency = "INVALID_CURRENCY";
            Bill testedClass = this.getTestedClass();

            testedClass.setCurrency(invalidCurrency);
        });
    }

    @Test
    public void it_should_manipulate_token() {
        final String token = "6EBQR37MgDJPfEiLY3jtRq7eTP2aodR5V5wmXyyZhru5FM5yF4RCGKYQtnT7nhwHjA";
        Bill testedClass = this.getTestedClass();

        testedClass.setToken(token);
        Assertions.assertSame(token, testedClass.getToken());
    }

    @Test
    public void it_should_manipulate_email() {
        final String email = "Some@email.com";
        Bill testedClass = this.getTestedClass();

        testedClass.setEmail(email);
        Assertions.assertSame(email, testedClass.getEmail());
    }

    @Test
    public void it_should_manipulate_items() {
        final List<Item> items = Collections.singletonList(Mockito.mock(Item.class));
        Bill testedClass = this.getTestedClass();

        testedClass.setItems(items);
        Assertions.assertSame(items, testedClass.getItems());
    }

    @Test
    public void it_should_manipulate_number() {
        final String number = "bill1234-EFGH";
        Bill testedClass = this.getTestedClass();

        testedClass.setNumber(number);
        Assertions.assertSame(number, testedClass.getNumber());
    }

    @Test
    public void it_should_manipulate_name() {
        final String name = "SomeString";
        Bill testedClass = this.getTestedClass();

        testedClass.setName(name);
        Assertions.assertSame(name, testedClass.getName());
    }

    @Test
    public void it_should_manipulate_address1() {
        final String address1 = "SomeString";
        Bill testedClass = this.getTestedClass();

        testedClass.setAddress1(address1);
        Assertions.assertSame(address1, testedClass.getAddress1());
    }

    @Test
    public void it_should_manipulate_address2() {
        final String address2 = "SomeString";
        Bill testedClass = this.getTestedClass();

        testedClass.setAddress2(address2);
        Assertions.assertSame(address2, testedClass.getAddress2());
    }

    @Test
    public void it_should_manipulate_city() {
        final String city = "SomeString";
        Bill testedClass = this.getTestedClass();

        testedClass.setCity(city);
        Assertions.assertSame(city, testedClass.getCity());
    }

    @Test
    public void it_should_manipulate_state() {
        final String state = "SomeString";
        Bill testedClass = this.getTestedClass();

        testedClass.setState(state);
        Assertions.assertSame(state, testedClass.getState());
    }

    @Test
    public void it_should_manipulate_zip() {
        final String zip = "SomeString";
        Bill testedClass = this.getTestedClass();

        testedClass.setZip(zip);
        Assertions.assertSame(zip, testedClass.getZip());
    }

    @Test
    public void it_should_manipulate_country() {
        final String country = "SomeString";
        Bill testedClass = this.getTestedClass();

        testedClass.setCountry(country);
        Assertions.assertSame(country, testedClass.getCountry());
    }

    @Test
    public void it_should_manipulate_cc() {
        final List<String> cc = Collections.singletonList("SomeString");
        Bill testedClass = this.getTestedClass();

        testedClass.setCc(cc);
        Assertions.assertSame(cc, testedClass.getCc());
    }

    @Test
    public void it_should_manipulate_phone() {
        final String phone = "555-123-456";
        Bill testedClass = this.getTestedClass();

        testedClass.setPhone(phone);
        Assertions.assertSame(phone, testedClass.getPhone());
    }

    @Test
    public void it_should_manipulate_dueDate() {
        final String dueDate = "2021-05-21T09:51:04.126Z";
        Bill testedClass = this.getTestedClass();

        testedClass.setDueDate(dueDate);
        Assertions.assertSame(dueDate, testedClass.getDueDate());
    }

    @Test
    public void it_should_manipulate_passProcessingFee() {
        final boolean passProcessingFee = true;
        Bill testedClass = this.getTestedClass();

        Assertions.assertSame(false, testedClass.getPassProcessingFee());
        testedClass.setPassProcessingFee(passProcessingFee);
        Assertions.assertSame(passProcessingFee, testedClass.getPassProcessingFee());
    }

    @Test
    public void it_should_manipulate_status() {
        final String status = "SomeString";
        Bill testedClass = this.getTestedClass();

        testedClass.setStatus(status);
        Assertions.assertSame(status, testedClass.getStatus());
    }

    @Test
    public void it_should_manipulate_url() {
        final String url = "SomeString";
        Bill testedClass = this.getTestedClass();

        testedClass.setUrl(url);
        Assertions.assertSame(url, testedClass.getUrl());
    }

    @Test
    public void it_should_manipulate_createDate() {
        final String createDate = "2021-05-21T09:51:04.126Z";
        Bill testedClass = this.getTestedClass();

        testedClass.setCreatedDate(createDate);
        Assertions.assertSame(createDate, testedClass.getCreatedDate());
    }

    @Test
    public void it_should_manipulate_id() {
        final String id = "3Zpmji8bRKxWJo2NJbWX5H";
        Bill testedClass = this.getTestedClass();

        testedClass.setId(id);
        Assertions.assertSame(id, testedClass.getId());
    }

    @Test
    public void it_should_manipulate_merchant() {
        final String merchant = "7HyKWn3d4xdhAMQYAEVxVq";
        Bill testedClass = this.getTestedClass();

        testedClass.setMerchant(merchant);
        Assertions.assertSame(merchant, testedClass.getMerchant());
    }

    private Bill getTestedClass() {
        return new Bill();
    }
}

