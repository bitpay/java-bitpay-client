package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceBuyerProvidedInfo {
    private String _name;
    private String _phoneNumber;
    private String _selectedTransactionCurrency;
    private String _emailAddress;
    private String _selectedWallet;

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
    public String getSelectedTransactionCurrency() {
        return _selectedTransactionCurrency;
    }

    @JsonProperty("selectedTransactionCurrency")
    public void setSelectedTransactionCurrency(String selectedTransactionCurrency) {
        this._selectedTransactionCurrency = selectedTransactionCurrency;
    }

    @JsonIgnore
    public String getEmailAddress() {
        return _emailAddress;
    }

    @JsonProperty("emailAddress")
    public void setEmailAddress(String emailAddress) {
        this._emailAddress = emailAddress;
    }

    @JsonIgnore
    public String getSelectedWallet() {
        return _selectedWallet;
    }

    @JsonProperty("selectedWallet")
    public void setSelectedWallet(String selectedWallet) {
        this._selectedWallet = selectedWallet;
    }
}
