package com.bitpay.sdk.model.Ledger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ledger {

    private List<LedgerEntry> _entries;
    private String _currency;
    private Double _balance;

    public Ledger() {
    }

    @JsonIgnore
    public List<LedgerEntry> getEntries() {
        return _entries;
    }

    @JsonProperty("Entries")
    public void setEntries(List<LedgerEntry> entries) {
        this._entries = entries;
    }

    @JsonIgnore
    public String getCurrency() {
        return _currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this._currency = currency;
    }

    @JsonIgnore
    public Double getBalance() {
        return _balance;
    }

    @JsonProperty("balance")
    public void setBalance(Double balance) {
        this._balance = balance;
    }
}
