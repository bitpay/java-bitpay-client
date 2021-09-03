package com.bitpay.sdk.model.Wallet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyQr {
    private String _type;
    private Boolean _collapsed;

    public CurrencyQr() {
    }

    @JsonIgnore
    public String getType() {
        return _type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this._type = type;
    }

    @JsonIgnore
    public Boolean getCollapsed() { return _collapsed; }

    @JsonProperty("collapsed")
    public void setCollapsed(Boolean collapsed) {
        this._collapsed = collapsed;
    }
}
