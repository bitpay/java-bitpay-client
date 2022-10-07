/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvoiceTransactionDetailsTest {

    @Test
    public void it_should_manipulate_id_amount() {
        // given
        InvoiceTransactionDetails testedClass = this.getTestedClass();
        double expected = 12.34;

        // when
        testedClass.setAmount(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAmount());
    }

    @Test
    public void it_should_manipulate_id_description() {
        // given
        InvoiceTransactionDetails testedClass = this.getTestedClass();
        String expected = "SomeDescription";

        // when
        testedClass.setDescription(expected);

        // then
        Assertions.assertSame(expected, testedClass.getDescription());
    }

    @Test
    public void it_should_manipulate_id_isFee() {
        // given
        InvoiceTransactionDetails testedClass = this.getTestedClass();
        Boolean expected = true;

        // when
        testedClass.setIsFee(expected);

        // then
        Assertions.assertSame(expected, testedClass.getIsFee());
    }

    private InvoiceTransactionDetails getTestedClass() {
        return new InvoiceTransactionDetails(1.00, "ignore", false);
    }
}
