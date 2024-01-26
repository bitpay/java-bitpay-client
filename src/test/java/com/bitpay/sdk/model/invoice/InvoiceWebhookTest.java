package com.bitpay.sdk.model.invoice;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Hashtable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InvoiceWebhookTest {

    @Test
    public void testManipulateId() {
        InvoiceWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setId(expected);

        Assertions.assertSame(expected, testedClass.getId());
    }

    @Test
    public void testManipulateUrl() {
        InvoiceWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setUrl(expected);

        Assertions.assertSame(expected, testedClass.getUrl());
    }

    @Test
    public void testManipulatePosData() {
        InvoiceWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setPosData(expected);

        Assertions.assertSame(expected, testedClass.getPosData());
    }

    @Test
    public void testManipulateStatus() {
        InvoiceWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setStatus(expected);

        Assertions.assertSame(expected, testedClass.getStatus());
    }

    @Test
    public void testManipulatePrice() {
        InvoiceWebhook testedClass = this.getTestedClass();
        Double expected = 12.34;
        testedClass.setPrice(expected);

        Assertions.assertSame(expected, testedClass.getPrice());
    }

    @Test
    public void testManipulateCurrency() {
        InvoiceWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setCurrency(expected);

        Assertions.assertSame(expected, testedClass.getCurrency());
    }

    @Test
    public void testManipulateInvoiceTime() {
        InvoiceWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setInvoiceTime(expected);

        Assertions.assertSame(expected, testedClass.getInvoiceTime());
    }

    @Test
    public void testManipulateCurrencyTime() {
        InvoiceWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setCurrencyTime(expected);

        Assertions.assertSame(expected, testedClass.getCurrencyTime());
    }

    @Test
    public void testManipulateExceptionStatus() {
        InvoiceWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setExceptionStatus(expected);

        Assertions.assertSame(expected, testedClass.getExceptionStatus());
    }

    @Test
    public void testManipulateBuyerFields() {
        InvoiceWebhook testedClass = this.getTestedClass();
        BuyerFields expected = new BuyerFields();
        testedClass.setBuyerFields(expected);

        Assertions.assertSame(expected, testedClass.getBuyerFields());
    }

    @Test
    public void testManipulatePaymentSubtotals() {
        InvoiceWebhook testedClass = this.getTestedClass();
        Hashtable<String, BigInteger> expected = new Hashtable<String, BigInteger>();
        testedClass.setPaymentSubtotals(expected);

        Assertions.assertSame(expected, testedClass.getPaymentSubtotals());
    }

    @Test
    public void testManipulatePaymentTotals() {
        InvoiceWebhook testedClass = this.getTestedClass();
        Hashtable<String, BigInteger> expected = new Hashtable<String, BigInteger>();
        testedClass.setPaymentTotals(expected);

        Assertions.assertSame(expected, testedClass.getPaymentTotals());
    }

    @Test
    public void testManipulateExchangeRates() {
        InvoiceWebhook testedClass = this.getTestedClass();
        Hashtable<String, Hashtable<String, BigDecimal>> expected = new Hashtable<String, Hashtable<String, BigDecimal>>();
        testedClass.setExchangeRates(expected);

        Assertions.assertSame(expected, testedClass.getExchangeRates());
    }

    @Test
    public void testManipulateAmountPaid() {
        InvoiceWebhook testedClass = this.getTestedClass();
        Double expected = 13.85;
        testedClass.setAmountPaid(expected);

        Assertions.assertSame(expected, testedClass.getAmountPaid());
    }

    @Test
    public void testManipulateOrderId() {
        InvoiceWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setOrderId(expected);

        Assertions.assertSame(expected, testedClass.getOrderId());
    }

    @Test
    public void testManipulateTransactionCurrency() {
        InvoiceWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setTransactionCurrency(expected);

        Assertions.assertSame(expected, testedClass.getTransactionCurrency());
    }

    @Test
    public void testManipulateInInvoiceId() {
        InvoiceWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setInInvoiceId(expected);

        Assertions.assertSame(expected, testedClass.getInInvoiceId());
    }

    @Test
    public void testManipulateInPaymentRequest() {
        InvoiceWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setInPaymentRequest(expected);

        Assertions.assertSame(expected, testedClass.getInPaymentRequest());
    }

    private InvoiceWebhook getTestedClass() {
        return new InvoiceWebhook();
    }
}
