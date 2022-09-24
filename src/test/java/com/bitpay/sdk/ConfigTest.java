/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ConfigTest {

    @Test
    public void it_should_get_environment() {
        // given
        Config testedClass = getTestedClass();
        String expectedValue = "expectedValue";

        // when
        testedClass.setEnvironment(expectedValue);

        // then
        Assertions.assertEquals(expectedValue, testedClass.getEnvironment());
    }

    @Test
    public void it_should_get_env_config() {
        // given
        Config testedClass = getTestedClass();
        String expectedKey = "expectedKey";
        JsonNode config = Mockito.mock(JsonNode.class);
        JsonNode nestedNode = Mockito.mock(JsonNode.class);
        Mockito.when(config.path(expectedKey)).thenReturn(nestedNode);

        // when
        testedClass.setEnvConfig(config);

        // then
        Assertions.assertEquals(nestedNode, testedClass.getEnvConfig(expectedKey));
    }

    private Config getTestedClass() {
        return new Config();
    }
}
