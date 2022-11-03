/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DateDeserializerTest {

    @Test
    public void it_should_deserialize_date() throws IOException {
        // given
        System.setProperty("user.timezone", "UTC");
        DateDeserializer testedClass = new DateDeserializer();
        JsonParser jsonParser = Mockito.mock(JsonParser.class);
        DeserializationContext deserializationContext = Mockito.mock(DeserializationContext.class);
        Mockito.when(jsonParser.getText()).thenReturn("1977-05-24T18:07:03.232Z");

        // when
        Long result = testedClass.deserialize(jsonParser, deserializationContext);

        // then
        Assertions.assertEquals(233345223232L, result);
    }
}
