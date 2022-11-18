/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model;

import java.util.Objects;

/**
 * Facades named collections of capabilities that can be granted,
 * such as the ability to create invoices or grant refunds.
 * In the ticket analogy, this corresponds to the ticket 'level',
 * where a 'VIP' ticket would confer broader access than a 'Standard' level ticket. When registering an Identity,
 * it is against a specific facade.
 * Best practices suggest that the requested facade should be limited to the minimum level that
 * grants the required capabilities.
 *
 * @see <a href="https://bitpay.com/api/#rest-api-concepts-api-tokens">REST API concepts</a>
 */
public enum Facade {
    /**
     * The constant Merchant.
     */
    MERCHANT("merchant"),
    /**
     * The constant Payout.
     */
    PAYOUT("payout");

    private final String value;

    Facade(String value) {
        this.value = value;
    }

    public static Facade fromValue(final String text) {
        if (Objects.isNull(text)) {
            return null;
        }

        for (final Facade item : values()) {
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
