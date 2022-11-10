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

    private SupportedTransactionCurrency btc = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency bch = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency eth = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency usdc = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency gusd = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency pax = new SupportedTransactionCurrency();
    private SupportedTransactionCurrency xrp = new SupportedTransactionCurrency();

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
    public void setBtc(SupportedTransactionCurrency btc) {
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
    public void setBch(SupportedTransactionCurrency bch) {
        this.bch = bch;
    }

    /**
     * Gets ETH.
     *
     * @return the ETH
     */
    @JsonIgnore
    public SupportedTransactionCurrency getEth() { return this.eth; }

    /**
     * Sets ETH.
     *
     * @param eth the ETH
     */
    @JsonProperty("ETH")
    public void setEth(SupportedTransactionCurrency eth) {
        this.eth = eth;
    }

    /**
     * Gets USDC.
     *
     * @return the USDC
     */
    @JsonIgnore
    public SupportedTransactionCurrency getUsdc() { return this.usdc; }

    /**
     * Sets USDC.
     *
     * @param usdc the USDC
     */
    @JsonProperty("USDC")
    public void setUsdc(SupportedTransactionCurrency usdc) { this.usdc = usdc; }

    /**
     * Gets GUSD.
     *
     * @return the GUSD
     */
    @JsonIgnore
    public SupportedTransactionCurrency getGusd() { return this.gusd; }

    /**
     * Sets GUSD.
     *
     * @param gusd the GUSD
     */
    @JsonProperty("GUSD")
    public void setGusd(SupportedTransactionCurrency gusd) { this.gusd = gusd; }

    /**
     * Gets PAX.
     *
     * @return the pax
     */
    @JsonIgnore
    public SupportedTransactionCurrency getPax() { return this.pax; }

    /**
     * Sets pax.
     *
     * @param pax the PAX
     */
    @JsonProperty("PAX")
    public void setPax(SupportedTransactionCurrency pax) { this.pax = pax; }

    /**
     * Gets XRP.
     *
     * @return the XRP
     */
    @JsonIgnore
    public SupportedTransactionCurrency getXrp() { return this.xrp; }

    /**
     * Sets XRP.
     *
     * @param xrp the XRP
     */
    @JsonProperty("XRP")
    public void setXrp(SupportedTransactionCurrency xrp) { this.xrp = xrp; }
}
