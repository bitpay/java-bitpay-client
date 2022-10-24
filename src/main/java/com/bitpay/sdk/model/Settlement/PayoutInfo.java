/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk.model.Settlement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Payout info.
 * Object containing the settlement info provided by the Merchant in his BitPay account settings.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-settlements">Settlements</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutInfo {
    private String _name;
    private String _account;
    private String _routing;
    private String _merchantEin;
    private String _label;
    private String _bankCountry;
    private String _bank;
    private String _swift;
    private String _address;
    private String _city;
    private String _postal;
    private String _sort;
    private String _wire;
    private String _bankName;
    private String _bankAddress;
    private String _bankAddress2;
    private String _iban;
    private String _additionalInformation;
    private String _accountHolderName;
    private String _accountHolderAddress;
    private String _accountHolderAddress2;
    private String _accountHolderPostalCode;
    private String _accountHolderCity;
    private String _accountHolderCountry;

    /**
     * Instantiates a new Payout info.
     */
    public PayoutInfo() {
    }

    /**
     * Gets account holder name.
     *
     * @return the name
     */
    @JsonIgnore
    public String getName() {
        return _name;
    }

    /**
     * Sets account holder name.
     *
     * @param name the name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this._name = name;
    }

    /**
     * Gets account.
     *
     * @return the account
     */
    @JsonIgnore
    public String getAccount() {
        return _account;
    }

    /**
     * Sets account.
     *
     * @param account the account
     */
    @JsonProperty("account")
    public void setAccount(String account) {
        this._account = account;
    }

    /**
     * Gets routing. For merchants receiving USD settlements via local ACH,
     * this field contains the ABA provided by the merchant.
     *
     * @return the routing
     */
    @JsonIgnore
    public String getRouting() {
        return _routing;
    }

    /**
     * Sets routing. For merchants receiving USD settlements via local ACH,
     * this field contains the ABA provided by the merchant.
     *
     * @param routing the routing
     */
    @JsonProperty("routing")
    public void setRouting(String routing) {
        this._routing = routing;
    }

    /**
     * Gets merchant ein.
     *
     * @return the merchant ein
     */
    @JsonIgnore
    public String getMerchantEin() {
        return _merchantEin;
    }

    /**
     * Sets merchant ein.
     *
     * @param merchantEin the merchant ein
     */
    @JsonProperty("merchantEin")
    public void setMerchantEin(String merchantEin) {
        this._merchantEin = merchantEin;
    }

    /**
     * It should be replaced by getLabel().
     *
     * @return the label
     */
    @JsonIgnore
    @Deprecated
    public String getlabel() {
        return _label;
    }

    /**
     * Gets label. As indicated by the merchant in his settlement settings.
     *
     * @return the label
     */
    @JsonIgnore
    public String getLabel() {
        return _label;
    }

    /**
     * It should be replaced by setLabel().
     *
     * @param label the label
     */
    @JsonProperty("label")
    @Deprecated
    public void setlabel(String label) {
        this._label = label;
    }

    /**
     * Sets label. As indicated by the merchant in his settlement settings.
     *
     * @param label the label
     */
    @JsonProperty("label")
    public void setLabel(String label) {
        this._label = label;
    }

    /**
     * Gets country where the merchant's bank account is located.
     *
     * @return the bank country
     */
    @JsonIgnore
    public String getBankCountry() {
        return _bankCountry;
    }

    /**
     * Sets country where the merchant's bank account is located.
     *
     * @param bankCountry the bank country
     */
    @JsonProperty("bankCountry")
    public void setBankCountry(String bankCountry) {
        this._bankCountry = bankCountry;
    }

    /**
     * Gets name of the bank used by the merchant.
     *
     * @return the bank
     */
    @JsonIgnore
    public String getBank() { return _bank; }

    /**
     * Sets name of the bank used by the merchant.
     *
     * @param bank the bank
     */
    @JsonProperty("bank")
    public void setBank(String bank) { this._bank = bank; }

    /**
     * Gets SWIFT/BIC code of the merchant's bank.
     *
     * @return the swift
     */
    @JsonIgnore
    public String getSwift() { return _swift; }

    /**
     * Sets SWIFT/BIC code of the merchant's bank.
     *
     * @param swift the swift
     */
    @JsonProperty("swift")
    public void setSwift(String swift) { this._swift = swift; }

    /**
     * Gets address. This field is used to indicate the wallet address used for the settlement,
     * if the settlement currency selected by the merchant is one of the supported crypto currency:
     * Bitcoin (BTC), Bitcoin Cash (BCH), Paxos Standard (PAX), Gemini Dollar (GUSD), Circle USD coin (USDC),
     * Binance USD (BUSD) or Ripple (XRP).
     * If the settlement currency used is AUD, GBP, NZD, MXN, ZAR -
     * this field is used to indicate the address of the merchant's bank
     *
     * @return the address
     */
    @JsonIgnore
    public String getAddress() { return _address; }

    /**
     * Sets address. This field is used to indicate the wallet address used for the settlement,
     * if the settlement currency selected by the merchant is one of the supported crypto currency:
     * Bitcoin (BTC), Bitcoin Cash (BCH), Paxos Standard (PAX), Gemini Dollar (GUSD), Circle USD coin (USDC),
     * Binance USD (BUSD) or Ripple (XRP).
     * If the settlement currency used is AUD, GBP, NZD, MXN, ZAR -
     * this field is used to indicate the address of the merchant's bank
     *
     * @param address the address
     */
    @JsonProperty("address")
    public void setAddress(String address) { this._address = address; }

    /**
     * Gets city of the merchant bank, field return if the settlement currency is.
     *
     * @return the city
     */
    @JsonIgnore
    public String getCity() { return _city; }

    /**
     * Sets city of the merchant bank, field return if the settlement currency is.
     *
     * @param city the city
     */
    @JsonProperty("city")
    public void setCity(String city) { this._city = city; }

    /**
     * Gets postal code of the merchant bank, field return if the settlement currency is.
     *
     * @return the postal
     */
    @JsonIgnore
    public String getPostal() { return _postal; }

    /**
     * Sets postal code of the merchant bank, field return if the settlement currency is.
     *
     * @param postal the postal
     */
    @JsonProperty("postal")
    public void setPostal(String postal) { this._postal = postal; }

    /**
     * Gets sort used to pass country specific bank fields: BSB for AUD.
     *
     * @return the sort
     */
    @JsonIgnore
    public String getSort() { return _sort; }

    /**
     * Sets sort used to pass country specific bank fields: BSB for AUD.
     *
     * @param sort the sort
     */
    @JsonProperty("sort")
    public void setSort(String sort) { this._sort = sort; }

    /**
     * Gets wire.
     * If set to true, this means BitPay will be settling the account using an international transfer
     * via the SWIFT network instead of local settlement methods like ACH(United States)
     * or SEPA (European Economic Area).
     *
     * @return the wire
     */
    @JsonIgnore
    public String getWire() { return _wire; }

    /**
     * Sets wire.
     * If set to true, this means BitPay will be settling the account using an international transfer
     * via the SWIFT network instead of local settlement methods like ACH(United States)
     * or SEPA (European Economic Area).
     *
     * @param wire the wire
     */
    @JsonProperty("wire")
    public void setWire(String wire) { this._wire = wire; }

    /**
     * Gets bank name. Name of the bank used by the merchant. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the bank name
     */
    @JsonIgnore
    public String getBankName() { return _bankName; }

    /**
     * Sets bank name. Name of the bank used by the merchant. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @param bankName the bank name
     */
    @JsonProperty("bankName")
    public void setBankName(String bankName) { this._bankName = bankName; }

    /**
     * Gets bank address of the merchant's bank. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the bank address
     */
    @JsonIgnore
    public String getBankAddress() { return _bankAddress; }

    /**
     * Sets bank address of the merchant's bank. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @param bankAddress the bank address
     */
    @JsonProperty("bankAddress")
    public void setBankAddress(String bankAddress) { this._bankAddress = bankAddress; }

    /**
     * Gets bank address 2 of the merchant's bank. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the bank address 2
     */
    @JsonIgnore
    public String getBankAddress2() { return _bankAddress2; }

    /**
     * Sets bank address 2 of the merchant's bank. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @param bankAddress2 the bank address 2
     */
    @JsonProperty("bankAddress2")
    public void setBankAddress2(String bankAddress2) { this._bankAddress2 = bankAddress2; }

    /**
     * Gets iban. The merchant's bank account number, in the IBAN (International Bank Account Number) format.
     * Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the iban
     */
    @JsonIgnore
    public String getIban() { return _iban; }

    /**
     * Sets iban. The merchant's bank account number, in the IBAN (International Bank Account Number) format.
     * Field returned if "wire": true in the "payoutInfo" object
     *
     * @param iban the iban
     */
    @JsonProperty("iban")
    public void setIban(String iban) { this._iban = iban; }

    /**
     * Gets additional information. When providing the settlement info via the dashboard,
     * this field can be used by the merchant to provide additional information about the receiving bank.
     * Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the additional information
     */
    @JsonIgnore
    public String getAdditionalInformation() { return _additionalInformation; }

    /**
     * Sets additional information. When providing the settlement info via the dashboard,
     * this field can be used by the merchant to provide additional information about the receiving bank.
     *
     * @param additionalInformation the additional information
     */
    @JsonProperty("additionalInformation")
    public void setAdditionalInformation(String additionalInformation) { this._additionalInformation = additionalInformation; }

    /**
     * Gets bank account holder name. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the account holder name
     */
    @JsonIgnore
    public String getAccountHolderName() { return _accountHolderName; }

    /**
     * Sets bank account holder name.
     *
     * @param accountHolderName the account holder name
     */
    @JsonProperty("accountHolderName")
    public void setAccountHolderName(String accountHolderName) { this._accountHolderName = accountHolderName; }

    /**
     * Gets bank account holder address. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the account holder address
     */
    @JsonIgnore
    public String getAccountHolderAddress() { return _accountHolderAddress; }

    /**
     * Sets bank account holder address.
     *
     * @param accountHolderAddress the account holder address
     */
    @JsonProperty("accountHolderAddress")
    public void setAccountHolderAddress(String accountHolderAddress) { this._accountHolderAddress = accountHolderAddress; }

    /**
     * Gets bank account holder address 2. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the account holder address 2
     */
    @JsonIgnore
    public String getAccountHolderAddress2() { return _accountHolderAddress2; }

    /**
     * Sets bank account holder address 2.
     *
     * @param accountHolderAddress2 the account holder address 2
     */
    @JsonProperty("accountHolderAddress2")
    public void setAccountHolderAddress2(String accountHolderAddress2) { this._accountHolderAddress2 = accountHolderAddress2; }

    /**
     * Gets bank account holder postal code. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the account holder postal code
     */
    @JsonIgnore
    public String getAccountHolderPostalCode() { return _accountHolderPostalCode; }

    /**
     * Sets bank account holder postal code.
     *
     * @param accountHolderPostalCode the account holder postal code
     */
    @JsonProperty("accountHolderPostalCode")
    public void setAccountHolderPostalCode(String accountHolderPostalCode) { this._accountHolderPostalCode = accountHolderPostalCode; }

    /**
     * Gets bank account holder city. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the account holder city
     */
    @JsonIgnore
    public String getAccountHolderCity() { return _accountHolderCity; }

    /**
     * Sets bank account holder city.
     *
     * @param accountHolderCity the account holder city
     */
    @JsonProperty("accountHolderCity")
    public void setAccountHolderCity(String accountHolderCity) { this._accountHolderCity = accountHolderCity; }

    /**
     * Gets bank account holder country. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the account holder country
     */
    @JsonIgnore
    public String getAccountHolderCountry() { return _accountHolderCountry; }

    /**
     * Sets bank account holder country.
     *
     * @param accountHolderCountry the account holder country
     */
    @JsonProperty("accountHolderCountry")
    public void setAccountHolderCountry(String accountHolderCountry) { this._accountHolderCountry = accountHolderCountry; }
}
