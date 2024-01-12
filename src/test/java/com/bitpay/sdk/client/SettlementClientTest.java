/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.settlement.Settlement;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SettlementClientTest extends AbstractClientTest {

    @Test
    public void it_should_get_settlements() throws BitPayException {
        // given
        this.addServerJsonResponse(
            "/settlements?token=someMerchantToken&startDate=2021-5-10&endDate=2021-5-12&status=processing",
            "GET",
            null,
            getPreparedJsonDataFromFile("getSettlementsResponse.json")
        );

        // when
        List<Settlement> result =
            this.getTestedClass().getSettlements(null, "2021-5-10", "2021-5-12", "processing", null, null);

        // then
        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals("KBkdURgmE3Lsy9VTnavZHX", result.get(0).getId());
        Assertions.assertEquals("YJCgTf3jrXHkUVzLQ7y4eg", result.get(0).getAccountId());
        Assertions.assertEquals("EUR", result.get(0).getCurrency());
        Assertions.assertEquals("Test Organization", result.get(0).getPayoutInfo().getName());
        Assertions.assertEquals("NL85ABNA0000000000", result.get(0).getPayoutInfo().getAccount());
        Assertions.assertEquals("Corporate account", result.get(0).getPayoutInfo().getLabel());
        Assertions.assertEquals("Netherlands", result.get(0).getPayoutInfo().getBankCountry());
        Assertions.assertEquals("Test", result.get(0).getPayoutInfo().getBank());
        Assertions.assertEquals("RABONL2U", result.get(0).getPayoutInfo().getSwift());
        Assertions.assertEquals("processing", result.get(0).getStatus());
        Assertions.assertEquals("2021-05-10T09:05:00.176Z", result.get(0).getDateCreated().toString());
        Assertions.assertEquals("2021-05-10T11:52:29.681Z", result.get(0).getDateExecuted().toString());
        Assertions.assertNull(result.get(0).getDateCompleted());
        Assertions.assertEquals("2021-05-09T09:00Z", result.get(0).getOpeningDate().toString());
        Assertions.assertEquals("2021-05-10T09:00Z", result.get(0).getClosingDate().toString());
        Assertions.assertEquals(1.27f, result.get(0).getOpeningBalance());
        Assertions.assertEquals(20.82f, result.get(0).getLedgerEntriesSum());
        Assertions.assertEquals(0, result.get(0).getWithHoldings().size());
        Assertions.assertEquals(0,result.get(0).getWithHoldingsSum());
        Assertions.assertEquals(22.09f, result.get(0).getTotalAmount());
        Assertions.assertNull(result.get(0).getLedgerEntries());
        Assertions.assertEquals("2gBtViSiBWSEJGo1LfaMFHoaBRzE2jek2VitKAYeenj2SRiTVSCgRvs1WTN8w4w8Lc",
            result.get(0).getToken());

        Assertions.assertEquals("RPWTabW8urd3xWv2To989v", result.get(1).getId());
        Assertions.assertEquals("YJCgTf3jrXHkUVzLQ7y4eg", result.get(1).getAccountId());
        Assertions.assertEquals("EUR", result.get(1).getCurrency());
        Assertions.assertEquals("Test Organization", result.get(1).getPayoutInfo().getName());
        Assertions.assertEquals("NL85ABNA0000000000", result.get(1).getPayoutInfo().getAccount());
        Assertions.assertEquals("Corporate account", result.get(1).getPayoutInfo().getLabel());
        Assertions.assertEquals("Netherlands", result.get(1).getPayoutInfo().getBankCountry());
        Assertions.assertEquals("Test", result.get(1).getPayoutInfo().getBank());
        Assertions.assertEquals("RABONL2U", result.get(1).getPayoutInfo().getSwift());
        Assertions.assertEquals("processing", result.get(1).getStatus());
        Assertions.assertEquals("2021-05-11T09:05:00.176Z", result.get(1).getDateCreated().toString());
        Assertions.assertEquals("2021-05-11T11:52:29.681Z", result.get(1).getDateExecuted().toString());
        Assertions.assertNull(result.get(1).getDateCompleted());
        Assertions.assertEquals("2021-05-10T09:00Z", result.get(1).getOpeningDate().toString());
        Assertions.assertEquals("2021-05-11T09:00Z", result.get(1).getClosingDate().toString());
        Assertions.assertEquals(23.27f, result.get(1).getOpeningBalance());
        Assertions.assertEquals(20.82f, result.get(1).getLedgerEntriesSum());
        Assertions.assertEquals("Pending Refunds", result.get(1).getWithHoldings().get(0).getDescription());
        Assertions.assertEquals(8.21f, result.get(1).getWithHoldingsSum());
        Assertions.assertEquals(35.88f, result.get(1).getTotalAmount());
        Assertions.assertNull(result.get(1).getLedgerEntries());
        Assertions.assertEquals("2gBtViSiBWSEJitKAYSCgRvs1WTN8w4Go1Leenj2SRiTVFHoaBRzE2jek2VfaMw8Lc",
            result.get(1).getToken());
    }

    @Test
    public void it_should_get_settlement() throws BitPayException {
        // given
        this.addServerJsonResponse(
            //
            "/settlements/DNFnN3fFjjzLn6if5bdGJC?token=someMerchantToken",
            "GET",
            null,
            getPreparedJsonDataFromFile("getSettlementResponse.json")
        );

        // when
        Settlement result = this.getTestedClass().get("DNFnN3fFjjzLn6if5bdGJC");

        // then
        Assertions.assertEquals("RPWTabW8urd3xWv2To989v", result.getId());
        Assertions.assertEquals("YJCgTf3jrXHkUVzLQ7y4eg", result.getAccountId());
        Assertions.assertEquals("EUR", result.getCurrency());
        Assertions.assertEquals("Test Organization", result.getPayoutInfo().getName());
        Assertions.assertEquals("NL85ABNA0000000000", result.getPayoutInfo().getAccount());
        Assertions.assertEquals("Corporate account", result.getPayoutInfo().getLabel());
        Assertions.assertEquals("Netherlands", result.getPayoutInfo().getBankCountry());
        Assertions.assertEquals("Test", result.getPayoutInfo().getBank());
        Assertions.assertEquals("RABONL2U", result.getPayoutInfo().getSwift());
        Assertions.assertEquals("processing", result.getStatus());
        Assertions.assertEquals("2021-05-11T09:05:00.176Z", result.getDateCreated().toString());
        Assertions.assertEquals("2021-05-11T11:52:29.681Z", result.getDateExecuted().toString());
        Assertions.assertNull(result.getDateCompleted());
        Assertions.assertEquals("2021-05-10T09:00Z", result.getOpeningDate().toString());
        Assertions.assertEquals("2021-05-11T09:00Z", result.getClosingDate().toString());
        Assertions.assertEquals(23.27f, result.getOpeningBalance());
        Assertions.assertEquals(20.82f, result.getLedgerEntriesSum());
        Assertions.assertEquals(8.21f, result.getWithHoldings().get(0).getAmount());
        Assertions.assertEquals(8.21f, result.getWithHoldingsSum());
        Assertions.assertEquals(35.88f, result.getTotalAmount());
        Assertions.assertNull(result.getLedgerEntries());
        Assertions
            .assertEquals("2GrR6GDeYxUFYM9sDKViy6nFFTy4Rjvm1SYdLBjK46jkeJdgUTRccRfhtwkhNcuZky", result.getToken());
    }

    @Test
    public void it_should_get_settlement_reconciliation_report() throws BitPayGenericException, BitPayApiException {
        // given
        this.addServerJsonResponse(
            //
            "/settlements/RvNuCTMAkURKimwgvSVEMP/reconciliationreport?token=5T1T5yGDEtFDYe8jEVBSYLHKewPYXZrDLvZxtXBzn69fBbZYitYQYH4BFYFvvaVU7D",
            "GET",
            null,
            getPreparedJsonDataFromFile("getSettlementReconciliationReportResponse.json")
        );

        // when
        Settlement result = this.getTestedClass().getSettlementReconciliationReport("RvNuCTMAkURKimwgvSVEMP",
            "5T1T5yGDEtFDYe8jEVBSYLHKewPYXZrDLvZxtXBzn69fBbZYitYQYH4BFYFvvaVU7D");

        // then
        Assertions.assertEquals("RvNuCTMAkURKimwgvSVEMP", result.getId());
        Assertions.assertEquals("YJCgTf3jrXHkUVzLQ7y4eg", result.getAccountId());
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("Test", result.getPayoutInfo().getLabel());
        Assertions.assertEquals("Netherlands", result.getPayoutInfo().getBankCountry());
        Assertions.assertEquals("RABONL2U", result.getPayoutInfo().getSwift());
        Assertions.assertEquals("Test", result.getPayoutInfo().getBankName());
        Assertions.assertEquals("test", result.getPayoutInfo().getBankAddress());
        Assertions.assertEquals("test", result.getPayoutInfo().getBankAddress2());
        Assertions.assertEquals("NL85ABNA0000000000", result.getPayoutInfo().getIban());
        Assertions.assertEquals("United States", result.getPayoutInfo().getAccountHolderCountry());
        Assertions.assertEquals("processing", result.getStatus());
        Assertions.assertEquals("2018-08-23T20:45:22.742Z", result.getDateCreated().toString());
        Assertions.assertEquals("2018-08-23T20:47:06.912Z", result.getDateExecuted().toString());
        Assertions.assertNull(result.getDateCompleted());
        Assertions.assertEquals("2018-08-01T13:00Z", result.getOpeningDate().toString());
        Assertions.assertEquals("2018-08-23T13:00Z", result.getClosingDate().toString());
        Assertions.assertEquals(23.13f, result.getOpeningBalance());
        Assertions.assertEquals(2956.77f, result.getLedgerEntriesSum());
        Assertions.assertEquals(1, result.getWithHoldings().size());
        Assertions.assertEquals(590.08f, result.getWithHoldingsSum());
        Assertions.assertEquals(2389.82f, result.getTotalAmount());

        Assertions.assertEquals(42, result.getLedgerEntries().size());
        Assertions.assertEquals(1000, result.getLedgerEntries().get(0).getCode());
        Assertions.assertEquals("E1pJQNsHP2oHuMo2fagpe6", result.getLedgerEntries().get(0).getInvoiceId());
        Assertions.assertEquals(5.83f, result.getLedgerEntries().get(0).getAmount());
        Assertions.assertEquals("2018-08-01T20:16:03.742Z", result.getLedgerEntries().get(0).getTimestamp().toString());
        Assertions.assertEquals("Test invoice BCH", result.getLedgerEntries().get(0).getDescription());
        Assertions.assertEquals("Test invoice BCH", result.getLedgerEntries().get(0).getInvoiceData().getOrderId());
        Assertions.assertEquals(5.0f, result.getLedgerEntries().get(0).getInvoiceData().getPrice());
        Assertions.assertEquals("EUR", result.getLedgerEntries().get(0).getInvoiceData().getCurrency());
        Assertions.assertEquals("BCH", result.getLedgerEntries().get(0).getInvoiceData().getTransactionCurrency());
        Assertions
            .assertEquals(100.0, result.getLedgerEntries().get(0).getInvoiceData().getPayoutPercentage().get("USD"));
    }

    private SettlementClient getTestedClass() {
        return SettlementClient.getInstance(this.getBitPayClient(), this.getAccessTokens());
    }
}
