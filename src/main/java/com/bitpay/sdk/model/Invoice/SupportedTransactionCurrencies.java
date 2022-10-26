/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The currencies that may be used to pay this invoice.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-invoices-resource">REST API Invoices</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupportedTransactionCurrencies {

    private SupportedTransactionCurrency _btc = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency _bch = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency _eth = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency _usdc = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency _gusd = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency _pax = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency _xrp = new SupportedTransactionCurrency();

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
        return _btc;
    }

    /**
     * Sets BTC.
     *
     * @param btc the BTC
     */
    @JsonProperty("BTC")
    public void setBtc(SupportedTransactionCurrency btc) {
        this._btc = btc;
    }

    /**
     * Gets BCH.
     *
     * @return the BCH
     */
    @JsonIgnore
    public SupportedTransactionCurrency getBch() {
        return _bch;
    }

    /**
     * Sets BCH.
     *
     * @param bch the BCH
     */
    @JsonProperty("BCH")
    public void setBch(SupportedTransactionCurrency bch) {
        this._bch = bch;
    }

    /**
     * Gets ETH.
     *
     * @return the ETH
     */
    @JsonIgnore
    public SupportedTransactionCurrency getEth() { return _eth; }

    /**
     * Sets ETH.
     *
     * @param eth the ETH
     */
    @JsonProperty("ETH")
    public void setEth(SupportedTransactionCurrency eth) {
        this._eth = eth;
    }

    /**
     * Gets USDC.
     *
     * @return the USDC
     */
    @JsonIgnore
    public SupportedTransactionCurrency getUsdc() { return _usdc; }

    /**
     * Sets USDC.
     *
     * @param usdc the USDC
     */
    @JsonProperty("USDC")
    public void setUsdc(SupportedTransactionCurrency usdc) { this._usdc = usdc; }

    /**
     * Gets GUSD.
     *
     * @return the GUSD
     */
    @JsonIgnore
    public SupportedTransactionCurrency getGusd() { return _gusd; }

    /**
     * Sets GUSD.
     *
     * @param gusd the GUSD
     */
    @JsonProperty("GUSD")
    public void setGusd(SupportedTransactionCurrency gusd) { this._gusd = gusd; }

    /**
     * Gets PAX.
     *
     * @return the pax
     */
    @JsonIgnore
    public SupportedTransactionCurrency getPax() { return _pax; }

    /**
     * Sets pax.
     *
     * @param pax the PAX
     */
    @JsonProperty("PAX")
    public void setPax(SupportedTransactionCurrency pax) { this._pax = pax; }

    /**
     * Gets XRP.
     *
     * @return the XRP
     */
    @JsonIgnore
    public SupportedTransactionCurrency getXrp() { return _xrp; }

    /**
     * Sets XRP.
     *
     * @param xrp the XRP
     */
    @JsonProperty("XRP")
    public void setXrp(SupportedTransactionCurrency xrp) { this._xrp = xrp; }
}
