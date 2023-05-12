/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * The type Miner fees item.
 *
 * @see <a href="https://bitpay.readme.io/reference/ledgers">REST API Invoice</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MinerFeesItem {

    private BigDecimal satoshisPerByte;
    private BigDecimal totalFee;
    private double fiatAmount;

    /**
     * Instantiates a new Miner fees item.
     */
    public MinerFeesItem() {
    }

    /**
     * Gets satoshis per byte.
     *
     * @return the satoshis per byte
     */
    @JsonIgnore
    public BigDecimal getSatoshisPerByte() {
        return this.satoshisPerByte;
    }

    /**
     * Sets satoshis per byte.
     *
     * @param satoshisPerByte the satoshis per byte
     */
    @JsonProperty("satoshisPerByte")
    public void setSatoshisPerByte(BigDecimal satoshisPerByte) {
        this.satoshisPerByte = satoshisPerByte;
    }

    /**
     * Gets total fee.
     *
     * @return the total fee
     */
    @JsonIgnore
    public BigDecimal getTotalFee() {
        return this.totalFee;
    }

    /**
     * Sets total fee.
     *
     * @param totalFee the total fee
     */
    @JsonProperty("totalFee")
    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * Gets fiat amount.
     *
     * @return the fiat amount
     */
    @JsonIgnore
    public double getFiatAmount() {
        return this.fiatAmount;
    }

    /**
     * Sets fiat amount.
     *
     * @param fiatAmount the fiat amount
     */
    @JsonProperty("fiatAmount")
    public void setFiatAmount(double fiatAmount) {
        this.fiatAmount = fiatAmount;
    }
}
