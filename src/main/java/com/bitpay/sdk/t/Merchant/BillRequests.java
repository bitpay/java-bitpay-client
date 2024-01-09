package com.bitpay.sdk.t.Merchant;

import com.bitpay.sdk.Client;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.bill.Bill;
import com.bitpay.sdk.model.bill.Item;
import com.bitpay.sdk.t.ClientProvider;
import java.util.ArrayList;
import java.util.List;

public class BillRequests {

    public void createBill() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        Bill bill = new Bill();
        bill.setCurrency("USD");
        bill.setEmail("some@email.com");
        bill.setAddress1("SomeAddress");
        bill.setCity("MyCity");
        // ...

        Bill result = client.createBill(bill);
    }

    public void getBill() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        Bill bill = client.getBill("someBillid");

        List<Bill> bills =  client.getBills("draft");
    }

    public void updateBill() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        Item item = new Item();
        item.setPrice(12.34);
        item.setQuantity(2);
        item.setDescription("someDescription");

        List<Item> items = new ArrayList<>();
        items.add(item);

        Bill bill = new Bill();
        bill.setItems(items);

        client.updateBill(bill, "someBillId");
    }

    public void deliverBillViaEmail() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        client.deliverBill("someBillId", "myBillToken");
    }
}
