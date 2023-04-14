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
 * @see <a href="https://bitpay.readme.io/reference/concepts">REST API concepts</a>
 */
public enum Facade {
    /**
     * The broadest set of capabilities against a merchant organization.
     * Allows for create, search, and view actions for Invoices and Bills; ledger download,
     * as well as the creation of new merchant or pos tokens associated with the account.
     */
    MERCHANT("merchant"),
    /**
     * This is the facade which allows merchant to access the Payouts related resources and corresponding endpoints.
     * Access to this facade is not enabled by default, for more information please contact our support channel.
     */
    PAYOUT("payout"),
    /**
     * Limited to creating new invoice or bills and search specific invoices or bills based on their id for
     * the merchant's organization.
     */
    POS("pos");

    private final String value;

    Facade(String value) {
        this.value = value;
    }

    /**
     * Get Facade from value.
     *
     * @param text the text
     * @return the facade
     */
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
