/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CurrencyClientTest extends AbstractClientTest {

    @Test
    public void it_should_get_currency_info() throws BitPayException {
        // given
        CurrencyClient client = this.getCurrencyClient();
        this.addServerJsonResponse(
            "/currencies",
            "GET",
            null,
            getPreparedJsonDataFromFile("getCurrenciesResponse.json")
        );

        // when
        Map<String, Object> result = client.getCurrencyInfo("USD");

        // then
        Assertions.assertEquals("USD", result.get("code"));
        Assertions.assertEquals("$", result.get("symbol"));
        Assertions.assertEquals(2, result.get("precision"));
        Assertions.assertEquals("US Dollar", result.get("name"));
        Assertions.assertEquals("US Dollars", result.get("plural"));
        Assertions.assertEquals("usd bucks", result.get("alts"));
        Assertions.assertEquals(0.01, result.get("minimum"));
        Assertions.assertEquals(false, result.get("sanctioned"));
        Assertions.assertEquals(2, result.get("decimals"));
    }

    private CurrencyClient getCurrencyClient() throws BitPayException {
        return new CurrencyClient(this.getBitPayClient());
    }
}
