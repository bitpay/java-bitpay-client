/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.util;

import com.bitpay.sdk.Config;
import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.Facade;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * This class provide container for tokens.
 */
public class TokenContainer {

    private Hashtable<String, String> data; // {facade, token}

    /**
     * Instantiates a new Access tokens.
     */
    public TokenContainer() {
        initData();
    }

    /**
     * Instantiates a new Access tokens.
     *
     * @param configuration Configuration file
     */
    public TokenContainer(Config configuration) {
        initData();

        Iterator<Map.Entry<String, JsonNode>> tokens = configuration.getApiTokens().fields();
        while (tokens.hasNext()) {
            Map.Entry<String, JsonNode> next = tokens.next();
            if (!next.getValue().asText().isEmpty()) {
                this.put(next.getKey(), next.getValue().asText());
            }
        }
    }

    /**
     * Retrieve a token associated with a known resource. The token is used to access other related resources.
     *
     * @param facade The identifier for the desired resource.
     * @return The token associated with resource.
     * @throws BitPayGenericException BitPayGenericException class
     */
    public String getAccessToken(Facade facade) throws BitPayGenericException {
        return this.getAccessToken(facade.toString());
    }

    /**
     * Retrieve a token associated with a known resource. The token is used to access other related resources.
     *
     * @param key The identifier for the desired resource.
     * @return The token associated with resource.
     * @throws BitPayGenericException BitPayGenericException class
     */
    public String getAccessToken(String key) throws BitPayGenericException {
        if (!this.data.containsKey(key)) {
            BitPayExceptionProvider.throwGenericExceptionWithMessage(
                "There is no token for the specified key : " + key);
        }

        return this.data.get(key);
    }

    /**
     * Add facade token to cache.
     *
     * @param facade Facade
     * @param token  Token
     */
    public void put(
        Facade facade,
        String token
    ) {
        this.data.put(facade.toString(), token);
    }

    /**
     * Put value to hashtable.
     *
     * @param key   string
     * @param value string
     */
    public void put(String key, String value) {
        this.data.put(key, value);
    }

    /**
     * Add pos token.
     *
     * @param token the token
     */
    public void addPos(String token) {
        this.put(Facade.POS, token);
    }

    /**
     * Add merchant token.
     *
     * @param token the token
     */
    public void addMerchant(String token) {
        this.put(Facade.MERCHANT, token);
    }

    /**
     * Add payout token.
     *
     * @param token the token
     */
    public void addPayout(String token) {
        this.put(Facade.PAYOUT, token);
    }

    /**
     * Specified whether the client has authorization (a token) for the specified facade.
     *
     * @param facade The facade name for which authorization is tested.
     * @return True if this client is authorized, false otherwise.
     */
    public boolean tokenExists(String facade) {
        return this.data.containsKey(facade);
    }

    /**
     * Specified whether the client has authorization (a token) for the specified facade.
     *
     * @param facade The facade for which authorization is tested.
     * @return True if this client is authorized, false otherwise.
     */
    public boolean tokenExists(Facade facade) {
        return this.tokenExists(facade.toString());
    }

    private void initData() {
        this.data = new Hashtable<String, String>();
    }
}
