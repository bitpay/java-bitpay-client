package test;

import com.bitpay.sdk.BitPayException;
import com.bitpay.sdk.Client;
import com.bitpay.sdk.Env;
import com.bitpay.sdk.model.Currency;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.Invoice.Invoice;
import com.bitpay.sdk.util.BitPayLogger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BitPayTest2 {

    private static final BitPayLogger _log = new BitPayLogger(BitPayLogger.DEBUG);
    private static String clientName = "BitPay Java Library Tester2";
    private Client bitpay;

    @BeforeClass
    public static void setUpOneTime() throws UnknownHostException, BitPayException {
//        Client bitpay = new Client("BitPay.config.json", null);
        Client bitpay = new Client(
                Env.Test,
                "bitpay_private_test.key",
                new Env.Tokens() {{
                    pos = "AvJdGrEqTW9HVsJit9zabAnrJabqaQDhWHRacHYgfgxK";
                    merchant = "2smKkjA1ACPKWUGN7wUEEqdWi3rhXYhDX6AKgG4njKvj";
                    payroll = "9pJ7fzW1GGeuDQfj32aNATCDnyY6YAacVMcDrs7HHUNo";
                }},
                null
        );

        if (!bitpay.tokenExist(Facade.PointOfSale)) {
            // Get POS facade authorization code.
            // Obtain a pairingCode from the BitPay server.  The pairingCode must be emitted from
            // this device and input into and approved by the desired merchant account.  To
            // generate invoices a POS facade is required.
            String pairingCode = bitpay.requestClientAuthorization(Facade.PointOfSale);

            // Signal the device operator that this client needs to be paired with a merchant account.
            _log.info("Client is requesting POS facade access. Go to " + Env.TestUrl + " and pair this client with your merchant account using the pairing code: " + pairingCode);
            throw new BitPayException("Error: client is not authorized.");
        }
    }

    @Before
    public void setUp() throws BitPayException {
        //ensure the second argument (api url) is the same as the one used in setUpOneTime()
//        bitpay = new Client("BitPay.config.json", null);
        bitpay = new Client(
                Env.Test,
                "bitpay_private_test.key",
                new Env.Tokens() {{
                    pos = "AvJdGrEqTW9HVsJit9zabAnrJabqaQDhWHRacHYgfgxK";
                    merchant = "2smKkjA1ACPKWUGN7wUEEqdWi3rhXYhDX6AKgG4njKvj";
                    payroll = "9pJ7fzW1GGeuDQfj32aNATCDnyY6YAacVMcDrs7HHUNo";
                }},
                null
        );
    }

    @Test
    public void testShouldGetInvoiceId() {
        Invoice invoice = new Invoice(1.00, "USD");
        try {
            invoice = bitpay.createInvoice(invoice);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(invoice.getId());
    }

    @Test
    public void testCurrency() {
        boolean validCurrency;
        validCurrency = Currency.isValid("EUR");
        assertTrue(validCurrency);
    }
}
