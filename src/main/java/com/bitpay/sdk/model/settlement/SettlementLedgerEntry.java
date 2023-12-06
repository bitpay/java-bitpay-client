/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.settlement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;

/**
 * The type Settlement ledger entry.
 *
 * @see <a href="https://bitpay.readme.io/reference/settlements">Settlements</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SettlementLedgerEntry {

    private Integer code;
    private String invoiceId;
    private Float amount;
    private ZonedDateTime timestamp;
    private String description;
    private String reference;
    private InvoiceData invoiceData;

    /**
     * Instantiates a new Settlement ledger entry.
     */
    public SettlementLedgerEntry() {
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    @JsonIgnore
    public Integer getCode() {
        return this.code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    @JsonProperty("code")
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * Gets invoice id.
     *
     * @return the invoice id
     */
    @JsonIgnore
    public String getInvoiceId() {
        return this.invoiceId;
    }

    /**
     * Sets invoice id.
     *
     * @param invoiceId the invoice id
     */
    @JsonProperty("invoiceId")
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    @JsonIgnore
    public Float getAmount() {
        return this.amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    @JsonProperty("amount")
    public void setAmount(Float amount) {
        this.amount = amount;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    @JsonIgnore
    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    @JsonProperty("timestamp")
    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    @JsonIgnore
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets reference.
     *
     * @return the reference
     */
    @JsonIgnore
    public String getReference() {
        return this.reference;
    }

    /**
     * Sets reference.
     *
     * @param reference the reference
     */
    @JsonProperty("reference")
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Gets invoice data.
     *
     * @return the invoice data
     */
    @JsonIgnore
    public InvoiceData getInvoiceData() {
        return this.invoiceData;
    }

    /**
     * Sets invoice data.
     *
     * @param invoiceData the invoice data
     */
    @JsonProperty("invoiceData")
    public void setInvoiceData(InvoiceData invoiceData) {
        this.invoiceData = invoiceData;
    }

}
