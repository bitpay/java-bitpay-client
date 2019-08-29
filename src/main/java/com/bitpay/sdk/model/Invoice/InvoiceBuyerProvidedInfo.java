package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InvoiceBuyerProvidedInfo {
    private String _name;
    private String _phoneNumber;
    private String _emailAddress;

    public InvoiceBuyerProvidedInfo() {
    }

    @JsonIgnore
    public String getName() {
        return _name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this._name = name;
    }

    @JsonIgnore
    public String getPhoneNumber() {
        return _phoneNumber;
    }

    @JsonProperty("phoneNumber")
    public void setPhoneNumber(String phoneNumber) {
        this._phoneNumber = phoneNumber;
    }

    @JsonIgnore
    public String getEmailAddress() {
        return _emailAddress;
    }

    @JsonProperty("emailAddress")
    public void setEmailAddress(String emailAddress) {
        this._emailAddress = emailAddress;
    }
}
