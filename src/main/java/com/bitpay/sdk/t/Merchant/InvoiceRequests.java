package com.bitpay.sdk.t.Merchant;

import com.bitpay.sdk.Client;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.invoice.Buyer;
import com.bitpay.sdk.model.invoice.Invoice;
import com.bitpay.sdk.t.ClientProvider;
import java.util.List;

public class InvoiceRequests {

    public void createInvoice() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

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
        Client client = ClientProvider.create();

        Invoice invoiceById = client.getInvoice("myInvoiceId");

        Invoice invoiceByGuid = client.getInvoiceByGuid("someGuid"); // we can add a GUID during the invoice creation

        List<Invoice> invoices = client.getInvoices("2023-04-14", "2023-04-17", null, null, null, null);
    }

    public void updateInvoice() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        Invoice invoice = client.updateInvoice("someInvoiceId", "12312321321", null, null, null);
    }

    public void cancelInvoice() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        client.cancelInvoice("invoiceId");

        client.cancelInvoiceByGuid("invoiceGuid");
    }

    public void requestInvoiceWebhookToBeResent() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        client.requestInvoiceWebhookToBeResent("someInvoiceId");
    }
}
