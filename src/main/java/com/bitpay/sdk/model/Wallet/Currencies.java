/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk.model.Wallet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Currencies.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-wallets">Wallets</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currencies {
    private String _code;
    private Boolean _p2p;
    private Boolean _dappBrowser;
    private Boolean _payPro;
    private CurrencyQr _qr;
    private String _image;

    /**
     * Instantiates a new Currencies.
     */
    public Currencies() {
    }

    /**
     * Gets identifying code for the currency.
     *
     * @return the code
     */
    @JsonIgnore
    public String getCode() {
        return _code;
    }

    /**
     * Sets identifying code for the currency.
     *
     * @param code the code
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this._code = code;
    }

    /**
     * Gets P2P - it indicates that this is a peer to peer (p2p) payment method (as opposed to payment protocol).
     *
     * @return the p2p
     */
    @JsonIgnore
    public Boolean getP2p() {
        return _p2p;
    }

    /**
     * Sets P2P - it indicates that this is a peer to peer (p2p) payment method (as opposed to payment protocol).
     *
     * @param p2p the p2p
     */
    @JsonProperty("p2p")
    public void setP2p(Boolean p2p) {
        this._p2p = p2p;
    }

    /**
     * Gets dapp browser - it indicates that this payment method operates via a browser plugin
     * interacting with the invoice.
     *
     * @return the dapp browser
     */
    @JsonIgnore
    public Boolean getDappBrowser() {
        return _dappBrowser;
    }

    /**
     * Sets dapp browser - it indicates that this payment method operates via a browser plugin
     * interacting with the invoice.
     *
     * @param dappBrowser the dapp browser
     */
    @JsonProperty("dappBrowser")
    public void setDappBrowser(Boolean dappBrowser) {
        this._dappBrowser = dappBrowser;
    }

    /**
     * Gets pay pro. Whether or not BitPay Payment Protocol is supported on this particular currency option.
     *
     * @return the pay pro
     */
    @JsonIgnore
    public Boolean getPayPro() { return _payPro; }

    /**
     * Sets pay pro. Whether or not BitPay Payment Protocol is supported on this particular currency option.
     *
     * @param payPro the pay pro
     */
    @JsonProperty("payPro")
    public void setPayPro(Boolean payPro) {
        this._payPro = payPro;
    }

    /**
     * Gets QR. Object containing QR code related information to show for this payment method.
     *
     * @return the qr
     */
    @JsonIgnore
    public CurrencyQr getQr() { return _qr; }

    /**
     * Sets qr. Object containing QR code related information to show for this payment method.
     *
     * @param qr the QR
     */
    @JsonProperty("qr")
    public void setQr(CurrencyQr qr) {
        this._qr = qr;
    }

    /**
     * Gets URL that displays wallet avatar image.
     *
     * @return the image
     */
    @JsonIgnore
    public String getImage() {
        return _image;
    }

    /**
     * Sets URL that displays wallet avatar image.
     *
     * @param image the image
     */
    @JsonProperty("image")
    public void setImage(String image) {
        this._image = image;
    }
}
