package com.bitpay.sdk;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * The type Config.
 */
public class Config {

    private String environment;
    private JsonNode envConfig;

    /**
     * Instantiates a new Config.
     */
    public Config() {
    }

    /**
     * Gets environment.
     *
     * There are two environments available for merchants to use the following API.
     * To access these APIs, merchants need to combine the base URL + endpoint and make sure to have API credentials
     * for the corresponding environments.
     *
     * @return the environment
     */
    @JsonIgnore
    public String getEnvironment() {
        return environment;
    }

    /**
     * Sets environment.
     *
     * There are two environments available for merchants to use the following API.
     * To access these APIs, merchants need to combine the base URL + endpoint and make sure to have API credentials
     * for the corresponding environments.
     *
     * @param environment the environment
     */
    @JsonProperty("Environment")
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    /**
     * Gets config for specific environment.
     *
     * @see <a href="https://github.com/bitpay/java-bitpay-client/blob/master/GUIDE.md#handling-your-client-private-key">
     *     Example of env config as JSON
     *     </a>
     *
     * @param env the env
     * @return the env config
     */
    @JsonIgnore
    public JsonNode getEnvConfig(String env) {
        return envConfig.path(env);
    }

    /**
     * Sets config for specific environment.
     *
     * @see <a href="https://github.com/bitpay/java-bitpay-client/blob/master/GUIDE.md#handling-your-client-private-key">
     *     Example of env config as JSON
     *     </a>
     *
     * @param envConfig the env config
     */
    @JsonProperty("EnvConfig")
    public void setEnvConfig(JsonNode envConfig) {
        this.envConfig = envConfig;
    }
}