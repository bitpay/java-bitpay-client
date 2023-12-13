/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.settlement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * The type Settlement.
 *
 * @see <a href="https://bitpay.readme.io/reference/settlements">Settlements</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Settlement {

    private String id;
    private String accountId;
    private String currency;
    private PayoutInfo payoutInfo;
    private String status;
    private ZonedDateTime dateCreated;
    private ZonedDateTime dateExecuted;
    private ZonedDateTime dateCompleted;
    private ZonedDateTime openingDate;
    private ZonedDateTime closingDate;
    private Float openingBalance;
    private Float ledgerEntriesSum;
    private List<WithHoldings> withHoldings;
    private Float withHoldingsSum;
    private Float totalAmount;
    private List<SettlementLedgerEntry> ledgerEntries;
    private String token;

    /**
     * Instantiates a new Settlement.
     */
    public Settlement() {
    }

    /**
     * Gets id.
     * String identifying the settlement; this id will also be in the description of the corresponding bank settlement.
     *
     * @return the id
     */
    @JsonIgnore
    public String getId() {
        return this.id;
    }

    /**
     * Sets id.
     * String identifying the settlement; this id will also be in the description of the corresponding bank settlement.
     *
     * @param id the id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets account id.
     * String identifying the BitPay merchant. For internal use, this field can be ignored in merchant implementations.
     *
     * @return the account id
     */
    @JsonIgnore
    public String getAccountId() {
        return this.accountId;
    }

    /**
     * Sets account id.
     * String identifying the BitPay merchant. For internal use, this field can be ignored in merchant implementations.
     *
     * @param accountId the account id
     */
    @JsonProperty("accountId")
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * Gets currency. ISO 4217 3-character currency code. This is the currency associated with the settlement.
     *
     * @return the currency
     * @see <a href="https://bitpay.com/docs/settlement">Supported settlement currencies are listed</a>
     */
    @JsonIgnore
    public String getCurrency() {
        return this.currency;
    }

    /**
     * Sets currency. ISO 4217 3-character currency code. This is the currency associated with the settlement.
     *
     * @param currency the currency
     * @see <a href="https://bitpay.com/docs/settlement">Supported settlement currencies are listed</a>
     */
    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets payout info. Object containing the settlement info provided by the Merchant in his BitPay account settings.
     *
     * @return the payout info
     */
    @JsonIgnore
    public PayoutInfo getPayoutInfo() {
        return this.payoutInfo;
    }

    /**
     * Sets payout info. Object containing the settlement info provided by the Merchant in his BitPay account settings.
     *
     * @param payoutInfo the payout info
     */
    @JsonProperty("payoutInfo")
    public void setPayoutInfo(PayoutInfo payoutInfo) {
        this.payoutInfo = payoutInfo;
    }

    /**
     * Gets status of the settlement. Possible statuses are "new", "processing", "rejected" and "completed".
     *
     * @return the status
     */
    @JsonIgnore
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets status of the settlement. Possible statuses are "new", "processing", "rejected" and "completed".
     *
     * @param status the status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets date created.
     *
     * @return the date created
     */
    @JsonIgnore
    public ZonedDateTime getDateCreated() {
        return this.dateCreated;
    }

    /**
     * Sets date created.
     *
     * @param dateCreated the date created
     */
    @JsonProperty("dateCreated")
    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Gets timestamp when the settlement was executed. UTC date, ISO-8601 format yyyy-mm-ddThh:mm:ssZ
     *
     * @return the date executed
     */
    @JsonIgnore
    public ZonedDateTime getDateExecuted() {
        return this.dateExecuted;
    }

    /**
     * Sets timestamp when the settlement was executed. UTC date, ISO-8601 format yyyy-mm-ddThh:mm:ssZ
     *
     * @param dateExecuted the date executed
     */
    @JsonProperty("dateExecuted")
    public void setDateExecuted(ZonedDateTime dateExecuted) {
        this.dateExecuted = dateExecuted;
    }

    /**
     * Gets date completed.
     *
     * @return the date completed
     */
    @JsonIgnore
    public ZonedDateTime getDateCompleted() {
        return this.dateCompleted;
    }

    /**
     * Sets date completed.
     *
     * @param dateCompleted the date completed
     */
    @JsonProperty("dateCompleted")
    public void setDateCompleted(ZonedDateTime dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    /**
     * Gets opening date. Corresponds to the closingDate of the previous settlement executed.
     * For the first settlement of an account the value will be the BitPay merchant account creation date.
     * UTC date, ISO-8601 format yyyy-mm-ddThh:mm:ssZ
     *
     * @return the opening date
     */
    @JsonIgnore
    public ZonedDateTime getOpeningDate() {
        return this.openingDate;
    }

    /**
     * Sets opening date. Corresponds to the closingDate of the previous settlement executed.
     * For the first settlement of an account the value will be the BitPay merchant account creation date.
     * UTC date, ISO-8601 format yyyy-mm-ddThh:mm:ssZ
     *
     * @param openingDate the opening date
     */
    @JsonProperty("openingDate")
    public void setOpeningDate(ZonedDateTime openingDate) {
        this.openingDate = openingDate;
    }

    /**
     * Gets date and time for last ledger entry used for the settlement. UTC date, ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @return the closing date
     */
    @JsonIgnore
    public ZonedDateTime getClosingDate() {
        return this.closingDate;
    }

    /**
     * Sets date and time for last ledger entry used for the settlement. UTC date, ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @param closingDate the closing date
     */
    @JsonProperty("closingDate")
    public void setClosingDate(ZonedDateTime closingDate) {
        this.closingDate = closingDate;
    }

    /**
     * Gets balance of the ledger at the openingDate.
     *
     * @return the opening balance
     */
    @JsonIgnore
    public Float getOpeningBalance() {
        return this.openingBalance;
    }

    /**
     * Sets balance of the ledger at the openingDate.
     *
     * @param openingBalance the opening balance
     */
    @JsonProperty("openingBalance")
    public void setOpeningBalance(Float openingBalance) {
        this.openingBalance = openingBalance;
    }

    /**
     * Gets sum of all ledger entries in the settlement,
     * this means all the debits and credits which happened between openingDate and closingDate.
     *
     * @return the ledger entries sum
     */
    @JsonIgnore
    public Float getLedgerEntriesSum() {
        return this.ledgerEntriesSum;
    }

    /**
     * Sets sum of all ledger entries in the settlement,
     * this means all the debits and credits which happened between openingDate and closingDate.
     *
     * @param ledgerEntriesSum the ledger entries sum
     */
    @JsonProperty("ledgerEntriesSum")
    public void setLedgerEntriesSum(Float ledgerEntriesSum) {
        this.ledgerEntriesSum = ledgerEntriesSum;
    }

    /**
     * Gets array of With Holdings.
     * With Holdings are kept on the ledger to be used later and thus withheld from this settlement.
     * Each withholding is a JSON object containing a code, amount and description field.
     * The possible withholding codes are:
     * <ul>
     *     <li>W001 - Refund Reserve: a merchant can set a refund reserve for his account.
     *     This code indicate the current amount in the reserve at the moment the settlement is generated.</li>
     *     <li>W002 - Settlement Fee: in case BitPay is charging the bank fees to the merchant,
     *     via a SWIFT wire for instance, will be posted to the ledger when the settlementReport goes into status
     *     ‘processing’. This code is not used for SEPA or ACH transfers</li>
     *     <li>W003 - Liquidity Withholding : Used when BitPay's balance at the specific settlement bank
     *     is insufficient to payout the full amount today</li>
     *     <li>W004 - Insufficient Balance : in the event a case the ledger balance of the merchant drops after
     *     the settlement cut-off, the settlement amount will be adjusted.</li>
     *     <li>W005 - Pending Refunds : in order to make sure enough funds are left on the account to execute pending
     *     refund requests.</li>
     * </ul>
     *
     * @return the with holdings
     */
    @JsonIgnore
    public List<WithHoldings> getWithHoldings() {
        return this.withHoldings;
    }

    /**
     * Sets array of With Holdings.
     * With Holdings are kept on the ledger to be used later and thus withheld from this settlement.
     * Each withholding is a JSON object containing a code, amount and description field.
     * The possible withholding codes are:
     * <ul>
     *     <li>W001 - Refund Reserve: a merchant can set a refund reserve for his account.
     *     This code indicate the current amount in the reserve at the moment the settlement is generated.</li>
     *     <li>W002 - Settlement Fee: in case BitPay is charging the bank fees to the merchant,
     *     via a SWIFT wire for instance, will be posted to the ledger when the settlementReport goes into status
     *     ‘processing’. This code is not used for SEPA or ACH transfers</li>
     *     <li>W003 - Liquidity Withholding : Used when BitPay's balance at the specific settlement bank
     *     is insufficient to payout the full amount today</li>
     *     <li>W004 - Insufficient Balance : in the event a case the ledger balance of the merchant drops
     *     after the settlement cut-off, the settlement amount will be adjusted.</li>
     *     <li>W005 - Pending Refunds : in order to make sure enough funds are left on the account to execute
     *     pending refund requests.</li>
     * </ul>
     *
     * @param withHoldings the with holdings
     */
    @JsonProperty("withholdings")
    public void setWithHoldings(List<WithHoldings> withHoldings) {
        this.withHoldings = withHoldings;
    }

    /**
     * Gets sum of all amounts that are withheld from settlement.
     *
     * @return the with holdings sum
     */
    @JsonIgnore
    public Float getWithHoldingsSum() {
        return this.withHoldingsSum;
    }

    /**
     * Sets sum of all amounts that are withheld from settlement.
     *
     * @param withHoldingsSum the with holdings sum
     */
    @JsonProperty("withholdingsSum")
    public void setWithHoldingsSum(Float withHoldingsSum) {
        this.withHoldingsSum = withHoldingsSum;
    }

    /**
     * Gets total amount.
     * Total amount sent to the merchant; 2 decimals.
     * TotalAmount = openingBalance + ledgerEntriesSum - withholdingsSum
     *
     * @return the total amount
     */
    @JsonIgnore
    public Float getTotalAmount() {
        return this.totalAmount;
    }

    /**
     * Sets total amount.
     * Total amount sent to the merchant; 2 decimals.
     * TotalAmount = openingBalance + ledgerEntriesSum - withholdingsSum
     *
     * @param totalAmount the total amount
     */
    @JsonProperty("totalAmount")
    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Gets Ledger Entries.
     *
     * @return the ledger entries
     */
    @JsonIgnore
    public List<SettlementLedgerEntry> getLedgerEntries() {
        return this.ledgerEntries;
    }

    /**
     * Sets Ledger Entries.
     *
     * @param ledgerEntries the ledger entries
     */
    @JsonProperty("ledgerEntries")
    public void setLedgerEntries(List<SettlementLedgerEntry> ledgerEntries) {
        this.ledgerEntries = ledgerEntries;
    }

    /**
     * Gets API token for the corresponding settlement resource.
     * This token is actually derived from the merchant facade token used during the query.
     * This token is required to fetch the reconciliation report.
     *
     * @return the token
     */
    @JsonIgnore
    public String getToken() {
        return this.token;
    }

    /**
     * Sets API token for the corresponding settlement resource.
     * This token is actually derived from the merchant facade token used during the query.
     * This token is required to fetch the reconciliation report.
     *
     * @param token the token
     */
    @JsonProperty("token")
    public void setToken(String token) {
        this.token = token;
    }
}
