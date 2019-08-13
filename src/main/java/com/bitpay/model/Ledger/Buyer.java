package com.bitpay.model.Ledger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Buyer {

    private String _name;
    private String _address1;
    private String _address2;
    private String _locality;
    private String _region;
    private String _postalCode;
    private String _country;
    private String _phone;
    private boolean _notify;
    private String _state;
    private String _zip;
    private String _city;

    public Buyer() {
    }

    @JsonIgnore
    public String getName() {
        return _name;
    }

    @JsonProperty("buyerName")
    public void setName(String name) {
        this._name = name;
    }

    @JsonIgnore
    public String getAddress1() {
        return _address1;
    }

    @JsonProperty("buyerAddress1")
    public void setAddress1(String address1) {
        this._address1 = address1;
    }

    @JsonIgnore
    public String getAddress2() {
        return _address2;
    }

    @JsonProperty("buyerAddress2")
    public void setAddress2(String address2) {
        this._address2 = address2;
    }

    @JsonIgnore
    public String getLocality() {
        return _locality;
    }

    @JsonProperty("buyerLocality")
    public void setLocality(String locality) {
        this._locality = locality;
    }

    @JsonIgnore
    public String getRegion() {
        return _region;
    }

    @JsonProperty("buyerRegion")
    public void setRegion(String region) {
        this._region = region;
    }

    @JsonIgnore
    public String getPostalCode() {
        return _postalCode;
    }

    @JsonProperty("buyerPostalCode")
    public void setPostalCode(String postalCode) {
        this._postalCode = postalCode;
    }

    @JsonIgnore
    public String getCountry() {
        return _country;
    }

    @JsonProperty("buyerCountry")
    public void setCountry(String country) {
        this._country = country;
    }

    @JsonIgnore
    public String getPhone() {
        return _phone;
    }

    @JsonProperty("buyerPhone")
    public void setPhone(String phone) {
        this._phone = phone;
    }

    @JsonIgnore
    public boolean getNotify() {
        return _notify;
    }

    @JsonProperty("buyerNotify")
    public void setNotify(boolean notify) {
        this._notify = notify;
    }

    @JsonIgnore
    public String getState() {
        return _state;
    }

    @JsonProperty("buyerState")
    public void setState(String state) {
        this._state = state;
    }

    @JsonIgnore
    public String getZip() {
        return _zip;
    }

    @JsonProperty("buyerZip")
    public void setZip(String zip) {
        this._zip = zip;
    }

    @JsonIgnore
    public String getCity() {
        return _city;
    }

    @JsonProperty("buyerCity")
    public void setCity(String city) {
        this._city = city;
    }


}
