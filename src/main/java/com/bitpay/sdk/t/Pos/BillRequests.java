package com.bitpay.sdk.t.Pos;

import com.bitpay.sdk.Client;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.bill.Bill;
import com.bitpay.sdk.t.ClientProvider;

public class BillRequests {

    public void createBill() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.createPos();

        Bill bill = new Bill();
        bill.setCurrency("USD");
        bill.setEmail("some@email.com");
        bill.setAddress1("SomeAddress");
        bill.setCity("MyCity");
        // ...

        Bill result = client.createBill(bill);
    }

    public void getBill() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.createPos();

        Bill bill = client.getBill("someBillid");
    }

    public void deliverBillViaEmail() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.createPos();

        client.deliverBill("someBillId", "myBillToken");
    }
}
