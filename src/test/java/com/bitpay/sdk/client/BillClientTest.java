/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.Bill.Bill;
import com.bitpay.sdk.model.Bill.Item;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.util.AccessTokens;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BillClientTest extends AbstractClientTest {

    @Test
    public void it_should_create_bill() throws BitPayException {
        // given
        AccessTokens accessTokens = this.getAccessTokens();
        BillClient client = this.getBillClient(accessTokens);
        this.addServerJsonResponse(
            "/bills",
            "POST",
            getPreparedJsonDataFromFile("createBillRequest.json"),
            getPreparedJsonDataFromFile("createBillResponse.json")
        );

        // when
        Bill result = client.createBill(getBill(), Facade.MERCHANT, true);

        // then
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("6EBQR37MgDJPfEiLY3jtRq7eTP2aodR5V5wmXyyZhru5FM5yF4RCGKYQtnT7nhwHjA", result.getToken());
        Assertions.assertEquals("john@doe.com", result.getEmail());
        Assertions.assertEquals("bill1234-EFGH", result.getNumber());
        Assertions.assertEquals("John Doe", result.getName());
        Assertions.assertEquals("2630 Hegal Place", result.getAddress1());
        Assertions.assertEquals("Apt 42", result.getAddress2());
        Assertions.assertEquals("Alexandria", result.getCity());
        Assertions.assertEquals("VA", result.getState());
        Assertions.assertEquals("23242", result.getZip());
        Assertions.assertEquals("jane@doe.com", result.getCc().get(0));
        Assertions.assertEquals("555-123-456", result.getPhone());
        Assertions.assertEquals("2021-05-31T00:00:00.000Z", result.getDueDate());
        Assertions.assertTrue(result.getPassProcessingFee());
        Assertions.assertEquals("draft", result.getStatus());
        Assertions.assertEquals("https://bitpay.com/bill?id=3Zpmji8bRKxWJo2NJbWX5H&resource=bills", result.getUrl());
        Assertions.assertEquals("3Zpmji8bRKxWJo2NJbWX5H", result.getId());
        Assertions.assertEquals("7HyKWn3d4xdhAMQYAEVxVq", result.getMerchant());
        Assertions.assertEquals("NV35GRWtrdB2cmGEjY4LKY", result.getItems().get(0).getId());
        Assertions.assertEquals("Test Item 1", result.getItems().get(0).getDescription());
        Assertions.assertEquals(6.0, result.getItems().get(0).getPrice());
        Assertions.assertEquals(1, result.getItems().get(0).getQuantity());
        Assertions.assertEquals("Apy3i2TpzHRYP8tJCkrZMT", result.getItems().get(1).getId());
        Assertions.assertEquals("Test Item 2", result.getItems().get(1).getDescription());
        Assertions.assertEquals(4.0, result.getItems().get(1).getPrice());
        Assertions.assertEquals(1, result.getItems().get(1).getQuantity());
        Assertions.assertEquals("6EBQR37MgDJPfEiLY3jtRq7eTP2aodR5V5wmXyyZhru5FM5yF4RCGKYQtnT7nhwHjA", accessTokens.getAccessToken("3Zpmji8bRKxWJo2NJbWX5H"));
    }

    @Test
    public void it_should_return_bill() throws BitPayException {
        // given
        AccessTokens accessTokens = this.getAccessTokens();
        BillClient client = this.getBillClient(accessTokens);
        this.addServerJsonResponse(
            "/bills/3Zpmji8bRKxWJo2NJbWX5H?token=someMerchantToken",
            "GET",
            null,
            getPreparedJsonDataFromFile("createBillResponse.json")
        );

        // when
        Bill result = client.getBill("3Zpmji8bRKxWJo2NJbWX5H", Facade.MERCHANT, true);

        // then
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("6EBQR37MgDJPfEiLY3jtRq7eTP2aodR5V5wmXyyZhru5FM5yF4RCGKYQtnT7nhwHjA", result.getToken());
        Assertions.assertEquals("john@doe.com", result.getEmail());
        Assertions.assertEquals("bill1234-EFGH", result.getNumber());
        Assertions.assertEquals("John Doe", result.getName());
        Assertions.assertEquals("2630 Hegal Place", result.getAddress1());
        Assertions.assertEquals("Apt 42", result.getAddress2());
        Assertions.assertEquals("Alexandria", result.getCity());
        Assertions.assertEquals("VA", result.getState());
        Assertions.assertEquals("23242", result.getZip());
        Assertions.assertEquals("jane@doe.com", result.getCc().get(0));
        Assertions.assertEquals("555-123-456", result.getPhone());
        Assertions.assertEquals("2021-05-31T00:00:00.000Z", result.getDueDate());
        Assertions.assertTrue(result.getPassProcessingFee());
        Assertions.assertEquals("draft", result.getStatus());
        Assertions.assertEquals("https://bitpay.com/bill?id=3Zpmji8bRKxWJo2NJbWX5H&resource=bills", result.getUrl());
        Assertions.assertEquals("7HyKWn3d4xdhAMQYAEVxVq", result.getMerchant());
        Assertions.assertEquals("NV35GRWtrdB2cmGEjY4LKY", result.getItems().get(0).getId());
        Assertions.assertEquals("Test Item 1", result.getItems().get(0).getDescription());
        Assertions.assertEquals(6.0, result.getItems().get(0).getPrice());
        Assertions.assertEquals(1, result.getItems().get(0).getQuantity());
        Assertions.assertEquals("Apy3i2TpzHRYP8tJCkrZMT", result.getItems().get(1).getId());
        Assertions.assertEquals("Test Item 2", result.getItems().get(1).getDescription());
        Assertions.assertEquals(4.0, result.getItems().get(1).getPrice());
        Assertions.assertEquals(1, result.getItems().get(1).getQuantity());
    }

    @Test
    public void it_should_return_bills() throws BitPayException {
        // given
        AccessTokens accessTokens = this.getAccessTokens();
        BillClient client = this.getBillClient(accessTokens);
        this.addServerJsonResponse(
            "/bills?token=someMerchantToken",
            "GET",
            null,
            getPreparedJsonDataFromFile("getBillsResponse.json")
        );

        // when
        List<Bill> result = client.getBills();

        // then
        Assertions.assertEquals("X6KJbe9RxAGWNReCwd1xRw", result.get(0).getId());
        Assertions.assertEquals("USD", result.get(0).getCurrency());
        Assertions.assertEquals("6EBQR37MgDJPfEiLY3jtRqBMYLg8XSDqhp2kp7VSDqCMHGHnsw4bqnnwQmtehzCvSo", result.get(0).getToken());
        Assertions.assertEquals("john@doe.com", result.get(0).getEmail());
        Assertions.assertEquals("bill1234-ABCD", result.get(0).getNumber());
        Assertions.assertEquals("John Doe", result.get(0).getName());
        Assertions.assertEquals("2630 Hegal Place", result.get(0).getAddress1());
        Assertions.assertEquals("Apt 42", result.get(0).getAddress2());
        Assertions.assertEquals("Alexandria", result.get(0).getCity());
        Assertions.assertEquals("VA", result.get(0).getState());
        Assertions.assertEquals("23242", result.get(0).getZip());
        Assertions.assertEquals("jane@doe.com", result.get(0).getCc().get(0));
        Assertions.assertEquals("555-123-456", result.get(0).getPhone());
        Assertions.assertEquals("2021-05-31T00:00:00.000Z", result.get(0).getDueDate());
        Assertions.assertTrue(result.get(0).getPassProcessingFee());
        Assertions.assertEquals("draft", result.get(0).getStatus());
        Assertions.assertEquals("https://bitpay.com/bill?id=X6KJbe9RxAGWNReCwd1xRw&resource=bills", result.get(0).getUrl());
        Assertions.assertEquals("7HyKWn3d4xdhAMQYAEVxVq", result.get(0).getMerchant());
        Assertions.assertEquals("EL4vx41Nxc5RYhbqDthjE", result.get(0).getItems().get(0).getId());
        Assertions.assertEquals("Test Item 1", result.get(0).getItems().get(0).getDescription());
        Assertions.assertEquals(6.0, result.get(0).getItems().get(0).getPrice());
        Assertions.assertEquals(1, result.get(0).getItems().get(0).getQuantity());
        Assertions.assertEquals("6spPADZ2h6MfADvnhfsuBt", result.get(0).getItems().get(1).getId());
        Assertions.assertEquals("Test Item 2", result.get(0).getItems().get(1).getDescription());
        Assertions.assertEquals(4.0, result.get(0).getItems().get(1).getPrice());
        Assertions.assertEquals(1, result.get(0).getItems().get(1).getQuantity());
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void it_should_return_bills_by_status() throws BitPayException {
        // given
        AccessTokens accessTokens = this.getAccessTokens();
        BillClient client = this.getBillClient(accessTokens);
        this.addServerJsonResponse(
            "/bills?token=someMerchantToken&status=draft",
            "GET",
            null,
            getPreparedJsonDataFromFile("getBillsResponse.json")
        );

        // when
        List<Bill> result = client.getBills("draft");

        // then
        Assertions.assertEquals("X6KJbe9RxAGWNReCwd1xRw", result.get(0).getId());
        Assertions.assertEquals("USD", result.get(0).getCurrency());
        Assertions.assertEquals("6EBQR37MgDJPfEiLY3jtRqBMYLg8XSDqhp2kp7VSDqCMHGHnsw4bqnnwQmtehzCvSo", result.get(0).getToken());
        Assertions.assertEquals("john@doe.com", result.get(0).getEmail());
        Assertions.assertEquals("bill1234-ABCD", result.get(0).getNumber());
        Assertions.assertEquals("John Doe", result.get(0).getName());
        Assertions.assertEquals("2630 Hegal Place", result.get(0).getAddress1());
        Assertions.assertEquals("Apt 42", result.get(0).getAddress2());
        Assertions.assertEquals("Alexandria", result.get(0).getCity());
        Assertions.assertEquals("VA", result.get(0).getState());
        Assertions.assertEquals("23242", result.get(0).getZip());
        Assertions.assertEquals("jane@doe.com", result.get(0).getCc().get(0));
        Assertions.assertEquals("555-123-456", result.get(0).getPhone());
        Assertions.assertEquals("2021-05-31T00:00:00.000Z", result.get(0).getDueDate());
        Assertions.assertTrue(result.get(0).getPassProcessingFee());
        Assertions.assertEquals("draft", result.get(0).getStatus());
        Assertions.assertEquals("https://bitpay.com/bill?id=X6KJbe9RxAGWNReCwd1xRw&resource=bills", result.get(0).getUrl());
        Assertions.assertEquals("7HyKWn3d4xdhAMQYAEVxVq", result.get(0).getMerchant());
        Assertions.assertEquals("EL4vx41Nxc5RYhbqDthjE", result.get(0).getItems().get(0).getId());
        Assertions.assertEquals("Test Item 1", result.get(0).getItems().get(0).getDescription());
        Assertions.assertEquals(6.0, result.get(0).getItems().get(0).getPrice());
        Assertions.assertEquals(1, result.get(0).getItems().get(0).getQuantity());
        Assertions.assertEquals("6spPADZ2h6MfADvnhfsuBt", result.get(0).getItems().get(1).getId());
        Assertions.assertEquals("Test Item 2", result.get(0).getItems().get(1).getDescription());
        Assertions.assertEquals(4.0, result.get(0).getItems().get(1).getPrice());
        Assertions.assertEquals(1, result.get(0).getItems().get(1).getQuantity());
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void it_should_update_bill() throws BitPayException {
        // given
        final String billId = "3Zpmji8bRKxWJo2NJbWX5H";
        AccessTokens accessTokens = this.getAccessTokens();
        BillClient client = this.getBillClient(accessTokens);
        Bill billToUpdate = this.getBill();
        billToUpdate.setStatus("draft");
        Item newItem = new Item();
        newItem.setDescription("Test Item 3");
        newItem.setPrice(5.00);
        newItem.setQuantity(1);

        billToUpdate.getItems().add(newItem);

        this.addServerJsonResponse(
            "/bills/" + billId,
            "PUT",
            null,
            getPreparedJsonDataFromFile("updateBillResponse.json")
        );

        // when
        Bill result = client.updateBill(billToUpdate, billId);

        // then
        Assertions.assertEquals(billId, result.getId());
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("7dnoyMe27VDKY1WNrCTqgK5RWbEi4XkvBSUTTwET6XnNYfWKYdrnSyg7myn7oc3vms", result.getToken());
        Assertions.assertEquals("john@doe.com", result.getEmail());
        Assertions.assertEquals("bill1234-EFGH", result.getNumber());
        Assertions.assertEquals("John Doe", result.getName());
        Assertions.assertEquals("2630 Hegal Place", result.getAddress1());
        Assertions.assertEquals("Apt 42", result.getAddress2());
        Assertions.assertEquals("Alexandria", result.getCity());
        Assertions.assertEquals("VA", result.getState());
        Assertions.assertEquals("23242", result.getZip());
        Assertions.assertEquals("jane@doe.com", result.getCc().get(0));
        Assertions.assertEquals("555-123-456", result.getPhone());
        Assertions.assertEquals("2021-05-31T00:00:00.000Z", result.getDueDate());
        Assertions.assertTrue(result.getPassProcessingFee());
        Assertions.assertEquals("draft", result.getStatus());
        Assertions.assertEquals("https://bitpay.com/bill?id=3Zpmji8bRKxWJo2NJbWX5H&resource=bills", result.getUrl());
        Assertions.assertEquals("7HyKWn3d4xdhAMQYAEVxVq", result.getMerchant());
        Assertions.assertEquals("8vXbhqWDL1A9F66ZwJAiyJ", result.getItems().get(0).getId());
        Assertions.assertEquals("Test Item 1", result.getItems().get(0).getDescription());
        Assertions.assertEquals(6.0, result.getItems().get(0).getPrice());
        Assertions.assertEquals(1, result.getItems().get(0).getQuantity());
        Assertions.assertEquals("WmgAaPiyuY9L6vBX7KvF2R", result.getItems().get(1).getId());
        Assertions.assertEquals("Test Item 2", result.getItems().get(1).getDescription());
        Assertions.assertEquals(4.0, result.getItems().get(1).getPrice());
        Assertions.assertEquals(1, result.getItems().get(1).getQuantity());
        Assertions.assertEquals("89xhSLYPnLDBczsQHCvJ2D", result.getItems().get(2).getId());
        Assertions.assertEquals("Test Item 3", result.getItems().get(2).getDescription());
        Assertions.assertEquals(5.0, result.getItems().get(2).getPrice());
        Assertions.assertEquals(1, result.getItems().get(2).getQuantity());
    }

    @Test
    public void it_should_deliver_bill() throws BitPayException {
        // given
        final String billId = "3Zpmji8bRKxWJo2NJbWX5H";
        AccessTokens accessTokens = this.getAccessTokens();
        BillClient client = this.getBillClient(accessTokens);
        Bill billToUpdate = this.getBill();
        billToUpdate.setStatus("draft");
        Item newItem = new Item();
        newItem.setDescription("Test Item 3");
        newItem.setPrice(5.00);
        newItem.setQuantity(1);

        billToUpdate.getItems().add(newItem);

        this.addServerJsonResponse(
            "/bills/3Zpmji8bRKxWJo2NJbWX5H/deliveries",
            "POST",
            getPreparedJsonDataFromFile("deliverBillRequest.json"),
            getPreparedJsonDataFromFile("deliverBillResponse.json")
        );

        // when
        String result = client.deliverBill(billId, "6EBQR37MgDJPfEiLY3jtRq7eTP2aodR5V5wmXyyZhru5FM5yF4RCGKYQtnT7nhwHjA", false);

        // then
        Assertions.assertEquals("Success", result);
    }

    private Bill getBill() {
        List<String> cc = new ArrayList<String>();
        cc.add("jane@doe.com");

        List<Item> items = new ArrayList<>();
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setDescription("Test Item 1");
        item1.setPrice(6.00);
        item1.setQuantity(1);
        item2.setDescription("Test Item 2");
        item2.setPrice(4.00);
        item2.setQuantity(1);
        items.add(item1);
        items.add(item2);

        final Bill bill = new Bill();
        bill.setToken(AbstractClientTest.MERCHANT_TOKEN);
        bill.setNumber("bill1234-ABCD");
        try {
            bill.setCurrency("USD");
        } catch (BitPayException e) {
            // nothing
        }
        bill.setName("John Doe");
        bill.setAddress1("2630 Hegal Place");
        bill.setAddress2("Apt 42");
        bill.setCity("Alexandria");
        bill.setState("VA");
        bill.setZip("23242");
        bill.setCountry("US");
        bill.setEmail("23242");
        bill.setCc(cc);
        bill.setPhone("555-123-456");
        bill.setDueDate("2021-5-31");
        bill.setPassProcessingFee(true);
        bill.setItems(items);

        return bill;
    }

    private BillClient getBillClient(AccessTokens accessTokens) {
        if (Objects.isNull(accessTokens)) {
            accessTokens = this.getAccessTokens();
        }

        return new BillClient(this.getBitPayClient(), accessTokens);
    }

}
