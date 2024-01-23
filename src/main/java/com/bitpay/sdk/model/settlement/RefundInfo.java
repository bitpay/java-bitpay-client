/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.settlement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Hashtable;

/**
 * The type Refund info.
 *
 * @see <a href="https://bitpay.readme.io/reference/refunds-resource">Refunds</a>
 * @see <a href="https://bitpay.readme.io/reference/settlements">Settlements</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundInfo {
    protected String supportRequest;
    protected String currency;
    protected Hashtable<String, Double> amounts;

    /**
     * Instantiates a new Refund info.
     */
    public RefundInfo() {
    }

    /**
     * Gets the refund requestId.
     *
     * @return the support request
     */
    @JsonIgnore
    public String getSupportRequest() {
        return this.supportRequest;
    }

    /**
     * Sets the refund requestId.
     *
     * @param supportRequest the support request
     */
    @JsonProperty("supportRequest")
    public void setSupportRequest(String supportRequest) {
        this.supportRequest = supportRequest;
    }

    /**
     * Gets reference currency used for the refund, usually the same as the currency used to create the invoice.
     *
     * @return the currency
     */
    @JsonIgnore
    public String getCurrency() {
        return this.currency;
    }

    /**
     * Sets reference currency used for the refund, usually the same as the currency used to create the invoice.
     *
     * @param currency the currency
     */
    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets amount. For a refunded invoice, this object will contain the crypto currency amount refunded by BitPay
     * to the consumer (in the selected transactionCurrency) and the equivalent refunded amount from the invoice
     * in the given currency (thus linked to the amount debited from the merchant account to cover the refund).
     *
     * @return the amount
     */
    @JsonIgnore
    public Hashtable<String, Double> getAmount() {
        return this.amounts;
    }

    /**
     * Sets amount. For a refunded invoice, this object will contain the crypto currency amount refunded by BitPay
     * to the consumer (in the selected transactionCurrency) and the equivalent refunded amount from the invoice
     * in the given currency (thus linked to the amount debited from the merchant account to cover the refund).
     *
     * @param amounts the amounts
     */
    @JsonProperty("amounts")
    public void setAmount(Hashtable<String, Double> amounts) {
        this.amounts = amounts;
    }
}
