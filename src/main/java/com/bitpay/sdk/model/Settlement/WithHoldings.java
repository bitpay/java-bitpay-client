package com.bitpay.sdk.model.Settlement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WithHoldings {
    private Float _amount;
    private String _code;
    private String _description;
    private String _notes;
    private String _label;
    private String _bankCountry;

    public WithHoldings() {
    }

    @JsonIgnore
    public Float getAmount() {
        return _amount;
    }

    @JsonProperty("amount")
    public void setAmount(Float amount) {
        this._amount = amount;
    }

    @JsonIgnore
    public String getCode() {
        return _code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this._code = code;
    }

    @JsonIgnore
    public String getDescription() {
        return _description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this._description = description;
    }

    @JsonIgnore
    public String getNotes() {
        return _notes;
    }

    @JsonProperty("notes")
    public void setNotes(String notes) {
        this._notes = notes;
    }

    @JsonIgnore
    public String getlabel() {
        return _label;
    }

    @JsonProperty("label")
    public void setlabel(String label) {
        this._label = label;
    }

    @JsonIgnore
    public String getBankCountry() {
        return _bankCountry;
    }

    @JsonProperty("bankCountry")
    public void setBankCountry(String bankCountry) {
        this._bankCountry = bankCountry;
    }
}
