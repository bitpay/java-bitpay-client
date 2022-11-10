/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk.model.Rate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * Rate is a class that represents a currency name, currency code, and value,
 * and is used to create an object that is used when fetching exchange rates
 * from the API.
 * </p>
 * <p>
 * Here's an example of the JSON object that's created:
 * </p>
 * <pre>
 * {
 *   "code": "BTC",
 *   "name": "Bitcoin",
 *   "rate": 1
 * }
 * </pre>
 * 
 * @see com.bitpay.sdk.Client#getRates()
 * @see com.bitpay.sdk.model.Rate.Rates
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate {

    /**
     * An ISO 4217 currency code or cryptocurrency code
     * 
     * Refer to <a href="https://www.iso.org/iso-4217-currency-codes.html">ISO Standards</a>
     * for a list of ISO 4217 currency codes.
     * 
     */
    private String code;

    /**
     * The name of the currency or cryptocurrency
     * 
     * Refer to <a href="https://www.iso.org/iso-4217-currency-codes.html">ISO Standards</a>
     * for a list of ISO 4217 currency codes.
     */
    private String name;

    /**
     * The value of the rate, returned in the JSON as "rate" and to a precision
     * of two decimal places.
     */
    private Double value;

    /**
     * Class constructor.
     */
    public Rate() {
    }

    /**
     * Returns the name of the rate's currency or cryptocurrency.
     *
     * @return the name of the rate's currency or cryptocurrency
     */
    @JsonIgnore
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the rate's currency or cryptocurrency.
     *
     * @param name the name of the rate
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the rate's ISO 4217 currency code or cryptocurrency code.
     *
     * @return the rate's ISO 4217 currency code or cryptocurrency code
     */
    @JsonIgnore
    public String getCode() {
        return this.code;
    }

    /**
     * Sets the rate's ISO 4217 currency code or cryptocurrency code.
     *
     * @param code the rate's ISO 4217 currency code or cryptocurrency code
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Returns the numeric value of the rate.
     *
     * @return the numeric value of the rate, to two decimal places
     */
    @JsonIgnore
    public Double getValue() {
        return this.value;
    }

    /**
     * Sets the numeric value of the rate.
     *
     * @param value the numeric value of the rate, to two decimal places
     */
    @JsonProperty("rate")
    public void setValue(Double value) {
        this.value = value;
    }

}
