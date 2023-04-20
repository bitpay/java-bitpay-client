/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.exceptions.WalletQueryException;
import com.bitpay.sdk.model.Wallet.Wallet;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.apache.http.HttpResponse;

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
     * @throws WalletQueryException WalletQueryException class
     * @throws BitPayException      BitPayException class
     */
    public List<Wallet> getSupportedWallets() throws WalletQueryException, BitPayException {
        List<Wallet> wallets;

        try {
            HttpResponse response = this.bitPayClient.get("supportedwallets");
            wallets = Arrays.asList(
                JsonMapperFactory.create().readValue(this.bitPayClient.responseToJsonString(response), Wallet[].class));
        } catch (JsonProcessingException e) {
            throw new WalletQueryException(null,
                "failed to deserialize BitPay server response (Wallet) : " + e.getMessage());
        } catch (Exception e) {
            throw new WalletQueryException(null,
                "failed to deserialize BitPay server response (Wallet) : " + e.getMessage());
        }

        return wallets;
    }
}
