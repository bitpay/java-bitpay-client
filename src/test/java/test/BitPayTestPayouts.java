package test;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.Client;
import com.bitpay.sdk.Env;
import com.bitpay.sdk.exceptions.PayoutBatchCreationException;
import com.bitpay.sdk.model.Currency;
import com.bitpay.sdk.model.Payout.*;
import com.bitpay.sdk.util.BitPayLogger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
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
    	            payout = "9pJ7fzW1GGeuDQfj32aNATCDnyY6YAacVMcDrs7HHUNo";
                }},
                null,
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
                    payout = "9pJ7fzW1GGeuDQfj32aNATCDnyY6YAacVMcDrs7HHUNo";
                }},
                null,
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
            assertTrue(recipients.size() == 3);
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
            List<PayoutRecipient> recipients = this.bitpay.getPayoutRecipients(null, 2, 1);
            assertEquals(2, recipients.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testShouldSubmitGetAndDeletePayoutRecipient() {
        List<PayoutRecipient> recipientsList = Arrays.asList(
                new PayoutRecipient("sandbox+recipient1@bitpay.com", "recipient1",
                        "https://hookb.in/wNDlQMV7WMFz88VDyGnJ"),
                new PayoutRecipient("sandbox+recipient2@bitpay.com", "recipient2",
                        "https://hookb.in/QJOPBdMgRkukpp2WO60o"),
                new PayoutRecipient("sandbox+recipient3@bitpay.com", "recipient3",
                        "https://hookb.in/QJOPBdMgRkukpp2WO60o"));
        PayoutRecipients recipientsObj = new PayoutRecipients(recipientsList);
        try {
            List<PayoutRecipient> recipients = this.bitpay.submitPayoutRecipients(recipientsObj);
            PayoutRecipient firstRecipient = recipients.get(0);
            PayoutRecipient retrieveRecipient = this.bitpay.getPayoutRecipient(firstRecipient.getId());
            List<PayoutRecipient> retrieveRecipients = this.bitpay.getPayoutRecipients(null, null, null);
            retrieveRecipient.setLabel("Updated Label");
            PayoutRecipient updateRecipient = this.bitpay.updatePayoutRecipient(retrieveRecipient.getId(),
                    retrieveRecipient);
            Boolean deleteRecipient = this.bitpay.deletePayoutRecipient(retrieveRecipient.getId());

            assertNotNull(firstRecipient);
            assertNotNull(retrieveRecipient.getId());
            assertNotNull(retrieveRecipients);
            assertEquals(firstRecipient.getId(), retrieveRecipient.getId());
            assertEquals(retrieveRecipient.getStatus(), RecipientStatus.INVITED);
            assertEquals(updateRecipient.getLabel(), "Updated Label");
            assertTrue(deleteRecipient);
        } catch (Exception e) {

        }
    }
    
    @Test
    public void testShouldRequestPayoutRecipientNotification() {
        try {
        	List<PayoutRecipient> recipientsList = Arrays.asList(
                    new PayoutRecipient("sandbox+recipient1@bitpay.com", "recipient1",
                            "https://hookb.in/wNDlQMV7WMFz88VDyGnJ"));
        	PayoutRecipients recipientsObj = new PayoutRecipients(recipientsList);
        	List<PayoutRecipient> recipients = this.bitpay.submitPayoutRecipients(recipientsObj);
        	PayoutRecipient firstRecipient = recipients.get(0);
        	Boolean result = this.bitpay.requestPayoutRecipientNotification(firstRecipient.getId());
        	
        	assertTrue(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testShouldSubmitPayout() {
        try {
        	String currency = Currency.USD;
        	String ledgerCurrency = Currency.ETH; 
        	Payout payout = new Payout(5.0, currency, ledgerCurrency);
        	List<PayoutRecipient> recipients = this.bitpay.getPayoutRecipients("active", 1, null);
        	payout.setRecipientId(recipients.get(0).getId());
        	payout.setNotificationURL("https://hookbin.com/yDEDeWJKyasG9yjj9X9P");
        	Payout createPayout = this.bitpay.submitPayout(payout);
        	Boolean cancelledPayout = this.bitpay.cancelPayout(createPayout.getId());
        	
        	assertNotNull(createPayout.getId());
            assertTrue(cancelledPayout);
        	
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testShouldGetPayouts() {
        try {
        	Date date = new Date();        	
        	Date dateBefore = new Date(date.getTime() - 24 * 24 * 3600 * 1000);
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	String endDate = sdf.format(date);
            String startDate = sdf.format(dateBefore);
            List<Payout> payouts = this.bitpay.getPayouts(startDate, endDate, null, null, null, null);
           
            assertTrue("No payouts retrieved", payouts.size() > 0);
        	
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testShouldGetPayoutsByStatus() {
        try {
        	Date date = new Date();        	
        	Date dateBefore = new Date(date.getTime() - 24 * 24 * 3600 * 1000);
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	String endDate = sdf.format(date);
            String startDate = sdf.format(dateBefore);
            List<Payout> payouts = this.bitpay.getPayouts(startDate, endDate, PayoutStatus.New, null, null, null);
           
            assertTrue("No payouts retrieved",payouts.size() > 0);
        	
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testShouldSubmitGetAndDeletePayout() {
        try {
        	String currency = Currency.USD;
        	String ledgerCurrency = Currency.ETH; 
        	Payout payout = new Payout(5.0, currency, ledgerCurrency);
        	List<PayoutRecipient> recipients = this.bitpay.getPayoutRecipients("active", 1, null);
        	payout.setRecipientId(recipients.get(0).getId());
        	payout.setNotificationURL("https://hookbin.com/yDEDeWJKyasG9yjj9X9P");
        	Payout createPayout = this.bitpay.submitPayout(payout);
        	Payout retrievedPayout = this.bitpay.getPayout(createPayout.getId());
        	Boolean cancelledPayout = this.bitpay.cancelPayout(createPayout.getId());
        	
        	assertNotNull(createPayout.getId());
            assertNotNull(retrievedPayout.getId());
            assertEquals(createPayout.getId(), retrievedPayout.getId());
            assertEquals(retrievedPayout.getStatus(), PayoutStatus.New);
            assertTrue(cancelledPayout); 
            
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testShouldRequestPayoutNotification() {
        try {
        	String currency = Currency.USD;
        	String ledgerCurrency = Currency.ETH;
        	Payout payout = new Payout(5.0, currency, ledgerCurrency);
        	List<PayoutRecipient> recipients = this.bitpay.getPayoutRecipients("active", 1, null);
        	payout.setRecipientId(recipients.get(0).getId());
        	payout.setNotificationURL("https://hookbin.com/yDEDeWJKyasG9yjj9X9P");
        	Payout createPayout = this.bitpay.submitPayout(payout);
        	Boolean result = this.bitpay.requestPayoutNotification(createPayout.getId()); 
        	Boolean cancelledPayout = this.bitpay.cancelPayout(createPayout.getId());
        	
            assertTrue(result);
            assertTrue(cancelledPayout); 
            
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testShouldSubmitPayoutBatch() {
        try {
        	Date date = new Date();
        	Date threeDaysFromNow = new Date(date.getTime() + 3 * 24 * 3600 * 1000);
        	
        	long effectiveDate = threeDaysFromNow.getTime();
        	String currency = Currency.USD;
        	String ledgerCurrency = Currency.ETH;
        	List<PayoutInstruction> instructions = Arrays.asList(
        		new PayoutInstruction(10.0, RecipientReferenceMethod.EMAIL, "sandbox+recipient1@bitpay.com"),
        		new PayoutInstruction(10.0, RecipientReferenceMethod.EMAIL, "sandbox+recipient2@bitpay.com")
        	);
        	
        	PayoutBatch batch0 = new PayoutBatch(currency, effectiveDate, instructions, ledgerCurrency);
        	batch0.setNotificationURL("https://hookbin.com/yDEDeWJKyasG9yjj9X9P");
        	
            batch0 = this.bitpay.submitPayoutBatch(batch0);
            this.bitpay.cancelPayoutBatch(batch0.getId());
            
            assertNotNull(batch0.getId());
            assertTrue(batch0.getInstructions().size() == 2);
            
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testShouldSubmitGetAndDeletePayoutBatch() throws PayoutBatchCreationException {
        Date date = new Date();
        Date threeDaysFromNow = new Date(date.getTime() + 3 * 24 * 3600 * 1000);

        long effectiveDate = threeDaysFromNow.getTime();
        String currency = Currency.USD;
    	String ledgerCurrency = Currency.ETH;
        List<PayoutInstruction> instructions = Arrays.asList(
        	new PayoutInstruction(10.0, RecipientReferenceMethod.EMAIL, "sandbox+recipient1@bitpay.com"),
        	new PayoutInstruction(10.0, RecipientReferenceMethod.EMAIL, "sandbox+recipient2@bitpay.com")
        );

        PayoutBatch batch0 = new PayoutBatch(currency, effectiveDate, instructions, ledgerCurrency);
        batch0.setNotificationURL("https://hookbin.com/yDEDeWJKyasG9yjj9X9P");
        try {
            batch0 = this.bitpay.submitPayoutBatch(batch0);

            assertNotNull(batch0.getId());
            assertTrue(batch0.getInstructions().size() == 2);

            PayoutBatch batch1 = this.bitpay.getPayoutBatch(batch0.getId());

            assertEquals(batch1.getId(), batch0.getId());
            assertTrue(batch1.getInstructions().size() == 2);

            this.bitpay.cancelPayoutBatch(batch0.getId());

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testShouldRequestPayoutBatchNotification() {
        try {
        	Date date = new Date();
        	Date threeDaysFromNow = new Date(date.getTime() + 3 * 24 * 3600 * 1000);
        	
        	long effectiveDate = threeDaysFromNow.getTime();
        	String currency = Currency.USD;
        	String ledgerCurrency = Currency.ETH;
        	List<PayoutInstruction> instructions = Arrays.asList(
        		new PayoutInstruction(10.0, RecipientReferenceMethod.EMAIL, "sandbox+recipient1@bitpay.com"),
        	    new PayoutInstruction(10.0, RecipientReferenceMethod.EMAIL, "sandbox+recipient2@bitpay.com")
        	);
        	
        	PayoutBatch batch0 = new PayoutBatch(currency, effectiveDate, instructions, ledgerCurrency);
        	batch0.setNotificationURL("https://hookbin.com/yDEDeWJKyasG9yjj9X9P");
        	
            batch0 = this.bitpay.submitPayoutBatch(batch0);
            Boolean result = this.bitpay.requestPayoutBatchNotification(batch0.getId());
            this.bitpay.cancelPayoutBatch(batch0.getId());
            
            assertTrue(result); 
            
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testShouldGetPayoutBatches() {
        try {
        	Date date = new Date();        	
        	Date dateBefore = new Date(date.getTime() - 24 * 24 * 3600 * 1000);
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	String endDate = sdf.format(date);
            String startDate = sdf.format(dateBefore);            
            
            List<PayoutBatch> batches = this.bitpay.getPayoutBatches(startDate, endDate, null, null, null);
            
            assertTrue(batches.size() > 0);
            
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testShouldGetPayoutBatchesByStatus() {
        try {
        	Date date = new Date();        	
        	Date dateBefore = new Date(date.getTime() - 24 * 24 * 3600 * 1000);
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	String endDate = sdf.format(date);
            String startDate = sdf.format(dateBefore);
            
            List<PayoutBatch> batches = this.bitpay.getPayoutBatches(startDate, endDate, PayoutStatus.New, null, null);
            
            assertTrue(batches.size() > 0);
            
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
