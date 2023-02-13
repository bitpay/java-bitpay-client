/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BillCreationException;
import com.bitpay.sdk.exceptions.BillDeliveryException;
import com.bitpay.sdk.exceptions.BillQueryException;
import com.bitpay.sdk.exceptions.BillUpdateException;
import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.Bill.Bill;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.bitpay.sdk.util.ParameterAdder;
import com.bitpay.sdk.util.TokenContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;

/**
 * The type Bill client.
 */
public class BillClient {

    private final BitPayClient bitPayClient;
    private final TokenContainer accessTokens;

    /**
     * Instantiates a new Bill client.
     *
     * @param bitPayClient the bit pay client
     * @param accessTokens the access tokens
     */
    public BillClient(BitPayClient bitPayClient, TokenContainer accessTokens) {
        this.bitPayClient = bitPayClient;
        this.accessTokens = accessTokens;
    }

    /**
     * Create a BitPay Bill.
     *
     * @param bill        A Bill object with request parameters defined.
     * @param facade      The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay generated Bill object.
     * @throws BitPayException       BitPayException class
     * @throws BillCreationException BillCreationException class
     */
    public Bill create(Bill bill, Facade facade, boolean signRequest)
        throws BitPayException, BillCreationException {
        if (Objects.isNull(bill) || Objects.isNull(facade)) {
            throw new BillCreationException(null, "missing required parameter");
        }
        String token = this.accessTokens.getAccessToken(facade);
        bill.setToken(token);
        JsonMapper mapper = JsonMapperFactory.create();
        String json;

        try {
            json = mapper.writeValueAsString(bill);
        } catch (JsonProcessingException e) {
            throw new BillCreationException(null, "failed to serialize Bill object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.bitPayClient.post("bills", json, signRequest);
            bill = mapper.readerForUpdating(bill).readValue(this.bitPayClient.responseToJsonString(response));
        } catch (Exception e) {
            throw new BillCreationException(null,
                "failed to deserialize BitPay server response (Bill) : " + e.getMessage());
        }

        return bill;
    }

    /**
     * Retrieve a BitPay bill by bill id using the specified facade.
     *
     * @param billId      The id of the bill to retrieve.
     * @param facade      The facade used to retrieve it.
     * @param signRequest Signed request.
     * @return A BitPay Bill object.
     * @throws BitPayException    BitPayException class
     * @throws BillQueryException BillQueryException class
     */
    public Bill get(String billId, Facade facade, boolean signRequest) throws BitPayException, BillQueryException {
        if (Objects.isNull(billId) || Objects.isNull(facade)) {
            throw new BillQueryException(null, "missing required parameter");
        }
        String token = this.accessTokens.getAccessToken(facade);
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", token);

        Bill bill;

        try {
            HttpResponse response = this.bitPayClient.get("bills/" + billId, params, signRequest);
            bill = JsonMapperFactory.create().readValue(this.bitPayClient.responseToJsonString(response), Bill.class);
        } catch (JsonProcessingException e) {
            throw new BillQueryException(null,
                "failed to deserialize BitPay server response (Bill) : " + e.getMessage());
        } catch (Exception e) {
            throw new BillQueryException(null,
                "failed to deserialize BitPay server response (Bill) : " + e.getMessage());
        }

        return bill;
    }

    /**
     * Retrieve a collection of BitPay bills.
     *
     * @param status The status to filter the bills.
     * @return A list of BitPay Bill objects.
     * @throws BitPayException    BitPayException class
     * @throws BillQueryException BillQueryException class
     */
    public List<Bill>getBills(String status) throws BitPayException, BillQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        ParameterAdder.execute(params, "status", status);

        List<Bill> bills;

        try {
            HttpResponse response = this.bitPayClient.get("bills", params);
            bills = Arrays.asList(JsonMapperFactory.create().readValue(this.bitPayClient.responseToJsonString(response), Bill[].class));
        } catch (JsonProcessingException e) {
            throw new BillQueryException(null,
                "failed to deserialize BitPay server response (Bills) : " + e.getMessage());
        } catch (Exception e) {
            throw new BillQueryException(null,
                "failed to deserialize BitPay server response (Bills) : " + e.getMessage());
        }

        return bills;
    }

    /**
     * Retrieve a collection of BitPay bills.
     *
     * @return A list of BitPay Bill objects.
     * @throws BitPayException    BitPayException class
     * @throws BillQueryException BillQueryException class
     */
    public List<Bill> getBills() throws BitPayException, BillQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        List<Bill> bills;

        try {
            HttpResponse response = this.bitPayClient.get("bills", params);
            bills = Arrays.asList(JsonMapperFactory.create().readValue(this.bitPayClient.responseToJsonString(response), Bill[].class));
        } catch (JsonProcessingException e) {
            throw new BillQueryException(null,
                "failed to deserialize BitPay server response (Bills) : " + e.getMessage());
        } catch (Exception e) {
            throw new BillQueryException(null,
                "failed to deserialize BitPay server response (Bills) : " + e.getMessage());
        }

        return bills;
    }

    /**
     * Update a BitPay Bill.
     *
     * @param bill   A Bill object with the parameters to update defined.
     * @param billId The Id of the Bill to udpate.
     * @return An updated Bill object.
     * @throws BitPayException     BitPayException class
     * @throws BillUpdateException BillUpdateException class
     */
    public Bill update(Bill bill, String billId) throws BitPayException, BillUpdateException {
        if (Objects.isNull(billId) || Objects.isNull(bill)) {
            throw new BillUpdateException(null, "missing required parameter");
        }
        JsonMapper mapper = JsonMapperFactory.create();
        String json;
        try {
            json = mapper.writeValueAsString(bill);
        } catch (JsonProcessingException e) {
            throw new BillUpdateException(null, "failed to serialize Bill object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.bitPayClient.update("bills/" + billId, json);
            bill = mapper.readerForUpdating(bill).readValue(this.bitPayClient.responseToJsonString(response));
        } catch (Exception e) {
            throw new BillUpdateException(null,
                "failed to deserialize BitPay server response (Bill) : " + e.getMessage());
        }

        return bill;
    }

    /**
     * Deliver a BitPay Bill.
     *
     * @param billId      The id of the requested bill.
     * @param billToken   The token of the requested bill.
     * @param signRequest Allow unsigned request
     * @return A response status returned from the API.
     * @throws BillDeliveryException BillDeliveryException class
     */
    public String deliver(String billId, String billToken, boolean signRequest) throws BillDeliveryException {
        if (Objects.isNull(billId) || Objects.isNull(billToken)) {
            throw new BillDeliveryException(null, "missing required parameter");
        }

        Map<String, String> map = new HashMap<>();
        map.put("token", billToken);
        JsonMapper mapper = JsonMapperFactory.create();
        String json;
        String result;
        try {
            json = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new BillDeliveryException(null, "failed to serialize Bill object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.bitPayClient.post("bills/" + billId + "/deliveries", json, signRequest);
            result = this.bitPayClient.responseToJsonString(response).replace("\"", "");
        } catch (Exception e) {
            throw new BillDeliveryException(null,
                "failed to deserialize BitPay server response (Bill) : " + e.getMessage());
        }

        return result;
    }
}
