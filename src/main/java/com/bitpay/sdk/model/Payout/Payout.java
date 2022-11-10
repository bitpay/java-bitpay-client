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

import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payout {

    private String token = "";

    private Double amount = 0.0;
    private String currency = "";
    private Long effectiveDate;

    private String reference = "";
    private String notificationEmail = "";
    private String notificationURL = "";
    private String redirectUrl = "";
    private String ledgerCurrency = "";

    private String id;
    private String shopperId;
    private String recipientId;
    private PayoutInstruction exchangeRates;
    private String account;
    private String email;
    private String label;
    private String supportPhone;
    private String status;
    private String message;
    private Double percentFee;
    private Double fee;
    private Double depositTotal;
    private Double rate;
    private Double btc;
    private Long requestDate;
    private Long dateExecuted;
    private List<PayoutInstructionTransaction> transactions = Collections.emptyList();

    /**
     * Constructor, create an empty Payout object.
     */
    public Payout() {
        amount = 0.0;
        currency = "USD";
        notificationEmail = "";
        notificationURL = "";
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
        this.amount = amount;
        this.currency = currency;
        this.ledgerCurrency = ledgerCurrency;
    }

    // API fields
    //

    @JsonProperty("token")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getToken() {
        return this.token;
    }

    @JsonProperty("token")
    public void setToken(String token) {
        this.token = token;
    }

    // Required fields
    //

    @JsonProperty("amount")
    public Double getAmount() {
        return this.amount;
    }

    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return this.currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) throws BitPayException {
        if (!Currency.isValid(currency)) {
            throw new BitPayException(null, "Error: currency code must be a type of Model.Currency");
        }
        this.currency = currency;
    }

    @JsonProperty("ledgerCurrency")
    public String getLedgerCurrency() {
        return this.ledgerCurrency;
    }

    @JsonProperty("ledgerCurrency")
    public void setLedgerCurrency(String ledgerCurrency) throws BitPayException {
        if (!Currency.isValid(ledgerCurrency)) {
            throw new BitPayException(null, "Error: currency code must be a type of Model.Currency");
        }
        this.ledgerCurrency = ledgerCurrency;
    }

    @JsonProperty("effectiveDate")
    @JsonSerialize(using = DateSerializer.class)
    public Long getEffectiveDate() {
        return this.effectiveDate;
    }

    @JsonProperty("effectiveDate")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setEffectiveDate(Long effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @JsonProperty("transactions")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<PayoutInstructionTransaction> getTransactions() {
        return this.transactions;
    }

    @JsonProperty("transactions")
    public void setTransactions(List<PayoutInstructionTransaction> transactions) {
        this.transactions = transactions;
    }

    // Optional fields
    //

    @JsonProperty("reference")
    public String getReference() {
        return this.reference;
    }

    @JsonProperty("reference")
    public void setReference(String reference) {
        this.reference = reference;
    }

    @JsonProperty("notificationEmail")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getNotificationEmail() {
        return this.notificationEmail;
    }

    @JsonProperty("notificationEmail")
    public void setNotificationEmail(String notificationEmail) {
        this.notificationEmail = notificationEmail;
    }

    @JsonProperty("notificationURL")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getNotificationURL() {
        return this.notificationURL;
    }
    
    @JsonProperty("notificationURL")
    public void setNotificationURL(String notificationURL) {
        this.notificationURL = notificationURL;
    }
    
    @JsonProperty("redirectUrl")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    @JsonProperty("redirectUrl")
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    // Response fields
    //

    @JsonIgnore
    public String getId() {
        return this.id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("shopperId")
    public String getShopperId() {
        return this.shopperId;
    }

    @JsonProperty("shopperId")
    public void setShopperId(String shopperId) {
        this.shopperId = shopperId;
    }

    @JsonProperty("recipientId")
    public String getRecipientId() {
        return this.recipientId;
    }

    @JsonProperty("recipientId")
    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    @JsonProperty("exchangeRates")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public PayoutInstruction getExchangeRates() {
        return this.exchangeRates;
    }

    @JsonProperty("exchangeRates")
    public void setExchangeRates(PayoutInstruction exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    @JsonIgnore
    public String getAccount() {
        return this.account;
    }

    @JsonProperty("account")
    public void setAccount(String account) {
        this.account = account;
    }

    @JsonIgnore
    public String getMessage() {
        return this.message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("email")
    public String getEmail() {
        return this.email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getLabel() {
        return this.label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    @JsonIgnore
    public String getSupportPhone() {
        return this.supportPhone;
    }

    @JsonProperty("supportPhone")
    public void setSupportPhone(String supportPhone) {
        this.supportPhone = supportPhone;
    }

    @JsonIgnore
    public String getStatus() {
        return this.status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonIgnore
    public Double getPercentFee() {
        return this.percentFee;
    }

    @JsonProperty("percentFee")
    public void setPercentFee(Double percentFee) {
        this.percentFee = percentFee;
    }

    @JsonIgnore
    public Double getFee() {
        return this.fee;
    }

    @JsonProperty("fee")
    public void setFee(Double fee) {
        this.fee = fee;
    }

    @JsonIgnore
    public Double getDepositTotal() {
        return this.depositTotal;
    }

    @JsonProperty("depositTotal")
    public void setDepositTotal(Double depositTotal) {
        this.depositTotal = depositTotal;
    }

    @JsonIgnore
    public Double getBtc() {
        return this.btc;
    }

    @JsonProperty("btc")
    public void setBtc(Double btc) {
        this.btc = btc;
    }

    @JsonIgnore
    public Double getRate() {
        return this.rate;
    }

    @JsonProperty("rate")
    public void setRate(Double rate) {
        this.rate = rate;
    }

    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public long getRequestDate() {
        return this.requestDate;
    }

    @JsonProperty("requestDate")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setRequestDate(long requestDate) {
        this.requestDate = requestDate;
    }

    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public long getDateExecuted() {
        return this.dateExecuted;
    }

    @JsonProperty("dateExecuted")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setDateExecuted(long dateExecuted) {
        this.dateExecuted = dateExecuted;
    }

}
