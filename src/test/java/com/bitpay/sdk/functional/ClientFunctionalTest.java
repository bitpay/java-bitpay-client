/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.functional;

import com.bitpay.sdk.Client;
import com.bitpay.sdk.ConfigFilePath;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.Currency;
import com.bitpay.sdk.model.bill.Bill;
import com.bitpay.sdk.model.bill.Item;
import com.bitpay.sdk.model.invoice.Buyer;
import com.bitpay.sdk.model.invoice.Invoice;
import com.bitpay.sdk.model.invoice.InvoiceEventToken;
import com.bitpay.sdk.model.invoice.Refund;
import com.bitpay.sdk.model.ledger.Ledger;
import com.bitpay.sdk.model.ledger.LedgerEntry;
import com.bitpay.sdk.model.payout.Payout;
import com.bitpay.sdk.model.payout.PayoutGroup;
import com.bitpay.sdk.model.payout.PayoutRecipient;
import com.bitpay.sdk.model.payout.PayoutRecipients;
import com.bitpay.sdk.model.rate.Rate;
import com.bitpay.sdk.model.rate.Rates;
import com.bitpay.sdk.model.wallet.Wallet;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClientFunctionalTest {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final LocalDateTime yesterday;
    private final LocalDateTime tomorrow;
    private final LocalDateTime monthAgo;
    private final Client client;

    /**
     * Before use these tests you have to generate BitPay.config.json by BitPaySetup and put this file
     * into this directory.
     * You should create recipient in test.bitpay.com/dashboard/payouts/recipients and put this email
     * to "email.txt" file in this directory. It's required for submit requests.
     * It's impossible to test settlements in test environment.
     */
    public ClientFunctionalTest() throws BitPayException {
        String path = getPathExistingClass() + "BitPay.config.json";
        this.client = new Client(new ConfigFilePath(path), null, null);
        this.yesterday = LocalDateTime.now().minusDays(1);
        this.tomorrow = LocalDateTime.now().plusDays(1);
        this.monthAgo = LocalDateTime.now().minusDays(30);
    }

    /**
     * Tested rates requests:
     * - GetRate(string baseCurrency, string currency)
     * - GetRates()
     * - GetRates(string currency)
     */
    @Test
    public void it_should_test_rate_requests() throws BitPayGenericException, BitPayApiException {
        Rate rate = this.client.getRate(Currency.BCH, Currency.USD);
        Assertions.assertTrue(rate.getValue() != 0);

        Rates rates = this.client.getRates();
        Assertions.assertTrue(rates.getRate(Currency.USD) != 0);

        Rates rateUsd = this.client.getRates(Currency.BTC);
        Assertions.assertTrue(rateUsd.getRate(Currency.BCH) != 0);
    }

    /**
     *     Tested wallet requests:
     *     - GetSupportedWallets()
     */
    @Test
    public void it_should_test_wallet_requests() throws BitPayException {
        List<Wallet> supportedWallets = this.client.getSupportedWallets();
        Assertions.assertFalse(supportedWallets.isEmpty());
    }

    /**
     * Tested currency requests:
     * - GetCurrencyInfo(string currencyCode)
     */
    @Test
    public void it_should_test_currency_requests() throws BitPayException {
        Map<String, Object> getCurrencyInfo = this.client.getCurrencyInfo("BTC");
        Assertions.assertEquals("Bitcoin", getCurrencyInfo.get("name"));
    }

    /**
     * Tested invoice requests:
     * - CreateInvoice
     * - GetInvoice
     * - GetInvoiceByGuid
     * - GetInvoices
     * - UpdateInvoice
     * - CancelInvoice
     * - GetInvoiceEventToken
     * <p>
     * Not tested requests:
     * - RequestInvoiceWebhookToBeResent
     */
    @Test
    public void it_should_test_invoice_requests() throws BitPayException {
        Invoice invoice = this.client.createInvoice(getExampledInvoice());
        String invoiceToken = invoice.getToken();
        String invoiceId = invoice.getId();

        Invoice invoiceGet = this.client.getInvoice(invoiceId);
        Assertions.assertEquals(invoiceToken, invoiceGet.getToken());

        Invoice invoiceGetByGuid = this.client.getInvoiceByGuid(invoice.getGuid());
        Assertions.assertEquals(invoiceToken, invoiceGetByGuid.getToken());

        List<Invoice> invoices = this.client.getInvoices(
            this.yesterday.format(dateFormatter),
            this.tomorrow.format(dateFormatter),
            null,
            null,
            null,
            null
        );
        Assertions.assertFalse(invoices.isEmpty());
        Assertions.assertTrue(invoices.stream().anyMatch(i -> i.getToken().equals(invoiceToken)));

        InvoiceEventToken getInvoiceEventToken = this.client.getInvoiceEventToken(invoiceId);
        Assertions.assertNotNull(getInvoiceEventToken.getToken());

        String updatedEmail = "updated@email.com";
        Invoice updatedInvoice = this.client.updateInvoice(invoiceId, null, null, updatedEmail, null);
        Assertions.assertEquals(updatedEmail, updatedInvoice.getBuyerProvidedEmail());
        Invoice invoiceGetAfterUpdate = this.client.getInvoice(invoiceId);
        Assertions.assertEquals(updatedEmail, invoiceGetAfterUpdate.getBuyerProvidedEmail());

        Invoice cancelInvoice = this.client.cancelInvoice(invoiceId);
        Assertions.assertTrue(cancelInvoice.getIsCancelled());

        Invoice invoiceToCancelByGuid = this.client.createInvoice(getExampledInvoice());
        Invoice cancelInvoiceByGuid = this.client.cancelInvoiceByGuid(invoiceToCancelByGuid.getGuid());
        Assertions.assertTrue(cancelInvoiceByGuid.getIsCancelled());
    }

    /**
     * Tested refund requests:
     * <p>
     * - CreateRefund(Refund refundToCreate)
     * - GetRefund(string refundId)
     * - GetRefundByGuid(string guid)
     * - GetRefunds(string invoiceId)
     * - SendRefundNotification(string refundId)
     * - CancelRefund(string refundId)
     * - CancelRefundByGuid(string guid)
     * <p>
     * Not tested refund requests:
     * - UpdateRefund(string refundId, string status) / preview status limitation
     * - UpdateRefundByGuid(string guid, string status) / preview status limitation
     */
    @Test
    public void it_should_test_refunds_requests() throws BitPayException {
        Invoice invoice = this.client.createInvoice(getExampledInvoice());
        String invoiceId = invoice.getId();
        this.client.payInvoice(invoiceId, "complete");

        Refund refundToCreateRequest = new Refund();
        refundToCreateRequest.setInvoice(invoiceId);
        refundToCreateRequest.setAmount(10.0);
        Refund refund = this.client.createRefund(refundToCreateRequest);
        String refundId = refund.getId();
        String refundToken = refund.getToken();

        Refund retrieveRefund = this.client.getRefund(refundId);
        Assertions.assertEquals(refundId, retrieveRefund.getId());
        Assertions.assertNotNull(retrieveRefund.getInvoice());

        Refund retrieveRefundByGuid = this.client.getRefundByGuid(refund.getGuid());
        Assertions.assertEquals(refundId, retrieveRefundByGuid.getId());

        List<Refund> retrieveRefundByInvoiceId = this.client.getRefunds(invoiceId);
        Assertions.assertFalse(retrieveRefundByInvoiceId.isEmpty());
        Assertions.assertTrue(retrieveRefundByInvoiceId.stream().anyMatch(i -> i.getInvoice().equals(invoiceId)));

        Boolean refundNotification = this.client.sendRefundNotification(refundId, refundToken);
        Assertions.assertTrue(refundNotification);

        Refund cancelRefund = this.client.cancelRefund(refundId);
        Assertions.assertEquals("canceled", cancelRefund.getStatus());
        Refund retrieveRefundAfterCanceled = this.client.getRefund(refundId);
        Assertions.assertEquals("canceled", retrieveRefundAfterCanceled.getStatus());

        Refund refundToCreateForCancelByGuid = new Refund();
        refundToCreateForCancelByGuid.setInvoice(invoiceId);
        refundToCreateForCancelByGuid.setAmount(10.0);
        Refund refundToCancelByGuid = this.client.createRefund(refundToCreateForCancelByGuid);
        Refund refundCanceledByGuid = this.client.cancelRefundByGuid(refundToCancelByGuid.getGuid());
        Assertions.assertEquals("canceled", refundCanceledByGuid.getStatus());
    }

    /**
     * Tested recipient requests:
     * - SubmitPayoutRecipients(PayoutRecipients recipients)
     * - GetPayoutRecipient(string recipientId)
     * - GetPayoutRecipients(string status, int limit, int offset)
     * - UpdatePayoutRecipient(string recipientId, PayoutRecipient recipient)
     * - DeletePayoutRecipient(string recipientId)
     * - RequestPayoutRecipientNotification(string recipientId)
     */
    @Test
    public void it_should_test_recipients_requests() throws BitPayException {
        String email = "bob@email.com";
        PayoutRecipient requestedRecipient = new PayoutRecipient(email, "Bob", null);
        List<PayoutRecipient> requestedRecipients = Collections.singletonList(requestedRecipient);

        List<PayoutRecipient> recipients =
            this.client.submitPayoutRecipients(new PayoutRecipients(requestedRecipients));
        String recipientId = recipients.get(0).getId();

        PayoutRecipient retrieveRecipient = this.client.getPayoutRecipient(recipientId);
        Assertions.assertEquals(email, retrieveRecipient.getEmail());

        List<PayoutRecipient> retrieveRecipientsByStatus = this.client.getPayoutRecipients("invited", 1, 0);
        Assertions.assertFalse(retrieveRecipientsByStatus.isEmpty());

        String updatedLabel = "updatedLabel";
        PayoutRecipient updateRecipientRequest = new PayoutRecipient();
        updateRecipientRequest.setLabel(updatedLabel);
        PayoutRecipient updateRecipient = this.client.updatePayoutRecipient(recipientId, updateRecipientRequest);
        Assertions.assertEquals(updatedLabel, updateRecipient.getLabel());

        Boolean requestPayoutRecipientNotification = this.client.requestPayoutRecipientNotification(recipientId);
        Assertions.assertTrue(requestPayoutRecipientNotification);

        Boolean removeRecipient = this.client.deletePayoutRecipient(recipientId);
        Assertions.assertTrue(removeRecipient);
    }

    /**
     * You need to have recipient before
     * <p>
     * Tested payout requests:
     * - SubmitPayout(Payout payout)
     * - GetPayout(string payoutId)
     * - CancelPayout(string payoutId)
     * - GetPayouts(Dictionary<string, dynamic> filters)
     * - RequestPayoutNotification(string payoutId)
     */
    @Test
    public void it_should_test_payout_requests() throws BitPayException {
        String email = getEmail();
        PayoutRecipient requestedRecipient = new PayoutRecipient(email, "Bob", null);
        List<PayoutRecipient> requestedRecipients = Collections.singletonList(requestedRecipient);

        List<PayoutRecipient> recipients =
            this.client.submitPayoutRecipients(new PayoutRecipients(requestedRecipients));
        String recipientId = recipients.get(0).getId();

        Payout payout = getPayout(email, recipientId);

        Payout submitPayout = this.client.submitPayout(payout);
        String payoutId = submitPayout.getId();
        Assertions.assertNotNull(payoutId);
        Assertions.assertEquals(email, submitPayout.getNotificationEmail());

        Payout getPayoutById = this.client.getPayout(payoutId);
        Assertions.assertEquals(email, getPayoutById.getNotificationEmail());
        List<Payout> getPayouts = this.client.getPayouts(
            this.monthAgo.format(this.dateFormatter),
            this.tomorrow.format(this.dateFormatter),
            null,
            null,
            null,
            null,
            null
        );
        Assertions.assertFalse(getPayouts.isEmpty());
        Assertions.assertTrue(getPayouts.stream().anyMatch(p -> p.getNotificationEmail().equals(email)));

        Boolean requestPayoutNotification = this.client.requestPayoutNotification(payoutId);
        Assertions.assertTrue(requestPayoutNotification);

        Boolean cancelledPayout = this.client.cancelPayout(payoutId);
        Assertions.assertTrue(cancelledPayout);
    }

    /**
     * You need to have recipient before
     * <p>
     * Tested payout requests:
     * - SubmitPayouts(Collection<Payout> payouts)
     * - GetPayout(string payoutId)
     * - CancelPayouts(string groupId)
     * - GetPayouts(Dictionary<string, dynamic> filters)
     * - RequestPayoutNotification(string payoutId)
     */
    @Test
    public void it_should_test_payout_group_requests() throws BitPayException {
        String email = getEmail();
        PayoutRecipient requestedRecipient = new PayoutRecipient(email, "Bob", null);
        List<PayoutRecipient> requestedRecipients = Collections.singletonList(requestedRecipient);

        List<PayoutRecipient> recipients =
            this.client.submitPayoutRecipients(new PayoutRecipients(requestedRecipients));
        String recipientId = recipients.get(0).getId();

        Collection<Payout> payouts = Arrays.asList(getPayout(email, recipientId), getPayout(email, recipientId));

        PayoutGroup submitPayoutGroup = this.client.submitPayouts(payouts);
        Payout firstSubmittedPayout = submitPayoutGroup.getPayouts().get(0);
        String payoutId = firstSubmittedPayout.getId();
        Assertions.assertNotNull(payoutId);
        Assertions.assertEquals(email, firstSubmittedPayout.getNotificationEmail());

        Payout getPayoutById = this.client.getPayout(payoutId);
        Assertions.assertEquals(email, getPayoutById.getNotificationEmail());
        List<Payout> getPayouts = this.client.getPayouts(
            this.monthAgo.format(this.dateFormatter),
            this.tomorrow.format(this.dateFormatter),
            null,
            null,
            null,
            null,
            firstSubmittedPayout.getGroupId()
        );
        Assertions.assertFalse(getPayouts.isEmpty());
        Assertions.assertTrue(getPayouts.stream().anyMatch(p -> p.getNotificationEmail().equals(email)));

        Boolean requestPayoutNotification = this.client.requestPayoutNotification(payoutId);
        Assertions.assertTrue(requestPayoutNotification);

        PayoutGroup cancelledPayouts = this.client.cancelPayouts(firstSubmittedPayout.getGroupId());
        Assertions.assertTrue(!cancelledPayouts.getPayouts().isEmpty());
    }

    /**
     * Create payments before start it.
     * <p>
     * Tested ledgers requests:
     * - GetLedgers()
     * - GetLedgerEntries(string currency)
     */
    @Test
    public void it_should_test_ledgers_requests() throws BitPayException {
        List<Ledger> ledgers = this.client.getLedgers();
        Assertions.assertFalse(ledgers.isEmpty());

        List<LedgerEntry> ledgersEntries = this.client.getLedgerEntries("USD", this.monthAgo.format(this.dateFormatter),
            this.tomorrow.format(this.dateFormatter));
        Assertions.assertFalse(ledgersEntries.isEmpty());
    }

    /**
     *     Tested bills requests:
     *     - CreateBill(Bill bill)
     *     - GetBill(string billId)
     *     - GetBills(string status)
     *     - UpdateBill(Bill bill, string billId)
     *     - DeliverBill(string billId, string billToken)
     */
    @Test
    public void it_should_test_bills_requests() throws BitPayException {
        Item item1 = new Item();
        item1.setQuantity(1);
        item1.setDescription("Test Item 1");
        item1.setPrice(10.00);
        List<Item> items = Collections.singletonList(item1);

        Bill requestedBill = new Bill();
        requestedBill.setNumber("bill1234-ABCD");
        requestedBill.setCurrency("USD");
        requestedBill.setName("John Doe");
        requestedBill.setEmail("john@doe.com");
        requestedBill.setAddress1("2630 Hegal Place");
        requestedBill.setAddress2("Apt 42");
        requestedBill.setCity("Alexandria");
        requestedBill.setState("VA");
        requestedBill.setZip("23242");
        requestedBill.setCountry("US");
        requestedBill.setPhone("555-123-456");
        requestedBill.setDueDate(ZonedDateTime.now());
        requestedBill.setPassProcessingFee(true);
        requestedBill.setItems(items);

        Bill createBill = this.client.createBill(requestedBill);
        String billId = createBill.getId();

        Bill getBill = this.client.getBill(billId);
        Assertions.assertEquals(billId, getBill.getId());

        List<Bill> getBills = this.client.getBills();
        Assertions.assertFalse(getBills.isEmpty());

        Item itemUpdated = new Item();
        itemUpdated.setDescription("Test Item Updated");
        itemUpdated.setPrice(9.00);
        itemUpdated.setQuantity(1);
        List<Item> itemsUpdated = Collections.singletonList(itemUpdated);
        Bill updatedBillRequest = new Bill();
        updatedBillRequest.setCurrency("USD");
        updatedBillRequest.setToken(createBill.getToken());
        updatedBillRequest.setItems(itemsUpdated);
        updatedBillRequest.setEmail("john@doe.com");

        Bill updatedBill = this.client.updateBill(updatedBillRequest, billId);
        Assertions.assertEquals(9.00, updatedBill.getItems().get(0).getPrice());

        String deliverBill = this.client.deliverBill(billId, createBill.getToken());
        Assertions.assertEquals("Success", deliverBill);
    }

    private Invoice getExampledInvoice() throws BitPayException {
        Invoice invoice = new Invoice();
        invoice.setPrice(10.00);
        invoice.setCurrency("USD");
        invoice.setFullNotifications(true);
        invoice.setExtendedNotifications(true);
        invoice.setTransactionSpeed("medium");
        invoice.setNotificationUrl("https://notification.url/aaa");
        invoice.setNotificationEmail(this.getEmail());
        invoice.setItemDesc("Created by Java integration tests");
        invoice.setAutoRedirect(true);
        invoice.setForcedBuyerSelectedWallet("bitpay");

        Buyer buyer = new Buyer();
        buyer.setName("Marcin");
        buyer.setAddress1("SomeStreet");
        buyer.setAddress2("911");
        buyer.setLocality("Washington");
        buyer.setRegion("District of Columbia");
        buyer.setPostalCode("20000");
        buyer.setCountry("USA");
        buyer.setEmail("buyer@buyeremaildomain.com");
        buyer.setNotify(true);

        invoice.setBuyer(buyer);

        return invoice;
    }

    private String getEmail() {
        return getDataFromFile("email.txt");
    }

    private String getDataFromFile(String fileName) {
        String pathname = getPathExistingClass() + fileName;
        File file = new File(pathname);

        String data = null;
        try {
            data = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            throw new IllegalArgumentException(
                "Missing file " + fileName + " . Please read configuration above constructor");
        }

        return data;
    }

    private String getPathExistingClass() {
        return "src/test/java/com/bitpay/sdk/functional/";
    }

    private static Payout getPayout(String email, String recipientId) throws BitPayException {
        Payout payout = new Payout();

        payout.setAmount(10.00);
        payout.setCurrency("USD");
        payout.setLedgerCurrency("USD");
        payout.setRecipientId(recipientId);
        payout.setNotificationEmail(email);
        payout.setReference("Java Integration Test " + UUID.randomUUID().toString());
        payout.setNotificationUrl("https://somenotiticationURL.com");

        return payout;
    }
}
