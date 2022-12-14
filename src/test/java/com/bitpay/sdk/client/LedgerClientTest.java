/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.Ledger.Ledger;
import com.bitpay.sdk.util.AccessTokens;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LedgerClientTest extends AbstractClientTest {

    @Test
    public void it_should_get_ledgers() throws BitPayException {
        // given
        AccessTokens accessTokens = this.getAccessTokens();
        this.addServerJsonResponse(
            "/ledgers?token=" + MERCHANT_TOKEN,
            "GET",
            null,
            getPreparedJsonDataFromFile("getLedgersResponse.json")
        );

        // when
        List<Ledger> result = this.getTestedClass(accessTokens).getLedgers();

        // then
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("EUR", result.get(0).getCurrency());
        Assertions.assertEquals("USD", result.get(1).getCurrency());
        Assertions.assertEquals("BTC", result.get(2).getCurrency());
        Assertions.assertEquals(0.0, result.get(0).getBalance());
        Assertions.assertEquals(2389.82, result.get(1).getBalance());
        Assertions.assertEquals(0.000287, result.get(2).getBalance());
    }

    private LedgerClient getTestedClass(AccessTokens accessTokens) {
        return new LedgerClient(this.getBitPayClient(), accessTokens);
    }
}
