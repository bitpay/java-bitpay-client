/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk.model.Settlement;

import com.bitpay.sdk.util.DateDeserializer;
import com.bitpay.sdk.util.DateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * The type Settlement ledger entry.
 * @see <a href="https://bitpay.com/api/#rest-api-resources-settlements">Settlements</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SettlementLedgerEntry {

    private Integer _code;
    private String _invoiceId;
    private Float _amount;
    private Long _timestamp;
    private String _description;
    private String _reference;
    private InvoiceData _invoiceData;

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
        return _code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    @JsonProperty("code")
    public void setCode(Integer code) {
        this._code = code;
    }

    /**
     * Gets invoice id.
     *
     * @return the invoice id
     */
    @JsonIgnore
    public String getInvoiceId() {
        return _invoiceId;
    }

    /**
     * Sets invoice id.
     *
     * @param invoiceId the invoice id
     */
    @JsonProperty("invoiceId")
    public void setInvoiceId(String invoiceId) {
        this._invoiceId = invoiceId;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    @JsonIgnore
    public Float getAmount() {
        return _amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    @JsonProperty("amount")
    public void setAmount(Float amount) {
        this._amount = amount;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public Long getTimestamp() {
        return _timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    @JsonProperty("timestamp")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setTimestamp(Long timestamp) {
        this._timestamp = timestamp;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    @JsonIgnore
    public String getDescription() {
        return _description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this._description = description;
    }

    /**
     * Gets reference.
     *
     * @return the reference
     */
    @JsonIgnore
    public String getReference() {
        return _reference;
    }

    /**
     * Sets reference.
     *
     * @param reference the reference
     */
    @JsonProperty("reference")
    public void setReference(String reference) {
        this._reference = reference;
    }

    /**
     * Gets invoice data.
     *
     * @return the invoice data
     */
    @JsonIgnore
    public InvoiceData getInvoiceData() {
        return _invoiceData;
    }

    /**
     * Sets invoice data.
     *
     * @param invoiceData the invoice data
     */
    @JsonProperty("invoiceData")
    public void setInvoiceData(InvoiceData invoiceData) {
        this._invoiceData = invoiceData;
    }

}
