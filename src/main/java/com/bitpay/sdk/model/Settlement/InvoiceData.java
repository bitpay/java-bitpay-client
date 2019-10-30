package com.bitpay.sdk.model.Settlement;

import com.bitpay.sdk.util.DateDeserializer;
import com.bitpay.sdk.util.DateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceData {
    private String _orderId;
    private Long _date;
    private Float _price;
    private String _currency;
    private String _transactionCurrency;
    private Float _overPaidAmount;
    private Double _payoutPercentage;
    private Float _btcPrice;
    private RefundInfo _refundInfo;

    public InvoiceData() {
    }

    @JsonIgnore
    public String getOrderId() {
        return _orderId;
    }

    @JsonProperty("orderId")
    public void setOrderId(String orderId) {
        this._orderId = orderId;
    }

    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public Long getDate() {
        return _date;
    }

    @JsonProperty("date")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setDate(Long date) {
        this._date = date;
    }

    @JsonIgnore
    public Float getPrice() {
        return _price;
    }

    @JsonProperty("price")
    public void setPrice(Float price) {
        this._price = price;
    }

    @JsonIgnore
    public String getCurrency() {
        return _currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this._currency = currency;
    }

    @JsonIgnore
    public String getTransactionCurrency() {
        return _transactionCurrency;
    }

    @JsonProperty("transactionCurrency")
    public void setTransactionCurrency(String transactionCurrency) {
        this._transactionCurrency = transactionCurrency;
    }

    @JsonIgnore
    public Float getOverPaidAmount() {
        return _overPaidAmount;
    }

    @JsonProperty("overPaidAmount")
    public void setOverPaidAmount(Float overPaidAmount) {
        this._overPaidAmount = overPaidAmount;
    }

    @JsonIgnore
    public Double getPayoutPercentage() {
        return _payoutPercentage;
    }

    @JsonProperty("payoutPercentage")
    public void setPayoutPercentage(Double payoutPercentage) {
        this._payoutPercentage = payoutPercentage;
    }

    @JsonIgnore
    public Float getBtcPrice() {
        return _btcPrice;
    }

    @JsonProperty("btcPrice")
    public void setBtcPrice(Float btcPrice) {
        this._btcPrice = btcPrice;
    }

    @JsonIgnore
    public RefundInfo getAmount() {
        return _refundInfo;
    }

    @JsonProperty("refundInfo")
    public void setAmount(RefundInfo refundInfo) {
        this._refundInfo = refundInfo;
    }
}
