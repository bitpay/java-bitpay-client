package com.bitpay.sdk.model.Payout;

import com.bitpay.sdk.exceptions.BitPayException;
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
public class Payout {

    private String _token = "";

    private Double _amount = 0.0;
    private String _currency = "";
    private Long _effectiveDate;

    private String _reference = "";
    private String _notificationEmail = "";
    private String _notificationURL = "";
    private String _redirectUrl = "";
    private String _ledgerCurrency = "";

    private String _id;
    private String _shopperId;
    private String _recipientId;
    private PayoutInstruction _exchangeRates;
    private String _account;
    private String _email;
    private String _label;
    private String _supportPhone;
    private String _status;
    private String _message;
    private Double _percentFee;
    private Double _fee;
    private Double _depositTotal;
    private Double _rate;
    private Double _btc;
    private Long _requestDate;
    private Long _dateExecuted;
    private List<PayoutInstructionTransaction> _transactions = Collections.emptyList();

    /**
     * Constructor, create an empty Payout object.
     */
    public Payout() {
        _amount = 0.0;
        _currency = "USD";
        _notificationEmail = "";
        _notificationURL = "";
    }

    /**
     * Constructor, create an instruction-full request Payout object.
     *
     * @param amount         Date when request is effective. Note that the time of
     *                       day will automatically be set to 09:00:00.000 UTC time
     *                       for the given day. Only requests submitted before
     *                       09:00:00.000 UTC are guaranteed to be processed on the
     *                       same day.
     * @param currency       The three digit currency string for the PayoutBatch to
     *                       use.
     * @param ledgerCurrency Ledger currency code set for the payout request
     *                       (ISO4217 3-character currency code), it indicates on
     *                       which ledger the payoutrequest will be recorded. If not
     *                       provided in the request, this parameter will be set by
     *                       default to the active ledger currency on your
     *                       account,e.g. your settlement currency. Supported ledger
     *                       currency codes for payout requestsare EUR, USD, GBP,
     *                       CAD, NZD, AUD, ZAR, JPY, BTC, BCH, GUSD, USDC, PAX,XRP,
     *                       BUSD, DOGE,ETH, WBTC, DAI
     */
    public Payout(Double amount, String currency, String ledgerCurrency) {
        this._amount = amount;
        this._currency = currency;
        this._ledgerCurrency = ledgerCurrency;
    }

    // API fields
    //

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
        if (!Currency.isValid(currency)) {
            throw new BitPayException(null, "Error: currency code must be a type of Model.Currency");
        }
        this._currency = currency;
    }

    @JsonProperty("ledgerCurrency")
    public String getLedgerCurrency() {
        return _ledgerCurrency;
    }

    @JsonProperty("ledgerCurrency")
    public void setLedgerCurrency(String ledgerCurrency) throws BitPayException {
        if (!Currency.isValid(ledgerCurrency)) {
            throw new BitPayException(null, "Error: currency code must be a type of Model.Currency");
        }
        this._ledgerCurrency = ledgerCurrency;
    }

    @JsonProperty("effectiveDate")
    @JsonSerialize(using = DateSerializer.class)
    public Long getEffectiveDate() {
        return _effectiveDate;
    }

    @JsonProperty("effectiveDate")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setEffectiveDate(Long effectiveDate) {
        this._effectiveDate = effectiveDate;
    }

    @JsonProperty("transactions")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<PayoutInstructionTransaction> getTransactions() {
        return _transactions;
    }

    @JsonProperty("transactions")
    public void setTransactions(List<PayoutInstructionTransaction> transactions) {
        this._transactions = transactions;
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
        return _notificationURL;
    }
    
    @JsonProperty("notificationURL")
    public void setNotificationURL(String notificationURL) {
        this._notificationURL = notificationURL;
    }
    
    @JsonProperty("redirectUrl")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getRedirectUrl() {
        return _redirectUrl;
    }

    @JsonProperty("redirectUrl")
    public void setRedirectUrl(String redirectUrl) {
        this._redirectUrl = redirectUrl;
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

    @JsonProperty("shopperId")
    public String getShopperId() {
        return _shopperId;
    }

    @JsonProperty("shopperId")
    public void setShopperId(String shopperId) {
        this._shopperId = shopperId;
    }

    @JsonProperty("recipientId")
    public String getRecipientId() {
        return _recipientId;
    }

    @JsonProperty("recipientId")
    public void setRecipientId(String recipientId) {
        this._recipientId = recipientId;
    }

    @JsonProperty("exchangeRates")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public PayoutInstruction getExchangeRates() {
        return _exchangeRates;
    }

    @JsonProperty("exchangeRates")
    public void setExchangeRates(PayoutInstruction exchangeRates) {
        this._exchangeRates = exchangeRates;
    }

    @JsonIgnore
    public String getAccount() {
        return _account;
    }

    @JsonProperty("account")
    public void setAccount(String account) {
        this._account = account;
    }

    @JsonIgnore
    public String getMessage() {
        return _message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this._message = message;
    }

    @JsonProperty("email")
    public String getEmail() {
        return _email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this._email = email;
    }

    @JsonIgnore
    public String getLabel() {
        return _label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this._label = label;
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
