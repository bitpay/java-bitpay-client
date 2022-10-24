package com.bitpay.sdk.model.Ledger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Ledgers are records of money movement.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-ledgers">REST API Ledgers</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ledger {

    private List<LedgerEntry> _entries;
    private String _currency;
    private Double _balance;

    /**
     * Instantiates a new Ledger.
     */
    public Ledger() {
    }

    /**
     * Gets Ledger entries.
     *
     * @return the entries
     */
    @JsonIgnore
    public List<LedgerEntry> getEntries() {
        return _entries;
    }

    /**
     * Sets Ledger entries.
     *
     * @param entries the entries
     */
    @JsonProperty("Entries")
    public void setEntries(List<LedgerEntry> entries) {
        this._entries = entries;
    }

    /**
     * Gets ISO 4217 3-character currency code for your merchant account.
     *
     * @return the currency
     */
    @JsonIgnore
    public String getCurrency() {
        return _currency;
    }

    /**
     * Sets ISO 4217 3-character currency code for your merchant account.
     *
     * @param currency the currency
     */
    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this._currency = currency;
    }

    /**
     * Gets balance of the ledger.
     *
     * @return the balance
     */
    @JsonIgnore
    public Double getBalance() {
        return _balance;
    }

    /**
     * Sets balance of the ledger.
     *
     * @param balance the balance
     */
    @JsonProperty("balance")
    public void setBalance(Double balance) {
        this._balance = balance;
    }
}
