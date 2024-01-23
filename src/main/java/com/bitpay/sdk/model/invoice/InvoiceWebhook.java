/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Hashtable;

/**
 * Invoice Webhook.
 *
 * @see <a href="https://developer.bitpay.com/reference/notifications-invoices">Invoice Webhook</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class InvoiceWebhook {

    protected String id;
    protected String url;
    protected String posData;
    protected String status;
    protected Double price;
    protected String currency;
    protected String invoiceTime;
    protected String currencyTime;
    protected String exceptionStatus;
    protected BuyerFields buyerFields;
    protected Hashtable<String, BigInteger> paymentSubtotals;
    protected Hashtable<String, BigInteger> paymentTotals;
    protected Hashtable<String, Hashtable<String, BigDecimal>> exchangeRates;
    protected Double amountPaid;
    protected String orderId;
    protected String transactionCurrency;
    protected String inInvoiceId;
    protected String inPaymentRequest;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPosData() {
        return this.posData;
    }

    public void setPosData(String posData) {
        this.posData = posData;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getInvoiceTime() {
        return this.invoiceTime;
    }

    public void setInvoiceTime(String invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public String getCurrencyTime() {
        return this.currencyTime;
    }

    public void setCurrencyTime(String currencyTime) {
        this.currencyTime = currencyTime;
    }

    public String getExceptionStatus() {
        return this.exceptionStatus;
    }

    public void setExceptionStatus(String exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }

    public BuyerFields getBuyerFields() {
        return this.buyerFields;
    }

    public void setBuyerFields(BuyerFields buyerFields) {
        this.buyerFields = buyerFields;
    }

    public Hashtable<String, BigInteger> getPaymentSubtotals() {
        return this.paymentSubtotals;
    }

    public void setPaymentSubtotals(Hashtable<String, BigInteger> paymentSubtotals) {
        this.paymentSubtotals = paymentSubtotals;
    }

    public Hashtable<String, BigInteger> getPaymentTotals() {
        return this.paymentTotals;
    }

    public void setPaymentTotals(Hashtable<String, BigInteger> paymentTotals) {
        this.paymentTotals = paymentTotals;
    }

    public Hashtable<String, Hashtable<String, BigDecimal>> getExchangeRates() {
        return this.exchangeRates;
    }

    public void setExchangeRates(
        Hashtable<String, Hashtable<String, BigDecimal>> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public Double getAmountPaid() {
        return this.amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransactionCurrency() {
        return this.transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public String getInInvoiceId() {
        return this.inInvoiceId;
    }

    public void setInInvoiceId(String inInvoiceId) {
        this.inInvoiceId = inInvoiceId;
    }

    public String getInPaymentRequest() {
        return this.inPaymentRequest;
    }

    public void setInPaymentRequest(String inPaymentRequest) {
        this.inPaymentRequest = inPaymentRequest;
    }
}
