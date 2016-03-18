package test;

import static org.junit.Assert.*;

import java.util.List;

import model.Invoice;
import model.InvoiceBuyer;
import model.Rate;
import model.Rates;
import model.Refund;
import model.RefundHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.BitPay;
import controller.BitPayException;

public class BitPayTest {

    private BitPay bitpay;
    private Invoice basicInvoice;
    private static double BTC_EPSILON = .000000001;
    private static double EPSILON = .001;

    private static String clientName = "BitPay Java Library Tester";
    private static String pairingCode;
    	
	@Before
	public void setUp() throws Exception 
	{
        // This scenario qualifies that this (test) client does not have merchant facade access.
		clientName += " on " + java.net.InetAddress.getLocalHost();
		bitpay = new BitPay(clientName);
		//if you get "too many requests, try setting this to 2000, 3000 or more
		bitpay.setRateLimiterTimeOut(3000); //this is in milliseconds

		// Authorize this client for use with a BitPay merchant account.  This client requires both
		// POS and MERCHANT facades.
        if (!bitpay.clientIsAuthorized(BitPay.FACADE_POS))
        {
            // Get POS facade authorization.
            // Obtain a pairingCode from your BitPay account administrator.  When the pairingCode
            // is created by your administrator it is assigned a facade.  To generate invoices a
            // POS facade is required.
        	
        	// As an alternative to this client outputting a pairing code, the BitPay account owner
        	// may interactively generate a pairing code via the BitPay merchant dashboard at
        	// https://bitpay.com/dashboard/merchant/api-tokens.  This client can subsequently
        	// accept the pairing code using the following call.
        	
            // bitpay.authorizeClient(pairingCode);
            
            pairingCode = bitpay.requestClientAuthorization(BitPay.FACADE_POS);

            // Signal the device operator that this client needs to be paired with a merchant account.
            System.out.print("Info: Client is requesting POS facade access. Pair this client with your merchant account using the pairing code: " + pairingCode);
            throw new BitPayException("Error: client is not authorized.");
        }

        if (!bitpay.clientIsAuthorized(BitPay.FACADE_MERCHANT))
        {
            // Get MERCHANT facade authorization.
            // Obtain a pairingCode from your BitPay account administrator.  When the pairingCode
            // is created by your administrator it is assigned a facade.  To generate invoices a
            // POS facade is required.
            pairingCode = bitpay.requestClientAuthorization(BitPay.FACADE_MERCHANT);
            
            // Signal the device operator that this client needs to be paired with a merchant account.
            System.out.print("Info: Client is requesting MERCHANT facade access. Pair this client with your merchant account using the pairing code: " + pairingCode);
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
			basicInvoice = bitpay.createInvoice(invoice);
		} catch (BitPayException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertNotNull(basicInvoice.getId());
	}
	
	@Test
	public void testShouldGetInvoiceURL() 
	{
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
	public void testShouldGetInvoiceStatus() 
	{
        Invoice invoice = new Invoice(50.0, "USD");
		try {
			basicInvoice = bitpay.createInvoice(invoice);
		} catch (BitPayException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertEquals(Invoice.STATUS_NEW, basicInvoice.getStatus());
	}

	@Test
	public void testShouldGetInvoiceBTCPrice() 
	{
        Invoice invoice = new Invoice(50.0, "USD");
		try {
			basicInvoice = bitpay.createInvoice(invoice);
		} catch (BitPayException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertNotNull(basicInvoice.getBtcPrice());		
	}

	@Test
	public void testShouldCreateInvoiceOneTenthBTC() 
	{
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
	public void testShouldCreateInvoice100USD() 
	{
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
	public void testShouldCreateInvoice100EUR() 
	{
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
	public void testShouldGetInvoice() 
	{
        Invoice invoice = new Invoice(100.0, "EUR");
		Invoice retreivedInvoice = null;
		try {
			// Create invoice on POS facade.
			invoice = this.bitpay.createInvoice(invoice);
			//
			// Must use a merchant token to retrieve this invoice since it was not created on the public facade.
			String token = this.bitpay.getAccessToken(BitPay.FACADE_MERCHANT);
			retreivedInvoice = this.bitpay.getInvoice(invoice.getId(), token);
		} catch (BitPayException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertEquals(invoice.getId(), retreivedInvoice.getId());		
	}

	@Test
	public void testShouldCreateInvoiceWithAdditionalParams() 
	{
		InvoiceBuyer buyer = new InvoiceBuyer();
		buyer.setName("Satoshi");
		buyer.setEmail("satoshi@bitpay.com");
		
        Invoice invoice = new Invoice(100.0, "USD");
        invoice.setBuyer(buyer);
        invoice.setFullNotifications(true);
        invoice.setNotificationEmail("satoshi@bitpay.com");
		invoice.setPosData("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
		try {
	        invoice = this.bitpay.createInvoice(invoice);
		} catch (BitPayException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertEquals(Invoice.STATUS_NEW, invoice.getStatus());
		assertEquals(100.0, invoice.getPrice(), EPSILON);
		assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", invoice.getPosData());
		assertEquals("Satoshi", invoice.getBuyer().getName());
		assertEquals("satoshi@bitpay.com", invoice.getBuyer().getEmail());
		assertEquals(true, invoice.getFullNotifications());
		assertEquals("satoshi@bitpay.com", invoice.getNotificationEmail());
	}

	/*@Test
	public void testShouldCreateAndCancelRefundRequest()
	{
		String invoiceId = "9Hz86CCoAJWdTHGsB6Bra9";
		String bitcoinAddress = "381rUw3naC9HujBPMyVfPoVsnVCeTQz1m8";
		
		boolean canceled = false;
		try {
			RefundHelper refundRequest = this.bitpay.requestRefund(invoiceId, bitcoinAddress);
			
			assertNotNull(refundRequest.getInvoice().getId());
			assertNotNull(refundRequest.getRefund().getId());
			assertEquals(refundRequest.getInvoice().getId(), invoiceId);

			List<Refund> refunds = this.bitpay.getAllRefunds(refundRequest.getInvoice());
			assertTrue(refunds.size() > 0);

			canceled = this.bitpay.cancelRefundRequest(invoiceId, refundRequest.getRefund().getId());
			
		} catch (BitPayException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertTrue(canceled);
	}*/

	@Test
	public void testShouldGetExchangeRates() 
	{
		Rates rates = null;
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
	public void testShouldGetEURExchangeRate() 
	{
		Rates rates = null;
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
	public void testShouldGetCNYExchangeRate() 
	{
		Rates rates = null;
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
	public void testShouldUpdateExchangeRates() 
	{
		Rates rates = null;
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

	/*
	@Test
	public void testShouldGetInvoices() {
		List<Invoice> invoices = null;
		try {
			invoices = this.bitpay.getInvoices("2014-01-01", "2014-09-01");
		} catch (BitPayException e) {
			e.printStackTrace();
		}
		assertTrue(invoices.size() > 0);
	}
	*/
}
