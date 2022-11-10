/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.util;

import com.bitpay.sdk.Config;
import com.bitpay.sdk.exceptions.BitPayException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccessTokenCacheTest {

    @Mock
    private Config configuration;

    @Test
    public void it_should_throws_exception_for_non_existing_access_token() {
        Assertions.assertThrows(BitPayException.class, () -> {
            AccessTokenCache testedClass = this.getTestedClass();
            testedClass.getAccessToken("nonExisting");
        });
    }

    @Test
    public void it_should_test_getAccessToken() throws BitPayException {
        // given
        AccessTokenCache testedClass = this.getTestedClass();
        String key = "someKey";
        String value = "someValue";

        // when
        testedClass.put(key, value);

        // then
        Assertions.assertEquals(value, testedClass.getAccessToken(key));
    }

    @Test
    public void it_should_test_getTokenByFacade() {
        // given
        AccessTokenCache testedClass = this.getTestedClass();
        String key = "someKey";
        String value = "someValue";

        // when
        testedClass.put(key, value);

        // then
        Assertions.assertEquals("", testedClass.getTokenByFacade("nonExistingFacade"));
        Assertions.assertEquals(value, testedClass.getTokenByFacade(key));
    }

    @Test
    public void it_should_test_tokenExist() {
        // given
        AccessTokenCache testedClass = this.getTestedClass();
        String key = "someKey";
        String value = "someValue";

        // when
        testedClass.put(key, value);

        // then
        Assertions.assertFalse(testedClass.tokenExist("nonExistingFacade"));
        Assertions.assertTrue(testedClass.tokenExist(key));
    }

    @Test
    public void it_should_test_cacheToken() throws BitPayException {
        // given
        final String env = "test";
        ObjectNode node = Mockito.mock(ObjectNode.class);
        Mockito.when(this.configuration.getEnvironment()).thenReturn(env);
        Mockito.when(this.configuration.getEnvConfig(env)).thenReturn(node);
        AccessTokenCache testedClass = this.getTestedClass();
        String key = "someKey";
        String value = "someValue";
        testedClass.put(key, "anotherValue");

        // when
        testedClass.cacheToken(key, value);

        // then
        Assertions.assertEquals(value, testedClass.getTokenByFacade(key));
        Mockito.verify(this.configuration, Mockito.times(1)).getEnvConfig(env);
        Mockito.verify(node, Mockito.times(1)).put(
            ArgumentMatchers.eq("ApiTokens"),
            ArgumentMatchers.any(ObjectNode.class)
        );
    }

    @Test
    public void it_should_test_clear() {
        // given
        AccessTokenCache testedClass = this.getTestedClass();
        String key = "someKey";
        String value = "someValue";
        testedClass.put(key, value);

        // when
        testedClass.clear();

        // then
        String result = testedClass.getTokenByFacade(key);
        Assertions.assertEquals("", result);
    }

    private AccessTokenCache getTestedClass() {
        return new AccessTokenCache(this.configuration);
    }
}
