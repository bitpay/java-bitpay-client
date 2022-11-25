/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.util;

import java.util.UUID;

/**
 * The type Uuid generator.
 */
public class UuidGenerator {

    /**
     * Generate UUID string.
     *
     * @return the string
     */
    public String execute() {
        UUID uuid = UUID.randomUUID();

        return uuid.toString();
    }
}
