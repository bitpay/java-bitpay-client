package com.bitpay.sdk.model.Ledger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LedgerEntry {

    private String _type;
    private String _amount;
    private String _code;
    private String _description;
    private String _timestamp;
    private String _txType;
    private String _scale;
    private String _invoiceId;
    private Buyer _buyer;
    private Double _invoiceAmount;
    private String _invoiceCurrency;
    private String _transactionCurrency;
    private String _id;

    public LedgerEntry() {
    }

    @JsonIgnore
    public String getType() {
        return _type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this._type = type;
    }

    @JsonIgnore
    public String getAmount() {
        return _amount;
    }

    @JsonProperty("amount")
    public void setAmount(String amount) {
        this._amount = amount;
    }

    @JsonIgnore
    public String getCode() {
        return _code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this._code = code;
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
    public String getTimestamp() {
        return _timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this._timestamp = timestamp;
    }

    @JsonIgnore
    public String getTxType() {
        return _txType;
    }

    @JsonProperty("txType")
    public void setTxType(String txType) {
        this._txType = txType;
    }

    @JsonIgnore
    public String getScale() {
        return _scale;
    }

    @JsonProperty("scale")
    public void setScale(String scale) {
        this._scale = scale;
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
    public Buyer getBuyer() {
        return _buyer;
    }

    @JsonProperty("buyerFields")
    public void setBuyer(Buyer buyer) {
        this._buyer = buyer;
    }

    @JsonIgnore
    public Double getInvoiceAmount() {
        return _invoiceAmount;
    }

    @JsonProperty("invoiceAmount")
    public void setInvoiceAmount(Double invoiceAmount) {
        this._invoiceAmount = invoiceAmount;
    }

    @JsonIgnore
    public String getInvoiceCurrency() {
        return _invoiceCurrency;
    }

    @JsonProperty("invoiceCurrency")
    public void setInvoiceCurrency(String invoiceCurrency) {
        this._invoiceCurrency = invoiceCurrency;
    }

    @JsonIgnore
    public String getTransactionCurrency() {
        return _transactionCurrency;
    }

    @JsonProperty("transactionCurrency")
    public void setTransactionCurrency(String transactionCurrency) {
        this._transactionCurrency = transactionCurrency;
    }

    @JsonIgnore
    public String getId() {
        return _id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this._id = id;
    }
}
