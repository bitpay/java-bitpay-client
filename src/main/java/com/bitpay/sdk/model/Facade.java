/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model;

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
public class Facade {
    /**
     * The constant Merchant.
     */
    public static final String Merchant = "merchant";
    /**
     * The constant Payout.
     */
    public static final String Payout = "payout";
}
