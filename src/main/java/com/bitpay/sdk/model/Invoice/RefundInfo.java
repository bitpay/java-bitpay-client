package com.bitpay.sdk.model.Invoice;

import com.bitpay.sdk.BitPayException;
import com.bitpay.sdk.model.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Hashtable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundInfo {

    private String _supportRequest;
    private String _currency;
    private Hashtable<String, Double> _amounts;

    public RefundInfo() {
    }

    @JsonIgnore
    public String getSupportRequest() {
        return _supportRequest;
    }

    @JsonProperty("supportRequest")
    public void setSupportRequest(String supportRequest) {
        this._supportRequest = supportRequest;
    }

    @JsonIgnore
    public String getCurrency() {
        return _currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) throws BitPayException {
        if (!Currency.isValid(currency))
            throw new BitPayException("Error: currency code must be a type of Model.Currency");

        this._currency = currency;
    }

    @JsonIgnore
    public Hashtable<String, Double> getAmounts() {
        return _amounts;
    }

    @JsonProperty("amounts")
    public void setAmounts(Hashtable<String, Double> amounts) {
        this._amounts = amounts;
    }

}
