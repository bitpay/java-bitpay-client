/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.ledger;

import com.bitpay.sdk.model.ModelConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Buyer.
 *
 * @see <a href="https://bitpay.readme.io/reference/ledgers">REST API Ledgers</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Buyer {

    private String name = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String address1 = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String address2 = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String city = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String state = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String zip = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String country = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String phone = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private boolean notify;
    private String email = ModelConfiguration.DEFAULT_NON_SENT_VALUE;

    /**
     * Instantiates a new Buyer.
     */
    public Buyer() {
    }

    /**
     * Gets Buyer's name.
     *
     * @return the name
     *
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getName() {
        return this.name;
    }

    /**
     * Sets Buyer's name.
     *
     * @param name the name
     */
    @JsonProperty("buyerName")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets Buyer's address.
     *
     * @return the address 1
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getAddress1() {
        return this.address1;
    }

    /**
     * Sets Buyer's address.
     *
     * @param address1 the address 1
     */
    @JsonProperty("buyerAddress1")
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * Gets Buyer's appartment or suite number.
     *
     * @return the address 2
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getAddress2() {
        return this.address2;
    }

    /**
     * Sets Buyer's appartment or suite number.
     *
     * @param address2 the address 2
     */
    @JsonProperty("buyerAddress2")
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCountry() {
        return this.country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    @JsonProperty("buyerCountry")
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets Buyer's phone number.
     *
     * @return the phone
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getPhone() {
        return this.phone;
    }

    /**
     * Sets Buyer's phone number.
     *
     * @param phone the phone
     */
    @JsonProperty("buyerPhone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets notify.
     * Indicates whether a BitPay email confirmation should be sent to the buyer once he has paid the invoice.
     *
     * @return the notify
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean getNotify() {
        return this.notify;
    }

    /**
     * Sets notify.
     * Indicates whether a BitPay email confirmation should be sent to the buyer once he has paid the invoice.
     *
     * @param notify the notify
     */
    @JsonProperty("buyerNotify")
    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    /**
     * Gets Buyer's state.
     *
     * @return the state
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getState() {
        return this.state;
    }

    /**
     * Sets Buyer's state.
     *
     * @param state the state
     */
    @JsonProperty("buyerState")
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets Buyer's Zip.
     *
     * @return the zip
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getZip() {
        return this.zip;
    }

    /**
     * Sets Buyer's Zip.
     *
     * @param zip the zip
     */
    @JsonProperty("buyerZip")
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Gets Buyer's city.
     *
     * @return the city
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCity() {
        return this.city;
    }

    /**
     * Sets Buyer's city.
     *
     * @param city the city
     */
    @JsonProperty("buyerCity")
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets Buyer's email address.
     *
     * @return the email
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets Buyer's email address.
     *
     * @param email the email
     */
    @JsonProperty("buyerEmail")
    public void setEmail(String email) {
        this.email = email;
    }
}
