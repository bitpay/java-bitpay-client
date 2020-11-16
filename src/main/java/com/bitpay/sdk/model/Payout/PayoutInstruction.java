package com.bitpay.sdk.model.Payout;

import com.bitpay.sdk.exceptions.PayoutCreationException;
import com.bitpay.sdk.util.PayoutInstructionBtcSummaryDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutInstruction {
    private Double _amount;
    private String _email;
    private String _recipientId;
    private String _shopperId;
    private String _label = "";
    private String _walletProvider;
    private String _id;

    private PayoutInstructionBtcSummary _btc;
    private List<PayoutInstructionTransaction> _transactions;
    private String _status;

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
     * @throws PayoutCreationException BitPayException class
     */
    public PayoutInstruction(Double amount, int method, String methodValue) throws PayoutCreationException {
        this._amount = amount;
        switch (method) {
            case RecipientReferenceMethod.EMAIL:
                this._email = methodValue;
                break;
            case RecipientReferenceMethod.RECIPIENT_ID:
                this._recipientId = methodValue;
                break;
            case RecipientReferenceMethod.SHOPPER_ID:
                this._shopperId = methodValue;
                break;
            default:
                throw new PayoutCreationException("method code must be a type of RecipientReferenceMethod");
        }
    }

    @JsonProperty("amount")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Double getAmount() {
        return _amount;
    }

    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this._amount = amount;
    }

    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getEmail() {
        return _email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this._email = email;
    }

    @JsonProperty("recipientId")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getRecipientId() {
        return _recipientId;
    }

    @JsonProperty("recipientId")
    public void setRecipientId(String recipientId) {
        this._recipientId = recipientId;
    }

    @JsonProperty("shopperId")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getShopperId() {
        return _shopperId;
    }

    @JsonProperty("shopperId")
    public void setShopperId(String shopperId) {
        this._shopperId = shopperId;
    }

    @JsonProperty("label")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getLabel() {
        return _label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this._label = label;
    }

    @JsonProperty("walletProvider")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getWalletProvider() {
        return _walletProvider;
    }

    @JsonProperty("walletProvider")
    public void setWalletProvider(String walletProvider) {
        this._walletProvider = walletProvider;
    }

    // Response fields
    //

    @JsonIgnore
    public String getId() {
        return _id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this._id = id;
    }

    @JsonIgnore
    public PayoutInstructionBtcSummary getBtc() {
        return _btc;
    }

    @JsonProperty("btc")
    @JsonDeserialize(using = PayoutInstructionBtcSummaryDeserializer.class)
    public void setBtc(PayoutInstructionBtcSummary btc) {
        this._btc = btc;
    }

    @JsonIgnore
    public List<PayoutInstructionTransaction> getTransactions() {
        return _transactions;
    }

    @JsonProperty("transactions")
    public void setTransactions(List<PayoutInstructionTransaction> transactions) {
        this._transactions = transactions;
    }

    @JsonIgnore
    public String getStatus() {
        return _status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this._status = status;
    }
}
