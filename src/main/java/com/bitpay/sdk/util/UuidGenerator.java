/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.util;

import java.util.UUID;

public class UuidGenerator {

    public String execute() {
        UUID uuid = UUID.randomUUID();

        return uuid.toString();
    }
}
