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
 * @see <a href="https://bitpay.com/api/#rest-api-resources-settlements">Settlements</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceData {
    private String _orderId;
    private Long _date;
    private Float _price;
    private String _currency;
    private String _transactionCurrency;
    private Float _overPaidAmount;
    private Map<String, Double> _payoutPercentage;
    private Float _btcPrice;
    private RefundInfo _refundInfo;

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
        return _orderId;
    }

    /**
     * Sets invoice orderId provided during invoice creation.
     *
     * @param orderId the order id
     */
    @JsonProperty("orderId")
    public void setOrderId(String orderId) {
        this._orderId = orderId;
    }

    /**
     * Gets at which the invoice was created (UTC). ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @return the date
     */
    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public Long getDate() {
        return _date;
    }

    /**
     * Sets at which the invoice was created (UTC). ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @param date the date
     */
    @JsonProperty("date")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setDate(Long date) {
        this._date = date;
    }

    /**
     * Gets invoice price in the invoice original currency.
     *
     * @return the price
     */
    @JsonIgnore
    public Float getPrice() {
        return _price;
    }

    /**
     * Sets invoice price in the invoice original currency.
     *
     * @param price the price
     */
    @JsonProperty("price")
    public void setPrice(Float price) {
        this._price = price;
    }

    /**
     * Gets invoice currency.
     *
     * @return the currency
     */
    @JsonIgnore
    public String getCurrency() {
        return _currency;
    }

    /**
     * Sets invoice currency.
     *
     * @param currency the currency
     */
    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this._currency = currency;
    }

    /**
     * Gets cryptocurrency selected by the consumer when paying the invoice.
     *
     * @return the transaction currency
     */
    @JsonIgnore
    public String getTransactionCurrency() {
        return _transactionCurrency;
    }

    /**
     * Sets cryptocurrency selected by the consumer when paying the invoice.
     *
     * @param transactionCurrency the transaction currency
     */
    @JsonProperty("transactionCurrency")
    public void setTransactionCurrency(String transactionCurrency) {
        this._transactionCurrency = transactionCurrency;
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
        return _overPaidAmount;
    }

    /**
     * Sets over paid amount.
     *
     * @param overPaidAmount the over paid amount
     */
    @JsonProperty("overPaidAmount")
    public void setOverPaidAmount(Float overPaidAmount) {
        this._overPaidAmount = overPaidAmount;
    }

    /**
     * Gets payout percentage defined by the merchant on his BitPay account settings.
     *
     * @return the payout percentage
     */
    @JsonIgnore
    public Map<String, Double> getPayoutPercentage() {
        return _payoutPercentage;
    }

    /**
     * Sets payout percentage defined by the merchant on his BitPay account settings.
     *
     * @param payoutPercentage the payout percentage
     */
    @JsonProperty("payoutPercentage")
    public void setPayoutPercentage(Map<String, Double> payoutPercentage) {
        this._payoutPercentage = payoutPercentage;
    }

    /**
     * Gets btc price.
     *
     * @return the btc price
     */
    @JsonIgnore
    public Float getBtcPrice() {
        return _btcPrice;
    }

    /**
     * Sets btc price.
     *
     * @param btcPrice the btc price
     */
    @JsonProperty("btcPrice")
    public void setBtcPrice(Float btcPrice) {
        this._btcPrice = btcPrice;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    @JsonIgnore
    public RefundInfo getAmount() {
        return _refundInfo;
    }

    /**
     * Sets amount.
     *
     * @param refundInfo the refund info
     */
    @JsonProperty("refundInfo")
    public void setAmount(RefundInfo refundInfo) {
        this._refundInfo = refundInfo;
    }
}
