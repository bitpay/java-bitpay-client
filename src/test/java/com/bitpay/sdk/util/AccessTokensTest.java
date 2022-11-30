/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.util;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.Facade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccessTokensTest {

    @Test
    public void it_should_throws_exception_for_non_existing_access_token() {
        Assertions.assertThrows(BitPayException.class, () -> {
            AccessTokens testedClass = this.getTestedClass();
            testedClass.getAccessToken("nonExisting");
        });
    }

    @Test
    public void it_should_test_getAccessToken() throws BitPayException {
        // given
        AccessTokens testedClass = this.getTestedClass();
        String key = "someKey";
        String value = "someValue";

        // when
        testedClass.put(key, value);

        // then
        Assertions.assertEquals(value, testedClass.getAccessToken(key));
    }

    @Test
    public void it_should_test_tokenExist() {
        // given
        AccessTokens testedClass = this.getTestedClass();
        String value = "someValue";

        // when
        testedClass.put(Facade.PAYOUT.toString(), value);

        // then
        Assertions.assertFalse(testedClass.tokenExists("nonExistingFacade"));
        Assertions.assertTrue(testedClass.tokenExists(Facade.PAYOUT.toString()));
        Assertions.assertTrue(testedClass.tokenExists(Facade.PAYOUT));
    }

    private AccessTokens getTestedClass() {
        return new AccessTokens();
    }
}
