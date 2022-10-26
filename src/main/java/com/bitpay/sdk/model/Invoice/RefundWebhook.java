/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * The type Refund webhook.
 *
 * @see <a href="https://bitpay.com/api/#notifications-webhooks-instant-payment-notifications-refunds">Webhooks refunds</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundWebhook {

	private String _id;
	private String _invoice;
	private String _supportRequest;
	private String _status;
	private Double _amount;
	private String _currency;
	private Date _lastRefundNotification;
	private Double _refundFee;
	private boolean _immediate;
	private boolean _buyerPaysRefundFee;
	private Date _requestDate;

    /**
     * Instantiates a new Refund webhook.
     */
    public RefundWebhook() {
	}

    /**
     * Gets BitPay refund ID.
     *
     * @return the id
     */
    @JsonProperty("id")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getId() {
		return _id;
	}

    /**
     * Sets BitPay refund ID.
     *
     * @param _id the id
     */
    @JsonProperty("id")
	public void setId(String _id) {
		this._id = _id;
	}

    /**
     * Gets BitPay invoice ID associated to the refund.
     *
     * @return the invoice
     */
    @JsonProperty("invoice")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getInvoice() {
		return _invoice;
	}

    /**
     * Sets BitPay invoice ID associated to the refund.
     *
     * @param _invoice the invoice
     */
    @JsonProperty("invoice")
	public void setInvoice(String _invoice) {
		this._invoice = _invoice;
	}

    /**
     * Gets BitPay support request ID associated to the refund.
     *
     * @return the support request
     */
    @JsonProperty("supportRequest")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getSupportRequest() {
		return _supportRequest;
	}

    /**
     * Sets BitPay support request ID associated to the refund.
     *
     * @param _supportRequest the support request
     */
    @JsonProperty("supportRequest")
	public void setSupportRequest(String _supportRequest) {
		this._supportRequest = _supportRequest;
	}

    /**
     * Gets refund lifecycle status of the request (refer to status field in refunds resource).
	 *
	 * @see <a href="https://bitpay.com/api/#rest-api-resources-refunds-resource">Refunds resource</a>
     *
     * @return the status
     */
    @JsonProperty("status")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getStatus() {
		return _status;
	}

    /**
     * Sets refund lifecycle status of the request (refer to status field in refunds resource).
	 *
	 * @see <a href="https://bitpay.com/api/#rest-api-resources-refunds-resource">Refunds resource</a>
     *
     * @param _status the status
     */
    @JsonProperty("status")
	public void setStatus(String _status) {
		this._status = _status;
	}

    /**
     * Gets amount to be refunded in the currency of the associated invoice.
     *
     * @return the amount
     */
    @JsonProperty("amount")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public Double getAmount() {
		return _amount;
	}

    /**
     * Sets amount to be refunded in the currency of the associated invoice.
     *
     * @param _amount the amount
     */
    @JsonProperty("amount")
	public void setAmount(Double _amount) {
		this._amount = _amount;
	}

    /**
     * Gets currency used for the refund, the same as the currency used to create the associated invoice.
     *
     * @return the currency
     */
    @JsonProperty("currency")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getCurrency() {
		return _currency;
	}

    /**
     * Sets currency used for the refund, the same as the currency used to create the associated invoice.
     *
     * @param _currency the currency
     */
    @JsonProperty("currency")
	public void setCurrency(String _currency) {
		this._currency = _currency;
	}

    /**
     * Gets timestamp of last notification sent to customer about refund.
     *
     * @return the last refund notification
     */
    @JsonProperty("lastRefundNotification")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public Date getLastRefundNotification() {
		return _lastRefundNotification;
	}

    /**
     * Sets timestamp of last notification sent to customer about refund.
     *
     * @param _lastRefundNotification the last refund notification
     */
    @JsonProperty("lastRefundNotification")
	public void setLastRefundNotification(Date _lastRefundNotification) {
		this._lastRefundNotification = _lastRefundNotification;
	}

    /**
     * Gets the amount of refund fee expressed in terms of pricing currency.
     *
     * @return the refund fee
     */
    @JsonProperty("refundFee")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public Double getRefundFee() {
		return _refundFee;
	}

    /**
     * Sets the amount of refund fee expressed in terms of pricing currency.
     *
     * @param _refundFee the refund fee
     */
    @JsonProperty("refundFee")
	public void setRefundFee(Double _refundFee) {
		this._refundFee = _refundFee;
	}

    /**
     * Gets immediate. Whether funds should be removed from merchant ledger immediately on submission or
	 * at time of processing.
     *
     * @return the immediate
     */
    @JsonProperty("immediate")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public boolean getImmediate() {
		return _immediate;
	}

    /**
     * Sets immediate. Whether funds should be removed from merchant ledger immediately on submission or
	 * at time of processing
     *
     * @param _immediate the immediate
     */
    @JsonProperty("immediate")
	public void setImmediate(boolean _immediate) {
		this._immediate = _immediate;
	}

    /**
     * Gets buyer pays refund fee. Whether the buyer should pay the refund fee (default is the merchant).
     *
     * @return the buyer pays refund fee
     */
    @JsonProperty("buyerPaysRefundFee")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public boolean getBuyerPaysRefundFee() {
		return _buyerPaysRefundFee;
	}

    /**
     * Sets buyer pays refund fee. Whether the buyer should pay the refund fee (default is the merchant).
     *
     * @param buyerPaysRefundFee the buyer pays refund fee
     */
    @JsonProperty("buyerPaysRefundFee")
	public void setBuyerPaysRefundFee(boolean buyerPaysRefundFee) {
		this._buyerPaysRefundFee = buyerPaysRefundFee;
	}

    /**
     * Gets request date. Timestamp the refund request was created.
     *
     * @return the request date
     */
    @JsonProperty("requestDate")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public Date getRequestDate() {
		return _requestDate;
	}

    /**
     * Sets request date. Timestamp the refund request was created.
     *
     * @param requestDate the request date
     */
    @JsonProperty("requestDate")
	public void setRequestDate(Date requestDate) {
		this._requestDate = requestDate;
	}

}
