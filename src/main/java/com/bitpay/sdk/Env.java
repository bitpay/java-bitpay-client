package com.bitpay.sdk;

/**
 * The type Environment.
 */
public class Env {
    /**
     * Test Environment.
     */
    public static final String Test = "Test";
    /**
     * Production Environment.
     */
    public static final String Prod = "Prod";
    /**
     * Test Url.
     */
    public static final String TestUrl = "https://test.bitpay.com/";
    /**
     * Production Url.
     */
    public static final String ProdUrl = "https://bitpay.com/";
    /**
     * BitPay Api Version.
     */
    public static final String BitpayApiVersion = "2.0.0";
    /**
     * BitPay Plugin Info Version.
     */
    public static final String BitpayPluginInfo = "BitPay_Java_Client_v8.6.0";
    /**
     * BitPay Api Frame.
     */
    public static final String BitpayApiFrame = "std";
    /**
     * BitPay Api Frame Version.
     */
    public static final String BitpayApiFrameVersion = "1.0.0";

    /**
     * The type Tokens.
     */
    public static class Tokens {
        /**
         * The Merchant.
         */
        public String merchant;
        /**
         * The Payout.
         */
        public String payout;
    }
}
