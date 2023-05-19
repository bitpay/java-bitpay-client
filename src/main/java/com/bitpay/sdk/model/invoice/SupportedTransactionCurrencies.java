/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The currencies that may be used to pay this invoice.
 *
 * @see <a href="https://bitpay.readme.io/reference/invoices">REST API Invoices</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupportedTransactionCurrencies {

    private SupportedTransactionCurrency btc = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency bch = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency eth = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency usdc = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency gusd = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency pax = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency xrp = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency busd = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency doge = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency ltc = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency wbtc = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency dai = new SupportedTransactionCurrency();

    /**
     * Instantiates a new Supported transaction currencies.
     */
    public SupportedTransactionCurrencies() {
    }

    /**
     * Gets BTC.
     *
     * @return the BTC
     */
    @JsonIgnore
    public SupportedTransactionCurrency getBtc() {
        return this.btc;
    }

    /**
     * Sets BTC.
     *
     * @param btc the BTC
     */
    @JsonProperty("BTC")
    public void setBtc(final SupportedTransactionCurrency btc) {
        this.btc = btc;
    }

    /**
     * Gets BCH.
     *
     * @return the BCH
     */
    @JsonIgnore
    public SupportedTransactionCurrency getBch() {
        return this.bch;
    }

    /**
     * Sets BCH.
     *
     * @param bch the BCH
     */
    @JsonProperty("BCH")
    public void setBch(final SupportedTransactionCurrency bch) {
        this.bch = bch;
    }

    /**
     * Gets ETH.
     *
     * @return the ETH
     */
    @JsonIgnore
    public SupportedTransactionCurrency getEth() {
        return this.eth;
    }

    /**
     * Sets ETH.
     *
     * @param eth the ETH
     */
    @JsonProperty("ETH")
    public void setEth(final SupportedTransactionCurrency eth) {
        this.eth = eth;
    }

    /**
     * Gets USDC.
     *
     * @return the USDC
     */
    @JsonIgnore
    public SupportedTransactionCurrency getUsdc() {
        return this.usdc;
    }

    /**
     * Sets USDC.
     *
     * @param usdc the USDC
     */
    @JsonProperty("USDC")
    public void setUsdc(final SupportedTransactionCurrency usdc) {
        this.usdc = usdc;
    }

    /**
     * Gets GUSD.
     *
     * @return the GUSD
     */
    @JsonIgnore
    public SupportedTransactionCurrency getGusd() {
        return this.gusd;
    }

    /**
     * Sets GUSD.
     *
     * @param gusd the GUSD
     */
    @JsonProperty("GUSD")
    public void setGusd(final SupportedTransactionCurrency gusd) {
        this.gusd = gusd;
    }

    /**
     * Gets PAX.
     *
     * @return the pax
     */
    @JsonIgnore
    public SupportedTransactionCurrency getPax() {
        return this.pax;
    }

    /**
     * Sets pax.
     *
     * @param pax the PAX
     */
    @JsonProperty("PAX")
    public void setPax(final SupportedTransactionCurrency pax) {
        this.pax = pax;
    }

    /**
     * Gets XRP.
     *
     * @return the XRP
     */
    @JsonIgnore
    public SupportedTransactionCurrency getXrp() {
        return this.xrp;
    }

    /**
     * Sets XRP.
     *
     * @param xrp the XRP
     */
    @JsonProperty("XRP")
    public void setXrp(final SupportedTransactionCurrency xrp) {
        this.xrp = xrp;
    }

    /**
     * Gets BUSD.
     *
     * @return BUSD
     */
    @JsonIgnore
    public SupportedTransactionCurrency getBusd() {
        return this.busd;
    }

    /**
     * Sets BUSD.
     *
     * @param busd BUSD
     */
    @JsonProperty("BUSD")
    public void setBusd(final SupportedTransactionCurrency busd) {
        this.busd = busd;
    }

    /**
     * Gets DOGE.
     *
     * @return DOGE
     */
    @JsonIgnore
    public SupportedTransactionCurrency getDoge() {
        return this.doge;
    }

    /**
     * Sets DOGE.
     *
     * @param doge DOGE
     */
    @JsonProperty("DOGE")
    public void setDoge(final SupportedTransactionCurrency doge) {
        this.doge = doge;
    }

    /**
     * Gets LTC.
     *
     * @return LTC
     */
    @JsonIgnore
    public SupportedTransactionCurrency getLtc() {
        return this.ltc;
    }

    /**
     * Sets LTC.
     *
     * @param ltc LTC
     */
    @JsonProperty("LTC")
    public void setLtc(final SupportedTransactionCurrency ltc) {
        this.ltc = ltc;
    }

    /**
     * Gets DAI.
     *
     * @return DAI
     */
    @JsonIgnore
    public SupportedTransactionCurrency getDai() {
        return this.dai;
    }

    /**
     * Sets DAI.
     *
     * @param dai DAI
     */
    @JsonProperty("DAI")
    public void setDai(final SupportedTransactionCurrency dai) {
        this.dai = dai;
    }

    /**
     * Gets WBTC.
     *
     * @return WBTC
     */
    @JsonIgnore
    public SupportedTransactionCurrency getWbtc() {
        return this.wbtc;
    }

    /**
     * Sets WBTC.
     *
     * @param wbtc WBTC
     */
    @JsonProperty("WBTC")
    public void setWbtc(final SupportedTransactionCurrency wbtc) {
        this.wbtc = wbtc;
    }
}
