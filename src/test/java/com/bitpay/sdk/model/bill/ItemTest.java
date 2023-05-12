/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk.model.bill;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ItemTest {

    @Test
    public void it_should_manipulate_id() {
        String id = "1";

        Item testedClass = this.getTestedClass();
        testedClass.setId(id);

        Assertions.assertSame(id, testedClass.getId());
    }

    @Test
    public void it_should_manipulate_description() {
        String description = "someDescription";

        Item testedClass = this.getTestedClass();
        testedClass.setDescription(description);

        Assertions.assertSame(description, testedClass.getDescription());
    }

    @Test
    public void it_should_manipulate_price() {
        Double price = 2.00;

        Item testedClass = this.getTestedClass();
        testedClass.setPrice(price);

        Assertions.assertSame(price, testedClass.getPrice());
    }

    @Test
    public void it_should_manipulate_quantity() {
        Integer quantity = 4;

        Item testedClass = this.getTestedClass();
        testedClass.setQuantity(quantity);

        Assertions.assertSame(quantity, testedClass.getQuantity());
    }

    private Item getTestedClass() {
        return new Item();
    }
}
