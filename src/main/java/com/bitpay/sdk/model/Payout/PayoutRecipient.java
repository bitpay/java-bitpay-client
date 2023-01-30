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
    private String email = "";
    private String guid;
    private String label = "";
    private String reference = "";
    private String notificationURL = "";

    private String account;
    private String status;
    private String id;
    private String shopperId;
    private String token;
    private String supportPhone;


    /**
     * Constructor, create a minimal Recipient object.
     *
     * @param email           string Recipient email address to which the invite shall be sent.
     * @param label           string Recipient nickname assigned by the merchant (Optional).
     * @param notificationURL string URL to which BitPay sends webhook notifications to inform the merchant about the
     *                        status of a given recipient. HTTPS is mandatory (Optional).
     */
    public PayoutRecipient(String email, String label, String notificationURL) {
        this.email = email;
        this.label = label;
        this.notificationURL = notificationURL;
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
        return this.guid;
    }

    /**
     * Sets guid.
     *
     * @param guid the guid
     */
    @JsonProperty("guid")
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * Gets Recipient email address.
     *
     * @return the email
     */
    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets Recipient email address.
     *
     * @param email the email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
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
    	return this.token;
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
    	this.token = token;
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
        return this.label;
    }

    /**
     * Sets label.
     * For merchant use, pass through - could be customer name or unique reference.
     *
     * @param label the label
     */
    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
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
        return this.notificationURL;
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
        this.notificationURL = notificationURL;
    }

    /**
     * Gets reference.
     *
     * @return the reference
     */
    @JsonProperty("reference")
    public String getReference() {
        return this.reference;
    }

    /**
     * Sets reference.
     *
     * @param reference the reference
     */
    @JsonProperty("reference")
    public void setReference(String reference) {
        this.reference = reference;
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
        return this.status;
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
        this.status = status;
    }

    /**
     * Gets unique recipient id assigned by BitPay for a given customer email.
     *
     * @return the id
     */
    @JsonIgnore
    public String getId() {
        return this.id;
    }

    /**
     * Sets unique recipient id assigned by BitPay for a given customer email.
     *
     * @param id the id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
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
        return this.shopperId;
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
        this.shopperId = shopperId;
    }

    /**
     * Gets account.
     *
     * @return the account
     */
    @JsonIgnore
    public String getAccount() {
        return this.account;
    }

    /**
     * Sets account.
     *
     * @param account the account
     */
    @JsonProperty("account")
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * Gets support phone.
     *
     * @return the support phone
     */
    @JsonIgnore
    public String getSupportPhone() {
        return this.supportPhone;
    }

    /**
     * Sets support phone.
     *
     * @param supportPhone the support phone
     */
    @JsonProperty("supportPhone")
    public void setSupportPhone(String supportPhone) {
        this.supportPhone = supportPhone;
    }
}
