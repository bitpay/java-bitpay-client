package com.bitpay.sdk.model.Payout;

/**
 * The type Payout status.
 *
 * @see <a href="https://bitpay.readme.io/reference/payouts">REST API Payouts</a>
 */
public class PayoutStatus {
    /**
     * The constant New.
     */
    public static final String New = "new";
    /**
     * The constant Funded.
     */
    public static final String Funded = "funded";
    /**
     * The constant Processing.
     */
    public static final String Processing = "processing";
    /**
     * The constant Complete.
     */
    public static final String Complete = "complete";
    /**
     * The constant Failed.
     */
    public static final String Failed = "failed";
    /**
     * The constant Cancelled.
     */
    public static final String Cancelled = "cancelled";
    /**
     * The constant Paid.
     */
    public static final String Paid = "paid";
    /**
     * The constant Unpaid.
     */
    public static final String Unpaid = "unpaid";
}
