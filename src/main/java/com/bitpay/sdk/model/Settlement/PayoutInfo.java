package com.bitpay.sdk.model.Settlement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutInfo {
    private String _name;
    private String _account;
    private String _routing;
    private String _merchantEin;
    private String _label;
    private String _bankCountry;
    private String _bank;
    private String _swift;
    private String _address;
    private String _city;
    private String _postal;
    private String _sort;
    private String _wire;
    private String _bankName;
    private String _bankAddress;
    private String _iban;
    private String _additionalInformation;
    private String _accountHolderName;
    private String _accountHolderAddress;
    private String _accountHolderAddress2;
    private String _accountHolderPostalCode;
    private String _accountHolderCity;
    private String _accountHolderCountry;

    public PayoutInfo() {
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
    public String getAccount() {
        return _account;
    }

    @JsonProperty("account")
    public void setAccount(String account) {
        this._account = account;
    }

    @JsonIgnore
    public String getRouting() {
        return _routing;
    }

    @JsonProperty("routing")
    public void setRouting(String routing) {
        this._routing = routing;
    }

    @JsonIgnore
    public String getMerchantEin() {
        return _merchantEin;
    }

    @JsonProperty("merchantEin")
    public void setMerchantEin(String merchantEin) {
        this._merchantEin = merchantEin;
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

    @JsonIgnore
    public String getBank() { return _bank; }

    @JsonProperty("bank")
    public void setBank(String bank) { this._bank = bank; }

    @JsonIgnore
    public String getSwift() { return _swift; }

    @JsonProperty("swift")
    public void setSwift(String swift) { this._swift = swift; }

    @JsonIgnore
    public String getAddress() { return _address; }

    @JsonProperty("address")
    public void setAddress(String address) { this._address = address; }

    @JsonIgnore
    public String getCity() { return _city; }

    @JsonProperty("city")
    public void setCity(String city) { this._city = city; }

    @JsonIgnore
    public String getPostal() { return _postal; }

    @JsonProperty("postal")
    public void setPostal(String postal) { this._postal = postal; }

    @JsonIgnore
    public String getSort() { return _sort; }

    @JsonProperty("sort")
    public void setSort(String sort) { this._sort = sort; }

    @JsonIgnore
    public String getWire() { return _wire; }

    @JsonProperty("wire")
    public void setWire(String wire) { this._wire = wire; }

    @JsonIgnore
    public String getBankName() { return _bankName; }

    @JsonProperty("bankName")
    public void setBankName(String bankName) { this._bankName = bankName; }

    @JsonIgnore
    public String getBankAddress() { return _bankAddress; }

    @JsonProperty("bankAddress")
    public void setBankAddress(String bankAddress) { this._bankAddress = bankAddress; }

    @JsonIgnore
    public String getIban() { return _iban; }

    @JsonProperty("iban")
    public void setIban(String iban) { this._iban = iban; }

    @JsonIgnore
    public String getAdditionalInformation() { return _additionalInformation; }

    @JsonProperty("additionalInformation")
    public void setAdditionalInformation(String additionalInformation) { this._additionalInformation = additionalInformation; }

    @JsonIgnore
    public String getAccountHolderName() { return _accountHolderName; }

    @JsonProperty("accountHolderName")
    public void setAccountHolderName(String accountHolderName) { this._accountHolderName = accountHolderName; }

    @JsonIgnore
    public String getAccountHolderAddress() { return _accountHolderAddress; }

    @JsonProperty("accountHolderAddress")
    public void setAccountHolderAddress(String accountHolderAddress) { this._accountHolderAddress = accountHolderAddress; }

    @JsonIgnore
    public String getAccountHolderAddress2() { return _accountHolderAddress2; }

    @JsonProperty("accountHolderAddress2")
    public void setAccountHolderAddress2(String accountHolderAddress2) { this._accountHolderAddress2 = accountHolderAddress2; }

    @JsonIgnore
    public String getAccountHolderPostalCode() { return _accountHolderPostalCode; }

    @JsonProperty("accountHolderPostalCode")
    public void setAccountHolderPostalCode(String accountHolderPostalCode) { this._accountHolderPostalCode = accountHolderPostalCode; }

    @JsonIgnore
    public String getAccountHolderCity() { return _accountHolderCity; }

    @JsonProperty("accountHolderCity")
    public void setAccountHolderCity(String accountHolderCity) { this._accountHolderCity = accountHolderCity; }

    @JsonIgnore
    public String getAccountHolderCountry() { return _accountHolderCountry; }

    @JsonProperty("accountHolderCountry")
    public void setAccountHolderCountry(String accountHolderCountry) { this._accountHolderCountry = accountHolderCountry; }
}
