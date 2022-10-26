/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Information collected from the buyer during the process of paying an invoice. Initially this object is empty.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-invoices-resource">REST API Invoices</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceBuyerProvidedInfo {
    private String _name;
    private String _phoneNumber;
    private String _selectedTransactionCurrency;
    private String _emailAddress;
    private String _selectedWallet;
    private String _sms;
    private Boolean _smsVerified;

    /**
     * Instantiates a new Invoice buyer provided info.
     */
    public InvoiceBuyerProvidedInfo() {
    }

    /**
     * Gets name. Populated with the buyer's name address if passed in the buyer object by the merchant.
     *
     * @return the name
     */
    @JsonIgnore
    public String getName() {
        return _name;
    }

    /**
     * Sets name. Populated with the buyer's name address if passed in the buyer object by the merchant.
     *
     * @param name the name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this._name = name;
    }

    /**
     * Gets phone number. Populated with the buyer's phone number if passed in the buyer object by the merchant.
     *
     * @return the phone number
     */
    @JsonIgnore
    public String getPhoneNumber() {
        return _phoneNumber;
    }

    /**
     * Sets phone number. Populated with the buyer's phone number if passed in the buyer object by the merchant.
     *
     * @param phoneNumber the phone number
     */
    @JsonProperty("phoneNumber")
    public void setPhoneNumber(String phoneNumber) {
        this._phoneNumber = phoneNumber;
    }

    /**
     * Gets selected transaction currency. This field will be populated with the cryptocurrency selected to pay
     * the BitPay invoice, current supported values are "BTC", "BCH", "ETH", "GUSD", "PAX", "BUSD", "USDC", "XRP",
     * "DOGE", "DAI" and "WBTC". If not yet selected, this field will not be returned.
     *
     * @return the selected transaction currency
     */
    @JsonIgnore
    public String getSelectedTransactionCurrency() {
        return _selectedTransactionCurrency;
    }

    /**
     * Sets selected transaction currency. This field will be populated with the cryptocurrency selected to pay
     * the BitPay invoice, current supported values are "BTC", "BCH", "ETH", "GUSD", "PAX", "BUSD", "USDC", "XRP",
     * "DOGE", "DAI" and "WBTC". If not yet selected, this field will not be returned.
     *
     * @param selectedTransactionCurrency the selected transaction currency
     */
    @JsonProperty("selectedTransactionCurrency")
    public void setSelectedTransactionCurrency(String selectedTransactionCurrency) {
        this._selectedTransactionCurrency = selectedTransactionCurrency;
    }

    /**
     * Gets email address. Populated with the buyer's email address if passed in the buyer object, otherwise this
     * field is not returned in the response.
     *
     * @return the email address
     */
    @JsonIgnore
    public String getEmailAddress() {
        return _emailAddress;
    }

    /**
     * Sets email address. Populated with the buyer's email address if passed in the buyer object, otherwise this
     * field is not returned in the response.
     *
     * @param emailAddress the email address
     */
    @JsonProperty("emailAddress")
    public void setEmailAddress(String emailAddress) {
        this._emailAddress = emailAddress;
    }

    /**
     * Gets selected wallet. This field contains the name of the cryptocurrency wallet selected by the shopper to
     * complete the payment.
     *
     * @return the selected wallet
     */
    @JsonIgnore
    public String getSelectedWallet() {
        return _selectedWallet;
    }

    /**
     * Sets selected wallet. This field contains the name of the cryptocurrency wallet selected by the shopper to
     * complete the payment.
     *
     * @param selectedWallet the selected wallet
     */
    @JsonProperty("selectedWallet")
    public void setSelectedWallet(String selectedWallet) {
        this._selectedWallet = selectedWallet;
    }

    /**
     * Gets SMS provided by user for communications. This is only used for instances where a buyers email
     * (primary form of buyer communication) is can not be gathered.
     *
     * @return the sms
     */
    @JsonIgnore
    public String getSms() {
        return _sms;
    }

    /**
     * Sets SMS provided by user for communications. This is only used for instances where a buyers email
     * (primary form of buyer communication) is can not be gathered.
     *
     * @param sms the sms
     */
    @JsonProperty("sms")
    public void setSms(String sms) {
        this._sms = sms;
    }

    /**
     * Gets verification status of SMS (ie. have they passed the challenge).
     *
     * @return the sms verified
     */
    @JsonIgnore
    public Boolean getSmsVerified() {
        return _smsVerified;
    }

    /**
     * Sets verification status of SMS (ie. have they passed the challenge).
     *
     * @param smsVerified the sms verified
     */
    @JsonProperty("smsVerified")
    public void setSmsVerified(Boolean smsVerified) {
        this._smsVerified = smsVerified;
    }
}
