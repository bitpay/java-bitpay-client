/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.payout;

/**
 * The type Recipient status.
 *
 * @see <a href="https://bitpay.readme.io/reference/payouts">REST API Payouts</a>
 */
public class RecipientStatus {

    /**
     * The constant INVITED.
     */
    public static final String INVITED = "invited";
    /**
     * The constant UNVERIFIED.
     */
    public static final String UNVERIFIED = "unverified";
    /**
     * The constant VERIFIED.
     */
    public static final String VERIFIED = "verified";
    /**
     * The constant ACTIVE.
     */
    public static final String ACTIVE = "active";
    /**
     * The constant PAUSED.
     */
    public static final String PAUSED = "paused";
    /**
     * The constant REMOVED.
     */
    public static final String REMOVED = "removed";
}
