/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.invoice;

import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.Currency;
import com.bitpay.sdk.model.ModelConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * The type Invoice.
 *
 * @see <a href="https://bitpay.readme.io/reference/invoices">REST API Invoices</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Invoice {

    protected String currency = ModelConfiguration.DEFAULT_NON_SENT_VALUE;

    protected String guid;
    protected String token;

    protected Double price;
    protected String posData;
    protected String notificationUrl = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String transactionSpeed = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected Boolean fullNotifications;
    protected String notificationEmail = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String redirectUrl = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String closeUrl = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String orderId = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String itemDesc = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String itemCode = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected Boolean physical;
    protected List<String> paymentCurrencies;
    protected Integer acceptanceWindow;
    protected Buyer buyer;
    protected String buyerSms = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String merchantName = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String selectedTransactionCurrency = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String forcedBuyerSelectedWallet = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected InvoiceUniversalCodes universalCodes;
    protected List<InvoiceItemizedDetails> itemizedDetails;
    protected Boolean autoRedirect;

    protected String id;
    protected String url = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String status = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected Boolean lowFeeDetected;
    protected Long invoiceTime;
    protected Long expirationTime;
    protected Long currentTime;
    protected String exceptionStatus = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected Integer targetConfirmations;
    protected List<InvoiceTransaction> transactions;
    protected List<Map<String, InvoiceRefundAddresses>> refundAddresses;
    protected Boolean refundAddressRequestPending;
    protected String buyerProvidedEmail = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected InvoiceBuyerProvidedInfo invoiceBuyerProvidedInfo;
    protected SupportedTransactionCurrencies supportedTransactionCurrencies;
    protected MinerFees minerFees;
    protected Shopper shopper;
    protected String billId = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected ArrayList<RefundInfo> refundInfo;
    protected Boolean extendedNotifications;
    protected String transactionCurrency = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected String forcedBuyerSelectedTransactionCurrency = ModelConfiguration.DEFAULT_NON_SENT_VALUE;
    protected BigDecimal amountPaid;
    protected String displayAmountPaid;
    protected Hashtable<String, Hashtable<String, BigDecimal>> exchangeRates;
    protected Boolean isCancelled;
    protected Boolean bitpayIdRequired;
    protected Hashtable<String, BigInteger> paymentSubtotals;
    protected Hashtable<String, BigInteger> paymentTotals;
    protected Hashtable<String, String> paymentDisplayTotals;
    protected Hashtable<String, String> paymentDisplaySubTotals;
    protected Boolean nonPayProPaymentReceived;
    protected Boolean jsonPayProRequired;
    protected BigDecimal underpaidAmount;
    protected BigDecimal overpaidAmount;
    protected Hashtable<String, Hashtable<String, String>> paymentCodes;

    /**
     * Constructor, create an empty Invoice object.
     */
    public Invoice() {
    }

    /**
     * Constructor, create a minimal request Invoice object.
     *
     * @param price    The amount for which the invoice will be created.
     * @param currency The three digit currency type used to compute the invoice bitcoin amount.
     */
    public Invoice(final Double price, final String currency) {
        this.price = price;
        this.currency = currency;
    }

    // API fields
    //

    /**
     * Gets a passthru variable provided by the merchant and designed to be used by the merchant to correlate
     * the invoice with an order ID in their system, which can be used as a lookup variable in Retrieve Invoice by GUID.
     *
     * @return the guid
     */
    @JsonProperty("guid")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getGuid() {
        return this.guid;
    }

    /**
     * Sets a passthru variable provided by the merchant and designed to be used by the merchant to correlate
     * the invoice with an order ID in their system, which can be used as a lookup variable in Retrieve Invoice by GUID.
     *
     * @param guid the guid
     */
    @JsonProperty("guid")
    public void setGuid(final String guid) {
        this.guid = guid;
    }

    /**
     * Gets invoice resource token.
     * This token is derived from the API token initially used to create the invoice and is tied
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
     * Sets invoice resource token.
     * This token is derived from the API token initially used to create the invoice and is tied
     * to the specific resource id created.
     *
     * @param token the token
     */
    @JsonProperty("token")
    public void setToken(final String token) {
        this.token = token;
    }

    // Required fields
    //

    /**
     * Gets fixed price amount for the checkout, in the "currency" of the invoice object.
     *
     * @return the price
     */
    @JsonProperty("price")
    public Double getPrice() {
        return this.price;
    }

    /**
     * Sets fixed price amount for the checkout, in the "currency" of the invoice object.
     *
     * @param price the price
     */
    @JsonProperty("price")
    public void setPrice(final Double price) {
        this.price = price;
    }

    /**
     * Gets ISO 4217 3-character currency code.
     * This is the currency associated with the price field.
     *
     * @return the currency
     * @see <a href="https://bitpay.readme.io/reference/currencies">Supported currencies</a>
     */
    @JsonProperty("currency")
    public String getCurrency() {
        return this.currency;
    }

    /**
     * Sets ISO 4217 3-character currency code.
     * This is the currency associated with the price field.
     *
     * @param currency the currency
     * @throws BitPayGenericException BitPayGenericException class
     * @see <a href="https://bitpay.readme.io/reference/currencies">Supported currencies</a>
     */
    @JsonProperty("currency")
    public void setCurrency(final String currency) throws BitPayGenericException {
        if (!Currency.isValid(currency)) {
            BitPayExceptionProvider.throwInvalidCurrencyException(currency);
        }

        this.currency = currency;
    }

    // Optional fields
    //

    /**
     * Gets order id. Can be used by the merchant to assign their own internal Id to an invoice.
     * If used, there should be a direct match between an orderId and an invoice id.
     *
     * @return the order id
     */
    @JsonProperty("orderId")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getOrderId() {
        return this.orderId;
    }

    /**
     * Sets order id. Can be used by the merchant to assign their own internal Id to an invoice.
     * If used, there should be a direct match between an orderId and an invoice id.
     *
     * @param orderId the order id
     */
    @JsonProperty("orderId")
    public void setOrderId(final String orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets item description. Will be added as a line item on the BitPay checkout page, under the merchant name.
     *
     * @return the item desc
     */
    @JsonProperty("itemDesc")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getItemDesc() {
        return this.itemDesc;
    }

    /**
     * Sets item description. Will be added as a line item on the BitPay checkout page, under the merchant name.
     *
     * @param itemDesc the item desc
     */
    @JsonProperty("itemDesc")
    public void setItemDesc(final String itemDesc) {
        this.itemDesc = itemDesc;
    }

    /**
     * Gets item code. "bitcoindonation" for donations, otherwise do not include the field in the request.
     *
     * @return the item code
     */
    @JsonProperty("itemCode")
    @JsonInclude(value = JsonInclude.Include.NON_DEFAULT, content = JsonInclude.Include.NON_DEFAULT)
    public String getItemCode() {
        return this.itemCode;
    }

    /**
     * Sets item code. "bitcoindonation" for donations, otherwise do not include the field in the request.
     *
     * @param itemCode the item code
     */
    @JsonProperty("itemCode")
    public void setItemCode(final String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * Gets pos data. A passthru variable provided by the merchant and designed to be used by the merchant
     * to correlate the invoice with an order or other object in their system.
     * This passthru variablecan be a serialized object,
     * e.g.: "posData": "\"{ \"ref\" : 711454, \"item\" : \"test_item\" }\"".
     *
     * @return the pos data
     */
    @JsonProperty("posData")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getPosData() {
        return this.posData;
    }

    /**
     * Sets pos data. A passthru variable provided by the merchant and designed to be used by the merchant
     * to correlate the invoice with an order or other object in their system.
     * This passthru variablecan be a serialized object,
     * e.g.: "posData": "\"{ \"ref\" : 711454, \"item\" : \"test_item\" }\"".
     *
     * @param posData the pos data
     */
    @JsonProperty("posData")
    public void setPosData(final String posData) {
        this.posData = posData;
    }

    /**
     * Gets URL to which BitPay sends webhook notifications. HTTPS is mandatory.
     *
     * @return the notification url
     */
    @JsonProperty("notificationURL")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getNotificationUrl() {
        return this.notificationUrl;
    }

    /**
     * Sets URL to which BitPay sends webhook notifications. HTTPS is mandatory.
     *
     * @param notificationUrl the notification url
     */
    @JsonProperty("notificationURL")
    public void setNotificationUrl(final String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

    /**
     * Gets transaction speed. This is a risk mitigation parameter for the merchant to configure how they want to
     * fulfill orders depending on the number of block confirmations for the transaction made by the consumer
     * on the selected cryptocurrency.
     * <ul>
     *     <li>high: The invoice is marked as "confirmed" by BitPay as soon as full payment is received
     *     but not yet validated on the corresponding blockchain.
     *     The invoice will go from a status of "new" to "confirmed", bypassing the "paid" status.
     *     If you want an immediate notification for a payment, you can use the high speed setting.
     *     However, it makes you more susceptible to receiving fraudulent payments, so it is not recommended.
     *     </li>
     *     <li>medium: (Recommended for most merchants) The invoice is marked as "confirmed" after the transaction
     *     has received basic confirmation on the corresponding blockchain.
     *     For invoices paid in Bitcoin (BTC),
     *     this means 1 confirmation on the blockchain which takes on average 10 minutes.
     *     The invoice will go from a status of "new" to "paid" followed by "confirmed" and then "complete"
     *     </li>
     *     <li>low: The invoice is marked as "confirmed" once BitPay has credited the funds to the merchant account.
     *     The invoice will go from a status of "new" to "paid" followed by "complete",
     *     thus bypassing the "confirmed" status.
     *     For invoices paid in Bitcoin (BTC),
     *     this means 6 confirmations on the blockchain which takes on average an hour
     *     </li>
     * </ul>
     *
     * <p>If not set on the invoice, transactionSpeed will default to the account-level Order Settings.</p>
     * <p>Note : orders are only credited to your BitPay Account Summary for settlement after the invoice
     * reaches the status "complete" (regardless of this setting).
     * </p>
     *
     * @return the transaction speed
     */
    @JsonProperty("transactionSpeed")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getTransactionSpeed() {
        return this.transactionSpeed;
    }

    /**
     * Sets transaction speed. This is a risk mitigation parameter for the merchant to configure how they want to
     * fulfill orders depending on the number of block confirmations for the transaction made by the consumer
     * on the selected cryptocurrency.
     * <ul>
     *     <li>high: The invoice is marked as "confirmed" by BitPay as soon as full payment is received
     *     but not yet validated on the corresponding blockchain.
     *     The invoice will go from a status of "new" to "confirmed", bypassing the "paid" status.
     *     If you want an immediate notification for a payment, you can use the high speed setting.
     *     However, it makes you more susceptible to receiving fraudulent payments, so it is not recommended.
     *     </li>
     *     <li>medium: (Recommended for most merchants) The invoice is marked as "confirmed" after the transaction
     *     has received basic confirmation on the corresponding blockchain.
     *     For invoices paid in Bitcoin (BTC),
     *     this means 1 confirmation on the blockchain which takes on average 10 minutes.
     *     The invoice will go from a status of "new" to "paid" followed by "confirmed" and then "complete"
     *     </li>
     *     <li>low: The invoice is marked as "confirmed" once BitPay has credited the funds to the merchant account.
     *     The invoice will go from a status of "new" to "paid" followed by "complete",
     *     thus bypassing the "confirmed" status.
     *     For invoices paid in Bitcoin (BTC),
     *     this means 6 confirmations on the blockchain which takes on average an hour
     *     </li>
     * </ul>
     *
     * <p>If not set on the invoice, transactionSpeed will default to the account-level Order Settings.</p>
     * <p>Note : orders are only credited to your BitPay Account Summary for settlement after the invoice
     * reaches the status "complete" (regardless of this setting).
     * </p>
     *
     * @param transactionSpeed the transaction speed
     */
    @JsonProperty("transactionSpeed")
    public void setTransactionSpeed(final String transactionSpeed) {
        this.transactionSpeed = transactionSpeed;
    }

    /**
     * Gets full notifications. This parameter is set to true by default,
     * meaning all standard notifications are being sent for a payment made to an invoice.
     * If you decide to set it to false instead, only 1 webhook will be sent for each invoice paid by the consumer.
     * This webhook will be for the "confirmed" or "complete" invoice status,
     * depending on the transactionSpeed selected.
     *
     * @return the full notifications
     */
    @JsonProperty("fullNotifications")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Boolean getFullNotifications() {
        return this.fullNotifications;
    }

    /**
     * Sets full notifications. This parameter is set to true by default,
     * meaning all standard notifications are being sent for a payment made to an invoice.
     * If you decide to set it to false instead, only 1 webhook will be sent for each invoice paid by the consumer.
     * This webhook will be for the "confirmed" or "complete" invoice status,
     * depending on the transactionSpeed selected.
     *
     * @param fullNotifications the full notifications
     */
    @JsonProperty("fullNotifications")
    public void setFullNotifications(final Boolean fullNotifications) {
        this.fullNotifications = fullNotifications;
    }

    /**
     * Gets extended notifications. Allows merchants to get access to additional webhooks.
     * For instance when an invoice expires without receiving a payment or when it is refunded.
     * If set to true, then fullNotifications is automatically set to true.
     * When using the extendedNotifications parameter,
     * the webhook also have a payload slightly different from the standard webhooks.
     *
     * @return the extended notifications
     */
    @JsonProperty("extendedNotifications")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Boolean getExtendedNotifications() {
        return this.extendedNotifications;
    }

    /**
     * Sets extended notifications. Allows merchants to get access to additional webhooks.
     * For instance when an invoice expires without receiving a payment or when it is refunded.
     * If set to true, then fullNotifications is automatically set to true.
     * When using the extendedNotifications parameter,
     * the webhook also have a payload slightly different from the standard webhooks.
     *
     * @param extendedNotifications the extended notifications
     */
    @JsonProperty("extendedNotifications")
    public void setExtendedNotifications(final Boolean extendedNotifications) {
        this.extendedNotifications = extendedNotifications;
    }

    /**
     * Gets merchant email address for notification of invoice status change.
     * It is also possible to configure this email via the account setting on the BitPay dashboard
     * or disable the email notification.
     *
     * @return the notification email
     */
    @JsonProperty("notificationEmail")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getNotificationEmail() {
        return this.notificationEmail;
    }

    /**
     * Sets merchant email address for notification of invoice status change.
     * It is also possible to configure this email via the account setting on the BitPay dashboard
     * or disable the email notification.
     *
     * @param notificationEmail the notification email
     */
    @JsonProperty("notificationEmail")
    public void setNotificationEmail(final String notificationEmail) {
        this.notificationEmail = notificationEmail;
    }

    /**
     * Gets redirect url. The shopper will be redirected to this URL when clicking on the Return button after
     * a successful payment or when clicking on the Close button if a separate closeURL is not specified.
     * Be sure to include "http://" or "https://" in the url.
     *
     * @return the redirect url
     */
    @JsonProperty("redirectURL")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    /**
     * Sets redirect url. The shopper will be redirected to this URL when clicking on the Return button after
     * a successful payment or when clicking on the Close button if a separate closeURL is not specified.
     * Be sure to include "http://" or "https://" in the url.
     *
     * @param redirectUrl the redirect url
     */
    @JsonProperty("redirectURL")
    public void setRedirectUrl(final String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    /**
     * Gets URL to redirect if the shopper does not pay the invoice and click on the Close button instead.
     * Be sure to include "http://" or "https://" in the url.
     *
     * @return the close url
     */
    @JsonProperty("closeURL")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCloseUrl() {
        return this.closeUrl;
    }

    /**
     * Sets URL to redirect if the shopper does not pay the invoice and click on the Close button instead.
     * Be sure to include "http://" or "https://" in the url.
     *
     * @param closeUrl the close url
     */
    @JsonProperty("closeURL")
    public void setCloseUrl(final String closeUrl) {
        this.closeUrl = closeUrl;
    }

    /**
     * Gets physical. Indicates whether items are physical goods. Alternatives include digital goods and services.
     *
     * @return the physical
     */
    @JsonProperty("physical")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Boolean getPhysical() {
        return this.physical;
    }

    /**
     * Sets physical. Indicates whether items are physical goods. Alternatives include digital goods and services.
     *
     * @param physical the physical
     */
    @JsonProperty("physical")
    public void setPhysical(final Boolean physical) {
        this.physical = physical;
    }

    /**
     * Gets payment currencies. Allow the merchant to select the cryptocurrencies available as payment option
     * on the BitPay invoice.
     * Possible values are currently "BTC", "BCH", "ETH", "GUSD", "PAX", "BUSD", "USDC", "XRP", "DOGE", "DAI" and
     * "WBTC".
     * For instance "paymentCurrencies": ["BTC"] will create an invoice with only XRP available as transaction currency,
     * thus bypassing the currency selection step on the invoice.
     *
     * @return the payment currencies
     */
    @JsonProperty("paymentCurrencies")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<String> getPaymentCurrencies() {
        return this.paymentCurrencies;
    }

    /**
     * Sets payment currencies. Allow the merchant to select the cryptocurrencies available as payment option
     * on the BitPay invoice.
     * Possible values are currently "BTC", "BCH", "ETH", "GUSD", "PAX", "BUSD", "USDC", "XRP", "DOGE", "DAI" and
     * "WBTC".
     * For instance "paymentCurrencies": ["BTC"] will create an invoice with only XRP available as transaction currency,
     * thus bypassing the currency selection step on the invoice.
     *
     * @param paymentCurrencies the payment currencies
     */
    @JsonProperty("paymentCurrencies")
    public void setPaymentCurrencies(final List<String> paymentCurrencies) {
        this.paymentCurrencies = paymentCurrencies;
    }

    /**
     * Gets number of milliseconds that a user has to pay an invoice before it expires (0-900000).
     * If not set, invoice will default to the account acceptanceWindow.
     * If account acceptanceWindow is not set, invoice will default to 15 minutes (900,000 milliseconds).
     *
     * @return the acceptance window
     */
    @JsonProperty("acceptanceWindow")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Integer getAcceptanceWindow() {
        return this.acceptanceWindow;
    }

    /**
     * Sets number of milliseconds that a user has to pay an invoice before it expires (0-900000).
     * If not set, invoice will default to the account acceptanceWindow.
     * If account acceptanceWindow is not set, invoice will default to 15 minutes (900,000 milliseconds).
     *
     * @param acceptanceWindow the acceptance window
     */
    @JsonProperty("acceptanceWindow")
    public void setAcceptanceWindow(final Integer acceptanceWindow) {
        this.acceptanceWindow = acceptanceWindow;
    }

    /**
     * Gets a display string for merchant identification (ex. Wal-Mart Store #1452, Bowling Green, KY).
     *
     * @return the merchant name
     */
    @JsonProperty("merchantName")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getMerchantName() {
        return this.merchantName;
    }

    /**
     * Sets a display string for merchant identification (ex. Wal-Mart Store #1452, Bowling Green, KY).
     *
     * @param merchantName the merchant name
     */
    @JsonProperty("merchantName")
    public void setMerchantName(final String merchantName) {
        this.merchantName = merchantName;
    }

    /**
     * Gets selected transaction currency. This field will be populated with the cryptocurrency selected to pay
     * the BitPay invoice, current supported values are "BTC", "BCH", "ETH", "GUSD", "PAX", "BUSD", "USDC", "XRP",
     * "DOGE", "DAI" and "WBTC". If not yet selected, this field will not be returned.
     *
     * @return the selected transaction currency
     */
    @JsonProperty("selectedTransactionCurrency")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getSelectedTransactionCurrency() {
        return this.selectedTransactionCurrency;
    }

    /**
     * Sets selected transaction currency. This field will be populated with the cryptocurrency selected to pay
     * the BitPay invoice, current supported values are "BTC", "BCH", "ETH", "GUSD", "PAX", "BUSD", "USDC", "XRP",
     * "DOGE", "DAI" and "WBTC". If not yet selected, this field will not be returned.
     *
     * @param selectedTransactionCurrency the selected transaction currency
     * @throws BitPayGenericException BitPayGenericException class
     */
    @JsonProperty("selectedTransactionCurrency")
    public void setSelectedTransactionCurrency(final String selectedTransactionCurrency) throws BitPayGenericException {
        if (!Currency.isValid(selectedTransactionCurrency)) {
            BitPayExceptionProvider.throwInvalidCurrencyException(selectedTransactionCurrency);
        }

        this.selectedTransactionCurrency = this.currency;
    }

    /**
     * Gets merchant pre-selects wallet on behalf of buyer.
     *
     * @return the forced buyer selected wallet
     */
    @JsonProperty("forcedBuyerSelectedWallet")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getForcedBuyerSelectedWallet() {
        return this.forcedBuyerSelectedWallet;
    }

    /**
     * Sets merchant pre-selects wallet on behalf of buyer.
     *
     * @param forcedBuyerSelectedWallet the forced buyer selected wallet
     */
    @JsonProperty("forcedBuyerSelectedWallet")
    public void setForcedBuyerSelectedWallet(final String forcedBuyerSelectedWallet) {
        this.forcedBuyerSelectedWallet = forcedBuyerSelectedWallet;
    }

    /**
     * Gets object containing wallet-specific URLs for payment protocol.
     *
     * @return the universal codes
     */
    @JsonProperty("universalCodes")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public InvoiceUniversalCodes getUniversalCodes() {
        return this.universalCodes;
    }

    /**
     * Sets object containing wallet-specific URLs for payment protocol.
     *
     * @param universalCodes the universal codes
     */
    @JsonProperty("universalCodes")
    public void setUniversalCodes(final InvoiceUniversalCodes universalCodes) {
        this.universalCodes = universalCodes;
    }

    /**
     * Gets object containing line item details for display.
     *
     * @return the itemized details
     */
    @JsonProperty("itemizedDetails")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<InvoiceItemizedDetails> getItemizedDetails() {
        return this.itemizedDetails;
    }

    /**
     * Sets object containing line item details for display.
     *
     * @param itemizedDetails the itemized details
     */
    @JsonProperty("itemizedDetails")
    public void setItemizedDetails(final List<InvoiceItemizedDetails> itemizedDetails) {
        this.itemizedDetails = itemizedDetails;
    }

    /**
     * Gets auto redirect. Set to false by default, merchant can setup automatic redirect to their website by setting
     * this parameter to true. This will applied to the following scenarios:
     * <ul>
     *     <li>When the invoice is paid, it automatically redirects the shopper to the redirectURL indicated</li>
     *     <li>When the invoice expires, it automatically redirects the shopper to the closeURL if specified and
     *     to the redirectURL otherwise
     *     </li>
     * </ul>
     * <p>Note: If automatic redirect is enabled, redirectURL becomes a mandatory invoice parameters.</p>
     *
     * @return the auto redirect
     */
    @JsonProperty("autoRedirect")
    public Boolean getAutoRedirect() {
        return this.autoRedirect;
    }

    /**
     * Sets auto redirect. Set to false by default, merchant can setup automatic redirect to their website by setting
     * this parameter to true. This will applied to the following scenarios:
     * <ul>
     *     <li>When the invoice is paid, it automatically redirects the shopper to the redirectURL indicated</li>
     *     <li>When the invoice expires, it automatically redirects the shopper to the closeURL if specified and
     *     to the redirectURL otherwise
     *     </li>
     * </ul>
     * <p>Note: If automatic redirect is enabled, redirectURL becomes a mandatory invoice parameters.</p>
     *
     * @param autoRedirect the auto redirect
     */
    @JsonProperty("autoRedirect")
    public void setAutoRedirect(final Boolean autoRedirect) {
        this.autoRedirect = autoRedirect;
    }

    /**
     * Gets BitPay id required. BitPay ID is a verification process that is required when a user is making payments or
     * receiving a refund over a given threshold, which may vary by region.
     * This Boolean forces the invoice to require BitPay ID regardless of the price.
     *
     * @return the bitpay id required
     */
    @JsonProperty("bitpayIdRequired")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Boolean getBitpayIdRequired() {
        return this.bitpayIdRequired;
    }

    /**
     * Sets BitPay id required. BitPay ID is a verification process that is required when a user is making payments or
     * receiving a refund over a given threshold, which may vary by region.
     * This Boolean forces the invoice to require BitPay ID regardless of the price.
     *
     * @param bitpayIdRequired the bitpay id required
     */
    @JsonProperty("bitpayIdRequired")
    public void setBitpayIdRequired(final Boolean bitpayIdRequired) {
        this.bitpayIdRequired = bitpayIdRequired;
    }

    // Buyer data
    //

    /**
     * Gets buyer. Allows merchant to pass buyer related information in the invoice object.
     *
     * @return the buyer
     */
    @JsonProperty("buyer")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Buyer getBuyer() {
        return this.buyer;
    }

    /**
     * Sets buyer. Allows merchant to pass buyer related information in the invoice object.
     *
     * @param buyer the buyer
     */
    @JsonProperty("buyer")
    public void setBuyer(final Buyer buyer) {
        this.buyer = buyer;
    }

    /**
     * Gets buyer sms. SMS phone number including country code i.e. “+12223334444.
     *
     * @return the buyer sms
     */
    @JsonProperty("buyerSms")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getBuyerSms() {
        return this.buyerSms;
    }

    /**
     * Sets buyer sms. SMS phone number including country code i.e. “+12223334444.
     *
     * @param buyerSms the buyer sms
     */
    @JsonProperty("buyerSms")
    public void setBuyerSms(final String buyerSms) {
        this.buyerSms = buyerSms;
    }

    // Response fields
    //

    /**
     * Gets forced buyer selected transaction currency. Merchant pre-selects transaction currency on behalf of buyer.
     *
     * @return the forced buyer selected transaction currency
     */
    @JsonProperty("forcedBuyerSelectedTransactionCurrency")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getForcedBuyerSelectedTransactionCurrency() {
        return this.forcedBuyerSelectedTransactionCurrency;
    }

    /**
     * Sets forced buyer selected transaction currency. Merchant pre-selects transaction currency on behalf of buyer.
     *
     * @param forcedBuyerSelectedTransactionCurrency the forced buyer selected transaction currency
     */
    @JsonProperty("forcedBuyerSelectedTransactionCurrency")
    public void setForcedBuyerSelectedTransactionCurrency(final String forcedBuyerSelectedTransactionCurrency) {
        this.forcedBuyerSelectedTransactionCurrency = forcedBuyerSelectedTransactionCurrency;
    }

    /**
     * Gets Invoice resource id.
     *
     * @return the id
     */
    @JsonIgnore
    public String getId() {
        return this.id;
    }

    /**
     * Sets Invoice resource id.
     *
     * @param id the id
     */
    @JsonProperty("id")
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Gets web address of invoice, expires at expirationTime.
     *
     * @return the url
     */
    @JsonIgnore
    public String getUrl() {
        return this.url;
    }

    /**
     * Sets web address of invoice, expires at expirationTime.
     *
     * @param url the url
     */
    @JsonProperty("url")
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * Gets Invoice status. Invoice status can have the following values:
     * <ul>
     *     <li>"new": An invoice starts in this state. When in this state and only in this state,
     *     payments broadcasted by purchasers will be applied to the invoice
     *     (there is a 15 minute window for the purchaser to send a payment from their crypto wallet).
     *     If an invoice has received a partial payment, it will still reflect a status of new to the merchant.
     *     From a merchant system perspective, an invoice is either paid or not paid,
     *     partial payments are automatically refunded by BitPay to the consumer.
     *     </li>
     *     <li>"paid" As soon as payment is received it is evaluated against the invoice requested amount.
     *     If the amount paid is equal to or greater than the amount expected then the invoice is marked as being paid.
     *     To detect whether the invoice has been overpaid consult the invoice exception status
     *     (exceptionStatus parameter).
     *     The overpaid amount on an invoice is automatically refunded by BitPay to the consumer.
     *     </li>
     *     <li>"confirmed": This status can be used by merchants in order to fulfill orders placed by the consumer.
     *     Merchants can configure the timing at which BitPay sets this specific invoice status,
     *     depending on the number of confirmation achieved by the consumer's transaction in the selected
     *     cryptocurrency. This can be configured during invoice creation using the "transactionSpeed" parameter
     *     (section Create an invoice), or at account level via a dashboard setting.
     *     </li>
     *     <li>"complete": When an invoice has the status complete,
     *     it means that BitPay has credited the merchant account, in the currency indicated in the settlement settings.
     *     For instance, with invoices paid in Bitcoin (BTC), 6 confirmation blocks on the bitcoin network are required
     *     for an invoice to be complete, this takes on average 1 hour.
     *     </li>
     *     <li>"expired": An invoice reaches the expired status if no payment was received and the 15 minute payment
     *     window has elapsed.
     *     </li>
     *     <li>"invalid" An invoice is considered invalid when it was paid, but the corresponding cryptocurrency
     *     transaction was not confirmed within 1 hour on the corresponding blockchain. It is possible that some
     *     transactions can take longer than 1 hour to be included in a block. If the transaction confirms after 1 hour,
     *     BitPay will update the invoice state from "invalid" to "complete"
     *     (6 confirmations for transactions on thebitcoin network for instance).
     *     </li>
     * </ul>
     *
     * Detailed information about invoice status notifications can be found under the Instant Payment Notification
     * (IPN) section.
     *
     * @return the status
     * @see <a href="https://bitpay.readme.io/reference/notifications-invoices">Instant Payment Notification</a>
     */
    @JsonIgnore
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets Invoice status. Invoice status can have the following values:
     * <ul>
     *     <li>"new": An invoice starts in this state. When in this state and only in this state,
     *     payments broadcasted by purchasers will be applied to the invoice
     *     (there is a 15 minute window for the purchaser to send a payment from their crypto wallet).
     *     If an invoice has received a partial payment, it will still reflect a status of new to the merchant.
     *     From a merchant system perspective, an invoice is either paid or not paid,
     *     partial payments are automatically refunded by BitPay to the consumer.
     *     </li>
     *     <li>"paid" As soon as payment is received it is evaluated against the invoice requested amount.
     *     If the amount paid is equal to or greater than the amount expected then the invoice is marked as being paid.
     *     To detect whether the invoice has been overpaid consult the invoice exception status
     *     (exceptionStatus parameter).
     *     The overpaid amount on an invoice is automatically refunded by BitPay to the consumer.
     *     </li>
     *     <li>"confirmed": This status can be used by merchants in order to fulfill orders placed by the consumer.
     *     Merchants can configure the timing at which BitPay sets this specific invoice status,
     *     depending on the number of confirmation achieved by the consumer's transaction in the selected
     *     cryptocurrency. This can be configured during invoice creation using the "transactionSpeed" parameter
     *     (section Create an invoice), or at account level via a dashboard setting.
     *     </li>
     *     <li>"complete": When an invoice has the status complete,
     *     it means that BitPay has credited the merchant account, in the currency indicated in the settlement settings.
     *     For instance, with invoices paid in Bitcoin (BTC), 6 confirmation blocks on the bitcoin network are required
     *     for an invoice to be complete, this takes on average 1 hour.
     *     </li>
     *     <li>"expired": An invoice reaches the expired status if no payment was received and the 15 minute payment
     *     window has elapsed.
     *     </li>
     *     <li>"invalid" An invoice is considered invalid when it was paid, but the corresponding cryptocurrency
     *     transaction was not confirmed within 1 hour on the corresponding blockchain. It is possible that some
     *     transactions can take longer than 1 hour to be included in a block. If the transaction confirms after 1 hour,
     *     BitPay will update the invoice state from "invalid" to "complete"
     *     (6 confirmations for transactions on thebitcoin network for instance).
     *     </li>
     * </ul>
     *
     * Detailed information about invoice status notifications can be found under the Instant Payment Notification
     * (IPN) section.
     *
     * @param status the status
     * @see <a href="https://bitpay.readme.io/reference/notifications-invoices">Instant Payment Notification</a>
     */
    @JsonProperty("status")
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Gets low fee detected. Flag to indicate if the miner fee used by the buyer is too low.
     * Initially set to false when the invoice is created.
     *
     * @return the low fee detected
     */
    @JsonIgnore
    public Boolean getLowFeeDetected() {
        return this.lowFeeDetected;
    }

    /**
     * Sets low fee detected. Flag to indicate if the miner fee used by the buyer is too low.
     * Initially set to false when the invoice is created.
     *
     * @param lowFeeDetected the low fee detected
     */
    @JsonProperty("lowFeeDetected")
    public void setLowFeeDetected(final Boolean lowFeeDetected) {
        this.lowFeeDetected = lowFeeDetected;
    }

    /**
     * Gets invoice time. UNIX time of invoice creation, in milliseconds.
     *
     * @return the invoice time
     */
    @JsonIgnore
    public Long getInvoiceTime() {
        return this.invoiceTime;
    }

    /**
     * Sets invoice time. UNIX time of invoice creation, in milliseconds.
     *
     * @param invoiceTime the invoice time
     */
    @JsonProperty("invoiceTime")
    public void setInvoiceTime(final Long invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    /**
     * Gets UNIX time when invoice is last available to be paid, in milliseconds.
     *
     * @return the expiration time
     */
    @JsonIgnore
    public Long getExpirationTime() {
        return this.expirationTime;
    }

    /**
     * Sets UNIX time when invoice is last available to be paid, in milliseconds.
     *
     * @param expirationTime the expiration time
     */
    @JsonProperty("expirationTime")
    public void setExpirationTime(final Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    /**
     * Gets UNIX time of API call, in milliseconds.
     *
     * @return the current time
     */
    @JsonIgnore
    public Long getCurrentTime() {
        return this.currentTime;
    }

    /**
     * Sets UNIX time of API call, in milliseconds.
     *
     * @param currentTime the current time
     */
    @JsonProperty("currentTime")
    public void setCurrentTime(final Long currentTime) {
        this.currentTime = currentTime;
    }

    /**
     * Gets transactions. Initially empty when the invoice is created.
     * This array will be populated with the crypto currency transaction hashes linked to the invoice.
     * For instance the consumer's transaction hash if the invoice is paid,
     * but also the refund transaction hash if the merchant decide to issue a refund to the purchaser.
     *
     * @return the transactions
     */
    @JsonIgnore
    public List<InvoiceTransaction> getTransactions() {
        return this.transactions;
    }

    /**
     * Sets transactions. This array will be populated with the cryptocurrency transaction hashes linked to the invoice.
     * For instance the consumer's transaction hash if the invoice is paid,
     * but also the refund transaction hash if the merchant decide to issue a refund to the purchaser.
     *
     * @param transactions the transactions
     */
    @JsonProperty("transactions")
    public void setTransactions(final List<InvoiceTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Gets exception status. Initially a boolean false, this parameter will indicate if the purchaser sent too much
     * ("paidOver") or not enough funds ("paidPartial") in the transaction to pay the BitPay invoice.
     * Possible values are:
     * <ul>
     *     <li>"false": default value (boolean) unless an exception is triggered.</li>
     *     <li>"paidPartial": (string) if the consumer did not send enough funds when paying the invoice.</li>
     *     <li>"paidOver": (string) if the consumer sent to much funds when paying the invoice.</li>
     * </ul>
     *
     * @return the exception status
     */
    @JsonIgnore
    public String getExceptionStatus() {
        return this.exceptionStatus;
    }

    /**
     * Sets exception status. This parameter will indicate if the purchaser sent too much
     * ("paidOver") or not enough funds ("paidPartial") in the transaction to pay the BitPay invoice.
     * Possible values are:
     * <ul>
     *     <li>false: default value (boolean) unless an exception is triggered.</li>
     *     <li>"paidPartial": (string) if the consumer did not send enough funds when paying the invoice.</li>
     *     <li>"paidOver": (string) if the consumer sent to much funds when paying the invoice.</li>
     * </ul>
     *
     * @param exceptionStatus the exception status
     */
    @JsonProperty("exceptionStatus")
    public void setExceptionStatus(final String exceptionStatus) {
        this.exceptionStatus = exceptionStatus;
    }

    /**
     * Gets target confirmations. Indicates the number of block confirmation of the crypto currency transaction which
     * are required to credit a paid invoice to the merchant accoun.
     * Currently the value set is set to 6 by default for BTC/BCH/XRP, 40 for DOGE and 50 for
     * ETH/GUSD/PAX/USDC/BUSD/DAI/WBTC.
     *
     * @return the target confirmations
     */
    @JsonIgnore
    public Integer getTargetConfirmations() {
        return this.targetConfirmations;
    }

    /**
     * Sets target confirmations. Indicates the number of block confirmation of the crypto currency transaction which
     * are required to credit a paid invoice to the merchant accoun.
     * Currently the value set is set to 6 by default for BTC/BCH/XRP, 40 for DOGE and 50 for
     * ETH/GUSD/PAX/USDC/BUSD/DAI/WBTC.
     *
     * @param targetConfirmations the target confirmations
     */
    @JsonProperty("targetConfirmations")
    public void setTargetConfirmations(final Integer targetConfirmations) {
        this.targetConfirmations = targetConfirmations;
    }

    /**
     * Gets refund addresses. Initially empty when the invoice is created.
     * This field will be populated with the refund address provided by the customer
     * if you request a refund of the specific invoice.
     *
     * @return the refund addresses
     */
    @JsonIgnore
    public List<Map<String, InvoiceRefundAddresses>> getRefundAddresses() {
        return this.refundAddresses;
    }

    /**
     * Sets refund addresses. This field will be populated with the refund address provided by the customer
     * if you request a refund of the specific invoice.
     *
     * @param refundAddresses the refund addresses
     */
    @JsonProperty("refundAddresses")
    public void setRefundAddresses(final List<Map<String, InvoiceRefundAddresses>> refundAddresses) {
        this.refundAddresses = refundAddresses;
    }

    /**
     * Gets refund address request pending. Initially set to false when the invoice is created,
     * this field will be set to true once a refund request has been issued by the merchant.
     * This flag is here to indicate that the refund request is pending action from the buyer to provide an address
     * for the refund, via the secure link which has been automatically emailed to him.
     *
     * @return the refund address request pending
     */
    @JsonIgnore
    public Boolean getRefundAddressRequestPending() {
        return this.refundAddressRequestPending;
    }

    /**
     * Sets refund address request pending. Initially set to false when the invoice is created,
     * this field will be set to true once a refund request has been issued by the merchant.
     * This flag is here to indicate that the refund request is pending action from the buyer to provide an address
     * for the refund, via the secure link which has been automatically emailed to him.
     *
     * @param refundAddressRequestPending the refund address request pending
     */
    @JsonProperty("refundAddressRequestPending")
    public void setRefundAddressRequestPending(final Boolean refundAddressRequestPending) {
        this.refundAddressRequestPending = refundAddressRequestPending;
    }

    /**
     * Gets buyer provided email.
     * Populated with the buyer's email address if passed in the buyer object by the merchant,
     * otherwise this field is not returned for newly created invoices.
     * If the merchant does not pass the buyer email in the invoice request,
     * the bitpay invoice UI will prompt the user to enter his email address and this field will be populated
     * with the email submitted.
     *
     * @return the buyer provided email
     */
    @JsonIgnore
    public String getBuyerProvidedEmail() {
        return this.buyerProvidedEmail;
    }

    /**
     * Sets buyer provided email.
     * Populated with the buyer's email address if passed in the buyer object by the merchant,
     * otherwise this field is not returned for newly created invoices.
     * with the email submitted.
     *
     * @param buyerProvidedEmail the buyer provided email
     */
    @JsonProperty("buyerProvidedEmail")
    public void setBuyerProvidedEmail(final String buyerProvidedEmail) {
        this.buyerProvidedEmail = buyerProvidedEmail;
    }

    /**
     * Gets information collected from the buyer during the process of paying an invoice.
     * Initially this object is empty.
     *
     * @return the invoice buyer provided info
     */
    @JsonIgnore
    public InvoiceBuyerProvidedInfo getInvoiceBuyerProvidedInfo() {
        return this.invoiceBuyerProvidedInfo;
    }

    /**
     * Sets information collected from the buyer during the process of paying an invoice.
     *
     * @param invoiceBuyerProvidedInfo the invoice buyer provided info
     */
    @JsonProperty("buyerProvidedInfo")
    public void setInvoiceBuyerProvidedInfo(final InvoiceBuyerProvidedInfo invoiceBuyerProvidedInfo) {
        this.invoiceBuyerProvidedInfo = invoiceBuyerProvidedInfo;
    }

    /**
     * Gets supported transaction currencies. This field will be populated with the cryptocurrency selected to pay
     * the BitPay invoice, current supported values are "BTC", "BCH", "ETH", "GUSD", "PAX", "BUSD", "USDC", "XRP",
     * "DOGE", "DAI" and "WBTC". If not yet selected, this field will not be returned.
     *
     * @return the supported transaction currencies
     */
    @JsonIgnore
    public SupportedTransactionCurrencies getSupportedTransactionCurrencies() {
        return this.supportedTransactionCurrencies;
    }

    /**
     * Sets supported transaction currencies. This field will be populated with the cryptocurrency selected to pay
     * the BitPay invoice, current supported values are "BTC", "BCH", "ETH", "GUSD", "PAX", "BUSD", "USDC", "XRP",
     * "DOGE", "DAI" and "WBTC". If not yet selected, this field will not be returned.
     *
     * @param supportedTransactionCurrencies the supported transaction currencies
     */
    @JsonProperty("supportedTransactionCurrencies")
    public void setSupportedTransactionCurrencies(final SupportedTransactionCurrencies supportedTransactionCurrencies) {
        this.supportedTransactionCurrencies = supportedTransactionCurrencies;
    }

    /**
     * Gets the total amount of fees that the purchaser will pay to cover BitPay's UTXO sweep cost for an invoice.
     * The key is the currency and the value is an object containing the satoshis per byte, the total fee,
     * and the fiat amount.
     * This is referenced as "Network Cost" on an invoice, see this support article for more information.
     *
     * @return the miner fees
     */
    @JsonIgnore
    public MinerFees getMinerFees() {
        return this.minerFees;
    }

    /**
     * Sets the total amount of fees that the purchaser will pay to cover BitPay's UTXO sweep cost for an invoice.
     * The key is the currency and the value is an object containing the satoshis per byte, the total fee,
     * and the fiat amount.
     * This is referenced as "Network Cost" on an invoice, see this support article for more information.
     *
     * @param minerFees the miner fees
     */
    @JsonProperty("minerFees")
    public void setMinerFees(final MinerFees minerFees) {
        this.minerFees = minerFees;
    }

    /**
     * Gets shopper. This object will be available on the invoice if a shopper signs in on an invoice using
     * his BitPay ID. See the following blogpost for more information.
     *
     * @return the shopper
     * @see <a href="https://blog.bitpay.com/bitpay-dashboard-id/">Blogpost</a>
     */
    @JsonIgnore
    public Shopper getShopper() {
        return this.shopper;
    }

    /**
     * Gets shopper. This object will be available on the invoice if a shopper signs in on an invoice using
     * his BitPay ID. See the following blogpost for more information.
     *
     * @param shopper the shopper
     * @see <a href="https://blog.bitpay.com/bitpay-dashboard-id/">Blogpost</a>
     */
    @JsonProperty("shopper")
    public void setShopper(final Shopper shopper) {
        this.shopper = shopper;
    }

    /**
     * Gets bill id. This field will be in the invoice object only if the invoice was generated from a bill,
     * see the Bills resource for more information.
     *
     * @return the bill id
     * @see <a href="https://bitpay.readme.io/reference/bills">Bills resource</a>
     */
    @JsonIgnore
    public String getBillId() {
        return this.billId;
    }

    /**
     * Sets bill id. This field will be in the invoice object only if the invoice was generated from a bill,
     * see the Bills resource for more information
     *
     * @param billId the bill id
     * @see <a href="https://bitpay.readme.io/reference/bills">Bills resource</a>
     */
    @JsonProperty("billId")
    public void setBillId(final String billId) {
        this.billId = billId;
    }

    /**
     * Gets refund info. For a refunded invoice, this object will contain the details of executed refunds for
     * the corresponding invoice.
     *
     * @return the refund info
     */
    @JsonIgnore
    public ArrayList<RefundInfo> getRefundInfo() {
        return this.refundInfo;
    }

    /**
     * Sets refund info. For a refunded invoice, this object will contain the details of executed refunds
     * for the corresponding invoice.
     *
     * @param refundInfo the refund info
     */
    @JsonProperty("refundInfo")
    public void setRefundInfo(final ArrayList<RefundInfo> refundInfo) {
        this.refundInfo = refundInfo;
    }

    /**
     * Gets transaction currency. The cryptocurrency used to pay the invoice. This field will only be available after
     * a transaction is applied to the invoice. Possible values are currently "BTC", "BCH", "ETH", "GUSD", "PAX",
     * "BUSD", "USDC", "XRP", "DOGE", "DAI" and "WBTC".
     *
     * @return the transaction currency
     */
    @JsonIgnore
    public String getTransactionCurrency() {
        return this.transactionCurrency;
    }

    /**
     * Sets transaction currency. The cryptocurrency used to pay the invoice. This field will only be available after
     * a transaction is applied to the invoice. Possible values are currently "BTC", "BCH", "ETH", "GUSD", "PAX",
     * "BUSD", "USDC", "XRP", "DOGE", "DAI" and "WBTC".
     *
     * @param transactionCurrency the transaction currency
     */
    @JsonProperty("transactionCurrency")
    public void setTransactionCurrency(final String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    /**
     * Gets amount paid. Initially set to 0 when creating the invoice.
     * It will be updated with the total amount paid to the invoice, indicated in the smallest possible unit for the
     * corresponding transactionCurrency.
     *
     * @return the amount paid
     */
    @JsonIgnore
    public BigDecimal getAmountPaid() {
        return this.amountPaid;
    }

    /**
     * Sets amount paid. It will be updated with the total amount paid to the invoice,
     * indicated in the smallest possible unit for the corresponding transactionCurrency.
     *
     * @param amountPaid the amount paid
     */
    @JsonProperty("amountPaid")
    public void setAmountPaid(final BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    /**
     * Gets display amount paid. Initially set to "0" when creating the invoice.
     * It will be updated with the total amount paid to the invoice indicated in the base unit for the corresponding
     * transactionCurrency.
     *
     * @return the display amount paid
     */
    @JsonIgnore
    public String getDisplayAmountPaid() {
        return this.displayAmountPaid;
    }

    /**
     * Sets display amount paid. Initially set to "0" when creating the invoice. It will be updated with the total
     * amount paid to the invoice indicated in the base unit for the corresponding transactionCurrency.
     *
     * @param displayAmountPaid the display amount paid
     */
    @JsonProperty("displayAmountPaid")
    public void setDisplayAmountPaid(final String displayAmountPaid) {
        this.displayAmountPaid = displayAmountPaid;
    }

    /**
     * Gets exchange rates keyed by source and target currencies.
     *
     * @return the exchange rates
     */
    @JsonIgnore
    public Hashtable<String, Hashtable<String, BigDecimal>> getExchangeRates() {
        return this.exchangeRates;
    }

    /**
     * Sets exchange rates keyed by source and target currencies.
     *
     * @param exchangeRates the exchange rates
     */
    @JsonProperty("exchangeRates")
    public void setExchangeRates(final Hashtable<String, Hashtable<String, BigDecimal>> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    /**
     * Gets is cancelled. Indicates whether or not the invoice was cancelled.
     *
     * @return the is cancelled
     */
    @JsonIgnore
    public Boolean getIsCancelled() {
        return this.isCancelled;
    }

    /**
     * Sets is cancelled. Indicates whether or not the invoice was cancelled.
     *
     * @param isCancelled the is cancelled
     */
    @JsonProperty("isCancelled")
    public void setIsCancelled(final Boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    /**
     * Gets payment sub totals. For internal use. This field can be ignored in merchant implementations.
     *
     * @return the payment sub totals
     */
    @JsonIgnore
    public Hashtable<String, BigInteger> getPaymentSubTotals() {
        return this.paymentSubtotals;
    }

    /**
     * Sets payment sub totals. For internal use. This field can be ignored in merchant implementations.
     *
     * @param paymentSubtotals the payment subtotals
     */
    @JsonProperty("paymentSubtotals")
    public void setPaymentSubTotals(final Hashtable<String, BigInteger> paymentSubtotals) {
        this.paymentSubtotals = paymentSubtotals;
    }

    /**
     * Gets payment totals. For internal use - This field can be ignored in merchant implementations.
     *
     * @return the payment totals
     */
    @JsonIgnore
    public Hashtable<String, BigInteger> getPaymentTotals() {
        return this.paymentTotals;
    }

    /**
     * Sets payment totals. For internal use - This field can be ignored in merchant implementations.
     *
     * @param paymentTotals the payment totals
     */
    @JsonProperty("paymentTotals")
    public void setPaymentTotals(final Hashtable<String, BigInteger> paymentTotals) {
        this.paymentTotals = paymentTotals;
    }

    /**
     * Gets the total amount that the purchaser should pay as displayed on the invoice UI.
     * This is like paymentDisplaySubTotals but with the minerFees included.
     * The key is the currency and the value is an amount indicated in the base unit
     * for each supported transactionCurrency.
     *
     * @return the payment display totals
     */
    @JsonIgnore
    public Hashtable<String, String> getPaymentDisplayTotals() {
        return this.paymentDisplayTotals;
    }

    /**
     * Sets the total amount that the purchaser should pay as displayed on the invoice UI.
     * This is like paymentDisplaySubTotals but with the minerFees included.
     * The key is the currency and the value is an amount indicated in the base unit
     * for each supported transactionCurrency.
     *
     * @param paymentDisplayTotals the payment display totals
     */
    @JsonProperty("paymentDisplayTotals")
    public void setPaymentDisplayTotals(final Hashtable<String, String> paymentDisplayTotals) {
        this.paymentDisplayTotals = paymentDisplayTotals;
    }

    /**
     * Gets equivalent to price for each supported transactionCurrency, excluding minerFees.
     * The key is the currency and the value is an amount indicated in the base unit
     * for each supported transactionCurrency.
     *
     * @return the payment display sub totals
     */
    @JsonIgnore
    public Hashtable<String, String> getPaymentDisplaySubTotals() {
        return this.paymentDisplaySubTotals;
    }

    /**
     * Sets equivalent to price for each supported transactionCurrency, excluding minerFees.
     * The key is the currency and the value is an amount indicated in the base unit
     * for each supported transactionCurrency.
     *
     * @param paymentDisplaySubTotals the payment display sub totals
     */
    @JsonProperty("paymentDisplaySubTotals")
    public void setPaymentDisplaySubTotals(final Hashtable<String, String> paymentDisplaySubTotals) {
        this.paymentDisplaySubTotals = paymentDisplaySubTotals;
    }

    /**
     * Gets non pay pro payment received. This boolean will be available on an invoice object once an invoice
     * is paid and indicate if the transaction was made with a wallet using the payment protocol (true)
     * or peer to peer (false).
     *
     * @return the non pay pro payment received
     */
    @JsonIgnore
    public Boolean getNonPayProPaymentReceived() {
        return this.nonPayProPaymentReceived;
    }

    /**
     * Sets non pay pro payment received. This boolean will be available on an invoice object once an invoice
     * is paid and indicate if the transaction was made with a wallet using the payment protocol (true)
     * or peer to peer (false).
     *
     * @param nonPayProPaymentReceived the non pay pro payment received
     */
    @JsonProperty("nonPayProPaymentReceived")
    public void setNonPayProPaymentReceived(final Boolean nonPayProPaymentReceived) {
        this.nonPayProPaymentReceived = nonPayProPaymentReceived;
    }

    /**
     * Gets json pay pro required. Boolean set to false by default.
     * If set to true, this means that the invoice will only accept payments from wallets
     * which have implemented the BitPay JSON Payment Protocol.
     *
     * @return the json pay pro required
     * @see <a href="https://bitpay.com/docs/payment-protocol">BitPay JSON Payment Protocol</a>
     */
    @JsonIgnore
    public Boolean getJsonPayProRequired() {
        return this.jsonPayProRequired;
    }

    /**
     * Sets json pay pro required. If set to true, this means that the invoice will only accept payments from wallets
     * which have implemented the BitPay JSON Payment Protocol.
     *
     * @param jsonPayProRequired the json pay pro required
     * @see <a href="https://bitpay.com/docs/payment-protocol">BitPay JSON Payment Protocol</a>
     */
    @JsonProperty("jsonPayProRequired")
    public void setJsonPayProRequired(final Boolean jsonPayProRequired) {
        this.jsonPayProRequired = jsonPayProRequired;
    }

    /**
     * Gets under paid amount. This parameter will be returned on the invoice object if the invoice was underpaid
     * ("exceptionStatus": "paidPartial").
     * It equals to the absolute difference between amountPaid and paymentTotals for the corresponding
     * transactionCurrency used.
     *
     * @return the under paid amount
     */
    @JsonIgnore
    public BigDecimal getUnderPaidAmount() {
        return this.underpaidAmount;
    }

    /**
     * Sets under paid amount. This parameter will be returned on the invoice object if the invoice was underpaid
     * ("exceptionStatus": "paidPartial").
     * It equals to the absolute difference between amountPaid and paymentTotals for the corresponding
     * transactionCurrency used.
     *
     * @param underpaidAmount the underpaid amount
     */
    @JsonProperty("underpaidAmount")
    public void setUnderPaidAmount(final BigDecimal underpaidAmount) {
        this.underpaidAmount = underpaidAmount;
    }

    /**
     * Gets over paid amount. This parameter will be returned on the invoice object if the invoice was overpaid
     * ("exceptionStatus": "paidOver").
     * It equals to the absolute difference between amountPaid and paymentTotals for the corresponding
     * transactionCurrency used.
     *
     * @return the over paid amount
     */
    @JsonIgnore
    public BigDecimal getOverPaidAmount() {
        return this.overpaidAmount;
    }

    /**
     * Sets over paid amount. This parameter will be returned on the invoice object if the invoice was overpaid
     * ("exceptionStatus": "paidOver").
     * It equals to the absolute difference between amountPaid and paymentTotals for the corresponding
     * transactionCurrency used.
     *
     * @param overpaidAmount the overpaid amount
     */
    @JsonProperty("overpaidAmount")
    public void setOverPaidAmount(final BigDecimal overpaidAmount) {
        this.overpaidAmount = overpaidAmount;
    }

    /**
     * Gets payment codes. The URIs for sending a transaction to the invoice.
     * The first key is the transaction currency.
     * The transaction currency maps to an object containing the payment URIs.
     * The key of this object is the BIP number and the value is the payment URI.
     * <ul>
     *     <li>For "BTC", "BCH" and "DOGE" - BIP72b and BIP73 are supported.</li>
     *     <li>For "ETH", "GUSD", "PAX", "BUSD", "USDC", "DAI" and "WBTC"- EIP681 is supported</li>
     *     <li>For "XRP" - RIP681, BIP72b and BIP73 is supported</li>
     * </ul>
     *
     * @return the payment codes
     */
    @JsonIgnore
    public Hashtable<String, Hashtable<String, String>> getPaymentCodes() {
        return this.paymentCodes;
    }

    /**
     * Sets payment codes. The URIs for sending a transaction to the invoice.
     * The first key is the transaction currency.
     * The transaction currency maps to an object containing the payment URIs.
     * The key of this object is the BIP number and the value is the payment URI.
     * <ul>
     *     <li>For "BTC", "BCH" and "DOGE" - BIP72b and BIP73 are supported.</li>
     *     <li>For "ETH", "GUSD", "PAX", "BUSD", "USDC", "DAI" and "WBTC"- EIP681 is supported</li>
     *     <li>For "XRP" - RIP681, BIP72b and BIP73 is supported</li>
     * </ul>
     *
     * @param paymentCodes the payment codes
     */
    @JsonProperty("paymentCodes")
    public void setPaymentCodes(final Hashtable<String, Hashtable<String, String>> paymentCodes) {
        this.paymentCodes = paymentCodes;
    }
}
