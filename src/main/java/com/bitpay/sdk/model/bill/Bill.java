/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.bill;

import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.Currency;
import com.bitpay.sdk.model.ModelConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * <p>Bills are payment requests addressed to specific buyers.</p>
 * <p>Bill line items have fixed prices, typically denominated in fiat currency.</p>
 *
 * @see <a href="https://developer.bitpay.com/reference/bills">REST API Bills</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bill {

    private String currency = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String token = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String email = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private List<Item> items;
    private String number = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String name = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String address1 = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String address2 = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String city = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String state = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String zip = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String country = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private List<String> cc;
    private String phone = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String dueDate = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private Boolean passProcessingFee;
    private String status = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String url = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private ZonedDateTime createdDate;
    private String id = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    private String merchant = ModelConfiguration.DEFAULT_NON_SENT_VALUE;

    /**
     * Constructor, create an empty Bill object.
     */
    public Bill() {
    }

    /**
     * Constructor, create a minimal request Bill object.
     *
     * @param number   A string for tracking purposes.
     * @param currency The three digit currency type used to compute the bill's amount.
     * @param email    The email address of the receiver for this bill.
     * @param items    The list of itens to add to this bill.
     */
    public Bill(
        final String number,
        final String currency,
        final String email,
        final List<Item> items
    ) {
        this.number = number;
        this.currency = currency;
        this.email = email;
        this.items = items;
    }

    // API fields
    //

    /**
     * <p>Gets API token for bill resource.</p>
     * <p>
     * This token is actually derived from the API token used to create the bill
     * and is tied to the specific resource id created.
     * </p>
     *
     * @return the token
     */
    @JsonProperty("token")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getToken() {
        return this.token;
    }

    /**
     * <p>Sets API token for bill resource.</p>
     * <p>
     * This token is actually derived from the API token used to create the bill
     * and is tied to the specific resource id created.
     * </p>
     *
     * @param token the token
     *
     */
    @JsonProperty("token")
    public void setToken(final String token) {
        this.token = token;
    }

    /**
     * Gets ISO 4217 3-character currency code. This is the currency associated with the price field
     *
     * @return the currency
     */
    // Required fields
    //
    @JsonProperty("currency")
    public String getCurrency() {
        return this.currency;
    }

    /**
     * Sets ISO 4217 3-character currency code. This is the currency associated with the price field.
     *
     * @param currency the currency
     * @throws BitPayGenericException the bit pay exception
     */
    @JsonProperty("currency")
    public void setCurrency(final String currency) throws BitPayGenericException {
        if (!Currency.isValid(currency)) {
            BitPayExceptionProvider.throwInvalidCurrencyException(currency);
        }

        this.currency = currency;
    }

    /**
     * Gets Bill recipient's email address.
     *
     * @return the email
     */
    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets Bill recipient's email address.
     *
     * @param email the email
     */
    @JsonProperty("email")
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets list of line items.
     *
     * @return the items
     */
    @JsonProperty("items")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<Item> getItems() {
        return this.items;
    }

    /**
     * Sets list of line items.
     *
     * @param items the items
     */
    @JsonProperty("items")
    public void setItems(final List<Item> items) {
        this.items = items;
    }

    // Optional fields
    //

    /**
     * Gets Bill identifier, specified by merchant.
     *
     * @return the number
     */
    @JsonProperty("number")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getNumber() {
        return this.number;
    }

    /**
     * Sets Bill identifier, specified by merchant.
     *
     * @param number the number
     */
    @JsonProperty("number")
    public void setNumber(final String number) {
        this.number = number;
    }

    /**
     * Gets Bill recipient's name.
     *
     * @return the name
     */
    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getName() {
        return this.name;
    }

    /**
     * Sets Bill recipient's name.
     *
     * @param name the name
     */
    @JsonProperty("name")
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets Bill recipient's address 1.
     *
     * @return the address 1
     */
    @JsonProperty("address1")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getAddress1() {
        return this.address1;
    }

    /**
     * Sets Bill recipient's address 1.
     *
     * @param address1 the address 1
     */
    @JsonProperty("address1")
    public void setAddress1(final String address1) {
        this.address1 = address1;
    }

    /**
     * Gets Bill recipient's address 2.
     *
     * @return the address 2
     */
    @JsonProperty("address2")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getAddress2() {
        return this.address2;
    }

    /**
     * Sets Bill recipient's address 2.
     *
     * @param address2 the address 2
     */
    @JsonProperty("address2")
    public void setAddress2(final String address2) {
        this.address2 = address2;
    }

    /**
     * Gets Bill recipient's city.
     *
     * @return the city
     */
    @JsonProperty("city")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCity() {
        return this.city;
    }

    /**
     * Sets Bill recipient's city.
     *
     * @param city the city
     */
    @JsonProperty("city")
    public void setCity(final String city) {
        this.city = city;
    }

    /**
     * Gets Bill recipient's state or province.
     *
     * @return the state
     */
    @JsonProperty("state")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getState() {
        return this.state;
    }

    /**
     * Sets Bill recipient's state or province.
     *
     * @param state the state
     */
    @JsonProperty("state")
    public void setState(final String state) {
        this.state = state;
    }

    /**
     * Gets recipient's ZIP code.
     *
     * @return the zip
     */
    @JsonProperty("zip")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getZip() {
        return this.zip;
    }

    /**
     * Sets recipient's ZIP code.
     *
     * @param zip the zip
     */
    @JsonProperty("zip")
    public void setZip(final String zip) {
        this.zip = zip;
    }

    /**
     * Gets Bill recipient's country.
     *
     * @return the country
     */
    @JsonProperty("country")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCountry() {
        return this.country;
    }

    /**
     * Sets Bill recipient's country.
     *
     * @param country the country
     */
    @JsonProperty("country")
    public void setCountry(final String country) {
        this.country = country;
    }

    /**
     * Gets email addresses to which a copy of the bill must be sent.
     *
     * @return the cc
     */
    @JsonProperty("cc")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<String> getCc() {
        return this.cc;
    }

    /**
     * Sets email addresses to which a copy of the bill must be sent.
     *
     * @param cc the cc
     */
    @JsonProperty("cc")
    public void setCc(final List<String> cc) {
        this.cc = cc;
    }

    /**
     * Gets Bill recipient's phone number.
     *
     * @return the phone
     */
    @JsonProperty("phone")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getPhone() {
        return this.phone;
    }

    /**
     * Sets Bill recipient's phone number.
     *
     * @param phone the phone
     */
    @JsonProperty("phone")
    public void setPhone(final String phone) {
        this.phone = phone;
    }

    /**
     * Gets date and time at which a bill is due, ISO-8601 format yyyy-mm-ddThh:mm:ssZ. (UTC).
     *
     * @return the due date
     */
    @JsonProperty("dueDate")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getDueDate() {
        return this.dueDate;
    }

    /**
     * Sets date and time at which a bill is due, ISO-8601 format yyyy-mm-ddThh:mm:ssZ. (UTC).
     *
     * @param dueDate the due date
     */
    @JsonProperty("dueDate")
    public void setDueDate(final String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets if set to true, BitPay's processing fee will be included in the amount charged on the invoice.
     *
     * @return the pass processing fee
     */
    @JsonProperty("passProcessingFee")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Boolean getPassProcessingFee() {
        return this.passProcessingFee;
    }

    /**
     * Sets if set to true, BitPay's processing fee will be included in the amount charged on the invoice.
     *
     * @param passProcessingFee the pass processing fee
     */
    @JsonProperty("passProcessingFee")
    public void setPassProcessingFee(final Boolean passProcessingFee) {
        this.passProcessingFee = passProcessingFee;
    }

    // Response fields
    //

    /**
     * Gets can "draft", "sent", "new", "paid", or "complete".
     *
     * @return the status
     */
    @JsonIgnore
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets can "draft", "sent", "new", "paid", or "complete".
     *
     * @param status the status
     */
    @JsonProperty("status")
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Gets web address of bill.
     *
     * @return the url
     */
    @JsonIgnore
    public String getUrl() {
        return this.url;
    }

    /**
     * Sets web address of bill.
     *
     * @param url the url
     */
    @JsonProperty("url")
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * Gets date and time of Bill creation, ISO-8601 format yyyy-mm-ddThh:mm:ssZ. (UTC).
     *
     * @return the create date
     */
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    /**
     * Sets date and time of Bill creation, ISO-8601 format yyyy-mm-ddThh:mm:ssZ. (UTC).
     *
     * @param createdDate the create date
     */
    @JsonProperty("createdDate")
    public void setCreatedDate(final ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets Bill resource id.
     *
     * @return the id
     */
    @JsonIgnore
    public String getId() {
        return this.id;
    }

    /**
     * Sets Bill resource id.
     *
     * @param id the id
     */
    @JsonProperty("id")
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Gets internal identifier for BitPay, this field can be ignored by the merchants.
     *
     * @return the merchant
     */
    @JsonIgnore
    public String getMerchant() {
        return this.merchant;
    }

    /**
     * Sets internal identifier for BitPay, this field can be ignored by the merchants.
     *
     * @param merchant the merchant
     */
    @JsonProperty("merchant")
    public void setMerchant(final String merchant) {
        this.merchant = merchant;
    }
}
