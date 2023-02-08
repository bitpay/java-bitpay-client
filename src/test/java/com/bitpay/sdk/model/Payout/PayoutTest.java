/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Payout;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.ModelConfiguration;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PayoutTest {

    @Test
    public void it_should_set_default_values_on_create() {
        // given
        Payout testedClass = this.getTestedClass();

        // then
        Assertions.assertEquals(0.0, testedClass.getAmount());
        Assertions.assertEquals("USD", testedClass.getCurrency());
        Assertions.assertEquals(ModelConfiguration.DEFAULT_NON_SENT_VALUE, testedClass.getNotificationEmail());
        Assertions.assertEquals(ModelConfiguration.DEFAULT_NON_SENT_VALUE, testedClass.getNotificationURL());
    }

    @Test
    public void it_should_change_token() {
        // given
        String expected = "expectedString";
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setToken(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getToken());
    }

    @Test
    public void it_should_change_amount() {
        // given
        Double expected = 12.34;
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setAmount(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAmount());
    }

    @Test
    public void it_should_not_allow_to_set_invalid_currency() {
        Assertions.assertThrows(BitPayException.class, () -> {
            final String invalidCurrency = "INVALID_CURRENCY";
            Payout testedClass = this.getTestedClass();

            testedClass.setCurrency(invalidCurrency);
        });
    }

    @Test
    public void it_should_change_currency() throws BitPayException {
        // given
        String expected = "USD";
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setCurrency(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getCurrency());
    }

    @Test
    public void it_should_change_effectiveDate() {
        // given
        Long expected = 123L;
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setEffectiveDate(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getEffectiveDate());
    }

    @Test
    public void it_should_change_reference() {
        // given
        String expected = "expectedString";
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setReference(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getReference());
    }

    @Test
    public void it_should_change_notificationEmail() {
        // given
        String expected = "expectedString";
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setNotificationEmail(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getNotificationEmail());
    }

    @Test
    public void it_should_change_notificationURL() {
        // given
        String expected = "expectedString";
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setNotificationURL(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getNotificationURL());
    }

    @Test
    public void it_should_not_allow_to_set_invalid_ledger_currency() {
        Assertions.assertThrows(BitPayException.class, () -> {
            final String invalidLedgerCurrency = "INVALID_LEDGER";
            Payout testedClass = this.getTestedClass();

            testedClass.setLedgerCurrency(invalidLedgerCurrency);
        });
    }

    @Test
    public void it_should_change_ledgerCurrency() throws BitPayException {
        // given
        String expected = "USD";
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setLedgerCurrency(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getLedgerCurrency());
    }

    @Test
    public void it_should_change_id() {
        // given
        String expected = "expectedString";
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setId(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getId());
    }

    @Test
    public void it_should_change_shopperId() {
        // given
        String expected = "expectedString";
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setShopperId(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getShopperId());
    }

    @Test
    public void it_should_change_recipientId() {
        // given
        String expected = "expectedString";
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setRecipientId(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getRecipientId());
    }

    @Test
    public void it_should_change_exchangeRates() {
        // given
        PayoutInstruction expected = Mockito.mock(PayoutInstruction.class);
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setExchangeRates(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getExchangeRates());
    }

    @Test
    public void it_should_change_account() {
        // given
        String expected = "expectedString";
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setAccount(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAccount());
    }

    @Test
    public void it_should_change_email() {
        // given
        String expected = "expectedString";
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setEmail(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getEmail());
    }

    @Test
    public void it_should_change_label() {
        // given
        String expected = "expectedString";
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setLabel(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getLabel());
    }

    @Test
    public void it_should_change_supportPhone() {
        // given
        String expected = "expectedString";
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setSupportPhone(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getSupportPhone());
    }

    @Test
    public void it_should_change_status() {
        // given
        String expected = "expectedString";
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setStatus(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getStatus());
    }

    @Test
    public void it_should_change_message() {
        // given
        String expected = "expectedString";
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setMessage(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getMessage());
    }

    @Test
    public void it_should_change_percentFee() {
        // given
        Double expected = 12.34;
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setPercentFee(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getPercentFee());
    }

    @Test
    public void it_should_change_fee() {
        // given
        Double expected = 12.34;
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setFee(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getFee());
    }

    @Test
    public void it_should_change_depositTotal() {
        // given
        Double expected = 12.34;
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setDepositTotal(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getDepositTotal());
    }

    @Test
    public void it_should_change_rate() {
        // given
        Double expected = 12.34;
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setRate(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getRate());
    }

    @Test
    public void it_should_change_btc() {
        // given
        Double expected = 12.34;
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setBtc(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getBtc());
    }

    @Test
    public void it_should_change_requestDate() {
        // given
        long expected = 1234L;
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setRequestDate(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getRequestDate());
    }

    @Test
    public void it_should_change_dateExecuted() {
        // given
        long expected = 1234L;
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setDateExecuted(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getDateExecuted());
    }

    @Test
    public void it_should_change_transactions() {
        // given
        List<PayoutInstructionTransaction> expected =
            Collections.singletonList(Mockito.mock(PayoutInstructionTransaction.class));
        Payout testedClass = this.getTestedClass();

        // when
        testedClass.setTransactions(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getTransactions());
    }

    private Payout getTestedClass() {
        return new Payout();
    }
}
