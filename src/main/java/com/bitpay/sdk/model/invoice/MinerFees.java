/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The total amount of fees that the purchaser will pay to cover BitPay's UTXO sweep cost for an invoice.
 * The key is the currency and the value is an object containing the satoshis per byte,
 * the total fee, and the fiat amount. This is referenced as "Network Cost" on an invoice,
 * see this support article for more information.
 *
 * @see <a href="https://bitpay.readme.io/reference/invoices">REST API Invoices</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MinerFees {

    private MinerFeesItem btc = new MinerFeesItem();
    private MinerFeesItem bch = new MinerFeesItem();
    private MinerFeesItem eth = new MinerFeesItem();
    private MinerFeesItem usdc = new MinerFeesItem();
    private MinerFeesItem gusd = new MinerFeesItem();
    private MinerFeesItem pax = new MinerFeesItem();
    private MinerFeesItem busd = new MinerFeesItem();
    private MinerFeesItem xrp = new MinerFeesItem();
    private MinerFeesItem doge = new MinerFeesItem();
    private MinerFeesItem ltc = new MinerFeesItem();
    private MinerFeesItem dai = new MinerFeesItem();
    private MinerFeesItem wbtc = new MinerFeesItem();
    private MinerFeesItem matic = new MinerFeesItem();
    private MinerFeesItem usdcM = new MinerFeesItem();

    /**
     * Instantiates a new Miner fees.
     */
    public MinerFees() {
    }

    /**
     * Gets BTC.
     *
     * @return the BTC
     */
    @JsonIgnore
    public MinerFeesItem getBtc() {
        return this.btc;
    }

    /**
     * Sets BTC.
     *
     * @param btc the BTC
     */
    @JsonProperty("BTC")
    public void setBtc(MinerFeesItem btc) {
        this.btc = btc;
    }

    /**
     * Gets BCH.
     *
     * @return the BCH
     */
    @JsonIgnore
    public MinerFeesItem getBch() {
        return this.bch;
    }

    /**
     * Sets BCH.
     *
     * @param bch the BCH
     */
    @JsonProperty("BCH")
    public void setBch(MinerFeesItem bch) {
        this.bch = bch;
    }

    /**
     * Gets ETH.
     *
     * @return the ETH
     */
    @JsonIgnore
    public MinerFeesItem getEth() {
        return this.eth;
    }

    /**
     * Sets ETH.
     *
     * @param eth the eth
     */
    @JsonProperty("ETH")
    public void setEth(MinerFeesItem eth) {
        this.eth = eth;
    }

    /**
     * Gets USDC.
     *
     * @return the USDC
     */
    @JsonIgnore
    public MinerFeesItem getUsdc() {
        return this.usdc;
    }

    /**
     * Sets USDC.
     *
     * @param usdc the USDC
     */
    @JsonProperty("USDC")
    public void setUsdc(MinerFeesItem usdc) {
        this.usdc = usdc;
    }

    /**
     * Gets GUSD.
     *
     * @return the GUSD
     */
    @JsonIgnore
    public MinerFeesItem getGusd() {
        return this.gusd;
    }

    /**
     * Sets GUSD.
     *
     * @param gusd the GUSD
     */
    @JsonProperty("GUSD")
    public void setGusd(MinerFeesItem gusd) {
        this.gusd = gusd;
    }

    /**
     * Gets PAX.
     *
     * @return the PAX
     */
    @JsonIgnore
    public MinerFeesItem getPax() {
        return this.pax;
    }

    /**
     * Sets PAX.
     *
     * @param pax the PAX
     */
    @JsonProperty("PAX")
    public void setPax(MinerFeesItem pax) {
        this.pax = pax;
    }

    /**
     * Gets XRP.
     *
     * @return the XRP
     */
    @JsonIgnore
    public MinerFeesItem getXrp() {
        return this.xrp;
    }

    /**
     * Sets XRP.
     *
     * @param xrp the XRP
     */
    @JsonProperty("XRP")
    public void setXrp(MinerFeesItem xrp) {
        this.xrp = xrp;
    }

    /**
     * Gets BUSD.
     *
     * @return BUSD
     */
    @JsonIgnore
    public MinerFeesItem getBusd() {
        return this.busd;
    }

    /**
     * Sets BUSD.
     *
     * @param busd BUSD
     */
    @JsonProperty("BUSD")
    public void setBusd(MinerFeesItem busd) {
        this.busd = busd;
    }

    /**
     * Gets DOGE.
     *
     * @return DOGE
     */
    @JsonIgnore
    public MinerFeesItem getDoge() {
        return this.doge;
    }

    /**
     * Sets DOGE.
     *
     * @param doge DOGE
     */
    @JsonProperty("DOGE")
    public void setDoge(MinerFeesItem doge) {
        this.doge = doge;
    }

    /**
     * Gets LTC.
     *
     * @return LTC
     */
    @JsonIgnore
    public MinerFeesItem getLtc() {
        return this.ltc;
    }

    /**
     * Sets LTC.
     *
     * @param ltc LTC
     */
    @JsonProperty("LTC")
    public void setLtc(MinerFeesItem ltc) {
        this.ltc = ltc;
    }

    /**
     * Gets DAI.
     *
     * @return DAI
     */
    @JsonIgnore
    public MinerFeesItem getDai() {
        return this.dai;
    }

    /**
     * Sets DAI.
     *
     * @param dai DAI
     */
    @JsonProperty("DAI")
    public void setDai(MinerFeesItem dai) {
        this.dai = dai;
    }

    /**
     * Gets WBTC.
     *
     * @return WBTC
     */
    @JsonIgnore
    public MinerFeesItem getWbtc() {
        return this.wbtc;
    }

    /**
     * Sets WBTC.
     *
     * @param wbtc WBTC
     */
    @JsonProperty("WBTC")
    public void setWbtc(MinerFeesItem wbtc) {
        this.wbtc = wbtc;
    }

    /**
     * Gets MATIC.
     *
     * @return MATIC
     */
    @JsonIgnore
    public MinerFeesItem getMatic() {
        return matic;
    }

    /**
     * Sets MATIC.
     *
     * @param matic MATIC
     */
    @JsonProperty("MATIC")
    public void setMatic(MinerFeesItem matic) {
        this.matic = matic;
    }

    /**
     * Gets USDC_m.
     *
     * @return USDC_m
     */
    @JsonIgnore
    public MinerFeesItem getUsdcM() {
        return usdcM;
    }

    /**
     * Sets USDC_m.
     *
     * @param usdcM USDC_m
     */
    @JsonProperty("USDC_m")
    public void setUsdcM(MinerFeesItem usdcM) {
        this.usdcM = usdcM;
    }
}
