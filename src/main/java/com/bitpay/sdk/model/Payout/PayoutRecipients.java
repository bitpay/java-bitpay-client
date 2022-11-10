package com.bitpay.sdk.model.Payout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class PayoutRecipients {
    private String guid;
    private List<PayoutRecipient> recipients = Collections.emptyList();
    private String token = "";

    /**
     * Constructor, create an recipient-full request PayoutBatch object.
     *
     * @param recipients array array of JSON objects, with containing the following parameters.
     */
    public PayoutRecipients(List<PayoutRecipient> recipients) {
        this.recipients = recipients;
    }

    // API fields
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

    @JsonProperty("recipients")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<PayoutRecipient> getInstructions() {
        return this.recipients;
    }

    @JsonProperty("recipients")
    public void setInstructions(List<PayoutRecipient> recipients) {
        this.recipients = recipients;
    }
}
