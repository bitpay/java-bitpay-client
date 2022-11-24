/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.util;

import com.bitpay.sdk.Config;
import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.Facade;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Hashtable;

public class AccessTokenCache {

    private final Config configuration;
    private Hashtable<String, String> data; // {facade, token}

    public AccessTokenCache(Config configuration) {
        this.data = new Hashtable<String, String>();
        this.configuration = configuration;
    }

    /**
     * Retrieve a token associated with a known resource. The token is used to access other related resources.
     *
     * @param facade The identifier for the desired resource.
     * @return The token associated with resource.
     * @throws BitPayException BitPayException class
     */
    public String getAccessToken(Facade facade) throws BitPayException {
        if (!this.data.containsKey(facade.toString())) {
            throw new BitPayException(null, "There is no token for the specified key : " + facade);
        }

        return this.data.get(facade.toString());
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
     * Put value to hashtable.
     *
     * @param key   string
     * @param value string
     */
    public void put(String key, String value) {
        this.data.put(key, value);
    }

    /**
     * Returns the token for the specified facade.
     *
     * @param facade The facade name for which the token is requested.
     * @return The token for the given facade.
     */
    public String getTokenByFacade(String facade) {
        if (!this.tokenExist(facade))
            return "";

        return this.data.get(facade);
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

    /**
     * Add this token to the token cache.
     *
     * @param key   The token type.
     * @param token The token value.
     */
    public void cacheToken(String key, String token) throws BitPayException {
        // we add the token to the runtime dictionary
        if (tokenExist(key)) {
            this.data.put(key, token);
        }

        // we also persist the token
        writeTokenCache();
    }

    public void clear() {
        this.data = new Hashtable<String, String>();
    }

    /**
     * Persist the token cache to disk.
     *
     * @throws BitPayException BitPayException class
     */
    private void writeTokenCache() throws BitPayException {
        try {
            JsonMapper mapper = JsonMapperFactory.create();
            JsonNode tokens = mapper.valueToTree(this.data);
            ((ObjectNode) this.configuration.getEnvConfig(this.configuration.getEnvironment()))
                .put("ApiTokens", tokens);
        } catch (Exception e) {
            throw new BitPayException(null, "When trying to write the tokens : " + e.getMessage());
        }
    }
}
