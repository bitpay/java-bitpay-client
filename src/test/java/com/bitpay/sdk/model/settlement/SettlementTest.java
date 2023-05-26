/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.settlement;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SettlementTest {

    @Test
    public void it_should_change_id() {
        // given
        String expected = "expectedString";
        Settlement testedClass = this.getTestedClass();

        // when
        testedClass.setId(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getId());
    }

    @Test
    public void it_should_change_accountId() {
        // given
        String expected = "expectedString";
        Settlement testedClass = this.getTestedClass();

        // when
        testedClass.setAccountId(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAccountId());
    }

    @Test
    public void it_should_change_currency() {
        // given
        String expected = "expectedString";
        Settlement testedClass = this.getTestedClass();

        // when
        testedClass.setCurrency(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getCurrency());
    }

    @Test
    public void it_should_change_payoutInfo() {
        // given
        PayoutInfo expected = Mockito.mock(PayoutInfo.class);
        Settlement testedClass = this.getTestedClass();

        // when
        testedClass.setPayoutInfo(expected);

        // then
        Assertions.assertSame(expected, testedClass.getPayoutInfo());
    }

    @Test
    public void it_should_change_status() {
        // given
        String expected = "expectedString";
        Settlement testedClass = this.getTestedClass();

        // when
        testedClass.setStatus(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getStatus());
    }

    @Test
    public void it_should_change_dateCreated() {
        // given
        Long expected = 12L;
        Settlement testedClass = this.getTestedClass();

        // when
        testedClass.setDateCreated(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getDateCreated());
    }

    @Test
    public void it_should_change_dateExecuted() {
        // given
        Long expected = 12L;
        Settlement testedClass = this.getTestedClass();

        // when
        testedClass.setDateExecuted(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getDateExecuted());
    }

    @Test
    public void it_should_change_dateCompleted() {
        // given
        Long expected = 12L;
        Settlement testedClass = this.getTestedClass();

        // when
        testedClass.setDateCompleted(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getDateCompleted());
    }

    @Test
    public void it_should_change_openingDate() {
        // given
        Long expected = 12L;
        Settlement testedClass = this.getTestedClass();

        // when
        testedClass.setOpeningDate(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getOpeningDate());
    }

    @Test
    public void it_should_change_closingDate() {
        // given
        Long expected = 12L;
        Settlement testedClass = this.getTestedClass();

        // when
        testedClass.setClosingDate(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getClosingDate());
    }

    @Test
    public void it_should_change_openingBalance() {
        // given
        Float expected = 12.34F;
        Settlement testedClass = this.getTestedClass();

        // when
        testedClass.setOpeningBalance(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getOpeningBalance());
    }

    @Test
    public void it_should_change_ledgerEntriesSum() {
        // given
        Float expected = 12.34F;
        Settlement testedClass = this.getTestedClass();

        // when
        testedClass.setLedgerEntriesSum(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getLedgerEntriesSum());
    }

    @Test
    public void it_should_change_withHoldings() {
        // given
        List<WithHoldings> expected = Collections.singletonList(Mockito.mock(WithHoldings.class));
        Settlement testedClass = this.getTestedClass();

        // when
        testedClass.setWithHoldings(expected);

        // then
        Assertions.assertSame(expected, testedClass.getWithHoldings());
    }

    @Test
    public void it_should_change_withHoldingsSum() {
        // given
        Float expected = 12.34F;
        Settlement testedClass = this.getTestedClass();

        // when
        testedClass.setWithHoldingsSum(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getWithHoldingsSum());
    }

    @Test
    public void it_should_change_totalAmount() {
        // given
        Float expected = 12.34F;
        Settlement testedClass = this.getTestedClass();

        // when
        testedClass.setTotalAmount(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getTotalAmount());
    }

    @Test
    public void it_should_change_ledgerEntries() {
        // given
        List<SettlementLedgerEntry> expected = Collections.singletonList(Mockito.mock(SettlementLedgerEntry.class));
        Settlement testedClass = this.getTestedClass();

        // when
        testedClass.setLedgerEntries(expected);

        // then
        Assertions.assertSame(expected, testedClass.getLedgerEntries());
    }

    @Test
    public void it_should_change_token() {
        // given
        String expected = "expectedString";
        Settlement testedClass = this.getTestedClass();

        // when
        testedClass.setToken(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getToken());
    }

    private Settlement getTestedClass() {
        return new Settlement();
    }
}
