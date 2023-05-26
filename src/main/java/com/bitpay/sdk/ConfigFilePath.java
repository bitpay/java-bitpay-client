/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk;

/**
 * The type Config file path.
 */
public class ConfigFilePath {

    private final String value;

    /**
     * Instantiates a new Config file path.
     *
     * @param configFilePath the configFilePath
     */
    public ConfigFilePath(final String configFilePath) {
        this.value = configFilePath;
    }

    /**
     * Value of config file path.
     *
     * @return the string
     */
    public String value() {
        return this.value;
    }
}
