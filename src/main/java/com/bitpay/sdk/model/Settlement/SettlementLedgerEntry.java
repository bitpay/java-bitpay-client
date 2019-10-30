package com.bitpay.sdk.model.Settlement;

import com.bitpay.sdk.util.DateDeserializer;
import com.bitpay.sdk.util.DateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SettlementLedgerEntry {

    private Integer _code;
    private String _invoiceId;
    private Float _amount;
    private Long _timestamp;
    private String _description;
    private String _reference;
    private InvoiceData _invoiceData;

    public SettlementLedgerEntry() {
    }

    @JsonIgnore
    public Integer getCode() {
        return _code;
    }

    @JsonProperty("code")
    public void setCode(Integer code) {
        this._code = code;
    }

    @JsonIgnore
    public String getInvoiceId() {
        return _invoiceId;
    }

    @JsonProperty("invoiceId")
    public void setInvoiceId(String invoiceId) {
        this._invoiceId = invoiceId;
    }

    @JsonIgnore
    public Float getAmount() {
        return _amount;
    }

    @JsonProperty("amount")
    public void setAmount(Float amount) {
        this._amount = amount;
    }

    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public Long getTimestamp() {
        return _timestamp;
    }

    @JsonProperty("timestamp")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setTimestamp(Long timestamp) {
        this._timestamp = timestamp;
    }

    @JsonIgnore
    public String getDescription() {
        return _description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this._description = description;
    }

    @JsonIgnore
    public String getReference() {
        return _reference;
    }

    @JsonProperty("reference")
    public void setReference(String reference) {
        this._reference = reference;
    }

    @JsonIgnore
    public InvoiceData getInvoiceData() {
        return _invoiceData;
    }

    @JsonProperty("invoiceData")
    public void setInvoiceData(InvoiceData invoiceData) {
        this._invoiceData = invoiceData;
    }

}
