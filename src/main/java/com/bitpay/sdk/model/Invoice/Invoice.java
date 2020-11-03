package com.bitpay.sdk.model.Invoice;

import com.bitpay.sdk.BitPayException;
import com.bitpay.sdk.model.Currency;
import com.fasterxml.jackson.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Invoice {

    private String _currency;

    private String _guid = "";
    private String _token = "";

    private Double _price;
    private String _posData = "";
    private String _notificationURL = "";
    private String _transactionSpeed = "";
    private boolean _fullNotifications = false;
    private String _notificationEmail = "";
    private String _redirectURL = "";
    private String _orderId = "";
    private String _itemDesc = "";
    private String _itemCode = "";
    private boolean _physical = false;
    private List<String> _paymentCurrencies;
    private long _acceptanceWindow;
    private Buyer _buyer;

    private String _id;
    private String _url;
    private String _status;
    private boolean _lowFeeDetected;
    private long _invoiceTime;
    private long _expirationTime;
    private long _currentTime;
    private String _exceptionStatus;
    private long _targetConfirmations;
    private List<InvoiceTransaction> _transactions;
    private ArrayList _refundAddresses;
    private boolean _refundAddressRequestPending;
    private String _buyerProvidedEmail;
    private InvoiceBuyerProvidedInfo _invoiceBuyerProvidedInfo = new InvoiceBuyerProvidedInfo();
    private SupportedTransactionCurrencies _supportedTransactionCurrencies = new SupportedTransactionCurrencies();
    private MinerFees _minerFees = new MinerFees();
    private Shopper _shopper = new Shopper();
    private String _billId;
    private ArrayList<RefundInfo> _refundInfo;
    private boolean _extendedNotifications = false;
    private String _transactionCurrency;
    private BigDecimal _amountPaid;
    private Hashtable<String, Hashtable<String, String>> _exchangeRates;

    /**
     * Constructor, create an empty Invoice object.
     */
    public Invoice() {
    }

    /**
     * Constructor, create a minimal request Invoice object.
     *
     * @param price    The amount for which the invoice will be created.
     * @param currency The three digit currency type used to compute the invoice bitcoin amount.
     */
    public Invoice(Double price, String currency) {
        this._price = price;
        this._currency = currency;
    }

    // API fields
    //

    @JsonProperty("guid")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getGuid() {
        return _guid;
    }

    @JsonProperty("guid")
    public void setGuid(String _guid) {
        this._guid = _guid;
    }

    @JsonProperty("token")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getToken() {
        return _token;
    }

    @JsonProperty("token")
    public void setToken(String _token) {
        this._token = _token;
    }

    // Required fields
    //

    @JsonProperty("price")
    public Double getPrice() {
        return _price;
    }

    @JsonProperty("price")
    public void setPrice(Double _price) {
        this._price = _price;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return _currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String _currency) throws BitPayException {
        if (!Currency.isValid(_currency))
            throw new BitPayException("Error: currency code must be a type of Model.Currency");

        this._currency = _currency;
    }

    // Optional fields
    //

    @JsonProperty("orderId")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getOrderId() {
        return _orderId;
    }

    @JsonProperty("orderId")
    public void setOrderId(String _orderId) {
        this._orderId = _orderId;
    }

    @JsonProperty("itemDesc")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getItemDesc() {
        return _itemDesc;
    }

    @JsonProperty("itemDesc")
    public void setItemDesc(String _itemDesc) {
        this._itemDesc = _itemDesc;
    }

    @JsonProperty("itemCode")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getItemCode() {
        return _itemCode;
    }

    @JsonProperty("itemCode")
    public void setItemCode(String _itemCode) {
        this._itemCode = _itemCode;
    }

    @JsonProperty("posData")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getPosData() {
        return _posData;
    }

    @JsonProperty("posData")
    public void setPosData(String _posData) {
        this._posData = _posData;
    }

    @JsonProperty("notificationURL")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getNotificationURL() {
        return _notificationURL;
    }

    @JsonProperty("notificationURL")
    public void setNotificationURL(String _notificationURL) {
        this._notificationURL = _notificationURL;
    }

    @JsonProperty("transactionSpeed")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getTransactionSpeed() {
        return _transactionSpeed;
    }

    @JsonProperty("transactionSpeed")
    public void setTransactionSpeed(String _transactionSpeed) {
        this._transactionSpeed = _transactionSpeed;
    }

    @JsonProperty("fullNotifications")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean getFullNotifications() {
        return _fullNotifications;
    }

    @JsonProperty("fullNotifications")
    public void setFullNotifications(boolean _fullNotifications) {
        this._fullNotifications = _fullNotifications;
    }

    @JsonProperty("extendedNotifications")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean getExtendedNotifications() {
        return _extendedNotifications;
    }

    @JsonProperty("extendedNotifications")
    public void setExtendedNotifications(boolean _extendedNotifications) {
        this._extendedNotifications = _extendedNotifications;
    }

    @JsonProperty("notificationEmail")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getNotificationEmail() {
        return _notificationEmail;
    }

    @JsonProperty("notificationEmail")
    public void setNotificationEmail(String _notificationEmail) {
        this._notificationEmail = _notificationEmail;
    }

    @JsonProperty("redirectURL")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getRedirectURL() {
        return _redirectURL;
    }

    @JsonProperty("redirectURL")
    public void setRedirectURL(String _redirectURL) {
        this._redirectURL = _redirectURL;
    }

    @JsonProperty("physical")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean getPhysical() {
        return _physical;
    }

    @JsonProperty("physical")
    public void setPhysical(boolean _physical) {
        this._physical = _physical;
    }

    @JsonProperty("paymentCurrencies")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<String> getPaymentCurrencies() {
        return _paymentCurrencies;
    }

    @JsonProperty("paymentCurrencies")
    public void setPaymentCurrencies(List<String> _paymentCurrencies) {
        this._paymentCurrencies = _paymentCurrencies;
    }

    @JsonProperty("acceptanceWindow")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public long getAcceptanceWindow() {
        return _acceptanceWindow;
    }

    @JsonProperty("acceptanceWindow")
    public void setAcceptanceWindow(long _acceptanceWindow) {
        this._acceptanceWindow = _acceptanceWindow;
    }

    // Buyer data
    //

    @JsonProperty("buyer")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Buyer getBuyer() {
        return _buyer;
    }

    @JsonProperty("buyer")
    public void setBuyer(Buyer _buyer) {
        this._buyer = _buyer;
    }

    // Response fields
    //

    @JsonIgnore
    public String getId() {
        return _id;
    }

    @JsonProperty("id")
    public void setId(String _id) {
        this._id = _id;
    }

    @JsonIgnore
    public String getUrl() {
        return _url;
    }

    @JsonProperty("url")
    public void setUrl(String _url) {
        this._url = _url;
    }

    @JsonIgnore
    public String getStatus() {
        return _status;
    }

    @JsonProperty("status")
    public void setStatus(String _status) {
        this._status = _status;
    }

    @JsonIgnore
    public Boolean getLowFeeDetected() {
        return _lowFeeDetected;
    }

    @JsonProperty("lowFeeDetected")
    public void setLowFeeDetected(Boolean _lowFeeDetected) {
        this._lowFeeDetected = _lowFeeDetected;
    }

    @JsonIgnore
    public long getInvoiceTime() {
        return _invoiceTime;
    }

    @JsonProperty("invoiceTime")
    public void setInvoiceTime(long _invoiceTime) {
        this._invoiceTime = _invoiceTime;
    }

    @JsonIgnore
    public long getExpirationTime() {
        return _expirationTime;
    }

    @JsonProperty("expirationTime")
    public void setExpirationTime(long _expirationTime) {
        this._expirationTime = _expirationTime;
    }

    @JsonIgnore
    public long getCurrentTime() {
        return _currentTime;
    }

    @JsonProperty("currentTime")
    public void setCurrentTime(long _currentTime) {
        this._currentTime = _currentTime;
    }

    @JsonIgnore
    public List<InvoiceTransaction> getTransactions() {
        return _transactions;
    }

    @JsonProperty("transactions")
    public void setTransactions(List<InvoiceTransaction> _transactions) {
        this._transactions = _transactions;
    }

    @JsonIgnore
    public String getExceptionStatus() {
        return _exceptionStatus;
    }

    @JsonProperty("exceptionStatus")
    public void setExceptionStatus(String _exceptionStatus) {
        this._exceptionStatus = _exceptionStatus;
    }

    @JsonIgnore
    public long getTargetConfirmations() {
        return _targetConfirmations;
    }

    @JsonProperty("targetConfirmations")
    public void setTargetConfirmations(long _targetConfirmations) {
        this._targetConfirmations = _targetConfirmations;
    }

    @JsonIgnore
    public ArrayList getRefundAddresses() {
        return _refundAddresses;
    }

    @JsonProperty("refundAddresses")
    public void setRefundAddresses(ArrayList _refundAddresses) {
        this._refundAddresses = _refundAddresses;
    }

    @JsonIgnore
    public boolean getRefundAddressRequestPending() {
        return _refundAddressRequestPending;
    }

    @JsonProperty("refundAddressRequestPending")
    public void setRefundAddressRequestPending(boolean _refundAddressRequestPending) {
        this._refundAddressRequestPending = _refundAddressRequestPending;
    }

    @JsonIgnore
    public String getBuyerProvidedEmail() {
        return _buyerProvidedEmail;
    }

    @JsonProperty("buyerProvidedEmail")
    public void setBuyerProvidedEmail(String _buyerProvidedEmail) {
        this._buyerProvidedEmail = _buyerProvidedEmail;
    }

    @JsonIgnore
    public InvoiceBuyerProvidedInfo getInvoiceBuyerProvidedInfo() {
        return _invoiceBuyerProvidedInfo;
    }

    @JsonProperty("invoiceBuyerProvidedInfo")
    public void setInvoiceBuyerProvidedInfo(InvoiceBuyerProvidedInfo _invoiceBuyerProvidedInfo) {
        this._invoiceBuyerProvidedInfo = _invoiceBuyerProvidedInfo;
    }

    @JsonIgnore
    public SupportedTransactionCurrencies getSupportedTransactionCurrencies() {
        return _supportedTransactionCurrencies;
    }

    @JsonProperty("supportedTransactionCurrencies")
    public void setSupportedTransactionCurrencies(SupportedTransactionCurrencies _supportedTransactionCurrencies) {
        this._supportedTransactionCurrencies = _supportedTransactionCurrencies;
    }

    @JsonIgnore
    public MinerFees getMinerFees() {
        return _minerFees;
    }

    @JsonProperty("minerFees")
    public void setMinerFees(MinerFees _minerFees) {
        this._minerFees = _minerFees;
    }

    @JsonIgnore
    public Shopper getShopper() {
        return _shopper;
    }

    @JsonProperty("shopper")
    public void setShopper(Shopper _shopper) {
        this._shopper = _shopper;
    }

    @JsonIgnore
    public String getBillId() {
        return _billId;
    }

    @JsonProperty("billId")
    public void setBillId(String _billId) {
        this._billId = _billId;
    }

    @JsonIgnore
    public ArrayList<RefundInfo> getRefundInfo() {
        return _refundInfo;
    }

    @JsonProperty("refundInfo")
    public void setRefundInfo(ArrayList<RefundInfo> _refundInfo) {
        this._refundInfo = _refundInfo;
    }

    @JsonIgnore
    public String getTransactionCurrency() {
        return _transactionCurrency;
    }

    @JsonProperty("transactionCurrency")
    public void setTransactionCurrency(String _transactionCurrency) {
        this._transactionCurrency = _transactionCurrency;
    }

    @JsonIgnore
    public BigDecimal getAmountPaid() {
        return _amountPaid;
    }

    @JsonProperty("amountPaid")
    public void setAmountPaid(BigDecimal _amountPaid) {
        this._amountPaid = _amountPaid;
    }

    @JsonIgnore
    public Hashtable<String, Hashtable<String, String>> getExchangeRates() {
        return _exchangeRates;
    }

    @JsonProperty("exchangeRates")
    public void setExchangeRates(Hashtable<String, Hashtable<String, String>> _exchangeRates) {
        this._exchangeRates = _exchangeRates;
    }
}
