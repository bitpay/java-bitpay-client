package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Refund {

    private String _guid;
    private Double _amount;
    private String _currency;
    private String _invoice;
    private Boolean _preview;
    private Boolean _immediate;
    private Boolean _buyerPaysRefundFee;
    private String _reference;
    private Double _refundFee;
    private Date _lastRefundNotification;

    /**
     * Amount to be refunded in terms of the transaction currency.
     */
    private BigDecimal _transactionAmount;

    /**
     * The refund fee expressed in terms of transaction currency.
     */
    private BigDecimal _transactionRefundFee;

    /**
     * The currency used for the invoice transaction.
     */
    private String _transactionCurrency;


    private String _id;
    private Date _requestDate;
    private String _status;

    public Refund() {
    }

    // Request fields
    //

    @JsonProperty("guid")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getGuid() {
        return _guid;
    }

    @JsonProperty("guid")
    public void setGuid(String guid) { this._guid = guid; }

    @JsonProperty("amount")
    public Double getAmount() {
        return _amount;
    }

    @JsonProperty("amount")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setAmount(Double amount) {
        this._amount = amount;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return _currency;
    }

    @JsonProperty("currency")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setCurrency(String currency) {
        this._currency = currency;
    }

    @JsonProperty("invoice")
    public String getInvoice() {
        return _invoice;
    }

    @JsonProperty("invoice")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setInvoice(String invoice) {
        this._invoice = invoice;
    }

    @JsonProperty("preview")
    public Boolean getPreview() {
        return _preview;
    }

    @JsonProperty("preview")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setPreview(Boolean preview) {
        this._preview = preview;
    }

    @JsonProperty("immediate")
    public Boolean getImmediate() {
        return _immediate;
    }

    @JsonProperty("immediate")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setImmediate(Boolean immediate) {
        this._immediate = immediate;
    }

    @JsonProperty("buyerPaysRefundFee")
    public Boolean getBuyerPaysRefundFee() {
        return _buyerPaysRefundFee;
    }

    @JsonProperty("buyerPaysRefundFee")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setBuyerPaysRefundFee(Boolean buyerPaysRefundFee) {
        this._buyerPaysRefundFee = buyerPaysRefundFee;
    }

    @JsonProperty("reference")
    public String getReference() {
        return _reference;
    }

    @JsonProperty("reference")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setReference(String reference) {
        this._reference = reference;
    }

    // Response fields
    //

    @JsonIgnore
    public String getId() {
        return _id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this._id = id;
    }

    @JsonIgnore
    public Date getRequestDate() {
        return _requestDate;
    }

    @JsonProperty("requestDate")
    public void setRequestDate(Date requestDate) {
        this._requestDate = requestDate;
    }

    @JsonIgnore
    public String getStatus() {
        return _status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this._status = status;
    }

    /**
     * Gets the {@link #_transactionAmount} for a Refund.
     * 
     * @return the transaction amount of the Refund
     * 
     * @see    Refund
     */
    @JsonProperty("transactionAmount")
    public BigDecimal getTransactionAmount() {
        return _transactionAmount;
    }

    /**
     * Sets the {@link #_transactionAmount} for a Refund.
     * 
     * @param transactionAmount Amount to be refunded in terms of the transaction currency
     * 
     * @see   Refund
     */
    @JsonProperty("transactionAmount")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setTransactionAmount(BigDecimal transactionAmount) {
        this._transactionAmount = transactionAmount;
    }

    /**
     * Gets the {@link #_transactionRefundFee} for a Refund.
     * 
     * @return the transaction refund fee of the Refund
     * 
     * @see    Refund
     */
    @JsonProperty("transactionRefundFee")
    public BigDecimal getTransactionRefundFee() {
        return _transactionRefundFee;
    }

    /**
     * Sets the {@link #_transactionAmount} for a Refund.
     * 
     * @param transactionRefundFee The refund fee expressed in terms of transaction currency
     * 
     * @see   Refund
     */
    @JsonProperty("transactionRefundFee")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setTransactionRefundFee(BigDecimal transactionRefundFee) {
        this._transactionRefundFee = transactionRefundFee;
    }

    /**
     * Gets the {@link #_transactionCurrency} for a Refund.
     * 
     * @return the transaction currency of the Refund
     * 
     * @see    Refund
     */
    @JsonProperty("transactionCurrency")
    public String getTransactionCurrency() {
        return _transactionCurrency;
    }

    /**
     * Sets the {@link #_transactionCurrency} for a Refund.
     * 
     * @param transactionCurrency The refund fee expressed in terms of transaction currency
     * 
     * @see   Refund
     */
    @JsonProperty("transactionCurrency")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setTransactionCurrency(String transactionCurrency) {
        this._transactionCurrency = transactionCurrency;
    }

    @JsonIgnore
    public Date getLastRefundNotification() {
        return _lastRefundNotification;
    }

    @JsonProperty("lastRefundNotification")
    public void setLastRefundNotification(Date lastRefundNotification) {
        this._lastRefundNotification = lastRefundNotification;
    }

    @JsonIgnore
    public Double getRefundFee() {
        return _refundFee;
    }

    @JsonProperty("refundFee")
    public void setRefundFee(Double refundFee) {
        this._refundFee = refundFee;
    }
}
