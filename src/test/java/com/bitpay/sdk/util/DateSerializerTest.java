/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DateSerializerTest {

    @Test
    public void it_should_serialize_date() throws IOException {
        // given
        DateSerializer testedClass = new DateSerializer();
        JsonGenerator jsonGenerator = Mockito.mock(JsonGenerator.class);
        SerializerProvider serializerProvider = Mockito.mock(SerializerProvider.class);
        Long value = 233345223232L;

        // when
        testedClass.serialize(value, jsonGenerator, serializerProvider);

        // then
        Mockito.verify(jsonGenerator, Mockito.times(1)).writeString("1977-05-24T18:07:03.232Z");
    }
}
