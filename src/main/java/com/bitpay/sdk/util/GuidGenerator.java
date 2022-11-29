/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.util;

import java.util.UUID;

/**
 * The type Guid generator.
 */
public class GuidGenerator {

    /**
     * Generate GUID string based on UUID.
     *
     * @return the string
     */
    public String execute() {
        UUID uuid = UUID.randomUUID();

        return uuid.toString();
    }
}
