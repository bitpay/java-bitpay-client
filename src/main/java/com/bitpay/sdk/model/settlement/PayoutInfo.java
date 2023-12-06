/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.settlement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Payout info.
 * Object containing the settlement info provided by the Merchant in his BitPay account settings.
 *
 * @see <a href="https://bitpay.readme.io/reference/settlements">Settlements</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutInfo {
    private String name;
    private String account;
    private String routing;
    private String merchantEin;
    private String label;
    private String bankCountry;
    private String bank;
    private String swift;
    private String address;
    private String city;
    private String postal;
    private String sort;
    private boolean wire;
    private String bankName;
    private String bankAddress;
    private String bankAddress2;
    private String iban;
    private String additionalInformation;
    private String accountHolderName;
    private String accountHolderAddress;
    private String accountHolderAddress2;
    private String accountHolderPostalCode;
    private String accountHolderCity;
    private String accountHolderCountry;

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
        return this.name;
    }

    /**
     * Sets account holder name.
     *
     * @param name the name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets account.
     *
     * @return the account
     */
    @JsonIgnore
    public String getAccount() {
        return this.account;
    }

    /**
     * Sets account.
     *
     * @param account the account
     */
    @JsonProperty("account")
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * Gets routing. For merchants receiving USD settlements via local ACH,
     * this field contains the ABA provided by the merchant.
     *
     * @return the routing
     */
    @JsonIgnore
    public String getRouting() {
        return this.routing;
    }

    /**
     * Sets routing. For merchants receiving USD settlements via local ACH,
     * this field contains the ABA provided by the merchant.
     *
     * @param routing the routing
     */
    @JsonProperty("routing")
    public void setRouting(String routing) {
        this.routing = routing;
    }

    /**
     * Gets merchant ein.
     *
     * @return the merchant ein
     */
    @JsonIgnore
    public String getMerchantEin() {
        return this.merchantEin;
    }

    /**
     * Sets merchant ein.
     *
     * @param merchantEin the merchant ein
     */
    @JsonProperty("merchantEin")
    public void setMerchantEin(String merchantEin) {
        this.merchantEin = merchantEin;
    }

    /**
     * Gets label. As indicated by the merchant in his settlement settings.
     *
     * @return the label
     */
    @JsonIgnore
    public String getLabel() {
        return this.label;
    }

    /**
     * Sets label. As indicated by the merchant in his settlement settings.
     *
     * @param label the label
     */
    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets country where the merchant's bank account is located.
     *
     * @return the bank country
     */
    @JsonIgnore
    public String getBankCountry() {
        return this.bankCountry;
    }

    /**
     * Sets country where the merchant's bank account is located.
     *
     * @param bankCountry the bank country
     */
    @JsonProperty("bankCountry")
    public void setBankCountry(String bankCountry) {
        this.bankCountry = bankCountry;
    }

    /**
     * Gets name of the bank used by the merchant.
     *
     * @return the bank
     */
    @JsonIgnore
    public String getBank() {
        return this.bank;
    }

    /**
     * Sets name of the bank used by the merchant.
     *
     * @param bank the bank
     */
    @JsonProperty("bank")
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * Gets SWIFT/BIC code of the merchant's bank.
     *
     * @return the swift
     */
    @JsonIgnore
    public String getSwift() {
        return this.swift;
    }

    /**
     * Sets SWIFT/BIC code of the merchant's bank.
     *
     * @param swift the swift
     */
    @JsonProperty("swift")
    public void setSwift(String swift) {
        this.swift = swift;
    }

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
    public String getAddress() {
        return this.address;
    }

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
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets city of the merchant bank, field return if the settlement currency is.
     *
     * @return the city
     */
    @JsonIgnore
    public String getCity() {
        return this.city;
    }

    /**
     * Sets city of the merchant bank, field return if the settlement currency is.
     *
     * @param city the city
     */
    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets postal code of the merchant bank, field return if the settlement currency is.
     *
     * @return the postal
     */
    @JsonIgnore
    public String getPostal() {
        return this.postal;
    }

    /**
     * Sets postal code of the merchant bank, field return if the settlement currency is.
     *
     * @param postal the postal
     */
    @JsonProperty("postal")
    public void setPostal(String postal) {
        this.postal = postal;
    }

    /**
     * Gets sort used to pass country specific bank fields: BSB for AUD.
     *
     * @return the sort
     */
    @JsonIgnore
    public String getSort() {
        return this.sort;
    }

    /**
     * Sets sort used to pass country specific bank fields: BSB for AUD.
     *
     * @param sort the sort
     */
    @JsonProperty("sort")
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * Gets wire.
     * If set to true, this means BitPay will be settling the account using an international transfer
     * via the SWIFT network instead of local settlement methods like ACH(United States)
     * or SEPA (European Economic Area).
     *
     * @return the wire
     */
    @JsonIgnore
    public boolean getWire() {
        return this.wire;
    }

    /**
     * Sets wire.
     * If set to true, this means BitPay will be settling the account using an international transfer
     * via the SWIFT network instead of local settlement methods like ACH(United States)
     * or SEPA (European Economic Area).
     *
     * @param wire the wire
     */
    @JsonProperty("wire")
    public void setWire(boolean wire) {
        this.wire = wire;
    }

    /**
     * Gets bank name. Name of the bank used by the merchant. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the bank name
     */
    @JsonIgnore
    public String getBankName() {
        return this.bankName;
    }

    /**
     * Sets bank name. Name of the bank used by the merchant. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @param bankName the bank name
     */
    @JsonProperty("bankName")
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * Gets bank address of the merchant's bank. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the bank address
     */
    @JsonIgnore
    public String getBankAddress() {
        return this.bankAddress;
    }

    /**
     * Sets bank address of the merchant's bank. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @param bankAddress the bank address
     */
    @JsonProperty("bankAddress")
    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    /**
     * Gets bank address 2 of the merchant's bank. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the bank address 2
     */
    @JsonIgnore
    public String getBankAddress2() {
        return this.bankAddress2;
    }

    /**
     * Sets bank address 2 of the merchant's bank. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @param bankAddress2 the bank address 2
     */
    @JsonProperty("bankAddress2")
    public void setBankAddress2(String bankAddress2) {
        this.bankAddress2 = bankAddress2;
    }

    /**
     * Gets iban. The merchant's bank account number, in the IBAN (International Bank Account Number) format.
     * Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the iban
     */
    @JsonIgnore
    public String getIban() {
        return this.iban;
    }

    /**
     * Sets iban. The merchant's bank account number, in the IBAN (International Bank Account Number) format.
     * Field returned if "wire": true in the "payoutInfo" object
     *
     * @param iban the iban
     */
    @JsonProperty("iban")
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * Gets additional information. When providing the settlement info via the dashboard,
     * this field can be used by the merchant to provide additional information about the receiving bank.
     * Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the additional information
     */
    @JsonIgnore
    public String getAdditionalInformation() {
        return this.additionalInformation;
    }

    /**
     * Sets additional information. When providing the settlement info via the dashboard,
     * this field can be used by the merchant to provide additional information about the receiving bank.
     *
     * @param additionalInformation the additional information
     */
    @JsonProperty("additionalInformation")
    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    /**
     * Gets bank account holder name. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the account holder name
     */
    @JsonIgnore
    public String getAccountHolderName() {
        return this.accountHolderName;
    }

    /**
     * Sets bank account holder name.
     *
     * @param accountHolderName the account holder name
     */
    @JsonProperty("accountHolderName")
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    /**
     * Gets bank account holder address. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the account holder address
     */
    @JsonIgnore
    public String getAccountHolderAddress() {
        return this.accountHolderAddress;
    }

    /**
     * Sets bank account holder address.
     *
     * @param accountHolderAddress the account holder address
     */
    @JsonProperty("accountHolderAddress")
    public void setAccountHolderAddress(String accountHolderAddress) {
        this.accountHolderAddress = accountHolderAddress;
    }

    /**
     * Gets bank account holder address 2. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the account holder address 2
     */
    @JsonIgnore
    public String getAccountHolderAddress2() {
        return this.accountHolderAddress2;
    }

    /**
     * Sets bank account holder address 2.
     *
     * @param accountHolderAddress2 the account holder address 2
     */
    @JsonProperty("accountHolderAddress2")
    public void setAccountHolderAddress2(String accountHolderAddress2) {
        this.accountHolderAddress2 = accountHolderAddress2;
    }

    /**
     * Gets bank account holder postal code. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the account holder postal code
     */
    @JsonIgnore
    public String getAccountHolderPostalCode() {
        return this.accountHolderPostalCode;
    }

    /**
     * Sets bank account holder postal code.
     *
     * @param accountHolderPostalCode the account holder postal code
     */
    @JsonProperty("accountHolderPostalCode")
    public void setAccountHolderPostalCode(String accountHolderPostalCode) {
        this.accountHolderPostalCode = accountHolderPostalCode;
    }

    /**
     * Gets bank account holder city. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the account holder city
     */
    @JsonIgnore
    public String getAccountHolderCity() {
        return this.accountHolderCity;
    }

    /**
     * Sets bank account holder city.
     *
     * @param accountHolderCity the account holder city
     */
    @JsonProperty("accountHolderCity")
    public void setAccountHolderCity(String accountHolderCity) {
        this.accountHolderCity = accountHolderCity;
    }

    /**
     * Gets bank account holder country. Field returned if "wire": true in the "payoutInfo" object.
     *
     * @return the account holder country
     */
    @JsonIgnore
    public String getAccountHolderCountry() {
        return this.accountHolderCountry;
    }

    /**
     * Sets bank account holder country.
     *
     * @param accountHolderCountry the account holder country
     */
    @JsonProperty("accountHolderCountry")
    public void setAccountHolderCountry(String accountHolderCountry) {
        this.accountHolderCountry = accountHolderCountry;
    }
}
