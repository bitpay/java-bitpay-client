/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.wallet.Wallet;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * The type Wallet client.
 */
public class WalletClient implements ResourceClient {

    private static WalletClient instance;
    private final BitPayClient bitPayClient;

    /**
     * Instantiates a new Wallet client.
     *
     * @param bitPayClient the bit pay client
     */
    private WalletClient(BitPayClient bitPayClient) {
        this.bitPayClient = bitPayClient;
    }

    /**
     * Factory method for Wallet Client.
     *
     * @param bitPayClient BitPay Client
     * @return WalletClient
     */
    public static WalletClient getInstance(BitPayClient bitPayClient) {
        if (Objects.isNull(instance)) {
            instance = new WalletClient(bitPayClient);
        }

        return instance;
    }

    /**
     * Retrieve all supported wallets.
     *
     * @return A list of wallet objets.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException      BitPayApiException class
     */
    public List<Wallet> getSupportedWallets() throws BitPayApiException, BitPayGenericException {
        List<Wallet> wallets = null;

        HttpResponse response = this.bitPayClient.get("supportedwallets");
        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        try {
            wallets = Arrays.asList(
                JsonMapperFactory.create().readValue(jsonResponse, Wallet[].class)
            );
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Wallet", e.getMessage());
        }

        return wallets;
    }
}
