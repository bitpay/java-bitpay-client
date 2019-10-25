package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundParams {

    private String _requesterType        = "";
    private String _requesterEmail       = "";
    private Double _amount               = 0.0;
    private String _currency             = "";
    private String _email                = "";
    private String _purchaserNotifyEmail = "";
    private String _refundAddress        = "";
    private String _supportRequestEid    = "";

    public RefundParams() {
    }

    @JsonIgnore
    public String getRequesterType() {
        return _requesterType;
    }

    @JsonProperty("requesterType")
    public void setRequesterType(String requesterType) {
        this._requesterType = requesterType;
    }

    @JsonIgnore
    public String getRequesterEmail() {
        return _requesterEmail;
    }

    @JsonProperty("requesterEmail")
    public void setRequesterEmail(String requesterEmail) {
        this._requesterEmail = requesterEmail;
    }

    @JsonIgnore
    public Double getAmount() {
        return _amount;
    }

    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this._amount = amount;
    }

    @JsonIgnore
    public String getCurrency() {
        return _currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this._currency = currency;
    }

    @JsonIgnore
    public String getEmail() {
        return _email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this._email = email;
    }

    @JsonIgnore
    public String getPurchaserNotifyEmail() {
        return _purchaserNotifyEmail;
    }

    @JsonProperty("purchaserNotifyEmail")
    public void setPurchaserNotifyEmail(String purchaserNotifyEmail) {
        this._purchaserNotifyEmail = purchaserNotifyEmail;
    }

    @JsonIgnore
    public String getRefundAddress() {
        return _refundAddress;
    }

    @JsonProperty("refundAddress")
    public void setRefundAddress(String refundAddress) {
        this._refundAddress = refundAddress;
    }

    @JsonIgnore
    public String getSupportRequestEid() {
        return _supportRequestEid;
    }

    @JsonProperty("supportRequestEid")
    public void setSupportRequestEid(String supportRequestEid) {
        this._supportRequestEid = supportRequestEid;
    }
}
