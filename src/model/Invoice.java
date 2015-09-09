package model;

import java.util.Hashtable;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

import controller.BitPayException;

@JsonIgnoreProperties(ignoreUnknown=true) 	
public class Invoice {

	public static final String STATUS_NEW = "new";
	public static final String STATUS_PAID = "paid";
	public static final String STATUS_CONFIRMED = "confirmed";
	public static final String STATUS_COMPLETE = "complete";
	public static final String STATUS_INVALID = "invalid";
	public static final String EXSTATUS_FALSE = "false";
	public static final String EXSTATUS_PAID_OVER = "paidOver";
	public static final String EXSTATUS_PAID_PARTIAL = "paidPartial";
		
	private Long _nonce = 0L;
	private String _guid = "";
	private String _token = "";
	
	private Double _price;
	private String _currency;
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
	private InvoiceBuyer _buyer;
	
	private String _id;
	private String _url;
	private String _status;
	private String _btcPrice;
	private String _invoiceTime;
	private long _expirationTime;
	private long _currentTime;
	private String _btcPaid;
	private String _btcDue;
	private List<InvoiceTransaction> _transactions;
	private String _rate;
	private Hashtable<String, String> _exRates;
	private String _exceptionStatus;
	private InvoicePaymentUrls _paymentUrls;
	
    public Invoice() {}

    public Invoice(Double _price, String _currency)
    {
        this._price = _price;
        this._currency = _currency;
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

    @JsonProperty("nonce")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public Long getNonce() {
		return _nonce;
	}
	
    @JsonProperty("nonce")
	public void setNonce(Long _nonce) {
		this._nonce = _nonce;
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
        if (_currency.length() != 3)
        {
            throw new BitPayException("Error: currency code must be exactly three characters");
        }
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

    @JsonProperty("buyer")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  	public InvoiceBuyer getBuyer() {
  		return _buyer;
  	}
  	
    @JsonProperty("buyer")
  	public void setBuyer(InvoiceBuyer _buyer) {
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
	public String getBtcPrice() {
		return _btcPrice;
	}
	
    @JsonProperty("btcPrice")
	public void setBtcPrice(String _btcPrice) {
		this._btcPrice = _btcPrice;
	}

    @JsonIgnore
	public String getInvoiceTime() {
		return _invoiceTime;
	}
	
    @JsonProperty("invoiceTime")
	public void setInvoiceTime(String _invoiceTime) {
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
	public String getBtcPaid() {
		return _btcPaid;
	}
	
    @JsonProperty("btcPaid")
	public void setBtcPaid(String _btcPaid) {
		this._btcPaid = _btcPaid;
	}

    @JsonIgnore
	public String getBtcDue() {
		return _btcDue;
	}
	
    @JsonProperty("btcDue")
	public void setBtcDue(String _btcDue) {
		this._btcDue = _btcDue;
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
	public String getRate() {
		return _rate;
	}
	
    @JsonProperty("rate")
	public void setRate(String _rate) {
		this._rate = _rate;
	}

    @JsonIgnore
	public Hashtable<String, String> getExRates() {
		return _exRates;
	}
	
    @JsonProperty("exRates")
	public void setExRates(Hashtable<String, String> _exRates) {
		this._exRates = _exRates;
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
  	public InvoicePaymentUrls getPaymentUrls() {
  		return _paymentUrls;
  	}
  	
    @JsonProperty("paymentUrls")
  	public void setPaymentUrls(InvoicePaymentUrls _paymentUrls) {
  		this._paymentUrls = _paymentUrls;
  	}
}
