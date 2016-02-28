package test;

import static org.junit.Assert.*;

import model.Invoice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.BitPay;
import controller.BitPayException;

public class BitPayTest2 {

    private BitPay bitpay;
    private static String clientName = "BitPay Java Library Tester2";
	
	@Before
	public void setUp() throws Exception 
	{
		// If this test has never been run before then this test must be run twice in order to pass.
		// The first time this test runs it will create an identity and emit a client pairing code.
		// The pairing code must then be authorized in a BitPay account.  Running the test a second
		// time should result in the authorized client (this test) running to completion.
		clientName += " on " + java.net.InetAddress.getLocalHost();
        bitpay = new BitPay(clientName);        
        
        if (!bitpay.clientIsAuthorized(BitPay.FACADE_POS))
        {
            // Get POS facade authorization code.
            // Obtain a pairingCode from the BitPay server.  The pairingCode must be emitted from
        	// this device and input into and approved by the desired merchant account.  To
        	// generate invoices a POS facade is required.
            String pairingCode = bitpay.requestClientAuthorization(BitPay.FACADE_POS);
            
            // Signal the device operator that this client needs to be paired with a merchant account.
            System.out.print("Info: Pair this client with your merchant account using the pairing code: " + pairingCode);
            throw new BitPayException("Error: client is not authorized.");
        }
	}

	@After
	public void tearDown() throws Exception 
	{
	}

	@Test
	public void testShouldGetInvoiceId() 
	{
        Invoice invoice = new Invoice(50.0, "USD");
		try {
			invoice = bitpay.createInvoice(invoice);
		} catch (BitPayException e) {
			e.printStackTrace();
		}
		assertNotNull(invoice.getId());
	}
}
