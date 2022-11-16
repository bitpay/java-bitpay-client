/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk.model.Settlement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type With holdings.
 * @see <a href="https://bitpay.com/api/#rest-api-resources-settlements">Settlements</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WithHoldings {
    private Float _amount;
    private String _code;
    private String _description;
    private String _notes;
    private String _label;
    private String _bankCountry;

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
        return _amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    @JsonProperty("amount")
    public void setAmount(Float amount) {
        this._amount = amount;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    @JsonIgnore
    public String getCode() {
        return _code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this._code = code;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    @JsonIgnore
    public String getDescription() {
        return _description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this._description = description;
    }

    /**
     * Gets notes.
     *
     * @return the notes
     */
    @JsonIgnore
    public String getNotes() {
        return _notes;
    }

    /**
     * Sets notes.
     *
     * @param notes the notes
     */
    @JsonProperty("notes")
    public void setNotes(String notes) {
        this._notes = notes;
    }

    /**
     * It should be replaced by getLabel().
     *
     * @return the label
     */
    @Deprecated
    @JsonIgnore
    public String getlabel() {
        return _label;
    }

    /**
     * It should be replaced by setLabel().
     *
     * @param label the label
     */
    @Deprecated
    public void setlabel(String label) {
        this._label = label;
    }

    /**
     * Gets label.
     *
     * @return the
     */
    @JsonIgnore
    public String getLabel() {
        return _label;
    }

    /**
     * Sets label.
     *
     * @param label the label
     */
    @JsonProperty("label")
    public void setLabel(String label) {
        this._label = label;
    }

    /**
     * Gets bank country.
     *
     * @return the bank country
     */
    @JsonIgnore
    public String getBankCountry() {
        return _bankCountry;
    }

    /**
     * Sets bank country.
     *
     * @param bankCountry the bank country
     */
    @JsonProperty("bankCountry")
    public void setBankCountry(String bankCountry) {
        this._bankCountry = bankCountry;
    }
}
