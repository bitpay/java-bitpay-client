package com.bitpay.sdk.model.Invoice;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    private String _closeURL = "";
    private String _orderId = "";
    private String _itemDesc = "";
    private String _itemCode = "";
    private boolean _physical = false;
    private List<String> _paymentCurrencies;
    private long _acceptanceWindow;
    private Buyer _buyer;
    private String _buyerSms;
    private String _merchantName;
    private String _selectedTransactionCurrency;
    private String _forcedBuyerSelectedWallet;
    private InvoiceTransactionDetails _transactionDetails;
    private InvoiceUniversalCodes _universalCodes;
    private List<InvoiceItemizedDetails> _itemizedDetails;
    private boolean _autoRedirect = false;

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
    private String _forcedBuyerSelectedTransactionCurrency;
    private BigDecimal _amountPaid;
    private BigDecimal _displayAmountPaid;
    private Hashtable<String, Hashtable<String, String>> _exchangeRates;
    private boolean _isCancelled = false;
    private boolean _bitpayIdRequired = false;
    private Hashtable<String, String> _paymentSubtotals;
    private Hashtable<String, String> _paymentTotals;
    private Hashtable<String, String> _paymentDisplayTotals;
    private Hashtable<String, String> _paymentDisplaySubTotals;
    private boolean _nonPayProPaymentReceived;
    private boolean _jsonPayProRequired = false;
    private BigDecimal _underpaidAmount;
    private BigDecimal _overpaidAmount;
    private Hashtable<String, Hashtable<String, String>> _paymentCodes;
    
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
            throw new BitPayException(null, "Error: currency code must be a type of Model.Currency");

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

    @JsonProperty("closeURL")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCloseURL() {
        return _closeURL;
    }

    @JsonProperty("closeURL")
    public void setCloseURL(String _closeURL) {
        this._closeURL = _closeURL;
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

    @JsonProperty("merchantName")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getMerchantName() {
        return _merchantName;
    }

    @JsonProperty("merchantName")
    public void setMerchantName(String _merchantName) {
        this._merchantName = _merchantName;
    }

    @JsonProperty("selectedTransactionCurrency")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getSelectedTransactionCurrency() {
        return _selectedTransactionCurrency;
    }

    @JsonProperty("selectedTransactionCurrency")
    public void setSelectedTransactionCurrency(String _selectedTransactionCurrency) throws BitPayException {
        if (!Currency.isValid(_selectedTransactionCurrency))
            throw new BitPayException(null, "Error: selectedTransactionCurrency code must be a type of Model.Currency");

        this._selectedTransactionCurrency = _currency;
    }

    @JsonProperty("forcedBuyerSelectedWallet")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getForcedBuyerSelectedWallet() {
        return _forcedBuyerSelectedWallet;
    }

    @JsonProperty("forcedBuyerSelectedWallet")
    public void setForcedBuyerSelectedWallet(String _forcedBuyerSelectedWallet) {
        this._forcedBuyerSelectedWallet = _forcedBuyerSelectedWallet;
    }

    @JsonProperty("transactionDetails")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public InvoiceTransactionDetails getTransactionDetails() {
        return _transactionDetails;
    }

    @JsonProperty("transactionDetails")
    public void setTransactionDetails(InvoiceTransactionDetails _transactionDetails) {
        this._transactionDetails = _transactionDetails;
    }

    @JsonProperty("universalCodes")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public InvoiceUniversalCodes getUniversalCodes() {
        return _universalCodes;
    }

    @JsonProperty("universalCodes")
    public void setUniversalCodes(InvoiceUniversalCodes _universalCodes) {
        this._universalCodes = _universalCodes;
    }

    @JsonProperty("itemizedDetails")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<InvoiceItemizedDetails> getItemizedDetails() {
        return _itemizedDetails;
    }

    @JsonProperty("itemizedDetails")
    public void setItemizedDetails(List<InvoiceItemizedDetails> _itemizedDetails) {
        this._itemizedDetails = _itemizedDetails;
    }

    @JsonIgnore
    public boolean getAutoRedirect() {
        return _autoRedirect;
    }

    @JsonProperty("autoRedirect")
    public void setAutoRedirect(boolean _autoRedirect) {
        this._autoRedirect = _autoRedirect;
    }

    @JsonProperty("bitpayIdRequired")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean getBitpayIdRequired() {
        return _bitpayIdRequired;
    }

    @JsonProperty("bitpayIdRequired")
    public void setBitpayIdRequired(boolean _bitpayIdRequired) {
        this._bitpayIdRequired = _bitpayIdRequired;
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

    @JsonProperty("buyerSms")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getBuyerSms() {
        return _buyerSms;
    }

    @JsonProperty("buyerSms")
    public void setBuyerSms(String _buyerSms) {
        this._buyerSms = _buyerSms;
    }

    // Response fields
    //
    
    @JsonIgnore
    public String getForcedBuyerSelectedTransactionCurrency() {
        return _forcedBuyerSelectedTransactionCurrency;
    }

    @JsonProperty("forcedBuyerSelectedTransactionCurrency")
    public void setForcedBuyerSelectedTransactionCurrency(String _forcedBuyerSelectedTransactionCurrency) {
        this._forcedBuyerSelectedTransactionCurrency = _forcedBuyerSelectedTransactionCurrency;
    }

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
    public boolean getLowFeeDetected() {
        return _lowFeeDetected;
    }

    @JsonProperty("lowFeeDetected")
    public void setLowFeeDetected(boolean _lowFeeDetected) {
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

    @JsonProperty("buyerProvidedInfo")
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
    public BigDecimal getDisplayAmountPaid() {
        return _displayAmountPaid;
    }

    @JsonProperty("displayAmountPaid")
    public void setDisplayAmountPaid(BigDecimal _displayAmountPaid) {
        this._displayAmountPaid = _displayAmountPaid;
    }

    @JsonIgnore
    public Hashtable<String, Hashtable<String, String>> getExchangeRates() {
        return _exchangeRates;
    }

    @JsonProperty("exchangeRates")
    public void setExchangeRates(Hashtable<String, Hashtable<String, String>> _exchangeRates) {
        this._exchangeRates = _exchangeRates;
    }

    @JsonIgnore
    public boolean getIsCancelled() {
        return _isCancelled;
    }

    @JsonProperty("isCancelled")
    public void setIsCancelled(boolean _isCancelled) {
        this._isCancelled = _isCancelled;
    }

    @JsonIgnore
    public Hashtable<String, String> getPaymentSubTotals() {
        return _paymentSubtotals;
    }

    @JsonProperty("paymentSubtotals")
    public void setPaymentSubTotals(Hashtable<String, String> _paymentSubtotals) {
        this._paymentSubtotals = _paymentSubtotals;
    }

    @JsonIgnore
    public Hashtable<String, String> getPaymentTotals() {
        return _paymentTotals;
    }

    @JsonProperty("paymentTotals")
    public void setPaymentTotals(Hashtable<String, String> _paymentTotals) {
        this._paymentTotals = _paymentTotals;
    }

    @JsonIgnore
    public Hashtable<String, String> getPaymentDisplayTotals() {
        return _paymentDisplayTotals;
    }

    @JsonProperty("paymentDisplayTotals")
    public void setPaymentDisplayTotals(Hashtable<String, String> _paymentDisplayTotals) {
        this._paymentDisplayTotals = _paymentDisplayTotals;
    }

    @JsonIgnore
    public Hashtable<String, String> getPaymentDisplaySubTotals() {
        return _paymentDisplaySubTotals;
    }

    @JsonProperty("paymentDisplaySubTotals")
    public void setPaymentDisplaySubTotals(Hashtable<String, String> _paymentDisplaySubTotals) {
        this._paymentDisplaySubTotals = _paymentDisplaySubTotals;
    }

    @JsonIgnore
    public boolean getNonPayProPaymentReceived() {
        return _nonPayProPaymentReceived;
    }

    @JsonProperty("nonPayProPaymentReceived")
    public void setNonPayProPaymentReceived(boolean _nonPayProPaymentReceived) {
        this._nonPayProPaymentReceived = _nonPayProPaymentReceived;
    }

    @JsonIgnore
    public boolean getJsonPayProRequired() {
        return _jsonPayProRequired;
    }

    @JsonProperty("jsonPayProRequired")
    public void setJsonPayProRequired(boolean _jsonPayProRequired) {
        this._jsonPayProRequired = _jsonPayProRequired;
    }

    @JsonIgnore
    public BigDecimal getUnderPaidAmount() {
        return _underpaidAmount;
    }

    @JsonProperty("underpaidAmount")
    public void setUnderPaidAmount(BigDecimal _underpaidAmount) {
        this._underpaidAmount = _underpaidAmount;
    }
    
    @JsonIgnore
    public BigDecimal getOverPaidAmount() {
        return _overpaidAmount;
    }

    @JsonProperty("overpaidAmount")
    public void setOverPaidAmount(BigDecimal _overpaidAmount) {
        this._overpaidAmount = _overpaidAmount;
    }

    @JsonIgnore
    public Hashtable<String, Hashtable<String, String>> getPaymentCodes() {
        return _paymentCodes;
    }

    @JsonProperty("paymentCodes")
    public void setPaymentCodes(Hashtable<String, Hashtable<String, String>> _paymentCodes) {
        this._paymentCodes = _paymentCodes;
    }    
}