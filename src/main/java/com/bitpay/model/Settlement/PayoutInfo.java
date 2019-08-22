package com.bitpay.model.Settlement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PayoutInfo {
    private String _name;
    private String _account;
    private String _routing;
    private String _merchantEin;
    private String _label;
    private String _bankCountry;

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
}
