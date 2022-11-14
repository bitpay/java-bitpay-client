/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Hashtable;

/**
 * The type Refund info.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-refunds">REST API Refunds</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundInfo {

    private String _supportRequest;
    private String _currency;
    private String refundRequestEid;
    private Hashtable<String, Double> _amounts;

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
        return _supportRequest;
    }

    /**
     * Sets the refund requestId.
     *
     * @param supportRequest the support request
     */
    @JsonProperty("supportRequest")
    public void setSupportRequest(String supportRequest) {
        this._supportRequest = supportRequest;
    }

    /**
     * Gets reference currency used for the refund, usually the same as the currency used to create the invoice.
     *
     * @return the currency
     */
    @JsonIgnore
    public String getCurrency() {
        return _currency;
    }

    /**
     * Sets reference currency used for the refund, usually the same as the currency used to create the invoice.
     *
     * @param currency the currency
     * @throws BitPayException the bit pay exception
     */
    @JsonProperty("currency")
    public void setCurrency(String currency) throws BitPayException {
        if (!Currency.isValid(currency))
            throw new BitPayException(null, "Error: currency code must be a type of Model.Currency");

        this._currency = currency;
    }

    /**
     * Gets Refund Request Eid.
     *
     * @return Refund Request Eid
     */
    public String getRefundRequestEid() {
        return refundRequestEid;
    }

    /**
     * Sets Refund Request Eid.
     *
     * @param refundRequestEid Refund Request Eid
     */
    public void setRefundRequestEid(String refundRequestEid) {
        this.refundRequestEid = refundRequestEid;
    }

    /**
     * Gets amounts. For a refunded invoice, this object will contain the crypto currency amount refunded by BitPay
     * to the consumer (in the selected transactionCurrency) and the equivalent refunded amount from the invoice
     * in the given currency (thus linked to the amount debited from the merchant account to cover the refund).
     *
     * @return the amounts
     */
    @JsonIgnore
    public Hashtable<String, Double> getAmounts() {
        return _amounts;
    }

    /**
     * Sets amounts. For a refunded invoice, this object will contain the crypto currency amount refunded by BitPay
     * to the consumer (in the selected transactionCurrency) and the equivalent refunded amount from the invoice
     * in the given currency (thus linked to the amount debited from the merchant account to cover the refund).
     *
     * @param amounts the amounts
     */
    @JsonProperty("amounts")
    public void setAmounts(Hashtable<String, Double> amounts) {
        this._amounts = amounts;
    }

}
