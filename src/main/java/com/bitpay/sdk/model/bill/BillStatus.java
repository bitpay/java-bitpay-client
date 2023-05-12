/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model.bill;

/**
 * The type Bill status.
 *
 * @see <a href="https://developer.bitpay.com/reference/bills">REST API Bills</a>
 */
public class BillStatus {

    /**
     * The constant Draft.
     */
    public static final String Draft = "draft";
    /**
     * The constant Sent.
     */
    public static final String Sent = "sent";
    /**
     * The constant New.
     */
    public static final String New = "new";
    /**
     * The constant Paid.
     */
    public static final String Paid = "paid";
    /**
     * The constant Complete.
     */
    public static final String Complete = "complete";
}
