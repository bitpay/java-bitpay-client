package com.bitpay.sdk.model.Ledger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Ledger entry.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-resources-ledgers">REST API Ledgers</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LedgerEntry {

    private String _type;
    private String _amount;
    private String _code;
    private String _description;
    private String _timestamp;
    private String _txType;
    private String _scale;
    private String _invoiceId;
    private Buyer _buyer;
    private Double _invoiceAmount;
    private String _invoiceCurrency;
    private String _transactionCurrency;
    private String _id;

    /**
     * Instantiates a new Ledger entry.
     */
    public LedgerEntry() {
    }

    /**
     * Gets type of Ledger entry name.
     *
     * @return the type
     *
     * @see <a href="https://bitpay.com/api/#ledger-entry-codes">Ledger entry codes</a>
     */
    @JsonIgnore
    public String getType() {
        return _type;
    }

    /**
     * Sets type of Ledger entry name.
     *
     * @param type the type
     *
     * @see <a href="https://bitpay.com/api/#ledger-entry-codes">Ledger entry codes</a>
     */
    @JsonProperty("type")
    public void setType(String type) {
        this._type = type;
    }

    /**
     * Gets amount.
     * Ledger entry amount, relative to the scale.
     * The decimal amount can be obtained by dividing the amount field by the scale parameter.
     *
     * @return the amount
     */
    @JsonIgnore
    public String getAmount() {
        return _amount;
    }

    /**
     * Sets amount.
     * Ledger entry amount, relative to the scale.
     * The decimal amount can be obtained by dividing the amount field by the scale parameter.
     *
     * @param amount the amount
     *
     *
     */
    @JsonProperty("amount")
    public void setAmount(String amount) {
        this._amount = amount;
    }

    /**
     * Gets Ledger entry code.
     * Contains the Ledger entry code. See the list of Ledger Entry Codes.
     *
     * @return the code
     *
     * @see <a href="https://bitpay.com/api/#ledger-entry-codes">Ledger entry codes</a>
     */
    @JsonIgnore
    public String getCode() {
        return _code;
    }

    /**
     * Sets Ledger entry code.
     * Contains the Ledger entry code. See the list of Ledger Entry Codes.
     *
     * @param code the code
     *
     * @see <a href="https://bitpay.com/api/#ledger-entry-codes">Ledger entry codes</a>
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this._code = code;
    }

    /**
     * Gets description.
     * Ledger entry description.
     * Also contains an id depending on the type of entry
     * (for instance payout id, settlement id, invoice orderId etc...).
     *
     * @return the description
     */
    @JsonIgnore
    public String getDescription() {
        return _description;
    }

    /**
     * Sets description.
     * Ledger entry description.
     * Also contains an id depending on the type of entry
     * (for instance payout id, settlement id, invoice orderId etc...).
     *
     * @param description the description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this._description = description;
    }

    /**
     * Gets date and time of the ledger entry (UTC). ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @return the timestamp
     */
    @JsonIgnore
    public String getTimestamp() {
        return _timestamp;
    }

    /**
     * Sets date and time of the ledger entry (UTC). ISO-8601 format yyyy-mm-ddThh:mm:ssZ.
     *
     * @param timestamp the timestamp
     */
    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this._timestamp = timestamp;
    }

    /**
     * Gets tx type.
     *
     * @deprecated see type
     *
     * @return the tx type
     */
    @Deprecated
    @JsonIgnore
    public String getTxType() {
        return _txType;
    }

    /**
     * Sets tx type.
     *
     * @deprecated see type
     *
     * @param txType the tx type
     */
    @JsonProperty("txType")
    public void setTxType(String txType) {
        this._txType = txType;
    }

    /**
     * Gets power of 10 used for conversion.
     *
     * @return the scale
     */
    @JsonIgnore
    public String getScale() {
        return _scale;
    }

    /**
     * Sets power of 10 used for conversion.
     *
     * @param scale the scale
     */
    @JsonProperty("scale")
    public void setScale(String scale) {
        this._scale = scale;
    }

    /**
     * Gets BitPay invoice Id.
     *
     * @return the invoice id
     */
    @JsonIgnore
    public String getInvoiceId() {
        return _invoiceId;
    }

    /**
     * Sets BitPay invoice Id.
     *
     * @param invoiceId the invoice id
     */
    @JsonProperty("invoiceId")
    public void setInvoiceId(String invoiceId) {
        this._invoiceId = invoiceId;
    }

    /**
     * Gets Buyer.
     *
     * @return the buyer
     */
    @JsonIgnore
    public Buyer getBuyer() {
        return _buyer;
    }

    /**
     * Sets Buyer.
     *
     * @param buyer the buyer
     */
    @JsonProperty("buyerFields")
    public void setBuyer(Buyer buyer) {
        this._buyer = buyer;
    }

    /**
     * Gets invoice price in the invoice original currency.
     *
     * @return the invoice amount
     */
    @JsonIgnore
    public Double getInvoiceAmount() {
        return _invoiceAmount;
    }

    /**
     * Sets invoice price in the invoice original currency.
     *
     * @param invoiceAmount the invoice amount
     */
    @JsonProperty("invoiceAmount")
    public void setInvoiceAmount(Double invoiceAmount) {
        this._invoiceAmount = invoiceAmount;
    }

    /**
     * Gets currency used for invoice creation.
     *
     * @return the invoice currency
     */
    @JsonIgnore
    public String getInvoiceCurrency() {
        return _invoiceCurrency;
    }

    /**
     * Sets currency used for invoice creation.
     *
     * @param invoiceCurrency the invoice currency
     */
    @JsonProperty("invoiceCurrency")
    public void setInvoiceCurrency(String invoiceCurrency) {
        this._invoiceCurrency = invoiceCurrency;
    }

    /**
     * Gets transaction currency.
     * Cryptocurrency selected by the consumer when paying an invoice.
     *
     * @return the transaction currency
     */
    @JsonIgnore
    public String getTransactionCurrency() {
        return _transactionCurrency;
    }

    /**
     * Sets transaction currency.
     * Cryptocurrency selected by the consumer when paying an invoice.
     *
     * @param transactionCurrency the transaction currency
     */
    @JsonProperty("transactionCurrency")
    public void setTransactionCurrency(String transactionCurrency) {
        this._transactionCurrency = transactionCurrency;
    }

    /**
     * Gets Ledger resource Id.
     *
     * @return the id
     */
    @JsonIgnore
    public String getId() {
        return _id;
    }

    /**
     * Sets Ledger resource Id.
     *
     * @param id the id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this._id = id;
    }
}
