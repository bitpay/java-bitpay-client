package com.bitpay.sdk.model.Payout;

import com.bitpay.sdk.BitPayException;
import com.bitpay.sdk.Client;
import com.bitpay.sdk.model.Currency;
import com.bitpay.sdk.util.DateDeserializer;
import com.bitpay.sdk.util.DateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutBatch {
    public static final String MethodVwap24 = "vwap_24hr";

    private String _guid = "";
    private String _token = "";

    private Double _amount = 0.0;
    private String _currency = "";
    private Long _effectiveDate;
    private List<PayoutInstruction> _instructions = Collections.emptyList();

    private String _reference = "";
    private String _notificationEmail = "";
    private String _notificationUrl = "";
    private String _pricingMethod = MethodVwap24;

    private String _id;
    private String _account;
    private String _supportPhone;
    private String _status;
    private Double _percentFee;
    private Double _fee;
    private Double _depositTotal;
    private Double _rate;
    private Double _btc;
    private Long _requestDate;
    private Long _dateExecuted;

    /**
     * Constructor, create an empty PayoutBatch object.
     */
    public PayoutBatch() {
        _amount = 0.0;
        _currency = "USD";
        _notificationEmail = "";
        _notificationUrl = "";
        _pricingMethod = MethodVwap24;
    }

    /**
     * Constructor, create an instruction-full request PayoutBatch object.
     *
     * @param currency      The three digit currency string for the PayoutBatch to use.
     * @param effectiveDate Date when request is effective. Note that the time of day will automatically be set to 09:00:00.000 UTC time for the given day. Only requests submitted before 09:00:00.000 UTC are guaranteed to be processed on the same day.
     * @param instructions  Payout instructions.
     */
    public PayoutBatch(String currency, long effectiveDate, List<PayoutInstruction> instructions) {
        this._currency = currency;
        this._effectiveDate = effectiveDate;
        this._instructions = instructions;
        _computeAndSetAmount();
    }

    // Private methods
    //

    private void _computeAndSetAmount() {
        Map currencyInfo = Client.getCurrencyInfo("USD");
        Integer precision = currencyInfo.isEmpty() ? 2 : Integer.valueOf(currencyInfo.get("precision").toString());

        Double amount = 0.0;
        for (PayoutInstruction instruction : this._instructions) {
            amount += instruction.getAmount();
        }

        this._amount = new BigDecimal(amount).setScale(precision, RoundingMode.HALF_UP).doubleValue();
    }

    // API fields
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

    @JsonProperty("token")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getToken() {
        return _token;
    }

    @JsonProperty("token")
    public void setToken(String token) {
        this._token = token;
    }

    // Required fields
    //

    @JsonProperty("amount")
    public Double getAmount() {
        return _amount;
    }

    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this._amount = amount;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return _currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) throws BitPayException {
        if (!Currency.isValid(_currency))
            throw new BitPayException("Error: currency code must be a type of Model.Currency");

        this._currency = currency;
    }

    @JsonProperty("effectiveDate")
    @JsonSerialize(using = DateSerializer.class)
    public long getEffectiveDate() {
        return _effectiveDate;
    }

    @JsonProperty("effectiveDate")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setEffectiveDate(long effectiveDate) {
        this._effectiveDate = effectiveDate;
    }

    @JsonProperty("instructions")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<PayoutInstruction> getInstructions() {
        return _instructions;
    }

    @JsonProperty("instructions")
    public void setInstructions(List<PayoutInstruction> instructions) {
        this._instructions = instructions;
        _computeAndSetAmount();
    }

    // Optional fields
    //

    @JsonProperty("reference")
    public String getReference() {
        return _reference;
    }

    @JsonProperty("reference")
    public void setReference(String reference) {
        this._reference = reference;
    }

    @JsonProperty("notificationEmail")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getNotificationEmail() {
        return _notificationEmail;
    }

    @JsonProperty("notificationEmail")
    public void setNotificationEmail(String notificationEmail) {
        this._notificationEmail = notificationEmail;
    }

    @JsonProperty("notificationURL")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getNotificationURL() {
        return _notificationUrl;
    }

    @JsonProperty("notificationURL")
    public void setRedirectURL(String notificationUrl) {
        this._notificationUrl = notificationUrl;
    }

    @JsonProperty("pricingMethod")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getPricingMethod() {
        return _pricingMethod;
    }

    @JsonProperty("pricingMethod")
    public void setPricingMethod(String pricingMethod) {
        this._pricingMethod = pricingMethod;
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
    public String getAccount() {
        return _account;
    }

    @JsonProperty("Account")
    public void setAccount(String account) {
        this._account = account;
    }

    @JsonIgnore
    public String getSupportPhone() {
        return _supportPhone;
    }

    @JsonProperty("supportPhone")
    public void setSupportPhone(String supportPhone) {
        this._supportPhone = supportPhone;
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
    public Double getPercentFee() {
        return _percentFee;
    }

    @JsonProperty("percentFee")
    public void setPercentFee(Double percentFee) {
        this._percentFee = percentFee;
    }

    @JsonIgnore
    public Double getFee() {
        return _fee;
    }

    @JsonProperty("fee")
    public void setFee(Double fee) {
        this._fee = fee;
    }

    @JsonIgnore
    public Double getDepositTotal() {
        return _depositTotal;
    }

    @JsonProperty("depositTotal")
    public void setDepositTotal(Double depositTotal) {
        this._depositTotal = depositTotal;
    }

    @JsonIgnore
    public Double getBtc() {
        return _btc;
    }

    @JsonProperty("btc")
    public void setBtc(Double btc) {
        this._btc = btc;
    }

    @JsonIgnore
    public Double getRate() {
        return _rate;
    }

    @JsonProperty("rate")
    public void setRate(Double rate) {
        this._rate = rate;
    }

    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public long getRequestDate() {
        return _requestDate;
    }

    @JsonProperty("requestDate")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setRequestDate(long requestDate) {
        this._requestDate = requestDate;
    }

    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public long getDateExecuted() {
        return _dateExecuted;
    }

    @JsonProperty("dateExecuted")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setDateExecuted(long dateExecuted) {
        this._dateExecuted = dateExecuted;
    }

}
