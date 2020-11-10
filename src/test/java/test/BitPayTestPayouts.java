package test;

import com.bitpay.sdk.BitPayException;
import com.bitpay.sdk.Client;
import com.bitpay.sdk.Env;
import com.bitpay.sdk.exceptions.PayoutCreationException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.Payout.*;
import com.bitpay.sdk.util.BitPayLogger;
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

public class BitPayTestPayouts {

    private static final BitPayLogger _log = new BitPayLogger(BitPayLogger.DEBUG);
    private static String pairingCode;
    private static URI myKeyFile;
    private Client bitpay;

    @BeforeClass
    public static void setUpOneTime() throws InterruptedException, IOException, BitPayException, URISyntaxException {

//        Client bitpay = new Client("BitPay.config.json");
        Client bitpay = new Client(
                Env.Test,
                "bitpay_private_test.key",
                new Env.Tokens() {{
                    pos = "AvJdGrEqTW9HVsJit9zabAnrJabqaQDhWHRacHYgfgxK";
                    merchant = "2smKkjA1ACPKWUGN7wUEEqdWi3rhXYhDX6AKgG4njKvj";
                    payroll = "Ax2Yunq4EtbL8cFJeJmeL9g1ZvjPWJudyPBY1iuPqUwB";
                }},
                null
        );
        bitpay.setLoggerLevel(BitPayLogger.DEBUG);
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
                }},
                null
        );
        bitpay.setLoggerLevel(BitPayLogger.DEBUG);
    }

    @Test
    public void testShouldSubmitPayoutRecipients() {
        List<PayoutRecipient> recipientsList = Arrays.asList(
                new PayoutRecipient(
                        "sandbox+recipient1@bitpay.com",
                        "recipient1",
                        "https://hookb.in/wNDlQMV7WMFz88VDyGnJ"),
                new PayoutRecipient(
                        "sandbox+recipient2@bitpay.com",
                        "recipient2",
                        "https://hookb.in/QJOPBdMgRkukpp2WO60o"),
                new PayoutRecipient(
                        "sandbox+recipient3@bitpay.com",
                        "recipient3",
                        "https://hookb.in/QJOPBdMgRkukpp2WO60o")
        );

        PayoutRecipients recipientsObj = new PayoutRecipients(recipientsList);
        try {
            List<PayoutRecipient> recipients = this.bitpay.submitPayoutRecipients(recipientsObj);

            assertNotNull(recipients);
            assertTrue(recipients.size() == 2);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testShouldGetPayoutRecipientId() {
        List<PayoutRecipient> recipientsList = Arrays.asList(
                new PayoutRecipient(
                        "sandbox+recipient1@bitpay.com",
                        "recipient1",
                        "https://hookb.in/wNDlQMV7WMFz88VDyGnJ"),
                new PayoutRecipient(
                        "sandbox+recipient2@bitpay.com",
                        "recipient2",
                        "https://hookb.in/QJOPBdMgRkukpp2WO60o"),
                new PayoutRecipient(
                        "sandbox+recipient3@bitpay.com",
                        "recipient3",
                        "https://hookb.in/QJOPBdMgRkukpp2WO60o")
        );

        PayoutRecipients recipientsObj = new PayoutRecipients(recipientsList);
        try {
            List<PayoutRecipient> recipients = this.bitpay.submitPayoutRecipients(recipientsObj);
            PayoutRecipient firstRecipient = recipients.get(0);
            PayoutRecipient retrieved = this.bitpay.getPayoutRecipient(firstRecipient.getId());

            assertNotNull(firstRecipient);
            assertNotNull(retrieved.getId());
            assertEquals(firstRecipient.getId(), retrieved.getId());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testShouldGetPayoutRecipients() {
        try {
            List<PayoutRecipient> recipients = this.bitpay.getPayoutRecipients(null, 2);
            assertEquals(2, recipients.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testShouldSubmitGetAndDeletePayoutBatch() throws PayoutCreationException {
        Date date = new Date();
        Date threeDaysFromNow = new Date(date.getTime() + 3 * 24 * 3600 * 1000);

        long effectiveDate = threeDaysFromNow.getTime();
        String currency = "USD";
        List<PayoutInstruction> instructions = Arrays.asList(
                new PayoutInstruction(100.0, RecipientReferenceMethod.EMAIL, "sandbox+recipient1@bitpay.com")
        );

        PayoutBatch batch0 = new PayoutBatch(currency, effectiveDate, instructions);
        try {
            batch0 = this.bitpay.submitPayoutBatch(batch0);

            assertNotNull(batch0.getId());
            assertTrue(batch0.getInstructions().size() == 1);

            PayoutBatch batch1 = this.bitpay.getPayoutBatch(batch0.getId());

            assertEquals(batch1.getId(), batch0.getId());
            assertTrue(batch1.getInstructions().size() == 1);

            this.bitpay.cancelPayoutBatch(batch0.getId());

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testShouldGetPayoutBatches() {
        try {
            List<PayoutBatch> batches = this.bitpay.getPayoutBatches();
            assertTrue(batches.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testShouldGetPayoutBatchesByStatus() {
        try {
            List<PayoutBatch> batches = this.bitpay.getPayoutBatches(PayoutStatus.New);
            assertTrue(batches.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
