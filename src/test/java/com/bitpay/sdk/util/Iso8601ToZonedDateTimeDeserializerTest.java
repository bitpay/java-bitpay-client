package com.bitpay.sdk.util;

import com.bitpay.sdk.util.serializer.Iso8601ToZonedDateTimeDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class Iso8601ToZonedDateTimeDeserializerTest {

    @Test
    public void it_should_deserialize_date() throws IOException {
        // given
        Iso8601ToZonedDateTimeDeserializer testedClass = new Iso8601ToZonedDateTimeDeserializer();
        JsonParser jsonParser = Mockito.mock(JsonParser.class);
        JsonMapper jsonMapper = Mockito.mock(JsonMapper.class);
        TextNode textNode = Mockito.mock(TextNode.class);
        DeserializationContext deserializationContext = Mockito.mock(DeserializationContext.class);
        Mockito.when(jsonParser.getCodec()).thenReturn(jsonMapper);
        Mockito.when(jsonMapper.readTree(jsonParser)).thenReturn(textNode);
        Mockito.when(textNode.asText()).thenReturn("1977-05-24T18:07:03.232Z");

        // when
        ZonedDateTime result = testedClass.deserialize(jsonParser, deserializationContext);

        // then
        Assertions.assertEquals("1977-05-24T18:07:03.232Z", result.toString());
    }
}
