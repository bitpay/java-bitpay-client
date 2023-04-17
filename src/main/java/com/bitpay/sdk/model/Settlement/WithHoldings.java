/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk.model.Settlement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type With holdings.
 * @see <a href="https://bitpay.readme.io/reference/settlements">Settlements</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WithHoldings {
    private Float amount;
    private String code;
    private String description;
    private String notes;
    private String label;
    private String bankCountry;

    /**
     * Instantiates a new With holdings.
     */
    public WithHoldings() {
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    @JsonIgnore
    public Float getAmount() {
        return this.amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    @JsonProperty("amount")
    public void setAmount(Float amount) {
        this.amount = amount;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    @JsonIgnore
    public String getCode() {
        return this.code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    @JsonIgnore
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets notes.
     *
     * @return the notes
     */
    @JsonIgnore
    public String getNotes() {
        return this.notes;
    }

    /**
     * Sets notes.
     *
     * @param notes the notes
     */
    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Gets label.
     *
     * @return the
     */
    @JsonIgnore
    public String getLabel() {
        return this.label;
    }

    /**
     * Sets label.
     *
     * @param label the label
     */
    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets bank country.
     *
     * @return the bank country
     */
    @JsonIgnore
    public String getBankCountry() {
        return this.bankCountry;
    }

    /**
     * Sets bank country.
     *
     * @param bankCountry the bank country
     */
    @JsonProperty("bankCountry")
    public void setBankCountry(String bankCountry) {
        this.bankCountry = bankCountry;
    }
}
