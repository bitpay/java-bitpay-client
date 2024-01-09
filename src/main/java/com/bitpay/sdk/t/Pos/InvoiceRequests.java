package com.bitpay.sdk.t.Pos;

import com.bitpay.sdk.Client;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.invoice.Buyer;
import com.bitpay.sdk.model.invoice.Invoice;
import com.bitpay.sdk.t.ClientProvider;

public class InvoiceRequests {

    public void createInvoice() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.createPos();

        Invoice invoice = new Invoice();
        invoice.setFullNotifications(true);
        invoice.setExtendedNotifications(true);
        invoice.setNotificationUrl("https://test/lJnJg9WW7MtG9GZlPVdj");
        invoice.setRedirectUrl("https://test/lJnJg9WW7MtG9GZlPVdj");
        invoice.setNotificationEmail("my@email.com");
        invoice.setBuyerSms("+12223334445");

        Buyer buyer = new Buyer();
        buyer.setName("Test");
        buyer.setEmail("test@email.com");
        buyer.setAddress1("168 General Grove");
        buyer.setCountry("AD");
        buyer.setLocality("Port Horizon");
        buyer.setNotify(true);
        buyer.setPhone("+990123456789");
        buyer.setPostalCode("KY7 1TH");
        buyer.setRegion("New Port");

        invoice.setBuyer(buyer);

        Invoice result = client.createInvoice(invoice);
    }

    public void getInvoice() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.createPos();

        Invoice invoiceById = client.getInvoice("myInvoiceId");
    }
}
