package com.bitpay.sdk.model.Settlement;

import com.bitpay.sdk.util.DateDeserializer;
import com.bitpay.sdk.util.DateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Settlement {

    private String _id;
    private String _accountId;
    private String _currency;
    private PayoutInfo _payoutInfo;
    private String _status;
    private Long _dateCreated;
    private Long _dateExecuted;
    private Long _dateCompleted;
    private Long _openingDate;
    private Long _closingDate;
    private Float _openingBalance;
    private Float _ledgerEntriesSum;
    private List<WithHoldings> _withHoldings;
    private Float _withHoldingsSum;
    private Float _totalAmount;
    private List<SettlementLedgerEntry> _ledgerEntries;
    private String _token;

    public Settlement() {
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
    public String getAccountId() {
        return _accountId;
    }

    @JsonProperty("accountId")
    public void setAccountId(String accountId) {
        this._accountId = accountId;
    }

    @JsonIgnore
    public String getCurrency() {
        return _currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this._currency = currency;
    }

    @JsonIgnore
    public PayoutInfo getPayoutInfo() {
        return _payoutInfo;
    }

    @JsonProperty("payoutInfo")
    public void setPayoutInfo(PayoutInfo payoutInfo) {
        this._payoutInfo = payoutInfo;
    }

    @JsonIgnore
    public String getStatus() {
        return _status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this._status = status;
    }

    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public Long getDateCreated() {
        return _dateCreated;
    }

    @JsonProperty("dateCreated")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setDateCreated(Long dateCreated) {
        this._dateCreated = dateCreated;
    }

    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public Long getDateExecuted() {
        return _dateExecuted;
    }

    @JsonProperty("dateExecuted")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setDateExecuted(Long dateExecuted) {
        this._dateExecuted = dateExecuted;
    }

    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public Long getDateCompleted() {
        return _dateCompleted;
    }

    @JsonProperty("dateCompleted")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setDateCompleted(Long dateCompleted) {
        this._dateCompleted = dateCompleted;
    }

    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public Long getOpeningDate() {
        return _openingDate;
    }

    @JsonProperty("openingDate")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setOpeningDate(Long openingDate) {
        this._openingDate = openingDate;
    }

    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    public Long getClosingDate() {
        return _closingDate;
    }

    @JsonProperty("closingDate")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setClosingDate(Long closingDate) {
        this._closingDate = closingDate;
    }

    @JsonIgnore
    public Float getOpeningBalance() {
        return _openingBalance;
    }

    @JsonProperty("openingBalance")
    public void setOpeningBalance(Float openingBalance) {
        this._openingBalance = openingBalance;
    }

    @JsonIgnore
    public Float getLedgerEntriesSum() {
        return _ledgerEntriesSum;
    }

    @JsonProperty("ledgerEntriesSum")
    public void setLedgerEntriesSum(Float ledgerEntriesSum) {
        this._ledgerEntriesSum = ledgerEntriesSum;
    }

    @JsonIgnore
    public List<WithHoldings> getWithHoldings() {
        return _withHoldings;
    }

    @JsonProperty("withHoldings")
    public void setWithHoldings(List<WithHoldings> withHoldings) {
        this._withHoldings = withHoldings;
    }

    @JsonIgnore
    public Float getWithHoldingsSum() {
        return _withHoldingsSum;
    }

    @JsonProperty("withHoldingsSum")
    public void setWithHoldingsSum(Float withHoldingsSum) {
        this._withHoldingsSum = withHoldingsSum;
    }

    @JsonIgnore
    public Float getTotalAmount() {
        return _totalAmount;
    }

    @JsonProperty("totalAmount")
    public void setTotalAmount(Float totalAmount) {
        this._totalAmount = totalAmount;
    }

    @JsonIgnore
    public List<SettlementLedgerEntry> getLedgerEntries() {
        return _ledgerEntries;
    }

    @JsonProperty("ledgerEntries")
    public void setLedgerEntries(List<SettlementLedgerEntry> ledgerEntries) {
        this._ledgerEntries = ledgerEntries;
    }

    @JsonIgnore
    public String getToken() {
        return _token;
    }

    @JsonProperty("token")
    public void setToken(String token) {
        this._token = token;
    }
}
