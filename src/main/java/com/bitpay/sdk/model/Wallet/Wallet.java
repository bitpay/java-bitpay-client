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
    private String _key;
    private String _displayName;
    private String _avatar;
    private Boolean _payPro;
    private ArrayList<Currencies> _currencies;
    private String _image;

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
        return _key;
    }

    /**
     * Sets a unique identifier for the wallet.
     *
     * @param key the key
     */
    @JsonProperty("key")
    public void setKey(String key) {
        this._key = key;
    }

    /**
     * Gets human readable display name for the wallet.
     *
     * @return the display name
     */
    @JsonIgnore
    public String getDisplayName() {
        return _displayName;
    }

    /**
     * Sets human readable display name for the wallet.
     *
     * @param displayName the display name
     */
    @JsonProperty("displayName")
    public void setDisplayName(String displayName) {
        this._displayName = displayName;
    }

    /**
     * Gets filename of a wallet graphic (not fully qualified).
     *
     * @return the avatar
     */
    @JsonIgnore
    public String getAvatar() {
        return _avatar;
    }

    /**
     * Sets filename of a wallet graphic (not fully qualified).
     *
     * @param avatar the avatar
     */
    @JsonProperty("avatar")
    public void setAvatar(String avatar) {
        this._avatar = avatar;
    }

    /**
     * Gets pay pro. Whether or not the wallet supports ANY BitPay Payment Protocol options.
     *
     * @return the pay pro
     */
    @JsonIgnore
    public Boolean getPayPro() { return _payPro; }

    /**
     * Sets pay pro. Whether or not the wallet supports ANY BitPay Payment Protocol options.
     *
     * @param payPro the pay pro
     */
    @JsonProperty("payPro")
    public void setPayPro(Boolean payPro) {
        this._payPro = payPro;
    }

    /**
     * Gets currencies. Details of what currencies support payments for this wallet.
     *
     * @return the currencies
     */
    @JsonIgnore
    public ArrayList<Currencies> getCurrencies() { return _currencies; }

    /**
     * Sets currencies. Details of what currencies support payments for this wallet.
     *
     * @param currencies the currencies
     */
    @JsonProperty("currencies")
    public void setCurrencies(ArrayList<Currencies> currencies) {
        this._currencies = currencies;
    }

    /**
     * Gets image. URL that displays currency image.
     *
     * @return the image
     */
    @JsonIgnore
    public String getImage() {
        return _image;
    }

    /**
     * Sets image. URL that displays currency image.
     *
     * @param image the image
     */
    @JsonProperty("image")
    public void setImage(String image) {
        this._image = image;
    }
}
