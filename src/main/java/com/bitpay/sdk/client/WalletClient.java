/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.exceptions.WalletQueryException;
import com.bitpay.sdk.model.Wallet.Wallet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpResponse;

/**
 * The type Wallet client.
 */
public class WalletClient {

    private final BitPayClient bitPayClient;

    /**
     * Instantiates a new Wallet client.
     *
     * @param bitPayClient the bit pay client
     */
    public WalletClient(BitPayClient bitPayClient) {
        this.bitPayClient = bitPayClient;
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
            wallets = Arrays.asList(new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), Wallet[].class));
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
