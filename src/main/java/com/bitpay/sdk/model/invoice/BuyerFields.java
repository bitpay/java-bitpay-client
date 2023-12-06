/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @see <a href="https://developer.bitpay.com/reference/notifications-invoices">Invoice Webhook</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class BuyerFields {

    protected String buyerName;
    protected String buyerAddress1;
    protected String buyerAddress2;
    protected String buyerCity;
    protected String buyerState;
    protected String buyerZip;
    protected String buyerCountry;
    protected String buyerPhone;
    protected Boolean buyerNotify;
    protected String buyerEmail;

    public String getBuyerName() {
        return this.buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerAddress1() {
        return this.buyerAddress1;
    }

    public void setBuyerAddress1(String buyerAddress1) {
        this.buyerAddress1 = buyerAddress1;
    }

    public String getBuyerAddress2() {
        return this.buyerAddress2;
    }

    public void setBuyerAddress2(String buyerAddress2) {
        this.buyerAddress2 = buyerAddress2;
    }

    public String getBuyerCity() {
        return this.buyerCity;
    }

    public void setBuyerCity(String buyerCity) {
        this.buyerCity = buyerCity;
    }

    public String getBuyerState() {
        return this.buyerState;
    }

    public void setBuyerState(String buyerState) {
        this.buyerState = buyerState;
    }

    public String getBuyerZip() {
        return this.buyerZip;
    }

    public void setBuyerZip(String buyerZip) {
        this.buyerZip = buyerZip;
    }

    public String getBuyerCountry() {
        return this.buyerCountry;
    }

    public void setBuyerCountry(String buyerCountry) {
        this.buyerCountry = buyerCountry;
    }

    public String getBuyerPhone() {
        return this.buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public Boolean getBuyerNotify() {
        return this.buyerNotify;
    }

    public void setBuyerNotify(Boolean buyerNotify) {
        this.buyerNotify = buyerNotify;
    }

    public String getBuyerEmail() {
        return this.buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }
}
