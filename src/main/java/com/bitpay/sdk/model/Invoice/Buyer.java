/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Allows merchant to pass buyer related information in the invoice object.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-invoices-resource">REST API Invoices</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Buyer {

    private String _name;
    private String _address1;
    private String _address2;
    private String _locality;
    private String _region;
    private String _postalCode;
    private String _country;
    private String _email;
    private String _phone;
    private boolean _notify;

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
        return _name;
    }

    /**
     * Sets Buyer's name.
     *
     * @param name the name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this._name = name;
    }

    /**
     * Gets Buyer's address.
     *
     * @return the address 1
     */
    @JsonProperty("address1")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getAddress1() {
        return _address1;
    }

    /**
     * Sets Buyer's address.
     *
     * @param address1 the address 1
     */
    @JsonProperty("address1")
    public void setAddress1(String address1) {
        this._address1 = address1;
    }

    /**
     * Gets Buyer's appartment or suite number.
     *
     * @return the address 2
     */
    @JsonProperty("address2")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getAddress2() {
        return _address2;
    }

    /**
     * Sets Buyer's appartment or suite number.
     *
     * @param address2 the address 2
     */
    @JsonProperty("address2")
    public void setAddress2(String address2) {
        this._address2 = address2;
    }

    /**
     * Gets Buyer's city or locality.
     *
     * @return the locality
     */
    @JsonProperty("locality")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getLocality() {
        return _locality;
    }

    /**
     * Sets Buyer's city or locality.
     *
     * @param locality the locality
     */
    @JsonProperty("locality")
    public void setLocality(String locality) {
        this._locality = locality;
    }

    /**
     * Gets Buyer's state or province.
     *
     * @return the region
     */
    @JsonProperty("region")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getRegion() {
        return _region;
    }

    /**
     * Sets Buyer's state or province.
     *
     * @param region the region
     */
    @JsonProperty("region")
    public void setRegion(String region) {
        this._region = region;
    }

    /**
     * Gets Buyer's Zip or Postal Code.
     *
     * @return the postal code
     */
    @JsonProperty("postalCode")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getPostalCode() {
        return _postalCode;
    }

    /**
     * Sets Buyer's Zip or Postal Code.
     *
     * @param postalCode the postal code
     */
    @JsonProperty("postalCode")
    public void setPostalCode(String postalCode) {
        this._postalCode = postalCode;
    }

    /**
     * Gets Buyer's Country code. Format ISO 3166-1 alpha-2.
     *
     * @return the country
     */
    @JsonProperty("country")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCountry() {
        return _country;
    }

    /**
     * Sets Buyer's Country code. Format ISO 3166-1 alpha-2.
     *
     * @param country the country
     */
    @JsonProperty("country")
    public void setCountry(String country) {
        this._country = country;
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
        return _email;
    }

    /**
     * Sets Buyer's email address. If provided during invoice creation,
     * this will bypass the email prompt for the consumer when opening the invoice..
     *
     * @param email the email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this._email = email;
    }

    /**
     * Gets Buyer's phone number.
     *
     * @return the phone
     */
    @JsonProperty("phone")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getPhone() {
        return _phone;
    }

    /**
     * Sets Buyer's phone number.
     *
     * @param phone the phone
     */
    @JsonProperty("phone")
    public void setPhone(String phone) {
        this._phone = phone;
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
        return _notify;
    }

    /**
     * Sets notify. Indicates whether a BitPay email confirmation should be sent to the buyer once
     * he has paid the invoice.
     *
     * @param notify the notify
     */
    @JsonProperty("notify")
    public void setNotify(boolean notify) {
        this._notify = notify;
    }

}
