/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Objects;

/**
 * The type Config.
 */
public class Config {

    /**
     * Test Url.
     */
    public static final String TEST_URL = "https://test.bitpay.com/";
    /**
     * Production Url.
     */
    public static final String PROD_URL = "https://bitpay.com/";

    /**
     * BitPay Api Version.
     */
    public static final String BITPAY_API_VERSION = "2.0.0";

    /**
     * BitPay Plugin Info Version.
     */
    public static final String BITPAY_PLUGIN_INFO = "BitPay_Java_Client_v10.1.3";
    /**
     * BitPay Api Frame.
     */
    public static final String BITPAY_API_FRAME = "std";
    /**
     * BitPay Api Frame Version.
     */
    public static final String BITPAY_API_FRAME_VERSION = "1.0.0";

    private static final String API_TOKENS_KEY = "ApiTokens";

    private Environment environment;
    private JsonNode envConfig;

    /**
     * Instantiates a new Config.
     */
    public Config() {
    }

    /**
     * Gets environment.
     * <p>
     * There are two environments available for merchants to use the following API.
     * To access these APIs, merchants need to combine the base URL + endpoint and make sure to have API credentials
     * for the corresponding environments.
     * </p>
     *
     * @return the environment
     */
    @JsonIgnore
    public Environment getEnvironment() {
        return this.environment;
    }

    /**
     * Sets environment.
     * <p>
     * There are two environments available for merchants to use the following API.
     * To access these APIs, merchants need to combine the base URL + endpoint and make sure to have API credentials
     * for the corresponding environments.
     * </p>
     *
     * @param environment the environment
     */
    @JsonProperty("Environment")
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }

    /**
     * Gets config for specific environment.
     *
     * @param env the env
     * @return the env config
     * @see <a href="https://github.com/bitpay/java-bitpay-client/blob/master/GUIDE.md#handling-your-client-private-key">
     *     Example of env config as JSON
     *     </a>
     */
    @JsonIgnore
    public JsonNode getEnvConfig(final Environment env) {
        return this.envConfig.path(env.toString());
    }

    /**
     * Sets config for specific environment.
     *
     * @param envConfig the env config
     * @see <a href="https://github.com/bitpay/java-bitpay-client/blob/master/GUIDE.md#handling-your-client-private-key">
     *     Example of env config as JSON
     *     </a>
     */
    @JsonProperty("EnvConfig")
    public void setEnvConfig(final JsonNode envConfig) {
        this.envConfig = envConfig;
    }

    /**
     * Gets api tokens.
     *
     * @return the api tokens
     */
    public ObjectNode getApiTokens() {
        final ObjectNode envConfig = (ObjectNode) this.getEnvConfig(this.getEnvironment());
        ObjectNode apiTokens = null;
        apiTokens = (ObjectNode) envConfig.get(API_TOKENS_KEY);
        if (Objects.isNull(apiTokens)) {
            apiTokens = JsonNodeFactory.instance.objectNode();
            envConfig.putIfAbsent(API_TOKENS_KEY, apiTokens);
        }

        return apiTokens;
    }
}
