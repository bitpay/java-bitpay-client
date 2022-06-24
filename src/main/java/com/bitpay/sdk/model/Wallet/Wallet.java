package com.bitpay.sdk.model.Wallet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Wallet {
    private String _key;
    private String _displayName;
    private String _avatar;
    private Boolean _payPro;
    private ArrayList<Currencies> _currencies;
    private String _image;

    public Wallet() {
    }

    @JsonIgnore
    public String getKey() {
        return _key;
    }

    @JsonProperty("key")
    public void setKey(String key) {
        this._key = key;
    }

    @JsonIgnore
    public String getDisplayName() {
        return _displayName;
    }

    @JsonProperty("displayName")
    public void setDisplayName(String displayName) {
        this._displayName = displayName;
    }

    @JsonIgnore
    public String getAvatar() {
        return _avatar;
    }

    @JsonProperty("avatar")
    public void setAvatar(String avatar) {
        this._avatar = avatar;
    }

    @JsonIgnore
    public Boolean getPayPro() { return _payPro; }

    @JsonProperty("payPro")
    public void setPayPro(Boolean payPro) {
        this._payPro = payPro;
    }

    @JsonIgnore
    public ArrayList<Currencies> getCurrencies() { return _currencies; }

    @JsonProperty("currencies")
    public void setCurrencies(ArrayList<Currencies> currencies) {
        this._currencies = currencies;
    }
    
    @JsonIgnore
    public String getImage() {
        return _image;
    }

    @JsonProperty("image")
    public void setImage(String image) {
        this._image = image;
    }
}
