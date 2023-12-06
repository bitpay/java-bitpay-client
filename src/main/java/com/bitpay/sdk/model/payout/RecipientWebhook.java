/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.payout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Recipient webhook.
 *
 * @see <a href="https://developer.bitpay.com/reference/recipients-1">Recipient Webhook</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class RecipientWebhook {

    protected String email;
    protected String label;
    protected String status;
    protected String id;
    protected String shopperId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopperId() {
        return shopperId;
    }

    public void setShopperId(String shopperId) {
        this.shopperId = shopperId;
    }
}
