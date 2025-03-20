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
        Environment expectedValue = Environment.PROD;

        // when
        testedClass.setEnvironment(expectedValue);

        // then
        Assertions.assertEquals(expectedValue, testedClass.getEnvironment());
    }

    @Test
    public void it_should_get_env_config() {
        // given
        Config testedClass = getTestedClass();
        final Environment env = Environment.TEST;
        JsonNode config = Mockito.mock(JsonNode.class);
        JsonNode nestedNode = Mockito.mock(JsonNode.class);
        Mockito.when(config.path(env.toString())).thenReturn(nestedNode);

        // when
        testedClass.setEnvConfig(config);

        // then
        Assertions.assertEquals(nestedNode, testedClass.getEnvConfig(env));
    }

    @Test
    public void it_should_returns_environments() {
        Assertions.assertEquals("Test", Environment.TEST.toString());
        Assertions.assertEquals("Prod", Environment.PROD.toString());
    }

    @Test
    public void it_should_returns_env_urls() {
        Assertions.assertEquals("https://test.bitpay.com/", Config.TEST_URL);
        Assertions.assertEquals("https://bitpay.com/", Config.PROD_URL);
    }

    @Test
    public void it_should_returns_bitpay_api_version() {
        Assertions.assertEquals("2.0.0", Config.BITPAY_API_VERSION);
    }

    @Test
    public void it_should_returns_bitpay_plugin_info() {
        Assertions.assertTrue(Config.BITPAY_PLUGIN_INFO.contains("BitPay_Java_Client_v10.1.3"));
    }

    @Test
    public void it_should_returns_bitpay_api_frame() {
        Assertions.assertEquals("std", Config.BITPAY_API_FRAME);
    }

    @Test
    public void it_should_returns_bitpay_api_frame_version() {
        Assertions.assertEquals("1.0.0", Config.BITPAY_API_FRAME_VERSION);
    }

    private Config getTestedClass() {
        return new Config();
    }
}
