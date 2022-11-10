package com.bitpay.sdk.model.Payout;

import com.bitpay.sdk.exceptions.BitPayException;
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

/**
 * The type Payout batch.
 * See also {@link com.bitpay.sdk.model.Payout.Payout}
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-payouts">REST API Payouts</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutBatch {
    /**
     * The constant MethodVwap24.
     */
    public static final String MethodVwap24 = "vwap_24hr";

    private String _guid = "";
    private String _token = "";

    private Double _amount = 0.0;
    private String _currency = "";
    private Long _effectiveDate;
    private List<PayoutInstruction> _instructions = Collections.emptyList();
    private PayoutInstruction _exchangeRates;
    private String _ledgerCurrency = "";

    private String _reference = "";
    private String _notificationEmail = "";
    private String _notificationURL = "";
    private String _redirectUrl = "";
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
        _notificationURL = "";
        _pricingMethod = MethodVwap24;
    }

    /**
     * Constructor, create an instruction-full request PayoutBatch object.
     *
     * @param currency       The three digit currency string for the PayoutBatch to use.
     * @param effectiveDate  Date when request is effective. Note that the time of day will automatically be set to 09:00:00.000 UTC time for the given day. Only requests submitted before 09:00:00.000 UTC are guaranteed to be processed on the same day.
     * @param instructions   Payout instructions.
     * @param ledgerCurrency Ledger currency code set for the payout request                       (ISO4217 3-character currency code), it indicates on                       which ledger the payoutrequest will be recorded. If not                       provided in the request, this parameter will be set by                       default to the active ledger currency on your                       account,e.g. your settlement currency. Supported ledger                       currency codes for payout requestsare EUR, USD, GBP,                       CAD, NZD, AUD, ZAR, JPY, BTC, BCH, GUSD, USDC, PAX,XRP,                       BUSD, DOGE,ETH, WBTC, DAI
     */
    public PayoutBatch(String currency, Long effectiveDate, List<PayoutInstruction> instructions, String ledgerCurrency) {
        this._currency = currency;
        this._effectiveDate = effectiveDate;
        this._instructions = instructions;
        this._ledgerCurrency = ledgerCurrency;
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

    /**
     * Gets guid.
     *
     * @return the guid
     */
    @JsonProperty("guid")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getGuid() {
        return _guid;
    }

    /**
     * Sets guid.
     *
     * @param guid the guid
     */
    @JsonProperty("guid")
    public void setGuid(String guid) {
        this._guid = guid;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    @JsonProperty("token")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getToken() {
        return _token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    @JsonProperty("token")
    public void setToken(String token) {
        this._token = token;
    }

    // Required fields
    //

    /**
     * Gets amount.
     *
     * @return the amount
     */
    @JsonProperty("amount")
    public Double getAmount() {
        return _amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this._amount = amount;
    }

    /**
     * Gets currency.
     *
     * @return the currency
     */
    @JsonProperty("currency")
    public String getCurrency() {
        return _currency;
    }

    /**
     * Sets currency.
     *
     * @param currency the currency
     * @throws BitPayException the bit pay exception
     */
    @JsonProperty("currency")
    public void setCurrency(String currency) throws BitPayException {
        if (!Currency.isValid(currency))
            throw new BitPayException(null, "Error: currency code must be a type of Model.Currency");

        this._currency = currency;
    }

    /**
     * Gets ledger currency.
     *
     * @return the ledger currency
     */
    @JsonProperty("ledgerCurrency")
    public String getLedgerCurrency() {
        return _ledgerCurrency;
    }

    /**
     * Sets ledger currency.
     *
     * @param ledgerCurrency the ledger currency
     * @throws BitPayException the bit pay exception
     */
    @JsonProperty("ledgerCurrency")
    public void setLedgerCurrency(String ledgerCurrency) throws BitPayException {
        if (!Currency.isValid(ledgerCurrency))
            throw new BitPayException(null, "Error: currency code must be a type of Model.Currency");

        this._ledgerCurrency = ledgerCurrency;
    }

    /**
     * Gets effective date.
     *
     * @return the effective date
     */
    @JsonProperty("effectiveDate")
    @JsonSerialize(using = DateSerializer.class)
    public Long getEffectiveDate() {
        return _effectiveDate;
    }

    /**
     * Sets effective date.
     *
     * @param effectiveDate the effective date
     */
    @JsonProperty("effectiveDate")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setEffectiveDate(Long effectiveDate) {
        this._effectiveDate = effectiveDate;
    }

    /**
     * Gets instructions.
     *
     * @return the instructions
     */
    @JsonProperty("instructions")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<PayoutInstruction> getInstructions() {
        return _instructions;
    }

    /**
     * Sets instructions.
     *
     * @param instructions the instructions
     */
    @JsonProperty("instructions")
    public void setInstructions(List<PayoutInstruction> instructions) {
        this._instructions = instructions;
        _computeAndSetAmount();
    }

    // Optional fields
    //

    /**
     * Gets reference.
     *
     * @return the reference
     */
    @JsonProperty("reference")
    public String getReference() {
        return _reference;
    }

    /**
     * Sets reference.
     *
     * @param reference the reference
     */
    @JsonProperty("reference")
    public void setReference(String reference) {
        this._reference = reference;
    }

    /**
     * Gets notification email.
     *
     * @return the notification email
     */
    @JsonProperty("notificationEmail")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getNotificationEmail() {
        return _notificationEmail;
    }

    /**
     * Sets notification email.
     *
     * @param notificationEmail the notification email
     */
    @JsonProperty("notificationEmail")
    public void setNotificationEmail(String notificationEmail) {
        this._notificationEmail = notificationEmail;
    }

    /**
     * Gets notification url.
     *
     * @return the notification url
     */
    @JsonProperty("notificationURL")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getNotificationURL() {
        return _notificationURL;
    }

    /**
     * Sets notification url.
     *
     * @param notificationURL the notification url
     */
    @JsonProperty("notificationURL")
    public void setNotificationURL(String notificationURL) {
        this._notificationURL = notificationURL;
    }

    /**
     * Gets redirect url.
     *
     * @return the redirect url
     */
    @JsonProperty("redirectUrl")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getRedirectUrl() {
        return _redirectUrl;
    }

    /**
     * Sets redirect url.
     *
     * @param redirectUrl the redirect url
     */
    @JsonProperty("redirectUrl")
    public void setRedirectUrl(String redirectUrl) {
        this._redirectUrl = redirectUrl;
    }

    /**
     * Gets pricing method.
     *
     * @return the pricing method
     */
    @JsonProperty("pricingMethod")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getPricingMethod() {
        return _pricingMethod;
    }

    /**
     * Sets pricing method.
     *
     * @param pricingMethod the pricing method
     */
    @JsonProperty("pricingMethod")
    public void setPricingMethod(String pricingMethod) {
        this._pricingMethod = pricingMethod;
    }

    // Response fields
    //

    /**
     * Gets id.
     *
     * @return the id
     */
    @JsonIgnore
    public String getId() {
        return _id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this._id = id;
    }

    /**
     * Gets account.
     *
     * @return the account
     */
    @JsonIgnore
    public String getAccount() {
        return _account;
    }

    /**
     * Sets account.
     *
     * @param account the account
     */
    @JsonProperty("account")
    public void setAccount(String account) {
        this._account = account;
    }

    /**
     * Gets exchange rates.
     *
     * @return the exchange rates
     */
    @JsonProperty("exchangeRates")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public PayoutInstruction getExchangeRates() {
        return _exchangeRates;
    }

    /**
     * Sets exchange rates.
     *
     * @param exchangeRates the exchange rates
     */
    @JsonProperty("exchangeRates")
    public void setExchangeRates(PayoutInstruction exchangeRates) {
        this._exchangeRates = exchangeRates;
    }

    /**
     * Gets support phone.
     *
     * @return the support phone
     */
    @JsonIgnore
    public String getSupportPhone() {
        return _supportPhone;
    }

    /**
     * Sets support phone.
     *
     * @param supportPhone the support phone
     */
    @JsonProperty("supportPhone")
    public void setSupportPhone(String supportPhone) {
        this._supportPhone = supportPhone;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    @JsonIgnore
    public String getStatus() {
        return _status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this._status = status;
    }

    /**
     * Gets percent fee.
     *
     * @return the percent fee
     */
    @JsonIgnore
    public Double getPercentFee() {
        return _percentFee;
    }

    /**
     * Sets percent fee.
     *
     * @param percentFee the percent fee
     */
    @JsonProperty("percentFee")
    public void setPercentFee(Double percentFee) {
        this._percentFee = percentFee;
    }

    /**
     * Gets fee.
     *
     * @return the fee
     */
    @JsonIgnore
    public Double getFee() {
        return _fee;
    }

    /**
     * Sets fee.
     *
     * @param fee the fee
     */
    @JsonProperty("fee")
    public void setFee(Double fee) {
        this._fee = fee;
    }

    /**
     * Gets deposit total.
     *
     * @return the deposit total
     */
    @JsonIgnore
    public Double getDepositTotal() {
        return _depositTotal;
    }

    /**
     * Sets deposit total.
     *
     * @param depositTotal the deposit total
     */
    @JsonProperty("depositTotal")
    public void setDepositTotal(Double depositTotal) {
        this._depositTotal = depositTotal;
    }

    /**
     * Gets btc.
     *
     * @return the btc
     */
    @JsonIgnore
    public Double getBtc() {
        return _btc;
    }

    /**
     * Sets btc.
     *
     * @param btc the btc
     */
    @JsonProperty("btc")
    public void setBtc(Double btc) {
        this._btc = btc;
    }

    /**
     * Gets rate.
     *
     * @return the rate
     */
    @JsonIgnore
    public Double getRate() {
        return _rate;
    }

    /**
     * Sets rate.
     *
     * @param rate the rate
     */
    @JsonProperty("rate")
    public void setRate(Double rate) {
        this._rate = rate;
    }

    /**
     * Gets request date.
     *
     * @return the request date
     */
    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public long getRequestDate() {
        return _requestDate;
    }

    /**
     * Sets request date.
     *
     * @param requestDate the request date
     */
    @JsonProperty("requestDate")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setRequestDate(long requestDate) {
        this._requestDate = requestDate;
    }

    /**
     * Gets date executed.
     *
     * @return the date executed
     */
    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public long getDateExecuted() {
        return _dateExecuted;
    }

    /**
     * Sets date executed.
     *
     * @param dateExecuted the date executed
     */
    @JsonProperty("dateExecuted")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setDateExecuted(long dateExecuted) {
        this._dateExecuted = dateExecuted;
    }

}
