/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Shopper.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-invoices-resource">REST API Invoices</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shopper {

    private String user;

    /**
     * Instantiates a new Shopper.
     */
    public Shopper() {
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    @JsonIgnore
    public String getName() {
        return this.user;
    }

    /**
     * Sets name.
     *
     * @param user the user
     */
    @JsonProperty("user")
    public void setName(String user) {
        this.user = user;
    }
}
