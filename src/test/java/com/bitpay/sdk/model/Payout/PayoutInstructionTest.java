/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Payout;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PayoutInstructionTest {

    @Test
    public void it_should_change_amount() {
        // given
        Double expected = 12.34;
        PayoutInstruction testedClass = this.getTestedClass();

        // when
        testedClass.setAmount(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAmount());
    }

    @Test
    public void it_should_change_email() {
        // given
        String expected = "expectedString";
        PayoutInstruction testedClass = this.getTestedClass();

        // when
        testedClass.setEmail(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getEmail());
    }

    @Test
    public void it_should_change_recipientId() {
        // given
        String expected = "expectedString";
        PayoutInstruction testedClass = this.getTestedClass();

        // when
        testedClass.setRecipientId(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getRecipientId());
    }

    @Test
    public void it_should_change_shopperId() {
        // given
        String expected = "expectedString";
        PayoutInstruction testedClass = this.getTestedClass();

        // when
        testedClass.setShopperId(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getShopperId());
    }

    @Test
    public void it_should_change_label() {
        // given
        String expected = "expectedString";
        PayoutInstruction testedClass = this.getTestedClass();

        // when
        testedClass.setLabel(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getLabel());
    }

    @Test
    public void it_should_change_walletProvider() {
        // given
        String expected = "expectedString";
        PayoutInstruction testedClass = this.getTestedClass();

        // when
        testedClass.setWalletProvider(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getWalletProvider());
    }

    @Test
    public void it_should_change_id() {
        // given
        String expected = "expectedString";
        PayoutInstruction testedClass = this.getTestedClass();

        // when
        testedClass.setId(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getId());
    }

    @Test
    public void it_should_change_btc() {
        // given
        PayoutInstructionBtcSummary expected = Mockito.mock(PayoutInstructionBtcSummary.class);
        PayoutInstruction testedClass = this.getTestedClass();

        // when
        testedClass.setBtc(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getBtc());
    }

    @Test
    public void it_should_change_transactions() {
        // given
        List<PayoutInstructionTransaction> expected =
            Collections.singletonList(Mockito.mock(PayoutInstructionTransaction.class));
        PayoutInstruction testedClass = this.getTestedClass();

        // when
        testedClass.setTransactions(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getTransactions());
    }

    @Test
    public void it_should_change_status() {
        // given
        String expected = "expectedString";
        PayoutInstruction testedClass = this.getTestedClass();

        // when
        testedClass.setStatus(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getStatus());
    }

    @Test
    public void it_should_change_address() {
        // given
        String expected = "expectedString";
        PayoutInstruction testedClass = this.getTestedClass();

        // when
        testedClass.setAddress(expected);

        // then
        Assertions.assertEquals(expected, testedClass.getAddress());
    }

    private PayoutInstruction getTestedClass() {
        return new PayoutInstruction();
    }
}
