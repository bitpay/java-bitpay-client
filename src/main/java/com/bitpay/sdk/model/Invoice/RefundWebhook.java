package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

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

	public RefundWebhook() {
	}

	@JsonProperty("id")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getId() {
		return _id;
	}

	@JsonProperty("id")
	public void setId(String _id) {
		this._id = _id;
	}

	@JsonProperty("invoice")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getInvoice() {
		return _invoice;
	}

	@JsonProperty("invoice")
	public void setInvoice(String _invoice) {
		this._invoice = _invoice;
	}

	@JsonProperty("supportRequest")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getSupportRequest() {
		return _supportRequest;
	}

	@JsonProperty("supportRequest")
	public void setSupportRequest(String _supportRequest) {
		this._supportRequest = _supportRequest;
	}

	@JsonProperty("status")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getStatus() {
		return _status;
	}

	@JsonProperty("status")
	public void setStatus(String _status) {
		this._status = _status;
	}

	@JsonProperty("amount")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public Double getAmount() {
		return _amount;
	}

	@JsonProperty("amount")
	public void setAmount(Double _amount) {
		this._amount = _amount;
	}

	@JsonProperty("currency")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getCurrency() {
		return _currency;
	}

	@JsonProperty("currency")
	public void setCurrency(String _currency) {
		this._currency = _currency;
	}

	@JsonProperty("lastRefundNotification")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public Date getLastRefundNotification() {
		return _lastRefundNotification;
	}

	@JsonProperty("lastRefundNotification")
	public void setLastRefundNotification(Date _lastRefundNotification) {
		this._lastRefundNotification = _lastRefundNotification;
	}

	@JsonProperty("refundFee")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public Double getRefundFee() {
		return _refundFee;
	}

	@JsonProperty("refundFee")
	public void setRefundFee(Double _refundFee) {
		this._refundFee = _refundFee;
	}

	@JsonProperty("immediate")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public boolean getImmediate() {
		return _immediate;
	}

	@JsonProperty("immediate")
	public void setImmediate(boolean _immediate) {
		this._immediate = _immediate;
	}

	@JsonProperty("buyerPaysRefundFee")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public boolean getBuyerPaysRefundFee() {
		return _buyerPaysRefundFee;
	}

	@JsonProperty("buyerPaysRefundFee")
	public void setBuyerPaysRefundFee(boolean buyerPaysRefundFee) {
		this._buyerPaysRefundFee = _buyerPaysRefundFee;
	}

	@JsonProperty("requestDate")
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public Date getRequestDate() {
		return _requestDate;
	}

	@JsonProperty("requestDate")
	public void setRequestDate(Date requestDate) {
		this._requestDate = _requestDate;
	}

}
