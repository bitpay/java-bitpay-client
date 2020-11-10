package com.bitpay.sdk.model.Payout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutRecipient {
    private String _email = "";
    private String _label = "";
    private String _notificationURL = "";

    private String _status;
    private String _id;
    private String _shopperId;
    private String _token;

    /**
     * Constructor, create a minimal Recipient object.
     *
     * @param email           string Recipient email address to which the invite shall be sent.
     * @param label           string Recipient nickname assigned by the merchant (Optional).
     * @param notificationURL string URL to which BitPay sends webhook notifications to inform the merchant about the
     *                        status of a given recipient. HTTPS is mandatory (Optional).
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

    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getGuid() {
        return _email;
    }

    @JsonProperty("email")
    public void setGuid(String email) {
        this._email = email;
    }

    // Optional fields
    //

    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getToken() {
        return _label;
    }

    @JsonProperty("label")
    public void setToken(String label) {
        this._label = label;
    }

    @JsonProperty("notificationURL")
    public String getReference() {
        return _notificationURL;
    }

    @JsonProperty("notificationURL")
    public void setReference(String notificationURL) {
        this._notificationURL = notificationURL;
    }

    // Response fields
    //

    @JsonIgnore
    public String getStatus() {
        return _status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this._status = status;
    }

    @JsonIgnore
    public String getId() {
        return _id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this._id = id;
    }

    @JsonIgnore
    public String getAccount() {
        return _shopperId;
    }

    @JsonProperty("Account")
    public void setAccount(String shopperId) {
        this._shopperId = shopperId;
    }

    @JsonIgnore
    public String getSupportPhone() {
        return _token;
    }

    @JsonProperty("token")
    public void setSupportPhone(String token) {
        this._token = token;
    }
}
