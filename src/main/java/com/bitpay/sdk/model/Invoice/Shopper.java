package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
