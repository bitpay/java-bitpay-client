/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.exceptions.BitPayValidationException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.bill.Bill;
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
import org.apache.http.message.BasicNameValuePair;

/**
 * The type Bill client.
 */
public class BillClient implements ResourceClient {

    private static BillClient instance;
    private final BitPayClient bitPayClient;
    private final TokenContainer accessTokens;

    /**
     * Instantiates a new Bill client.
     *
     * @param bitPayClient the bit pay client
     * @param accessTokens the access tokens
     */
    private BillClient(
        BitPayClient bitPayClient,
        TokenContainer accessTokens
    ) {
        this.bitPayClient = bitPayClient;
        this.accessTokens = accessTokens;
    }

    /**
     * Factory method for Bill Client.
     *
     * @param bitPayClient BitPay Client
     * @param accessTokens Access Tokens
     * @return BillClient
     */
    public static BillClient getInstance(
        BitPayClient bitPayClient,
        TokenContainer accessTokens
    ) {
        if (Objects.isNull(instance)) {
            instance = new BillClient(bitPayClient, accessTokens);
        }

        return instance;
    }

    /**
     * Create a BitPay Bill.
     *
     * @param bill        A Bill object with request parameters defined.
     * @param facade      The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay generated Bill object.
     * @throws BitPayValidationException BitPayValidationException
     * @throws BitPayGenericException BitPayGenericException
     * @throws BitPayApiException BitPayApiException
     */
    public Bill create(
        Bill bill,
        Facade facade,
        boolean signRequest
    ) throws BitPayValidationException, BitPayGenericException, BitPayApiException {
        if (Objects.isNull(bill) || Objects.isNull(facade)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }
        String token = this.accessTokens.getAccessToken(facade);
        bill.setToken(token);
        JsonMapper mapper = JsonMapperFactory.create();
        String json = null;

        try {
            json = mapper.writeValueAsString(bill);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwSerializeResourceException("Bill", e.getMessage());
        }

        String jsonResponse = this.bitPayClient.post("bills", json, signRequest);

        try {
            bill = mapper.readerForUpdating(bill).readValue(jsonResponse);
        } catch (Exception e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Bill", e.getMessage());
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
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayValidationException BitPayValidationException class
     * @throws BitPayApiException BitPayApiException class
     */
    public Bill get(
        String billId,
        Facade facade,
        boolean signRequest
    ) throws BitPayGenericException, BitPayApiException {
        if (Objects.isNull(billId) || Objects.isNull(facade)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }
        String token = this.accessTokens.getAccessToken(facade);
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", token);

        Bill bill = null;
        String jsonResponse = this.bitPayClient.get("bills/" + billId, params, signRequest);

        try {
            bill = JsonMapperFactory.create().readValue(jsonResponse, Bill.class);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Bill", e.getMessage());
        }

        return bill;
    }

    /**
     * Retrieve a collection of BitPay bills.
     *
     * @param status The status to filter the bills.
     * @return A list of BitPay Bill objects.
     * @throws BitPayGenericException BitPayGenericException
     * @throws BitPayApiException BitPayApiException
     */
    public List<Bill> getBills(String status) throws BitPayGenericException, BitPayApiException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));
        ParameterAdder.execute(params, "status", status);

        List<Bill> bills = null;

        try {
            String jsonResponse = this.bitPayClient.get("bills", params);
            bills = Arrays.asList(
                JsonMapperFactory.create().readValue(jsonResponse, Bill[].class));
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Bill", e.getMessage());
        }

        return bills;
    }

    /**
     * Retrieve a collection of BitPay bills.
     *
     * @return A list of BitPay Bill objects.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     */
    public List<Bill> getBills() throws BitPayGenericException, BitPayApiException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        ParameterAdder.execute(params, "token", this.accessTokens.getAccessToken(Facade.MERCHANT));

        List<Bill> bills = null;
        String jsonResponse = this.bitPayClient.get("bills", params);

        try {
            bills = Arrays.asList(
                JsonMapperFactory.create().readValue(jsonResponse, Bill[].class));
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Bills", e.getMessage());
        }

        return bills;
    }

    /**
     * Update a BitPay Bill.
     *
     * @param bill   A Bill object with the parameters to update defined.
     * @param billId The Id of the Bill to udpate.
     * @return An updated Bill object.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayValidationException BitPayValidationException class
     * @throws BitPayApiException BitPayApiException class
     */
    public Bill update(
        Bill bill,
        String billId
    ) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(billId) || Objects.isNull(bill)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        JsonMapper mapper = JsonMapperFactory.create();
        String json = null;

        try {
            json = mapper.writeValueAsString(bill);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwSerializeResourceException("Bill", e.getMessage());
        }

        String jsonResponse = this.bitPayClient.update("bills/" + billId, json);

        try {
            bill = mapper.readerForUpdating(bill).readValue(jsonResponse);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Bills", e.getMessage());
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
     * @throws BitPayApiException BitPayApiException class
     * @throws BitPayValidationException BitPayValidationException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public String deliver(
        String billId,
        String billToken,
        boolean signRequest
    ) throws BitPayApiException, BitPayValidationException, BitPayGenericException {
        if (Objects.isNull(billId) || Objects.isNull(billToken)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        Map<String, String> map = new HashMap<>();
        map.put("token", billToken);
        JsonMapper mapper = JsonMapperFactory.create();
        String json = null;

        try {
            json = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwEncodeException(e.getMessage());
        }

        String response = this.bitPayClient.post("bills/" + billId + "/deliveries", json, signRequest);
        return response.replace("\"", "");
    }
}
