package com.bitpay.sdk.util;

import com.bitpay.sdk.util.serializer.ZonedDateTimeToIso8601Serializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ZonedDateTimeToIso8601SerializerTest {

    @Test
    public void it_should_serialize_date() throws IOException {
        // given
        ZonedDateTimeToIso8601Serializer testedClass = new ZonedDateTimeToIso8601Serializer();
        JsonGenerator jsonGenerator = Mockito.mock(JsonGenerator.class);
        SerializerProvider serializerProvider = Mockito.mock(SerializerProvider.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        ZonedDateTime value = ZonedDateTime.parse("2021-05-21T09:48:02.373Z", formatter);

        // when
        testedClass.serialize(value, jsonGenerator, serializerProvider);

        // then
        Mockito.verify(jsonGenerator, Mockito.times(1)).writeString("2021-05-21T09:48:02.37Z");
    }
}
