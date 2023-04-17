/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

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
    private MinerFeesItem xrp = new MinerFeesItem();

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
    public MinerFeesItem getUsdc() { return this.usdc; }

    /**
     * Sets USDC.
     *
     * @param usdc the USDC
     */
    @JsonProperty("USDC")
    public void setUsdc(MinerFeesItem usdc) { this.usdc = usdc; }

    /**
     * Gets GUSD.
     *
     * @return the GUSD
     */
    @JsonIgnore
    public MinerFeesItem getGusd() { return this.gusd; }

    /**
     * Sets GUSD.
     *
     * @param gusd the GUSD
     */
    @JsonProperty("GUSD")
    public void setGusd(MinerFeesItem gusd) { this.gusd = gusd; }

    /**
     * Gets PAX.
     *
     * @return the PAX
     */
    @JsonIgnore
    public MinerFeesItem getPax() { return this.pax; }

    /**
     * Sets PAX.
     *
     * @param pax the PAX
     */
    @JsonProperty("PAX")
    public void setPax(MinerFeesItem pax) { this.pax = pax; }

    /**
     * Gets XRP.
     *
     * @return the XRP
     */
    @JsonIgnore
    public MinerFeesItem getXrp() { return this.xrp; }

    /**
     * Sets xrp.
     *
     * @param xrp the XRP
     */
    @JsonProperty("XRP")
    public void setXrp(MinerFeesItem xrp) { this.xrp = xrp; }
}
