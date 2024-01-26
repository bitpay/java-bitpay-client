/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.settlement;

import com.bitpay.sdk.util.serializer.Iso8601ToZonedDateTimeDeserializer;
import com.bitpay.sdk.util.serializer.ZonedDateTimeToIso8601Serializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.ZonedDateTime;

/**
 * The type Settlement ledger entry.
 *
 * @see <a href="https://bitpay.readme.io/reference/settlements">Settlements</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SettlementLedgerEntry {
    protected Integer code;
    protected String invoiceId;
    protected Float amount;
    protected ZonedDateTime timestamp;
    protected String description;
    protected InvoiceData invoiceData;

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
    @JsonSerialize(using = ZonedDateTimeToIso8601Serializer.class)
    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    @JsonProperty("timestamp")
    @JsonDeserialize(using = Iso8601ToZonedDateTimeDeserializer.class)
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
