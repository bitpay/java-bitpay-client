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

    private List<LedgerEntry> entries;
    private String currency;
    private Double balance;

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
        return this.entries;
    }

    /**
     * Sets Ledger entries.
     *
     * @param entries the entries
     */
    @JsonProperty("Entries")
    public void setEntries(List<LedgerEntry> entries) {
        this.entries = entries;
    }

    /**
     * Gets ISO 4217 3-character currency code for your merchant account.
     *
     * @return the currency
     */
    @JsonIgnore
    public String getCurrency() {
        return this.currency;
    }

    /**
     * Sets ISO 4217 3-character currency code for your merchant account.
     *
     * @param currency the currency
     */
    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets balance of the ledger.
     *
     * @return the balance
     */
    @JsonIgnore
    public Double getBalance() {
        return this.balance;
    }

    /**
     * Sets balance of the ledger.
     *
     * @param balance the balance
     */
    @JsonProperty("balance")
    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
