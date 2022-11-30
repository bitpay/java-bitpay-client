/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Objects;

/**
 * The enum Environment.
 */
public enum Environment {

    /**
     * Test environment.
     */
    TEST("Test"),

    /**
     * Prod environment.
     */
    PROD("Prod");

    @JsonValue
    private final String value;

    Environment(String value) {
        this.value = value;
    }

    /**
     * Get Environment from value.
     *
     * @param text the text
     * @return the environment
     */
    public static Environment fromValue(final String text) {
        if (Objects.isNull(text)) {
            return null;
        }

        for (final Environment item : values()) {
            if (String.valueOf(item.value).equalsIgnoreCase(text)) {
                return item;
            }
        }

        return null;
    }

    public String toString() {
        return String.valueOf(this.value);
    }
}
