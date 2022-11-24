/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk.model.Wallet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * The type Wallet. Currencies are fiat currencies supported by BitPay.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-wallets">Wallets</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Wallet {
    private String key;
    private String displayName;
    private String avatar;
    private Boolean payPro;
    private ArrayList<Currencies> currencies;
    private String image;

    /**
     * Instantiates a new Wallet.
     */
    public Wallet() {
    }

    /**
     * Gets a unique identifier for the wallet.
     *
     * @return the key
     */
    @JsonIgnore
    public String getKey() {
        return this.key;
    }

    /**
     * Sets a unique identifier for the wallet.
     *
     * @param key the key
     */
    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets human readable display name for the wallet.
     *
     * @return the display name
     */
    @JsonIgnore
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Sets human readable display name for the wallet.
     *
     * @param displayName the display name
     */
    @JsonProperty("displayName")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets filename of a wallet graphic (not fully qualified).
     *
     * @return the avatar
     */
    @JsonIgnore
    public String getAvatar() {
        return this.avatar;
    }

    /**
     * Sets filename of a wallet graphic (not fully qualified).
     *
     * @param avatar the avatar
     */
    @JsonProperty("avatar")
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * Gets pay pro. Whether or not the wallet supports ANY BitPay Payment Protocol options.
     *
     * @return the pay pro
     */
    @JsonIgnore
    public Boolean getPayPro() { return this.payPro; }

    /**
     * Sets pay pro. Whether or not the wallet supports ANY BitPay Payment Protocol options.
     *
     * @param payPro the pay pro
     */
    @JsonProperty("payPro")
    public void setPayPro(Boolean payPro) {
        this.payPro = payPro;
    }

    /**
     * Gets currencies. Details of what currencies support payments for this wallet.
     *
     * @return the currencies
     */
    @JsonIgnore
    public ArrayList<Currencies> getCurrencies() { return this.currencies; }

    /**
     * Sets currencies. Details of what currencies support payments for this wallet.
     *
     * @param currencies the currencies
     */
    @JsonProperty("currencies")
    public void setCurrencies(ArrayList<Currencies> currencies) {
        this.currencies = currencies;
    }

    /**
     * Gets image. URL that displays currency image.
     *
     * @return the image
     */
    @JsonIgnore
    public String getImage() {
        return this.image;
    }

    /**
     * Sets image. URL that displays currency image.
     *
     * @param image the image
     */
    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }
}
