/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.invoice;

/**
 * The type Refund status.
 *
 * @see <a href="https://bitpay.readme.io/reference/refunds">REST API Refunds</a>
 */
public class RefundStatus {

    /**
     * No funds deducted, refund will not proceed automatically -
     * the status must be changed via Update a Refund Request.
     */
    public static final String PREVIEW = "preview";
    /**
     * Funds deducted/allocated if immediate,
     * will proceed when transactions are confirmed and the required data is collected.
     */
    public static final String CREATED = "created";
    /**
     * Refund is in process of being fulfilled.
     */
    public static final String PENDING = "pending";
    /**
     * Refund was canceled by merchant action. Immediate refunds cannot be canceled outside of preview state.
     */
    public static final String CANCELED = "canceled";
    /**
     * Refund was successfully processed.
     */
    public static final String SUCCESS = "success";
    /**
     * Refund failed during processing (this is really more of an internal state).
     */
    public static final String FAILURE = "failure";
}
