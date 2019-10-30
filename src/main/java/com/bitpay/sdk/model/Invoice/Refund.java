package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Refund {

    private String _guid;
    private String _refundEmail;
    private Double _amount;
    private String _currency;
    private String _token;
    private String _id;
    private Date _requestDate;
    private String _status;
    private RefundParams _params = new RefundParams();

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
    public void setGuid(String guid) {
        this._guid = guid;
    }

    @JsonProperty("refundEmail")
    public String getRefundEmail() {
        return _refundEmail;
    }

    @JsonProperty("refundEmail")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setRefundEmail(String refundEmail) {
        this._refundEmail = refundEmail;
    }

    @JsonProperty("amount")
    public Double getAmount() {
        return _amount;
    }

    @JsonProperty("amount")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public void setAmount(Double amount) {
        this._amount = amount;
    }

    @JsonProperty("token")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getToken() {
        return _token;
    }

    @JsonProperty("token")
    public void setToken(String token) {
        this._token = token;
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

    @JsonIgnore
    public RefundParams getParams() {
        return _params;
    }

    @JsonProperty("params")
    public void setPaymentUrls(RefundParams refundParams) {
        this._params = refundParams;
    }

}
