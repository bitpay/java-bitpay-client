package com.bitpay.sdk.model.Wallet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Currencies {
    private String _code;
    private Boolean _p2p;
    private Boolean _dappBrowser;
    private Boolean _payPro;
    private CurrencyQr _qr;
    private String _image;

    public Currencies() {
    }

    @JsonIgnore
    public String getCode() {
        return _code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this._code = code;
    }

    @JsonIgnore
    public Boolean getP2p() {
        return _p2p;
    }

    @JsonProperty("p2p")
    public void setP2p(Boolean p2p) {
        this._p2p = p2p;
    }

    @JsonIgnore
    public Boolean getDappBrowser() {
        return _dappBrowser;
    }

    @JsonProperty("dappBrowser")
    public void setDappBrowser(Boolean dappBrowser) {
        this._dappBrowser = dappBrowser;
    }

    @JsonIgnore
    public Boolean getPayPro() { return _payPro; }

    @JsonProperty("payPro")
    public void setPayPro(Boolean payPro) {
        this._payPro = payPro;
    }

    @JsonIgnore
    public CurrencyQr getQr() { return _qr; }

    @JsonProperty("qr")
    public void setQr(CurrencyQr qr) {
        this._qr = qr;
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
