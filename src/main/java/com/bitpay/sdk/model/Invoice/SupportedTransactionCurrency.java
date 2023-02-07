/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import static com.bitpay.sdk.model.ModelConfiguration.DEFAULT_NON_SENT_VALUE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The currency that may be used to pay this invoice. The values are objects with an "enabled" boolean and option.
 * An extra "reason" parameter is added in the object if a cryptocurrency is disabled on a specific invoice.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-invoices-resource">REST API Invoices</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupportedTransactionCurrency {

    private boolean enabled;
    private String reason = DEFAULT_NON_SENT_VALUE;

    /**
     * Instantiates a new Supported transaction currency.
     */
    public SupportedTransactionCurrency() {
    }

    /**
     * Gets enabled.
     *
     * @return the enabled
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean getEnabled() {
        return this.enabled;
    }

    /**
     * Sets enabled.
     *
     * @param enabled the enabled
     */
    @JsonProperty("enabled")
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets reason.
     *
     * @return the reason
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getReason() { return this.reason; }

    /**
     * Sets reason.
     *
     * @param reason the reason
     */
    @JsonProperty("reason")
    public void setReason(String reason) {
        this.reason = reason;
    }
}
