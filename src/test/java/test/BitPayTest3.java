package test;

import com.bitpay.BitPayException;
import com.bitpay.Client;
import com.bitpay.Env;
import com.bitpay.model.Facade;
import com.bitpay.model.Payout.PayoutBatch;
import com.bitpay.model.Payout.PayoutInstruction;
import com.bitpay.model.Payout.PayoutStatus;
import com.bitpay.util.BitPayLogger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class BitPayTest3 {

    private static final BitPayLogger _log = new BitPayLogger(BitPayLogger.DEBUG);
    private static String clientName = "BitPay Java Library Tester3";
    private static String pairingCode;
    private static URI myKeyFile;
    private Client bitpay;

    @BeforeClass
    public static void setUpOneTime() throws InterruptedException, IOException, BitPayException, URISyntaxException {
        boolean dumpOut = false;

//        Client bitpay = new Client("BitPay.config.json");
        Client bitpay = new Client(
                Env.Test,
                "bitpay_private_test.key",
                new Env.Tokens() {{
                    pos = "AvJdGrEqTW9HVsJit9zabAnrJabqaQDhWHRacHYgfgxK";
                    merchant = "2smKkjA1ACPKWUGN7wUEEqdWi3rhXYhDX6AKgG4njKvj";
                    payroll = "Ax2Yunq4EtbL8cFJeJmeL9g1ZvjPWJudyPBY1iuPqUwB";
                }}
        );

        // Authorize this client for use with a BitPay merchant account.  This client requires a
        // PAYROLL facades.
        if (!bitpay.tokenExist(Facade.Payroll)) {
            // Get PAYROLL facade authorization.
            // Obtain a pairingCode from your BitPay account administrator.  When the pairingCode
            // is created by your administrator it is assigned a facade.  To generate payout batches a
            // PAYROLL facade is required.

            // As an alternative to this client outputting a pairing code, the BitPay account owner
            // may interactively generate a pairing code via the BitPay merchant dashboard at
            // https://[test].bitpay.com/dashboard/merchant/api-tokens.  This client can subsequently
            // accept the pairing code using the following call.

            // bitpay.authorizeClient(pairingCode);

            pairingCode = bitpay.requestClientAuthorization(Facade.Payroll);

            // Signal the device operator that this client needs to be paired with a merchant account.
            _log.info("Client is requesting PAYROLL facade access. Go to " + Env.TestUrl + " and pair this client with your merchant account using the pairing code: " + pairingCode);
            dumpOut = true;
            //we already failed to authorize for a PAYROLL token, therefore we must sleep a bit to try to authorize for any other facade (rate limiter on the api side)
            Thread.sleep(10000);
        }

        if (dumpOut) {
            throw new BitPayException("Error: client is not authorized.");
        }
    }

    @Before
    public void setUp() throws BitPayException, IOException, URISyntaxException {
        //ensure the second argument (api url) is the same as the one used in setUpOneTime()
//        bitpay = new Client("BitPay.config.json");
        bitpay = new Client(
                Env.Test,
                "bitpay_private_test.key",
                new Env.Tokens() {{
                    pos = "AvJdGrEqTW9HVsJit9zabAnrJabqaQDhWHRacHYgfgxK";
                    merchant = "2smKkjA1ACPKWUGN7wUEEqdWi3rhXYhDX6AKgG4njKvj";
                    payroll = "Ax2Yunq4EtbL8cFJeJmeL9g1ZvjPWJudyPBY1iuPqUwB";
                }}
        );
    }

    @Test
    public void testShouldSubmitPayoutBatch() {
        Date date = new Date();
        Date threeDaysFromNow = new Date(date.getTime() + 3 * 24 * 3600 * 1000);

        long effectiveDate = threeDaysFromNow.getTime();
        String currency = "USD";
        List<PayoutInstruction> instructions = Arrays.asList(
                new PayoutInstruction(100.0, "mtHDtQtkEkRRB5mgeWpLhALsSbga3iZV6u"),
                new PayoutInstruction(200.0, "mvR4Xj7MYT7GJcL93xAQbSZ2p4eHJV5F7A")
        );

        PayoutBatch batch = new PayoutBatch(currency, effectiveDate, instructions);
        try {
            batch = this.bitpay.submitPayoutBatch(batch);

            assertNotNull(batch.getId());
            assertTrue(batch.getInstructions().size() == 2);

        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testShouldSubmitGetAndDeletePayoutBatch() {
        Date date = new Date();
        Date threeDaysFromNow = new Date(date.getTime() + 3 * 24 * 3600 * 1000);

        long effectiveDate = threeDaysFromNow.getTime();
        String currency = "USD";
        List<PayoutInstruction> instructions = Arrays.asList(
                new PayoutInstruction(100.0, "mtHDtQtkEkRRB5mgeWpLhALsSbga3iZV6u"),
                new PayoutInstruction(200.0, "mvR4Xj7MYT7GJcL93xAQbSZ2p4eHJV5F7A")
        );

        PayoutBatch batch0 = new PayoutBatch(currency, effectiveDate, instructions);
        try {
            batch0 = this.bitpay.submitPayoutBatch(batch0);

            assertNotNull(batch0.getId());
            assertTrue(batch0.getInstructions().size() == 2);

            PayoutBatch batch1 = this.bitpay.getPayoutBatch(batch0.getId());

            assertEquals(batch1.getId(), batch0.getId());
            assertTrue(batch1.getInstructions().size() == 2);

            this.bitpay.cancelPayoutBatch(batch0.getId());

        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testShouldGetPayoutBatches() {
        try {
            List<PayoutBatch> batches = this.bitpay.getPayoutBatches();
            assertTrue(batches.size() > 0);
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testShouldGetPayoutBatchesByStatus() {
        try {
            List<PayoutBatch> batches = this.bitpay.getPayoutBatches(PayoutStatus.New);
            assertTrue(batches.size() > 0);
        } catch (BitPayException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
