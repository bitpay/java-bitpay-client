/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.Ledger.Ledger;
import com.bitpay.sdk.model.Ledger.LedgerEntry;
import com.bitpay.sdk.util.TokenContainer;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LedgerClientTest extends AbstractClientTest {

    @Test
    public void it_should_get_ledgers() throws BitPayException {
        // given
        TokenContainer accessTokens = this.getAccessTokens();
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

    @Test
    public void it_should_get_ledger_entries() throws BitPayException {
        // given
        TokenContainer accessTokens = this.getAccessTokens();
        this.addServerJsonResponse(
            "/ledgers/USD?token=" + MERCHANT_TOKEN + "&startDate=2021-5-10&endDate=2021-5-31",
            "GET",
            null,
            getPreparedJsonDataFromFile("getLedgerEntriesResponse.json")
        );

        // when
        List<LedgerEntry> result = this.getTestedClass(accessTokens).getEntries("USD", "2021-5-10", "2021-5-31");
        LedgerEntry secondEntry = result.get(1);

        // then
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("1023", secondEntry.getCode());
        Assertions.assertEquals("-8000000", secondEntry.getAmount());
        Assertions.assertEquals("Invoice Fee", secondEntry.getDescription());
        Assertions.assertEquals("2021-05-10T20:08:52.919Z", secondEntry.getTimestamp());
        Assertions.assertEquals("Hpqc63wvE1ZjzeeH4kEycF", secondEntry.getInvoiceId());
        Assertions.assertEquals("2630 Hegal Place", secondEntry.getBuyer().getAddress1());
        Assertions.assertEquals(10, secondEntry.getInvoiceAmount());
        Assertions.assertEquals("USD", secondEntry.getInvoiceCurrency());
        Assertions.assertEquals("BCH", secondEntry.getTransactionCurrency());
        Assertions.assertEquals("XCkhgHKP2pSme4qszMpM3B", secondEntry.getId());
    }

    private LedgerClient getTestedClass(TokenContainer accessTokens) {
        return LedgerClient.getInstance(this.getBitPayClient(), accessTokens);
    }
}
