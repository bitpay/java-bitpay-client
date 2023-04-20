/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk.model.Settlement;

import com.bitpay.sdk.util.DateDeserializer;
import com.bitpay.sdk.util.DateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;

/**
 * The type Invoice data.
 * @see <a href="https://bitpay.readme.io/reference/settlements">Settlements</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceData {
    private String orderId;
    private Long date;
    private Float price;
    private String currency;
    private String transactionCurrency;
    private Float overPaidAmount;
    private Map<String, Double> payoutPercentage;
    private RefundInfo refundInfo;

    /**
     * Instantiates a new Invoice data.
     */
    public InvoiceData() {
    }

    /**
     * Gets invoice orderId provided during invoice creation.
     *
     * @return the order id
     */
    @JsonIgnore
    public String getOrderId() {
        return this.orderId;
    }

    /**
     * Sets invoice orderId provided during invoice creation.
     *
     * @param orderId the order id
     */
    @JsonProperty("orderId")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets at which the invoice was created (UTC). ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @return the date
     */
    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public Long getDate() {
        return this.date;
    }

    /**
     * Sets at which the invoice was created (UTC). ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @param date the date
     */
    @JsonProperty("date")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setDate(Long date) {
        this.date = date;
    }

    /**
     * Gets invoice price in the invoice original currency.
     *
     * @return the price
     */
    @JsonIgnore
    public Float getPrice() {
        return this.price;
    }

    /**
     * Sets invoice price in the invoice original currency.
     *
     * @param price the price
     */
    @JsonProperty("price")
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * Gets invoice currency.
     *
     * @return the currency
     */
    @JsonIgnore
    public String getCurrency() {
        return this.currency;
    }

    /**
     * Sets invoice currency.
     *
     * @param currency the currency
     */
    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets cryptocurrency selected by the consumer when paying the invoice.
     *
     * @return the transaction currency
     */
    @JsonIgnore
    public String getTransactionCurrency() {
        return this.transactionCurrency;
    }

    /**
     * Sets cryptocurrency selected by the consumer when paying the invoice.
     *
     * @param transactionCurrency the transaction currency
     */
    @JsonProperty("transactionCurrency")
    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    /**
     * Gets over paid amount.
     * This parameter will be returned on the invoice object if the invoice was overpaid ("exceptionStatus": "paidOver").
     * It equals to the absolute difference between amountPaid and paymentTotals for the corresponding
     * transactionCurrency used.
     *
     * See also {@link com.bitpay.sdk.model.Invoice.Invoice }
     *
     * @return the over paid amount
     */
    @JsonIgnore
    public Float getOverPaidAmount() {
        return this.overPaidAmount;
    }

    /**
     * Sets over paid amount.
     *
     * @param overPaidAmount the over paid amount
     */
    @JsonProperty("overPaidAmount")
    public void setOverPaidAmount(Float overPaidAmount) {
        this.overPaidAmount = overPaidAmount;
    }

    /**
     * Gets payout percentage defined by the merchant on his BitPay account settings.
     *
     * @return the payout percentage
     */
    @JsonIgnore
    public Map<String, Double> getPayoutPercentage() {
        return this.payoutPercentage;
    }

    /**
     * Sets payout percentage defined by the merchant on his BitPay account settings.
     *
     * @param payoutPercentage the payout percentage
     */
    @JsonProperty("payoutPercentage")
    public void setPayoutPercentage(Map<String, Double> payoutPercentage) {
        this.payoutPercentage = payoutPercentage;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    @JsonIgnore
    public RefundInfo getAmount() {
        return this.refundInfo;
    }

    /**
     * Sets amount.
     *
     * @param refundInfo the refund info
     */
    @JsonProperty("refundInfo")
    public void setAmount(RefundInfo refundInfo) {
        this.refundInfo = refundInfo;
    }
}
