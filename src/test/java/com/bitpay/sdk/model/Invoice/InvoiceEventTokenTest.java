/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvoiceEventTokenTest {

    @Test
    public void it_should_return_token() {
        // given
        InvoiceEventToken testedClass = this.getTestedClass();

        // then
        Assertions.assertEquals("4MuqDPt93i9Xbf8SnAPniwbGeNLW8A3ScgAmukFMgFUFRqTLuuhVdAFfePPysVqL2P", testedClass.getToken());
    }

    @Test
    public void it_should_return_events() {
        // given
        InvoiceEventToken testedClass = this.getTestedClass();

        // then
        Assertions.assertEquals(Arrays.asList("payment", "confirmation"), testedClass.getEvents());
    }

    @Test
    public void it_should_return_actions() {
        // given
        InvoiceEventToken testedClass = this.getTestedClass();

        // then
        Assertions.assertEquals(Arrays.asList("subscribe", "unsubscribe"), testedClass.getActions());
    }

    private InvoiceEventToken getTestedClass() {
        return new InvoiceEventToken(
            "4MuqDPt93i9Xbf8SnAPniwbGeNLW8A3ScgAmukFMgFUFRqTLuuhVdAFfePPysVqL2P",
            Arrays.asList("payment", "confirmation"),
            Arrays.asList("subscribe", "unsubscribe")
        );
    }
}
