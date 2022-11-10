package com.bitpay.sdk.model.Payout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutRecipient {
    private String email = "";
    private String guid = "";
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
    
    @JsonProperty("guid")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getGuid() {
        return this.guid;
    }

    @JsonProperty("guid")
    public void setGuid(String guid) {
        this.guid = guid;
    }

    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getEmail() {
        return this.email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("token")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getToken() {
    	return this.token;
    }
    
    @JsonProperty("token")
    public void setToken(String token) {
    	this.token = token;
    }
    
    // Optional fields
    //

    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getLabel() {
        return this.label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }
    

    @JsonProperty("notificationURL")
    public String getNotificationURL() {
        return this.notificationURL;
    }

    @JsonProperty("notificationURL")
    public void setNotificationURL(String notificationURL) {
        this.notificationURL = notificationURL;
    }
    
    @JsonProperty("reference")
    public String getReference() {
        return this.reference;
    }

    @JsonProperty("reference")
    public void setReference(String reference) {
        this.reference = reference;
    }

    // Response fields
    //

    @JsonIgnore
    public String getStatus() {
        return this.status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonIgnore
    public String getId() {
        return this.id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getShopperId() {
        return this.shopperId;
    }

    @JsonProperty("shopperId")
    public void setShopperId(String shopperId) {
        this.shopperId = shopperId;
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
    public String getSupportPhone() {
        return this.supportPhone;
    }

    @JsonProperty("supportPhone")
    public void setSupportPhone(String supportPhone) {
        this.supportPhone = supportPhone;
    }
}
