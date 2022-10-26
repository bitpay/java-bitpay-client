/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Invoice itemized details.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-invoices-resource">REST API Invoices</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceItemizedDetails {
    private double _amount;
    private String _description;
    private Boolean _isFee;

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
    public double getAmount() {
        return _amount;
    }

    /**
     * Sets the amount of currency.
     *
     * @param amount the amount
     */
    @JsonProperty("amount")
    public void setAmount(double amount) {
        this._amount = amount;
    }

    /**
     * Gets description. Display string for the item.
     *
     * @return the description
     */
    @JsonIgnore
    public String getDescription() {
        return _description;
    }

    /**
     * Sets description. Display string for the item.
     *
     * @param description the description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this._description = description;
    }

    /**
     * Gets is fee. Indicates whether or not the item is considered a fee/tax or part of the main purchase.
     *
     * @return the is fee
     */
    @JsonIgnore
    public Boolean getIsFee() { return _isFee; }

    /**
     * Sets is fee. Indicates whether or not the item is considered a fee/tax or part of the main purchase.
     *
     * @param isFee the is fee
     */
    @JsonProperty("IsFee")
    public void setIsFee(Boolean isFee) {
        this._isFee = isFee;
    }
}
