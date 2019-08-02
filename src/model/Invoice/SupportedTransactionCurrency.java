package model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SupportedTransactionCurrency {

    private boolean _enabled;

    public SupportedTransactionCurrency() {}

    @JsonIgnore
    public boolean getEnabled() {
        return _enabled;
    }

    @JsonProperty("enabled")
    public void setNotify(boolean enabled) {
        this._enabled = enabled;
    }
}
