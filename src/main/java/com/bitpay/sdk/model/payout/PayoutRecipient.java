/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.payout;

import com.bitpay.sdk.model.ModelConfiguration;
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
 * provide the home address in order to be able to submit a cryptocurrency withdrawal address
 * to be used for the payouts.
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
 * @see <a href="https://bitpay.readme.io/reference/recipients">REST API Recipients</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutRecipient {
    protected String email = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String guid;
    protected String label = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String notificationUrl = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String status = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String id = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String shopperId = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String token = ModelConfiguration.DEFAULT_NON_SENT_VALUE;

    /**
     * Constructor, create a minimal Recipient object.
     *
     * @param email           string Recipient email address to which the invite shall be sent.
     * @param label           string Recipient nickname assigned by the merchant (Optional).
     * @param notificationUrl string URL to which BitPay sends webhook notifications to inform the merchant about the
     *                        status of a given recipient. HTTPS is mandatory (Optional).
     */
    public PayoutRecipient(
        String email,
        String label,
        String notificationUrl
    ) {
        this.email = email;
        this.label = label;
        this.notificationUrl = notificationUrl;
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getNotificationUrl() {
        return this.notificationUrl;
    }

    /**
     * Sets notification url.
     * URL to which BitPay sends webhook notifications. If a notificationURL is not passed during payout creation,
     * the payout will default to using the merchant account level payout IPN if populated.
     *
     * @param notificationUrl the notification url
     */
    @JsonProperty("notificationURL")
    public void setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
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
     * See also {@link com.bitpay.sdk.model.payout.RecipientStatus}
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
     * See also {@link com.bitpay.sdk.model.payout.RecipientStatus}
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
     * Gets unique id assigned by BitPay if the shopper used his personal BitPay account to authenticate
     * and pay an invoice.
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
     * Sets unique id assigned by BitPay if the shopper used his personal BitPay account to authenticate
     * and pay an invoice.
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
}
