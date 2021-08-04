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

    @Before
    public void setUp() throws BitPayException, IOException, URISyntaxException {
        //ensure the second argument (api url) is the same as the one used in setUpOneTime()
//        bitpay = new Client("BitPay.config.json", null);
        bitpay = new Client(
        Env.Test,
                "3082013102010104206f265fe7bd03e113604f36e34d0a6bb0c0216423a23e17d0d5b00efd04cb377ba081e33081e0020101302c06072a8648ce3d0101022100fffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f3044042000000000000000000000000000000000000000000000000000000000000000000420000000000000000000000000000000000000000000000000000000000000000704410479be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8022100fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364141020101a12403220003b01f5ba6a59b44e139febabcbce5fdadfa1071eff16c43912527e7e5f91887bb",
                new Env.Tokens() {{
                    merchant = "";
                    payroll = "6cRuGXyitnoVJrGwFUQUKvcKXEAi6QCxoZG88gjGb6ub";
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
