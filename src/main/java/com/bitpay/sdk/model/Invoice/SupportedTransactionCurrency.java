package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SupportedTransactionCurrency {

    private boolean _enabled;
    private String _reason;

    public SupportedTransactionCurrency() {
    }

    @JsonIgnore
    public boolean getEnabled() {
        return _enabled;
    }

    @JsonProperty("enabled")
    public void setEnabled(boolean enabled) {
        this._enabled = enabled;
    }

    @JsonIgnore
    public String getReason() { return _reason; }

    @JsonProperty("reason")
    public void setReason(String reason) {
        this._reason = reason;
    }
}
