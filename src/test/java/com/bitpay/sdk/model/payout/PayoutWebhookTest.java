package com.bitpay.sdk.model.payout;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PayoutWebhookTest {

    @Test
    public void testManipulateId() {
        PayoutWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setId(expected);

        Assertions.assertSame(expected, testedClass.getId());
    }

    @Test
    public void testManipulateRecipientId() {
        PayoutWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setRecipientId(expected);

        Assertions.assertSame(expected, testedClass.getRecipientId());
    }

    @Test
    public void testManipulateShopperId() {
        PayoutWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setShopperId(expected);

        Assertions.assertSame(expected, testedClass.getShopperId());
    }

    @Test
    public void testManipulatePrice() {
        PayoutWebhook testedClass = this.getTestedClass();
        Double expected = 132.88;
        testedClass.setPrice(expected);

        Assertions.assertSame(expected, testedClass.getPrice());
    }

    @Test
    public void testManipulateCurrency() {
        PayoutWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setCurrency(expected);

        Assertions.assertSame(expected, testedClass.getCurrency());
    }

    @Test
    public void testManipulateLedgerCurrency() {
        PayoutWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setLedgerCurrency(expected);

        Assertions.assertSame(expected, testedClass.getLedgerCurrency());
    }

    @Test
    public void testManipulateExchangeRates() {
        PayoutWebhook testedClass = this.getTestedClass();
        Hashtable<String, Hashtable<String, BigDecimal>> expected = new Hashtable<String, Hashtable<String, BigDecimal>>();
        testedClass.setExchangeRates(expected);

        Assertions.assertSame(expected, testedClass.getExchangeRates());
    }

    @Test
    public void testManipulateEmail() {
        PayoutWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setEmail(expected);

        Assertions.assertSame(expected, testedClass.getEmail());
    }

    @Test
    public void testManipulateReference() {
        PayoutWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setReference(expected);

        Assertions.assertSame(expected, testedClass.getReference());
    }

    @Test
    public void testManipulateLabel() {
        PayoutWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setLabel(expected);

        Assertions.assertSame(expected, testedClass.getLabel());
    }

    @Test
    public void testManipulateNotificationURL() {
        PayoutWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setNotificationURL(expected);

        Assertions.assertSame(expected, testedClass.getNotificationURL());
    }

    @Test
    public void testManipulateNotificationEmail() {
        PayoutWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setNotificationEmail(expected);

        Assertions.assertSame(expected, testedClass.getNotificationEmail());
    }

    @Test
    public void testManipulateEffectiveDate() {
        PayoutWebhook testedClass = this.getTestedClass();
        ZonedDateTime expected = ZonedDateTime.now();
        testedClass.setEffectiveDate(expected);

        Assertions.assertSame(expected, testedClass.getEffectiveDate());
    }

    @Test
    public void testManipulateRequestDate() {
        PayoutWebhook testedClass = this.getTestedClass();
        ZonedDateTime expected = ZonedDateTime.now();
        testedClass.setRequestDate(expected);

        Assertions.assertSame(expected, testedClass.getRequestDate());
    }

    @Test
    public void testManipulateStatus() {
        PayoutWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setStatus(expected);

        Assertions.assertSame(expected, testedClass.getStatus());
    }

    @Test
    public void testManipulateTransactions() {
        PayoutWebhook testedClass = this.getTestedClass();
        List<PayoutTransaction> expected = new ArrayList<>();
        testedClass.setTransactions(expected);

        Assertions.assertSame(expected, testedClass.getTransactions());
    }

    private PayoutWebhook getTestedClass() {
        return new PayoutWebhook();
    }
}
