package test;

import com.bitpay.BitPayException;
import com.bitpay.model.Rate.Rate;
import com.bitpay.model.Rate.Rates;
import com.bitpay.model.Settlement.Settlement;
import com.bitpay.util.BitPayLogger;
import com.bitpay.Client;
import com.bitpay.model.Bill.Bill;
import com.bitpay.model.Bill.BillStatus;
import com.bitpay.model.Bill.Item;
import com.bitpay.model.*;
import com.bitpay.model.Invoice.Buyer;
import com.bitpay.model.Invoice.Invoice;
import com.bitpay.model.Invoice.InvoiceStatus;
import com.bitpay.model.Ledger.Ledger;
import com.bitpay.util.KeyUtils;
import org.bitcoinj.core.ECKey;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class BitPayTest {

    private static final BitPayLogger _log = new BitPayLogger(BitPayLogger.DEBUG);
    private final static double BTC_EPSILON = .000000001;
    private final static double EPSILON = .001;
    private static String clientName = "BitPay Java Library Tester";
    private static String pairingCode;
    private static String refundInvoiceId = null;
    private static URI myKeyFile;
    private Client bitpay;
    private Invoice basicInvoice;

    @BeforeClass
    public static void setUpOneTime() throws InterruptedException, IOException, BitPayException, URISyntaxException {
        boolean dumpOut = false;

        Client bitpay = new Client("/Users/antonio.buedo/Bitpay/Repos/java-bitpay-client/BitPay.config.json");

        if (!bitpay.clientIsAuthorized(Facade.Merchant)) {
            // Get MERCHANT facade authorization.
            // Obtain a pairingCode from your BitPay account administrator.  When the pairingCode
            // is created by your administrator it is assigned a facade.  To generate invoices a
            // POS facade is required.
            pairingCode = bitpay.requestClientAuthorization(Facade.Merchant);

            // Signal the device operator that this client needs to be paired with a merchant account.
            _log.info("Client is requesting MERCHANT facade access. Go to " + Client.BITPAY_TEST_URL + " and pair this client with your merchant account using the pairing code: " + pairingCode);
            dumpOut = true;
        }

        if (dumpOut) {
            throw new BitPayException("Error: client is not authorized.");
        }
    }

    @Before
    public void setUp() throws BitPayException, IOException, URISyntaxException {
        //ensure the second argument (api url) is the same as the one used in setUpOneTime()
//        bitpay = new Client(myKeyFile, clientName, Client.BITPAY_TEST_URL);
        bitpay = new Client("/Users/antonio.buedo/Bitpay/Repos/java-bitpay-client/BitPay.config.json");
    }

    @Test
    public void testCreateECKeyFromSeedString() {
        String randomSeedString = "LB1vBiLP1Bu6d1VUcwCUdp";
        ECKey key = KeyUtils.createEcKeyFromHexString(randomSeedString); //this will result in a runtime error if anything goes wrong
        assertNotNull(key);
    }

    @Test
    public void testShouldGetInvoiceId() {
        Invoice invoice = new Invoice(50.0, "USD");
        try {
            basicInvoice = bitpay.createInvoice(invoice);
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(basicInvoice.getId());
    }

    @Test
    public void testShouldGetInvoiceURL() {
        Invoice invoice = new Invoice(50.0, "USD");
        try {
            basicInvoice = bitpay.createInvoice(invoice);
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(basicInvoice.getUrl());
    }

    @Test
    public void testShouldGetInvoiceStatus() {
        Invoice invoice = new Invoice(2.0, "EUR");
        try {
            basicInvoice = bitpay.createInvoice(invoice);
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(InvoiceStatus.New, basicInvoice.getStatus());
    }

    @Test
    public void testShouldCreateInvoiceOneTenthBTC() {
        Invoice invoice = new Invoice(0.1, "BTC");
        try {
            invoice = this.bitpay.createInvoice(invoice);
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(0.1, invoice.getPrice(), BTC_EPSILON);
    }

    @Test
    public void testShouldCreateInvoice100USD() {
        Invoice invoice = new Invoice(100.0, "USD");
        try {
            invoice = this.bitpay.createInvoice(invoice);
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(100.0, invoice.getPrice(), EPSILON);
    }

    @Test
    public void testShouldCreateInvoice100EUR() {
        Invoice invoice = new Invoice(100.0, "EUR");
        try {
            invoice = this.bitpay.createInvoice(invoice);
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(100.0, invoice.getPrice(), EPSILON);
    }

    @Test
    public void testShouldGetInvoice() {
        Invoice invoice = new Invoice(100.0, "EUR");
        Invoice retreivedInvoice = null;
        try {
            // Create invoice on POS facade.
            invoice = this.bitpay.createInvoice(invoice);
            //
            // Must use a merchant token to retrieve this invoice since it was not created on the public facade.
            String token = this.bitpay.getAccessToken(Facade.Merchant);
            retreivedInvoice = this.bitpay.getInvoice(invoice.getId(), token);
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(invoice.getId(), retreivedInvoice.getId());
    }

    @Test
    public void testShouldCreateInvoiceWithAdditionalParams() {
        Buyer buyer = new Buyer();
        buyer.setName("Satoshi");
        buyer.setEmail("satoshi@buyeremaildomain.com");

        Invoice invoice = new Invoice(100.0, "USD");
        invoice.setBuyer(buyer);
        invoice.setFullNotifications(true);
        invoice.setNotificationEmail("satoshi@merchantemaildomain.com");
        invoice.setPosData("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
        try {
            invoice = this.bitpay.createInvoice(invoice);
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(InvoiceStatus.New, invoice.getStatus());
        assertEquals(100.0, invoice.getPrice(), EPSILON);
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", invoice.getPosData());
        assertEquals("Satoshi", invoice.getBuyer().getName());
        assertEquals("satoshi@buyeremaildomain.com", invoice.getBuyer().getEmail());
        assertEquals(true, invoice.getFullNotifications());
        assertEquals("satoshi@merchantemaildomain.com", invoice.getNotificationEmail());
    }

    @Test
    public void TestShouldCreateBillUSD() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item() {{
            setPrice(30.0);
            setQuantity(9);
            setDescription("product-a");
        }});
        items.add(new Item() {{
            setPrice(14.0);
            setQuantity(16);
            setDescription("product-b");
        }});
        items.add(new Item() {{
            setPrice(3.90);
            setQuantity(42);
            setDescription("product-c");
        }});
        items.add(new Item() {{
            setPrice(6.99);
            setQuantity(12);
            setDescription("product-d");
        }});

        Bill bill = new Bill("7", Currency.USD, "agallardo+java190812@bitpay.com", items);
        Bill basicBill = null;
        try {
            basicBill = this.bitpay.createBill(bill);
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(basicBill.getId());
    }

    @Test
    public void TestShouldCreateBillEUR() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item() {{
            setPrice(30.0);
            setQuantity(9);
            setDescription("product-a");
        }});
        items.add(new Item() {{
            setPrice(14.0);
            setQuantity(16);
            setDescription("product-b");
        }});
        items.add(new Item() {{
            setPrice(3.90);
            setQuantity(42);
            setDescription("product-c");
        }});
        items.add(new Item() {{
            setPrice(6.99);
            setQuantity(12);
            setDescription("product-d");
        }});

        Bill bill = new Bill("7", Currency.EUR, "agallardo+java190812@bitpay.com", items);
        Bill basicBill = null;
        try {
            basicBill = this.bitpay.createBill(bill);
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(basicBill.getId());
    }

    @Test
    public void TestShouldGetBillUrl() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item() {{
            setPrice(30.0);
            setQuantity(9);
            setDescription("product-a");
        }});
        items.add(new Item() {{
            setPrice(14.0);
            setQuantity(16);
            setDescription("product-b");
        }});
        items.add(new Item() {{
            setPrice(3.90);
            setQuantity(42);
            setDescription("product-c");
        }});
        items.add(new Item() {{
            setPrice(6.99);
            setQuantity(12);
            setDescription("product-d");
        }});

        Bill bill = new Bill("7", Currency.USD, "agallardo+java190812@bitpay.com", items);
        Bill basicBill = null;
        try {
            basicBill = this.bitpay.createBill(bill);
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(basicBill.getUrl());
    }

    @Test
    public void TestShouldGetBillStatus() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item() {{
            setPrice(30.0);
            setQuantity(9);
            setDescription("product-a");
        }});
        items.add(new Item() {{
            setPrice(14.0);
            setQuantity(16);
            setDescription("product-b");
        }});
        items.add(new Item() {{
            setPrice(3.90);
            setQuantity(42);
            setDescription("product-c");
        }});
        items.add(new Item() {{
            setPrice(6.99);
            setQuantity(12);
            setDescription("product-d");
        }});

        Bill bill = new Bill("7", Currency.USD, "agallardo+java190812@bitpay.com", items);
        Bill basicBill = null;
        try {
            basicBill = this.bitpay.createBill(bill);
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(BillStatus.Draft, basicBill.getStatus());
    }

    @Test
    public void TestShouldGetBill() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item() {{
            setPrice(30.0);
            setQuantity(9);
            setDescription("product-a");
        }});
        items.add(new Item() {{
            setPrice(14.0);
            setQuantity(16);
            setDescription("product-b");
        }});
        items.add(new Item() {{
            setPrice(3.90);
            setQuantity(42);
            setDescription("product-c");
        }});
        items.add(new Item() {{
            setPrice(6.99);
            setQuantity(12);
            setDescription("product-d");
        }});

        Bill bill = new Bill("7", Currency.USD, "agallardo+java190812@bitpay.com", items);
        Bill basicBill = null;
        Bill retrievedBill = null;
        try {
            basicBill = this.bitpay.createBill(bill);
            retrievedBill = this.bitpay.getBill(basicBill.getId());
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(basicBill.getId(), retrievedBill.getId());
    }

    @Test
    public void TestShouldGetAndUpdateBill() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item() {{
            setPrice(30.0);
            setQuantity(9);
            setDescription("product-a");
        }});
        items.add(new Item() {{
            setPrice(14.0);
            setQuantity(16);
            setDescription("product-b");
        }});
        items.add(new Item() {{
            setPrice(3.90);
            setQuantity(42);
            setDescription("product-c");
        }});
        items.add(new Item() {{
            setPrice(6.99);
            setQuantity(12);
            setDescription("product-d");
        }});

        Bill bill = new Bill("7", Currency.USD, "agallardo+java190812@bitpay.com", items);
        Bill basicBill = null;
        Bill retrievedBill = null;
        Bill updatedBill = null;
        try {
            basicBill = this.bitpay.createBill(bill);
            retrievedBill = this.bitpay.getBill(basicBill.getId());
            retrievedBill.setCurrency(Currency.EUR);
            retrievedBill.setName("updatedBill");
            retrievedBill.getItems().add(new Item() {{
                setPrice(60.0);
                setQuantity(7);
                setDescription("product-added");
            }});
            updatedBill = this.bitpay.updateBill(retrievedBill, retrievedBill.getId());
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals(basicBill.getId(), retrievedBill.getId());
        assertEquals(retrievedBill.getId(), updatedBill.getId());
        assertEquals(updatedBill.getCurrency(), Currency.EUR);
        assertEquals(updatedBill.getName(), "updatedBill");
    }

    @Test
    public void TestShouldDeliverBill() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item() {{
            setPrice(30.0);
            setQuantity(9);
            setDescription("product-a");
        }});
        items.add(new Item() {{
            setPrice(14.0);
            setQuantity(16);
            setDescription("product-b");
        }});
        items.add(new Item() {{
            setPrice(3.90);
            setQuantity(42);
            setDescription("product-c");
        }});
        items.add(new Item() {{
            setPrice(6.99);
            setQuantity(12);
            setDescription("product-d");
        }});

        Bill bill = new Bill("7", Currency.USD, "agallardo+java190812@bitpay.com", items);
        Bill basicBill = null;
        String result = "";
        Bill retrievedBill = null;
        try {
            basicBill = this.bitpay.createBill(bill);
            result = this.bitpay.deliverBill(basicBill.getId(), basicBill.getToken());
            retrievedBill = this.bitpay.getBill(basicBill.getId());
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertEquals("Success", result);
    }

    @Test
    public void TestShouldGetBills() {
        List<Bill> bills = null;
        try {
            bills = this.bitpay.getBills();
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertTrue(bills.size() > 0);
    }

    @Test
    public void TestShouldGetBillsByStatus() {
        List<Bill> bills = null;
        try {
            bills = this.bitpay.getBills(BillStatus.Draft);
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertTrue(bills.size() > 0);
    }

    @Test
    public void testShouldGetExchangeRates() {
        Rates rates;
        List<Rate> rateList = null;
        try {
            rates = this.bitpay.getRates();
            rateList = rates.getRates();
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(rateList);
    }

    @Test
    public void testShouldGetEURExchangeRate() {
        Rates rates;
        double rate = 0.0;
        try {
            rates = this.bitpay.getRates();
            rate = rates.getRate("EUR");
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertTrue(rate != 0);
    }

    @Test
    public void testShouldGetCNYExchangeRate() {
        Rates rates;
        double rate = 0.0;
        try {
            rates = this.bitpay.getRates();
            rate = rates.getRate("CNY");
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertTrue(rate != 0);
    }

    @Test
    public void testShouldUpdateExchangeRates() {
        Rates rates;
        List<Rate> rateList = null;
        try {
            rates = this.bitpay.getRates();
            rates.update();
            rateList = rates.getRates();
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(rateList);
    }

    @Test
    public void testShouldGetInvoices() {
        List<Invoice> invoices = null;
        try {
            //check within the last few days
            Date date = new Date();
            Date dateBefore = new Date(date.getTime() - 7 * 24 * 3600 * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(date);
            String sevenDaysAgo = sdf.format(dateBefore);
            invoices = this.bitpay.getInvoices(sevenDaysAgo, today);
        } catch (BitPayException e) {
            e.printStackTrace();
        }
        assert invoices != null;
        assertTrue(invoices.size() > 0);
    }

    @Test
    public void testShouldGetLedgerBtc() {
        Ledger ledger = null;
        try {
            //check within the last few days
            Date date = new Date();
            Date dateTomorrow = new Date(date.getTime() + 1 * 24 * 3600 * 1000);
            Date dateBefore = new Date(date.getTime() - 7 * 24 * 3600 * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String tomorrow = sdf.format(dateTomorrow);
            String sevenDaysAgo = sdf.format(dateBefore);
            ledger = this.bitpay.getLedger(Currency.BTC, sevenDaysAgo, tomorrow);
        } catch (BitPayException e) {
            e.printStackTrace();
        }
        assert ledger.getEntries() != null;
        assertTrue(ledger.getEntries().size() > 0);
    }

    @Test
    public void testShouldGetLedgerUsd() {
        Ledger ledger = null;
        try {
            //check within the last few days
            Date date = new Date();
            Date dateTomorrow = new Date(date.getTime() + 1 * 24 * 3600 * 1000);
            Date dateBefore = new Date(date.getTime() - 7 * 24 * 3600 * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String tomorrow = sdf.format(dateTomorrow);
            String sevenDaysAgo = sdf.format(dateBefore);
            ledger = this.bitpay.getLedger(Currency.USD, sevenDaysAgo, tomorrow);
        } catch (BitPayException e) {
            e.printStackTrace();
        }
        assert ledger.getEntries() != null;
        assertTrue(ledger.getEntries().size() > 0);
    }

    @Test
    public void testShouldGetLedgers() {
        List<Ledger> ledgers = null;
        try {
            ledgers = this.bitpay.getLedgers();
        } catch (BitPayException e) {
            e.printStackTrace();
        }
        assert ledgers != null;
        assertTrue(ledgers.size() > 0);
    }

    /*
    To use this test:
	You must have a paid/completed invoice in your account (list of invoices). The test looks for the first invoice in the "complete"
	state and authorises a refund. The actual refund will not be executed until the email receiver enters his bitcoin refund address.
    */
    @Test
    public void testShouldCreateRefundRequest() {
        List<Invoice> invoices;
        try {
            long bitcoinInventedDate = 1230786000000L;
            Date date = new Date();
            Date dateBefore = new Date(bitcoinInventedDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(date);
            String dateBeforeString = sdf.format(dateBefore);
            invoices = this.bitpay.getInvoices(dateBeforeString, today);
            for (Invoice invoice : invoices) {
                if (invoice.getStatus().equalsIgnoreCase("complete")) {
                    refundInvoiceId = invoice.getId();
                    break;
                }
            }
        } catch (BitPayException e) {
            e.printStackTrace();
        }
        assertNotNull(refundInvoiceId);
        String refundEmail = "youremail@yourdomain.com"; //change this to whatever address you want to refund to

        try {
            RefundHelper refundRequest = this.bitpay.requestRefund(refundInvoiceId, refundEmail);

            assertNotNull(refundRequest.getInvoice().getId());
            assertEquals(refundRequest.getInvoice().getId(), refundInvoiceId);

            List<Refund> refunds = this.bitpay.getAllRefunds(refundRequest.getInvoice());

        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void TestGetSettlements() {
        List<Settlement> settlements = null;
        try {
            //check within the last few days
            Date date = new Date();
            Date dateBefore = new Date(date.getTime() - 30 * 24 * 3600 * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(date);
            String oneMonthAgo = sdf.format(dateBefore);

            // make sure we get a ledger with a not null Entries property
            settlements = this.bitpay.getSettlements(Currency.USD, oneMonthAgo, today, null, null, null);
        } catch (BitPayException e) {
            e.printStackTrace();
        }
        assert settlements != null;
        assertTrue(settlements.size() > 0);
    }

    @Test
    public void TestGetSettlement() {
        List<Settlement> settlements = null;
        Settlement settlement = null;
        Settlement firstSettlement = null;
        try {
            //check within the last few days
            Date date = new Date();
            Date dateBefore = new Date(date.getTime() - 30 * 24 * 3600 * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(date);
            String oneMonthAgo = sdf.format(dateBefore);

            // make sure we get a ledger with a not null Entries property
            settlements = this.bitpay.getSettlements(Currency.USD, oneMonthAgo, today, null, null, null);
            firstSettlement = settlements.get(0);
            settlement = this.bitpay.getSettlement(firstSettlement.getId());
        } catch (BitPayException e) {
            e.printStackTrace();
        }
        assertNotNull(settlement.getId());
        assertEquals(firstSettlement.getId(), settlement.getId());
    }

    @Test
    public void TestGetSettlementReconciliationReport() {
        List<Settlement> settlements = null;
        Settlement settlement = null;
        Settlement firstSettlement = null;
        try {
            //check within the last few days
            Date date = new Date();
            Date dateBefore = new Date(date.getTime() - 30 * 24 * 3600 * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(date);
            String oneMonthAgo = sdf.format(dateBefore);

            // make sure we get a ledger with a not null Entries property
            settlements = this.bitpay.getSettlements(Currency.USD, oneMonthAgo, today, null, null, null);
            firstSettlement = settlements.get(0);
            settlement = this.bitpay.getSettlementReconciliationReport(firstSettlement);
        } catch (BitPayException e) {
            e.printStackTrace();
        }
        assertNotNull(settlement.getId());
        assertEquals(firstSettlement.getId(), settlement.getId());
    }
}
