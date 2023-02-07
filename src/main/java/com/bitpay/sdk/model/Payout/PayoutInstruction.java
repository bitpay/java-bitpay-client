package com.bitpay.sdk.model.Payout;

import com.bitpay.sdk.exceptions.PayoutBatchCreationException;
import com.bitpay.sdk.util.PayoutInstructionBtcSummaryDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * The type Payout instruction.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayoutInstruction {
    private Double amount;
    private String email;
    private String recipientId;
    private String shopperId;
    private String label;
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

    /**
     * Gets the payout amount in the requested currency. The minimum amount per instruction is $5 USD equivalent.
     *
     * @return the amount
     */
    @JsonProperty("amount")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Double getAmount() {
        return this.amount;
    }

    /**
     * Sets the payout amount in the requested currency. The minimum amount per instruction is $5 USD equivalent.
     *
     * @param amount the amount
     */
    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * Gets email address of an active recipient.
     * Note: In the future, BitPay may allow Recipients to update the email address tied to their personal account.
     * BitPay encourages the use of recipientId or shopperId when programatically creating payouts.
     *
     * @return the email
     */
    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets email address of an active recipient.
     * Note: In the future, BitPay may allow Recipients to update the email address tied to their personal account.
     * BitPay encourages the use of recipientId or shopperId when programatically creating payouts.
     *
     * @param email the email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets Bitpay recipient id of an active recipient,
     * assigned by BitPay for a given recipient email during the onboarding process (see Recipients below).
     *
     * @return the recipient id
     * @see <a href="https://bitpay.com/api/#rest-api-resources-recipients">Recipients</a>
     */
    @JsonProperty("recipientId")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getRecipientId() {
        return this.recipientId;
    }

    /**
     * Sets Bitpay recipient id of an active recipient,
     * assigned by BitPay for a given recipient email during the onboarding process (see Recipients below).
     *
     * @param recipientId the recipient id
     * @see <a href="https://bitpay.com/api/#rest-api-resources-recipients">Recipients</a>
     */
    @JsonProperty("recipientId")
    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    /**
     * Gets shopper id.
     * <p>
     *  This is the unique id assigned by BitPay if the shopper used their personal BitPay account to authenticate
     *   pay an invoice. For customers signing up for a brand new BitPay personal account, this id will only
     *  be created as part of the payout onboarding.
     * </p>
     * <p>
     *  The same field would also be available on paid invoices if the customer signed in with their
     *  BitPay personal account before completing the payment.
     *  This can allow merchants to monitor the activity of a customer (deposits and payouts).
     * </p>
     *
     * @return the shopper id
     */
    @JsonProperty("shopperId")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getShopperId() {
        return this.shopperId;
    }

    /**
     * Sets shopper id.
     * <p>
     *  This is the unique id assigned by BitPay if the shopper used their personal BitPay account to authenticate
     *   pay an invoice. For customers signing up for a brand new BitPay personal account, this id will only
     *  be created as part of the payout onboarding.
     * </p>
     * <p>
     *  The same field would also be available on paid invoices if the customer signed in with their
     *  BitPay personal account before completing the payment.
     *  This can allow merchants to monitor the activity of a customer (deposits and payouts).
     * </p>
     *
     * @param shopperId the shopper id
     */
    @JsonProperty("shopperId")
    public void setShopperId(String shopperId) {
        this.shopperId = shopperId;
    }

    /**
     * Gets label.
     * For merchant use, pass through - can be the customer name or unique payout reference assigned by the merchant
     * to the recipient.
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
     * For merchant use, pass through - can be the customer name or unique payout reference assigned by the merchant
     * to the recipient.
     *
     * @param label the label
     */
    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets wallet provider.
     *
     * @return the wallet provider
     */
    @JsonProperty("walletProvider")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getWalletProvider() {
        return this.walletProvider;
    }

    /**
     * Sets wallet provider.
     *
     * @param walletProvider the wallet provider
     */
    @JsonProperty("walletProvider")
    public void setWalletProvider(String walletProvider) {
        this.walletProvider = walletProvider;
    }

    // Response fields
    //

    /**
     * Gets id.
     *
     * @return the id
     */
    @JsonIgnore
    public String getId() {
        return this.id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets btc.
     *
     * @return the btc
     */
    @JsonIgnore
    public PayoutInstructionBtcSummary getBtc() {
        return this.btc;
    }

    /**
     * Sets btc.
     *
     * @param btc the btc
     */
    @JsonProperty("btc")
    @JsonDeserialize(using = PayoutInstructionBtcSummaryDeserializer.class)
    public void setBtc(PayoutInstructionBtcSummary btc) {
        this.btc = btc;
    }

    /**
     * Gets the cryptocurrency transactions details for the executed payout.
     *
     * @return the transactions
     */
    @JsonIgnore
    public List<PayoutInstructionTransaction> getTransactions() {
        return this.transactions;
    }

    /**
     * Sets the cryptocurrency transactions details for the executed payout.
     *
     * @param transactions the transactions
     */
    @JsonProperty("transactions")
    public void setTransactions(List<PayoutInstructionTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Gets status, the possible values are:
     * <ul>
     *     <li>new - initial status when the payout batch is created</li>
     *     <li>funded - if there are enough funds available on the merchant account,
     *      the payout batches are set to funded. This happens at the daily cutoff time for payout processing,
     *      e.g. 2pm and 9pm UTC
     *     </li>
     *     <li>processing - the payout batches switch to this status whenever the corresponding cryptocurrency
     *      transactions are broadcasted by BitPay
     *     </li>
     *     <li>complete - the payout batches are marked as complete when the cryptocurrency transaction has reached
     *      the typical target confirmation for the corresponding asset. For instance,
     *      6 confirmations for a bitcoin transaction.
     *     </li>
     *     <li>cancelled - when the merchant cancels a payout batch (only possible for requests in the status new</li>
     * </ul>
     *
     * @return the status
     */
    @JsonIgnore
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets status, the possible values are:
     * <ul>
     *     <li>new - initial status when the payout batch is created</li>
     *     <li>funded - if there are enough funds available on the merchant account,
     *      the payout batches are set to funded. This happens at the daily cutoff time for payout processing,
     *      e.g. 2pm and 9pm UTC
     *     </li>
     *     <li>processing - the payout batches switch to this status whenever the corresponding cryptocurrency
     *      transactions are broadcasted by BitPay
     *     </li>
     *     <li>complete - the payout batches are marked as complete when the cryptocurrency transaction has reached
     *      the typical target confirmation for the corresponding asset. For instance,
     *      6 confirmations for a bitcoin transaction.
     *     </li>
     *     <li>cancelled - when the merchant cancels a payout batch (only possible for requests in the status new</li>
     * </ul>
     *
     * @param status the status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    @JsonProperty("address")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getAddress() {
        return this.address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }
}
