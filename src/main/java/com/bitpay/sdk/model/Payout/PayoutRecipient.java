package com.bitpay.sdk.model.Payout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Payout recipient.
 * <p>
 * To create payouts, merchants will first need to issue email invites using this resource.
 * This is a mandatory step to onboard customers asking for cryptocurrency payouts.
 * </p>
 * <p>
 * The recipients of the email invites will be invited to create a BitPay personal account,
 * submit a photo of a proof of ID document (Passport, driver's license, Identity card) and
 * provide the home address in order to be able to submit a cryptocurrency withdrawal address to be used for the payouts.
 * </p>
 * <p>
 * Info. The BitPay personal account is NOT a cryptocurrency wallet,
 * it is a personal account which allows customers to receive cryptocurrency payouts from merchants,
 * make a larger purchase or request a refund,
 * but also apply for additional services like the BitPay Prepaid Mastercard card.
 * </p>
 * <p>
 * The Recipients resource allows a merchant to invite clients to sign up for a BitPay personal account.
 * </p>
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-recipients">REST API Recipients</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutRecipient {
    private String _email = "";
    private String _guid = "";
    private String _label = "";
    private String _reference = "";
    private String _notificationURL = "";

    private String _account;
    private String _status;
    private String _id;
    private String _shopperId;
    private String _token;
    private String _supportPhone;


    /**
     * Constructor, create a minimal Recipient object.
     *
     * @param email           string Recipient email address to which the invite shall be sent.
     * @param label           string Recipient nickname assigned by the merchant (Optional).
     * @param notificationURL string URL to which BitPay sends webhook notifications to inform the merchant about the                        status of a given recipient. HTTPS is mandatory (Optional).
     */
    public PayoutRecipient(String email, String label, String notificationURL) {
        this._email = email;
        this._label = label;
        this._notificationURL = notificationURL;
    }

    /**
     * Constructor, create a minimal Recipient object.
     */
    public PayoutRecipient() {
    }

    // Required fields
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
     * Gets Recipient email address.
     *
     * @return the email
     */
    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getEmail() {
        return _email;
    }

    /**
     * Sets Recipient email address.
     *
     * @param email the email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this._email = email;
    }

    /**
     * Gets resource token.
     * This token is derived from the API token initially used to create the recipient and is tied
     * to the specific resource id created.
     *
     * @return the token
     */
    @JsonProperty("token")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getToken() {
    	return _token;
    }

    /**
     * Sets resource token.
     * This token is derived from the API token initially used to create the recipient and is tied
     * to the specific resource id created.
     *
     * @param token the token
     */
    @JsonProperty("token")
    public void setToken(String token) {
    	this._token = token;
    }
    
    // Optional fields
    //

    /**
     * Gets label.
     * For merchant use, pass through - could be customer name or unique reference.
     *
     * @return the label
     */
    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getLabel() {
        return _label;
    }

    /**
     * Sets label.
     * For merchant use, pass through - could be customer name or unique reference.
     *
     * @param label the label
     */
    @JsonProperty("label")
    public void setLabel(String label) {
        this._label = label;
    }

    /**
     * Gets notification url.
     * URL to which BitPay sends webhook notifications. If a notificationURL is not passed during payout creation,
     * the payout will default to using the merchant account level payout IPN if populated.
     *
     * @return the notification url
     */
    @JsonProperty("notificationURL")
    public String getNotificationURL() {
        return _notificationURL;
    }

    /**
     * Sets notification url.
     * URL to which BitPay sends webhook notifications. If a notificationURL is not passed during payout creation,
     * the payout will default to using the merchant account level payout IPN if populated.
     *
     * @param notificationURL the notification url
     */
    @JsonProperty("notificationURL")
    public void setNotificationURL(String notificationURL) {
        this._notificationURL = notificationURL;
    }

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

    // Response fields
    //

    /**
     * Gets recipient status. Can have the following values:
     * <ul>
     *     <li>invited</li>
     *     <li>unverified</li>
     *     <li>verified</li>
     *     <li>active</li>
     *     <li>paused</li>
     *     <li>removed</li>
     * </ul>
     * <p>
     * See also {@link com.bitpay.sdk.model.Payout.RecipientStatus}
     *
     * @return the status
     */
    @JsonIgnore
    public String getStatus() {
        return _status;
    }

    /**
     * Sets recipient status. Can have the following values:
     * <ul>
     *     <li>invited</li>
     *     <li>unverified</li>
     *     <li>verified</li>
     *     <li>active</li>
     *     <li>paused</li>
     *     <li>removed</li>
     * </ul>
     * <p>
     * See also {@link com.bitpay.sdk.model.Payout.RecipientStatus}
     *
     * @param status the status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this._status = status;
    }

    /**
     * Gets unique recipient id assigned by BitPay for a given customer email.
     *
     * @return the id
     */
    @JsonIgnore
    public String getId() {
        return _id;
    }

    /**
     * Sets unique recipient id assigned by BitPay for a given customer email.
     *
     * @param id the id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this._id = id;
    }

    /**
     * Gets unique id assigned by BitPay if the shopper used his personal BitPay account to authenticate and pay an invoice.
     * For customers signing up for a brand new BitPay personal account,
     * this id will only be created as part of the payout onboarding.
     * <p>
     * The same field would also be available on paid invoices if the customer signed in with his
     * BitPay personal account before completing the payment.
     * This can allow merchants to monitor the activity of a customer (deposits and payouts).
     * </p>
     *
     * @return the shopper id
     */
    @JsonIgnore
    public String getShopperId() {
        return _shopperId;
    }

    /**
     * Sets unique id assigned by BitPay if the shopper used his personal BitPay account to authenticate and pay an invoice.
     * For customers signing up for a brand new BitPay personal account,
     * this id will only be created as part of the payout onboarding.
     * <p>
     * The same field would also be available on paid invoices if the customer signed in with his
     * BitPay personal account before completing the payment.
     * This can allow merchants to monitor the activity of a customer (deposits and payouts).
     * </p>
     *
     * @param shopperId the shopper id
     */
    @JsonProperty("shopperId")
    public void setShopperId(String shopperId) {
        this._shopperId = shopperId;
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
}
