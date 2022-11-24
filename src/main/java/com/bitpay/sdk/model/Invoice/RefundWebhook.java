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

	private String id;
	private String invoice;
	private String supportRequest;
	private String status;
	private Double amount;
	private String currency;
	private Date lastRefundNotification;
	private Double refundFee;
	private boolean immediate;
	private boolean buyerPaysRefundFee;
	private Date requestDate;

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
		return this.id;
	}

    /**
     * Sets BitPay refund ID.
     *
     * @param id the id
     */
    @JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

    /**
     * Gets BitPay invoice ID associated to the refund.
     *
     * @return the invoice
     */
    @JsonProperty("invoice")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getInvoice() {
		return this.invoice;
	}

    /**
     * Sets BitPay invoice ID associated to the refund.
     *
     * @param invoice the invoice
     */
    @JsonProperty("invoice")
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

    /**
     * Gets BitPay support request ID associated to the refund.
     *
     * @return the support request
     */
    @JsonProperty("supportRequest")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getSupportRequest() {
		return this.supportRequest;
	}

    /**
     * Sets BitPay support request ID associated to the refund.
     *
     * @param supportRequest the support request
     */
    @JsonProperty("supportRequest")
	public void setSupportRequest(String supportRequest) {
		this.supportRequest = supportRequest;
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
		return this.status;
	}

    /**
     * Sets refund lifecycle status of the request (refer to status field in refunds resource).
	 *
	 * @see <a href="https://bitpay.com/api/#rest-api-resources-refunds-resource">Refunds resource</a>
     *
     * @param status the status
     */
    @JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

    /**
     * Gets amount to be refunded in the currency of the associated invoice.
     *
     * @return the amount
     */
    @JsonProperty("amount")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public Double getAmount() {
		return this.amount;
	}

    /**
     * Sets amount to be refunded in the currency of the associated invoice.
     *
     * @param amount the amount
     */
    @JsonProperty("amount")
	public void setAmount(Double amount) {
		this.amount = amount;
	}

    /**
     * Gets currency used for the refund, the same as the currency used to create the associated invoice.
     *
     * @return the currency
     */
    @JsonProperty("currency")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getCurrency() {
		return this.currency;
	}

    /**
     * Sets currency used for the refund, the same as the currency used to create the associated invoice.
     *
     * @param currency the currency
     */
    @JsonProperty("currency")
	public void setCurrency(String currency) {
		this.currency = currency;
	}

    /**
     * Gets timestamp of last notification sent to customer about refund.
     *
     * @return the last refund notification
     */
    @JsonProperty("lastRefundNotification")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public Date getLastRefundNotification() {
		return this.lastRefundNotification;
	}

    /**
     * Sets timestamp of last notification sent to customer about refund.
     *
     * @param lastRefundNotification the last refund notification
     */
    @JsonProperty("lastRefundNotification")
	public void setLastRefundNotification(Date lastRefundNotification) {
		this.lastRefundNotification = lastRefundNotification;
	}

    /**
     * Gets the amount of refund fee expressed in terms of pricing currency.
     *
     * @return the refund fee
     */
    @JsonProperty("refundFee")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public Double getRefundFee() {
		return this.refundFee;
	}

    /**
     * Sets the amount of refund fee expressed in terms of pricing currency.
     *
     * @param refundFee the refund fee
     */
    @JsonProperty("refundFee")
	public void setRefundFee(Double refundFee) {
		this.refundFee = refundFee;
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
		return this.immediate;
	}

    /**
     * Sets immediate. Whether funds should be removed from merchant ledger immediately on submission or
	 * at time of processing
     *
     * @param immediate the immediate
     */
    @JsonProperty("immediate")
	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

    /**
     * Gets buyer pays refund fee. Whether the buyer should pay the refund fee (default is the merchant).
     *
     * @return the buyer pays refund fee
     */
    @JsonProperty("buyerPaysRefundFee")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public boolean getBuyerPaysRefundFee() {
		return this.buyerPaysRefundFee;
	}

    /**
     * Sets buyer pays refund fee. Whether the buyer should pay the refund fee (default is the merchant).
     *
     * @param buyerPaysRefundFee the buyer pays refund fee
     */
    @JsonProperty("buyerPaysRefundFee")
	public void setBuyerPaysRefundFee(boolean buyerPaysRefundFee) {
		this.buyerPaysRefundFee = buyerPaysRefundFee;
	}

    /**
     * Gets request date. Timestamp the refund request was created.
     *
     * @return the request date
     */
    @JsonProperty("requestDate")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public Date getRequestDate() {
		return this.requestDate;
	}

    /**
     * Sets request date. Timestamp the refund request was created.
     *
     * @param requestDate the request date
     */
    @JsonProperty("requestDate")
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

}
