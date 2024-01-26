/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.wallet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Currencies.
 *
 * @see <a href="https://bitpay.readme.io/reference/wallets">Wallets</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currencies {
    protected String code;
    protected Boolean p2p;
    protected Boolean dappBrowser;
    protected Boolean payPro;
    protected CurrencyQr qr;
    protected String image;
    protected String withdrawalFee;
    protected Boolean walletConnect;

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
        return this.code;
    }

    /**
     * Sets identifying code for the currency.
     *
     * @param code the code
     */
    @JsonProperty("code")
    public void setCode(final String code) {
        this.code = code;
    }

    /**
     * Gets P2P - it indicates that this is a peer to peer (p2p) payment method (as opposed to payment protocol).
     *
     * @return the p2p
     */
    @JsonIgnore
    public Boolean getP2p() {
        return this.p2p;
    }

    /**
     * Sets P2P - it indicates that this is a peer to peer (p2p) payment method (as opposed to payment protocol).
     *
     * @param p2p the p2p
     */
    @JsonProperty("p2p")
    public void setP2p(final Boolean p2p) {
        this.p2p = p2p;
    }

    /**
     * Gets dapp browser - it indicates that this payment method operates via a browser plugin
     * interacting with the invoice.
     *
     * @return the dapp browser
     */
    @JsonIgnore
    public Boolean getDappBrowser() {
        return this.dappBrowser;
    }

    /**
     * Sets dapp browser - it indicates that this payment method operates via a browser plugin
     * interacting with the invoice.
     *
     * @param dappBrowser the dapp browser
     */
    @JsonProperty("dappBrowser")
    public void setDappBrowser(final Boolean dappBrowser) {
        this.dappBrowser = dappBrowser;
    }

    /**
     * Gets pay pro. Whether or not BitPay Payment Protocol is supported on this particular currency option.
     *
     * @return the pay pro
     */
    @JsonIgnore
    public Boolean getPayPro() {
        return this.payPro;
    }

    /**
     * Sets pay pro. Whether or not BitPay Payment Protocol is supported on this particular currency option.
     *
     * @param payPro the pay pro
     */
    @JsonProperty("payPro")
    public void setPayPro(final Boolean payPro) {
        this.payPro = payPro;
    }

    /**
     * Gets QR. Object containing QR code related information to show for this payment method.
     *
     * @return the qr
     */
    @JsonIgnore
    public CurrencyQr getQr() {
        return this.qr;
    }

    /**
     * Sets qr. Object containing QR code related information to show for this payment method.
     *
     * @param qr the QR
     */
    @JsonProperty("qr")
    public void setQr(final CurrencyQr qr) {
        this.qr = qr;
    }

    /**
     * Gets URL that displays wallet avatar image.
     *
     * @return the image
     */
    @JsonIgnore
    public String getImage() {
        return this.image;
    }

    /**
     * Sets URL that displays wallet avatar image.
     *
     * @param image the image
     */
    @JsonProperty("image")
    public void setImage(final String image) {
        this.image = image;
    }

    /**
     * Gets Custodial wallet withdrawal fee.
     *
     * @return string withdrawal fee
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getWithdrawalFee() {
        return this.withdrawalFee;
    }

    /**
     * Sets Custodial wallet withdrawal fee.
     *
     * @param withdrawalFee withdrawal fee
     */
    @JsonProperty("withdrawalFee")
    public void setWithdrawalFee(final String withdrawalFee) {
        this.withdrawalFee = withdrawalFee;
    }

    /**
     * Gets whether or not this wallet supports walletConnect.
     *
     * @return boolean wallet connect
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Boolean getWalletConnect() {
        return this.walletConnect;
    }

    /**
     * Sets whether or not this wallet supports walletConnect.
     *
     * @param walletConnect wallet connect
     */
    @JsonProperty("walletConnect")
    public void setWalletConnect(final Boolean walletConnect) {
        this.walletConnect = walletConnect;
    }
}
