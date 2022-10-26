/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.util;

import com.bitpay.sdk.model.Payout.PayoutInstructionBtcSummary;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PayoutInstructionBtcSummaryDeserializerTest {

    @Test
    public void it_should_deserialize_btc() throws IOException {
        // given
        PayoutInstructionBtcSummaryDeserializer testedClass = new PayoutInstructionBtcSummaryDeserializer();
        JsonParser jsonParser = Mockito.mock(JsonParser.class);
        ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);
        ObjectNode node = Mockito.mock(ObjectNode.class);
        DoubleNode paid = Mockito.mock(DoubleNode.class);
        DoubleNode unpaid = Mockito.mock(DoubleNode.class);
        DeserializationContext deserializationContext = Mockito.mock(DeserializationContext.class);
        Mockito.when(jsonParser.getCodec()).thenReturn(objectMapper);
        Mockito.when(objectMapper.readTree(jsonParser)).thenReturn(node);
        Mockito.when(node.get("paid")).thenReturn(paid);
        Mockito.when(node.get("unpaid")).thenReturn(unpaid);
        Mockito.when(paid.asText()).thenReturn("2.0");
        Mockito.when(unpaid.asText()).thenReturn("4.0");

        // when
        PayoutInstructionBtcSummary result = testedClass.deserialize(jsonParser, deserializationContext);

        // then
        Assertions.assertEquals(2.0, result.getPaid());
        Assertions.assertEquals(4.0, result.getUnpaid());
    }
}
