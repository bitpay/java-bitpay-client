/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.invoice;

import com.bitpay.sdk.model.ModelConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Allows merchant to pass buyer related information in the invoice object.
 *
 * @see <a href="https://bitpay.readme.io/reference/invoices">REST API Invoices</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Buyer {

    private String name = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String address1 = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String address2 = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String locality = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String region = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String postalCode = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String country = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String email = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String phone = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private boolean notify;

    /**
     * Instantiates a new Buyer.
     */
    public Buyer() {
    }

    /**
     * Gets Buyer's name.
     *
     * @return the name
     */
    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getName() {
        return this.name;
    }

    /**
     * Sets Buyer's name.
     *
     * @param name the name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets Buyer's address.
     *
     * @return the address 1
     */
    @JsonProperty("address1")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getAddress1() {
        return this.address1;
    }

    /**
     * Sets Buyer's address.
     *
     * @param address1 the address 1
     */
    @JsonProperty("address1")
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * Gets Buyer's appartment or suite number.
     *
     * @return the address 2
     */
    @JsonProperty("address2")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getAddress2() {
        return this.address2;
    }

    /**
     * Sets Buyer's appartment or suite number.
     *
     * @param address2 the address 2
     */
    @JsonProperty("address2")
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * Gets Buyer's city or locality.
     *
     * @return the locality
     */
    @JsonProperty("locality")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getLocality() {
        return this.locality;
    }

    /**
     * Sets Buyer's city or locality.
     *
     * @param locality the locality
     */
    @JsonProperty("locality")
    public void setLocality(String locality) {
        this.locality = locality;
    }

    /**
     * Gets Buyer's state or province.
     *
     * @return the region
     */
    @JsonProperty("region")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getRegion() {
        return this.region;
    }

    /**
     * Sets Buyer's state or province.
     *
     * @param region the region
     */
    @JsonProperty("region")
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Gets Buyer's Zip or Postal Code.
     *
     * @return the postal code
     */
    @JsonProperty("postalCode")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getPostalCode() {
        return this.postalCode;
    }

    /**
     * Sets Buyer's Zip or Postal Code.
     *
     * @param postalCode the postal code
     */
    @JsonProperty("postalCode")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets Buyer's Country code. Format ISO 3166-1 alpha-2.
     *
     * @return the country
     */
    @JsonProperty("country")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCountry() {
        return this.country;
    }

    /**
     * Sets Buyer's Country code. Format ISO 3166-1 alpha-2.
     *
     * @param country the country
     */
    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets Buyer's email address. If provided during invoice creation,
     * this will bypass the email prompt for the consumer when opening the invoice..
     *
     * @return the email
     */
    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets Buyer's email address. If provided during invoice creation,
     * this will bypass the email prompt for the consumer when opening the invoice..
     *
     * @param email the email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets Buyer's phone number.
     *
     * @return the phone
     */
    @JsonProperty("phone")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getPhone() {
        return this.phone;
    }

    /**
     * Sets Buyer's phone number.
     *
     * @param phone the phone
     */
    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets notify. Indicates whether a BitPay email confirmation should be sent to the buyer once
     * he has paid the invoice.
     *
     * @return the notify
     */
    @JsonProperty("notify")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean getNotify() {
        return this.notify;
    }

    /**
     * Sets notify. Indicates whether a BitPay email confirmation should be sent to the buyer once
     * he has paid the invoice.
     *
     * @param notify the notify
     */
    @JsonProperty("notify")
    public void setNotify(boolean notify) {
        this.notify = notify;
    }

}
