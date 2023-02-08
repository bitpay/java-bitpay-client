package com.bitpay.sdk.model.Ledger;

import static com.bitpay.sdk.model.ModelConfiguration.DEFAULT_NON_SENT_VALUE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Ledgers are records of money movement.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-ledgers">REST API Ledgers</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ledger {

    private String currency = DEFAULT_NON_SENT_VALUE;
    private Double balance;

    /**
     * Instantiates a new Ledger.
     */
    public Ledger() {
    }

    /**
     * Gets ISO 4217 3-character currency code for your merchant account.
     *
     * @return the currency
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
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
