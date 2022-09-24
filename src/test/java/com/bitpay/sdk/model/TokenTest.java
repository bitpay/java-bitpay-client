/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk.model;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TokenTest {

    @Test
    public void it_should_get_guid() {
        // given
        Token testedClass = getTestedClass();
        String expectedValue = "expectedValue";

        // when
        testedClass.setGuid(expectedValue);

        // then
        Assertions.assertEquals(expectedValue, testedClass.getGuid());
    }

    @Test
    public void it_should_get_id() {
        // given
        Token testedClass = getTestedClass();
        String expectedValue = "expectedValue";

        // when
        testedClass.setId(expectedValue);

        // then
        Assertions.assertEquals(expectedValue, testedClass.getId());
    }

    @Test
    public void it_should_get_pairingCode() {
        // given
        Token testedClass = getTestedClass();
        String expectedValue = "expectedValue";

        // when
        testedClass.setPairingCode(expectedValue);

        // then
        Assertions.assertEquals(expectedValue, testedClass.getPairingCode());
    }

    @Test
    public void it_should_get_pairingExpiration() {
        // given
        Token testedClass = getTestedClass();
        long expectedValue = 10L;

        // when
        testedClass.setPairingExpiration(expectedValue);

        // then
        Assertions.assertEquals(expectedValue, testedClass.getPairingExpiration());
    }

    @Test
    public void it_should_get_facade() {
        // given
        Token testedClass = getTestedClass();
        String expectedValue = "expectedValue";

        // when
        testedClass.setFacade(expectedValue);

        // then
        Assertions.assertEquals(expectedValue, testedClass.getFacade());
    }

    @Test
    public void it_should_get_label() {
        // given
        Token testedClass = getTestedClass();
        String expectedValue = "expectedValue";

        // when
        testedClass.setLabel(expectedValue);

        // then
        Assertions.assertEquals(expectedValue, testedClass.getLabel());
    }

    @Test
    public void it_should_get_count() {
        // given
        Token testedClass = getTestedClass();
        int expectedValue = 5;

        // when
        testedClass.setCount(expectedValue);

        // then
        Assertions.assertEquals(expectedValue, testedClass.getCount());
    }

    @Test
    public void it_should_get_policies() {
        // given
        Token testedClass = getTestedClass();
        List<Policy> expectedValue = Arrays.asList(Mockito.mock(Policy.class));

        // when
        testedClass.setPolicies(expectedValue);

        Assertions.assertEquals(expectedValue, testedClass.getPolicies());
    }

    @Test
    public void it_should_get_resource() {
        // given
        Token testedClass = getTestedClass();
        String expectedValue = "expectedValue";

        // when
        testedClass.setResource(expectedValue);

        Assertions.assertEquals(expectedValue, testedClass.getResource());
    }

    @Test
    public void it_should_get_value() {
        // given
        Token testedClass = getTestedClass();
        String expectedValue = "expectedValue";

        // when
        testedClass.setValue(expectedValue);

        Assertions.assertEquals(expectedValue, testedClass.getValue());
    }

    @Test
    public void it_should_get_dateCreated() {
        // given
        Token testedClass = getTestedClass();
        long expectedValue = 10L;

        // when
        testedClass.setDateCreated(expectedValue);

        Assertions.assertEquals(expectedValue, testedClass.getDateCreated());
    }

    private Token getTestedClass() {
        return new Token();
    }
}
