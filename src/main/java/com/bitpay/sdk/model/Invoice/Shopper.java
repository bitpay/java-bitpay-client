package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Shopper {

    private String _user;

    public Shopper() {
    }

    @JsonIgnore
    public String getName() {
        return _user;
    }

    @JsonProperty("user")
    public void setName(String user) {
        this._user = user;
    }
}
