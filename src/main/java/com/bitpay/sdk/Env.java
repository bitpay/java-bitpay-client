package com.bitpay.sdk;

public class Env {
    public static final String Test = "Test";
    public static final String Prod = "Prod";
    public static final String TestUrl = "https://test.bitpay.com/";
    public static final String ProdUrl = "https://bitpay.com/";
    public static final String BitpayApiVersion = "2.0.0";
    public static final String BitpayPluginInfo = "BitPay_Java_Client_v8.0.2201";
    public static final String BitpayApiFrame = "std";
    public static final String BitpayApiFrameVersion = "1.0.0";

    public static class Tokens {
        public String merchant;
        public String payout;
    }
}
