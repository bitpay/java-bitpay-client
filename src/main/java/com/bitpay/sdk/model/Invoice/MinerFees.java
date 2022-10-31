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
 * @see <a href="https://bitpay.com/api/#rest-api-resources-invoices-resource">REST API Invoices</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MinerFees {

    private MinerFeesItem _btc = new MinerFeesItem();
    private MinerFeesItem _bch = new MinerFeesItem();
    private MinerFeesItem _eth = new MinerFeesItem();
    private MinerFeesItem _usdc = new MinerFeesItem();
    private MinerFeesItem _gusd = new MinerFeesItem();
    private MinerFeesItem _pax = new MinerFeesItem();
    private MinerFeesItem _xrp = new MinerFeesItem();

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
        return _btc;
    }

    /**
     * Sets BTC.
     *
     * @param btc the BTC
     */
    @JsonProperty("BTC")
    public void setBtc(MinerFeesItem btc) {
        this._btc = btc;
    }

    /**
     * Gets BCH.
     *
     * @return the BCH
     */
    @JsonIgnore
    public MinerFeesItem getBch() {
        return _bch;
    }

    /**
     * Sets BCH.
     *
     * @param bch the BCH
     */
    @JsonProperty("BCH")
    public void setBch(MinerFeesItem bch) {
        this._bch = bch;
    }

    /**
     * Gets ETH.
     *
     * @return the ETH
     */
    @JsonIgnore
    public MinerFeesItem getEth() {
        return _eth;
    }

    /**
     * Sets ETH.
     *
     * @param eth the eth
     */
    @JsonProperty("ETH")
    public void setEth(MinerFeesItem eth) {
        this._eth = eth;
    }

    /**
     * Gets USDC.
     *
     * @return the USDC
     */
    @JsonIgnore
    public MinerFeesItem getUsdc() { return _usdc; }

    /**
     * Sets USDC.
     *
     * @param usdc the USDC
     */
    @JsonProperty("USDC")
    public void setUsdc(MinerFeesItem usdc) { this._usdc = usdc; }

    /**
     * Gets GUSD.
     *
     * @return the GUSD
     */
    @JsonIgnore
    public MinerFeesItem getGusd() { return _gusd; }

    /**
     * Sets GUSD.
     *
     * @param gusd the GUSD
     */
    @JsonProperty("GUSD")
    public void setGusd(MinerFeesItem gusd) { this._gusd = gusd; }

    /**
     * Gets PAX.
     *
     * @return the PAX
     */
    @JsonIgnore
    public MinerFeesItem getPax() { return _pax; }

    /**
     * Sets PAX.
     *
     * @param pax the PAX
     */
    @JsonProperty("PAX")
    public void setPax(MinerFeesItem pax) { this._pax = pax; }

    /**
     * Gets XRP.
     *
     * @return the XRP
     */
    @JsonIgnore
    public MinerFeesItem getXrp() { return _xrp; }

    /**
     * Sets xrp.
     *
     * @param xrp the XRP
     */
    @JsonProperty("XRP")
    public void setXrp(MinerFeesItem xrp) { this._xrp = xrp; }
}
