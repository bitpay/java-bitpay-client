/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import static com.bitpay.sdk.model.ModelConfiguration.DEFAULT_NON_SENT_VALUE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object containing wallet-specific URLs for payment protocol.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-invoices-resource">REST API Invoices</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceUniversalCodes {

    private String paymentString = DEFAULT_NON_SENT_VALUE;
    private String verificationLink = DEFAULT_NON_SENT_VALUE;

    /**
     * Instantiates a new Invoice universal codes.
     */
    public InvoiceUniversalCodes() {
    }

    /**
     * Gets payment protocol URL for selected wallet, defaults to BitPay URL if no wallet selected.
     *
     * @return the bitpay
     */
    @JsonIgnore
    public String getBitpay() {
        return this.paymentString;
    }

    /**
     * Sets payment protocol URL for selected wallet, defaults to BitPay URL if no wallet selected.
     *
     * @param paymentString the payment string
     */
    @JsonProperty("paymentString")
    public void setBitpay(String paymentString) {
        this.paymentString = paymentString;
    }

    /**
     * Gets link to bring user to BitPay ID flow, only present when bitpayIdRequired is true.
     *
     * @return the verification link
     */
    @JsonIgnore
    public String getVerificationLink() {
        return this.verificationLink;
    }

    /**
     * Sets link to bring user to BitPay ID flow, only present when bitpayIdRequired is true.
     *
     * @param verificationLink the verification link
     */
    @JsonProperty("verificationLink")
    public void setVerificationLink(String verificationLink) {
        this.verificationLink = verificationLink;
    }
}
