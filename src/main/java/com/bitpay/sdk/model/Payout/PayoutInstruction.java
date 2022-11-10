package com.bitpay.sdk.model.Payout;

import com.bitpay.sdk.exceptions.PayoutBatchCreationException;
import com.bitpay.sdk.util.PayoutInstructionBtcSummaryDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutInstruction {
    private Double amount;
    private String email;
    private String recipientId;
    private String shopperId;
    private String label = "";
    private String walletProvider;
    private String id;

    private PayoutInstructionBtcSummary btc;
    private List<PayoutInstructionTransaction> transactions;
    private String status;
    private String address;

    /**
     * Constructor, create an empty PayoutInstruction object.
     */
    public PayoutInstruction() {
    }

    /**
     * Constructor, create a PayoutInstruction object.
     *
     * @param amount      float amount (in currency of batch).
     * @param method      int Method used to target the recipient.
     * @param methodValue string value for the choosen target method.
     * @throws PayoutBatchCreationException BitPayException class
     */
    public PayoutInstruction(Double amount, int method, String methodValue) throws PayoutBatchCreationException {
        this.amount = amount;
        switch (method) {
            case RecipientReferenceMethod.EMAIL:
                this.email = methodValue;
                break;
            case RecipientReferenceMethod.RECIPIENT_ID:
                this.recipientId = methodValue;
                break;
            case RecipientReferenceMethod.SHOPPER_ID:
                this.shopperId = methodValue;
                break;
            default:
                throw new PayoutBatchCreationException(null, "method code must be a type of RecipientReferenceMethod");
        }
    }

    @JsonProperty("amount")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Double getAmount() {
        return this.amount;
    }

    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this.amount = amount;
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

    @JsonProperty("recipientId")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getRecipientId() {
        return this.recipientId;
    }

    @JsonProperty("recipientId")
    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    @JsonProperty("shopperId")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getShopperId() {
        return this.shopperId;
    }

    @JsonProperty("shopperId")
    public void setShopperId(String shopperId) {
        this.shopperId = shopperId;
    }

    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getLabel() {
        return this.label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty("walletProvider")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getWalletProvider() {
        return this.walletProvider;
    }

    @JsonProperty("walletProvider")
    public void setWalletProvider(String walletProvider) {
        this.walletProvider = walletProvider;
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

    @JsonIgnore
    public PayoutInstructionBtcSummary getBtc() {
        return this.btc;
    }

    @JsonProperty("btc")
    @JsonDeserialize(using = PayoutInstructionBtcSummaryDeserializer.class)
    public void setBtc(PayoutInstructionBtcSummary btc) {
        this.btc = btc;
    }

    @JsonIgnore
    public List<PayoutInstructionTransaction> getTransactions() {
        return this.transactions;
    }

    @JsonProperty("transactions")
    public void setTransactions(List<PayoutInstructionTransaction> transactions) {
        this.transactions = transactions;
    }

    @JsonIgnore
    public String getStatus() {
        return this.status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("address")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getAddress() {
        return this.address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }
}
