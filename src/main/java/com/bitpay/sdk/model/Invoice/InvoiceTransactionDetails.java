/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import static com.bitpay.sdk.model.ModelConfiguration.DEFAULT_NON_SENT_VALUE;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Invoice transaction details.
 *
 * @see <a href="https://bitpay.readme.io/reference/invoices">REST API Invoices</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceTransactionDetails {
    private Double amount;
    private String description = DEFAULT_NON_SENT_VALUE;
    private Boolean isFee;

    /**
     * Instantiates a new Invoice transaction details.
     *
     * @param amount      the amount
     * @param description the description
     * @param isFee       the is fee
     */
    public InvoiceTransactionDetails(Double amount, String description, Boolean isFee) {
        this.amount = amount;
        this.description = description;
        this.isFee = isFee;
    }

    /**
     * Gets the amount of currency.
     *
     * @return the amount
     */
    @JsonProperty("amount")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
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
    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
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
    @JsonProperty("isFee")
    public Boolean getIsFee() {
        return this.isFee;
    }

    /**
     * Sets is fee. Indicates whether or not the item is considered a fee/tax or part of the main purchase.
     *
     * @param isFee the is fee
     */
    @JsonProperty("isFee")
    public void setIsFee(Boolean isFee) {
        this.isFee = isFee;
    }
}
