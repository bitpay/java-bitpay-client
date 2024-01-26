/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.wallet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Currency qr.
 *
 * @see <a href="https://bitpay.readme.io/reference/wallets">Wallets</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyQr {
    protected String type;
    protected Boolean collapsed;

    /**
     * Instantiates a new Currency qr.
     */
    public CurrencyQr() {
    }

    /**
     * Gets the type of QR code to use (ex. BIP21, ADDRESS, BIP72b, BIP681, BIP681b, etc).
     *
     * @return the type
     */
    @JsonIgnore
    public String getType() {
        return this.type;
    }

    /**
     * Sets the type of QR code to use (ex. BIP21, ADDRESS, BIP72b, BIP681, BIP681b, etc).
     *
     * @param type the type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets UI hint for BitPay invoice, generally not relevant to customer integrations.
     *
     * @return the collapsed
     */
    @JsonIgnore
    public Boolean getCollapsed() {
        return this.collapsed;
    }

    /**
     * Sets UI hint for BitPay invoice, generally not relevant to customer integrations.
     *
     * @param collapsed the collapsed
     */
    @JsonProperty("collapsed")
    public void setCollapsed(Boolean collapsed) {
        this.collapsed = collapsed;
    }
}
