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

    protected SupportedTransactionCurrency btc = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency bch = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency eth = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency usdc = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency gusd = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency pax = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency xrp = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency busd = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency doge = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency ltc = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency wbtc = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency dai = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency euroc = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency matic = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency maticE = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency ethM = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency usdcM = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency busdM = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency daiM = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency wbtcM = new SupportedTransactionCurrency();
    protected SupportedTransactionCurrency shibM = new SupportedTransactionCurrency();

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

    /**
     * Gets EUROC.
     *
     * @return EUROC
     */
    @JsonIgnore
    public SupportedTransactionCurrency getEuroc() {
        return this.euroc;
    }

    /**
     * Sets EUROC.
     *
     * @param euroc EUROC
     */
    @JsonProperty("EUROC")
    public void setEuroc(SupportedTransactionCurrency euroc) {
        this.euroc = euroc;
    }

    /**
     * Gets MATIC.
     *
     * @return MATIC
     */
    public SupportedTransactionCurrency getMatic() {
        return this.matic;
    }

    /**
     * Sets MATIC.
     *
     * @param matic MATIC
     */
    @JsonProperty("MATIC")
    public void setMatic(SupportedTransactionCurrency matic) {
        this.matic = matic;
    }

    /**
     * Gets MATIC_e.
     *
     * @return MATIC_e
     */
    @JsonIgnore
    public SupportedTransactionCurrency getMaticE() {
        return this.maticE;
    }

    /**
     * Sets MATIC_e.
     *
     * @param maticE MATIC_e
     */
    @JsonProperty("MATIC_e")
    public void setMaticE(SupportedTransactionCurrency maticE) {
        this.maticE = maticE;
    }

    /**
     * Gets ETH_m.
     *
     * @return Gets ETH_m.
     */
    public SupportedTransactionCurrency getEthM() {
        return this.ethM;
    }

    /**
     * Sets Gets ETH_m.
     *
     * @param ethM Gets ETH_m
     */
    @JsonProperty("ETH_m")
    public void setEthM(SupportedTransactionCurrency ethM) {
        this.ethM = ethM;
    }

    /**
     * Gets USDC_m.
     *
     * @return USDC_m
     */
    @JsonIgnore
    public SupportedTransactionCurrency getUsdcM() {
        return this.usdcM;
    }

    /**
     * Sets USDC_m.
     *
     * @param usdcM USDC_m
     */
    @JsonProperty("USDC_m")
    public void setUsdcM(SupportedTransactionCurrency usdcM) {
        this.usdcM = usdcM;
    }

    /**
     * Gets BUSD_m.
     *
     * @return BUSD_m
     */
    @JsonIgnore
    public SupportedTransactionCurrency getBusdM() {
        return this.busdM;
    }

    /**
     * Sets BUSD_m.
     *
     * @param busdM BUSD_m
     */
    @JsonProperty("BUSD_m")
    public void setBusdM(SupportedTransactionCurrency busdM) {
        this.busdM = busdM;
    }

    /**
     * Gets DAI_m.
     *
     * @return DAI_m
     */
    @JsonIgnore
    public SupportedTransactionCurrency getDaiM() {
        return this.daiM;
    }

    /**
     * Sets DAI_m.
     *
     * @param daiM DAI_m
     */
    @JsonProperty("DAI_m")
    public void setDaiM(SupportedTransactionCurrency daiM) {
        this.daiM = daiM;
    }

    /**
     * Gets WBTC_m.
     *
     * @return WBTC_m
     */
    @JsonIgnore
    public SupportedTransactionCurrency getWbtcM() {
        return this.wbtcM;
    }

    /**
     * Sets WBTC_m.
     *
     * @param wbtcM WBTC_m
     */
    @JsonProperty("WBTC_m")
    public void setWbtcM(SupportedTransactionCurrency wbtcM) {
        this.wbtcM = wbtcM;
    }

    /**
     * Gets SHIB_m.
     *
     * @return SHIB_m
     */
    @JsonIgnore
    public SupportedTransactionCurrency getShibM() {
        return this.shibM;
    }

    /**
     * Sets SHIB_m.
     *
     * @param shibM SHIB_m
     */
    @JsonProperty("SHIB_m")
    public void setShibM(SupportedTransactionCurrency shibM) {
        this.shibM = shibM;
    }
}
