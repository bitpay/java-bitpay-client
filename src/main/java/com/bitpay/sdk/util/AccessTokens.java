/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.util;

import com.bitpay.sdk.Config;
import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.Facade;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class AccessTokens {

    private Hashtable<String, String> data; // {facade, token}

    public AccessTokens() {
        initData();
    }

    public AccessTokens(Config configuration) {
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
     * @throws BitPayException BitPayException class
     */
    public String getAccessToken(Facade facade) throws BitPayException {
        return this.getAccessToken(facade.toString());
    }

    /**
     * Retrieve a token associated with a known resource. The token is used to access other related resources.
     *
     * @param key The identifier for the desired resource.
     * @return The token associated with resource.
     * @throws BitPayException BitPayException class
     */
    public String getAccessToken(String key) throws BitPayException {
        if (!this.data.containsKey(key)) {
            throw new BitPayException(null, "There is no token for the specified key : " + key);
        }

        return this.data.get(key);
    }

    /**
     * Add facade token to cache.
     *
     * @param facade Facade
     * @param token Token
     */
    public void put(Facade facade, String token) {
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

    public void addMerchant(String token) {
        this.put(Facade.MERCHANT, token);
    }

    public void addPos(String token) {
        this.put(Facade.POS, token);
    }

    public void addPayout(String token) {
        this.put(Facade.PAYOUT, token);
    }

    /**
     * Specified whether the client has authorization (a token) for the specified facade.
     *
     * @param facade The facade name for which authorization is tested.
     * @return True if this client is authorized, false otherwise.
     */
    public boolean tokenExist(String facade) {
        return this.data.containsKey(facade);
    }

    private void initData() {
        this.data = new Hashtable<String, String>();
    }
}
