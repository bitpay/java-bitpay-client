/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.invoice;

import com.bitpay.sdk.model.ModelConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Invoice itemized details.
 *
 * @see <a href="https://bitpay.readme.io/reference/invoices">REST API Invoices</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceItemizedDetails {
    protected Double amount;
    protected String description = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected Boolean isFee;

    /**
     * Instantiates a new Invoice itemized details.
     */
    public InvoiceItemizedDetails() {
    }

    /**
     * Gets the amount of currency.
     *
     * @return the amount
     */
    @JsonIgnore
    public Double getAmount() {
        return this.amount;
    }

    /**
     * Sets the amount of currency.
     *
     * @param amount the amount
     */
    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * Gets description. Display string for the item.
     *
     * @return the description
     */
    @JsonIgnore
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets description. Display string for the item.
     *
     * @param description the description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets is fee. Indicates whether or not the item is considered a fee/tax or part of the main purchase.
     *
     * @return the is fee
     */
    @JsonIgnore
    public Boolean getIsFee() {
        return this.isFee;
    }

    /**
     * Sets is fee. Indicates whether or not the item is considered a fee/tax or part of the main purchase.
     *
     * @param isFee the is fee
     */
    @JsonProperty("IsFee")
    public void setIsFee(Boolean isFee) {
        this.isFee = isFee;
    }
}
